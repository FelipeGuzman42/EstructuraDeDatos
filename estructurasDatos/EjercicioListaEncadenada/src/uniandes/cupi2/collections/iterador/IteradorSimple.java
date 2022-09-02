/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: IteradorSimple.java,v 1.24 2008/09/30 16:06:59 alf-mora Exp $
 * Universidad de los Andes (Bogot� - Colombia)
 * Departamento de Ingenier�a de Sistemas y Computaci�n 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Framework: Cupi2Collections
 * Autor: Jorge Villalobos - Abr 5, 2006
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.cupi2.collections.iterador;

/**
 * Implementaci�n de un iterador simple. A trav�s de este tipo de iterador s�lo se pueden recorrer los elementos de la estructura de datos sobre la que se encuentra asociada
 * pero no �sta no puede ser modificada.
 * @param <T> Tipo de datos sobre los que se itera
 */
public class IteradorSimple<T> implements Iterador<T>
{
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------
	
    /**
	 * Constante para la serializaci�n
	 */
	private static final long serialVersionUID = 1L;	

	/**
	 * Constante para posici�n del iterador por defecto
	 */
    private final static int NADA = -1;

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------
    /**
     * Elementos sobre los que se est� iterando
     */
    private T[] elems;

    /**
     * Posici�n que del pr�ximo elemento a ser visitado
     */
    private int posActual;

    /**
     * La siguiente posici�n libre en elems. Corresponde en realidad al n�mero de elementos sobre los que se est� iterando
     */
    private int sigPosLibre;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Constructor de un iterador con el tama�o (capacidad) especificado. <br>
     * <b> post: </b> Se cre� un iterador con la capacidad especificada.<br>
     * @param tamanio El tama�o que va a tener el iterador<br>
     */
    @SuppressWarnings("unchecked")
    public IteradorSimple( int tamanio )
    {
        elems = ( T[] )new Object[tamanio];
        sigPosLibre = 0;
        posActual = NADA;
    }

    // -----------------------------------------------------------------
    // M�todos: interface Iterador
    // -----------------------------------------------------------------
    /**
     * Indica si a�n hay elementos sobre los cuales iterar. <br>
     * <b>post: </b> Se retorn� true si a�n no se han recorrido todos los elementos o false en caso contrario.
     * @return True si a�n no se han recorrido todos los elementos o false en caso contrario
     */
    public boolean haySiguiente( )
    {
        return elems.length > 0 && ( posActual + 1 ) < sigPosLibre;
    }

    /**
     * Retorna el elemento a ser visitado. <br>
     * <b>pre: </b> A�n existe al menos un elemento sobre el cual iterar. <br>
     * <b>post: </b> Se retorn� el elemento a ser visitado. De no existir un siguiente se retorna null.
     * @return El elemento a ser visitado. De no existir un siguiente se retorna null.
     */
    public T darSiguiente( )
    {
        return haySiguiente( ) ? elems[ ++posActual ] : null;
    }

    /**
     * Retorna el �ltimo elemento visitado. <br>
     * <b>pre: </b> A�n existe al menos un elemento sobre el cual retroceder. <br>
     * <b>post: </b> Se retorn� el �ltimo elemento visitado.
     * @return El �ltimo elemento visitado o null si no hay elementos sobre los cuales retroceder.
     */
    public T darAnterior( )
    {
        return hayAnterior( ) ? elems[ --posActual ] : null;
    }

    /**
     * Indica si a�n hay elementos sobre los cuales retroceder. <br>
     * <b>post: </b> Se retorn� true si a�n hay elementos sobre los cuales retroceder o false en caso contrario.
     * @return True si a�n hay elementos sobre los cuales retroceder o false en caso contrario
     */
    public boolean hayAnterior( )
    {
        return elems.length > 0 && posActual > 0;
    }

    /**
     * Sit�a el iterador de nuevo al inicio de la colecci�n de datos con la que se encuentra asociado. <br>
     * <b>post: </b> El iterador se encuentra al inicio de la colecci�n de datos con la que se encuentra asociada.
     */
    public void reiniciar( )
    {
        posActual = NADA;
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Agrega un nuevo elemento al final del iterador. <br>
     * <b>post: </b> Se adicion� el elemento especificado en la �ltima posici�n del iterador, y sigPosLibre= sigPosLibre+1.
     * @throws IteradorException Si el iterador no tiene capacidad para m�s elementos
     */
    public void agregar( T elem ) throws IteradorException
    {
        if( sigPosLibre <= elems.length - 1 )
        {
            elems[ sigPosLibre++ ] = elem;
        }
        else
            throw new IteradorException( "L�mite del iterador alcanzado" );
    }

    /**
     * Inserta un nuevo elemento en la primera posici�n del iterador. <br>
     * <b>post: </b> Se adicion� el elemento especificado en la primera posici�n del iterador, y sigPosLibre= sigPosLibre+1. <br>
     * @throws IteradorException Si el iterador no tiene capacidad para m�s elementos
     */
    public void insertar( T elem ) throws IteradorException
    {
        if( sigPosLibre >= elems.length )
            throw new IteradorException( "L�mite del iterador alcanzado" );
        // Abre espacio para el nuevo elemento
        for( int i = sigPosLibre; i > 0; i-- )
        {
            elems[ i ] = elems[ i - 1 ];
        }
        sigPosLibre++;
        elems[ 0 ] = elem;
    }

    /**
     * Convierte el iterador a un String. <br>
     * <b>post: </b> Se retorn� la representaci�n en String del iterador. El String tiene el formato "[numeroElementos]: e1-e2-e3..-en", donde e1, e2, ..., en son los
     * elementos del iterador y numeroElementos su tama�o.
     * @return La representaci�n en String del iterador
     */
    @Override
    public String toString( )
    {
        String resp = "[" + sigPosLibre + "]:";
        for( int i = 0; i < sigPosLibre; i++ )
        {
            resp += elems[ i ] + "-";
        }
        return resp;
    }

    /**
     * Retorna la siguiente posici�n libre del iterador (n�mero de elementos sobre los que se est� iterando).<br>
     * <b>post: </b> Se retorn� la siguente posici�n libre en el iterador.
     * @return La siguente posici�n libre en el iterador
     */
    public int darSigPosLibre( )
    {
        return sigPosLibre;
    }

    /**
     * Retorna la posici�n del pr�ximo elemento a ser visitado. <br>
     * <b>post: </b> Se retorn� la posici�n del pr�ximo elemento a ser visitado.
     * @return La posici�n del pr�ximo elemento a ser visitado
     */
    public int darPosActual( )
    {
        return posActual;
    }

    /**
     * Retorna el tama�o del iterador (n�mero m�ximo de elementos que puede recorrer). <br>
     * <b>post: </b> Se retorn� el tama�o del iterador.
     * @return El tama�o del iterador
     */
    public int darLongitud( )
    {
        return elems.length;
    }
}
