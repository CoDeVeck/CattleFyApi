package com.Cibertec.CattleFyApi.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Configuration("firebaseConfig")
public class FirebaseConfig {

    private static final String RENDER_SECRET_FILE_PATH = "/etc/secrets/FIREBASE_CREDENTIALS";

    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            InputStream serviceAccount = null;

            Path renderSecretPath = Path.of(RENDER_SECRET_FILE_PATH);
            if (Files.exists(renderSecretPath)) {
                log.info("Cargando credenciales de Firebase desde Secret File de Render: {}", RENDER_SECRET_FILE_PATH);
                serviceAccount = new FileInputStream(renderSecretPath.toFile());
            }

            if (serviceAccount == null) {
                String firebaseCreds = System.getenv("FIREBASE_CREDENTIALS");
                if (firebaseCreds != null && !firebaseCreds.isBlank()) {
                    log.info("Cargando credenciales de Firebase desde variable de entorno");
                    serviceAccount = new ByteArrayInputStream(firebaseCreds.getBytes(StandardCharsets.UTF_8));
                }
            }

            if (serviceAccount == null) {
                ClassPathResource resource = new ClassPathResource("serviceAccountKey.json");
                if (!resource.exists()) {
                    // Si llega aquí, significa que fallaron las 3 formas.
                    log.error("Archivo serviceAccountKey.json no encontrado en resources, ni en Render Secret File, ni como variable de entorno FIREBASE_CREDENTIALS");
                    throw new RuntimeException("Archivo serviceAccountKey.json no encontrado");
                }
                log.info("Cargando credenciales de Firebase desde archivo local (Fallback)");
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