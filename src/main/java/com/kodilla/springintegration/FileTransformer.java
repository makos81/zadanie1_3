package com.kodilla.springintegration;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileTransformer {
    public String transformFile(String fileName) throws IOException {
        Path p = Paths.get(fileName);
        return p.getFileName().toString();
    }
}
