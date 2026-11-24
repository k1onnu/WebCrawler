package com.webcrawler.config;

import io.micrometer.context.ContextExecutorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class ExecutorConfig {
    @Value("${crawler.pool.size:4}")
    private int poolSize;

    @Value("${crawler.scheduler.pool.size:1}")
    private int schedulerPoolSize;

    @Bean(name = "crawlerPool", destroyMethod = "shutdown")
    public ExecutorService crawlerPool() {
        ExecutorService delegate = Executors.newFixedThreadPool(poolSize, threadFactory("crawler"));
        return ContextExecutorService.wrap(delegate);
    }

    @Bean(name = "schedulerPool", destroyMethod = "shutdown")
    public ScheduledExecutorService schedulerPool() {
        return Executors.newScheduledThreadPool(
                schedulerPoolSize,
                threadFactory("scheduler")
        );
    }

    private ThreadFactory threadFactory(String prefix) {
        AtomicInteger counter = new AtomicInteger(0);
        return r -> {
            Thread thread = new Thread(r);
            thread.setName("%s-%d".formatted(prefix, counter.getAndIncrement()));
            thread.setDaemon(false);
            return thread;
        };
    }
}
