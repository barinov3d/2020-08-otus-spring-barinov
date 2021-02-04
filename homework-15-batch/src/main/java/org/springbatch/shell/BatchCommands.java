package org.springbatch.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {

    private final Job importUserJob;

    private final JobLauncher jobLauncher;


    @ShellMethod(value = "startMigrationJobWithJobLauncher", key = "start")
    public void startMigrationJobWithJobLauncher() throws Exception {
        JobExecution execution = jobLauncher.run(importUserJob, new JobParameters());
    }
}
