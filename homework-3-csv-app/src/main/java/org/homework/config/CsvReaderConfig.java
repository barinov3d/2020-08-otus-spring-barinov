package org.homework.config;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.homework.model.Exam;
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
    private final Integer passBorder;

    public CsvReaderConfig(@Value("${application.fileName}_${application.locale}.csv") String fileName, @Value("${application.passBorder}") Integer passBorder) throws FileNotFoundException {
        file = ResourceUtils.getFile("classpath:" + fileName);
        checkBorderValue(passBorder);
        this.passBorder = passBorder;
    }

    public CsvReader csvReader() throws FileNotFoundException {
        FileReader fileReader = new FileReader(file);
        CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build();
        return new CsvReaderImpl(csvReader, fileReader);
    }

    @Bean
    public Exam exam() throws Exception {
        return csvReader().getAsExam(passBorder);
    }

    private void checkBorderValue(Integer passBorder) {
        if (!((passBorder >= 0) && (passBorder <= 100))) {
            throw new RuntimeException("Incorrect border value. Value should be: 0 >= value <= 100");
        }
    }
}
