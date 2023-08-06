package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    private class Node implements Comparable<Node> {
        final int p;
        @Override
        public int compareTo(Node node) {
            return h(node.p) - h(p);
        }
        public Node(int p) {
            this.p = p;
        }
    }
    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return maze.toX(v) - maze.toX(t) + (maze.toY(v) - maze.toY(t));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar() {
        MinPQ<Node> pq = new MinPQ<>();
        edgeTo[s] = s;
        distTo[s] = 0;
        pq.insert(new Node(s));
        announce();
        while (!pq.isEmpty()) {
            int f = pq.delMin().p;
            if (f == t) {
                announce();
                break;
            }
            for (int n: maze.adj(f)) {
                if (marked[n]) continue;
                marked[n] = true;
                distTo[n] = distTo[f] + 1;
                edgeTo[n] = f;
                announce();
                pq.insert(new Node(n));
            }
        }
    }

    @Override
    public void solve() {
        astar();
    }

}

