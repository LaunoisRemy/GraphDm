package Graph;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    /**
     * Création d'un graphe non orienté, non pondéré, représenté par une matrice d'adjacence
     * @param nbSommets : nombre de sommets du graphe
     * @return Graph, le graphe créé
     */
    public static Graph creationGrapheQ1(int nbSommets){
        Graph graph = new Graph();

        //Création des sommets
        for (int i = 0; i < nbSommets; i++) {
            graph.addNode(i);
        }
        //Un nombre random d'arêtes
        //Nombre max d'arêtes : (n*(n-1))/2
        int nbArcs=(int) (Math.random()* (nbSommets*(nbSommets-1))/2);

        //Création de chaque arête ssi l'arête n'existe déjà pas et si la source de l'arête est différent de la destination
        for (int i = 0; i < nbArcs; i++) {
            boolean bonArc=false;
            while (!bonArc){
                //la source et la destination sont choisis aléatoirement
                int source = (int) (Math.random()*(nbSommets));
                int destination = (int) (Math.random()*(nbSommets));
                HashMap<Integer, List<Pair<Integer, Integer>>> matriceAdja = graph.getMatrice();
                if(!matriceAdja.get(source).contains(destination) && source!=destination){
                    bonArc=true;
                    graph.addNextToNodeNoWeight(source,destination);
                }
            }
        }

        return graph;
    }

    /**
     * Création d'un graphe pondéré, représenté par une matrice d'adjacence
     * Le graphe est orienté si le paramétre oriented est à True, sinon le graphe est non orienté
     * @param nbSommets : nombre de sommets du graphe
     * @param oriented : True si le graphe doit être orienté, False sinon
     * @return Graph, le graphe créé
     */
    public static Graph creationGrapheQ2Q3(int nbSommets,boolean oriented){
        Graph graph = new Graph();

        //Création des sommets
        for (int i = 0; i < nbSommets; i++) {
            graph.addNode(i);
        }
        //Un nombre random d'arêtes
        //Nombre max d'arêtes : (n*(n-1))/2
        int nbArcs=(int) (Math.random()* (nbSommets*(nbSommets-1))/2);

        //Création de chaque arête ssi l'arête n'existe déjà pas et si la source de l'arête est différent de la destination
        for (int i = 0; i < nbArcs; i++) {
            boolean bonArc=false;
            while (!bonArc){
                //la source et la destination sont choisis aléatoirement
                int source = (int) (Math.random()*(nbSommets));
                int destination = (int) (Math.random()*(nbSommets));
                if(source!=destination){
                    HashMap<Integer, List<Pair<Integer, Integer>>> matrice = graph.getMatrice();
                    List<Pair<Integer, Integer>> voisins= matrice.get(source);
                    boolean voisinsTrouve = false;
                    for (Pair<Integer, Integer> v: voisins
                    ) {
                        if(v.getKey() == destination) {
                            voisinsTrouve= true;
                        }
                    }
                    if(!voisinsTrouve ){
                        bonArc=true;
                        //le poids de l'arc est choisi aléatoirement entre 1 et 20
                        int poids = (int) (Math.random()*(19))+1;
                        graph.addNextToNodeWithWeight(source,new Pair<>(destination,poids),oriented);
                    }
                }

            }
        }
        return graph;
    }

    /**
     * MAIN pour tester les différents algorithmes
     */
    public static void main(String[] args) {

        System.out.println("\n===================== Question 1 =====================");
        Graph graph =creationGrapheQ1(5);
        int nb = graph.getNbConnectedComp();
        System.out.println("Number of connected component(s) : "+nb);
        System.out.println(graph.toString());
        System.out.println("Is the graph connected ? "+graph.estCo(nb));

        System.out.println("\n===================== Question 2 =====================");
        int nbSommets = 5;
        System.out.println("Le nombred le sommets est = " + nbSommets);
        int source = (int) (Math.random()* (nbSommets-1));
        System.out.println("la source est = "+ source);
        Graph graphQ2= creationGrapheQ2Q3(nbSommets,false);
        System.out.println(graphQ2.toString());

        Pair<ArrayList<Integer>,Integer> res= graphQ2.prim(source);
        if(res != null){
            System.out.println("L'arbre couvrant minimale est :" + res.getKey().toString());
            System.out.println("Le poids :" +res.getValue());
        }

        System.out.println(" \n===================== Question 3 =====================");
        int nbSommetsQ3 = 5;
        Graph graphQ3= creationGrapheQ2Q3(nbSommetsQ3,true);
        System.out.println("Le nombred le sommets est = " + nbSommetsQ3);
        int sourceQ3 = (int) (Math.random()* (nbSommetsQ3-1));
        System.out.println("la source est = "+ sourceQ3);

        System.out.println(graphQ3.toString());
        HashMap<Integer, CheDis> resDij = graphQ3.dijkstra(sourceQ3);
        if(resDij !=null){
            resDij.forEach((k,v)-> {
                System.out.println("Chemin : "+ sourceQ3 + " ->" + k + ":"+v);
            });
        }else {
            System.out.println("Source sans voisins");
        }


    }

}
