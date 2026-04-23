package at.spengergasse.spring_thymeleaf.controllers;

import at.spengergasse.spring_thymeleaf.entities.Patient;
import at.spengergasse.spring_thymeleaf.entities.PatientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
@RequestMapping("/patient")
public class PatientController {

    private final PatientRepository patientRepository;

    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("patients", patientRepository.findAll());
        return "patlist";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("patient", new Patient());
        return "add_patient";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute Patient patient, Model model) {

        // patient immer ins Model damit Felder nicht leer werden bei Fehler
        model.addAttribute("patient", patient);

        // Geburtsdatum leer?
        if (patient.getBirth() == null) {
            model.addAttribute("fehler", "Bitte Geburtsdatum eingeben!");
            return "add_patient";
        }

        // Geburtsdatum in der Zukunft?
        if (patient.getBirth().isAfter(LocalDate.now())) {
            model.addAttribute("fehler", "Geburtsdatum darf nicht in der Zukunft liegen!");
            return "add_patient";
        }

        // SVNR leer?
        if (patient.getInsuranceNumber() == null) {
            model.addAttribute("fehler", "Bitte Sozialversicherungsnummer eingeben!");
            return "add_patient";
        }

        // SVNR muss genau 10 Stellen haben
        String svnrStr = String.valueOf(patient.getInsuranceNumber());
        if (svnrStr.length() != 10) {
            model.addAttribute("fehler", "Ungueltige Sozialversicherungsnummer - muss genau 10 Stellen haben!");
            return "add_patient";
        }

        patientRepository.save(patient);
        return "redirect:/patient/list";
    }
}
