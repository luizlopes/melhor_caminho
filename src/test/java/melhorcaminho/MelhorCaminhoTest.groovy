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
}
