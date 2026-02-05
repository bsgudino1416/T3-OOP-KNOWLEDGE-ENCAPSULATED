package ec.edu.espe.petshopinventorycontrol.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class BillIdGenerator {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("ddMMyy");

    private String lastDate = "";
    private int sequence = 1;

    public String nextId() {
        String today = LocalDate.now().format(DATE_FORMAT);
        if (!today.equals(lastDate)) {
            lastDate = today;
            sequence = 1;
        }
        String id = String.format("%s-%03d", today, sequence);
        sequence++;
        return id;
    }
}
