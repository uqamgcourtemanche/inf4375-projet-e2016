package ca.uqam.projet.resources;

public class Velo {

    private String id;
    private double lon;
    private double lat;

    public Velo(String id, double lon, double lat) {
        this.id = id;
        this.lon = lon;
        this.lat = lat;
    }

    public Velo() {
        this.id = null;
        this.lon = Double.NaN;
        this.lat = Double.NaN;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double x) {
        this.lon = x;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double y) {
        this.lat = y;
    }
}
