package miproject;

import java.awt.geom.GeneralPath;
import java.util.*;

/**
 * Created by dobei_000 on 2015.03.15..
 * A hálót megvalósító osztály
 */
public final class Net<T extends GraphNode> {
    private final int NUM_OF_CLUSTER;

    private volatile ArrayList<T> nodes = new ArrayList<T>();
    private ArrayList<Bug> bugs = new ArrayList<Bug>();


    public Net(ArrayList<T> c) {
        this.nodes = c;
        this.NUM_OF_CLUSTER = ((int) (Math.log(this.nodes.size())))*20;
    }


    public void addNode(T graphNode){
        if(!this.nodes.contains(graphNode))
            this.nodes.add(graphNode);
    }

    public HashMap<GraphNode, ArrayList<GraphNode>> classicCluster(){
        ArrayList<GraphNode> sources = new ArrayList<GraphNode>(getClusterSourcePoints(NUM_OF_CLUSTER));
        // klaszterközéppontok és a hozzájuk tartozó node-ok
        HashMap<GraphNode, ArrayList<GraphNode>> clusters = new HashMap<GraphNode, ArrayList<GraphNode>>();

        for(GraphNode g: sources){
            clusters.put(g, new ArrayList<GraphNode>());
        }

        Bug b;
        // végigmegyünk a node-okon, és mindegyiket beosztjuk valamelyik klaszterhalmazba
        for(GraphNode g: this.nodes){
            if(!sources.contains(g)) {
                b = new Bug(g);
                b.start();
                clusters.get(b.AStar(sources)).add(g);
            }
        }
        return clusters;
    }


    /**
     * Klaszterhalmazokat képző függvény (a választott klaszterközéppontoktól mért távolság alapján)
     * @return   map, aminek kulcsai a klaszterközéppontok,
     *           a hozzá tartozó értékek pedig a klaszterhalmazba tartozó node-ok
     */
    public  HashMap<GraphNode, ArrayList<GraphNode>> heuristicCluster(){
        ArrayList<GraphNode> sources = new ArrayList<GraphNode>(getClusterSourcePoints(NUM_OF_CLUSTER));
        // klaszterközéppontok és a hozzájuk tartozó node-ok
        HashMap<GraphNode, ArrayList<GraphNode>> clusters = new HashMap<GraphNode, ArrayList<GraphNode>>();

        for(GraphNode gn: this.nodes){
            GraphNode nearest = null;
            double minDist = Double.POSITIVE_INFINITY;
            // Minden node-hoz megkeressük a legközelebbi középpontot
            for(GraphNode source: sources){
                double actDist = gn.getCoord().distanceFrom(source.getCoord());
                if(actDist < minDist){
                    minDist = actDist;
                    nearest = source;
                }
            }
            // ha van legközelebbi középpontunk
            if(nearest != null){
                // és a középpontnak már vannak elemei a map-ben,
                // akkor hozzáadjuk az elemekhez az újat
                if(clusters.containsKey(nearest)){
                   clusters.get(nearest).add(gn);
                }else{
                    // különben felvesszük a középpontot az aktuális elemmel
                    ArrayList<GraphNode> a = new ArrayList<GraphNode>();
                    a.add(gn);
                    clusters.put(nearest, a);
                }
            }
        }
        if(clusters.isEmpty()) return null;
        else return clusters;
    }

    /**
     * Kiválasztja a hálóban kévő node-ok közül a legnagyobb sugarú node-okat
     * Az összes node számához logaritmikusan aránylik a középpontok száma
     * @return A klaszterközépontokat tartalmazó lista
     */
    private ArrayList<GraphNode> getClusterSourcePoints(int numOfPoints){
        ArrayList<GraphNode> clusterSources = new ArrayList<GraphNode>(this.nodes);
        if(this.nodes != null)
            Collections.sort(clusterSources, new Comparator<GraphNode>() {
                @Override
                public int compare(GraphNode o1, GraphNode o2) {
                    return o1.compareTo(o2);
                }
            });
             return new ArrayList<GraphNode>(clusterSources.subList((clusterSources.size()-numOfPoints), clusterSources.size()));
    }

    /**
     * A kiválasztott középpontokból elindítja a bogarakat, így a bogarak álltal bejárt node-ok fognak
     * a klaszterhalmazba tartozni (egy node több halmazba is tartozhat)
     * @return Visszaadja a bogarak listáját
     */
    public ArrayList<Bug> createClusters(){
        // a klaszterközéppontok listája
        ArrayList<GraphNode> sources = new ArrayList<GraphNode>(getClusterSourcePoints(NUM_OF_CLUSTER));
        for(GraphNode gn: sources){
            // a max távolság függ a start node sugarának nagyságától
            int distance = ((int) gn.getRadius()) * 12;
            createBug(gn, distance);
        }
        return this.bugs;
    }

    /**
     * Elindít egy bogarat a megadott node-ból
     * @param sourceNode    ebből a node-ból indul a bogár
     * @param maxDistance   a bogár álltal megtehető maximum távolság
     */
    private void createBug(GraphNode sourceNode, int maxDistance){

        this.bugs.add(new Bug(sourceNode, maxDistance));
        this.bugs.get(this.bugs.size()-1).start();

    }


}
