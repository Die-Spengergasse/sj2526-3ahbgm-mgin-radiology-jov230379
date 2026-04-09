package at.spengergasse.spring_thymeleaf.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Device {

    @Id
    private String id;

    private String type;
    private String room;

    public Device() {}

    public Device(String id, String type, String room) {
        this.id = id;
        this.type = type;
        this.room = room;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }
}