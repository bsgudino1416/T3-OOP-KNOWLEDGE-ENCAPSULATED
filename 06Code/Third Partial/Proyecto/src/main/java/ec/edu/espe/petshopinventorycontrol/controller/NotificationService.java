package ec.edu.espe.petshopinventorycontrol.controller;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    
    private static NotificationService instance;
    private List<ISystemObserver> observers;

    private NotificationService() {
        observers = new ArrayList<>();
    }

    public static NotificationService getInstance() {
        if (instance == null) {
            instance = new NotificationService();
        }
        return instance;
    }

    
    public void addObserver(ISystemObserver observer) {
        observers.add(observer);
    }

    
    public void removeObserver(ISystemObserver observer) {
        observers.remove(observer);
    }

   
    public void notifyAll(String message) {
        for (ISystemObserver observer : observers) {
            observer.update(message);
        }
        System.out.println("[System Log]: " + message); 
    }
}
