package ec.edu.espe.petshopinventorycontrol.model;

public class PersonalRecord {

    private String id;
    private String ci;
    private String name;
    private String post;
    private String schedule;
    private String day;
    private String address;
    private String state;
    private String dateIncorporated;

    public PersonalRecord() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDateIncorporated() {
        return dateIncorporated;
    }

    public void setDateIncorporated(String dateIncorporated) {
        this.dateIncorporated = dateIncorporated;
    }
}
