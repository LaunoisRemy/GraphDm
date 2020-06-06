package Graph;

import java.util.Deque;

/**
 * Classe permettant de calculer le chemin et la distance parcourue
 */
public class CheDis {

    protected Deque<Integer> ch;
    protected int dis;

    public CheDis(Deque<Integer> chemin, int distance) {
        this.ch = chemin;
        this.dis = distance;
    }

    @Override
    public String toString() {
        return (dis != Integer.MAX_VALUE ? ch.toString()+ ", Distance :" +dis : " n'a pas de chemin");
    }
}