package box;

public interface Box<T> extends Iterable<T>{
	public void put(T o);
	public void delete();
	public T get();
}
