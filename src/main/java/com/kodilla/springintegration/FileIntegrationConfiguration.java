package com.kodilla.springintegration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileNameGenerator;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;

import java.io.File;
import java.io.IOException;

@Configuration
public class FileIntegrationConfiguration {
    @Bean
    IntegrationFlow fileIntegrationFlow(FileReadingMessageSource fileAdapter, FileTransformer transformer,
                                        FileWritingMessageHandler outputFileHandler) {
        return IntegrationFlows.from(fileAdapter, config -> config.poller(Pollers.fixedDelay(100)))
                .transform(transformer)
                .handle(outputFileHandler)
                .get();
    }

    @Bean
    FileReadingMessageSource fileAdapter() {
        FileReadingMessageSource fileSource = new FileReadingMessageSource();
        fileSource.setDirectory(new File("data/input"));
        return fileSource;
    }

    @Bean
    FileTransformer transformer() {
        return new FileTransformer();
    }

    @Bean
    FileWritingMessageHandler outputFileAdapter() throws Exception {
        File directory = new File("data/output/");
        boolean result = directory.createNewFile();

        FileWritingMessageHandler handler = new FileWritingMessageHandler(directory);
        handler.setFileNameGenerator(file->"output.txt");
        handler.setAppendNewLine(true);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setExpectReply(false);

        return handler;
    }
}
