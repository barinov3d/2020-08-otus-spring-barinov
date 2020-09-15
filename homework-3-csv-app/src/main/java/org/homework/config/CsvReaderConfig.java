package org.homework.config;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.homework.utils.reader.CsvReader;
import org.homework.utils.reader.CsvReaderImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

@Configuration
public class CsvReaderConfig {
    private final File file;

    public CsvReaderConfig(@Value("${application.fileName}_${application.locale}.csv") String fileName) throws FileNotFoundException {
        file = ResourceUtils.getFile("classpath:" + fileName);
    }

    @Bean
    public CsvReader csvReader() throws FileNotFoundException {
        FileReader fileReader = new FileReader(file);
        CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build();
        return new CsvReaderImpl(csvReader, fileReader);
    }
}
