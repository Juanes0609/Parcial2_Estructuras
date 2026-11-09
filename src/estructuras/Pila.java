package estructuras;

/**
 * Pila generica (LIFO) implementada con nodos enlazados.
 */
public class Pila<T> {
    private Nodo<T> cima;
    private int tamanio;

    public Pila() {
        this.cima = null;
        this.tamanio = 0;
    }

    // El nuevo nodo apunta al cima anterior y pasa a ser el cima: O(1)
    public void apilar(T valor) {
        Nodo<T> nuevo = new Nodo<>(valor);
        nuevo.setSiguiente(cima);
        cima = nuevo;
        tamanio++;
    }

    public T desapilar() {
        if (estaVacia()) {
            throw new IllegalStateException("La pila está vacía");
        }
        T valor = cima.getValor();
        cima = cima.getSiguiente();
        tamanio--;
        return valor;
    }

    // Consulta el elemento de la cima sin retirarlo
    public T cima() {
        if (estaVacia()) {
            throw new IllegalStateException("La pila está vacía");
        }
        return cima.getValor();
    }

    public boolean estaVacia() {
        return cima == null;
    }

    public int tamano() {
        return tamanio;
    }

    // Recorre de la cima al fondo
    @Override
    public String toString() {
        if (estaVacia()) {
            return "(vacía)";
        }
        StringBuilder sb = new StringBuilder();
        Nodo<T> actual = cima;
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
