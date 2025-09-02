package ec.edu.ucacue.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.edu.ucacue.backend.entity.Career;

public interface CareerRepository extends JpaRepository<Career, Long> {}