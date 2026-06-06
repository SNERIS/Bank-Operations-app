# Përdorim Java 25, sepse projekti yt në pom.xml ka <java.version>25</java.version>
FROM eclipse-temurin:25-jre-alpine

# Vendosim direktorinë e punës brenda container-it
WORKDIR /app

# Kopjojmë .jar që gjeneron Maven te folderi target
# Emri aktual i jar-it është: target/my1project-0.0.1-SNAPSHOT.jar
COPY target/my1project-0.0.1-SNAPSHOT.jar app.jar

# Ekspozojmë portën 8080 ku dëgjon Spring Boot
EXPOSE 8080

# Komanda që nis aplikacionin kur ndizet container-i
ENTRYPOINT ["java", "-jar", "app.jar"]