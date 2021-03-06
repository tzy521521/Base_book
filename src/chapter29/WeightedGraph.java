package chapter29;

import chapter28.AbstractGraph;
import java.util.*;

public class WeightedGraph<V> extends AbstractGraph<V> {
  protected List<WeightedEdge> allEdges = new ArrayList<>();//图中所有的边
  //folyd
  protected int[][] dist;
  //路径。path[i][j]=k表示，"顶点i"到"顶点j"的最短路径会经过顶点k。
  protected int[][] path;
  /** Construct an empty */
  public WeightedGraph() {
  }
  
  /** Construct a WeightedGraph from vertices and edged in arrays */
  public WeightedGraph(V[] vertices, int[][] edges) {
    createWeightedGraph(Arrays.asList(vertices), edges);
  }

  /** Construct a WeightedGraph from vertices and edges in list */
  public WeightedGraph(int[][] edges, int numberOfVertices) {
    List<V> vertices = new ArrayList<>();
    for (int i = 0; i < numberOfVertices; i++)
      vertices.add((V)(new Integer(i)));
    
    createWeightedGraph(vertices, edges);
  }

  /** Construct a WeightedGraph for vertices 0, 1, 2 and edge list */
  public WeightedGraph(List<V> vertices, List<WeightedEdge> edges) {
    createWeightedGraph(vertices, edges);
  }

  /** Construct a WeightedGraph from vertices 0, 1, and edge array */
  public WeightedGraph(List<WeightedEdge> edges, int numberOfVertices) {
    List<V> vertices = new ArrayList<>();
    for (int i = 0; i < numberOfVertices; i++)
      vertices.add((V)(new Integer(i)));
    
    createWeightedGraph(vertices, edges);
  }

  /** Create adjacency lists from edge arrays */
  private void createWeightedGraph(List<V> vertices, int[][] edges) {
    this.vertices = vertices;
    //dist 二长度数组。即，dist[i][j]=sum表示，"顶点i"到"顶点j"的最短路径的长度是sum。Folyd
    dist=new int[vertices.size()][vertices.size()];
    path=new int[vertices.size()][vertices.size()];
    for (int i = 0; i <vertices.size() ; i++) {
      for (int j = 0; j <vertices.size() ; j++) {
        if (i==j)
          dist[i][j]=0;
        else
          dist[i][j]=Integer.MAX_VALUE;
      }
    }
    for (int i = 0; i < vertices.size(); i++) {
      neighbors.add(new ArrayList<>()); // Create a list for vertices
    }

    for (int i = 0; i < edges.length; i++) {
      WeightedEdge temp=new WeightedEdge(edges[i][0], edges[i][1], edges[i][2]);
      neighbors.get(edges[i][0]).add(temp);
      allEdges.add(temp);
      //初始化最短路径数组。
      dist[edges[i][0]][edges[i][1]]=edges[i][2];
      //初始化前驱定点数组。
      path[edges[i][0]][edges[i][1]]=edges[i][1];
    }
  }

  /** Create adjacency lists from edge lists */
  private void createWeightedGraph(List<V> vertices, List<WeightedEdge> edges) {
    this.vertices = vertices;     

    for (int i = 0; i < vertices.size(); i++) {
      neighbors.add(new ArrayList<>()); // Create a list for vertices
    }

    for (WeightedEdge edge: edges) {      
      neighbors.get(edge.u).add(edge); // Add an edge into the list
    }
  }

  /** Return the weight on the edge (u, v) */
  public double getWeight(int u, int v) throws Exception {
    for (Edge edge : neighbors.get(u)) {
      if (edge.v == v) {
        return ((WeightedEdge)edge).weight;
      }
    }
    
    throw new Exception("Edge does not exit");
  }
  
  /** Display edges with weights */
  public void printWeightedEdges() {
    for (int i = 0; i < getSize(); i++) {
      System.out.print(getVertex(i) + " (" + i + "): ");
      for (Edge edge : neighbors.get(i)) {
        System.out.print("(" + edge.u +
          ", " + edge.v + ", " + ((WeightedEdge)edge).weight + ") ");
      }
      System.out.println();
    }
  }
 
  /** Add edges to the weighted graph */  
  public boolean addEdge(int u, int v, double weight) {
    return addEdge(new WeightedEdge(u, v, weight));
  }

  public MST getMinimumSpanningTreeWithKruskal(){
    ArrayList<WeightedEdge> T=new ArrayList<>();

    Collections.sort(allEdges);
    DisjSets disjSets=new DisjSets(getSize());
    double totalWeight = 0;
    while (disjSets.getCount()>1){
      for (WeightedEdge weightedEdge:allEdges) {
        int root1=disjSets.find(weightedEdge.u);
        int root2=disjSets.find(weightedEdge.v);
        if (root1!=disjSets.find(weightedEdge.v)){
          disjSets.unionByHeight(root1,root2);
          T.add(weightedEdge);
          totalWeight+=weightedEdge.weight;
        }
      }
    }
    return new MST(0, disjSets.getS(), null, totalWeight);
    //return T;
  }
  /** Get a minimum spanning tree rooted at vertex 0 */
  public MST getMinimumSpanningTree() {
    return getMinimumSpanningTree(0);
  }
  
  /** Get a minimum spanning tree rooted at a specified vertex O(n3)*/
  public MST getMinimumSpanningTree(int startingVertex) {
    // cost[v] stores the cost by adding v to the tree
    double[] cost = new double[getSize()];
    for (int i = 0; i < cost.length; i++) {
      cost[i] = Double.POSITIVE_INFINITY; // Initial cost 所有节点加入到T中初始代价为无穷大。
    }
    cost[startingVertex] = 0; // Cost of source is 0  设置给定初始节点的代价为0

    int[] parent = new int[getSize()]; // Parent of a vertex
    parent[startingVertex] = -1; // startingVertex is the root
    double totalWeight = 0; // Total weight of the tree thus far

    List<Integer> T = new ArrayList<>();

    // Expand T
    while (T.size() < getSize()) {
      // Find smallest cost v in V - T
      int u = -1; // Vertex to be determined
      double currentMinCost = Double.POSITIVE_INFINITY;
      for (int i = 0; i < getSize(); i++) {//T是个线性表判断u顶点是否已经在T中需要O(n)的时间。
        if (!T.contains(i) && cost[i] < currentMinCost) {
          currentMinCost = cost[i];
          u = i;
        }
      }

      T.add(u); // Add a new vertex to T
      totalWeight += cost[u]; // Add cost[u] to the tree

      // Adjust cost[v] for v that is adjacent to u and v in V - T
      for (Edge e : neighbors.get(u)) {////T是个线性表判断v顶点是否已经在T中需要O(n)的时间。
        if (!T.contains(e.v) && cost[e.v] > ((WeightedEdge)e).weight) {
          cost[e.v] = ((WeightedEdge)e).weight;
          parent[e.v] = u;
        }
      }
    } // End of while

    return new MST(startingVertex, parent, T, totalWeight);
  }

  /** Get a minimum spanning tree rooted at a specified vertex O(n2)*/
  public MST getMinimumSpanningTree0(int startingVertex){
      // cost[v] stores the cost by adding v to the tree
      double[] cost = new double[getSize()];
      for (int i = 0; i < cost.length; i++) {
          cost[i] = Double.POSITIVE_INFINITY; // Initial cost 所有节点加入到T中初始代价为无穷大。
      }
      cost[startingVertex] = 0; // Cost of source is 0  设置给定初始节点的代价为0

      int[] parent = new int[getSize()]; // Parent of a vertex
      parent[startingVertex] = -1; // startingVertex is the root
      boolean[] isInT=new boolean[getSize()];//高效的判断u和v是否在T中。
      double totalWeight = 0; // Total weight of the tree thus far

      List<Integer> T = new ArrayList<>();

      // Expand T
      while (T.size() < getSize()) {
          // Find smallest cost v in V - T
          int u = -1; // Vertex to be determined
          double currentMinCost = Double.POSITIVE_INFINITY;
          for (int i = 0; i < getSize(); i++) {//T是个线性表判断u顶点是否已经在T中需要O(n)的时间。
              if (!T.contains(i) && cost[i] < currentMinCost) {
                  currentMinCost = cost[i];
                  u = i;
              }
          }

          T.add(u); // Add a new vertex to T
          isInT[u]=true;
          totalWeight += cost[u]; // Add cost[u] to the tree

          // Adjust cost[v] for v that is adjacent to u and v in V - T
          for (Edge e : neighbors.get(u)) {////T是个线性表判断v顶点是否已经在T中需要O(n)的时间。
              if (!isInT[e.v] && cost[e.v] > ((WeightedEdge)e).weight) {
                  cost[e.v] = ((WeightedEdge)e).weight;
                  parent[e.v] = u;
              }
          }
      } // End of while

      return new MST(startingVertex, parent, T, totalWeight);
  }
  /** MST is an inner class in WeightedGraph */
  public class MST extends Tree {
    private double totalWeight; // Total weight of all edges in the tree

    public MST(int root, int[] parent, List<Integer> searchOrder, double totalWeight) {
      super(root, parent, searchOrder);
      this.totalWeight = totalWeight;
    }

    public double getTotalWeight() {
      return totalWeight;
    }
  }

  public int[][] getShortestPath(){
    for (int k = 0; k <getSize() ; k++) {
      //分别以每个顶点为中介节点更新最短路径矩阵
      for (int i = 0; i <getSize() ; i++) {
        for (int j = 0; j <getSize() ; j++) {
          int tem=(dist[i][k]==Integer.MAX_VALUE||dist[k][j]==Integer.MAX_VALUE)?Integer.MAX_VALUE:(dist[i][k]+dist[k][j]);
          if (dist[i][j]>tem){
            dist[i][j]=tem;
            path[i][j]=k;
          }
        }
      }
    }
    return dist;
  }

  public int[][] getPath() {
    return path;
  }
  public ArrayList<Integer> path(int source,int target){
    ArrayList<Integer> result=new ArrayList<>();
    int k=target;
    while (path[k][source]!=source){
      result.add(path[k][source]);
      k=path[k][source];
    }
    return result;
  }

  /** Find single source shortest paths */
  public ShortestPathTree getShortestPath(int sourceVertex) {
    // cost[v] stores the cost of the path from v to the source
    double[] cost = new double[getSize()];
    for (int i = 0; i < cost.length; i++) {
      cost[i] = Double.POSITIVE_INFINITY; // Initial cost set to infinity
    }
    cost[sourceVertex] = 0; // Cost of source is 0

    // parent[v] stores the previous vertex of v in the path
    int[] parent = new int[getSize()];
    parent[sourceVertex] = -1; // The parent of source is set to -1
    
    // T stores the vertices whose path found so far
    List<Integer> T = new ArrayList<>();
    
    // Expand T
    while (T.size() < getSize()) {
      // Find smallest cost v in V - T 
      int u = -1; // Vertex to be determined
      double currentMinCost = Double.POSITIVE_INFINITY;
      for (int i = 0; i < getSize(); i++) {
        if (!T.contains(i) && cost[i] < currentMinCost) {
          currentMinCost = cost[i];
          u = i;
        }
      }
      
      T.add(u); // Add a new vertex to T
      
      // Adjust cost[v] for v that is adjacent to u and v in V - T
      for (Edge e : neighbors.get(u)) {
        if (!T.contains(e.v) && cost[e.v] > cost[u] + ((WeightedEdge)e).weight) {
          cost[e.v] = cost[u] + ((WeightedEdge)e).weight;
          parent[e.v] = u; 
        }
      }
    } // End of while

    // Create a ShortestPathTree
    return new ShortestPathTree(sourceVertex, parent, T, cost);
  }

  /** ShortestPathTree is an inner class in WeightedGraph */
  public class ShortestPathTree extends Tree {
    private double[] cost; // cost[v] is the cost from v to source

    /** Construct a path */
    public ShortestPathTree(int source, int[] parent, List<Integer> searchOrder, double[] cost) {
      super(source, parent, searchOrder);
      this.cost = cost;
    }

    /** Return the cost for a path from the root to vertex v */
    public double getCost(int v) {
      return cost[v];
    }

    /** Print paths from all vertices to the source */
    public void printAllPaths() {
      System.out.println("All shortest paths from " + vertices.get(getRoot()) + " are:");
      for (int i = 0; i < cost.length; i++) {
        printPath(i); // Print a path from i to the source
        System.out.println("(cost: " + cost[i] + ")"); // Path cost
      }
    }
  }
}