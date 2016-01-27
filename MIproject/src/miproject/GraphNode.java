package miproject;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by dobei_000 on 2015.03.22..
 * A háló node-jainak nem specifikált őse
 */
public class GraphNode implements Comparable{

    protected String name;
    protected double radius;
    protected Coordinate coord;
    // szomszéd - távolság
    private TreeMap<Double, GraphNode> neighbors;

    /**
     *
     * @param name      A node neve
     * @param radius    A node sugara
     * @param coord     A node kordinátája
     */
    public GraphNode(String name, double radius, Coordinate coord) {
        this.name = name;
        this.radius = radius;
        this.coord = coord;
        this.neighbors = new TreeMap<Double, GraphNode>();
    }

    public GraphNode(GraphNode node){
        this.name = node.getName();
        this.radius = node.getRadius();
        this.coord = node.getCoord();
        this.neighbors = node.getNeighbors();
    }

    public Coordinate getCoord() {
        return coord;
    }

    public TreeMap<Double, GraphNode> getNeighbors() {
        return neighbors;
    }


    public String getName() {
        return name;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public int compareTo(Object o) {
        if(this.radius == ((GraphNode)o).radius){
            return 0;
        }
        else if(this.radius > ((GraphNode)o).radius) {
            return 1;
        }else return -1;
    }


    /**
     * Új szomszéd hozzáadása
     * @param c     A somszéd node
     * @return      Sikerült hozzáadni, vagy nem
     */
    public boolean addNeighbors(GraphNode c){
        // ha az útvonal még nem szerepel
        // vagy ha az útvonal kezdő, vagy végpontja megegyezik a kategória példányával
        if(this.neighbors.isEmpty() || !(this.neighbors.containsValue(c))){
            this.neighbors.put( this.coord.distanceFrom(c.getCoord()), c);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Eltávolítja a megadott szomszédot
     * @param c     Eltávolítandó szomszéd
     * @return      Sikerült-e vagy sem
     */
    public boolean removeNeighbors(GraphNode c){
        if((!this.neighbors.isEmpty()) && (this.neighbors.containsKey(c))){
            this.neighbors.remove(c);
            return true;
        }else{
            return false;
        }
    }


    
}
