package model.data_structures;

public class CC {
	
	//Codigo tomado de: https://github.com/kevin-wayne/algs4/blob/master/src/main/java/edu/princeton/cs/algs4/CC.java
	//Copyright 2002-2019, Robert Sedgewick and Kevin Wayne.
	
	    private boolean[] marked;   // marked[v] = has vertex v been marked?
	    private int[] id;           // id[v] = id of connected component containing v
	    private int[] size;         // size[id] = number of vertices in given component
	    private int count;          // number of connected components
	    private EdgeWeightedGraph Gr;
	    /**
	     * Computes the connected components of the undirected graph {@code G}.
	     *
	     * @param G the undirected graph
	     */
	    public CC(Graph G) {
	        marked = new boolean[G.V()];
	        id = new int[G.V()];
	        size = new int[G.V()];
	        for (int v = 0; v < G.V(); v++) {
	            if (!marked[v]) {
	                dfs(G, v);
	                count++;
	            }
	        }
	    }

	    /**
	     * Computes the connected components of the edge-weighted graph {@code G}.
	     *
	     * @param G the edge-weighted graph
	     */
	    public CC(EdgeWeightedGraph G) {
	    	Gr = G;
	        marked = new boolean[G.V()];
	        id = new int[G.V()];
	        size = new int[G.V()];
	        for (int v = 0; v < G.V(); v++) {
	            if (!marked[v]) {
	                dfs(v);
	                count++;
	            }
	        }
	    }

	    // depth-first search for a Graph
	    private void dfs(Graph G, int v) {
	        marked[v] = true;
	        id[v] = count;
	        size[count]++;
	        for (int w : G.adj(v)) {
	            if (!marked[w]) {
	                dfs(G, w);
	            }
	        }
	    }

	    // depth-first search for an EdgeWeightedGraph
	    private void dfs(int v) {
	        marked[v] = true;
	        id[v] = count;
	        size[count]++;
	        for (Edge e : Gr.adj(v)) {
	            int w = e.other(v);
	            if (!marked[w]) {
	                dfs(w);
	            }
	        }
	    }


	    /**
	     * Returns the component id of the connected component containing vertex {@code v}.
	     *
	     * @param  v the vertex
	     * @return the component id of the connected component containing vertex {@code v}
	     * @throws IllegalArgumentException unless {@code 0 <= v < V}
	     */
	    public int id(int v) {
	        validateVertex(v);
	        return id[v];
	    }

	    /**
	     * Returns the number of vertices in the connected component containing vertex {@code v}.
	     *
	     * @param  v the vertex
	     * @return the number of vertices in the connected component containing vertex {@code v}
	     * @throws IllegalArgumentException unless {@code 0 <= v < V}
	     */
	    public int size(int v) {
	        validateVertex(v);
	        return size[id[v]];
	    }

	    /**
	     * Returns the number of connected components in the graph {@code G}.
	     *
	     * @return the number of connected components in the graph {@code G}
	     */
	    public int count() {
	        return count;
	    }

	    /**
	     * Returns true if vertices {@code v} and {@code w} are in the same
	     * connected component.
	     *
	     * @param  v one vertex
	     * @param  w the other vertex
	     * @return {@code true} if vertices {@code v} and {@code w} are in the same
	     *         connected component; {@code false} otherwise
	     * @throws IllegalArgumentException unless {@code 0 <= v < V}
	     * @throws IllegalArgumentException unless {@code 0 <= w < V}
	     */
	    public boolean connected(int v, int w) {
	        validateVertex(v);
	        validateVertex(w);
	        return id(v) == id(w);
	    }

	    // throw an IllegalArgumentException unless {@code 0 <= v < V}
	    private void validateVertex(int v) {
	        int V = marked.length;
	        if (v < 0 || v >= V)
	            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
	    }
	    
	    public int[] ids() {
	    	int[] ids = new int[count+3000];
	    		for (int i = 0; i < id.length; i++) {
	    			int iD = id[i];
	    			boolean agregar = true;
	    			for (int j = 0; j < ids.length; j++) {
	    				int num = ids[j];
	    				if (iD == num) {
	    					agregar = false;
	    				}
	    			}
	    			if (agregar) {
	    				ids[ids.length-1] = iD;
	    			}
	    		}
	    	return ids;
	    }
	}
