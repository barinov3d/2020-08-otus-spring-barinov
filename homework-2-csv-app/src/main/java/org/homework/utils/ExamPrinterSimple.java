package org.homework.utils;

import org.homework.model.Exam;

public class ExamPrinterSimple implements ExamPrinter {

    private final Exam exam;

    public ExamPrinterSimple(Exam exam) {
        this.exam = exam;
    }

    @Override
    /**
     * Prints out data from Exam object
     * @param exam Exam object for print
     */
    public void print() {
        exam.getLines().forEach(l -> {
            System.out.println(l.getQuestion());
            l.getAnswers().forEach(System.out::println);
            pressAnyKeyToContinue();
        });
    }

    private void pressAnyKeyToContinue() {
        System.out.println("Press Enter key to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
        }
    }
}
