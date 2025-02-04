import java.util.ArrayList;
import java.util.Scanner;

public class CommonAction {
    public static Accounts login(ArrayList<Accounts> accountsArrayList, Scanner scanner) // common login to user and admin
    {
        System.out.println("Enter the User Id");
        String id = scanner.next();// get id
        for (Accounts accounts : accountsArrayList) // iterate all account
        {
            if (accounts.getId().equals(id)){ // check id
                    System.out.print("Enter User PIN: ");
                    String userPin = scanner.next();

                    if (accounts.getPassword().equals(userPin)) { // Check admin pin is valid
                        System.out.println("User login successful.");
                        System.out.println("\tUser Name:"+accounts.getName()+"\n\tUser Id:"+accounts.getId());
                        return accounts;// return user when login is successful
                    }
                    else {
                        System.out.println("Entered wrong Id or Password ");
                    }
            }
        }
        return null;
    }
}
