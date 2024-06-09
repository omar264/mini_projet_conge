package spring.jpa.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import spring.jpa.model.Conge;
import spring.jpa.model.EtatConge;




public interface CongeRepository extends  JpaRepository<Conge, Long>{
	
	// employee
	
	@Query("SELECT c FROM Conge c WHERE c.employe.id = :id order by c.id Desc")
	Page<Conge> findByEmployeeId(@Param("id") Long id, Pageable pageable);

    @Query("SELECT c FROM Conge c WHERE c.employe.id = :id AND YEAR(c.dateDebut) = :year AND c.etat = :etat order by c.id Desc")
    Page<Conge> findByEmployeeIdAndYearAndEtat(
            @Param("id") Long id,
            @Param("year") int year,
            @Param("etat") EtatConge etat,
            Pageable pageable
    );

    @Query("SELECT c FROM Conge c WHERE c.employe.id = :id AND YEAR(c.dateDebut) = :year order by c.id Desc")
    Page<Conge> findByEmployeeIdAndYear(@Param("id") Long id, @Param("year") int year, Pageable pageable);

    @Query("SELECT c FROM Conge c WHERE c.employe.id = :id AND c.etat = :etat order by c.id Desc")
    Page<Conge> findByEmployeeIdAndEtat(@Param("id") Long id, @Param("etat") EtatConge etat, Pageable pageable);
    
    // admin

    @Query("SELECT c FROM Conge c WHERE YEAR(c.dateDebut) = :year AND c.etat = :etat order by c.dateDebut Desc")
    Page<Conge> findByYearAndEtat(
            @Param("year") int year,
            @Param("etat") EtatConge etat,
            Pageable pageable
    );

    @Query("SELECT c FROM Conge c WHERE YEAR(c.dateDebut) = :year order by c.dateDebut Desc")
    Page<Conge> findByYear(@Param("year") int year, Pageable pageable);

    @Query("SELECT c FROM Conge c WHERE c.etat = :etat order by c.dateDebut Desc")
    Page<Conge> findByEtat(@Param("etat") EtatConge etat, Pageable pageable);

    @Query("SELECT c FROM Conge c order by c.dateDebut Desc")
    Page<Conge> findAll(Pageable pageable);
    
    @Query("SELECT c FROM Conge c WHERE c.employe.id = :employeeId order by c.dateDebut Desc")
    Page<Conge> findByEmployeeId(@Param("employeeId") String employeeId, Pageable pageable);

    @Query("SELECT c FROM Conge c WHERE c.employe.id = :employeeId AND YEAR(c.dateDebut) = :year order by c.dateDebut Desc")
    Page<Conge> findByEmployeeIdAndYear(
            @Param("employeeId") String employeeId,
            @Param("year") int year,
            Pageable pageable
    );

    @Query("SELECT c FROM Conge c WHERE c.employe.id = :employeeId AND c.etat = :etat order by c.dateDebut Desc")
    Page<Conge> findByEmployeeIdAndEtat(
            @Param("employeeId") String employeeId,
            @Param("etat") EtatConge etat,
            Pageable pageable
    );

    @Query("SELECT c FROM Conge c WHERE c.employe.id = :employeeId AND YEAR(c.dateDebut) = :year AND c.etat = :etat order by c.dateDebut Desc")
    Page<Conge> findByEmployeeIdAndYearAndEtat(
            @Param("employeeId") String employeeId,
            @Param("year") int year,
            @Param("etat") EtatConge etat,
            Pageable pageable
    );

    Conge save(Conge conge);
    
    void deleteById(Long id); 
    
    
    // system
    
    List<Conge> findByEtat(EtatConge etat);
    List<Conge> findByEtatAndDateDebutLessThanEqual(EtatConge etat, LocalDate date);
    List<Conge> findByEtatAndDateFinLessThanEqual(EtatConge etat, LocalDate date);
}
