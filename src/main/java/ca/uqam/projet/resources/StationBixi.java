package ca.uqam.projet.resources;

public class StationBixi {

    private int id;
    private String name;
    private String terminalName;
    private int nbBikes;
    private int nbEmptyDocks;
    private double lon, lat;

    public StationBixi(int id, String name, String terminalName, int nbBikes, int nbEmptyDocks, double lon, double lat) {
        this.id = id;
        this.name = name;
        this.terminalName = terminalName;
        this.nbBikes = nbBikes;
        this.nbEmptyDocks = nbEmptyDocks;
        this.lon = lon;
        this.lat = lat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }

    public int getNbBikes() {
        return nbBikes;
    }

    public void setNbBikes(int nbBikes) {
        this.nbBikes = nbBikes;
    }

    public int getNbEmptyDocks() {
        return nbEmptyDocks;
    }

    public void setNbEmptyDocks(int nbEmptyDocks) {
        this.nbEmptyDocks = nbEmptyDocks;
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
