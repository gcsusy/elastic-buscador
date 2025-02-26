package gc.unir.actividad2.elastic_buscador.controller;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class ElasticBuscadorController {
	@GetMapping("/")
    public String inicio() {
        return "Bienvenido al servicio Buscador de ElasticSearch en Bonsai";
    }
}

