package ec.edu.espe.petshopinventorycontrol.controller;


public class SessionManager {
    
    
    private static SessionManager instance;
    
    
    private String currentUser;
    private String userRole;

   
    private SessionManager() {
        this.currentUser = "Invitado";
        this.userRole = "Ninguno";
    }

   
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

   
    public void loginUser(String user, String role) {
        this.currentUser = user;
        this.userRole = role;
    }

    public void logout() {
        this.currentUser = "Invitado";
        this.userRole = "Ninguno";
    }

    public String getCurrentUser() {
        return currentUser;
    }
    
    public String getUserRole() {
        return userRole;
    }
}