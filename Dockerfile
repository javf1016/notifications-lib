FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copiar archivos del proyecto
COPY . .

# Compilar la librería (Maven)
RUN ./mvnw clean package -DskipTests || mvn clean package -DskipTests

# Ejecutar clase de ejemplos
# Main con ejemplos
CMD ["java", "-cp", "target/notifications-lib-1.0.0.jar", "com.company.notifications.demo.DemoApp"]