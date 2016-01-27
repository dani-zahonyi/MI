package miproject;

import java.awt.image.BufferedImage;

/**
 * Created by dobei_000 on 2015.04.04..
 * Egy zenei számot megvalósító osztály
 */
public final class Song {
    private Performer performer;      // előadó
    private String title;          // cím
    private MusicalGenre genre;          // műfaj
    private String albumTitel;

    /**
     *
     * @param performer     Előadó
     * @param title         Cím
     * @param genre         Műfaj
     * @param albumTitel    Album címe
     */
    public Song(Performer performer, String title, MusicalGenre genre, String albumTitel) {
        this.performer = performer;
        this.title = title;
        this.genre = genre;
        this.albumTitel = albumTitel;
    }

    public Performer getPerformer() {
        return performer;
    }

    public String getTitle() {
        return title;
    }

    public MusicalGenre getGenre() {
        return genre;
    }

    public String getAlbumTitel() {
        return albumTitel;
    }
}
