package ec.edu.ucacue.backend.controller;

import ec.edu.ucacue.backend.dto.*;
import ec.edu.ucacue.backend.service.InscriptionService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/inscripciones") @CrossOrigin
public class InscriptionController {
  private final InscriptionService service;
  public InscriptionController(InscriptionService s){ this.service = s; }

  // 1) Cotizar (mostrar valor y pedir confirmación/cancelación)
  @PostMapping("/cotizar")
  public QuoteResponse quote(@Valid @RequestBody InscriptionRequest req){
    return service.quote(req);
  }

  // 2) Confirmar (persistir inscripción y actualizar conteo implícitamente)
  @PostMapping("/confirmar")
  public ResponseEntity<?> confirm(@Valid @RequestBody InscriptionRequest req){
    Long id = service.confirm(req);
    return ResponseEntity.status(HttpStatus.CREATED).body(
      java.util.Map.of("id", id, "message", "Inscripción registrada")
    );
  }
}
