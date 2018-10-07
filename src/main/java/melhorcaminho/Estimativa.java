package melhorcaminho;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Estimativa {
    private final String destino;
    private String precedente;
    private int custo;
    private Status status;

    public Estimativa(String destino, String precedente, int custo, Status status) {
        this.destino = destino;
        this.precedente = precedente;
        this.custo = custo;
        this.status = status;
    }

    public boolean aberta() {
        return this.status == Status.aberto;
    }

    public void fecha() {
        this.status = Status.fechado;
    }

    public enum Status {
        aberto, fechado, zero;
    }
}
