package estructuras;

/**
 * Pila generica (LIFO) implementada con nodos enlazados.
 */
public class Pila<T> {
    private Nodo<T> tope;
    private int tamano;

    public Pila() {
        this.tope = null;
        this.tamano = 0;
    }

    // El nuevo nodo apunta al tope anterior y pasa a ser el tope: O(1)
    public void apilar(T valor) {
        Nodo<T> nuevo = new Nodo<>(valor);
        nuevo.setSiguiente(tope);
        tope = nuevo;
        tamano++;
    }

    public T desapilar() {
        if (estaVacia()) {
            throw new IllegalStateException("La pila está vacía");
        }
        T valor = tope.getValor();
        tope = tope.getSiguiente();
        tamano--;
        return valor;
    }

    // Consulta el elemento de la cima sin retirarlo
    public T cima() {
        if (estaVacia()) {
            throw new IllegalStateException("La pila está vacía");
        }
        return tope.getValor();
    }

    public boolean estaVacia() {
        return tope == null;
    }

    public int tamano() {
        return tamano;
    }

    // Recorre de la cima al fondo
    @Override
    public String toString() {
        if (estaVacia()) {
            return "(vacía)";
        }
        StringBuilder sb = new StringBuilder();
        Nodo<T> actual = tope;
        while (actual != null) {
            sb.append(actual.getValor());
            if (actual.getSiguiente() != null) {
                sb.append(" → ");
            }
            actual = actual.getSiguiente();
        }
        return sb.toString();
    }
}
