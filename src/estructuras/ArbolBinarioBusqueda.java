package estructuras;

/**
 * Árbol binario de búsqueda propio con claves enteras únicas.
 * Los códigos repetidos se ignoran (los códigos de equipo son únicos).
 */
public class ArbolBinarioBusqueda {

    private NodoArbol raiz;

    public NodoArbol getRaiz() {
        return raiz;
    }

    public boolean estaVacio() {
        return raiz == null;
    }

    /** Inserta el código; retorna false si ya existía. */
    public boolean insertar(int codigo) {
        if (raiz == null) {
            raiz = new NodoArbol(codigo);
            return true;
        }
        return insertar(raiz, codigo);
    }

    private boolean insertar(NodoArbol actual, int codigo) {
        if (codigo == actual.getCodigo()) {
            return false;
        }
        if (codigo < actual.getCodigo()) {
            if (actual.getIzquierdo() == null) {
                actual.setIzquierdo(new NodoArbol(codigo));
                return true;
            }
            return insertar(actual.getIzquierdo(), codigo);
        }
        if (actual.getDerecho() == null) {
            actual.setDerecho(new NodoArbol(codigo));
            return true;
        }
        return insertar(actual.getDerecho(), codigo);
    }

    /** Recorrido inorden (izquierdo, raíz, derecho) separado por " → ". */
    public String inorden() {
        StringBuilder sb = new StringBuilder();
        inorden(raiz, sb);
        return sb.toString();
    }

    private void inorden(NodoArbol nodo, StringBuilder sb) {
        if (nodo == null) {
            return;
        }
        inorden(nodo.getIzquierdo(), sb);
        if (!sb.isEmpty()) {
            sb.append(" → ");
        }
        sb.append(nodo.getCodigo());
        inorden(nodo.getDerecho(), sb);
    }
}
