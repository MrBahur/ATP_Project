package algorithms.search;

import java.util.PriorityQueue;

public class BestFirstSearch extends BreadthFirstSearch{
    public BestFirstSearch() {
        super("Best First Search");
        this.queue = new PriorityQueue<AState>((AState o1, AState o2) -> (o1.getCost()<o2.getCost())?1:-1);//need to find new compareTo


    }

}
