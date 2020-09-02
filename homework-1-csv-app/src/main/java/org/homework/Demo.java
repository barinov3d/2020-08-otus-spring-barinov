package org.homework;

import org.homework.utils.CsvReader;
import org.homework.utils.ExamPrinter;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Demo {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        final CsvReader csvReader = context.getBean(CsvReader.class);
        final ExamPrinter examPrinter = context.getBean(ExamPrinter.class);
        examPrinter.print(csvReader.getAsExam());
    }
}
