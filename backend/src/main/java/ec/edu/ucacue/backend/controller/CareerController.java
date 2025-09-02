package ec.edu.ucacue.backend.controller;

import ec.edu.ucacue.backend.entity.Inscription;
import ec.edu.ucacue.backend.repository.CareerRepository;
import ec.edu.ucacue.backend.repository.InscriptionRepository;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController 
@RequestMapping("/api/carreras") 
@CrossOrigin
public class CareerController {
  private final CareerRepository careerRepo;
  private final InscriptionRepository insRepo;

  public CareerController(CareerRepository c, InscriptionRepository i){ this.careerRepo = c; this.insRepo = i; }

  // Lista de carreras
  @GetMapping public Object list(){ return careerRepo.findAll(); }

  // Estadísticas: carrera + número de inscritos
  @GetMapping("/estadisticas")
  public List<Map<String,Object>> stats(){
    var raw = insRepo.countByCareer();
    List<Map<String,Object>> out = new ArrayList<>();
    for (Object[] row : raw){
      out.add(Map.of("id", row[0], "name", row[1], "inscritos", ((Long)row[2]).intValue()));
    }
    return out;
  }

/*   // Detalle de inscritos por carrera
  @GetMapping("/{id}/inscritos")
  public List<Map<String,Object>> enrolled(@PathVariable Long id){
    List<Inscription> list = insRepo.findByCareerIdOrderByCreatedAtDesc(id);
    return list.stream().map(i -> Map.of(
        "nombre", i.getApplicantName(),
        "cedula", i.getApplicantCedula(),
        "email", i.getApplicantEmail(),
        "tipoColegio", i.getSchool().getType().name(),
        "valorPagado", i.getAmountPaid()
    )).toList();
  }*/
  // Detalle de inscritos por carrera
  @GetMapping("/{id}/inscritos")
  public List<Map<String, Object>> enrolled(@PathVariable Long id) {
    List<Inscription> list = insRepo.findByCareerIdOrderByCreatedAtDesc(id);

    // Java 17:
    return list.stream()
        .map(i -> {
            Map<String, Object> map = new HashMap<>();
            map.put("nombre",      i.getApplicantName());
            map.put("cedula",      i.getApplicantCedula());
            map.put("email",       i.getApplicantEmail());
            map.put("tipoColegio", i.getSchool().getType().name());
            map.put("valorPagado", i.getAmountPaid());
            return map;
        })
        .toList();
  }
}
