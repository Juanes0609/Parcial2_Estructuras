package ejercicios;

import estructuras.Pila;

/**
 * Punto 2: obtener un segmento del historial (pila) sin modificar el original.
 */
public class Ejercicio2Historial {

    /**
     * Retorna una pila con los elementos de las posiciones inicio..fin (1 = cima).
     * La posicion inicio queda en la cima del segmento y el original no cambia.
     */
    public static <T> Pila<T> obtenerSegmento(Pila<T> original, int inicio, int fin) {
        Pila<T> segmento = new Pila<>();
        if (original == null || inicio < 1 || inicio > fin || fin > original.tamano()) {
            return segmento;
        }

        // Se sacan los primeros 'fin' elementos: la posicion 'fin' queda en la cima de aux
        Pila<T> aux = new Pila<>();
        for (int i = 0; i < fin; i++) {
            aux.apilar(original.desapilar());
        }

        // Se devuelven de 'fin' a 1; los que estan en el rango tambien van al segmento
        int pos = fin;
        while (!aux.estaVacia()) {
            T elemento = aux.desapilar();
            original.apilar(elemento);
            if (pos >= inicio) {
                segmento.apilar(elemento);
            }
            pos--;
        }
        return segmento;
    }

    public static void ejecutarPrueba() {
        System.out.println("===== PUNTO 2: Historial =====");
        Pila<String> historial = new Pila<>();
        historial.apilar("A1");
        historial.apilar("A2");
        historial.apilar("A3");
        historial.apilar("A4");
        historial.apilar("A5");
        historial.apilar("A6");
        System.out.println("Historial original (cima → fondo): " + historial);

        probarSegmento(historial, 2, 4);
        probarSegmento(historial, 3, 3);
        probarSegmento(historial, 0, 3);
        probarSegmento(historial, 4, 2);
        probarSegmento(historial, 2, 7);

        System.out.println("Historial despues de las llamadas: " + historial);
    }

    private static void probarSegmento(Pila<String> historial, int inicio, int fin) {
        Pila<String> segmento = obtenerSegmento(historial, inicio, fin);
        System.out.println("obtenerSegmento(inicio=" + inicio + ", fin=" + fin + ") = " + segmento);
    }
}
