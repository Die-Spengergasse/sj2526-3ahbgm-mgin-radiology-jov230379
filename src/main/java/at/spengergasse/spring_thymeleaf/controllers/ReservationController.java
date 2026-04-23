package at.spengergasse.spring_thymeleaf.controllers;

import at.spengergasse.spring_thymeleaf.entities.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final PatientRepository patientRepository;
    private final DeviceRepository deviceRepository;

    public ReservationController(ReservationRepository reservationRepository,
                                 PatientRepository patientRepository,
                                 DeviceRepository deviceRepository) {
        this.reservationRepository = reservationRepository;
        this.patientRepository = patientRepository;
        this.deviceRepository = deviceRepository;
    }

    @GetMapping("/add")
    public String add(Model model) {
        List<String> patientNames = new ArrayList<>();
        for (Patient p : patientRepository.findAll()) {
            patientNames.add(p.getFirstName() + " " + p.getSurname());
        }
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("patients", patientNames);
        model.addAttribute("devices", deviceRepository.findAll());
        return "add_reservation";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute Reservation reservation, Model model) {

        // Immer patients + devices ins Model laden (fuer Rueckgabe bei Fehler)
        List<String> patientNames = new ArrayList<>();
        for (Patient p : patientRepository.findAll()) {
            patientNames.add(p.getFirstName() + " " + p.getSurname());
        }
        model.addAttribute("reservation", reservation);
        model.addAttribute("patients", patientNames);
        model.addAttribute("devices", deviceRepository.findAll());

        // Zeit leer
        if (reservation.getReservationTime() == null) {
            model.addAttribute("fehler", "Bitte eine Zeit eingeben!");
            return "add_reservation";
        }

        // Reservierung in der Vergangenheit
        if (reservation.getReservationTime().isBefore(LocalDateTime.now())) {
            model.addAttribute("fehler", "Reservierung kann nicht in der Vergangenheit liegen!");
            return "add_reservation";
        }

        // Geraet belegt
        if (reservationRepository.existsByDeviceIdAndReservationTime(
                reservation.getDeviceId(), reservation.getReservationTime())) {
            model.addAttribute("fehler", "Dieses Geraet ist zu diesem Zeitpunkt bereits belegt!");
            return "add_reservation";
        }

        // Patient hat bereits zu diesem Zeitpunkt einen Termin
        if (reservationRepository.existsByPatientNameAndReservationTime(
                reservation.getPatientName(), reservation.getReservationTime())) {
            model.addAttribute("fehler", "Dieser Patient hat zu diesem Zeitpunkt bereits einen Termin!");
            return "add_reservation";
        }

        // DB Fehler
        try {
            reservationRepository.save(reservation);
        } catch (Exception e) {
            model.addAttribute("fehler", "Datenbankfehler: Bitte sicherstellen dass MySQL laeuft!");
            return "add_reservation";
        }

        return "redirect:/reservation/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("reservations", reservationRepository.findAll());
        return "reservation_list";
    }
}
