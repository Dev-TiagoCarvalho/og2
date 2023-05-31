package dev.online.config;

import dev.online.monitor.method.component.FileWriterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.File;
import java.io.IOException;

@Configuration
@EnableAsync
public class FileWriterConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileWriterConfig.class);

    @Value("${auditor.file.filepath}")
    private String file;

    @Bean
    public FileWriterService fileWriterService() throws IOException {
        String name = this.file == null ? "auditor.txt" : this.file;
        File file = new File(name);
        if(!file.exists()) LOGGER.warn("File {} created {}.", file.getName(), file.createNewFile() ? "success" : "failed");
        LOGGER.info("File {} is ready.", file.getName());
        return new FileWriterService(file);
    }
}
