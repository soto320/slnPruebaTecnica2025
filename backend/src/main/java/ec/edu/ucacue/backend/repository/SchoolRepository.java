package ec.edu.ucacue.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.edu.ucacue.backend.entity.School;

public interface SchoolRepository extends JpaRepository<School, Long> {}