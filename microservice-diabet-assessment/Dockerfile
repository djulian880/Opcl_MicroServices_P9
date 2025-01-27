# Utiliser une image de base JDK pour exécuter une application Java
#FROM eclipse-temurin
FROM gcr.io/distroless/java17-debian11:latest

LABEL authors="juliengutter"
# Défini le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier JAR généré dans le conteneur
COPY target/microservice-diabet-assessment-0.0.1-SNAPSHOT.jar /app/app.jar



# Copier le fichier application-docker.properties
COPY target/classes/application-docker.properties /app/config/application-docker.properties


# Spécifier le port sur lequel l'application fonctionne
EXPOSE 8112

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]