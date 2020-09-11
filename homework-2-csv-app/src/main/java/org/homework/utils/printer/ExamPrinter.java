package org.homework.utils.printer;

import java.io.InputStream;
import java.io.OutputStream;

public interface ExamPrinter {
    void print();
    void print(InputStream in, OutputStream out);
}
