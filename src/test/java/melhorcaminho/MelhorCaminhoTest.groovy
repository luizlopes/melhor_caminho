package melhorcaminho

import spock.lang.Specification

class MelhorCaminhoTest extends Specification {

    def "melhor caminho de A para B"() {
        given:
        MelhorCaminho melhorCaminho = new MelhorCaminho("A", "B")
        melhorCaminho.vizinhoMutuos("A", "B", 5)

        when:
        int custo = melhorCaminho.dePara("A", "B")

        then:
        custo == 5
    }

    def "melhor caminho de A para C"() {
        given:
        MelhorCaminho melhorCaminho = new MelhorCaminho("A", "B", "C")
        melhorCaminho.vizinhoMutuos("A", "B", 5)
        melhorCaminho.vizinhoMutuos("B", "C", 4)

        when:
        int custo = melhorCaminho.dePara("A", "C")

        then:
        custo == 9
    }
}
