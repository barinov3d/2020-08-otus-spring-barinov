package org.homework.utils.printer;

import lombok.SneakyThrows;
import org.homework.model.Answer;
import org.homework.model.Exam;
import org.homework.model.Line;
import org.homework.utils.ExamPrinterImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ExamPrinterImpl_UnitTest {

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final PrintStream writer = System.out;
    private final char firstCorrectLetter = 'D';
    private final char secondCorrectLetter = 'B';
    private final char thirdCorrectLetter = 'A';
    BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
    @Mock
    private Exam exam;
    private ExamPrinter examPrinterImpl;

    @SneakyThrows
    @Test
    @Order(1)
    void should_pass_zero_border() {
        examPrinterImpl = new ExamPrinterImpl(exam, reader, writer);
        fullExam("First name", "Last name", "Z", "Z", "Z", 0);
        assertTrue(examPrinterImpl.getExamResult());
    }

    @Test
    @Order(2)
    void should_failed_less_than__max_possible_pass_border() throws IOException {
        examPrinterImpl = new ExamPrinterImpl(exam, reader, writer);
        fullExam("First name", "Last name", "D", "B", "Z", 100);
        assertFalse(examPrinterImpl.getExamResult());
    }

    @Test
    @Order(3)
    void should_pass_equals_pass_border() throws IOException {
        examPrinterImpl = new ExamPrinterImpl(exam, reader, writer);
        fullExam("First name", "Last name", "D", "B", "Z", 66);
        assertTrue(examPrinterImpl.getExamResult());
    }

    @Test
    @Order(4)
    void should_failed_less_than_pass_border() throws IOException {
        examPrinterImpl = new ExamPrinterImpl(exam, reader, writer);
        fullExam("First name", "Last name", "D", "Z", "Z", 66);
        assertFalse(examPrinterImpl.getExamResult());
    }

    @Test
    @Order(5)
    void incorrect_pass_border_negative_value() {
        when(exam.getPassBorder()).thenReturn(-50);
        assertThrows(IncorrectBorderValueException.class, () -> new ExamPrinterImpl(exam, bufferedReader, writer));
    }

    @Test
    @Order(6)
    void incorrect_pass_border_more_than_one_hundred() {
        when(exam.getPassBorder()).thenReturn(101);
        assertThrows(IncorrectBorderValueException.class, () -> examPrinterImpl = new ExamPrinterImpl(exam, bufferedReader, writer));
    }

    @Test
    @Order(7)
    void try_get_result_before_exam_finished() {
        examPrinterImpl = new ExamPrinterImpl(exam, bufferedReader, writer);
        assertThrows(NotFinishedExamException.class, () -> examPrinterImpl.getExamResult());
    }

    @Test
    @Order(8)
    void happy_path() throws IOException {
        fullExam("First name", "Last name", "D", "B", "A", 100);
        assertTrue(examPrinterImpl.getExamResult());
    }

    @Test
    @Order(9)
    void happy_path_negative() throws IOException {
        fullExam("First name", "Last name", "D", "B", "Z", 100);
        assertFalse(examPrinterImpl.getExamResult());
    }

    void fullExam(String firstName, String lastName, String firstAnswerFromUser, String secondAnswerFromUser, String thirdAnswerFromUser, int passBorder) throws IOException {
        when(exam.getPassBorder()).thenReturn(passBorder);
        when(bufferedReader.readLine())
                .thenReturn(firstName)
                .thenReturn(lastName)
                .thenReturn(firstAnswerFromUser)
                .thenReturn(secondAnswerFromUser)
                .thenReturn(thirdAnswerFromUser);
        when(exam.getLines())
                .thenReturn(Arrays.asList(
                        new Line("q1", Collections.singletonList(new Answer(firstCorrectLetter, "", true))),
                        new Line("q2", Collections.singletonList(new Answer(secondCorrectLetter, "", true))),
                        new Line("q3", Collections.singletonList(new Answer(thirdCorrectLetter, "", true)))
                ));
        examPrinterImpl = new ExamPrinterImpl(exam, bufferedReader, writer);
        examPrinterImpl.print();
    }
}