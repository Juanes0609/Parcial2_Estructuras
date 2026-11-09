package ejercicios;

import estructuras.Pila;
import modelo.Caja;

/**
 * Punto 1: retiro y reorganizacion de cajas en un deposito (pila).
 */
public class Ejercicio1Deposito {

    /**
     * Retira la caja con el codigo dado y reorganiza la pila por posiciones.
     * Si el valor de la caja retirada es par, las posiciones impares quedan arriba;
     * si es impar, las posiciones pares quedan arriba. Se conserva el orden relativo.
     */
    public static boolean retirarCaja(Pila<Caja> pila, String codigo) {
        if (pila == null || codigo == null || pila.estaVacia()) {
            return false;
        }

        // Busqueda: se desapila hacia aux hasta encontrar el codigo o vaciar la pila
        Pila<Caja> aux = new Pila<>();
        while (!pila.estaVacia() && !pila.cima().getCodigo().equals(codigo)) {
            aux.apilar(pila.desapilar());
        }

        // No encontrado: se devuelve todo y la pila queda igual
        if (pila.estaVacia()) {
            volcar(aux, pila);
            return false;
        }

        // Encontrado: se retira la caja y se devuelven las de encima
        Caja retirada = pila.desapilar();
        volcar(aux, pila);

        // Separar por posicion (1 = cima). Ambas pilas quedan invertidas
        Pila<Caja> impares = new Pila<>();
        Pila<Caja> pares = new Pila<>();
        int posicion = 1;
        while (!pila.estaVacia()) {
            if (posicion % 2 != 0) {
                impares.apilar(pila.desapilar());
            } else {
                pares.apilar(pila.desapilar());
            }
            posicion++;
        }

        // Reorganizar: al volcar se restaura el orden relativo.
        // Lo que se vuelca primero queda al fondo.
        if (retirada.getValor() % 2 == 0) {
            volcar(pares, pila);
            volcar(impares, pila);
        } else {
            volcar(impares, pila);
            volcar(pares, pila);
        }
        return true;
    }

    // Pasa todos los elementos de origen a destino (invierte el orden)
    private static void volcar(Pila<Caja> origen, Pila<Caja> destino) {
        while (!origen.estaVacia()) {
            destino.apilar(origen.desapilar());
        }
    }

    public static void ejecutarPrueba() {
        System.out.println("===== PUNTO 1: Deposito de cajas =====");
        Pila<Caja> pila = new Pila<>();
        pila.apilar(new Caja("C1", 2));
        pila.apilar(new Caja("C2", 4));
        pila.apilar(new Caja("C3", 6));
        pila.apilar(new Caja("C4", 8));
        pila.apilar(new Caja("C5", 3));
        pila.apilar(new Caja("C6", 9));
        pila.apilar(new Caja("C7", 5));
        System.out.println("Pila inicial (cima → fondo): " + pila);

        probarRetiro(pila, "C3");
        probarRetiro(pila, "C4");
        probarRetiro(pila, "C7");

        System.out.println("Pila final: " + pila);
    }

    private static void probarRetiro(Pila<Caja> pila, String codigo) {
        boolean resultado = retirarCaja(pila, codigo);
        System.out.println("retirarCaja(" + codigo + ") = " + resultado + " | Pila: " + pila);
    }
}
