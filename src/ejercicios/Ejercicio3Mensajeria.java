package ejercicios;

import estructuras.Cola;
import modelo.Paquete;
import modelo.TipoEntrega;

/**
 * Punto 3: seleccion de paquetes para un vehiculo con capacidad limitada.
 * Toda la logica es recursiva, sin ciclos.
 */
public class Ejercicio3Mensajeria {

    /**
     * Selecciona primero los paquetes URGENTE y luego los NORMAL que quepan en la capacidad.
     * Los no seleccionados permanecen en 'pendientes' en su orden de registro.
     */
    public static Cola<Paquete> seleccionarPaquetes(Cola<Paquete> pendientes, int capacidad) {
        Cola<Paquete> seleccionados = new Cola<>();
        if (pendientes == null || capacidad <= 0) {
            return seleccionados;
        }

        // Pasada 1: urgentes. Pasada 2: normales con la capacidad que sobro
        int capacidadRestante = procesar(pendientes, seleccionados, TipoEntrega.URGENTE,
                pendientes.tamano(), capacidad);
        procesar(pendientes, seleccionados, TipoEntrega.NORMAL, pendientes.tamano(), capacidadRestante);
        return seleccionados;
    }

    /**
     * Rota la cola 'restantes' veces. Cada paquete del tipo indicado que quepa pasa a
     * 'seleccionados'; los demas se reencolan, asi conservan su orden. Retorna la capacidad final.
     */
    private static int procesar(Cola<Paquete> pendientes, Cola<Paquete> seleccionados,
                                TipoEntrega tipo, int restantes, int capacidad) {
        // Caso base: ya se revisaron todos los paquetes de esta pasada
        if (restantes == 0) {
            return capacidad;
        }

        Paquete actual = pendientes.desencolar();
        int capacidadActualizada = capacidad;

        if (actual.getTipoEntrega() == tipo && actual.getPeso() <= capacidad) {
            seleccionados.encolar(actual);
            capacidadActualizada = capacidad - actual.getPeso();
            System.out.println("  " + actual + " → seleccionado (capacidad restante: "
                    + capacidadActualizada + " kg)");
        } else if (actual.getTipoEntrega() == tipo) {
            pendientes.encolar(actual);
            System.out.println("  " + actual + " → pendiente, no cabe (capacidad restante: "
                    + capacidad + " kg)");
        } else {
            // Es de otro tipo: se reencola sin evaluarlo en esta pasada
            pendientes.encolar(actual);
        }

        // Caso recursivo: queda un paquete menos por revisar
        return procesar(pendientes, seleccionados, tipo, restantes - 1, capacidadActualizada);
    }

    /**
     * Suma recursivamente los pesos de una cola rotandola completa, sin modificarla.
     */
    private static int sumarPesos(Cola<Paquete> cola, int restantes) {
        if (restantes == 0) {
            return 0;
        }
        Paquete actual = cola.desencolar();
        cola.encolar(actual);
        return actual.getPeso() + sumarPesos(cola, restantes - 1);
    }

    public static void ejecutarPrueba() {
        System.out.println("===== PUNTO 3: Mensajeria =====");
        int capacidad = 14;
        Cola<Paquete> pendientes = crearColaPrueba();
        System.out.println("Cola inicial: " + pendientes);
        System.out.println("Capacidad del vehiculo: " + capacidad + " kg");
        System.out.println("Paquetes evaluados:");

        Cola<Paquete> seleccionados = seleccionarPaquetes(pendientes, capacidad);
        int capacidadRestante = capacidad - sumarPesos(seleccionados, seleccionados.tamano());

        System.out.println("Cola retornada (seleccionados): " + seleccionados);
        System.out.println("Pendientes: " + pendientes);
        System.out.println("Capacidad restante: " + capacidadRestante + " kg");

        System.out.println();
        System.out.println("Caso extra: capacidad 0");
        Cola<Paquete> pendientesExtra = crearColaPrueba();
        Cola<Paquete> seleccionadosExtra = seleccionarPaquetes(pendientesExtra, 0);
        System.out.println("Cola retornada (seleccionados): " + seleccionadosExtra);
        System.out.println("Pendientes: " + pendientesExtra);
    }

    private static Cola<Paquete> crearColaPrueba() {
        Cola<Paquete> cola = new Cola<>();
        cola.encolar(new Paquete("P1", TipoEntrega.NORMAL, 4));
        cola.encolar(new Paquete("P2", TipoEntrega.URGENTE, 7));
        cola.encolar(new Paquete("P3", TipoEntrega.NORMAL, 3));
        cola.encolar(new Paquete("P4", TipoEntrega.URGENTE, 5));
        cola.encolar(new Paquete("P5", TipoEntrega.NORMAL, 6));
        cola.encolar(new Paquete("P6", TipoEntrega.URGENTE, 4));
        cola.encolar(new Paquete("P7", TipoEntrega.NORMAL, 2));
        return cola;
    }
}
