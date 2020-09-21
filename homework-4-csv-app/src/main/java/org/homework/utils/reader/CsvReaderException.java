package org.homework.utils.reader;

import java.util.Objects;

public class CsvReaderException extends RuntimeException {
    protected CsvReaderException(final String message) {
        super(Objects.requireNonNull(message), null, false, false);
    }
}
