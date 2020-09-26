package org.homework.config;

import org.homework.model.Exam;
import org.homework.utils.ExamPrinterImpl;
import org.homework.utils.printer.ExamPrinter;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Configuration
public class ExamPrinterConfig {

    private final Exam exam;

    public ExamPrinterConfig(Exam exam) {
        this.exam = exam;
    }

    @Bean
    public ExamPrinter examPrinter(TextReader textReader, TextPrinter textPrinter, MessageSource messageSource) {
        return new ExamPrinterImpl(exam, textReader, textPrinter, messageSource);
    }

    @Bean
    public TextPrinter out() {
        return new TextPrinter(System.out);
    }

    @Bean
    public TextReader in() {
        return new TextReader(new BufferedReader(new InputStreamReader(System.in)));
    }

    @Bean
    public MessageSource messageSource() {
        var ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("classpath:/i18n/bundle");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

}
