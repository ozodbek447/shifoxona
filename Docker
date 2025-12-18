# 1️⃣ Base image sifatida OpenJDK 17 ni ishlatamiz
FROM eclipse-temurin:17-jdk-jammy

# 2️⃣ Ishchi katalog yaratamiz va unga o‘tib olamiz
WORKDIR /app

# 3️⃣ Maven build natijasidagi jar faylni konteynerga nusxalaymiz
#    Target katalogida build qilingani faraz qilinadi
COPY target/sunnatAkaWebSatt-0.0.1-SNAPSHOT.jar app.jar

# 4️⃣ Portni ochamiz (agar Spring Boot default 8080 bo‘lsa)
EXPOSE 8080

# 5️⃣ Spring Boot jar faylini ishga tushirish
ENTRYPOINT ["java","-jar","app.jar"]
