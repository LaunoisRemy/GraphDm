package Graph.Q3;




import javafx.util.Pair;

import java.util.*;

/**
 * Class represent graph with a list of adjacency
 */
public class GraphQ3 {
    private HashMap<Integer, List<Pair<Integer,Integer>>> matrice;
    private HashMap<Integer, Boolean> visited;

    /**
     * Constructor to null
     */
    public GraphQ3() {
        this.matrice = new HashMap<>();
        this.visited = new HashMap<>();
    }

    /**
     * Create a new node
     * @param node
     */
    public boolean addNode(int node) {
        if(!this.matrice.containsKey(node)){
            this.matrice.put(node,new ArrayList<>());
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
    public boolean addNextToNode(int node,int next,int weight){
        Pair<Integer, Integer> p_pair = new Pair<>(next,weight);
        return addNextToNode(node,p_pair);
    }

    public boolean addNextToNode(int node,Pair<Integer, Integer> p_pair){
        if(!this.matrice.containsKey(node)){
            this.addNode(node);
        }
        List<Pair<Integer,Integer>> voisins = this.matrice.get(node);

        boolean existe=voisins.stream()
                .anyMatch(p -> p.getKey() == p_pair.getKey());
        if(!existe){
            voisins.add(p_pair);
            Pair<Integer, Integer> new_pair = new Pair<>(node,p_pair.getValue());
            return true;
        }else{
            return false;
        }
    }

    @Deprecated
    /**
     * Add a full list to a node
     * If next already exist print msg
     * @param node represent the node where we want to add a next
     * @param nexts to add
     */
    public void addListNextToNode(int node, List<Integer> nexts)  {
        for (int newNexts : nexts){
            //addNextToNode(node,newNexts);
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


    public HashMap<Integer, List<Pair<Integer, Integer>>> getMatrice() {
        return matrice;
    }
    public List<Pair<Integer, Integer>> getValueMatrice(int key) {
        return matrice.get(key);
    }


    public Pair<ArrayList<Integer>,Integer> prim(int source){

        //Queue<Integer> queue = new LinkedList<>(matrice.keySet());
        // L'arbre des sommets a parcourir
        ArrayList<Integer> tree = new ArrayList<>();
        // Tous les arcs dont le sommet n'est pas dans tree
        ArrayList<Pair<Integer,Integer>> allEdges=new ArrayList<>();

        int current =source;
        tree.add(source);
        // Poids total
        int cost = 0;
        //Tant que on n'a pas tous les sommets dans notre arbre
        while (tree.size() != matrice.keySet().size()) {
            // Tous les voisins de current
            List<Pair<Integer,Integer>> edges = matrice.get(current);
            // On ajoute tous les voisins
            boolean goodAdd =   allEdges.addAll(edges);
            // On supprime ceux dont on a deja un lien
            allEdges.removeIf(p -> tree.contains(p.getKey()));
            assert goodAdd = false;


            // On trouve le minimum des voisins
            Optional<Pair<Integer, Integer>> min = allEdges.stream()
                    .min(Comparator.comparing(Pair::getValue));

            if(min.isPresent()){
                //On ajoute le poids minimum
                cost+=min.get().getValue();
                //On ajoute le sommet dans notre arbre
                tree.add(min.get().getKey());
                //on change le prochain current
                current=min.get().getKey();
            }

        }

        return new Pair<>(tree,cost);
    }

    public HashMap dijkstra (int source){

        int poidsCourant = 0;
        // liste des sommets non parcourus avec le poids du chemin pour y arriver
        HashMap<Integer,Integer> poids = new HashMap<>();
        //Liste des sommets parcourus
        HashMap<Integer,Integer> list = new HashMap<>();
        //La distance ini est de 0
        poids.put(source,0);
        // Tous les sommets
        Set<Integer> keys = matrice.keySet();
        //On met infini dans les chemins non parcourus
        for (int k: keys
             ) {
            if(k!=source){
                poids.put(k,Integer.MAX_VALUE);
            }
        }

        int courant = source;
        // Tant qu'on a pas parcourus tous les sommets
        while (poids.size() != 0){
            //On recupere les voisins du courant
            List<Pair<Integer,Integer>> voisins = matrice.get(courant);
            // Pour chaque voisins on change nos poids de chemin
            for (Pair<Integer,Integer> v:voisins
                 ) {
                int v_key = v.getKey();
                int v_value = v.getValue();
                int poidsChemin = v_value+poidsCourant;
                //On change si on ne l'a pas deja parcourus
                if( !list.containsKey(v_key) && poidsChemin< poids.get(v_key)){
                    poids.put(v_key,poidsChemin);
                }

            }
            //on a parcourus ce sommet
            list.put(courant,poids.get(courant));
            poids.remove(courant);
            int min = Integer.MAX_VALUE;
            //On cherche le sommet suivant (poids minimum)
            for(Map.Entry<Integer, Integer> entry : poids.entrySet()) {

                int key = entry.getKey();
                int value = entry.getValue();
                if(value < min){
                    min=value;
                    courant=key;
                }

            }
            poidsCourant=min;
        }

        return list;
    }

}
