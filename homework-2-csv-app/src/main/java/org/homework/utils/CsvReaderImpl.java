package org.homework.utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.homework.model.Exam;
import org.homework.model.Line;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CsvReaderImpl implements CsvReader {
    private final CSVReader csvReader;
    private final FileReader fileReader;

    public CsvReaderImpl(File file) throws FileNotFoundException {
        fileReader = new FileReader(file);
        csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build();
    }

    @Override
    /**
     * Converts csv file data to Exam object
     */
    public Exam getAsExam() throws Exception {
        final List<List<String>> lists = readAll(fileReader);
        List<Line> lines = new ArrayList<>();
        lists.forEach(list -> {
                    final List<String> tempList = list.subList(1, list.size());
                    lines.add(new Line(list.get(0), tempList));
                }
        );
        return new Exam(lines);
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

}
