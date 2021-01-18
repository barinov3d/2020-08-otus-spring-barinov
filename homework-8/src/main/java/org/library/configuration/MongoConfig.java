package org.library.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {
    @Value("${spring.data.mongodb.database}")
    private String dataBaseName;
    @Override
    protected String getDatabaseName() {
        return dataBaseName;
    }
    @Override
    protected boolean autoIndexCreation() {
        return true;
    }
}