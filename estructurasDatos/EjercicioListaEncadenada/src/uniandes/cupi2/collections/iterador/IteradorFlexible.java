/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: IteradorFlexible.java,v 1.5 2008/09/30 16:06:59 alf-mora Exp $
 * Universidad de los Andes (Bogot� - Colombia)
 * Departamento de Ingenier�a de Sistemas y Computaci�n 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Framework: Cupi2Collections
 * Autor: Jorge Villalobos - 25/02/2006
 * Autor: Pablo Barvo - 25/02/2006
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package uniandes.cupi2.collections.iterador;

import uniandes.cupi2.collections.iterador.Iterador;
import uniandes.cupi2.collections.listaEncadenada.ListaEncadenada;

/**
 * Implementaci�n de un iterador compuesto. A trav�s de este tipo de iterador se pueden recorrer los elementos de la estructura de datos sobre la que se encuentra asociada
 * pero �sta no puede ser modificada. Tambi�n se pueden agregar e insertar elementos.
 * @param <T> Tipo de datos sobre los que se itera
 */
public class IteradorFlexible<T> extends ListaEncadenada<T> implements Iterador<T>
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
     * Posici�n que del pr�ximo elemento a ser visitado
     */
    private int posActual;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------
    /**
     * Constructor del iterador. <br>
     * <b> post: </b> Se cre� un iterador vaci�.
     */
    public IteradorFlexible( )
    {
        super( );
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
        return ( posActual + 1 ) < darLongitud( );
    }

    /**
     * Retorna el elemento a ser visitado. <br>
     * <b>pre: </b> A�n existe al menos un elemento sobre el cual iterar. <br>
     * <b>post: </b> Se retorn� el elemento a ser visitado.
     * @return True si a�n no se han recorrido todos los elementos o false en caso contrario
     */
    public T darSiguiente( )
    {
        return haySiguiente( ) ? dar( ++posActual ) : null;
    }
    
    /**
     * Retorna el �ltimo elemento visitado. <br>
     * <b>pre: </b> A�n existe al menos un elemento sobre el cual retroceder. <br>
     * <b>post: </b> Se retorn� el �ltimo elemento visitado.
     * @return El �ltimo elemento visitado o null si no hay elementos sobre los cuales retroceder.
     */
    public T darAnterior( )
    {
        return hayAnterior( ) ? dar(--posActual) : null;
    }

    /**
     * Indica si a�n hay elementos sobre los cuales retroceder. <br>
     * <b>post: </b> Se retorn� true si a�n hay elementos sobre los cuales retroceder o false en caso contrario.
     * @return True si a�n hay elementos sobre los cuales retroceder o false en caso contrario
     */
    public boolean hayAnterior( )
    {
        return posActual > 0;
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
     * Retorna la posici�n del pr�ximo elemento a ser visitado. <br>
     * <b>post: </b> Se retorn� la posici�n del pr�ximo elemento a ser visitado.
     * @return La posici�n del pr�ximo elemento a ser visitado
     */
    public int darPosActual( )
    {
        return posActual;
    }

    /**
     * Agrega un nuevo elemento al final del iterador. Este m�todo hace lo mismo que insertarCola <br>
     * <b>post: </b> Se adicion� el elemento especificado en la �ltima posici�n del iterador.
     */
    public void agregar( T elem )
    {
        insertarCola( elem );
    }

    /**
     * Inserta un nuevo elemento en la primera posici�n del iterador. Este m�todo hace lo mismo que insertarCabeza <br>
     * <b>post: </b> Se adicion� el elemento especificado en la primera posici�n del iterador. <br>
     */
    public void insertar( T elem )
    {
        insertarCabeza( elem );
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
        String resp = "[" + darLongitud( ) + "]:";
        for( int i = 0; i < darLongitud( ); i++ )
        {
            resp += dar( i ) + "-";
        }
        return resp;
    }
}
