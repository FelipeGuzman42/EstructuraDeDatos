package model.data_structures;

public class Grafo<K, IV> implements IGrafo<K, IV> {

	//Código tomado de: https://github.com/le99/estructurasDatos/blob/master/Graph/src/data_struct/Grafo.java

	private EdgeWeightedGraph g;
	private LinearProbingHashST<K, Integer> keyToInteger;
	private LinearProbingHashST<Integer, K> integerToKey;
	private boolean[] marked;    // marked[v] = is there an s-v path?

	private int ultimoId;
	private LinearProbingHashST<K, IV> infoVertex;


	public Grafo (int V) {
		g = new EdgeWeightedGraph(V);
		keyToInteger = new LinearProbingHashST<K, Integer>();
		integerToKey = new LinearProbingHashST<Integer, K>();
		infoVertex =  new LinearProbingHashST<K, IV>();
		ultimoId = 0;
		marked = new boolean [V];
	}

	public int V() {
		return g.V();
	}

	public int E() {
		return g.E();
	}

	public void addEdge(K idVertexIni, K idVertexFin, double costHarversine, double costTiempo, double costVelocidad) {		
		Edge e = new Edge(keyToInteger.get(idVertexIni), keyToInteger.get(idVertexFin), costHarversine, costTiempo, costVelocidad);
		g.addEdge(e);
	}

	public void addVertex(K idVertex, IV infoV) {
		if(ultimoId >= g.V()) {
			//resize, muy ineficiente
			EdgeWeightedGraph g2 = new EdgeWeightedGraph(ultimoId + 1);
			for(int n = 0; n < g.V(); n++) {
				for(Edge e: g.adj(n)) {
					g2.addEdge(e);
				}
			}
			g = g2;
		}

		infoVertex.put(idVertex, infoV);
		keyToInteger.put(idVertex, ultimoId);
		integerToKey.put(ultimoId, idVertex);
		ultimoId++;

	}


	public Iterable <K> adj (K idVertex){
		Iterable<Edge> adjacentes = g.adj(keyToInteger.get(idVertex));
		Bag<K> res = new Bag<>();

		for(Edge e: adjacentes) {
			K id1 = integerToKey.get(e.either());
			if(id1.equals(idVertex)) {
				K id2 = integerToKey.get(e.other(e.either()));
				res.add(id2);
			}
			else {
				res.add(id1);
			}

		}

		return res;
	}
	
	public Iterable<Edge> arcosAdj (K idVertex){
		Iterable<Edge> adyacentes = g.adj(keyToInteger.get(idVertex));
		return adyacentes;
	}

	public IV getInfoVertex(K idVertex) {
		return infoVertex.get(idVertex);
	}

	public void setInfoVertex(K idVertex, IV infoV) {
		infoVertex.put(idVertex, infoV);
	}


	private CC calculatedCC;

	public int cc() {
		calculatedCC = new CC(g);
		return calculatedCC.count();
	}

	public Iterable<K> getCC(K idVertex){
		Bag<K> res = new Bag<>();
		cc();
		int id = calculatedCC.id(keyToInteger.get(idVertex));
		for(int n = 0; n < g.V(); n++) {
			if(calculatedCC.id(n) == id) {
				res.add(integerToKey.get(n));
			}
		}
		return res;
	}

	public double getCostArcHarversine(K idVertexIni, K idVertexFin) {
		double costo = -1;
		Iterable<Edge> edges = g.edges();
		int id1 = keyToInteger.get(idVertexIni);
		int id2 = keyToInteger.get(idVertexFin);
		for(Edge e: edges) {
			int v1 = e.either();
			int v2 = e.other(v1);
			if ((v1 == id1 && v2 == id2) || (v1 == id2 && v2 == id1)) {
				costo = e.weightHarversine();
			}
		}
		return costo;
	}
	
	public double getCostArcTiempo(K idVertexIni, K idVertexFin) {
		double costo = -1;
		Iterable<Edge> edges = g.edges();
		int id1 = keyToInteger.get(idVertexIni);
		int id2 = keyToInteger.get(idVertexFin);
		for(Edge e: edges) {
			int v1 = e.either();
			int v2 = e.other(v1);
			if ((v1 == id1 && v2 == id2) || (v1 == id2 && v2 == id1)) {
				costo = e.weightTiempo();
			}
		}
		return costo;
	}
	
	public double getCostArcVelocidad(K idVertexIni, K idVertexFin) {
		double costo = -1;
		Iterable<Edge> edges = g.edges();
		int id1 = keyToInteger.get(idVertexIni);
		int id2 = keyToInteger.get(idVertexFin);
		for(Edge e: edges) {
			int v1 = e.either();
			int v2 = e.other(v1);
			if ((v1 == id1 && v2 == id2) || (v1 == id2 && v2 == id1)) {
				costo = e.weightVelocidad();
			}
		}
		if((Double)costo == null) return -1;
		return costo;
	}

	public void setCostArc(K idVertexIni, K idVertexFin, double costHarversine, double costTiempo, double costVelocidad) {
		Iterable<Edge> edges = g.edges();
		int id1 = keyToInteger.get(idVertexIni);
		int id2 = keyToInteger.get(idVertexFin);
		for(Edge e: edges) {
			int v1 = e.either();
			int v2 = e.other(v1);
			if ((v1 == id1 && v2 == id2) || (v1 == id2 && v2 == id1)) {
				e.setWeight(costHarversine, costTiempo, costVelocidad);
			}
		}
	}
	
	@Override
	public void uncheck() {
		for (int i = 0; i < g.V(); i++) {
			if (marked[i]) {
				marked[i] = false;
			}
		}
	}

	public void dfs(K v) { //Este metodo se tomo de: https://github.com/kevin-wayne/algs4/blob/master/src/main/java/edu/princeton/cs/algs4/DepthFirstSearch.java
						   //De todas formas se adapto para que funcionara con la clase Grafo que se utiliza actualmente
		int id = keyToInteger.get(v);
		marked[id] = true;
		for (K w : adj(v)) {
			int idw = keyToInteger.get(w);
			if (!marked[idw]) {
				dfs(w);
			}
		}
	}
	
	public boolean[] marcados(){
		return marked;
	}
	
	public int[] ids() {
		return calculatedCC.ids();
	}
	
	public CC calculatedComps() {
		return calculatedCC;
	}
	
	public EdgeWeightedGraph edgeWeightedGrap() {
		return g;
	}
	
	public LinearProbingHashST<K, Integer> keyToInteger(){
		return keyToInteger;
	}

}