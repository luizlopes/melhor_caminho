package melhorcaminho;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MelhorCaminho {

    private List<String> locais;
    private int[][] vizinhanca;

    public MelhorCaminho(String... locais) {
        this.locais = Arrays.asList(locais);
        int tamanho = this.locais.size();
        this.vizinhanca = new int[tamanho][tamanho];
    }

    public void vizinhoMutuos(String origem, String destino, int custo) {
        int i = locais.indexOf(origem);
        int j = locais.indexOf(destino);
        vizinhanca[i][j] = custo;
        vizinhanca[j][i] = custo;
    }

    public int dePara(String de, String para) {
        int i = locais.indexOf(de);
        int j = locais.indexOf(para);
        return vizinhanca[i][j];
    }
}
