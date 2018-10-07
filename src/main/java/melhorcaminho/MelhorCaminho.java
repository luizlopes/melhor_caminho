package melhorcaminho;

import java.util.*;
import java.util.stream.Collectors;

public class MelhorCaminho {

    private List<String> locais;
    private Estimativa[][] vizinhanca;
    private Map<String, Set<Estimativa>> estimativasPorLocal = new HashMap<>();

    public MelhorCaminho(String... locais) {
        this.locais = Arrays.asList(locais);
        int tamanho = this.locais.size();
        this.vizinhanca = new Estimativa[tamanho][tamanho];
    }

    public void vizinhoMutuos(String origem, String destino, int custo) {
        Estimativa estimativa = criaEstimativa(origem, destino, custo);
        adicionaEstimativa(origem, estimativa);

        Estimativa estimativa1 = criaEstimativa(destino, origem, custo);
        adicionaEstimativa(destino, estimativa1);
    }

    public int dePara(String origem, String destino) {

        // BUSCANDO MENOR CUSTO ABERTO EM A
        Comparator<? super Estimativa> porCusto = Comparator.comparing(Estimativa::getCusto);
        Estimativa estimativaMenorCusto = estimativasPorLocal.get(origem).stream()
                .filter(Estimativa::aberta)
                .min(porCusto)
                .get();


        // BUSCANDO DESTINOS LIGADOS AO LOCAL DE MENOR CUSTO
        Set<Estimativa> estimativasB = estimativasPorLocal.get(estimativaMenorCusto.getDestino())
                .stream()
                .filter(estimativa -> estimativa.getDestino() != origem)
                .collect(Collectors.toSet());

        estimativasB.stream()
                .forEach(estimativa -> {
                    Optional<Estimativa> estimativaNaOrigem = buscaEstimativa(origem, estimativa.getDestino());
                    if (!estimativaNaOrigem.isPresent()) {
                        Estimativa estimativaNova = criaEstimativa(estimativaMenorCusto.getDestino(),
                                estimativa.getDestino(),
                                estimativaMenorCusto.getCusto() + estimativa.getCusto());

                        estimativasPorLocal.get(origem).add(estimativaNova);
                    }});

        return buscaEstimativa(origem, destino).get().getCusto();
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
