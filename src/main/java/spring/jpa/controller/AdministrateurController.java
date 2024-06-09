package spring.jpa.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;


import jakarta.servlet.http.HttpSession;
import spring.jpa.model.Conge;
import spring.jpa.model.Employe;
import spring.jpa.model.EtatConge;
import spring.jpa.repository.CongeRepository;
import spring.jpa.repository.EmployeRepository;

@Controller
@RequestMapping(value = "/admin")
public class AdministrateurController {
	private final CongeRepository congeRepos;
	private final EmployeRepository employeRepos;

	@Autowired
	public AdministrateurController(CongeRepository congeRepos, EmployeRepository employeRepos) {
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
            @RequestParam(name = "employeeId", required= false) String employeeId,
            @RequestParam(name = "etatRechercher", required = false) EtatConge etatRechercher,
            HttpSession session
	        ) {

	    Long connectedId = (Long) session.getAttribute("connectedId");
	    String connectedType = (String) session.getAttribute("connectedType");

	    if (connectedId == null || !"admin".equalsIgnoreCase(connectedType)) {
	        return "redirect:/auth/login";
	    }
	    
	    List<Employe> employes = employeRepos.findAll();
	    Page<Conge> conges;
	    
	    if (employeeId != null && !employeeId.isEmpty()) {
	        if (year != null && etatRechercher != null) {
	            conges = congeRepos.findByEmployeeIdAndYearAndEtat(
	                    employeeId,
	                    year,
	                    etatRechercher,
	                    PageRequest.of(p, 10)
	            );
	        } else if (year != null) {
	            conges = congeRepos.findByEmployeeIdAndYear(
	                    employeeId,
	                    year,
	                    PageRequest.of(p, 10)
	            );
	        } else if (etatRechercher != null) {
	            conges = congeRepos.findByEmployeeIdAndEtat(
	                    employeeId,
	                    etatRechercher,
	                    PageRequest.of(p, 10)
	            );
	        } else {
	            conges = congeRepos.findByEmployeeId(
	                    employeeId,
	                    PageRequest.of(p, 10)
	            );
	        }
	    } else {
	        if (year != null && etatRechercher != null) {
	            conges = congeRepos.findByYearAndEtat(
	                    year,
	                    etatRechercher,
	                    PageRequest.of(p, 10)
	            );
	        } else if (year != null) {
	            conges = congeRepos.findByYear(
	                    year,
	                    PageRequest.of(p, 10)
	            );
	        } else if (etatRechercher != null) {
	            conges = congeRepos.findByEtat(
	                    etatRechercher,
	                    PageRequest.of(p, 10)
	            );
	        } else {
	            conges = congeRepos.findAll(
	                    PageRequest.of(p, 10)
	            );
	        }
	    }


	    model.addAttribute("connectedId", connectedId);
	    model.addAttribute("conges", conges);
	    model.addAttribute("employes", employes);
	    model.addAttribute("year", year);
	    model.addAttribute("etatRechercher", etatRechercher);
	    model.addAttribute("employeeId", employeeId);
	    model.addAttribute("pageCourant", p);
	    return "admin";
	}
	
    @GetMapping("/validateConge")
    public String validateConge(@RequestParam Long id, HttpSession session) {
	    Long connectedId = (Long) session.getAttribute("connectedId");
	    String connectedType = (String) session.getAttribute("connectedType");

	    if (connectedId == null || !"admin".equalsIgnoreCase(connectedType)) {
	        return "redirect:/auth/login";
	    }

        Optional<Conge> optionalConge = congeRepos.findById(id);
        if (optionalConge.isPresent()) {
            Conge conge = optionalConge.get();
            if (conge != null) {
            	conge.setEtat(EtatConge.VALIDE);
            	congeRepos.save(conge);
            } else {
                return "redirect:/error";
            }
        } else {
            return "redirect:/error";
        }

        return "redirect:/admin/index";
    }
    
    @GetMapping("/refuseConge")
    public String refuseConge(@RequestParam Long id, HttpSession session) {
	    Long connectedId = (Long) session.getAttribute("connectedId");
	    String connectedType = (String) session.getAttribute("connectedType");

	    if (connectedId == null || !"admin".equalsIgnoreCase(connectedType)) {
	        return "redirect:/auth/login";
	    }

        Optional<Conge> optionalConge = congeRepos.findById(id);
        if (optionalConge.isPresent()) {
            Conge conge = optionalConge.get();
            if (conge != null) {
            	conge.setEtat(EtatConge.REFUSE);
            	congeRepos.save(conge);
            } else {
                return "redirect:/error";
            }
        } else {
            return "redirect:/error";
        }

        return "redirect:/admin/index";
    }
    
    @GetMapping("/stopConge")
    public String stopConge(@RequestParam Long id, HttpSession session) {
	    Long connectedId = (Long) session.getAttribute("connectedId");
	    String connectedType = (String) session.getAttribute("connectedType");

	    if (connectedId == null || !"admin".equalsIgnoreCase(connectedType)) {
	        return "redirect:/auth/login";
	    }

        Optional<Conge> optionalConge = congeRepos.findById(id);
        if (optionalConge.isPresent()) {
            Conge conge = optionalConge.get();
            if (conge != null) {
            	conge.setEtat(EtatConge.ARRETE);
            	conge.setDateRepture(LocalDate.now());
            	congeRepos.save(conge);
            } else {
                return "redirect:/error";
            }
        } else {
            return "redirect:/error";
        }

        return "redirect:/admin/index";
    }
    
    @GetMapping("/annulerConge")
    public String annulerConge(@RequestParam Long id, HttpSession session) {
	    Long connectedId = (Long) session.getAttribute("connectedId");
	    String connectedType = (String) session.getAttribute("connectedType");

	    if (connectedId == null || !"admin".equalsIgnoreCase(connectedType)) {
	        return "redirect:/auth/login";
	    }

        Optional<Conge> optionalConge = congeRepos.findById(id);
        if (optionalConge.isPresent()) {
            Conge conge = optionalConge.get();
            if (conge != null) {
            	conge.setEtat(EtatConge.ANNULE);
            	congeRepos.save(conge);
            } else {
                return "redirect:/error";
            }
        } else {
            return "redirect:/error";
        }

        return "redirect:/admin/index";
    }

}
