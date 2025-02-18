import java.util.Scanner;

public interface BMS_ActionsInterface {
     void start(); // start method
     void action(Accounts loginAccount,Scanner scanner); // action method
     void adminOption(Scanner scanner); // admin option method
     void userOption(Accounts loginAccount, Scanner scanner); // user option method
}
