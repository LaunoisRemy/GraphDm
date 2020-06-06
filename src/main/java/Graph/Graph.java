package Graph;

import javafx.util.Pair;

import java.util.*;

/**
 * Classe representant un graph avec une matrice d'adjacence
 */
public class Graph {
    private final HashMap<Integer, List<Pair<Integer,Integer>>> matrice;
    private final HashMap<Integer, Boolean> visited;

    /**
     * Constructeur sans paramètre
     */
    public Graph() {
        this.matrice = new HashMap<>();
        this.visited = new HashMap<>();
    }

    /**
     * Getter de la matrice d'adjacence du graphe
     * @return la matrice
     */
    public HashMap<Integer, List<Pair<Integer, Integer>>> getMatrice() {
        return matrice;
    }

    /**
     * Création d'un nouveau noeud
     * @param node : int, le noeud à ajouter
     * @return boolean : True si le noeud a bien été ajouté, False sinon
     */
    public boolean addNode(int node) {
        //Si le noeud n'existe pas déjà dans le graph, alors on l'ajoute
        if(!this.matrice.containsKey(node)){
            this.matrice.put(node,new ArrayList<>());
            this.visited.put(node,false);
            return true;
        }else{
            return false;
        }

    }

    /**
     * Ajout d'une arête entre un noeud voisin (next) et un noeud donné (node), sans poids, dans la matrice d'adjacence
     * Si le noeud (node) n'existe pas, celui-ci est ajouté
     * Si le voisin (next) n'existe pas, on l'ajoute aussi
     * @param node représente le noeud source
     * @param next représente le noeud destination (voisin)
     * @return boolean : True si l'arête a bien été ajoutée, False sinon
     */
    public boolean addNextToNodeNoWeight(int node, int next){
        //Si la source n'xiste pas, on l'ajoute au graphe
        if(!this.matrice.containsKey(node)){
            this.addNode(node);
        }
        //On récupère les voisins de la source
        List<Pair<Integer,Integer>> voisins = this.matrice.get(node);
        //On vérifie si next existe dans voisins de source
        boolean existe=voisins.stream().anyMatch(p -> p.getKey() == next);
        //Si non, alors on ajoute next comme voisin de node et node comme voisin de next (pour la matrice)
        if(!existe){
            voisins.add(new Pair<>(next,1));
            this.addNextToNodeNoWeight(next,node);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Ajout d'une arête entre un noeud voisin (p_pair) et un noeud donné (node), avec poids, dans la matrice d'adjacence
     * @param node : le noeud source de l'arête
     * @param p_pair : la paire (id du noeud,poids de l'arc) à lier à node
     * @param oriented : True si l'arete est orientée, False sinon
     * @return boolean : True si l'arete a bien été ajoutée, False sinon
     */
    public boolean addNextToNodeWithWeight(int node, Pair<Integer, Integer> p_pair,boolean oriented){
        //Si la source n'xiste pas, on l'ajoute au graphe
        if(!this.matrice.containsKey(node)){
            this.addNode(node);
        }
        //On récupère les voisins de la source
        List<Pair<Integer,Integer>> voisins = this.matrice.get(node);
        //On vérifie si next existe dans voisins de source
        boolean existe=voisins.stream()
                .anyMatch(p -> p.getKey() == p_pair.getKey());

        //Si non, alors - si le grpahe est orienté : on ajoute next comme voisin de node
        //              - si le graphe n'est pas orienté : on ajoute next comme voisin de node et node comme voisin de next
        if(!existe){
            voisins.add(p_pair);
            if(!oriented){
                Pair<Integer, Integer> new_pair = new Pair<>(node,p_pair.getValue());
                this.addNextToNodeWithWeight(p_pair.getKey(),new_pair,false);
            }
            return true;
        }else{
            return false;
        }
    }

    /**
     * Algorithme du BFS (parcours en largeur), permettant de parcourir tous les noeud d'une même composante connexe.
     * @param source : noeud de départ
     */
    public void breadthFirstSearch(int source) {
        //File des sommets visités (FIFO)
        Queue<Object> queue = new LinkedList<Object>();
        //On enfile la source
        queue.add(source);
        //On met le noeud source comme visité
        this.visited.put(source,true);

        //Tant qu'il reste encore des sommets à visiter
        while (queue.size() != 0) {
            //On défile un noeud
            int current = (int) queue.poll();
            // On prend tous les voisins
            List<Pair<Integer,Integer>> voisins = matrice.get(current);
            //Pour chacun d'entre eux, si celui-ci n'est pas encore visité, alors on l'ajoute à la file des sommets à visiter
            for (Pair<Integer,Integer> v : voisins) {
                if (!this.visited.get(v.getKey())) {
                    this.visited.put(v.getKey(),true);

                    queue.add(v.getKey());
                }
            }
        }
    }

    /**
     * Décompte du nombre de composantes connexes dans le graphe
     * Cette fonction est basée sur l'utilisation de l'algo du BFS
     * @return int: le nombre de composantes connexes présentent dans le graphe
     */
    public int getNbConnectedComp() {
        int nb = 0;
        //On récupère tous les sommets du graphe
        Set<Integer> keySet = this.matrice.keySet();
        //pour chacun d'entre eux, si ceui-ci n'a pas été visité, on lance l'algo du BFS
        //partant de ce sommet et on ajoute 1 au nombre de CC
        for (int v : keySet) {
            if (!this.visited.get(v)) {
                breadthFirstSearch(v);
                nb += 1;
            }
        }
        return nb;
    }

    /**
     * Fonction qui permet de savoir si le graphe est connexe
     * Pour cela, on utilise le paramètre nb représentant le nombre de CC du graphe
     * Si ce nombre est supérieur à 1, c'est que le graphe n'est pas connexe
     * @param nb : le nombre de composante connexe
     * @return boolean : True si le graphe est connexe, False sinon
     */
    public boolean estCo(int nb){
        return nb == 1;
    }

    /**
     * Agorithme de Prim, permettant de trouver un arbre couvrant minimal du graphe
     * Le graphe doit être connexe
     * @param source : le noeud de départ
     * @return Pair<ArrayList<Integer>,Integer> avec
     *                  ArrayList<Integer> l'arbre couvrant minimal
     *                  Integer le poids de celui-ci
     */
    public Pair<ArrayList<Integer>,Integer> prim(int source){
        int nb = this.getNbConnectedComp();
        //On vérifie si le graphe est connexe
        if(!estCo(nb)){
            System.out.println("Non connexe");
            return null;
        }

        // L'arbre couvrant minimal
        ArrayList<Integer> tree = new ArrayList<>();
        // Toutes les arêtes dont le sommet n'est pas dans tree
        ArrayList<Pair<Integer,Integer>> allEdges=new ArrayList<>();
        int current =source;
        //On ajoute la source à l'arbre couvrant minimal
        tree.add(source);
        // Poids total
        int cost = 0;

        //Tant qu'on n'a pas tous les sommets dans notre arbre
        while (tree.size() != matrice.keySet().size()) {
            // Tous les voisins du noeud courant
            List<Pair<Integer,Integer>> edges = matrice.get(current);
            // On ajoute tous les voisins (noeud et poids) du noeud courant
            // ces voisins s'ajoutent à ceux déjà trouvés avec de précèdent noeuds visités
            boolean goodAdd = allEdges.addAll(edges);
            // On supprime ceux qui ont un lien avec des noeuds déjà ajoutés à l'arbre
            allEdges.removeIf(p -> tree.contains(p.getKey()));
            assert goodAdd = false;
            // On trouve le poids minimum entre tous les voisins
            Optional<Pair<Integer, Integer>> min = allEdges.stream()
                    .min(Comparator.comparing(Pair::getValue));
            //Si on a trouvé un minimum
            if(min.isPresent()){
                //On ajoute le poids minimum au poids total de l'arbre couvrant
                cost+=min.get().getValue();
                //On ajoute le sommet dans notre arbre
                tree.add(min.get().getKey());
                //on change le prochain noeud courant
                current=min.get().getKey();
            }
        }
        return new Pair<>(tree,cost);
    }

    /**
     * Algorithme de Dijkstra permettant de trouver le plus court chemin
     * @param source : le noeud de départ
     * @return HashMap<Integer, CheDis> : le chemin emprunté et la distance parcourue
     */
    public HashMap<Integer, CheDis> dijkstra (int source){

        //Tableau de calcul du chemin le plus court
        HashMap<Integer, Deque<Integer>> tab = new HashMap<>();

        //Tableau des distances
        int[] dis = new int[matrice.size()];
        //Tableau des précèdents
        int[] prec = new int[matrice.size()];

        //Tous les sommets
        Set<Integer> keys = matrice.keySet();
        //On met la distance de la source à 0
        dis[source]=0;
        //Sommets qui nous reste à visiter
        Set<Integer> queue = new HashSet<>();

        //On met une distance infinie dans les chemins non parcourus
        for (int k: keys
        ) {
            if(k != source){
                dis[k]=Integer.MAX_VALUE; //infini
            }
            queue.add(k);
            tab.put(k, new ArrayDeque<>());
        }

        //Tant que tous les sommets n'ont pas été visités
        while(!queue.isEmpty()){
            //On prend le sommet avec l'arc de poids minimum
            int courant = getMin(queue, dis);
            if(courant == -1) break;
            //Tous les voisin du noeud courant
            List<Pair<Integer,Integer>> voisins = matrice.get(courant);

            //Pour chaque noeud on met à jour les distances
            for (Pair<Integer, Integer> v : voisins) {
                majDistance(courant, v.getKey(), dis, prec);
            }
        }

        //Pour tous les sommets du graphe
        for (int key: matrice.keySet()) {
            //Sommet courant
            int s = key;
            if (prec[key] == 0) continue;
            //Parcours de tous les sommets depuis s
            while (s != source) {
                tab.get(key).addFirst(s);
                s = prec[s];
            }
            tab.get(key).addFirst(source);
        }
        //Chemin le plus court
        HashMap<Integer, CheDis> res = new HashMap<>();
        //Pour chaque sommets calculé dans le tableau de Dijkstra, on l'ajoute dans le chemin
        for (int k: tab.keySet()) {
            res.put(k, new CheDis(tab.get(k), dis[k]));
        }
        return res;
    }

    /**
     * Retourne le poids entre un noeud source (i) et un point destination (j)
     * @param i le noeud source
     * @param j le noeud destination
     * @return int : le poids entre les deux sommets, 0 si j ne fait pas partie des voisins de i
     */
    private int getPoids(int i, int j){
        //Tous les voisins du sommet source
        List<Pair<Integer,Integer>> voisins = matrice.get(i);
        //Pour chaque sommet voisin, qaund le sommet courant est le sommet destination (j) on retourn le poids de l'arc
        for (Pair<Integer,Integer> p:voisins
             ) {
            if(j==p.getKey()) return p.getValue();
        }
        return 0;
    }

    /**
     * On met à jour la distance entre sCourant et v si la distance pour aller jusqu'à v est supérieur que si l'on passait par le sommet sCourant
     * @param sCourant : le sommet à tester
     * @param v : le sommet que l'on souhaite atteindre
     * @param dis : tableau des distances
     * @param prec : tableau des précèdents
     */
    private void majDistance(int sCourant, int v, int[] dis, int[] prec) {
        if (dis[v] > dis[sCourant] + getPoids(sCourant,v)) {
            dis[v] = dis[sCourant] + getPoids(sCourant,v);
            prec[v] = sCourant;
        }
    }

    /**
     * Fonction qui permet de trouver l'arête de poids minimal
     * @param nonMarques : les sommets pas encore visités
     * @param tabDistance : tableau des distances
     * @return int : le noeud v dont l'arête (s,v) est de poids minimal
     */
    private int getMin(Set<Integer> nonMarques, int[] tabDistance) {
        int min = Integer.MAX_VALUE;
        int sMin = -1;
        for (int s : nonMarques) {
            if (tabDistance[s] < min) {
                min = tabDistance[s];
                sMin = s;
            }
        }
        nonMarques.remove(sMin);
        return sMin;
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
        StringBuilder res= new StringBuilder();
        HashMap<Integer, List<Pair<Integer, Integer>>> listAdja =this.getMatrice();
        for(Map.Entry<Integer, List<Pair<Integer, Integer>>> entry : listAdja.entrySet()){
            int key = entry.getKey();
            List<Pair<Integer, Integer>> value = entry.getValue();
            res.append("Le sommet :").append(key).append(" a comme voisins :").append(value.toString()+"\n");
        }
        return String.valueOf(res);
    }
}
