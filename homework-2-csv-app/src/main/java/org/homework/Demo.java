package org.homework;

import org.homework.utils.ExamPrinter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class Demo {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Demo.class);
        //final CsvReader csvReader = context.getBean(CsvReader.class);
        final ExamPrinter examPrinter = context.getBean(ExamPrinter.class);
        examPrinter.print();
    }
}
