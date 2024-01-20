package tn.esprit.shadowtradergo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration

public class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/uploads/**") // L'URL à partir de laquelle les fichiers seront servis
                .addResourceLocations("file:uploads/") // Le chemin physique vers le répertoire où les fichiers sont stockés
                .setCachePeriod(0); // Désactiver la mise en cache
    }
}

