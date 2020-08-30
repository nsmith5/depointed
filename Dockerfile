# Frontend
FROM node:14 as frontend
WORKDIR /workdir/frontend
COPY frontend .
RUN ls
RUN yarn install
RUN yarn build

# Backend
FROM clojure:openjdk-11-lein as backend
WORKDIR /app/source
ADD project.clj .
RUN lein deps
ADD . .
COPY --from=frontend /workdir/frontend/dist ./resources/public
RUN lein uberjar

# Production
FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY --from=backend /app/source/target/uberjar/depointed-0.1.0-SNAPSHOT-standalone.jar depointed.jar
EXPOSE 3000
CMD ["java", "-jar", "depointed.jar"]
