package com.Cibertec.CattleFyApi.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Configuration("firebaseConfig")
public class FirebaseConfig {

    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            try {
                // Intentar cargar el archivo
                ClassPathResource resource = new ClassPathResource("serviceAccountKey.json");

                if (!resource.exists()) {
                    log.error("El archivo serviceAccountKey no existe en resources");
                    throw new RuntimeException("Archivo serviceAccountKey no encontrado");
                }

                InputStream serviceAccount = resource.getInputStream();

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp app = FirebaseApp.initializeApp(options);
                log.info("✅ Firebase inicializado correctamente");
                log.info("✅ Firebase App Name: {}", app.getName());
                log.info("✅ Project ID: {}", options.getProjectId());

                return app;

            } catch (Exception e) {
                log.error("❌ Error al inicializar Firebase: {}", e.getMessage());
                e.printStackTrace();
                throw e;
            }
        } else {
            log.info("Firebase ya estaba inicializado");
            return FirebaseApp.getInstance();
        }
    }
}