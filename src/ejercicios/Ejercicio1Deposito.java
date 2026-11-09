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

    // Expresion en postfija (la conversion desde infija se entrega en el Excel)
    private static final String POSTFIJA = "AB+C*AB-D*-";

    /**
     * Calcula A, B, C y D con los valores de la pila final y evalua la postfija
     * AB+C*AB-D*- usando una pila de Double. La pila recibida no se modifica.
     */
    public static double evaluarExpresion(Pila<Caja> pilaFinal) {
        if (pilaFinal == null || pilaFinal.tamano() < 4) {
            throw new IllegalArgumentException("La pila debe tener al menos 4 cajas");
        }

        // Leer los 4 valores de la cima hacia abajo y restaurar la pila
        Pila<Caja> aux = new Pila<>();
        Caja caja1 = pilaFinal.desapilar();
        aux.apilar(caja1);
        Caja caja2 = pilaFinal.desapilar();
        aux.apilar(caja2);
        Caja caja3 = pilaFinal.desapilar();
        aux.apilar(caja3);
        Caja caja4 = pilaFinal.desapilar();
        aux.apilar(caja4);
        volcar(aux, pilaFinal);

        double a = caja1.getValor();
        double b = caja2.getValor() / a;
        double c = caja3.getValor() + a;
        double d = (caja4.getValor() + c) * a;

        System.out.println("A = " + formatear(a) + ", B = " + formatear(b)
                + ", C = " + formatear(c) + ", D = " + formatear(d));
        System.out.println("Postfija: " + POSTFIJA + " → " + postfijaConValores(a, b, c, d));
        System.out.println("Traza de evaluacion:");

        Pila<Double> pila = new Pila<>();
        for (int i = 0; i < POSTFIJA.length(); i++) {
            char simbolo = POSTFIJA.charAt(i);
            String accion;
            if (esOperador(simbolo)) {
                // El primero que sale es el operando derecho
                double op2 = pila.desapilar();
                double op1 = pila.desapilar();
                double z = operar(op1, op2, simbolo);
                pila.apilar(z);
                accion = "Op2 = " + formatear(op2) + ", Op1 = " + formatear(op1) + ", Z = "
                        + formatear(op1) + " " + simbolo + " " + formatear(op2) + " = " + formatear(z)
                        + ", apilar Z";
            } else {
                double valor = valorVariable(simbolo, a, b, c, d);
                pila.apilar(valor);
                accion = "apilar " + simbolo + " = " + formatear(valor);
            }
            System.out.println("  " + simbolo + " | " + accion + " | Pila: " + pilaTexto(pila));
        }

        double resultado = pila.desapilar();
        System.out.println("Resultado = " + resultado);
        return resultado;
    }

    private static boolean esOperador(char simbolo) {
        return simbolo == '+' || simbolo == '-' || simbolo == '*' || simbolo == '/';
    }

    private static double operar(double op1, double op2, char operador) {
        return switch (operador) {
            case '+' -> op1 + op2;
            case '-' -> op1 - op2;
            case '*' -> op1 * op2;
            case '/' -> op1 / op2;
            default -> throw new IllegalArgumentException("Operador no valido: " + operador);
        };
    }

    private static double valorVariable(char variable, double a, double b, double c, double d) {
        return switch (variable) {
            case 'A' -> a;
            case 'B' -> b;
            case 'C' -> c;
            case 'D' -> d;
            default -> throw new IllegalArgumentException("Variable no valida: " + variable);
        };
    }

    // Reemplaza cada variable de la postfija por su valor calculado
    private static String postfijaConValores(double a, double b, double c, double d) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < POSTFIJA.length(); i++) {
            char simbolo = POSTFIJA.charAt(i);
            if (i > 0) {
                sb.append(' ');
            }
            if (esOperador(simbolo)) {
                sb.append(simbolo);
            } else {
                sb.append(formatear(valorVariable(simbolo, a, b, c, d)));
            }
        }
        return sb.toString();
    }

    // Pila de Double con los valores formateados (cima → fondo)
    private static String pilaTexto(Pila<Double> pila) {
        if (pila.estaVacia()) {
            return "(vacía)";
        }
        Pila<Double> aux = new Pila<>();
        StringBuilder sb = new StringBuilder();
        while (!pila.estaVacia()) {
            double valor = pila.desapilar();
            if (sb.length() > 0) {
                sb.append(" → ");
            }
            sb.append(formatear(valor));
            aux.apilar(valor);
        }
        while (!aux.estaVacia()) {
            pila.apilar(aux.desapilar());
        }
        return sb.toString();
    }

    // Muestra 6 en vez de 6.0, pero conserva decimales como 4.5
    private static String formatear(double numero) {
        if (numero == Math.rint(numero)) {
            return String.valueOf((long) numero);
        }
        return String.valueOf(numero);
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

        System.out.println();
        System.out.println("Evaluacion de la expresion:");
        evaluarExpresion(pila);
    }

    private static void probarRetiro(Pila<Caja> pila, String codigo) {
        boolean resultado = retirarCaja(pila, codigo);
        System.out.println("retirarCaja(" + codigo + ") = " + resultado + " | Pila: " + pila);
    }
}
