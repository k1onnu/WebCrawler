package com.webcrawler.concurrency;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FileLogger extends Thread {
    private static final String LOG_FILE = "crawler.log";
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private volatile boolean running = true;

    public FileLogger() {
        setName("file-logger");
        setDaemon(true);
        start();
    }

    public void run() {
        try (BufferedWriter writer = Files.newBufferedWriter(
                Path.of(LOG_FILE),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {

            while (running) {
                try {
                    String message = queue.take();
                    String line = "%s | %s"
                            .formatted(LocalDateTime
                            .now()
                            .format(TIME_FORMAT), message);

                    writer.write(line);
                    writer.newLine();
                    writer.flush();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Не удалось открыть файл лога: " + e.getMessage());
        }
    }

    public void log(String message) {
        queue.offer(message);
    }

    public void shutdown() {
        running = false;
        interrupt();
    }
}
