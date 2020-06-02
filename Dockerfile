FROM openjdk:8 as build-stage
WORKDIR /app
COPY . .
RUN apt-get update && apt-get -y install maven && mvn clean package

# Install JRE only because of space usage and security reasons
FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=build-stage /app/target/ /app/target
EXPOSE 8080
ARG DB_URL
ARG BUILD_ENV=production
ENV DATABASE_URL=$DB_URL
ENV BUILD_PROFILE=$BUILD_ENV
RUN adduser -D app && \
    chown -hR app /app
USER app
# Memory and port settings are needed for Heroku
CMD java -Xmx512M -XX:+CMSClassUnloadingEnabled -Dserver.port=$PORT -Dspring.profiles.active=$BUILD_PROFILE -jar ./target/ApplyJob-1.0-SNAPSHOT.jar

