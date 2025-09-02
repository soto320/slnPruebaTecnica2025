package ec.edu.ucacue.demo_crud.controller; // <-- AJUSTA a tu paquete

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PingController {

    @GetMapping(value = "/api/ping", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> ping() {
        return Map.of("status", "ok");
    }
}
