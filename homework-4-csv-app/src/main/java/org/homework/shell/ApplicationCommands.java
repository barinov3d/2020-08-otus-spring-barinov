package org.homework.shell;

import lombok.RequiredArgsConstructor;
import org.homework.config.YamlProps;
import org.homework.utils.printer.ExamPrinter;
import org.springframework.context.MessageSource;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommands {
    private final MessageSource messageSource;
    private final YamlProps props = new YamlProps();
    private final ExamPrinter examPrinter;
    private String userName;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption(defaultValue = "AnyUser") String userName) {
        this.userName = userName;
        return String.format("Добро пожаловать: %s", userName);
    }

    @ShellMethod(value = "Start exam command", key = {"s", "str", "start"})
    @ShellMethodAvailability(value = "isCommandAvailable")
    public void runExam() {
        examPrinter.print();
    }

    @ShellMethod(value = "Start exam command", key = {"r", "result"})
    public String getResult() {
        String result;
        if (examPrinter.getExamResult()) {
            result = getMessageFromProps("msg.finish.result.passed");
        } else {
            result = getMessageFromProps("msg.finish.result.failed") + "...";
        }
        return result;
    }

    private Availability isCommandAvailable() {
        return userName == null ? Availability.unavailable("Please, log in first") : Availability.available();
    }

    private String getMessageFromProps(String s) {
        return messageSource.getMessage(s, null, props.getLocale()).toUpperCase();
    }

}
