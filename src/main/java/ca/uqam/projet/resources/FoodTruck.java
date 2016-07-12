package ca.uqam.projet.resources;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class FoodTruck {

    public FoodTruck() {
        this.id = null;
        this.name = null;
        this.locations = new LinkedList<>();
        this.dirty = false;
    }

    public FoodTruck(String id, String name, List<Location> locations) {
        this.id = id;
        this.name = name;
        this.locations = locations;
        this.dirty = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        dirty = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        dirty = true;
    }

    public boolean isDirty() {
        return dirty;
    }

    public List<Location> getLocations() {
        return locations;
    }

    private String id;
    private String name;
    private List<Location> locations;
    private boolean dirty;

    public static class Location {

        private Date date;
        private String timeStart;
        private String timeEnd;
        private String locationName;
        private Coordinates coord;

        public Location(Date date, String timeStart, String timeEnd, String locationName, Coordinates coord) {
            this.date = date;
            this.timeStart = timeStart;
            this.timeEnd = timeEnd;
            this.locationName = locationName;
            this.coord = coord;
        }

        public Coordinates getCoord() {
            return this.coord;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getTimeStart() {
            return timeStart;
        }

        public void setTimeStart(String timeStart) {
            this.timeStart = timeStart;
        }

        public String getTimeEnd() {
            return timeEnd;
        }

        public void setTimeEnd(String timeEnd) {
            this.timeEnd = timeEnd;
        }

        public String getLocationName() {
            return locationName;
        }

        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }
    }

    public static class Coordinates {

        public Coordinates(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        private double x;
        private double y;
    }
}
