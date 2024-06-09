package spring.jpa.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.servlet.http.HttpSession;
import spring.jpa.model.Administrateur;
import spring.jpa.model.Employe;
import spring.jpa.repository.AdministrateurRepository;
import spring.jpa.repository.EmployeRepository;

@Controller
@RequestMapping("/auth")
@SessionAttributes({"connectedId", "connectedType"})
public class LoginController {
    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private AdministrateurRepository adminRepository;

    @RequestMapping(value="/login")
    public String login() {
        return "login";
    }

    @PostMapping(value="/login")
    public String login(@RequestParam("code") String code,
                        @RequestParam("password") String password,
                        @RequestParam("role") String role,
                        Model model) {
        if ("admin".equalsIgnoreCase(role)) {
            return adminLogin(code, password, model);
        } else {
            return employeeLogin(code, password, model);
        }
    }

    public String adminLogin(String code, String password, Model model) {
        Administrateur admin = adminRepository.findByCodeAndPassword(code, password);
        if (admin != null) {
            model.addAttribute("connectedId", admin.getId());
            model.addAttribute("connectedType", "admin");
            model.addAttribute("user", admin);
            return "redirect:/admin/index";
        } else {
            model.addAttribute("error", "Invalid credentials for admin");
            return "login";
        }
    }

    public String employeeLogin(String code, String password, Model model) {
        Employe employee = employeRepository.findByCodeAndPassword(code, password);
        if (employee != null) {
            model.addAttribute("connectedId", employee.getId());
            model.addAttribute("connectedType", "employee");
            model.addAttribute("user", employee);
            return "redirect:/employe/index";
        } else {
            model.addAttribute("error", "Invalid credentials for employee");
            return "login";
        }
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logout(SessionStatus status, HttpSession session) {
        status.setComplete();
        session.invalidate();
        return "redirect:/auth/login";
    }
}
