package dev.online.monitor.method.component;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.annotation.Async;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import java.io.IOException;

public class FileWriterService implements DisposableBean {

    private final FileWriter fw;
    private final BufferedWriter bw;
    private final PrintWriter writer;

    public FileWriterService(File file) throws IOException {
        this.fw = new FileWriter(file, true);
        this.bw = new BufferedWriter(fw);
        this.writer = new PrintWriter(bw);
    }

    public void writeLine(String message) {
        writer.println(message);
        flush();
    }

    @Async
    private void flush() {
        writer.flush();
    }

    @Override
    public void destroy() throws Exception {
        this.writer.close();
        this.bw.close();
        this.fw.close();
    }
}
