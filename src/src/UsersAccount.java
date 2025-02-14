import java.util.ArrayList;

public class UsersAccount extends Accounts {
    String locationOfUser; // store user location
    ArrayList<Tickets> ticket = new ArrayList<>(); // array list of ticket


    public UsersAccount(String name,String id,String password,String phone_number,String email_id,String location){ // constructor
        super(name,id,password,phone_number,email_id);
        this.locationOfUser = location;
    }

    // getter and setter

    public String getLocationOfUser() {
        return locationOfUser;
    }

    public ArrayList<Tickets> getTicket() {
        return ticket;
    }

    public void setLocationOfUser(String locationOfUser) {
        this.locationOfUser = locationOfUser;
    }
    @Override
    public String getId(){
        return super.id;
    }

    @Override
    public String getName(){
        return super.name;
    }

    @Override
    public String getPassword(){
        return super.password;
    }
}
