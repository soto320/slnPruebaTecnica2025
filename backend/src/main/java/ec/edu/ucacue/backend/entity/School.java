package ec.edu.ucacue.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class School {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(nullable = false)
  private String name;

  @NotBlank
  @Column(nullable = false)
  private String city;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private SchoolType type; // FISCAL, FISCOMISIONAL, PARTICULAR

  // ----- Getters & Setters -----
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public String getCity() { return city; }
  public void setCity(String city) { this.city = city; }

  public SchoolType getType() { return type; }
  public void setType(SchoolType type) { this.type = type; }

  // ----- Enum -----
  public enum SchoolType { FISCAL, FISCOMISIONAL, PARTICULAR }
}
