package Graph.Q1;




import java.util.*;

/**
 * Class represent graph with a list of adjacency
 */
public class Graph {
    private HashMap<Integer, List<Integer>> listAdjacency;
    private HashMap<Integer, Boolean> visited;

    /**
     * Constructor to null
     */
    public Graph() {
        this.listAdjacency = new HashMap<>();
        this.visited = new HashMap<>();
    }

    /**
     * Create a new node
     * @param node
     */
    public boolean addNode(int node) {
        if(!this.listAdjacency.containsKey(node)){
            this.listAdjacency.put(node,new ArrayList<>());
            this.visited.put(node,false);
            return true;
        }else{
            return false;
        }

    }

    /**
     * Add a next to a node and add in the other way (node to next)
     * If the node doesnt exist create it
     * If next already exist print msg
     * @param node represent the node where we want to add a next
     * @param next to add
     */
    public boolean addNextToNode(int node,int next){
        if(!this.listAdjacency.containsKey(node)){
            this.addNode(node);
        }
        List<Integer> nexts = this.listAdjacency.get(node);

        if(!nexts.contains(next)){
            nexts.add(next);
            this.addNextToNode(next,node);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Add a full list to a node
     * If next already exist print msg
     * @param node represent the node where we want to add a next
     * @param nexts to add
     */
    public void addListNextToNode(int node, List<Integer> nexts)  {
        for (int newNexts : nexts){
            addNextToNode(node,newNexts);
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (HashMap.Entry<Integer,List<Integer>> e : listAdjacency.entrySet()) {

            StringBuilder nexts = new StringBuilder("\n\t");
            for (int n: e.getValue() ) {
                nexts.append(n).append(",\n\t");
            }
            res.append("Node : ").append(e.getKey().toString()).append(":\n\t").append(nexts).append("\n");

        }
        return res.toString();
    }

    public HashMap<Integer, List<Integer>> getListAdjacency() {
        return listAdjacency;
    }
    public List<Integer> getValueListAdjacency(int key) {
        return listAdjacency.get(key);
    }

    public void breadthFirstSearch(int source) {
        Queue<Object> queue = new LinkedList<Object>();
        queue.add(source);

        //ArrayList<Object> nodesExplored = new ArrayList<>();
        //nodesExplored.add(source);

        this.visited.put(source,true);


        while (queue.size() != 0) {
            int current = (int) queue.poll();
            for (int v : this.listAdjacency.get(current)) {
                //Node neighborNode = v.getNeighbor();
                if (!this.visited.get(v)) {
                    this.visited.put(v,true);

                    queue.add(v);
                }
            }
        }
        //return nodesExplored;
    }

    public int getNbConnectedComp() {
        int nb = 0;

        Set<Integer> keySet = this.listAdjacency.keySet();
        int firstNode = (int) keySet.toArray()[0];
        int i =0;
        ArrayList<Integer> res = new ArrayList<>();
        for (int v : keySet) {
            if (!this.visited.get(v)) {
                breadthFirstSearch(v);
                nb += 1;
            }
        }

        return nb;
    }
}
