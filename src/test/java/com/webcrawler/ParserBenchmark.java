package com.webcrawler;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(2)
public class ParserBenchmark {

    private static final Pattern EMAIL = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    private List<String> candidates;

    @Setup
    public void setup() {
        candidates = new ArrayList<>(100_000);
        String[] domains = {
                "gmail.com", "yandex.ru", "mail.ru",          // массовые
                "utmn.ru", "urfu.ru",                          // вузовские
                "company.ru", "my-shop.ru", "kafe-romashka.ru" // бизнес/кастом
        };
        for (int i = 0; i < 100_000; i++) {
            if (i % 10 == 0) {
                candidates.add("not-an-email-" + i);       // ~10% мусора
            } else {
                candidates.add("user" + i + "@" + domains[i % domains.length]);
            }
        }
    }

    static boolean isValidManual(String s) {
        int at = s.indexOf('@');
        if (at <= 0 || at != s.lastIndexOf('@')) return false;
        int dot = s.lastIndexOf('.');
        if (dot <= at + 1 || dot == s.length() - 1) return false;
        return s.length() - dot - 1 >= 2;
    }

    static boolean isValidRegex(String s) {
        return EMAIL.matcher(s).matches();
    }

    static boolean isValidNaive(String s) {
        int at = s.indexOf('@');
        return at > 0 && s.indexOf('.', at) > 0;
    }

    // ось 1
    @Benchmark
    public long forManual() {
        long n = 0;
        for (String s : candidates) if (isValidManual(s)) n++;
        return n;
    }

    @Benchmark
    public long streamManual() {
        return candidates.stream().filter(ParserBenchmark::isValidManual).count();
    }

    @Benchmark
    public long parallelManual() {
        return candidates.parallelStream().filter(ParserBenchmark::isValidManual).count();
    }

    // ось 2
    @Benchmark
    public long forRegex() {
        long n = 0;
        for (String s : candidates) if (isValidRegex(s)) n++;
        return n;
    }

    @Benchmark
    public long forNaive() {
        long n = 0;
        for (String s : candidates) if (isValidNaive(s)) n++;
        return n;
    }

    @Benchmark
    public List<String> forManualCollect() {
        List<String> result = new ArrayList<>();
        for (String s : candidates) if (isValidManual(s)) result.add(s);
        return result;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ParserBenchmark.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}