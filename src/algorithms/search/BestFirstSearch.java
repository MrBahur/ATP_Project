package algorithms.search;

import java.util.PriorityQueue;

public class BestFirstSearch extends BreadthFirstSearch{

    /**
     * Constructor for Best First Search
     * Same as BFS, just changing the name and sending Comparator to priority queue
     * (instead of regular queue)
     */
    public BestFirstSearch() {
        super("Best First Search");
        this.queue = new PriorityQueue<AState>((AState o1, AState o2) -> Double.compare(o2.getCost(), o1.getCost()));//choosing the low cost path first

    }

}
