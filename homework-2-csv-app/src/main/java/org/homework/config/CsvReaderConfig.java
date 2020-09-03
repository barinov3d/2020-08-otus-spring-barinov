package org.homework.config;

import org.homework.utils.CsvReader;
import org.homework.utils.CsvReaderImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@Configuration
public class CsvReaderConfig {
    private final File file;

    public CsvReaderConfig(@Value("${fileName}") String fileName) throws FileNotFoundException {
        file = ResourceUtils.getFile("classpath:" + fileName);
    }

    @Bean
    public CsvReader csvReader() throws FileNotFoundException {
        return new CsvReaderImpl(file);
    }
}
