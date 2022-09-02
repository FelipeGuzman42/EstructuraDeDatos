package generics;

interface ITupla <T1, T2>{
	public void set1(T1 v1);
	public T1 get1();
	public void set2(T2 v2);
	public T2 get2();
}

public class Tupla<T1, T2> implements ITupla<T1, T2>{
	private T1 v1;
	private T2 v2;
	
	public void set1(T1 v1) {
		this.v1 = v1;
	}
	
	public T1 get1() {
		return v1;
	}
	
	public void set2(T2 v2) {
		this.v2 = v2;
	}
	
	public T2 get2() {
		return v2;
	}
	
}
