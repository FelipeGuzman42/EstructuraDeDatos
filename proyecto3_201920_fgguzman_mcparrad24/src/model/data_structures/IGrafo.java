package model.data_structures;

public interface IGrafo <K, V> {
	
	int V();
	
	int E();
	
	void addEdge(K idVertexIni, K idVertexFin, double costHarversine, double costTiempo, double costVelocidad);
	
	V getInfoVertex(K idVertex);
	
	void setInfoVertex(K idVertex, V infoVertex);
	
	double getCostArcHarversine(K idVertexIni, K idVertexFin);
	
	double getCostArcTiempo(K idVertexIni, K idVertexFin);
	
	double getCostArcVelocidad(K idVertexIni, K idVertexFin);
	
	void setCostArc(K idVertexIni, K idVertexFin, double costHarversine, double costTiempo, double costVelocidad);
	
	void addVertex(K idVertex, V infoVertex);
	
	Iterable<K> adj(K idVertex);
	
	void uncheck();
	
	void dfs(K s);
	
	int cc();
	
	Iterable<K> getCC(K idVertex);
	
}
