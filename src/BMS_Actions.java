import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class BMS_Actions {

      private static  ArrayList<Accounts> accountsArrayList = BMS.getAccountsArrayList(); // store account from BMS
      private static HashMap<String, ArrayList<Movies>> moviesHashMap = BMS.getMoviesHashMap(); // store movie from BMS
      private static HashMap<String,Theater> theaterHashMap= BMS.getTheaterHashMap(); // store theater from BMS

    public static void start(){ // starting method
        Scanner scanner = new Scanner(System.in); // scanner object
        accountsArrayList.add(new AdminAccount("Admin1","1","1","7339084681","admin1@gmail.com")); // default Admin
        accountsArrayList.add(new UsersAccount("2","2","2","2","2","cbe")); // default user
        while (true){
            System.out.println("Enter the option:");
            System.out.println(" 1.Login\n 2.Register\n3.Exit");
            int loginChoice = scanner.nextInt();
            Accounts loginAccount;
            // ask user login or register
            if (loginChoice == 1){
                loginAccount = CommonAction.login(accountsArrayList,scanner);
                if(loginAccount != null){
                    action(loginAccount,scanner);
                }
                else {
                    System.out.println("No user found \n Register new user \n1.Yes\n2.No");
                    int wantToRegister = scanner.nextInt();
                    if(wantToRegister == 1){
                        UserActions.registerUser(accountsArrayList,scanner);
                    }
                    else {
                        System.out.println("Thank You");
                    }
                }
            } else if(loginChoice == 2) {
                UserActions.registerUser(accountsArrayList,scanner);
            } else if (loginChoice == 3) {
                System.out.println("Thank you to use Book My Show");
                return;
            } else {
                System.out.println("Enter the valid input...");

            }
        }

    }
    public static void action(Accounts loginAccount,Scanner scanner){ // choose action
        if(loginAccount instanceof AdminAccount){
            adminOption(scanner);
        }
        else {
            userOption(loginAccount,scanner);
        }
    }
    public static void adminOption(Scanner scanner){ // action for admin
        while (true){
            System.out.println("1.Add admin \n 2.Add Movie \n 3.View Movie \n 4.Add Theater \n 5.View Theater \n 6.Exit");
            int choice= scanner.nextInt();
            if(choice == 1){
                AdminActions.addAdmin(accountsArrayList,scanner); //add admin
            } else if(choice == 2){
                AdminActions.addMovies(theaterHashMap,scanner); // add movies
            } else if (choice == 3) {
                AdminActions.viewMovies(moviesHashMap); // view movies
            } else if (choice == 4) {
                AdminActions.addTheater(scanner); // add theater
            } else if (choice == 5) {
                AdminActions.viewAllTheater(); // view theater
            } else if (choice == 6){
                System.out.println("logOut from admin");
                return;
            } else {
                System.out.println("Enter the valid input");
            }

        }

    }
    public static void userOption(Accounts loginAccount,Scanner scanner){
        while (true){
            System.out.println("Enter the user option   \n  1. Display Movie \n 2. Change Location /Date \n 3. View Ticket \n 4. Exit");
            int choice = scanner.nextInt();
            if(choice == 1){
                UserActions.availableMovies((UsersAccount) loginAccount, LocalDate.now()); // calling available Movies
            } else if (choice == 2) {
                LocalDate date = UserActions.changeLocationOrDate((UsersAccount) loginAccount, LocalDate.now());
                UserActions.availableMovies((UsersAccount) loginAccount,date);
            }else if (choice == 3) {
                UserActions.viewTickets((UsersAccount) loginAccount);
                return;
            } else if (choice == 4) {
                System.out.println("logOut from admin");
                return;
            }else {
                System.out.println("Enter the valid input");
            }
        }

    }
}

