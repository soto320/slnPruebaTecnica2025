package ec.edu.ucacue.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
public class Inscription {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(nullable = false)
  private String applicantName;

  @NotBlank
  @Pattern(regexp = "^\\d{10}$", message = "La cédula debe tener 10 dígitos")
  @Column(nullable = false, length = 10)
  private String applicantCedula;

  @Email
  @NotBlank
  @Column(nullable = false)
  private String applicantEmail;

  @ManyToOne(optional = false)
  private School school;

  @ManyToOne(optional = false)
  private Career career;

  @NotNull
  @DecimalMin("0.0")
  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal amountPaid;

  @Column(nullable = false)
  private OffsetDateTime createdAt = OffsetDateTime.now();

  // ----- Getters & Setters -----
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getApplicantName() { return applicantName; }
  public void setApplicantName(String applicantName) { this.applicantName = applicantName; }

  public String getApplicantCedula() { return applicantCedula; }
  public void setApplicantCedula(String applicantCedula) { this.applicantCedula = applicantCedula; }

  public String getApplicantEmail() { return applicantEmail; }
  public void setApplicantEmail(String applicantEmail) { this.applicantEmail = applicantEmail; }

  public School getSchool() { return school; }
  public void setSchool(School school) { this.school = school; }

  public Career getCareer() { return career; }
  public void setCareer(Career career) { this.career = career; }

  public BigDecimal getAmountPaid() { return amountPaid; }
  public void setAmountPaid(BigDecimal amountPaid) { this.amountPaid = amountPaid; }

  public OffsetDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
