package org.homework.utils;

import lombok.SneakyThrows;
import org.homework.config.YamlProps;
import org.homework.model.Answer;
import org.homework.model.Exam;
import org.homework.model.Line;
import org.homework.utils.printer.ExamPrinter;
import org.homework.utils.printer.IncorrectBorderValueException;
import org.homework.utils.printer.NotFinishedExamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Optional;

@Component
public class ExamPrinterImpl implements ExamPrinter {

    private final Exam exam;
    private final int passBorder;

    private final BufferedReader in;

    private final PrintStream out;
    @Autowired
    private final MessageSource messageSource;
    @Autowired
    private final YamlProps props;
    private int correctAnswerCounter;
    private boolean examResult;
    private boolean isExamFinished;

    public ExamPrinterImpl(Exam exam, BufferedReader in, PrintStream out, MessageSource messageSource, YamlProps props) {
        this.exam = exam;
        this.in = in;
        this.out = out;
        this.messageSource = messageSource;
        this.props = props;
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
        out.println(getMessageFromProps("msg.start.welcome"));
        out.println(getMessageFromProps("msg.start.firstName") + ":");
        String firstName = readLine();
        out.println(getMessageFromProps("msg.start.lastName") + ":");
        String lastName = readLine();
        out.println(getMessageFromProps("msg.start.greeting") + ", " + firstName + " " + lastName);
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
        out.println(firstName.toUpperCase() + " " + lastName.toUpperCase() + ", " + getMessageFromProps("msg.finish.result") + " : " + userResult + "%");
        out.println(getMessageFromProps("msg.finish.result.border") + ": ".toUpperCase() + passBorder + "%");
        if (getExamResult()) {
            out.println(getMessageFromProps("msg.finish.result.passed"));
        } else {
            out.println(getMessageFromProps("msg.finish.result.failed") + "...");
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
            out.print(getMessageFromProps("msg.inprogress.answer.text") + " ");
            if (correctAnswerLetter.equals(userAnswer.toUpperCase().charAt(0))) {
                out.print(getMessageFromProps("msg.inprogress.answer.positiveresult"));
                correctAnswerCounter++;
            } else {
                out.print(getMessageFromProps("msg.inprogress.answer.negativeresult"));
            }
            out.println();
        } else {
            out.println(getMessageFromProps("msg.inprogress.answer.empty") + "...");
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

    private String getMessageFromProps(String s) {
        return messageSource.getMessage(s, null, props.getLocale()).toUpperCase();
    }

}
