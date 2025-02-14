import java.time.LocalDate;

public class Movies {
    private Shows shows; //  variable to store shows
    private Screen screen; //  variable to store screen
    private Theater theater; //  variable to store theater
    private int durationOfMovie; //  variable to store duration of movie
    private String nameOfMovie; //  variable to store name of movie
    private LocalDate startingDate;  //  variable to store starting date
    private String locationOfTheater; //  variable to store location of theater

    public Movies(String nameOfMovie, String locationOfTheater, LocalDate startingDate,int duration,Theater theater,Screen screen,Shows shows){ // constructor
        this.nameOfMovie =nameOfMovie;
        this.locationOfTheater =locationOfTheater;
        this.durationOfMovie =duration;
        this.startingDate =startingDate;
        this.theater =theater;
        this.screen=screen;
        this.shows=shows;
    }

    // getter and setter

    public String getNameOfMovie() {
        return nameOfMovie;
    }

    public Theater getTheater() {
        return theater;
    }

    public String getLocationOfTheater() {
        return locationOfTheater;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public Shows getShows() {
        return shows;
    }
}

