package ec.edu.ucacue.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ec.edu.ucacue.backend.entity.Product;
import ec.edu.ucacue.backend.repository.ProductRepository;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {
  private final ProductRepository repo;
  public ProductController(ProductRepository repo) { this.repo = repo; }

  @GetMapping public List<Product> list() { return repo.findAll(); }

  @GetMapping("/{id}")
  public ResponseEntity<Product> get(@PathVariable Long id) {
    return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Product> create(@Valid @RequestBody Product p) {
    return ResponseEntity.ok(repo.save(p));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Product> update(@PathVariable Long id, @Valid @RequestBody Product p) {
    return repo.findById(id)
      .map(db -> {
        db.setName(p.getName());
        db.setPrice(p.getPrice());
        db.setDescription(p.getDescription());
        return ResponseEntity.ok(repo.save(db));
      }).orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    if (!repo.existsById(id)) return ResponseEntity.notFound().build();
    repo.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/health") public String health(){ return "OK"; }
}