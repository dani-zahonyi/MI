package miproject;

/**
 * Created by dobei_000 on 2015.03.13..
 * Egyszerű osztály 2D-s koordináták definiálására
 */
public final class Coordinate {
    private double xCoord;
    private double yCoord;

    public Coordinate() {}

    /**
     *
     * @param xCoord    X koordináta
     * @param yCoord    Y koordináta
     */
    public Coordinate(double xCoord, double yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public double getxCoord() {
        return xCoord;
    }

    public double getyCoord() {
        return yCoord;
    }

    public void setxCoord(double xCoord) {
        this.xCoord = xCoord;
    }

    public void setyCoord(double yCoord) {
        this.yCoord = yCoord;
    }

    /**
     *  Ez a koordináta és egy megadott célkoordináta távolságát számolja ki
     * @param targetCoord   A célhely koordinátája
     * @return              A távolság
     */
    public double distanceFrom(Coordinate targetCoord){
        return Math.sqrt(Math.pow(targetCoord.getxCoord() - this.xCoord, 2) + Math.pow(targetCoord.getyCoord() - this.yCoord , 2));
    }
}
