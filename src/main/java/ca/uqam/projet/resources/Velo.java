package ca.uqam.projet.resources;

public class Velo {

    private int id;
    private String name;
    private String terminalName;
    private int nbBikes;
    private double x;
    private double y;
    private boolean statut;

    public Velo(int id, String name, String terminalName, int nbBikes, double x, double y, boolean statut) {
        this.id = id;
        this.name = name;
        this.terminalName = terminalName;
        this.nbBikes = nbBikes;
        this.x = x;
        this.y = y;
        this.statut = statut;
    }

    public Velo() {
        this.id = -1;
        this.name = null;
        this.terminalName = null;
        this.nbBikes = -1;
        this.x = Double.NaN;
        this.y = Double.NaN;
        this.statut = false;
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

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

}
