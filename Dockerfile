## Stage 1: Build the application with Java 17
#FROM openjdk:17 as builder
#WORKDIR /application
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} application.jar
#RUN java -Djarmode=layertools -jar application.jar extract
#
#RUN \
#    chown root:root -R /application
## Stage 2: Create a minimal JRE image with Java 17
#FROM openjdk:17
#WORKDIR /application
#
#COPY --from=builder /application/dependencies/ ./dependencies/
#COPY --from=builder /application/spring-boot-loader/ ./spring-boot-loader/
#COPY --from=builder /application/snapshot-dependencies/ ./snapshot-dependencies/
##COPY --from=builder /application/internal-dependencies/ ./internal-dependencies/
#COPY --from=builder /application/application/ ./
#
#ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
#


FROM azul/zulu-openjdk:17-latest
COPY ./target/coreapi-auth-0.0.1-SNAPSHOT.jar /
EXPOSE 8080

ENTRYPOINT java -jar coreapi-auth-0.0.1-SNAPSHOT.jar