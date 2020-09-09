package org.homework.config;

import org.homework.utils.CsvReader;
import org.homework.utils.ExamPrinter;
import org.homework.utils.ExamPrinterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExamPrinterConfig {

    @Autowired
    private final CsvReader csvReader;
    private final Integer passBorder;

    public ExamPrinterConfig(CsvReader csvReader, @Value("${passBorder}") Integer passBorder) {
        this.csvReader = csvReader;
        this.passBorder = passBorder;
    }

    @Bean
    public ExamPrinter examPrinter() throws Exception {
        return new ExamPrinterImpl(csvReader.getAsExam(), passBorder);
    }
}
