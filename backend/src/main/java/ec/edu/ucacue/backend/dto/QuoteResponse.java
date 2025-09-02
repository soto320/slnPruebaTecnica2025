package ec.edu.ucacue.backend.dto;

import java.math.BigDecimal;

public record QuoteResponse(
  BigDecimal base,        // 100.00
  BigDecimal discountPct, // 0.00 o 0.03
  BigDecimal total,
  String schoolType,
  String career
) {}
