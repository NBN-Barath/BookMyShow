import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class BMS {
    private static ArrayList<Accounts> accountsArrayList = new ArrayList<>(); // array list to store accounts
    private static HashMap<String,ArrayList<Movies>> moviesHashMap = new HashMap<>();//Hashmap for movie.
    private static HashMap<String,Theater> theaterHashMap=new HashMap<>(); //Hashmap for theater.
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // store the date
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm"); // store the time

    //getter and setter
    public static ArrayList<Accounts> getAccountsArrayList(){return accountsArrayList;}

    public static HashMap<String, ArrayList<Movies>> getMoviesHashMap() {return moviesHashMap;}

    public static HashMap<String, Theater> getTheaterHashMap() {return theaterHashMap;}

    public static DateTimeFormatter getDateFormatter() {
        return dateFormatter;
    }

    public static DateTimeFormatter getTimeFormatter() {
        return timeFormatter;
    }
}

