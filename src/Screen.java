import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Screen {
    String nameOfScreen; // name of screen
    int totalNoOfSeat; // total no of seat in the in screen
    int totalOnOfSeatAvaliable;
    private HashSet<Shows> runningShows = new HashSet<>(); //current running show
    HashMap<Character, ArrayList<String>> seatingArrangement= new HashMap<>(); // HashMap to store seating arrangement
    String grid ; // store the user enters gird


    public Screen(String nameOfScreen,int totalNoOfSeat,HashMap<Character,ArrayList<String>> seatingArrangement, String grid){ // constructor for Screen
        this.nameOfScreen=nameOfScreen;
        this.totalNoOfSeat=totalNoOfSeat;
        this.totalOnOfSeatAvaliable =totalNoOfSeat;
        this.seatingArrangement= seatingArrangement;
        this.grid=grid;
    }

    // getter and setter


    public String getNameOfScreen() {
        return nameOfScreen;
    }

    public int getTotalNoOfSeat() {
        return totalNoOfSeat;
    }

    public HashMap<Character, ArrayList<String>> getSeatingArrangement() {
        return seatingArrangement;
    }

    public HashSet<Shows> getRunningShows() {
        return runningShows;
    }

    public String getGrid() {
        return grid;
    }
}
