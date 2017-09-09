package chapter28;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by tzy on 2017/9/9.
 */
public class UnweightedGraphNonrecursiveDFS<V> extends UnweightedGraph<V> {
    public UnweightedGraphNonrecursiveDFS() {
        super();
    }

    public UnweightedGraphNonrecursiveDFS(V[] vertices, int[][] edges) {
        super(vertices, edges);
    }

    public UnweightedGraphNonrecursiveDFS(List<V> vertices, List<Edge> edges) {
        super(vertices, edges);
    }

    public UnweightedGraphNonrecursiveDFS(List<Edge> edges, int numberOfVertices) {
        super(edges, numberOfVertices);
    }

    public UnweightedGraphNonrecursiveDFS(int[][] edges, int numberOfVertices) {
        super(edges, numberOfVertices);
    }

    @Override
    public Tree dfs(int v) {
        List<Integer> searchOrder = new ArrayList<>();
        int[] parent = new int[vertices.size()];
        for (int i = 0; i < parent.length; i++)
            parent[i] = -1; // Initialize parent[i] to -1

        // Mark visited vertices
        boolean[] isVisited = new boolean[vertices.size()];

        Stack<Edge> stack=new Stack<>();
        for (int i = neighbors.get(v).size()-1; i >=0 ; i--) {
            stack.push(neighbors.get(v).get(i));
        }
        searchOrder.add(v);
        isVisited[v]=true;
        /*
        while (!stack.isEmpty()){
            Edge temp=stack.pop();
            if (!isVisited[temp.v]){
                searchOrder.add(temp.v);
                isVisited[temp.v]=true;
                parent[temp.v]=temp.u;
                for (int i = neighbors.get(temp.v).size()-1; i >=0 ; i--) {
                        stack.push(neighbors.get(temp.v).get(i));
                }
            }
        }
         */
        while (!stack.isEmpty()&&searchOrder.size()<neighbors.size()){
            Edge temp=stack.pop();
            if (!isVisited[temp.v]){
                searchOrder.add(temp.v);
                isVisited[temp.v]=true;
                parent[temp.v]=temp.u;
                for (int i = neighbors.get(temp.v).size()-1; i >=0 ; i--) {
                    if (!isVisited[neighbors.get(temp.v).get(i).v])
                        stack.push(neighbors.get(temp.v).get(i));
                }
            }
        }
        return new Tree(v, parent, searchOrder);
    }

}
