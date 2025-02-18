import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class BMS_Actions implements BMS_ActionsInterface {

      private static  ArrayList<Accounts> accountsArrayList = BMS.getAccountsArrayList(); // store account from BMS
      private static HashMap<String, ArrayList<Movies>> moviesHashMap = BMS.getMoviesHashMap(); // store movie from BMS
      private static HashMap<String,Theater> theaterHashMap= BMS.getTheaterHashMap(); // store theater from BMS
      CommonAction commonAction = new CommonAction();

    @Override
    public void start(){ // start method
        Scanner scanner = new Scanner(System.in); // scanner object
        accountsArrayList.add(new AdminAccount("Admin1","1","1","7339084681","admin1@gmail.com")); // default Admin
        accountsArrayList.add(new UsersAccount("2","2","2","2","2","cbe")); // default user
        UserActions userActions = new UserActions(); // Object to UserActions
        while (true){
            System.out.println("Enter the option:");
            System.out.println(" 1.Login\n 2.Register\n3.Exit");
            int loginChoice = scanner.nextInt();
            Accounts loginAccount;
            // ask user login or register
            if (loginChoice == 1){
                loginAccount = commonAction.login(accountsArrayList,scanner);
                if(loginAccount != null){
                    action(loginAccount,scanner);
                }
                else { // if no user found
                    System.out.println("No user found \n Register new user \n1.Yes\n2.No");
                    int wantToRegister = scanner.nextInt();
                    if(wantToRegister == 1){
                        userActions.registerUser(accountsArrayList,scanner);
                    }
                    else {
                        System.out.println("Thank You");
                    }
                }
            } else if(loginChoice == 2) {
                userActions.registerUser(accountsArrayList,scanner); //  call registerUser method
            } else if (loginChoice == 3) {
                System.out.println("Thank you to use Book My Show");
                return;
            } else {
                System.out.println("Enter the valid input...");

            }
        }

    }
    @Override
    public void action(Accounts loginAccount,Scanner scanner){ // choose action
        if(loginAccount instanceof AdminAccount){
            adminOption(scanner);
        }
        else {
            userOption(loginAccount,scanner);
        }
    }
    @Override
    public void adminOption(Scanner scanner){ // action for admin
        AdminActions adminActions = new AdminActions();
        while (true){
            System.out.println("1.Add admin \n 2.Add Movie \n 3.View Movie \n 4.Add Theater \n 5.View Theater \n 6.Exit");
            int choice= scanner.nextInt();
            if(choice == 1){
                adminActions.addAdmin(accountsArrayList,scanner); //add admin
            } else if(choice == 2){
                adminActions.addMovies(theaterHashMap,scanner); // add movies
            } else if (choice == 3) {
                adminActions.viewMovies(moviesHashMap); // view movies
            } else if (choice == 4) {
                adminActions.addTheater(scanner); // add theater
            } else if (choice == 5) {
                adminActions.viewAllTheater(); // view theater
            } else if (choice == 6){
                System.out.println("logOut from admin");
                return;
            } else {
                System.out.println("Enter the valid input");
            }

        }

    }
    @Override
    public void userOption(Accounts loginAccount,Scanner scanner){
        UserActions userActions = new UserActions();
        while (true){
            System.out.println("Enter the user option   \n  1. Display Movie \n 2. Change Location /Date \n 3. View Ticket \n 4. Exit");
            int choice = scanner.nextInt();
            if(choice == 1){ // if Display movie
                userActions.availableMovies((UsersAccount) loginAccount, LocalDate.now()); // calling available Movies
            } else if (choice == 2) { // if Change location or date
                LocalDate date = userActions.changeLocationOrDate((UsersAccount) loginAccount, LocalDate.now());
                userActions.availableMovies((UsersAccount) loginAccount,date);
            }else if (choice == 3) { // if view ticket
                userActions.viewTickets((UsersAccount) loginAccount); // method to view ticket
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

