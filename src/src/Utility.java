import java.util.ArrayList;
import java.util.HashMap;

public interface Utility {
     static HashMap<Character, ArrayList<String>> generateSeatingPatterns(int noOfSeats, String screenGrid) {
        int remainingSeats = noOfSeats;
        String[] splitGrid = screenGrid.split("\\*");

        // Calculate the total seats required per row
        int seatsPerRow = 0;
        for (String grid : splitGrid) {
            int temp = Integer.parseInt(grid);
            seatsPerRow += temp;
        }
        // Check if the total number of seats is divisible by seatsPerRow
        if (remainingSeats % seatsPerRow != 0) {
            System.out.println("Invalid Grid");
            return null;
        }

        // Create the seating arrangement
        var seatArrangement = new HashMap<Character, ArrayList<String>>();
        char ch = 'A';

        while (remainingSeats > 0) {
            ArrayList<String> rowSeats = new ArrayList<>();
            int n = 1;

            // Handle lowercase letters after Z
            if ((int) ch > 90 && (int) ch < 97) {
                ch = 'a';
            }

            for (int i = 0; i < splitGrid.length; i++) {
                int seatsInBlock = Integer.parseInt(splitGrid[i]);

                for (int j = 0; j < seatsInBlock; j++) {
                    rowSeats.add(String.format("[%d]", n));
                    n++;
                }

                // Add a separator between blocks, except for the last block
                if (i < splitGrid.length - 1) { // Fix the condition here
                    rowSeats.add(" <==> ");
                }
            }

            // Add the row to the seating arrangement
            seatArrangement.put(ch, rowSeats);
            ch++;

            remainingSeats -= seatsPerRow;
        }

        return seatArrangement;
    }
}
