package spring.jpa.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
@Entity
public class Conge {
	@Override
    public String toString() {
        return "Conge [id=" + id + ", description=" + description + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin
                + ", dateRepture=" + dateRepture + ", etat=" + (etat != null ? etat.toString() : "null") + ", employe=" + employe + "]";
    }

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(length = 50)
	private String description;
	@Temporal(TemporalType.DATE)
	private LocalDate dateDebut;
	@Temporal(TemporalType.DATE)
	private LocalDate dateFin;
	@Temporal(TemporalType.DATE)
	private LocalDate dateRepture;
	private EtatConge etat;
	@ManyToOne
	private Employe employe;
	public Conge(String description, LocalDate dateDebut, LocalDate dateFin, LocalDate dateRepture, EtatConge etat) {
		super();
		this.description = description;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.dateRepture = dateRepture;
		this.etat = etat;
	}
	
	public Conge(String description, LocalDate dateDebut, LocalDate dateFin, LocalDate dateRepture, EtatConge etat, Employe employe) {
		super();
		this.description = description;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.dateRepture = dateRepture;
		this.etat = etat;
		this.employe = employe;
	}

	public Conge() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Employe getEmploye() {
		return employe;
	}

	public void setEmploye(Employe employeconge) {
		this.employe = employeconge;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDate getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}
	public LocalDate getDateFin() {
		return dateFin;
	}
	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}
	public LocalDate getDateRepture() {
		return dateRepture;
	}
	public void setDateRepture(LocalDate dateRepture) {
		this.dateRepture = dateRepture;
	}
	public EtatConge getEtat() {
		return etat;
	}

	public void setEtat(EtatConge etat) {
		this.etat = etat;
	}


}
