package at.spengergasse.spring_thymeleaf.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String patientName;
    private String deviceId;

    private LocalDateTime reservationTime;

    private String bodyRegion;
    private String comment;

    public Reservation() {}

    public int getId() { return id; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }

    public LocalDateTime getReservationTime() { return reservationTime; }
    public void setReservationTime(LocalDateTime reservationTime) { this.reservationTime = reservationTime; }

    public String getBodyRegion() { return bodyRegion; }
    public void setBodyRegion(String bodyRegion) { this.bodyRegion = bodyRegion; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}