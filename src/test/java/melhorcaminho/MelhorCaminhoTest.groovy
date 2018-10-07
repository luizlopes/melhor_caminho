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

    def "melhor caminho de A para C com dois custos diferentes"() {
        given:
        MelhorCaminho melhorCaminho = new MelhorCaminho("A", "B", "C")
        melhorCaminho.vizinhoMutuos("A", "B", 5)
        melhorCaminho.vizinhoMutuos("A", "C", 15)
        melhorCaminho.vizinhoMutuos("B", "C", 4)

        when:
        int custo = melhorCaminho.dePara("A", "C")

        then:
        custo == 9
    }

    def "melhor caminho de A para D"() {
        given:
        MelhorCaminho melhorCaminho = new MelhorCaminho("A", "B", "C", "D")
        melhorCaminho.vizinhoMutuos("A", "B", 5)
        melhorCaminho.vizinhoMutuos("B", "C", 4)
        melhorCaminho.vizinhoMutuos("C", "D", 3)

        when:
        int custo = melhorCaminho.dePara("A", "D")

        then:
        custo == 12
    }

    def "melhor caminho de A para D com dois custos diferentes"() {
        given:
        MelhorCaminho melhorCaminho = new MelhorCaminho("A", "B", "C", "D")
        melhorCaminho.vizinhoMutuos("A", "B", 5)
        melhorCaminho.vizinhoMutuos("B", "C", 4)
        melhorCaminho.vizinhoMutuos("B", "D", 3)
        melhorCaminho.vizinhoMutuos("C", "D", 3)

        when:
        int custo = melhorCaminho.dePara("A", "D")

        then:
        custo == 8
    }

    def "melhor caminho de A para E"() {
        given:
        MelhorCaminho melhorCaminho = new MelhorCaminho("A", "B", "C", "D", "E")
        melhorCaminho.vizinhoMutuos("A", "B", 5)
        melhorCaminho.vizinhoMutuos("B", "C", 7)
        melhorCaminho.vizinhoMutuos("B", "D", 3)
        melhorCaminho.vizinhoMutuos("C", "E", 4)
        melhorCaminho.vizinhoMutuos("D", "E", 10)
        melhorCaminho.vizinhoMutuos("D", "F", 8)

        when:
        int custo = melhorCaminho.dePara("A", "E")

        then:
        custo == 16
    }

    def "melhor caminho de A para F"() {
        given:
        MelhorCaminho melhorCaminho = new MelhorCaminho("A", "B", "C", "D", "E")
        melhorCaminho.vizinhoMutuos("A", "B", 5)
        melhorCaminho.vizinhoMutuos("B", "C", 7)
        melhorCaminho.vizinhoMutuos("B", "D", 3)
        melhorCaminho.vizinhoMutuos("C", "E", 4)
        melhorCaminho.vizinhoMutuos("D", "E", 10)
        melhorCaminho.vizinhoMutuos("D", "F", 8)

        when:
        int custo = melhorCaminho.dePara("A", "F")

        then:
        custo == 16
    }
}
