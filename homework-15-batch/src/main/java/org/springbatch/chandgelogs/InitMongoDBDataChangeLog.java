package org.springbatch.chandgelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import org.springbatch.model.Person;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    @ChangeSet(order = "000", id = "dropDB", author = "dmitry", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initPersons", author = "dmitry", runAlways = true)
    public void initPersons(MongockTemplate template) {
        template.save(new Person("John", 21));
        template.save(new Person("Igor", 32));
        template.save(new Person("Dmitry", 52));
        template.save(new Person("Michael", 22));
        template.save(new Person("German", 33));
        template.save(new Person("John", 21));
        template.save(new Person("Igor", 32));
        template.save(new Person("Dmitry", 52));
        template.save(new Person("Michael", 22));
        template.save(new Person("German", 33));
        template.save(new Person("John", 21));
        template.save(new Person("Igor", 32));
        template.save(new Person("Dmitry", 52));
        template.save(new Person("Michael", 22));
        template.save(new Person("German", 33));
        template.save(new Person("John", 21));
        template.save(new Person("Igor", 32));
        template.save(new Person("Dmitry", 52));
        template.save(new Person("Michael", 22));
        template.save(new Person("German", 33));
        template.save(new Person("John", 21));
        template.save(new Person("Igor", 32));
        template.save(new Person("Dmitry", 52));
        template.save(new Person("Michael", 22));
        template.save(new Person("German", 33));
    }
}
