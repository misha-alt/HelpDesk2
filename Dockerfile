# Stage 1: Сборка WAR-файла из исходников
FROM maven:3.8.6-jdk-8 AS builder

# Рабочая директория
WORKDIR /app

# Копируем только pom.xml и загружаем зависимости
COPY pom.xml .
RUN mvn dependency:go-offline

# Копируем исходники
COPY src ./src

# Собираем WAR
RUN mvn clean package

# Stage 2: Запуск приложения в Tomcat
FROM tomcat:9.0-jdk8-slim

# Очищаем стандартные приложения
RUN rm -rf /usr/local/tomcat/webapps/*

# Копируем WAR из предыдущего этапа
COPY --from=builder /app/target/helpDesk.war /usr/local/tomcat/webapps/app.war

# Открываем порт
EXPOSE 8080

# Команда запуска
CMD ["catalina.sh", "run"]