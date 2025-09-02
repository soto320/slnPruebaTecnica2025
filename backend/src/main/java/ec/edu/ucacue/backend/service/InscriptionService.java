package ec.edu.ucacue.backend.service;

import ec.edu.ucacue.backend.entity.*;
import ec.edu.ucacue.backend.dto.*;
import ec.edu.ucacue.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class InscriptionService {
  private final SchoolRepository schoolRepo;
  private final CareerRepository careerRepo;
  private final InscriptionRepository insRepo;

  public InscriptionService(SchoolRepository s, CareerRepository c, InscriptionRepository i) {
    this.schoolRepo = s; this.careerRepo = c; this.insRepo = i;
  }

  public QuoteResponse quote(InscriptionRequest r){
    var school = schoolRepo.findById(r.schoolId()).orElseThrow();
    var career = careerRepo.findById(r.careerId()).orElseThrow();

    var base = new BigDecimal("100.00");
    var discountPct = switch (school.getType()) {
      case FISCAL, FISCOMISIONAL -> new BigDecimal("0.03");
      default -> BigDecimal.ZERO;
    };
    var total = base.subtract(base.multiply(discountPct)).setScale(2, RoundingMode.HALF_UP);
    return new QuoteResponse(base, discountPct, total, school.getType().name(), career.getName());
  }

  @Transactional
  public Long confirm(InscriptionRequest r){
    var q = quote(r);
    var school = schoolRepo.findById(r.schoolId()).orElseThrow();
    var career = careerRepo.findById(r.careerId()).orElseThrow();

    var ins = new Inscription();
    ins.setApplicantName(r.name());
    ins.setApplicantCedula(r.cedula());
    ins.setApplicantEmail(r.email());
    ins.setSchool(school);
    ins.setCareer(career);
    ins.setAmountPaid(q.total());

    return insRepo.save(ins).getId();
  }
}
