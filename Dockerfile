#FROM maven:3.8.3-openjdk-17
#
#WORKDIR /app
#COPY . .
#RUN mvn clean install
#EXPOSE 8080
#CMD mvn spring-boot:run
#FROM openjdk:17-oracle as builder
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} /app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]


#FROM maven:3.8.3-openjdk-17 as build
#WORKDIR /app
#COPY pom.xml .
#RUN --mount=type=cache,target=/root/.m2/repository \
#    mvn dependency:go-offline
##RUN mvn dependency:go-offline
#COPY src src
#RUN mvn package -DskipTests
#
#FROM eclipse-temurin:17-jre-alpine
#COPY --from=build /app/target/*.jar ./app.jar
#ENTRYPOINT ["java","-jar","./app.jar"]


#FROM maven:3.8.3-openjdk-17 as build
#WORKDIR /app
#COPY pom.xml .
#RUN --mount=type=cache,target=/root/.m2/repository \
#      mvn dependency:go-offline
#COPY src src
#RUN mvn package -DskipTests
#
#FROM eclipse-temurin:17-jre-alpine AS builder
#COPY  --from=build /app/target/*.jar ./app.jar
#RUN java -Djarmode=layertools -jar ./app.jar extract
#
#FROM eclipse-temurin:17-jre-alpine
#COPY --from=builder /dependencies/ ./
#COPY --from=builder /spring-boot-loader/ ./
#COPY --from=builder /snapshot-dependencies/ ./
#COPY --from=builder /application/ ./
#ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]


FROM gradle:7.2.0-jdk17 as build
COPY --chown=gradle:gradle api /app
WORKDIR /app
RUN gradle build --no-daemon

FROM eclipse-temurin:17-jre-alpine AS builder
COPY  --from=build /app/target/*.jar ./app.jar
RUN java -Djarmode=layertools -jar ./app.jar extract

FROM eclipse-temurin:17-jre-alpine
COPY --from=builder /dependencies/ ./
COPY --from=builder /spring-boot-loader/ ./
COPY --from=builder /snapshot-dependencies/ ./
COPY --from=builder /application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]

#FROM --platform=$BUILDPLATFORM maven:3.8.3-openjdk-17 as build
#COPY pom.xml .
#RUN --mount=type=cache,target=/root/.m2/repository \
#    mvn dependency:go-offline
#COPY src src
#RUN mvn package -DskipTests
#
#FROM --platform=$TARGETPLATFORM eclipse-temurin:17-jre-jammy AS builder
#COPY  --from=build /target/*.jar app.jar
#RUN java -Djarmode=layertools -jar app.jar extract
#
#
#FROM --platform=$TARGETPLATFORM eclipse-temurin:17-jre-jammy
#COPY --from=builder /dependencies/ ./
#COPY --from=builder /spring-boot-loader/ ./
#COPY --from=builder /snapshot-dependencies/ ./
#COPY --from=builder /application/ ./
#ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]



#
#FROM liquibase/liquibase
#
#RUN lpm add mysql --global
