package model.data_structures;

public interface IMaxHeapCP<T> {

	/**
	 * Retornar el numero de elementos presentes en el arreglo
	 * @return
	 */
	int darTamano( );
	
	/**
	 * Agregar el nuevo datos.
	 * @param dato nuevo elemento
	 */
	public void insertar( T dato );
	
	/**
	 * Eliminar el dato maximo del arreglo
	 * @return dato eliminado
	 */
	public T eliminarMax();
	/**
	 * Devuelve el dato maximo
	 * @return dato maximo
	 */
	public T getMax();

}
