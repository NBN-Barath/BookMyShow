import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public interface UserActionsInterface {
    // all method declaration
    void registerUser(ArrayList<Accounts> accountsArrayList, Scanner scanner);
    void availableMovies(UsersAccount currentUser, LocalDate today);
    void bookTicket(UsersAccount user, ArrayList<Movies> movies);void viewTickets(UsersAccount user);
    LocalDate changeLocationOrDate(UsersAccount user, LocalDate today);
    ArrayList<String> seatSelection(Shows show, int totalSeatsToBook);
}
