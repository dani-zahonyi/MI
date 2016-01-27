package miproject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dobei_000 on 2015.03.22..
 * A felhasználót megvalósító osztály
 */
public class User {
    private String name;

    private HashMap<GraphNode, Double> ownCategories; // kategória, relevancia
    private ArrayList<UserNode> userNodes;

    public User(String name) {
        this.name = name;
    }

    public void getDatasFromDatabase(){
        // kedveslések lekérése az adatbázisból az this.ownCategories-be
    }

    public void setDataToDatebase(String newData){
        // elem hozzáadása az adatbázishoz
    }

    /**
     * Egy User-hez több node is tartozhat, ezek lesznek bekötve a hálóba és ezek
     * kerülnek majd be klaszterhalmazokba
     * @param rel   relevancia
     * @param coordinate    koordináta
     */
    private void createUserNode(double rel, Coordinate coordinate){
        UserNode userNode = new UserNode(this.name, rel, coordinate);
    }


}
