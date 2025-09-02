package ec.edu.ucacue.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
public class Product {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank @Column(nullable = false)
  private String name;

  @NotNull @DecimalMin("0.0")
  private BigDecimal price;

  private String description;


  // getters/setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public BigDecimal getPrice() { return price; }
  public void setPrice(BigDecimal price) { this.price = price; }

  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
}