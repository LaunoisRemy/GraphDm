package Graph;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        /*
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
*/
        int nbSommets = 5;

        System.out.println(" \n===================== Question 3 =====================");
        Graph graphQ3= creationGrapheQ2Q3(nbSommets,true);
        int nbSommetsQ3 = 5;
        System.out.println("Le nombred le sommets est = " + nbSommetsQ3);
        int sourceQ3 = (int) (Math.random()* (nbSommets-1));
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

    public static Graph creationGrapheQ1(int nbSommets){
        Graph graph = new Graph();

        //(n*(n-1) ) /2
        for (int i = 0; i < nbSommets; i++) {
            graph.addNode(i);
        }
        int nbArcs=(int) (Math.random()* (nbSommets*(nbSommets-1))/2);

        for (int i = 0; i < nbArcs; i++) {
            boolean bonArc=false;
            while (!bonArc){
                int source = (int) (Math.random()*(nbSommets));
                int destination = (int) (Math.random()*(nbSommets));
                HashMap<Integer, List<Pair<Integer, Integer>>> listAdja = graph.getMatrice();
                if(!listAdja.get(source).contains(destination) && source!=destination){
                    bonArc=true;
                    graph.addNextToNodeNoWeight(source,destination);
                }
            }
        }

        return graph;
    }

    public static Graph creationGrapheQ2Q3(int nbSommets,boolean oriented){
        Graph graph = new Graph();

        //(n*(n-1) ) /2
        for (int i = 0; i < nbSommets; i++) {
            graph.addNode(i);
        }

        int nbArcs=(int) (Math.random()* (nbSommets*(nbSommets-1))/2);
        for (int i = 0; i < nbArcs; i++) {
            boolean bonArc=false;
            while (!bonArc){

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
                        int poids = (int) (Math.random()*(19))+1;
                        graph.addNextToNodeWithWeight(source,new Pair<>(destination,poids),oriented);
                    }
                }

            }
        }

        return graph;
    }



}
