FROM openjdk:21-slim
COPY target/app.jar app.jar

HEALTHCHECK --interval=1s --timeout=30s --retries=30 \
    CMD pg_isready -h gigabank-db -p 5432

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]