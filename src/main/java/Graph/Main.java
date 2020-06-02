package Graph;

import Graph.Q3.GraphQ3;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        /*System.out.println("Question 1 =====================");
        Graph graph = new Graph();
        graph.addNode(5);
        graph.addNextToNode(5,7);
        graph.addNextToNode(7,8);
        graph.addNextToNode(10,15);

        int nb = graph.getNbConnectedComp();
        System.out.println("Number of connected component(s) : "+nb);

        boolean isC = true;
        if(nb>1){
            isC = false;
        }
        System.out.println("Is the graph connected ? "+isC);

        System.out.println("Question 2 =====================");

        GraphQ2 graphQ2= new GraphQ2();

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

         */
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






    }


}
