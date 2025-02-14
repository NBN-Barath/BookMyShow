import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Shows {
    private LocalTime startTime ; // starting time of the show
    private LocalTime endTime; // ending time of the show
    private LocalDate date; // starting date of the show
    private Screen screen; // store which screen
    private int ticketPrice; // store the ticket price of show
    private HashMap<Character, ArrayList<String>> seatArrangement; // hashmap to store seat arrangement
    private int totalSeats; // total no of seat



    public Shows(LocalTime startTime, LocalTime endTime, LocalDate date, Screen screen, int ticketPrice, HashMap<Character,ArrayList<String>> seatArrangement) // constructor
    {
        this.startTime=startTime;
        this.endTime=endTime;
        this.date=date;
        this.screen=screen;
        this.ticketPrice = ticketPrice;
        this.seatArrangement=seatArrangement;
        this.totalSeats=screen.totalNoOfSeat;
    }


    //getter and setter
    public LocalDate getDate() {
        return date;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public Screen getScreen() {
        return screen;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public HashMap<Character, ArrayList<String>> getSeatArrangement() {
        return seatArrangement;
    }

    public void setSeatArrangement(HashMap<Character, ArrayList<String>> seatArrangement) {
        this.seatArrangement = seatArrangement;
    }
}

