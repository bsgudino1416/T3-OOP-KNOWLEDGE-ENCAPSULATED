package ec.edu.espe.petshopinventorycontrol.controller;

import java.util.ArrayList;
import java.util.List;


public class EventManager {

    private static EventManager instance;
    private List<IEventListener> listeners;

    private EventManager() {
        listeners = new ArrayList<>();
    }

    public static synchronized EventManager getInstance() {
        if (instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    public void subscribe(IEventListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void unsubscribe(IEventListener listener) {
        listeners.remove(listener);
    }

    public void notify(EventType type, String message) {
        SystemEvent event = new SystemEvent(type, message);
        
        for (IEventListener listener : listeners) {
            try {
                listener.onEvent(event);
            } catch (Exception e) {
               
                System.err.println("Error notificando a un observador: " + e.getMessage());
            }
        }
    }
}
