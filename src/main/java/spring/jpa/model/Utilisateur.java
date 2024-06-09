package spring.jpa.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
@MappedSuperclass
public class Utilisateur {
	@Override
	public String toString() {
		return "Utilisateur [id=" + id + ", code=" + code + ", nom=" + nom + ", prenom=" + prenom
				+ ", dateEmbauchement=" + dateEmbauchement + ", password=" + password + "]";
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
	@Column(length = 50)
	protected String code ;
	@Column(length = 50)
	protected String nom;
	@Column(length = 50)
	protected String prenom;
	@Temporal(TemporalType.DATE)
	LocalDate dateEmbauchement;
	@Size(min = 6, max = 10, message = "Le mot de passe doit contenir entre 6 et 10 caractères.")
	protected String password;

	public Utilisateur() {
		super();
	}

	public Utilisateur(Long id, String code, String nom, String prenom, LocalDate dateEmbauchement,
			@Size(min = 6, max = 10, message = "Le mot de passe doit contenir entre 6 et 10 caractères.") String password) {
		super();
		this.id = id;
		this.code = code;
		this.nom = nom;
		this.prenom = prenom;
		this.dateEmbauchement = dateEmbauchement;
		this.password = password;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public LocalDate getDateEmbauchement() {
		return dateEmbauchement;
	}
	public void setDateEmbauchement(LocalDate dateEmbauchement) {
		this.dateEmbauchement = dateEmbauchement;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
