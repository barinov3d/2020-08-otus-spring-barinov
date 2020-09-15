package org.homework.utils.reader;

import org.homework.model.Exam;

public interface CsvReader {
    Exam getAsExam(int passBorder) throws Exception;
}
