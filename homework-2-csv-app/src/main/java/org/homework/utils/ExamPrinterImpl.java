package org.homework.utils;

import lombok.SneakyThrows;
import org.homework.model.Answer;
import org.homework.model.Exam;
import org.homework.model.Line;
import org.homework.utils.printer.ExamPrinter;
import org.homework.utils.printer.IncorrectBorderValueException;
import org.homework.utils.printer.NotFinishedExamException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Optional;

public class ExamPrinterImpl implements ExamPrinter {

    private final Exam exam;
    private final int passBorder;

    private final BufferedReader in;

    private final PrintStream out;
    private int correctAnswerCounter;
    private boolean examResult;
    private boolean isExamFinished;

    public ExamPrinterImpl(Exam exam, BufferedReader in, PrintStream out) {
        this.exam = exam;
        this.in = in;
        this.out = out;
        final int passBorder = exam.getPassBorder();
        checkBorderValue(passBorder);
        this.passBorder = passBorder;
    }

    @SneakyThrows
    @Override
    /**
     * Prints out data from Exam object
     * @param exam Exam object for print
     */
    public void print() {
        out.println("Welcome to java basic exam!".toUpperCase());
        out.println("Please enter firstName:");
        String firstName = readLine();
        out.println("Please enter lastName:");
        String lastName = readLine();
        out.println("Hello, " + firstName + " " + lastName);
        exam.getLines().forEach(this::accept);
        final int userResult = getPercentageOfCompletion();
        isExamFinished = true;
        setExamResult(userResult);
        printExamResult(firstName, lastName, userResult);
    }

    private String readLine() throws IOException {
        String line = null;
        StringBuilder rslt = new StringBuilder();
        if ((line = in.readLine()) != null) {
            rslt.append(line);
        }
        return rslt.toString();
    }

    private void printExamResult(String firstName, String lastName, int userResult) {
        out.println(firstName.toUpperCase() + " " + lastName.toUpperCase() +
                ", YOUR EXAM RESULT : " + userResult + "%");
        out.println("Minimum pass border: ".toUpperCase() + passBorder + "%");
        if (getExamResult()) {
            out.println("Congratulation! You pass the exam".toUpperCase());
        } else {
            out.println("You failed the exam. Try again later...".toUpperCase());
        }
    }

    @Override
    public boolean getExamResult() {
        if (!isExamFinished) {
            throw new NotFinishedExamException("Not possible to show result of exam. Exam is not finished");
        } else {
            return examResult;
        }
    }

    private void setExamResult(int userResult) {
        examResult = userResult >= passBorder;
    }

    private int getPercentageOfCompletion() {
        return correctAnswerCounter * 100 / exam.getLines().size();
    }

    @SneakyThrows
    private void proceedUserAnswerLetter(Character correctAnswerLetter) {
        out.println("Enter the correct answer letter A B C or D ...");
        String userAnswer = readLine();
        if (userAnswer.length() > 0) {
            if (correctAnswerLetter.equals(userAnswer.toUpperCase().charAt(0))) {
                out.println("You answer is CORRECT");
                correctAnswerCounter++;
            } else {
                out.println("Your answer is WRONG");
            }
        } else {
            out.println("Empty answer...");
        }
    }

    private void accept(Line l) {
        out.println(l.getQuestion());
        final List<Answer> answers = l.getAnswers();
        answers.forEach(answer -> {
            out.println(answer.getText());
        });
        final Optional<Answer> first = answers.stream().filter(Answer::isCorrectAnswer).findFirst();
        if (first.isEmpty()) {
            throw new RuntimeException("There is no correct answers in csv file. " +
                    "Correct answer should be mark with '+' at the end");
        }
        proceedUserAnswerLetter(first.get().getAnswerOptionLetter());
    }

    private void checkBorderValue(Integer passBorder) {
        if (!((passBorder >= 0) && (passBorder <= 100))) {
            throw new IncorrectBorderValueException("Incorrect border value. Value should be: 0 >= value <= 100");
        }
    }
}
