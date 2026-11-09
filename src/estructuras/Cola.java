package estructuras;

/**
 * Cola generica (FIFO) implementada con nodos enlazados, sin ciclos.
 */
public class Cola<T> {
    private Nodo<T> inicio;
    private Nodo<T> fin;
    private int tamanio;

    public Cola() {
        this.inicio = null;
        this.fin = null;
        this.tamanio = 0;
    }

    // Se enlaza al final; si estaba vacia, el nuevo nodo es inicio y fin: O(1)
    public void encolar(T valor) {
        Nodo<T> nuevo = new Nodo<>(valor);
        if (estaVacia()) {
            inicio = nuevo;
        } else {
            fin.setSiguiente(nuevo);
        }
        fin = nuevo;
        tamanio++;
    }

    public T desencolar() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola está vacía");
        }
        T valor = inicio.getValor();
        inicio = inicio.getSiguiente();
        // Si la cola quedo vacia, el fin tampoco apunta a nada
        if (inicio == null) {
            fin = null;
        }
        tamanio--;
        return valor;
    }

    // Consulta el elemento del inicio sin retirarlo
    public T frente() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola está vacía");
        }
        return inicio.getValor();
    }

    public boolean estaVacia() {
        return inicio == null;
    }

    public int tamano() {
        return tamanio;
    }

    // Del inicio al final, construido de forma recursiva
    @Override
    public String toString() {
        if (estaVacia()) {
            return "(vacía)";
        }
        return toStringRecursivo(inicio);
    }

    // Caso base: ultimo nodo. Caso recursivo: valor + " → " + resto de la cola
    private String toStringRecursivo(Nodo<T> nodo) {
        if (nodo.getSiguiente() == null) {
            return String.valueOf(nodo.getValor());
        }
        return nodo.getValor() + " → " + toStringRecursivo(nodo.getSiguiente());
    }
}
