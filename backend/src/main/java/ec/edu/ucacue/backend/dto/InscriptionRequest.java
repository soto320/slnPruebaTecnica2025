package ec.edu.ucacue.backend.dto;

import jakarta.validation.constraints.*;

public record InscriptionRequest(
  @NotBlank String name,
  @Pattern(regexp="^\\d{10}$") String cedula,
  @Email @NotBlank String email,
  @NotNull Long schoolId,
  @NotNull Long careerId
) {}
