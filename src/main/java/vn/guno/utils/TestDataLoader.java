package vn.guno.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class TestDataLoader {
    public static String loadTextFromResources(String fileName) {
        try (InputStream inputStream = TestDataLoader.class
                .getClassLoader()
                .getResourceAsStream(fileName)) {

            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + fileName);
            }

            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load file: " + fileName, e);
        }
    }

    public static String loadTextFromTestCases(String fileName) {
        return loadTextFromResources("testcases/" + fileName);
    }

}
