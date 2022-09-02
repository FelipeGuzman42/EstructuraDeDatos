package model.data_structures;

public class Edge implements Comparable<Edge> { 
	
	//Codigo tomado de: https://github.com/kevin-wayne/algs4/blob/master/src/main/java/edu/princeton/cs/algs4/Edge.java
	//Copyright 2002-2019, Robert Sedgewick and Kevin Wayne.
	//Se agrego método setWeight y se cambio weight para quitar final. 
	//Se agregaron los tres tipos de costos.

    private final int v;
    private final int w;
    private double weightHarversine;
    private double weightTiempo;
    private double weightVelocidad;

    /**
     * Initializes an edge between vertices {@code v} and {@code w} of
     * the given {@code weight}.
     *
     * @param  v one vertex
     * @param  w the other vertex
     * @param  weight the weight of this edge
     * @throws IllegalArgumentException if either {@code v} or {@code w} 
     *         is a negative integer
     * @throws IllegalArgumentException if {@code weight} is {@code NaN}
     */
    public Edge(int v, int w, double weightHarversine, double weightTiempo, double weightVelocidad) {
        if (v < 0) throw new IllegalArgumentException("vertex index must be a nonnegative integer");
        if (w < 0) throw new IllegalArgumentException("vertex index must be a nonnegative integer");
        if (Double.isNaN(weightHarversine)) throw new IllegalArgumentException("Weight is NaN");
        if (Double.isNaN(weightTiempo)) throw new IllegalArgumentException("Weight is NaN");
        if (Double.isNaN(weightVelocidad)) throw new IllegalArgumentException("Weight is NaN");
        this.v = v;
        this.w = w;
        this.weightHarversine = weightHarversine;
        this.weightTiempo = weightTiempo;
        this.weightVelocidad = weightVelocidad;
    }

    /**
     * Returns the weight of this edge.
     *
     * @return the weight of this edge
     */
    public double weightHarversine() {
        return weightHarversine;
    }
    
    public double weightTiempo() {
        return weightTiempo;
    }
    
    public double weightVelocidad() {
        return weightVelocidad;
    }

    /**
     * Returns either endpoint of this edge.
     *
     * @return either endpoint of this edge
     */
    public int either() {
        return v;
    }

    /**
     * Returns the endpoint of this edge that is different from the given vertex.
     *
     * @param  vertex one endpoint of this edge
     * @return the other endpoint of this edge
     * @throws IllegalArgumentException if the vertex is not one of the
     *         endpoints of this edge
     */
    public int other(int vertex) {
        if      (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new IllegalArgumentException("Illegal endpoint");
    }
    
    public void setWeight(double pWeightHarversine, double pWeightTiempo, double pWeightVelocidad) {
    	this.weightHarversine = pWeightHarversine;
    	this.weightTiempo = pWeightTiempo;
    	this.weightVelocidad = pWeightVelocidad;
    }

    /**
     * Compares two edges by weight.
     * Note that {@code compareTo()} is not consistent with {@code equals()},
     * which uses the reference equality implementation inherited from {@code Object}.
     *
     * @param  that the other edge
     * @return a negative integer, zero, or positive integer depending on whether
     *         the weight of this is less than, equal to, or greater than the
     *         argument edge
     */
    @Override
    public int compareTo(Edge that) {
        return Double.compare(this.weightHarversine, that.weightHarversine);
    }

}
