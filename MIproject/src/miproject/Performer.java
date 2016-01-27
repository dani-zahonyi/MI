package miproject;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by dobei_000 on 2015.04.04..
 * Az zenei előadót megvalósító osztály
 */
public class Performer {
    private String name;
    private BufferedImage image;
    private ArrayList<Song> songs = new ArrayList<Song>();

    /**
     *
     * @param name      Előadó neve
     * @param image     Előadóhoz tartozó kép
     */
    public Performer(String name, BufferedImage image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void addSong(Song song){
       if(!this.songs.contains(song))
            this.songs.add(song);
    }


}
