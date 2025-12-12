# --- Etapa 1: Build (El Taller) ---
# Usamos una imagen oficial de Maven que incluye el JDK 21
FROM maven:3.9-eclipse-temurin-21 AS builder

# Establecemos el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos solo el pom.xml para aprovechar el cache de Docker
# Si las dependencias no cambian, Docker no las volverá a descargar
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos el resto del código fuente
COPY src ./src

# Compilamos la aplicación, creando el .jar. Saltamos las pruebas.
# Usamos -DskipTests para evitar ejecutar tests durante el build de la imagen,
# ya que los tests se ejecutan en la fase de CI/CD, no en el build de la imagen.
RUN mvn clean install -DskipTests

# --- Etapa 2: Runtime (El Producto Final) ---
# Usamos una imagen de JRE súper ligera basada en Alpine Linux
FROM eclipse-temurin:21-jre-alpine

# Establecemos el directorio de trabajo
WORKDIR /app

# Copiamos el .jar creado en la etapa 'builder' a esta nueva etapa
COPY --from=builder /app/target/*.jar app.jar

# Exponemos el puerto en el que corre nuestra aplicación Spring Boot
EXPOSE 8080

# El comando que se ejecutará cuando el contenedor se inicie
ENTRYPOINT ["java", "-jar", "app.jar"]
