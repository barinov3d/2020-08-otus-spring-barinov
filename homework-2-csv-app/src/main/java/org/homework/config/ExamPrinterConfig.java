package org.homework.config;

import org.homework.utils.CsvReader;
import org.homework.utils.ExamPrinter;
import org.homework.utils.ExamPrinterSimple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExamPrinterConfig {

    @Autowired
    private final CsvReader csvReader;

    public ExamPrinterConfig(CsvReader csvReader) {
        this.csvReader = csvReader;
    }

    @Bean
    public ExamPrinter examPrinter() throws Exception {
        return new ExamPrinterSimple(csvReader.getAsExam());
    }
}
