package ec.edu.espe.petshopinventorycontrol.controller;

import java.time.LocalDateTime;


public class SystemEvent {
    private final EventType type;
    private final String message;
    private final LocalDateTime timestamp;

    public SystemEvent(EventType type, String message) {
        this.type = type;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("[%s] %s: %s", timestamp, type, message);
    }
}