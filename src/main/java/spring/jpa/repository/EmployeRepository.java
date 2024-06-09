package spring.jpa.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import spring.jpa.model.Employe;

public interface EmployeRepository extends JpaRepository<Employe,Long>{
	Employe findByCode(String code);
	List<Employe> findAll();

	@Query("SELECT employe FROM Employe employe WHERE employe.nom LIKE %:nom% AND employe.dateEmbauchement = :date")
	Page<Employe> findByEmployeOrDate(String nom, String date, Pageable pageable);



	Employe findByCodeAndPassword(String code, String password);
}
