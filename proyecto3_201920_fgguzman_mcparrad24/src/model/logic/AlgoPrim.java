package model.logic;

import model.data_structures.Edge;
import model.data_structures.EdgeWeightedGraph;
import model.data_structures.IndexMinPQ;

//Código tomado de: https://github.com/kevin-wayne/algs4/blob/master/src/main/java/edu/princeton/cs/algs4/MaxPQ.java
//Copyright 2002-2019, Robert Sedgewick and Kevin Wayne.
public class AlgoPrim {
	private Edge[] edgeTo; // edgeTo[v] = shortest edge from tree vertex to non-tree vertex
	private double[] distTo; // distTo[v] = weight of shortest such edge
	private boolean[] marked; // marked[v] = true if v on tree, false otherwise
	private IndexMinPQ<Double> pq;

	/**
	 * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
	 * 
	 * @param G the edge-weighted graph
	 */
	public AlgoPrim(EdgeWeightedGraph G) {
		edgeTo = new Edge[G.V()];
		distTo = new double[G.V()];
		marked = new boolean[G.V()];
		pq = new IndexMinPQ<Double>(G.V());
		for (int v = 0; v < G.V(); v++)
			distTo[v] = Double.POSITIVE_INFINITY;

		for (int v = 0; v < G.V(); v++) // run from each vertex to find
			if (!marked[v])
				prim(G, v); // minimum spanning forest
	}

	// run Prim's algorithm in graph G, starting from vertex s
	private void prim(EdgeWeightedGraph G, int s) {
		distTo[s] = 0.0;
		pq.insert(s, distTo[s]);
		while (!pq.isEmpty()) {
			int v = pq.delMin();
			scan(G, v);
		}
	}

	// scan vertex v
	private void scan(EdgeWeightedGraph G, int v) {
		marked[v] = true;
		for (Edge e : G.adj(v)) {
			int w = e.other(v);
			if (marked[w])
				continue; // v-w is obsolete edge
			if (e.weightHarversine() < distTo[w]) {
				distTo[w] = e.weightHarversine();
				edgeTo[w] = e;
				if (pq.contains(w))
					pq.decreaseKey(w, distTo[w]);
				else
					pq.insert(w, distTo[w]);
			}
		}
	}

	/**
	 * Returns the edges in a minimum spanning tree (or forest).
	 * 
	 * @return the edges in a minimum spanning tree (or forest) as an iterable of
	 *         edges
	 */
	public Iterable<Edge> edges() {
		Queue<Edge> mst = new Queue<Edge>();
		for (int v = 0; v < edgeTo.length; v++) {
			Edge e = edgeTo[v];
			if (e != null) {
				mst.enqueue(e);
			}
		}
		return mst;
	}

	/**
	 * Returns the sum of the edge weights in a minimum spanning tree (or forest).
	 * 
	 * @return the sum of the edge weights in a minimum spanning tree (or forest)
	 */
	public double weight() {
		double weight = 0.0;
		for (Edge e : edges())
			weight += e.weightHarversine();
		return weight;
	}

}
