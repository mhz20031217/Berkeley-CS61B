package lab11.graphs;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int sx, sy, ex, ey;
    private int S, E;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        sx = sourceX;
        sy = sourceY;
        ex = targetX;
        ey = targetY;
        S = maze.xyTo1D(sx, sy);
        E = maze.xyTo1D(ex, ey);
        marked = new boolean[maze.V()];
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        Queue<Integer> q = new ArrayDeque<>();
        marked[S] = true;
        distTo[S] = 0;
        edgeTo[S] = S;
        q.add(S);

        while (!q.isEmpty()) {
            int f = q.poll();
            if (f == E) {
                break;
            }
            for (int n: maze.adj(f)) {
                if (marked[n]) continue;
                marked[n] = true;
                distTo[n] = distTo[f] + 1;
                edgeTo[n] = f;
                q.add(n);
                announce();
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

