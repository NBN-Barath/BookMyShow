import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class AdminActions implements AdminActionsInterface {
    @Override
    public void addAdmin(ArrayList<Accounts> accountsArrayList, Scanner scanner) {
        System.out.print("Enter the new Admin Name: ");
        String registeringUserName = scanner.next();
        System.out.print("Enter the new Admin Id: ");
        String registeringUserId = scanner.next();

        // Check if the User ID is already exists
        for (Accounts account : accountsArrayList)
        {
            if (account.getId().equals(registeringUserId)) {
                System.out.println("User Id already exists... Try another User ID.");
                return; // Exit the method
            }
        }

        // register the new admin
        System.out.print("Enter Admin PIN: ");
        String registerUserPin = scanner.next();
        System.out.print("Enter new Admin Email ID: ");
        String registerUserEmailId = scanner.next();
        System.out.print("Enter new Admin  Phone Number: ");
        String registerUserPhoneNumber = scanner.next();

        // Create the new user account
        AdminAccount usersAccount = new AdminAccount(registeringUserName, registeringUserId, registerUserPin, registerUserPhoneNumber, registerUserEmailId);

        // Add the new account to the list
        accountsArrayList.add(usersAccount);
        System.out.println("Admin successfully registered!");

}
    @Override
    public void addMovies(HashMap<String, Theater> theaterHashMap, Scanner scanner) {
        System.out.println("Enter the movie name:");
        String movieName = scanner.next().trim();

        System.out.println("Enter the location for the movie:");
        String locationOfTheater = scanner.next().trim();

        // Check if theater exists in the given location
        Theater theater = null;
        for (String theaterKey : theaterHashMap.keySet()) {
            Theater currentTheater = theaterHashMap.get(theaterKey);
            if (currentTheater.getTheaterLocation().equalsIgnoreCase(locationOfTheater)) {
                theater = currentTheater;
                break;
            }
        }
        if (theater == null) {
            System.out.println("No theater found in your location!");
            return;
        }

        // Get the movie start date
        LocalDate startingDateOfMovie = getValidDate(scanner);
        if (startingDateOfMovie == null) {
            return;
        }

        // Get the movie duration
        System.out.println("Enter the duration of the movie (in minutes):");
        int duration = getPositiveInteger(scanner, "Invalid duration. Please enter a positive number:");
        if (duration <= 0){
            return;
        }

        // Get the ticket price
        System.out.println("Enter the ticket price:");
        int ticketPrice = getPositiveInteger(scanner, "Invalid price. Please enter a positive number:");
        if (ticketPrice <= 0) {
            return;
        }

        // Select a screen
        System.out.println("Available screens:");
        for (String screenName : theater.getScreenMap().keySet()) {
            System.out.println(screenName);
        }
        System.out.println("Enter the screen name:");
        String screenName = scanner.next().trim();
        Screen screen = theater.getScreenMap().get(screenName);
        if (screen == null) {
            System.out.println("Invalid screen name!");
            return;
        }

        // Enter the show start time
        //System.out.println("Enter the start time (HH:mm):");
        LocalTime startTime = getValidTime(scanner);
        if (startTime == null){
            return;
        }

        // Check for overlapping shows
        LocalTime endTime = startTime.plusMinutes(duration + 30); // Add buffer time
        boolean overlaps = false;
        for (Shows show : screen.getRunningShows()) {
            if (startingDateOfMovie.isEqual(show.getDate()) &&
                    !(endTime.isBefore(show.getStartTime()) || startTime.isAfter(show.getEndTime()))) {
                overlaps = true;
                break;
            }
        }
        if (overlaps) {
            System.out.println("Show overlaps with an existing one!");
            return;
        }

        // Add the movie and show
        HashMap<Character, ArrayList<String>> duplicateSeatingArrangement = new HashMap<>();
        for (Character key : screen.getSeatingArrangement().keySet()) {
            ArrayList<String> value = screen.getSeatingArrangement().get(key);
            duplicateSeatingArrangement.put(key, new ArrayList<>(value));
        }

        Shows show = new Shows(startTime, endTime, startingDateOfMovie, screen, ticketPrice, duplicateSeatingArrangement);
        screen.getRunningShows().add(show);

        Movies movie = new Movies(movieName, locationOfTheater, startingDateOfMovie, duration, theater, screen, show);
        if (!BMS.getMoviesHashMap().containsKey(movieName)) {
            BMS.getMoviesHashMap().put(movieName, new ArrayList<>());
        }
        BMS.getMoviesHashMap().get(movieName).add(movie);

        System.out.println("Movie added successfully!");
    }
    @Override
    public LocalDate getValidDate(Scanner scanner) {
        // Getting date
        System.out.println("Enter the date (dd mm yyyy):");
        try {
            return LocalDate.parse(scanner.next(), BMS.getDateFormatter());
        } catch (Exception e) {
            System.out.println("Invalid date format!");
            scanner.next(); // Clear invalid input
            return null;
        }
    }
    @Override
    public LocalTime getValidTime(Scanner scanner) { // getting time
        System.out.println("Enter the time (HH:mm):");
        try {
            String timeInput = scanner.next();
            return LocalTime.parse(timeInput, BMS.getTimeFormatter());
        } catch (Exception e) {
            System.out.println("Invalid time format!");
            return null;
        }
    }
    @Override
    public int getPositiveInteger(Scanner scanner, String errorMessage) {// method to get positive integer
        try {
            int value = scanner.nextInt();
            if (value > 0) return value;
        } catch (Exception ignored) {
        }
        System.out.println(errorMessage);
        scanner.next(); // Clear invalid input
        return -1;
    }

    @Override
    public void viewMovies(HashMap<String, ArrayList<Movies>> moviesHashMap) { // method to view movies
        if (moviesHashMap.isEmpty()){ // check moviehashmap is empty
            System.out.println("No movies available !");
            return ;
        }
        for(var movieKey :moviesHashMap.keySet()) {//loop to iterate all key in hashmap
            var availableMovie = BMS.getMoviesHashMap().get(movieKey); // assign the keyvalue in varable
            for (Movies movies : availableMovie) { // loop to iterate all movies
                // display movie info
                System.out.println("\n Theatre :" + movies.getTheater().getTheaterName());
                System.out.println(" Location :" + movies.getLocationOfTheater());
                System.out.println(" Date :" + movies.getStartingDate().format((BMS.getDateFormatter())));
                System.out.println(" Starting Time :" + movies.getShows().getStartTime().format((BMS.getTimeFormatter())));
                System.out.println(" End Time :" + movies.getShows().getEndTime().format((BMS.getTimeFormatter()))+"\n\n");

            }
        }
    }
    @Override
    public void addTheater(Scanner scanner) { // method to add theater
        try {
            System.out.print("Enter the Theater name: ");
            String name = scanner.next(); // getting theater name

            System.out.print("Enter the Location of the Theater: ");
            String location = scanner.next();// getting theater location

            // Check for duplicate theaters
            for (String temp : BMS.getTheaterHashMap().keySet()) { // iterate all key in movie hash map
                var currentTheater = BMS.getTheaterHashMap().get(temp); // getting current theter
                if (temp.equals(name) && currentTheater.getTheaterLocation().equals(location)) { // check if theter is already exist
                    System.out.println("Theater already exists!");
                    return;
                }
            }

            System.out.print("Enter the number of screens: ");
            int noOfScreen = getValidIntegerInput(scanner, "Number of screens must be a positive integer: "); // getting no of screen
            HashMap<String, Screen> screenHashMap = new HashMap<>(); // new hash map to screen

            while (noOfScreen > 0) { // check the no of screen
                System.out.print("Enter the name of the screen: ");
                String screenName = scanner.next();

                if (screenHashMap.containsKey(screenName)) { // check the screen hash map contains screen name
                    System.out.println("Screen with this name already exists! Please use a different name.");
                    continue;
                }

                System.out.print("Enter the number of seats: ");
                int noOfSeats = getValidIntegerInput(scanner, "Number of seats must be a positive integer: "); // getting input

                Utility utility = new Utility();
                System.out.print("Enter the grid: ");
                String screenGrid = scanner.next(); // getting grid
                var grid =utility.generateSeatingPatterns(noOfSeats, screenGrid); // store the seating patten
                if (grid == null) { // check it is null
                    System.out.println("Invalid grid! Please re-enter the screen details.");
                    continue;
                }

                Screen screen = new Screen(screenName, noOfSeats, grid, screenGrid); // constructor to create the screen
                screenHashMap.put(screenName, screen); //setting screen in hash map
                noOfScreen--;
            }

            Theater theater = new Theater(name, location, screenHashMap);// constructor to create the theater
            BMS.getTheaterHashMap().put(name, theater);//setting theater in hash map
            System.out.println("Theater added successfully!");

        } catch (Exception e) {
            System.out.println("An unexpected error occurred. Please try again.");
        }
    }
    @Override
    public int getValidIntegerInput(Scanner scanner, String errorMessage) { // method to get a valid integer
        while (true) {
            try {
                String input = scanner.next();
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                } else {
                    System.out.println(errorMessage);
                }
            } catch (NumberFormatException e) {
                System.out.println(errorMessage);
            }
        }
    }

    @Override
    public void viewAllTheater() { // method to view all theater
        if (BMS.getTheaterHashMap().isEmpty()){ // check moviehashmap is empty
            System.out.println("No Theater available !");
            return ;
        }
        for (var temp : BMS.getTheaterHashMap().keySet()) { // iterating the keyset in hashmap
            var theater = BMS.getTheaterHashMap().get(temp);// storing the theater

            System.out.println("Theater Name :" + theater.getTheaterName());// display theater name
            System.out.println("Theater Location:" + theater.getTheaterLocation()); // display theater location
            System.out.println("Screens:");//display screen info
            for (var tmp : theater.getScreenMap().entrySet()) { // iterate all enterys
                System.out.println("Name of the Screen :" + tmp.getKey()); // display Screen
                System.out.println("Number of seats available in the screen :" + tmp.getValue().getTotalNoOfSeat()); // display total seat in Screen
                System.out.println("Seat Arrangement of the screen:\n"); // display seating arrangement
                for (var  rowIdentifier : tmp.getValue().getSeatingArrangement().keySet()) {
                    var seatingPattenOfRow = tmp.getValue().getSeatingArrangement().get(rowIdentifier);
                    System.out.print(rowIdentifier + ": {");
                    for (int i = 0; i < seatingPattenOfRow.size(); i++) {// to go for the roe
                        System.out.print(seatingPattenOfRow.get(i));
                        if (i < seatingPattenOfRow.size() - 1) {
                            System.out.print(", "); // Add a comma between elements
                        }
                    }
                    System.out.println("}"); // Close the curly braces and move to the next line

                }
            }
        }
    }
}
