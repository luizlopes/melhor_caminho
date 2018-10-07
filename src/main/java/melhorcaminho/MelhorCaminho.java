package melhorcaminho;

import java.util.*;
import java.util.stream.Collectors;

public class MelhorCaminho {
    private static final Estimativa DESTINO_INALCANSAVEL = Estimativa.builder().custo(-1).build();

    private Map<String, Set<Estimativa>> estimativasPorLocal = new HashMap<>();

    public MelhorCaminho() {
    }

    public void vizinhoMutuos(String origem, String destino, int custo) {
        Estimativa estimativa = criaEstimativa(origem, destino, custo);
        adicionaEstimativa(origem, estimativa);

        Estimativa estimativa1 = criaEstimativa(destino, origem, custo);
        adicionaEstimativa(destino, estimativa1);
    }

    public int dePara(String origem, String destino) {
        if (origem.equals(destino)) return 0;

        while(temEstimativasAbertas(origem)) {
            Comparator<? super Estimativa> porCusto = Comparator.comparing(Estimativa::getCusto);

            estimativasPorLocal.get(origem).stream()
                    .filter(Estimativa::aberta)
                    .sorted(porCusto)
                    .forEach(estimativa -> {
                        validaCusto(origem, estimativa);
                        estimativa.fecha();
                    });
        }

        return buscaEstimativa(origem, destino).orElse(DESTINO_INALCANSAVEL).getCusto();
    }

    private void validaCusto(String origem, Estimativa estimativaDeNivelZero) {
        Set<Estimativa> estimativasDeNivelUm = estimativasPorLocal.get(estimativaDeNivelZero.getDestino())
                .stream()
                .filter(estimativa -> estimativa.getDestino() != origem)
                .collect(Collectors.toSet());

        estimativasDeNivelUm.stream().forEach(estimativaDeNivelUm -> {
            recalculaEstimativa(origem, estimativaDeNivelZero, estimativaDeNivelUm);
        });
    }

    private void recalculaEstimativa(String origem, Estimativa estimativaDeNivelZero, Estimativa estimativaDeNivelUm) {
        final Optional<Estimativa> estimativaNaOrigem = buscaEstimativa(origem, estimativaDeNivelUm.getDestino());

        int custoTotal = estimativaDeNivelZero.getCusto() + estimativaDeNivelUm.getCusto();

        if (estimativaNaOrigem.isPresent()) {
            if (estimativaNaOrigem.get().getCusto() > custoTotal) {
                estimativaNaOrigem.get().setPrecedente(estimativaDeNivelZero.getDestino());
                estimativaNaOrigem.get().setCusto(custoTotal);
            }
        } else {
            Estimativa estimativaNova = criaEstimativa(
                    estimativaDeNivelZero.getDestino(),
                    estimativaDeNivelUm.getDestino(),
                    custoTotal);
            estimativasPorLocal.get(origem).add(estimativaNova);
        }
    }

    private boolean temEstimativasAbertas(String origem) {
        return estimativasPorLocal.get(origem).stream()
                .filter(Estimativa::aberta)
                .findAny()
                .isPresent();
    }

    private Optional<Estimativa> buscaEstimativa(String origem, String destino) {
        return estimativasPorLocal.get(origem).stream()
                .filter(estimativa -> estimativa.getDestino() == destino)
                .findFirst();
    }

    private Estimativa criaEstimativa(String origem, String destino, int custo) {
        return Estimativa.builder()
                .precedente(origem)
                .destino(destino)
                .custo(custo)
                .status(Estimativa.Status.aberto)
                .build();
    }

    private void adicionaEstimativa(String origem, Estimativa estimativa) {
        if (estimativasPorLocal.get(origem) == null) {
            estimativasPorLocal.put(origem, new HashSet<>());
        }

        estimativasPorLocal.get(origem).add(estimativa);
    }
}
