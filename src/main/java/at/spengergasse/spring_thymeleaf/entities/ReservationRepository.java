package at.spengergasse.spring_thymeleaf.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    boolean existsByDeviceIdAndReservationTime(String deviceId, LocalDateTime reservationTime);
    boolean existsByPatientNameAndReservationTime(String patientName, LocalDateTime reservationTime);
}
