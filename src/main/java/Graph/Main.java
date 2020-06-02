package Graph;

import Graph.Q1.Graph;
import Graph.Q2.GraphQ2;
import Graph.Q3.GraphQ3;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n===================== Question 1 =====================");
        Graph graph =creationGrapheQ1(5);
        int nb = graph.getNbConnectedComp();
        HashMap<Integer, List<Integer>> listAdja =graph.getListAdjacency();
        for(Map.Entry<Integer, List<Integer>> entry : listAdja.entrySet()){
            int key = entry.getKey();
            List<Integer> value = entry.getValue();
            System.out.println("Le sommet :"+key+" a comme voisins :"+ value.toString());
        }
        System.out.println("Number of connected component(s) : "+nb);
        boolean isC = true;
        if(nb>1){
            isC = false;
        }
        System.out.println("Is the graph connected ? "+isC);
        /*
        System.out.println("\n===================== Question 2 =====================");
        GraphQ2 graphQ2= creationGrapheQ2(5);
        System.out.println(graphQ2.getMatrice());

        if(!graph2toGraph1(graphQ2)){
            System.out.println("Ca va bugger");
        }else{
            Pair<ArrayList<Integer>,Integer> res= graphQ2.prim(1);
            System.out.println(res.getKey().toString());
            System.out.println(res.getValue());
        };

        /*




        graphQ2.addNode(1);
        //A
        graphQ2.addNextToNode(1,2,2);
        graphQ2.addNextToNode(1,3,3);
        graphQ2.addNextToNode(1,4,3);
        //B
        graphQ2.addNextToNode(2,3,4);
        graphQ2.addNextToNode(2,5,3);
        //C
        graphQ2.addNextToNode(3,4,5);
        graphQ2.addNextToNode(3,5,1);
        graphQ2.addNextToNode(3,6,6);
        //D
        graphQ2.addNextToNode(4,6,7);
        //E
        graphQ2.addNextToNode(5,6,8);
        //F
        graphQ2.addNextToNode(6,7,9);

        Pair<ArrayList<Integer>,Integer> res= graphQ2.prim(1);
        System.out.println(res.getKey().toString());
        System.out.println(res.getValue());

        System.out.println("Question 2 =====================");
        GraphQ3 graphQ3= new GraphQ3();

        graphQ3.addNode(1);
        graphQ3.addNode(2);
        graphQ3.addNode(3);
        graphQ3.addNode(4);
        graphQ3.addNode(5);

        graphQ3.addNextToNode(1,5,3);
        graphQ3.addNextToNode(1,3,18);
        graphQ3.addNextToNode(5,2,10);
        graphQ3.addNextToNode(5,4,2);
        graphQ3.addNextToNode(4,2,1);
        graphQ3.addNextToNode(2,3,4);
        graphQ3.addNextToNode(2,1,8);

        HashMap resDij = graphQ3.dijkstra(1);
        AtomicInteger poids = new AtomicInteger();
        resDij.forEach((k,v)-> poids.addAndGet((Integer) v));

        System.out.println(resDij.toString());
        System.out.println(poids);

         */



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
                HashMap<Integer, List<Integer>> listAdja = graph.getListAdjacency();
                if(!listAdja.get(source).contains(destination) && source!=destination){
                    bonArc=true;
                    graph.addNextToNode(source,destination);

                }
            }
        }

        return graph;
    }

    public static GraphQ2 creationGrapheQ2(int nbSommets){
        GraphQ2 graph = new GraphQ2();

        //(n*(n-1) ) /2
        for (int i = 0; i < nbSommets; i++) {
            graph.addNode(i);
        }

        int nbArcs=(int) (Math.random()* (nbSommets*(nbSommets-1))/2);
        System.out.println(nbArcs);
        for (int i = 0; i < nbArcs; i++) {
            boolean bonArc=false;
            while (!bonArc){

                int source = (int) (Math.random()*(nbSommets));
                int destination = (int) (Math.random()*(nbSommets));
                if(source!=destination){
                    HashMap<Integer, List<Pair<Integer, Integer>>> matrice = graph.getMatrice();
                    List<Pair<Integer, Integer>> voisins= matrice.get(source);
                    int j =0;
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
                        graph.addNextToNode(source,new Pair<>(destination,poids));
                    }
                }

            }
        }

        return graph;
    }

    public static boolean graph2toGraph1(GraphQ2 g2){
        HashMap<Integer, List<Pair<Integer, Integer>>> matrice = g2.getMatrice();
        Graph g= new Graph();
        for(Map.Entry<Integer,  List<Pair<Integer, Integer>>> entry : matrice.entrySet()) {

            int key = entry.getKey();
            List<Pair<Integer, Integer>> values = entry.getValue();
            for (Pair<Integer, Integer> p: values
                 ) {
                g.addNextToNode(key,p.getKey());
            }

        }

        int nb = g.getNbConnectedComp();
        System.out.println("Number of connected component(s) : "+nb);
        boolean isC = true;
        if(nb>1){
            isC = false;
        }
        HashMap<Integer, List<Integer>> listAdja =g.getListAdjacency();
        for(Map.Entry<Integer, List<Integer>> entry : listAdja.entrySet()){
            int key = entry.getKey();
            List<Integer> value = entry.getValue();
            System.out.println("Le sommet :"+key+" a comme voisins :"+ value.toString());
        }

        return isC;
    }

}
