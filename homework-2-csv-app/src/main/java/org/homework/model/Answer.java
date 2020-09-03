package org.homework.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Answer {
    private final Character answerOptionLetter;
    private final String text;
    private final boolean isCorrectAnswer;
}
