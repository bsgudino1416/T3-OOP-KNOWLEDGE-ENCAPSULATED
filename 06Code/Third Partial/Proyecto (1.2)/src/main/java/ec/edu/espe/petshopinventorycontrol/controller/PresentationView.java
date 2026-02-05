package ec.edu.espe.petshopinventorycontrol.controller;

public interface PresentationView {
    void setProgressRange(int min, int max);
    void setProgressValue(int value);
    void setStatusText(String text);
    void setStartEnabled(boolean enabled);
    void openLoginAndClose();
}
