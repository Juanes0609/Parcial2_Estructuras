package modelo;

/**
 * Paquete de la empresa de mensajeria.
 */
public class Paquete {
    private final String codigo;
    private final TipoEntrega tipoEntrega;
    private final int peso;

    public Paquete(String codigo, TipoEntrega tipoEntrega, int peso) {
        if (codigo == null || tipoEntrega == null) {
            throw new IllegalArgumentException("El codigo y el tipo de entrega no pueden ser nulos");
        }
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor que 0");
        }
        this.codigo = codigo;
        this.tipoEntrega = tipoEntrega;
        this.peso = peso;
    }

    public String getCodigo() {
        return codigo;
    }

    public TipoEntrega getTipoEntrega() {
        return tipoEntrega;
    }

    public int getPeso() {
        return peso;
    }

    @Override
    public String toString() {
        return codigo + " (" + tipoEntrega + ", " + peso + " kg)";
    }
}
