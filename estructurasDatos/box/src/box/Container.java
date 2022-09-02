package box;

public interface Container<T> extends Iterable<T>{
	public void add(T elem);
}
