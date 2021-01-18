FROM maven:3.6-openjdk-11
ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
ADD ./pom.xml $PROJECT_DIR
#RUN mvn dependency:resolve
ADD ./src/ $PROJECT_DIR/src
RUN mvn -B clean install -DskipTests -Dcheckstyle.skip
#RUN mvn install

FROM openjdk:11-jre-slim
ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY --from=0 $PROJECT_DIR/target/homework-13-docker* $PROJECT_DIR/
EXPOSE 8080
CMD ["java", "-jar", "/opt/project/homework-13-docker-0.0.1-SNAPSHOT.jar"]

#docker build -t my-library:1.0 ./