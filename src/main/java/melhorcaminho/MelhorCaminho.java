package melhorcaminho;

import java.util.*;
import java.util.stream.Collectors;

public class MelhorCaminho {

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

        return buscaEstimativa(origem, destino).get().getCusto();
    }

    private boolean temEstimativasAbertas(String origem) {
        return estimativasPorLocal.get(origem).stream()
                .filter(Estimativa::aberta)
                .findAny()
                .isPresent();
    }

    private void validaCusto(String origem, Estimativa estimativaMenorCusto) {
        // BUSCANDO DESTINOS LIGADOS AO LOCAL DE MENOR CUSTO
        Set<Estimativa> estimativasB = estimativasPorLocal.get(estimativaMenorCusto.getDestino())
                .stream()
                .filter(estimativa -> estimativa.getDestino() != origem)
                .collect(Collectors.toSet());

        // VALIDANDO CUSTO DE DESTINOS LIGAGOS AO LOCAL DE MENOR CUSTO
        estimativasB.stream()
                .forEach(estimativa -> {
                    Optional<Estimativa> estimativaNaOrigem = buscaEstimativa(origem, estimativa.getDestino());
                    int custoTotal = estimativaMenorCusto.getCusto() + estimativa.getCusto();
                    if (!estimativaNaOrigem.isPresent()) {
                        Estimativa estimativaNova = criaEstimativa(estimativaMenorCusto.getDestino(),
                                estimativa.getDestino(),
                                custoTotal);

                        estimativasPorLocal.get(origem).add(estimativaNova);
                    } else {
                        if (estimativaNaOrigem.get().getCusto() > custoTotal) {
                            estimativaNaOrigem.get().setPrecedente(estimativaMenorCusto.getDestino());
                            estimativaNaOrigem.get().setCusto(custoTotal);
                        }
                    }
                });
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
