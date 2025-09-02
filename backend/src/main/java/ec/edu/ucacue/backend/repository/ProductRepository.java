package ec.edu.ucacue.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.edu.ucacue.backend.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {}
