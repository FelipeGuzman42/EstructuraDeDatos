package model.logic;

import model.data_structures.Edge;
import model.data_structures.EdgeWeightedGraph;
import model.data_structures.MinPQ;
import model.data_structures.UF;

//Código tomado de: https://github.com/kevin-wayne/algs4/blob/master/src/main/java/edu/princeton/cs/algs4/MaxPQ.java
//Copyright 2002-2019, Robert Sedgewick and Kevin Wayne.
public class AlgoKruskal {
	private double weight; // weight of MST
	private Queue<Edge> mst = new Queue<Edge>(); // edges in MST

	/**
	     * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
	     * @param G the edge-weighted graph
	     */
	    public AlgoKruskal(EdgeWeightedGraph G) {
	        // more efficient to build heap by passing array of edges
	        MinPQ<Edge> pq = new MinPQ<Edge>();
	        for (Edge e : G.edges()) {
	            pq.insert(e);
	        }

	        // run greedy algorithm
	        UF uf = new UF(G.V());
	        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
	            Edge e = pq.delMin();
	            int v = e.either();
	            int w = e.other(v);
	            if (uf.find(v) != uf.find(w)) { // v-w does not create a cycle
	                uf.union(v, w);  // merge v and w components
	                mst.enqueue(e);  // add edge e to mst
	                weight += e.weightHarversine();
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
		return mst;
	}

	/**
	 * Returns the sum of the edge weights in a minimum spanning tree (or forest).
	 * 
	 * @return the sum of the edge weights in a minimum spanning tree (or forest)
	 */
	public double weight() {
		return weight;
	}
}
