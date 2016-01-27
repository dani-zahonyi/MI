package miproject;

import java.util.ArrayList;
import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by dobei_000 on 2015.03.13..
 */
public final class MapParser <T extends GraphNode> {
    private final String FILE_NAME;
    private ArrayList<T> categories;


    public MapParser(String fileName) {
        this.categories = new ArrayList<T>();
        this.FILE_NAME = fileName;
        this.parser();
    }

    public ArrayList<T> getCategories() {
        return categories;
    }


    private void parseCategory(Scanner scanner){
        Double rectangle = null;
        Coordinate coord = new Coordinate();
        String name = null;

        while((scanner.hasNextLine())) {

            String nextString = scanner.next();

            if(nextString.contains("r=")){
                // a kategória sugara (mennyire közkedvelt)
                nextString = nextString.substring("r=\"".length(), nextString.length()-1);
                rectangle = new Double(nextString);

            }else if(nextString.contains("cx=\"")){
                // kategória x koordinátája
                nextString = nextString.substring("cx=\"".length(), nextString.length() - 1);
                coord.setxCoord(new Double(nextString));

            }else if(nextString.contains("class=")){
                // a kategória műfajának neve
                nextString = nextString.substring("class=\"".length(), nextString.length()-1);
                name = new String(nextString);
            }
            else if(nextString.contains("cy=\"")){
                // kategória x koordinátája
                nextString = nextString.substring("cy=\"".length(), nextString.length() - 1);
                coord.setyCoord(new Double(nextString));

            }else if(nextString.contains("/>")){
                // vége az útvonalnak
                if(rectangle != null && name != null){
                    this.categories.add((T) new GraphNode(name, rectangle, coord));

                  //  System.out.println(" -- csomópont létrehozva");
                }
                return;
            }

        }

    }

    private void parsePath(Scanner scanner){
        String sourceName = null;
        String targetName = null;

        while((scanner.hasNextLine())) {

            String nextString = scanner.next();

            if(nextString.contains("class=")){
                // ebben a token-ben a forrás műfaj neve
                // következő token-ben a cél műfaj neve
                nextString = nextString.substring("class=\"".length(), nextString.length());
                sourceName = new String(nextString);
                if(scanner.hasNextLine()){
                    nextString = scanner.next();
                    nextString = nextString.substring(0, nextString.length()-1);
                    targetName = new String(nextString);
                }
            }
            else if(nextString.contains("/>")){
                // vége az útvonalnak
                if(sourceName != null && targetName != null){
                    for(GraphNode c : this.categories){
                        // a kategória neve megyegyezik az útvonal egyik pontjával,
                        // akkor a másik pontját hozzáadjuk a szomszédaihoz
                        if(c.getName().equals(sourceName)){

                            for(GraphNode cat: this.categories){
                                if(cat.getName().equals(targetName)){
                                    c.addNeighbors(cat);
                                    cat.addNeighbors(c);
                                }
                            }

                        }
                    }
                  //  System.out.println(" -- útvonal létrehozva");
                }
                return;
            }

        }
    }

    public void parser(){
        try {
           // String text = new String(this.getFile());
            File file = new File(this.FILE_NAME);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String nextToken = scanner.next();
                // találtunk egy utat
                if (nextToken.equalsIgnoreCase("<path")){
                    this.parsePath(scanner);
                }else if(nextToken.equalsIgnoreCase("<circle")){
                    this.parseCategory(scanner);
                }
            }

            System.out.println("csomópontok: " + this.categories.size());
        }catch (IOException io){
            io.printStackTrace();
        }catch (NoSuchElementException nse) {
            nse.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
