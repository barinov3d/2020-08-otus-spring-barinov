package org.homework.utils.printer;

import java.util.Objects;

public class ExamPrinterException extends RuntimeException {
    protected ExamPrinterException(final String message) {
        super(Objects.requireNonNull(message), null, false, false);
    }
}
