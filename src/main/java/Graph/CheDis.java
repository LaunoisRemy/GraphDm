package Graph;

import java.util.Deque;

public class CheDis {

    protected Deque<Integer> ch;
    protected int dis;

    public CheDis(Deque<Integer> chemin, int distance) {
        this.ch = chemin;
        this.dis = distance;
    }

    @Override
    public String toString() {
        return (dis != Integer.MAX_VALUE ? ch.toString()+ ", Distance :" +dis : "a pas de chemin");
    }
}