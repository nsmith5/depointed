# Build
FROM clojure:openjdk-11-lein as builder
WORKDIR /app/source
ADD project.clj .
RUN lein deps
ADD . .
RUN lein uberjar

# Run
FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY --from=builder /app/source/target/uberjar/strokeless-0.1.0-SNAPSHOT-standalone.jar strokeless.jar
EXPOSE 3000
CMD ["java", "-jar", "strokeless.jar"]
