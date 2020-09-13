package org.homework.utils.reader;

import com.opencsv.CSVReader;
import org.homework.model.Answer;
import org.homework.model.Exam;
import org.homework.model.Line;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class CsvReaderImpl_UnitTest {

    private final String question = "q?";
    private final String answer1 = "A - ";
    private final String answer2 = "B - ";
    private final String answer3 = "C - ";
    private final String answer4 = "D - +";
    private final String[] csvLine = {
            question, answer1, answer2, answer3, answer4
    };
    private final int defaultPassBorder = 80;
    @Mock
    private CSVReader csvReader;
    @Mock
    private FileReader fileReader;

    private CsvReader csvReaderImpl;

    @BeforeEach
    void beforeEach() {
        csvReaderImpl = new CsvReaderImpl(csvReader, fileReader);
    }

    @Test
    @Order(1)
    void should_apply_border_value() throws Exception {
        when(csvReader.readAll()).thenReturn(new ArrayList<>());
        assertEquals(csvReaderImpl.getAsExam(defaultPassBorder).getPassBorder(), defaultPassBorder);
    }

    @Test
    @Order(2)
    void check_exam_size() throws Exception {
        when(csvReader.readAll()).thenReturn(Arrays.asList(csvLine, csvLine));
        final Exam exam = csvReaderImpl.getAsExam(defaultPassBorder);
        assertEquals(exam.getLines().size(), 2);
    }

    @Test
    @Order(3)
    void check_that_lines_from_exam_has_correct_values() throws Exception {
        when(csvReader.readAll()).thenReturn(Arrays.asList(csvLine, csvLine));
        Line line = new Line(question, Arrays.asList(
                new Answer(answer1.charAt(0), answer1, false),
                new Answer(answer2.charAt(0), answer2, false),
                new Answer(answer3.charAt(0), answer3, false),
                new Answer(answer4.charAt(0), answer4, true)
        ));
        final Exam exam = csvReaderImpl.getAsExam(defaultPassBorder);
        assertEquals(exam.getLines(), Arrays.asList(line, line));
    }

    @Test
    @Order(4)
    void check_correct_csv_text_format_empty_single_string() throws Exception {
        when(csvReader.readAll()).thenReturn(Collections.singletonList(new String[]{""}));
        assertThrows(NoAnwersException.class, () -> csvReaderImpl.getAsExam(defaultPassBorder));
    }

    @Test
    @Order(5)
    void check_correct_csv_text_format_no_answers() throws Exception {
        when(csvReader.readAll()).thenReturn(Collections.singletonList(new String[]{"Winter"}));
        assertThrows(NoAnwersException.class, () -> csvReaderImpl.getAsExam(defaultPassBorder));
    }

    @Test
    @Order(6)
    void check_correct_csv_text_format_incorrect_answers() throws Exception {
        when(csvReader.readAll()).thenReturn(Collections.singletonList(new String[]{"Winter", "Spring+", "Summer", "Autumn"}));
        assertThrows(IncorrectAswerFormatException.class, () -> csvReaderImpl.getAsExam(defaultPassBorder));
    }

    @Test
    @Order(7)
    void check_csv_text_has_correct_answer() throws Exception {
        when(csvReader.readAll()).thenReturn(Collections.singletonList(
                new String[]{question, answer1, answer2, answer3, answer4.replace("+", "")}));
        assertThrows(NoCorrectAnswerException.class, () -> csvReaderImpl.getAsExam(defaultPassBorder));
    }
}