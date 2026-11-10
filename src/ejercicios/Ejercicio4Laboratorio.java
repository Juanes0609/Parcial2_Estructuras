package ejercicios;

import estructuras.ArbolBinarioBusqueda;
import estructuras.NodoArbol;

/**
 * Punto 4a: consulta de equipos por rango en un ABB.
 */
public class Ejercicio4Laboratorio {

    /**
     * Cuenta los códigos que están en [limiteInferior, limiteSuperior], incluidos los extremos.
     * Aprovecha el orden del ABB para omitir subárboles:
     * - si el código del nodo es menor que el límite inferior, todo su subárbol izquierdo también lo es;
     * - si es mayor que el límite superior, todo su subárbol derecho también lo es.
     */
    public static int contarEquiposEnRango(NodoArbol raiz, int limiteInferior, int limiteSuperior) {
        if (raiz == null || limiteInferior > limiteSuperior) {
            return 0;
        }
        if (raiz.getCodigo() < limiteInferior) {
            return contarEquiposEnRango(raiz.getDerecho(), limiteInferior, limiteSuperior);
        }
        if (raiz.getCodigo() > limiteSuperior) {
            return contarEquiposEnRango(raiz.getIzquierdo(), limiteInferior, limiteSuperior);
        }
        return 1
                + contarEquiposEnRango(raiz.getIzquierdo(), limiteInferior, limiteSuperior)
                + contarEquiposEnRango(raiz.getDerecho(), limiteInferior, limiteSuperior);
    }

    public static void ejecutarPrueba() {
        System.out.println("=== PUNTO 4a: Consulta de equipos por rango (ABB) ===");

        int[] codigos = {41, 20, 52, 16, 6, 13, 12, 60, 22, 47, 50, 60, 43, 44};
        ArbolBinarioBusqueda arbol = new ArbolBinarioBusqueda();
        for (int codigo : codigos) {
            if (!arbol.insertar(codigo)) {
                System.out.println("Código " + codigo + " repetido: se ignora (los códigos son únicos).");
            }
        }
        System.out.println("Inorden: " + arbol.inorden());

        mostrarConsulta(arbol.getRaiz(), 15, 45);
        mostrarConsulta(arbol.getRaiz(), 40, 52);
        mostrarConsulta(arbol.getRaiz(), 6, 60);
        mostrarConsulta(arbol.getRaiz(), 23, 40);
        mostrarConsulta(arbol.getRaiz(), 50, 20);
        mostrarConsulta(new ArbolBinarioBusqueda().getRaiz(), 1, 100);
        System.out.println();
    }

    private static void mostrarConsulta(NodoArbol raiz, int inferior, int superior) {
        int total = contarEquiposEnRango(raiz, inferior, superior);
        String arbol = raiz == null ? " (árbol vacío)" : "";
        System.out.println("Rango [" + inferior + ", " + superior + "]" + arbol + " → " + total + " equipos");
    }
}
