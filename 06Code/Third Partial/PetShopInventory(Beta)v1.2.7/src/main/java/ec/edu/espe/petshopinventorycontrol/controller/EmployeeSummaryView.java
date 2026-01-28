/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espe.petshopinventorycontrol.controller;

/**
 *
 * @author Steven Loza @ESPE
 */
public interface EmployeeSummaryView {
    void refreshTotals();

    public void addProductToTable(String animales_de_granja, String cerdo, String comida, String balanceado, String text, int libras, double foodPrice);

    public void setVisible(boolean b);
}
