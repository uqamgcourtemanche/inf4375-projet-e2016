package ca.uqam.projet.resources;

public class StationBixi {

    private int id;
    private String name;
    private String terminalName;
    private int nbBikes;
    private int nbEmptyDocks;
    private double x, y;
    
    public StationBixi(int id, String name, String terminalName, int nbBikes, int nbEmptyDocks, double x, double y) {
        this.id = id;
        this.name = name;
        this.terminalName = terminalName;
        this.nbBikes = nbBikes;
        this.nbEmptyDocks = nbEmptyDocks;
        this.x = x;
        this.y = y;
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
    

    
}
