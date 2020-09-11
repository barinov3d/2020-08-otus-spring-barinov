package org.homework.utils.reader;

public class NoCorrectAnswerException extends CsvReaderException{
        protected NoCorrectAnswerException(String message) {
            super(message);
        }
}
