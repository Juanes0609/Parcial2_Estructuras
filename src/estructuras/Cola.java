package estructuras;

/**
 * Cola generica (FIFO) implementada con nodos enlazados, sin ciclos.
 */
public class Cola<T> {
    private Nodo<T> frente;
    private Nodo<T> fin;
    private int tamano;

    public Cola() {
        this.frente = null;
        this.fin = null;
        this.tamano = 0;
    }

    // Se enlaza al final; si estaba vacia, el nuevo nodo es frente y fin: O(1)
    public void encolar(T valor) {
        Nodo<T> nuevo = new Nodo<>(valor);
        if (estaVacia()) {
            frente = nuevo;
        } else {
            fin.setSiguiente(nuevo);
        }
        fin = nuevo;
        tamano++;
    }

    public T desencolar() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola está vacía");
        }
        T valor = frente.getValor();
        frente = frente.getSiguiente();
        // Si la cola quedo vacia, el fin tampoco apunta a nada
        if (frente == null) {
            fin = null;
        }
        tamano--;
        return valor;
    }

    // Consulta el elemento del frente sin retirarlo
    public T frente() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola está vacía");
        }
        return frente.getValor();
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public int tamano() {
        return tamano;
    }

    // Del frente al final, construido de forma recursiva
    @Override
    public String toString() {
        if (estaVacia()) {
            return "(vacía)";
        }
        return toStringRecursivo(frente);
    }

    // Caso base: ultimo nodo. Caso recursivo: valor + " → " + resto de la cola
    private String toStringRecursivo(Nodo<T> nodo) {
        if (nodo.getSiguiente() == null) {
            return String.valueOf(nodo.getValor());
        }
        return nodo.getValor() + " → " + toStringRecursivo(nodo.getSiguiente());
    }
}
