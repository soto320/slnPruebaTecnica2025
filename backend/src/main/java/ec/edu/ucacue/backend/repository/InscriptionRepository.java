package ec.edu.ucacue.backend.repository;

import ec.edu.ucacue.backend.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;




public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
  @Query("select c.id, c.name, count(i.id) from Inscription i right join i.career c group by c.id, c.name order by c.name")
  List<Object[]> countByCareer();
  List<Inscription> findByCareerIdOrderByCreatedAtDesc(Long careerId);
}
