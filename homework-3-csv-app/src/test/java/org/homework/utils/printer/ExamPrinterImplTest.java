package org.homework.utils.printer;

import lombok.SneakyThrows;
import org.homework.config.YamlProps;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ExamPrinterImplTest {

    @Autowired
    PrintStream writer;
    @Autowired
    YamlProps props;
    @Autowired
    MessageSource messageSource;
    @Mock
    BufferedReader bufferedReader;
    private ExamPrinter examPrinterImpl;
    @Mock
    private Exam exam;


    @SneakyThrows
    @Test
    @Order(1)
    void should_pass_zero_border() {
        fullExam("Z", "Z", "Z", 0);
        assertTrue(examPrinterImpl.getExamResult());
    }

    @Test
    @Order(2)
    void should_failed_less_than_max_possible_pass_border() throws IOException {
        fullExam("D", "B", "Z", 100);
        assertFalse(examPrinterImpl.getExamResult());
    }

    @Test
    @Order(3)
    void should_pass_equals_pass_border() throws IOException {
        fullExam("D", "B", "Z", 66);
        assertTrue(examPrinterImpl.getExamResult());
    }

    @Test
    @Order(4)
    void should_failed_less_than_pass_border() throws IOException {
        fullExam("D", "Z", "Z", 66);
        assertFalse(examPrinterImpl.getExamResult());
    }

    @Test
    @Order(5)
    void incorrect_pass_border_negative_value() {
        when(exam.getPassBorder()).thenReturn(-50);
        assertThrows(IncorrectBorderValueException.class, () -> new ExamPrinterImpl(exam, bufferedReader, writer, messageSource));
    }

    @Test
    @Order(6)
    void incorrect_pass_border_more_than_one_hundred() {
        when(exam.getPassBorder()).thenReturn(101);
        assertThrows(IncorrectBorderValueException.class, () -> examPrinterImpl = new ExamPrinterImpl(exam, bufferedReader, writer, messageSource));
    }

    @Test
    @Order(7)
    void try_get_result_before_exam_finished() {
        examPrinterImpl = new ExamPrinterImpl(exam, bufferedReader, writer, messageSource);
        assertThrows(NotFinishedExamException.class, () -> examPrinterImpl.getExamResult());
    }

    @Test
    @Order(8)
    void happy_path() throws IOException {
        fullExam("D", "B", "A", 100);
        assertTrue(examPrinterImpl.getExamResult());
    }

    @Test
    @Order(9)
    void happy_path_negative() throws IOException {
        fullExam("D", "B", "Z", 100);
        assertFalse(examPrinterImpl.getExamResult());
    }

    void fullExam(String firstAnswerFromUser, String secondAnswerFromUser, String thirdAnswerFromUser, int passBorder) throws IOException {
        when(exam.getPassBorder()).thenReturn(passBorder);
        when(bufferedReader.readLine())
                .thenReturn("First name")
                .thenReturn("Last name")
                .thenReturn(firstAnswerFromUser)
                .thenReturn(secondAnswerFromUser)
                .thenReturn(thirdAnswerFromUser);
        char firstCorrectLetter = 'D';
        char secondCorrectLetter = 'B';
        char thirdCorrectLetter = 'A';
        final List<Line> lines = Arrays.asList(
                new Line("q1", Collections.singletonList(new Answer(firstCorrectLetter, "", true))),
                new Line("q2", Collections.singletonList(new Answer(secondCorrectLetter, "", true))),
                new Line("q3", Collections.singletonList(new Answer(thirdCorrectLetter, "", true)))
        );
        when(exam.getLines()).thenReturn(lines);
        examPrinterImpl = new ExamPrinterImpl(exam, bufferedReader, writer, messageSource);
        examPrinterImpl.print();
    }

}