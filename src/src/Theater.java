import java.util.HashMap;

public class Theater {
    String theaterName; // store theater name
    String theaterLocation; // store theater Location
    private HashMap<String, Screen> screenMap; // store screen ans grid hash map

    public Theater (String name,String location,HashMap<String,Screen> screenMap){ // constructor
        this.theaterName=name;
        this.theaterLocation=location;
        this.screenMap=screenMap;
    }
    //getter and setter

    public String getTheaterLocation() {
        return theaterLocation;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public HashMap<String, Screen> getScreenMap() {
        return screenMap;
    }
}
