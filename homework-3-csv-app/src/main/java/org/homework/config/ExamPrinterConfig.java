package org.homework.config;

import org.homework.model.Exam;
import org.homework.utils.ExamPrinterImpl;
import org.homework.utils.printer.ExamPrinter;
import org.homework.utils.reader.CsvReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

@Configuration
public class ExamPrinterConfig {

    private final CsvReader csvReader;
    private final Integer passBorder;

    public ExamPrinterConfig(CsvReader csvReader, @Value("${application.passBorder}") Integer passBorder) {
        checkBorderValue(passBorder);
        this.csvReader = csvReader;
        this.passBorder = passBorder;
    }

    @Bean
    public ExamPrinter examPrinter() throws Exception {
        return new ExamPrinterImpl(csvReader.getAsExam(passBorder), bufferedReader(), out(), messageSource());
    }

    @Bean
    public PrintStream out() {
        return System.out;
    }

    @Bean
    public BufferedReader bufferedReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    @Bean
    public MessageSource messageSource() {
        var ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("classpath:/i18n/bundle");
        ms.setDefaultEncoding("WINDOWS-1251");
        return ms;
    }

    private void checkBorderValue(Integer passBorder) {
        if (!((passBorder >= 0) && (passBorder <= 100))) {
            throw new RuntimeException("Incorrect border value. Value should be: 0 >= value <= 100");
        }
    }

}
