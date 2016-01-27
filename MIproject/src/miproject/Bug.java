package miproject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by dobei_000 on 2015.03.14..
 * "bogár" példány, ami képes bejárni a hálókat és elraktározza a bejárt node-okat és a távolságukat egy megadott távolságon belül
 * Többször nem járja be ugyan azt a node-ot, de ha rövidebb utat talál, akkor felülírja a meglévő bejegyzését
 */
public class Bug extends Thread{
    private final double MAX_DISTANCE;
    // az egyes node-ok és a hozzájuk tartozó távolság
    // alapból a node-ok sugara szerint rendezi
    private TreeMap<GraphNode,Double> nodes = new TreeMap<GraphNode, Double>();

    // node-okhoz rendelt távolság szerinti összehasonlítás
    private final class ValueComparator implements Comparator<GraphNode> {

        Map<GraphNode, Double> base;
        public ValueComparator(Map<GraphNode, Double> base) {
            this.base = base;
        }

        public int compare(GraphNode a, GraphNode b) {
            if (base.get(a) >= base.get(b)) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    /**
     *
     * @param source    A start node, innen indulunk
     * @param maxDistance   Maximum távolság, ameddig a bogár elmegy
     */
    public Bug(GraphNode source, double maxDistance) {
        nodes.put(source, 0.0);
        this.MAX_DISTANCE = maxDistance;
    }

    public Bug(GraphNode source){
        this(source, Double.POSITIVE_INFINITY);
    }

    public TreeMap<GraphNode, Double> getNodes() {
        return nodes;
    }

    /**
     * Elindul a bogár a kezdőpozícióból
     */
    @Override
    public void run() {

        GraphNode c = this.nodes.firstEntry().getKey();
        //this._wander(c);


    }

    /**
     * Rekurzív bejáró függvény
     * @param c     Az a node, ahol éppen vagyunk
     */
  /*  private void _wander(GraphNode c){
        // végigmegyünk a node-ok útjain
        for(GraphNode cat: c.getNeighbors().keySet()){
            // ha az eddig megtett távolság kisebb, mint a megengedett
            this.distance = (double)this.nodes.get(c) + c.getNeighbors().get(cat);
            if(this.distance <= MAX_DISTANCE){
                if(!this.nodes.keySet().contains(cat)) {
                    // hozzáadjuk a listánkhoz, ha még nem jártunk itt
                    this.nodes.put(cat, this.distance);
                    this._wander(cat);
                }else if(this.nodes.get(cat) > this.distance){
                    // ha jártunk már ezen a node-on, de most kisebb költségű úton jutottunk el ide
                    this.nodes.remove(cat);
                    this.nodes.put(cat, this.distance);
                    this._wander(cat);
                }
            }
        }

    }*/

    /**
     * A* algoritmus több célállapot esetén
     * @param goals A cél node-ok
     * @return      A cél node-ok közül a legközelebb lévő
     */
    public GraphNode AStar(ArrayList<GraphNode> goals){
        return this._AStar(null, goals);
    }

    /**
     * A* algoritmus egy cél esetén
     * @param goal  A cél node
     * @return      A cél node távolsága
     */
    public double AStar(GraphNode goal){
        this._AStar(goal, null);
        return this.nodes.lastEntry().getValue();
    }

    /**
     * A* algritmus
     * @param goal      Cél node
     * @param goals     Cél node-ok listája
     * @return          Elsőként megtalált cél node
     */
    private GraphNode _AStar(GraphNode goal, ArrayList<GraphNode> goals){

        // amiket vizsgálnunk kell
        TreeMap< Double, GraphNode> queue = new TreeMap< Double, GraphNode>();

        // ahonnan inulunk
        Map.Entry<GraphNode, Double> source = this.nodes.firstEntry();

        queue.put(source.getValue(), source.getKey());

        // ha nem csak a kezdő node van a bejárt node-ok között, akkor azokat ki kell szedni
        if(this.nodes.size() > 0){
            this.nodes.clear();
        }

        boolean found = false;
        double distance;
        Map.Entry<Double, GraphNode> current;

        while((!queue.isEmpty()) && (!found)){
            // ez a node van a legközelebb
            current = queue.pollFirstEntry();
            // a node és a hozzá tartozó érték (heurisztikus táv - heurisztika)
            distance = (current.getKey() - current.getValue().getCoord().distanceFrom(source.getKey().getCoord()));
            this.nodes.put(current.getValue(), distance);

            // elértünk a célba
            if(goal != null && current.getValue().getName().equals(goal.getName())){
                found = true;
                return current.getValue();
            }else if(goals != null && goals.contains(current.getValue())){
                found = true;
                return current.getValue();
            }

            // végignézzük a current node szomszédait
            for(Double d: current.getValue().getNeighbors().keySet()){
                GraphNode neighbor = current.getValue().getNeighbors().get(d);
                // heurisztikus távolság
                double tmp_f_scores = distance + d + neighbor.getCoord().distanceFrom(source.getKey().getCoord());

              //ha benne van a veremben
                if(queue.containsValue(neighbor)){
                    for(Double dou: queue.keySet()){
                        // ha az új táv nagyobb, mint a benne lévő
                        if(queue.get(dou).getName().equals(neighbor.getName())){
                            if( tmp_f_scores >= dou ) {
                                break;
                            }else {
                                // különben kicseréljük
                                queue.put(tmp_f_scores, neighbor);
                                queue.remove(dou);
                                break;
                            }
                        }
                    }
                }
                // ha a szomszéd node nincs a queue-ben
                else if(!queue.values().contains(neighbor)){
                    queue.put(tmp_f_scores, neighbor);
                }
            }

        }
        return null;
    }

    @Override
    public String toString() {
        String tmp = "";

        for(GraphNode c: this.nodes.keySet()){
            tmp += c.getName() + " távolság: " + this.getNodes().get(c) + " sugár: " + c.getRadius() + "\n";
        }

        return tmp;
    }
}
