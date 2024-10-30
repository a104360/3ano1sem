from Graph import Graph
from Node import Node


def main():
    g = Graph()

    """

    Shinjuku 2

    Akihabara 3

    Asakusa 4

    Ginza 5 

    Harajuku 6
 
    Roppongi 7

    Ueno 8

    Ikebukuro 9
 
    Odaiba 10

    Meguro 11

    Nakano 12

    Tsukiji 13
    """

    #Ficha2
    g.add_edge("Shibuya", "Shinjuku", 5)
    g.add_edge("Shibuya", "Odaiba", 6)
    g.add_edge("Shinjuku", "Akihabara", 7)
    g.add_edge("Akihabara", "Asakusa", 2)
    g.add_edge("Akihabara", "Ginza", 4)
    g.add_edge("Asakusa", "Harajuku", 13)
    g.add_edge("Ginza", "Harajuku", 9)
    g.add_edge("Ginza", "Ikebukuro", 11)
    g.add_edge("Odaiba", "Ikebukuro", 12)
    g.add_edge("Harajuku", "Roppongi", 3)
    g.add_edge("Roppongi", "Ueno", 5)
    g.add_edge("Ikebukuro", "Ueno", 8)
    g.add_edge("Ueno", "Meguro", 6)
    g.add_edge("Meguro", "Nakano", 9)
    g.add_edge("Nakano", "Tsukiji", 4)
    g.add_edge("Tsukiji", "Odaiba", 10)

    # Adicionando heurísticas
    g.add_heuristica("Shibuya", 270)
    g.add_heuristica("Shinjuku", 250)
    g.add_heuristica("Akihabara", 145)
    g.add_heuristica("Asakusa", 95)
    g.add_heuristica("Ginza", 70)
    g.add_heuristica("Harajuku", 45)
    g.add_heuristica("Roppongi", 220)
    g.add_heuristica("Ueno", 140)
    g.add_heuristica("Ikebukuro", 85)
    g.add_heuristica("Odaiba", 25)
    g.add_heuristica("Meguro", 110)
    g.add_heuristica("Nakano", 65)
    g.add_heuristica("Tsukiji", 30)


    saida = -1
    while saida != 0:
        print("1-Imprimir Grafo")
        print("2-Desenhar Grafo")
        print("3-Imprimir  nodos de Grafo")
        print("4-Imprimir arestas de Grafo")
        print("5-DFS")
        print("6-BFS")
        print("7-A*")
        print("8-Gulosa")
        print("0-Saír")

        saida = int(input("introduza a sua opcao-> "))
        if saida == 0:
            print("saindo.......")
        elif saida == 1:
            print(g.m_graph)
            l = input("prima enter para continuar")
        elif saida == 2:
            g.desenha()
        elif saida == 3:
            print(g.m_graph.keys())
            l = input("prima enter para continuar")
        elif saida == 4:
            print(g.imprime_aresta())
            l = input("prima enter para continuar")
        elif saida == 5:
            inicio = input("Nodo inicial->")
            fim = input("Nodo final->")
            print(g.procura_DFS(inicio, fim, path=[], visited=set()))
            l = input("prima enter para continuar")
        elif saida == 6:
            inicio = input("Nodo inicial->")
            fim = input("Nodo final->")
            print(g.procura_BFS(inicio, fim))
            l = input("prima enter para continuar")
        elif saida == 7:
            inicio = input("Nodo inicial->")
            fim = input("Nodo final->")
            print(g.procura_aStar(inicio, fim))
            l = input("prima enter para continuar")
        elif saida == 8:
            inicio = input("Nodo inicial->")
            fim = input("Nodo final->")
            print(g.greedy(inicio, fim))
            l = input("prima enter para continuar")
        else:
            print("you didn't add anything")
            l = input("prima enter para continuar")


if __name__ == "__main__":
    main()
