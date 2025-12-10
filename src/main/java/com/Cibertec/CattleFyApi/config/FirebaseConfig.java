package com.Cibertec.CattleFyApi.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration("firebaseConfig")
public class FirebaseConfig {

    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            InputStream serviceAccount;

            String firebaseCreds = System.getenv("FIREBASE_CREDENTIALS");
            if (firebaseCreds != null && !firebaseCreds.isBlank()) {
                log.info("Cargando credenciales de Firebase desde variable de entorno");
                serviceAccount = new ByteArrayInputStream(firebaseCreds.getBytes(StandardCharsets.UTF_8));
            } else {
                ClassPathResource resource = new ClassPathResource("serviceAccountKey.json");
                if (!resource.exists()) {
                    log.error("Archivo serviceAccountKey.json no encontrado en resources y no existe variable de entorno FIREBASE_CREDENTIALS");
                    throw new RuntimeException("Archivo serviceAccountKey.json no encontrado");
                }
                log.info("Cargando credenciales de Firebase desde archivo local");
                serviceAccount = resource.getInputStream();
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp app = FirebaseApp.initializeApp(options);
            log.info("Firebase inicializado correctamente");
            log.info("Firebase App Name: {}", app.getName());
            log.info("Project ID: {}", options.getProjectId());

            return app;
        } else {
            log.info("Firebase ya estaba inicializado");
            return FirebaseApp.getInstance();
        }
    }
}