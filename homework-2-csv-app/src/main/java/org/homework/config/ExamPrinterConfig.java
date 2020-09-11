package org.homework.config;

import org.homework.utils.reader.CsvReader;
import org.homework.utils.printer.ExamPrinter;
import org.homework.utils.printer.ExamPrinterImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExamPrinterConfig {

    private final CsvReader csvReader;
    private final Integer passBorder;

    public ExamPrinterConfig(CsvReader csvReader, @Value("${passBorder}") Integer passBorder) {
        this.csvReader = csvReader;
        this.passBorder = passBorder;
    }

    @Bean
    public ExamPrinter examPrinter() throws Exception {
        return new ExamPrinterImpl(csvReader.getAsExam(passBorder));
    }
}
