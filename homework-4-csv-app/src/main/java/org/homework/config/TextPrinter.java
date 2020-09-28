package org.homework.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.PrintStream;

@AllArgsConstructor
@Getter
public class TextPrinter {

    private final PrintStream out;

}
