package ec.edu.ucacue.backend.controller;


import ec.edu.ucacue.backend.entity.School;
import ec.edu.ucacue.backend.repository.SchoolRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/colegios") @CrossOrigin
public class SchoolController {
  private final SchoolRepository repo;
  public SchoolController(SchoolRepository repo){ this.repo = repo; }
  @GetMapping public List<School> list(){ return repo.findAll(); }
}
