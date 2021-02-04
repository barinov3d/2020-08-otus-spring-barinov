package org.webflux.changelogs;


import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.webflux.Constants;
import org.webflux.model.Course;
import org.webflux.model.Student;
import org.webflux.repositories.CourseRepository;
import org.webflux.repositories.StudentRepository;

import java.time.LocalDate;
import java.util.List;

@ChangeLog
public class DatabaseChangeLog {

    @Autowired
    MongockTemplate mongoTemplate;

    @ChangeSet(order = "001", id = "dropDb", author = "dmitry", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertData", author = "dmitry")
    public void insertData(MongockTemplate mongockTemplate, StudentRepository studentRepository, CourseRepository courseRepository) {
        Course firstCourse = new Course(Constants.COURSE_1, LocalDate.of(2020, 1, 1));
        firstCourse.setId("1");
        Course secondCourse = new Course(Constants.COURSE_2, LocalDate.of(2020, 1, 15));
        secondCourse.setId("2");
        Course thirdCourse = new Course(Constants.COURSE_3, LocalDate.of(2021, 3, 30));
        thirdCourse.setId("3");

        Student firstStudent = new Student(Constants.FIRST_NAME_1, Constants.LAST_NAME_1, LocalDate.of(1991, 8, 3));
        Student secondStudent = new Student(Constants.FIRST_NAME_2, Constants.LAST_NAME_2, LocalDate.of(1985, 3, 12));
        Student thirdStudent = new Student(Constants.FIRST_NAME_3, Constants.LAST_NAME_3, LocalDate.of(1996, 2, 8));

        firstStudent.setCourses(List.of(firstCourse, secondCourse));
        firstStudent.setId("1");
        secondStudent.setCourses(List.of(firstCourse));
        secondStudent.setId("2");
        thirdStudent.setCourses(List.of(thirdCourse));
        thirdStudent.setId("3");

        mongockTemplate.save(firstStudent);
        mongockTemplate.save(secondStudent);
        mongockTemplate.save(thirdStudent);

        mongockTemplate.save(firstCourse);
        mongockTemplate.save(secondCourse);
        mongockTemplate.save(thirdCourse);
    }
}
