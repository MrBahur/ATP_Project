package algorithms.search;

import java.util.PriorityQueue;

public class BestFirstSearch extends BreadthFirstSearch{
    public BestFirstSearch() {
        super("Best First Search");
        this.queue = new PriorityQueue<AState>((AState o1, AState o2) -> Double.compare(o2.getCost(), o1.getCost()));//need to find new compareTo


    }

}
