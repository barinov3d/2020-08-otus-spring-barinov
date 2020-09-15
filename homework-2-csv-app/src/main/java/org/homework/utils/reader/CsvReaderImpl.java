package org.homework.utils.reader;

import com.opencsv.CSVReader;
import org.homework.model.Answer;
import org.homework.model.Exam;
import org.homework.model.Line;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CsvReaderImpl implements CsvReader {
    private final CSVReader csvReader;
    private final FileReader fileReader;


    public CsvReaderImpl(CSVReader csvReader, FileReader fileReader) {
        this.fileReader = fileReader;
        this.csvReader = csvReader;
    }

    @Override
    /**
     * Converts csv file data to Exam object
     */
    public Exam getAsExam(int passBorder) throws Exception {
        final List<List<String>> lists = readAll(fileReader);
        List<Line> lines = new ArrayList<>();
        lists.forEach(list -> {
                    final List<String> tempList = list.subList(1, list.size());
                    lines.add(new Line(list.get(0), getAsAnswer(tempList)));
                }
        );
        return new Exam(lines, passBorder);
    }


    /**
     * @param reader reader
     * @return csv file data as List<List<String>>
     * @throws Exception
     */
    private List<List<String>> readAll(Reader reader) throws Exception {
        List<String[]> list = csvReader.readAll();
        reader.close();
        csvReader.close();
        return list.stream().map(Arrays::asList).collect(Collectors.toList());
    }

    /**
     * Converts List<String> data to Answer object
     * Strings from list should be matches regex
     * See checkAnswerGlobalStructure() method
     */
    private List<Answer> getAsAnswer(List<String> answersLine) {
        final List<Answer> answerList = new ArrayList<>();
        if (answersLine.size() == 0) {
            throw new NoAnwersException("No answers found");
        }
        answersLine.stream().filter(l -> l.matches("^.*\\+$")).findFirst().orElseThrow(
                () -> new NoCorrectAnswerException("No correct answer found in line"));
        answersLine.forEach(line -> {
            if (!checkAnswerGlobalStructure(line)) {
                throw new IncorrectAswerFormatException("Text '" + line + "' doesn't matches regex");
            }
            if (checkAnswerCorrectAnswerStructure(line)) {
                answerList.add(new Answer(line.charAt(0), line, true));
            } else {
                answerList.add(new Answer(line.charAt(0), line, false));
            }
        });
        return answerList;
    }

    private boolean checkRegex(String text, String regex) {
        final Matcher matcher = Pattern.compile(regex).matcher(text);
        return matcher.matches();
    }

    private boolean checkAnswerGlobalStructure(String text) {
        return checkRegex(text, "^[A-Z] - .*$");
    }

    private boolean checkAnswerCorrectAnswerStructure(String text) {
        return checkRegex(text, "^[A-Z] - .*\\+$");
    }
}
