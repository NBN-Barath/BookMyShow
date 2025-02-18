import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public interface AdminActionsInterface {

    // all method declaration
    void addAdmin(ArrayList<Accounts> accountsArrayList, Scanner scanner);
    void addMovies(HashMap<String, Theater> theaterHashMap, Scanner scanner);
    LocalDate getValidDate(Scanner scanner);
    LocalTime getValidTime(Scanner scanner);
    int getPositiveInteger(Scanner scanner, String errorMessage);
    void viewMovies(HashMap<String, ArrayList<Movies>> moviesHashMap);
    void addTheater(Scanner scanner);
    int getValidIntegerInput(Scanner scanner, String errorMessage);
    void viewAllTheater();
}
