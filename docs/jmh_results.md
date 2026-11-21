# JMH version: 1.37
# VM version: JDK 21.0.12.1, OpenJDK 64-Bit Server VM, 21.0.12.1+1-LTS
# VM invoker: C:\Users\k1\.jdks\ms-21.0.12.1\bin\java.exe
# VM options: -javaagent:E:\IntelliJ IDEA Community Edition 2025.1\lib\idea_rt.jar=63207 -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8
# Blackhole mode: compiler (auto-detected, use -Djmh.blackhole.autoDetect=false to disable)
# Warmup: 3 iterations, 1 s each
# Measurement: 5 iterations, 1 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: com.webcrawler.ParserBenchmark.forManual

# Run progress: 0.00% complete, ETA 00:01:36
# Fork: 1 of 2
# Warmup Iteration   1: 1.204 ms/op
# Warmup Iteration   2: 1.216 ms/op
# Warmup Iteration   3: 1.188 ms/op
Iteration   1: 1.187 ms/op
Iteration   2: 1.188 ms/op
Iteration   3: 1.187 ms/op
Iteration   4: 1.188 ms/op
Iteration   5: 1.192 ms/op

# Run progress: 8.33% complete, ETA 00:01:40
# Fork: 2 of 2
# Warmup Iteration   1: 1.206 ms/op
# Warmup Iteration   2: 1.189 ms/op
# Warmup Iteration   3: 1.188 ms/op
Iteration   1: 1.188 ms/op
Iteration   2: 1.189 ms/op
Iteration   3: 1.188 ms/op
Iteration   4: 1.188 ms/op
Iteration   5: 1.187 ms/op


Result "com.webcrawler.ParserBenchmark.forManual":
  1.188 ±(99.9%) 0.002 ms/op [Average]
  (min, avg, max) = (1.187, 1.188, 1.192), stdev = 0.001
  CI (99.9%): [1.186, 1.190] (assumes normal distribution)


# JMH version: 1.37
# VM version: JDK 21.0.12.1, OpenJDK 64-Bit Server VM, 21.0.12.1+1-LTS
# VM invoker: C:\Users\k1\.jdks\ms-21.0.12.1\bin\java.exe
# VM options: -javaagent:E:\IntelliJ IDEA Community Edition 2025.1\lib\idea_rt.jar=63207 -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8
# Blackhole mode: compiler (auto-detected, use -Djmh.blackhole.autoDetect=false to disable)
# Warmup: 3 iterations, 1 s each
# Measurement: 5 iterations, 1 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: com.webcrawler.ParserBenchmark.forManualCollect

# Run progress: 16.67% complete, ETA 00:01:30
# Fork: 1 of 2
# Warmup Iteration   1: 1.355 ms/op
# Warmup Iteration   2: 1.290 ms/op
# Warmup Iteration   3: 1.279 ms/op
Iteration   1: 1.286 ms/op
Iteration   2: 1.285 ms/op
Iteration   3: 1.284 ms/op
Iteration   4: 1.281 ms/op
Iteration   5: 1.287 ms/op

# Run progress: 25.00% complete, ETA 00:01:21
# Fork: 2 of 2
# Warmup Iteration   1: 1.362 ms/op
# Warmup Iteration   2: 1.293 ms/op
# Warmup Iteration   3: 1.287 ms/op
Iteration   1: 1.294 ms/op
Iteration   2: 1.314 ms/op
Iteration   3: 1.304 ms/op
Iteration   4: 1.306 ms/op
Iteration   5: 1.308 ms/op


Result "com.webcrawler.ParserBenchmark.forManualCollect":
  1.295 ±(99.9%) 0.018 ms/op [Average]
  (min, avg, max) = (1.281, 1.295, 1.314), stdev = 0.012
  CI (99.9%): [1.277, 1.313] (assumes normal distribution)


# JMH version: 1.37
# VM version: JDK 21.0.12.1, OpenJDK 64-Bit Server VM, 21.0.12.1+1-LTS
# VM invoker: C:\Users\k1\.jdks\ms-21.0.12.1\bin\java.exe
# VM options: -javaagent:E:\IntelliJ IDEA Community Edition 2025.1\lib\idea_rt.jar=63207 -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8
# Blackhole mode: compiler (auto-detected, use -Djmh.blackhole.autoDetect=false to disable)
# Warmup: 3 iterations, 1 s each
# Measurement: 5 iterations, 1 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: com.webcrawler.ParserBenchmark.forNaive

# Run progress: 33.33% complete, ETA 00:01:12
# Fork: 1 of 2
# Warmup Iteration   1: 0.769 ms/op
# Warmup Iteration   2: 0.739 ms/op
# Warmup Iteration   3: 0.759 ms/op
Iteration   1: 0.761 ms/op
Iteration   2: 0.761 ms/op
Iteration   3: 0.763 ms/op
Iteration   4: 0.746 ms/op
Iteration   5: 0.720 ms/op

# Run progress: 41.67% complete, ETA 00:01:03
# Fork: 2 of 2
# Warmup Iteration   1: 0.750 ms/op
# Warmup Iteration   2: 0.765 ms/op
# Warmup Iteration   3: 0.761 ms/op
Iteration   1: 0.726 ms/op
Iteration   2: 0.738 ms/op
Iteration   3: 0.746 ms/op
Iteration   4: 0.768 ms/op
Iteration   5: 0.740 ms/op


Result "com.webcrawler.ParserBenchmark.forNaive":
  0.747 ±(99.9%) 0.025 ms/op [Average]
  (min, avg, max) = (0.720, 0.747, 0.768), stdev = 0.016
  CI (99.9%): [0.722, 0.772] (assumes normal distribution)


# JMH version: 1.37
# VM version: JDK 21.0.12.1, OpenJDK 64-Bit Server VM, 21.0.12.1+1-LTS
# VM invoker: C:\Users\k1\.jdks\ms-21.0.12.1\bin\java.exe
# VM options: -javaagent:E:\IntelliJ IDEA Community Edition 2025.1\lib\idea_rt.jar=63207 -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8
# Blackhole mode: compiler (auto-detected, use -Djmh.blackhole.autoDetect=false to disable)
# Warmup: 3 iterations, 1 s each
# Measurement: 5 iterations, 1 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: com.webcrawler.ParserBenchmark.forRegex

# Run progress: 50.00% complete, ETA 00:00:54
# Fork: 1 of 2
# Warmup Iteration   1: 17.674 ms/op
# Warmup Iteration   2: 16.930 ms/op
# Warmup Iteration   3: 16.958 ms/op
Iteration   1: 16.821 ms/op
Iteration   2: 16.670 ms/op
Iteration   3: 16.754 ms/op
Iteration   4: 16.733 ms/op
Iteration   5: 16.908 ms/op

# Run progress: 58.33% complete, ETA 00:00:45
# Fork: 2 of 2
# Warmup Iteration   1: 19.102 ms/op
# Warmup Iteration   2: 18.357 ms/op
# Warmup Iteration   3: 18.366 ms/op
Iteration   1: 18.380 ms/op
Iteration   2: 18.356 ms/op
Iteration   3: 18.290 ms/op
Iteration   4: 18.391 ms/op
Iteration   5: 18.289 ms/op


Result "com.webcrawler.ParserBenchmark.forRegex":
  17.559 ±(99.9%) 1.250 ms/op [Average]
  (min, avg, max) = (16.670, 17.559, 18.391), stdev = 0.827
  CI (99.9%): [16.309, 18.810] (assumes normal distribution)


# JMH version: 1.37
# VM version: JDK 21.0.12.1, OpenJDK 64-Bit Server VM, 21.0.12.1+1-LTS
# VM invoker: C:\Users\k1\.jdks\ms-21.0.12.1\bin\java.exe
# VM options: -javaagent:E:\IntelliJ IDEA Community Edition 2025.1\lib\idea_rt.jar=63207 -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8
# Blackhole mode: compiler (auto-detected, use -Djmh.blackhole.autoDetect=false to disable)
# Warmup: 3 iterations, 1 s each
# Measurement: 5 iterations, 1 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: com.webcrawler.ParserBenchmark.parallelManual

# Run progress: 66.67% complete, ETA 00:00:36
# Fork: 1 of 2
# Warmup Iteration   1: 0.167 ms/op
# Warmup Iteration   2: 0.158 ms/op
# Warmup Iteration   3: 0.157 ms/op
Iteration   1: 0.159 ms/op
Iteration   2: 0.156 ms/op
Iteration   3: 0.157 ms/op
Iteration   4: 0.157 ms/op
Iteration   5: 0.156 ms/op

# Run progress: 75.00% complete, ETA 00:00:27
# Fork: 2 of 2
# Warmup Iteration   1: 0.165 ms/op
# Warmup Iteration   2: 0.157 ms/op
# Warmup Iteration   3: 0.160 ms/op
Iteration   1: 0.163 ms/op
Iteration   2: 0.159 ms/op
Iteration   3: 0.156 ms/op
Iteration   4: 0.155 ms/op
Iteration   5: 0.158 ms/op


Result "com.webcrawler.ParserBenchmark.parallelManual":
  0.158 ±(99.9%) 0.003 ms/op [Average]
  (min, avg, max) = (0.155, 0.158, 0.163), stdev = 0.002
  CI (99.9%): [0.154, 0.161] (assumes normal distribution)


# JMH version: 1.37
# VM version: JDK 21.0.12.1, OpenJDK 64-Bit Server VM, 21.0.12.1+1-LTS
# VM invoker: C:\Users\k1\.jdks\ms-21.0.12.1\bin\java.exe
# VM options: -javaagent:E:\IntelliJ IDEA Community Edition 2025.1\lib\idea_rt.jar=63207 -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8
# Blackhole mode: compiler (auto-detected, use -Djmh.blackhole.autoDetect=false to disable)
# Warmup: 3 iterations, 1 s each
# Measurement: 5 iterations, 1 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: com.webcrawler.ParserBenchmark.streamManual

# Run progress: 83.33% complete, ETA 00:00:18
# Fork: 1 of 2
# Warmup Iteration   1: 1.093 ms/op
# Warmup Iteration   2: 1.077 ms/op
# Warmup Iteration   3: 1.076 ms/op
Iteration   1: 1.076 ms/op
Iteration   2: 1.081 ms/op
Iteration   3: 1.070 ms/op
Iteration   4: 1.048 ms/op
Iteration   5: 1.049 ms/op

# Run progress: 91.67% complete, ETA 00:00:09
# Fork: 2 of 2
# Warmup Iteration   1: 1.083 ms/op
# Warmup Iteration   2: 1.072 ms/op
# Warmup Iteration   3: 1.071 ms/op
Iteration   1: 1.070 ms/op
Iteration   2: 1.083 ms/op
Iteration   3: 1.066 ms/op
Iteration   4: 1.041 ms/op
Iteration   5: 1.042 ms/op


Result "com.webcrawler.ParserBenchmark.streamManual":
  1.063 ±(99.9%) 0.024 ms/op [Average]
  (min, avg, max) = (1.041, 1.063, 1.083), stdev = 0.016
  CI (99.9%): [1.038, 1.087] (assumes normal distribution)


# Run complete. Total time: 00:01:48

REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
experiments, perform baseline and negative tests that provide experimental control, make sure
the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
Do not assume the numbers tell you what you want them to tell.

NOTE: Current JVM experimentally supports Compiler Blackholes, and they are in use. Please exercise
extra caution when trusting the results, look into the generated code to check the benchmark still
works, and factor in a small probability of new VM bugs. Additionally, while comparisons between
different JVMs are already problematic, the performance difference caused by different Blackhole
modes can be very significant. Please make sure you use the consistent Blackhole mode for comparisons.

Benchmark                         Mode  Cnt   Score   Error  Units
ParserBenchmark.forManual         avgt   10   1.188 ± 0.002  ms/op
ParserBenchmark.forManualCollect  avgt   10   1.295 ± 0.018  ms/op
ParserBenchmark.forNaive          avgt   10   0.747 ± 0.025  ms/op
ParserBenchmark.forRegex          avgt   10  17.559 ± 1.250  ms/op
ParserBenchmark.parallelManual    avgt   10   0.158 ± 0.003  ms/op
ParserBenchmark.streamManual      avgt   10   1.063 ± 0.024  ms/op