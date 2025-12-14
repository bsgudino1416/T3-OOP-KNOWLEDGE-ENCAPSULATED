package ec.edu.espe.petshopinventorycontrol.model;

import org.bson.Document;

public class Client {

    private final String name;
    private final String idType;
    private final String identification;
    private final String phone;
    private final String address;
    private final String city;
    private final String attendedBy;

    public Client(String name, String idType, String identification,
                  String phone, String address, String city, String attendedBy) {
        this.name = name;
        this.idType = idType;
        this.identification = identification;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.attendedBy = attendedBy;
    }

    public Document toDocument() {
        return new Document()
                .append("name", name)
                .append("idType", idType)
                .append("identification", identification)
                .append("phone", phone)
                .append("address", address)
                .append("city", city)
                .append("attendedBy", attendedBy)
                .append("date", new java.util.Date());
    }
}

