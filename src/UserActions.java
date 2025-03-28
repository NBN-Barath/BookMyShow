import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class UserActions implements UserActionsInterface {
    static Scanner in = new Scanner(System.in);

    @Override
    // Register method
    public void registerUser(ArrayList<Accounts> accountsArrayList, Scanner scanner) {
        System.out.print("Enter the new User Name: ");
        String registeringUserName = scanner.next();
        System.out.print("Enter the new User Id: ");
        String registeringUserId = scanner.next();

        // Check if the User ID already exists
        for (Accounts account : accountsArrayList) {
            if (account.getId().equals(registeringUserId)) {
                System.out.println("User Id already exists... Try another User ID.");
                return;
            }
        }

        // Collecting additional user information
        System.out.print("Enter User PIN: ");
        String registerUserPin = scanner.next();
        System.out.print("Enter your Email ID: ");
        String registerUserEmailId = scanner.next();
        System.out.print("Enter your Phone Number: ");
        String registerUserPhoneNumber = scanner.next();
        System.out.println("Enter the Location of the User:");
        String registerUserLocation = scanner.next();

        // Verification process
        System.out.println("User needs to verify:");
        System.out.println("1. Email ID\n2. Phone Number");
        boolean verificationSuccess = false;
        while (!verificationSuccess) {
            System.out.print("Choose your verification method (1 or 2): ");
            int verificationChoice = scanner.nextInt();
            int otp = VerificationClass.otpGenerator();

            if (verificationChoice == 1) {
                System.out.println("The OTP is sent to the Email ID.");
            } else if (verificationChoice == 2) {
                System.out.println("The OTP is sent to the Phone Number.");
            } else {
                System.out.println("Enter a valid choice.");
                continue;
            }

            System.out.println("The OTP to complete the registration is " + otp);
            System.out.print("Enter the OTP: ");
            int enteredOTP = scanner.nextInt();
            if (otp == enteredOTP) {
                System.out.println("Verification Complete");
                verificationSuccess = true;
            } else {
                System.out.println("Invalid OTP. Please try again.");
            }
        }

        // Creating and adding the new user account
        UsersAccount usersAccount = new UsersAccount(registeringUserName, registeringUserId, registerUserPin, registerUserPhoneNumber, registerUserEmailId, registerUserLocation);
        accountsArrayList.add(usersAccount);
        System.out.println("User successfully registered!");
    }
    @Override
    public void availableMovies(UsersAccount currentUser, LocalDate today) { // method to see available Movies
        ArrayList<Movies> movies = new ArrayList<>(); // new array list to store movies
        System.out.println("-------------------------------");

        LocalDate currentDate = today; // Use the provided date
        String location = currentUser.getLocationOfUser(); // Get user location
        System.out.println("Movies Currently Available in Your Location: " + location);
        boolean movieFound = false; // variable to know movie is found

        HashMap<String, ArrayList<Movies>> movieDetails = new HashMap<>(); // Store movies grouped by name

        // Check and store movies based on location and date
        for (String movieName : BMS.getMoviesHashMap().keySet()) { // Check movies based on location and date
            ArrayList<Movies> movieList = BMS.getMoviesHashMap().get(movieName);

            for (Movies movie : movieList) {
                if (movie.getLocationOfTheater().equalsIgnoreCase(location) && currentDate.equals(movie.getStartingDate())) {
                    movieFound = true;
                    movieDetails.putIfAbsent(movie.getNameOfMovie(), new ArrayList<>());
                    movieDetails.get(movie.getNameOfMovie()).add(movie);
                }
            }
        }

        // Display movies with their respective theaters, screens, and showtimes
        if (movieFound) {
            for (String movieName : movieDetails.keySet()) {
                System.out.println("Movie: " + movieName);

                for (Movies movie : movieDetails.get(movieName)) {
                    System.out.println("  Theater: " + movie.getTheater().getTheaterName());
                    System.out.println("  Screen: " + movie.getShows().getScreen().getNameOfScreen());
                    System.out.println("  Show Time: " + movie.getShows().getStartTime());
                    System.out.println("-------------------------------");
                }
            }
        } else {
            // If no movies found initially
            System.out.println("-------------------------------");
            System.out.println("No movies available in your location today.");
            System.out.println("Would you like to change the (Date or Location)? (Y/N):");
           // scanner.nextLine();  // Consume the leftover newline
            String choice = in.next(); // Use nextLine() to avoid input issues

            if (choice.equalsIgnoreCase("Y")) {
                LocalDate updatedDate = changeLocationOrDate(currentUser, today);
                if (updatedDate != null) {
                    availableMovies(currentUser, updatedDate);
                    return;
                }
            }

        }

        System.out.println("Enter the Movie name to book:");
        String currentMovie = in.next();

        for (Movies movie : BMS.getMoviesHashMap().getOrDefault(currentMovie, new ArrayList<>())) {
            if (movie.getLocationOfTheater().equals(currentUser.getLocationOfUser()) && movie.getStartingDate().isEqual(currentDate)) {
                movies.add(movie);
            }
        }
        bookTicket(currentUser, movies);
    }

    @Override
    public void bookTicket(UsersAccount user, ArrayList<Movies> movies) {
        HashMap<String, HashSet<Shows>> theatreShows = new HashMap<>(); // new hash map

        // group shows by theatre
        for (Movies movie : movies) { // iterate movies
            if (!theatreShows.containsKey(movie.getTheater().getTheaterName())) { // check the show contains in theater
                theatreShows.put(movie.getTheater().getTheaterName(), new HashSet<>()); // put the theater in theater show
            }
            theatreShows.get(movie.getTheater().getTheaterName()).add(movie.getShows()); // getting values
        }

        // display available theatres and shows
        System.out.println("Available Theatres:");
        for (String theatre : theatreShows.keySet()) {
            System.out.println("Theatre: " + theatre);
            for (Shows show : theatreShows.get(theatre)) {
                System.out.println("Show Time: " + show.getStartTime());
            }
        }

        // select theatre
        String theatreName;
        while (true) {
            System.out.println("Enter theatre name (or 0 to exit):");
            theatreName = in.next();
            if (theatreName.equals("0")) {
                System.out.println("Exiting...");
                return;
            }
            if (theatreShows.containsKey(theatreName)) {
                break;
            }
            System.out.println("Invalid theatre. Try again.");
        }

        // select show time
        LocalTime showTime;
        while (true) {
            System.out.println("Enter show time:");
            try {
                showTime = LocalTime.parse(in.next(), BMS.getTimeFormatter());
                break;
            } catch (Exception e) {
                System.out.println("Invalid time format. Try again.");
            }
        }

        // find selected show
        Shows selectedShow = null;
        for (Shows show : theatreShows.get(theatreName)) {
            if (show.getStartTime().equals(showTime)) {
                selectedShow = show;
                break;
            }
        }
        // check the selected show
        if (selectedShow == null) {
            System.out.println("Show not available at this time.");
            return;
        }

        // display seat details
        System.out.println("Screen: " + selectedShow.getScreen().getNameOfScreen());
        System.out.println("Available Seats: " + selectedShow.getScreen().getTotalNoOfSeat());
        for (var entry : selectedShow.getSeatArrangement().entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        // select seats
        System.out.println("Enter number of seats:");
        int seatCount = Integer.parseInt(in.next());
        int price = selectedShow.getTicketPrice() * seatCount;
        ArrayList<String> bookedSeats = seatSelection( selectedShow,seatCount);

        if (bookedSeats != null) {
            // Create and add ticket
            Tickets ticket = new Tickets();
            ticket.setNameOfUser(user.getName());
            ticket.setLocationOfTheater(movies.get(0).getLocationOfTheater());
            ticket.setNameOfMovie(movies.get(0).getNameOfMovie());
            ticket.setNameOfScreen(selectedShow.getScreen().getNameOfScreen());
            ticket.setNameOfTheater(theatreName);
            ticket.setStartingTime(selectedShow.getStartTime());
            ticket.setTicketPrice(price);
            ticket.setTicketsBooked(bookedSeats);

            user.getTicket().add(ticket);
            System.out.println("Booking confirmed! Enjoy your movie.");
        } else {
            System.out.println("Booking failed.");
        }
    }

    @Override
    public void viewTickets(UsersAccount user) {//method for view the booked tickets
        if (user.getTicket().isEmpty()) {//if ticket of user is empty
            System.out.println("No Tickets booked !");
            return;
        }
        ArrayList<Tickets> ticket = user.getTicket();//get the ticket object for the current user
        for (Tickets tickets : ticket) // go through the arrayList
        {
            System.out.println("**********************************************\n");
            System.out.println("Theatre Name     : " + tickets.getNameOfTheater()); // print  theatre name
            System.out.println("Theatre Location : " + tickets.getLocationOfTheater()); // print  theatre location
            System.out.println("Movie Name       : " + tickets.getNameOfMovie()); // print movie name
            System.out.println("Screen Name      : " + tickets.getNameOfScreen());// print  screen name
            System.out.println("Show Time        : " + tickets.getStartingTime());// print  show time
            System.out.println("Booked Seats     : " + tickets.getTicketsBooked()); // print  booked seats
            System.out.println("Price            : " + tickets.getTicketPrice());// print  total price
            System.out.println("**********************************************\n");
        }
    }
    @Override
    public LocalDate changeLocationOrDate(UsersAccount user, LocalDate today) {//method to change the location and date.
        //Scanner sc = new Scanner(System.in);//Scanner object to get the object.
        System.out.println(" What would you want to change \n 1.Location \n 2.Date \n 3. Exit \n Enter your choice :");
        int choice = in.nextInt();//choice variable .
        switch (choice) {//switch case for the change location or date.
            case 1:
                //case for changing the location
                System.out.println("Your Location :" + user.getLocationOfUser());
                System.out.println("Available Locations:");
                var availableLocations = new HashSet<>();//hashset for the locations
                for (Theater theatre : BMS.getTheaterHashMap().values()) {//for loop for getting the theatre object from the theatrehashmap.
                    availableLocations.add(theatre.getTheaterLocation());//adding the locations on the available locations
                }
                for (var location : availableLocations) {//iterating the available location and storing on the location.
                    System.out.println("=>" + location);//printing the locations
                }
                // getting the locations
                System.out.println("Enter your new Location :");
                String newLocation = in.next();
                if (availableLocations.contains(newLocation)) {//if newlocation is containing in the locations
                    user.setLocationOfUser(newLocation);//setting the newlocation to the user
                    System.out.println("Location changed Successfully to " + newLocation);//printing the location after change
                    LocalDate todayNow =LocalDate.now();
                    String formattedDate = todayNow.format(BMS.getDateFormatter());
                    LocalDate date = LocalDate.parse(formattedDate, BMS.getDateFormatter());
                    return date;

                } else {//if location is not available
                    System.out.println("Location not valid !");
                }
                break;
            case 2://case for the changing the date.
                //getting the date with th perfect date.

                while (true) {
                    System.out.println("Enter your new date");
                    try {
                        LocalDate newDate = LocalDate.parse(in.nextLine(), BMS.getDateFormatter());//getting local date
                        if (newDate.isAfter(today) || newDate.isEqual(today)) { // check the date is not in past
                            System.out.println("Date changed successfully to :" + newDate);
                            return newDate;//returning the new date
                        }
                    } catch (Exception e) {//catch block
                        System.out.println("please enter the valid date !");
                        continue;
                    }
                    break;//breaks the while
                }
                break;//break for case 2.
            case 3://case for exiting .
                System.out.println("Exiting...");
                return null;//if not available.return null.
            default://if choice is invalid
                System.out.println("Invalid choice !");
        }
        return null;//return statement for method
    }
    @Override
    public ArrayList<String> seatSelection(Shows show, int totalSeatsToBook) { // method to seect seat
        Scanner sc = new Scanner(System.in);
        HashMap<Character, ArrayList<String>> seatArrangementCopy = new HashMap<>(); // new hash map

        // Copy seat arrangement
        for (var entry : show.getSeatArrangement().entrySet()) {
            seatArrangementCopy.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }

        ArrayList<String> selectedSeats = new ArrayList<>(); // new array list
        String[] gridParts = show.getScreen().getGrid().split("\\*");

        // Convert grid into section sizes
        int firstBlockSize = Integer.parseInt(gridParts[0]); // first section
        int secondBlockSize = Integer.parseInt(gridParts[1]); // second section
        int thirdBlockSize = Integer.parseInt(gridParts[2]); // third section

        int totalSeats = firstBlockSize + secondBlockSize + thirdBlockSize; // total seats ignoring gaps
        System.out.println("Total seats: " + totalSeats); // debugging total seats

        while (totalSeatsToBook > 0) { // check the total seat
            System.out.println("Enter seat number (e.g., A1, B2):");
            String seatNumber = sc.next();

            if (seatNumber.length() < 2) { // check valadity
                System.out.println("Invalid seat format! Try again.");
                continue;
            }

            char seatRow = seatNumber.charAt(0); // take the row
            int seatColumn; // variable to column

            try {
                seatColumn = Integer.parseInt(seatNumber.substring(1)); // take the column
            } catch (NumberFormatException e) { // catch block
                System.out.println("Invalid seat number! Try again.");
                continue;
            }

            if (!seatArrangementCopy.containsKey(seatRow) || seatColumn <= 0 || seatColumn > totalSeats) { // check the condition
                System.out.println("Invalid seat selection! Try again.");
                continue;
            }

            List<String> seatRowList = seatArrangementCopy.get(seatRow); // duplicate arraylist

            // Calculate seat position for the section
            int adjustedColumn ; // adjustment in column
            if (seatColumn <= firstBlockSize) {
                adjustedColumn = seatColumn - 1; // First block, normal index
                //System.out.println("First Block: Adjusted Column for " + seatColumn + " is " + adjustedColumn);
            } else if (seatColumn <= firstBlockSize + secondBlockSize) {
                adjustedColumn = seatColumn ; // Second block, adjust index
               // System.out.println("Second Block: Adjusted Column for " + seatColumn + " is " + adjustedColumn);
            } else {
                adjustedColumn = seatColumn +1 ; // Third block, adjust index
               // System.out.println("Third Block: Adjusted Column for " + seatColumn + " is " + adjustedColumn);
            }

            if (adjustedColumn >= seatRowList.size()) { // check size
                System.out.println("Invalid seat selection! Try again.");
                continue;
            }

            if (seatRowList.get(adjustedColumn).equals("[X]")) { // check if it is booked
                System.out.println("Seat already booked! Choose another.");
                continue;
            }

            seatRowList.set(adjustedColumn, "[X]"); // Mark seat as booked
            selectedSeats.add(seatNumber); // add in selected seat
            totalSeatsToBook--;

            // Display updated seating
            for (var entry : seatArrangementCopy.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
        }

        System.out.println("Confirm booking? [1 = Yes / 0 = No]");
        int choice = sc.nextInt();

        if (choice == 1) {
            show.setSeatArrangement(seatArrangementCopy); // set seating arrangement
            System.out.println("Booking successful!");
            return selectedSeats; // return the seat
        } else {
            System.out.println("Booking canceled.");
            return null;
        }
    }

}