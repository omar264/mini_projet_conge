package spring.jpa.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;

@Entity
public class Administrateur extends Utilisateur {
	public Administrateur() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Administrateur(Long id, String code, String nom, String prenom, LocalDate dateEmbauchement,
			@Size(min = 6, max = 10, message = "Le mot de passe doit contenir entre 6 et 10 caractères.") String password) {
		super(id, code, nom, prenom, dateEmbauchement, password);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Administrateur [id=" + id + ", code=" + code + ", nom=" + nom + ", prenom=" + prenom
				+ ", dateEmbauchement=" + dateEmbauchement + ", password=" + password + "]";
	}
}
