package spring.jpa.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;

@Entity
public class Employe extends Utilisateur  {
	@OneToMany(mappedBy = "employe")
	private Collection<Conge> conge = new ArrayList<Conge>();

    private static final int TOTAL_LEAVE_DAYS_PER_YEAR = 30;

    public int getCongeRestant() {
        int totalLeaveDaysThisYear = 0;

        int currentYear = LocalDate.now().getYear();

        for (Conge c : conge) {
            LocalDate startDate = c.getDateDebut();
            LocalDate endDate = c.getDateFin();

            if (startDate.getYear() == currentYear || endDate.getYear() == currentYear) {
                long daysBetween = ChronoUnit.DAYS.between(startDate, endDate.plusDays(1));
                totalLeaveDaysThisYear += daysBetween;
            }
        }

        int congeRestant = TOTAL_LEAVE_DAYS_PER_YEAR - totalLeaveDaysThisYear;

        return congeRestant;
    }



	@Override
	public String toString() {
		return "Employe [id=" + id + ", code=" + code + ", nom=" + nom + ", prenom=" + prenom
				+ ", dateEmbauchement=" + dateEmbauchement + ", password=" + password + "]";
	}



	public Employe() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Employe(Long id, String email, String nom, String prenom, LocalDate dateEmbauchement,
			@Size(min = 6, max = 10, message = "Le mot de passe doit contenir entre 6 et 10 caractères.") String password) {
		super(id, email, nom, prenom, dateEmbauchement, password);
		// TODO Auto-generated constructor stub
	}



	public Collection<Conge> getConge() {
		return conge;
	}

	public void setConge(Collection<Conge> conge) {
		this.conge = conge;
	}
}
