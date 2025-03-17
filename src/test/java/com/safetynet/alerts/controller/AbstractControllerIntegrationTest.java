package com.safetynet.alerts.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class AbstractControllerIntegrationTest {

    private final static String RESOURCE_NAME = "target/test-classes/data.json";
    private final static String CLEAN_RESOURCE_NAME = "src/test/resources/data_clean.json";

    public void cleanDatabase() throws IOException {
        Path originalPath = Paths.get(RESOURCE_NAME);
        Path copied = Paths.get(CLEAN_RESOURCE_NAME);

        Path result = Files.copy(copied, originalPath, StandardCopyOption.REPLACE_EXISTING);
        System.out.println(result);
    }

}
