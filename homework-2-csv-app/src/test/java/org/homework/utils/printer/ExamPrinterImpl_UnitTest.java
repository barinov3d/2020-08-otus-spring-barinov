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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ExamPrinterImpl_UnitTest {

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final PrintStream writer = System.out;
    @Mock
    private Exam exam;

    private ExamPrinter examPrinterImpl;

    @SneakyThrows
    @Test
    @Order(1)
    void should_always_pass() {
        when(exam.getPassBorder()).thenReturn(0);
        examPrinterImpl = new ExamPrinterImpl(exam, reader, writer);
        assertTrue(examPrinterImpl.getResult(100));
    }

    @Test
    @Order(2)
    void should_always_false() {
        when(exam.getPassBorder()).thenReturn(100);
        examPrinterImpl = new ExamPrinterImpl(exam, reader, writer);
        assertFalse(examPrinterImpl.getResult(99));
    }

    @Test
    @Order(3)
    void should_pass() {
        when(exam.getPassBorder()).thenReturn(60);
        examPrinterImpl = new ExamPrinterImpl(exam, reader, writer);
        assertTrue(examPrinterImpl.getResult(60));
    }

    @Test
    @Order(4)
    void should_failed() {
        when(exam.getPassBorder()).thenReturn(60);
        examPrinterImpl = new ExamPrinterImpl(exam, reader, writer);
        assertFalse(examPrinterImpl.getResult(59));
    }

    @Test
    @Order(5)
    void test() throws IOException {
        BufferedReader bufferedReader = org.mockito.Mockito.mock(BufferedReader.class);
        when(exam.getPassBorder()).thenReturn(100);
        when(bufferedReader.readLine())
                .thenReturn("First name")
                .thenReturn("Last name")
                .thenReturn("D")
                .thenReturn("B")
                .thenReturn("A");
        when(exam.getLines())
                .thenReturn(Arrays.asList(
                        new Line("q1", Arrays.asList(new Answer('D', "", true))),
                        new Line("q2", Arrays.asList(new Answer('B', "", true))),
                        new Line("q3", Arrays.asList(new Answer('A', "", true)))
                ));
        examPrinterImpl = new ExamPrinterImpl(exam, bufferedReader, writer);
        examPrinterImpl.print();
        assertTrue(examPrinterImpl.getResult(100));
    }
}