package org.homework.utils;

import org.homework.model.Answer;
import org.homework.model.Exam;
import org.homework.model.Line;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ExamPrinterImpl implements ExamPrinter {

    private final Exam exam;
    private final int passBorder;
    private int correctAnswerCounter;

    public ExamPrinterImpl(Exam exam, int passBorder) {
        this.exam = exam;
        this.passBorder = passBorder;
    }

    @Override
    /**
     * Prints out data from Exam object
     * @param exam Exam object for print
     */
    public void print() {
        System.out.println("Welcome to java basic exam!".toUpperCase());
        System.out.println("Please enter firstName:");
        Scanner in = new Scanner(System.in);
        String firstName = in.nextLine();
        System.out.println("Please enter lastName:");
        String lastName = in.nextLine();
        System.out.println("Hello, " + firstName + " " + lastName);
        exam.getLines().forEach(this::accept);
        final int userResult = correctAnswerCounter * 100 / exam.getLines().size();
        System.out.println(firstName.toUpperCase() + " " + lastName.toUpperCase() +
                ", YOUR EXAM RESULT : " + userResult + "%");
        if (userResult >= passBorder) {
            System.out.println("Congratilation! You pass the exam".toUpperCase());
        } else {
            System.out.println("You failed the exam. Try again later...".toUpperCase());
        }
    }

    private void proceedUserAnswerLetter(Character correctAnswerLetter) {
        System.out.println("Enter the correct answer letter (A/B/C/D)...");
        Scanner in = new Scanner(System.in);
        String userAnswer = in.nextLine();
        if (userAnswer.length() > 0) {
            if (correctAnswerLetter.equals(userAnswer.toUpperCase().charAt(0))) {
                System.out.println("You answer is CORRECT");
                correctAnswerCounter++;
            } else {
                System.out.println("Your answer is WRONG");
            }
        } else {
            System.out.println("Empty answer...");
        }
    }

    private void accept(Line l) {
        System.out.println(l.getQuestion());
        final List<Answer> answers = l.getAnswers();
        answers.forEach(answer -> {
            System.out.println(answer.getText());
        });
        final Optional<Answer> first = answers.stream().filter(Answer::isCorrectAnswer).findFirst();
        if (first.isEmpty()) {
            throw new RuntimeException("There is no correct answers in csv file. " +
                    "Correct answer should be mark with '+' at the end");
        }
        proceedUserAnswerLetter(first.get().getAnswerOptionLetter());
    }
}
