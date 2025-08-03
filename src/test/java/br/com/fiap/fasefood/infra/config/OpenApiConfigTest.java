package br.com.fiap.fasefood.infra.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OpenApiConfigTest {

    @Test
    void deveCriarBeanOpenAPI() {
        OpenApiConfig config = new OpenApiConfig();
        OpenAPI openAPI = config.faseFood();

        assertNotNull(openAPI);
        assertEquals("Fase Food", openAPI.getInfo().getTitle());
        assertEquals("v0.0.1", openAPI.getInfo().getVersion());
        assertEquals("Apache 2.0", openAPI.getInfo().getLicense().getName());
        assertEquals("http://localhost:8080", openAPI.getServers().get(0).getUrl());
    }
}
