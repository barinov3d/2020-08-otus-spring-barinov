package org.homework.utils;

import org.homework.model.Exam;

public interface CsvReader {
    Exam getAsExam() throws Exception;
}
