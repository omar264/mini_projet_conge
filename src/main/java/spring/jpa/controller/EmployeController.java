package spring.jpa.controller;

import org.springframework.data.domain.Page;

import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import spring.jpa.model.Conge;
import spring.jpa.model.Employe;
import spring.jpa.model.EtatConge;
import spring.jpa.repository.CongeRepository;
import spring.jpa.repository.EmployeRepository;


@Controller
@RequestMapping(value = "/employe")
public class EmployeController {
	private final CongeRepository congeRepos;
	private final EmployeRepository employeRepos;

	@Autowired
	public EmployeController(CongeRepository congeRepos, EmployeRepository employeRepos) {
		this.congeRepos = congeRepos;
		this.employeRepos = employeRepos;
	}

	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(SessionStatus status, HttpSession session) {
		status.setComplete();
		session.invalidate();
		return "redirect:/auth/login";
	}

	@RequestMapping(value = "/index")
	public String index(Model model,
						@RequestParam(name = "page", defaultValue = "0") int p,
						@RequestParam(name = "year", required = false) Integer year,
						@RequestParam(name = "etatRechercher", required = false) EtatConge etatRechercher,
						HttpSession session
	) {
		Long employeId = (Long) session.getAttribute("connectedId");
		String connectedType = (String) session.getAttribute("connectedType");

		if (employeId == null  || !"employee".equalsIgnoreCase(connectedType)) {
			return "redirect:/auth/login";
		}

		Employe employee = employeRepos.getById(employeId);

		Page<Conge> conges;
		if (year != null && etatRechercher != null) {
			conges = congeRepos.findByEmployeeIdAndYearAndEtat(
					employeId,
					year,
					etatRechercher,
					PageRequest.of(p, 10)
			);
		} else if (year != null) {
			conges = congeRepos.findByEmployeeIdAndYear(
					employeId,
					year,
					PageRequest.of(p, 10)
			);
		} else if (etatRechercher != null) {
			conges = congeRepos.findByEmployeeIdAndEtat(
					employeId,
					etatRechercher,
					PageRequest.of(p, 10)
			);
		} else {
			conges = congeRepos.findByEmployeeId(
					employeId,
					PageRequest.of(p, 10)
			);
		}

		model.addAttribute("connectedId", employeId);
		model.addAttribute("employee", employee);
		model.addAttribute("conges", conges);
		model.addAttribute("year", year);
		model.addAttribute("etatRechercher", etatRechercher);
		model.addAttribute("pageCourant", p);
		return "employe";
	}

	@RequestMapping(value = "/addConge")
	public String addConge(Model model,
						   HttpSession session
	) {
		Long employeId = (Long) session.getAttribute("connectedId");
		String connectedType = (String) session.getAttribute("connectedType");

		if (employeId == null  || !"employee".equalsIgnoreCase(connectedType)) {
			return "redirect:/auth/login";
		}

		Employe employee = employeRepos.getById(employeId);


		model.addAttribute("connectedId", employeId);
		model.addAttribute("employee", employee);
		return "AjoutConge";
	}


	@PostMapping("/createConge")
	public String createConge(@Valid Conge conge, HttpSession session, RedirectAttributes redirectAttributes) {
		Long employeId = (Long) session.getAttribute("connectedId");
		String connectedType = (String) session.getAttribute("connectedType");

		if (employeId == null  || !"employee".equalsIgnoreCase(connectedType)) {
			return "redirect:/auth/login";
		}

		Employe employe = employeRepos.findById(employeId).orElse(null);
		if (employe == null) {
			return "redirect:/error";
		}
		int congeRestant = employe.getCongeRestant();

		long numberOfDaysRequested = ChronoUnit.DAYS.between(conge.getDateDebut(), conge.getDateFin()) + 1;
		if (numberOfDaysRequested > congeRestant) {
			redirectAttributes.addFlashAttribute("alertMessage", "Vous n'avez pas assez de jours de congé restants pour demander ce congé.");
			return "redirect:/employe/index";
		}

		conge.setEtat(EtatConge.SOLLICITE);
		conge.setEmploye(employe);

		congeRepos.save(conge);

		return "redirect:/employe/index";
	}

	@GetMapping("/deleteConge")
	public String deleteConge(@RequestParam Long id, HttpSession session) {
		Long employeId = (Long) session.getAttribute("connectedId");
		String connectedType = (String) session.getAttribute("connectedType");

		if (employeId == null  || !"employee".equalsIgnoreCase(connectedType)) {
			return "redirect:/auth/login";
		}

		Optional<Conge> optionalConge = congeRepos.findById(id);
		if (optionalConge.isPresent()) {
			Conge conge = optionalConge.get();
			if (conge.getEmploye().getId().equals(employeId)) {
				conge.setEtat(EtatConge.ANNULE);
				congeRepos.save(conge);
			} else {
				return "redirect:/error";
			}
		} else {
			return "redirect:/error";
		}

		return "redirect:/employe/index";
	}

}

