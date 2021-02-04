package org.springbatch.config;

import lombok.RequiredArgsConstructor;
import org.springbatch.model.Person;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class JobConfig {
    public static final String MONGO_TO_MY_SQL = "mongoToMySql";
    private static final int CHUNK_SIZE = 5;

    private final JobBuilderFactory jobBuilderFactory;
    private final DataSource dataSource;
    private final StepBuilderFactory stepBuilderFactory;
    private final MongoTemplate mongoTemplate;

    @StepScope
    @Bean
    public MongoItemReader<Person> mongoItemReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<Person>()
                .name("mongoItemReader")
                .template(template)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .targetType(Person.class)
                .collection("persons")
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Person, Person> personItemProcessor() {
        return book -> book;
    }

    @StepScope
    @Bean
    public JdbcBatchItemWriter<Person> personJdbcBatchItemWriter() {
        JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO persons (name,age) VALUES (:name,:age)");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public Step personStep() {
        return stepBuilderFactory.get("personStep")
                .<Person, Person>chunk(CHUNK_SIZE)
                .reader(mongoItemReader(mongoTemplate))
                .processor(personItemProcessor())
                .writer(personJdbcBatchItemWriter())
                .build();
    }

    @Bean
    public Job mongoToMySql() {
        return jobBuilderFactory.get(MONGO_TO_MY_SQL)
                .start(personStep())
                .build();
    }
}
