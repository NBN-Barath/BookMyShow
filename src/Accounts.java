public class Accounts {
    private String name; // store name
    private String id; // store id
    private String password; // store password
    private String phone_number; // store phone numer
    private String email_id; // store email id


    public Accounts(String name, String id, String password, String phoneNumber, String emailId) { // constructor
        this.name=name;
        this.id=id;
        this.password=password;
        this.phone_number = phoneNumber;
        this.email_id = emailId;
    }

    // getter and setter
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

}
