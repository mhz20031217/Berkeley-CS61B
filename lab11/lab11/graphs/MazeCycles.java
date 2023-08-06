package lab11.graphs;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.UF;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    public MazeCycles(Maze m) {
        super(m);
    }

    @Override
    public void solve() {
        path = new Stack<>();
        dfs(0, -1);
        announce();
    }

    private Stack<Integer> path;
    // Helper methods go here
    private boolean dfs(int u, int parent) {
        if (marked[u]) {
            int p, lastp = u;
            while ((p = path.pop()) != u) {
                edgeTo[lastp] = p;
                lastp = p;
            }
            edgeTo[lastp] = p;
            return true;
        }
        marked[u] = true;
        path.push(u);
        for (int v: maze.adj(u)) {
            if (v == parent) continue;
            if (dfs(v, u)) {
                return true;
            }
        }
        path.pop();
        return false;
    }
}

