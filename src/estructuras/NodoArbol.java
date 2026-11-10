package estructuras;

/**
 * Nodo del árbol binario de búsqueda. La clave es el código entero del equipo.
 */
public class NodoArbol {

    private final int codigo;
    private NodoArbol izquierdo;
    private NodoArbol derecho;

    public NodoArbol(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public NodoArbol getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoArbol izquierdo) {
        this.izquierdo = izquierdo;
    }

    public NodoArbol getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoArbol derecho) {
        this.derecho = derecho;
    }
}
