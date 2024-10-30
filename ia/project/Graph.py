# Classe grafo para representaçao de grafos,
import math
from collections import deque

import networkx as nx  # biblioteca de tratamento de grafos necessária para desnhar graficamente o grafo
import matplotlib.pyplot as plt  # idem

from Node import Node


# Constructor
# Methods for adding edges
# Methods for removing edges
# Methods for searching a graph
# BFS, DFS, A*, Greedy





class Graph:
    def __init__(self, directed=False):
        self.m_nodes = []  
        self.m_directed = directed
        self.m_graph = {}  
        self.m_h = {}  

    #############
    #    escrever o grafo como string
    #############
    def __str__(self):
        out = ""
        for key in self.m_graph.keys():
            out = out + "node" + str(key) + ": " + str(self.m_graph[key]) + "\n"
        return out

    ################################
    #   encontrar nodo pelo nome
    ################################

    def get_node_by_name(self, name):
        search_node = Node(name)
        for node in self.m_nodes:
            if node == search_node:
                return node
          
        return None

    ##############################3
    #   imprimir arestas
    ############################333333

    def imprime_aresta(self):
        listaA = ""
        lista = self.m_graph.keys()
        for nodo in lista:
            for (nodo2, custo) in self.m_graph[nodo]:
                listaA = listaA + nodo + " ->" + nodo2 + " custo:" + str(custo) + "\n"
        return listaA

    ################
    #   adicionar   aresta no grafo
    ######################

    def add_edge(self, node1, node2, weight):
        n1 = Node(node1)
        n2 = Node(node2)
        if (n1 not in self.m_nodes):
            n1_id = len(self.m_nodes)  # numeração sequencial
            n1.setId(n1_id)
            self.m_nodes.append(n1)
            self.m_graph[node1] = []
        else:
            n1 = self.get_node_by_name(node1)

        if (n2 not in self.m_nodes):
            n2_id = len(self.m_nodes)  # numeração sequencial
            n2.setId(n2_id)
            self.m_nodes.append(n2)
            self.m_graph[node2] = []
        else:
            n2 = self.get_node_by_name(node2)

        self.m_graph[node1].append((node2, weight)) 

        if not self.m_directed:
            self.m_graph[node2].append((node1, weight))

    #############################
    # devolver nodos
    ##########################

    def getNodes(self):
        return self.m_nodes

    #######################
    #    devolver o custo de uma aresta
    ##############3

    def get_arc_cost(self, node1, node2):
        custoT = math.inf
        a = self.m_graph[node1]  # lista de arestas para aquele nodo
        for (nodo, custo) in a:
            if nodo == node2:
                custoT = custo

        return custoT

    ##############################
    #  dado um caminho calcula o seu custo
    ###############################

    def calcula_custo(self, caminho):
        # caminho é uma lista de nodos
        teste = caminho
        custo = 0
        i = 0
        while i + 1 < len(teste):
            custo = custo + self.get_arc_cost(teste[i], teste[i + 1])
            i = i + 1
        return custo

    ################################################################################
    #     procura DFS  -- To Do
    ####################################################################################

    def procura_DFS(self,start,end,path=[],visited=set()):
        path.append(start)
        visited.add(start)

        if start == end:
            return path
        for(node,cost) in self.getNeighbours(start):
            if node not in visited:
                return self.procura_DFS(node,end,path,visited)

    #####################################################
    # Procura BFS -- To Do
    ######################################################

    def procura_BFS(self,start,end):
        if start == end:
            return [start]
        
        visited = set([start])
        q = deque([start])

        path = {start:([start],0)}
        print(path)
        
        cost = 0

        while q:
            node = q.popleft()

            for (adjacent,nodeCost) in self.m_graph[node]:
                if adjacent not in visited:
                    visited.add(adjacent)
                    q.append(adjacent)

                    path[adjacent] = path[node] + (adjacent,nodeCost)
                    
                    if adjacent == end:
                        finalPath = path[start]

                        for a in path[adjacent]:
                            if a.__class__ == int:
                                cost += a
                            else:
                                finalPath[0].append(a)
                        return (finalPath[0],cost)
  
    ####################
    # funçãop  getneighbours, devolve vizinhos de um nó
    ##############################

    def getNeighbours(self, nodo):
        lista = []
        for (adjacente, peso) in self.m_graph[nodo]:
            lista.append((adjacente, peso))
        return lista

    ###########################
    # desenha grafo  modo grafico
    #########################

    def desenha(self):
        ##criar lista de vertices
        lista_v = self.m_nodes
        lista_a = []
        g = nx.Graph()
        for nodo in lista_v:
            n = nodo.getName()
            g.add_node(n)
            for (adjacente, peso) in self.m_graph[n]:
                lista = (n, adjacente)
                # lista_a.append(lista)
                g.add_edge(n, adjacente, weight=peso)

        pos = nx.spring_layout(g,1)
        nx.draw_networkx(g, pos, with_labels=True, font_weight='bold')
        labels = nx.get_edge_attributes(g, 'weight')
        nx.draw_networkx_edge_labels(g, pos, edge_labels=labels)

        plt.draw()
        plt.show()

    ####################################33
    #    add_heuristica   -> define heuristica para cada nodo 1 por defeito....
    ################################3

    def add_heuristica(self, n, estima):
        n1 = Node(n)
        if n1 in self.m_nodes:
            self.m_h[n] = estima



    ##########################################
    #    A* - To Do
    ##########################################

    def procura_aStar(self,start,end):
        # open is a list of nodes which have been visited, but whose neighbours
        # haven't all been inspected, starts off with the start node
        open = {start}

        # closed is a list of nodes which have been visited 
        # and whose neighbours have been inspected
        closed = set([])

        # g contains current distances from start to all other nodes
        # the default value (if it's not found in the map) is +infinity
        g = {}
        g[start] = 0

        # parents contains an adjancy map of all nodes
        parents = {}
        parents[start] = start
        n = None
        while len(open) > 0:

            # find a node with the lowest value of f() - evaluation function
            heuristics = {}
            flag = 0
            for v in open:
                if n == None:
                    n = v
                else: 
                    flag = 1
                    heuristics[v] = g[v] + self.getH(v)

            if flag == 1:
                min = self.findMin(heuristics)
                n = min
            if n == None:
                print('Path does not exist')
                return None
            if n == end:
                reconst_path = []

                while parents[n] != n:
                    reconst_path.append(n)
                    n = parents[n]
                
                reconst_path.append(start)
                reconst_path.reverse()

                return (reconst_path, self.calcula_custo(reconst_path))
            
            # for all the neighbour of the current node do
            for (node,cost) in self.getNeighbours(n):
                # if the current node is not in both open and closed
                # add it to open_list and note n as it's parent
                if node not in open and node not in closed:
                    open.add(node)
                    parents[node] = n
                    g[node] = g[n] + cost
                # otherwise, check if it is quicker to first visit n, then node
                # and if it is, update parent data and g data
                # and if the node was in the closed, move it to open
                else:
                    if g[node] > g[n] + cost:
                        g[node] = g[n] + cost
                        parents[node] = n
            #remove n from open, and add it to closed
            # because all of his neighbours were inspected
            open.remove(n)
            closed.add(n)
        
        return None
        """while True: 
            if len(openList) == 0:
                break
            currentNode = """


    ###################################3
    # devolve heuristica do nodo
    ####################################

    def getH(self, nodo):
        if nodo not in self.m_h.keys():
            return 1000
        else:
            return (self.m_h[nodo])


    ##########################################
    #   Greedy - To Do
    ##########################################


    def greedy(self,start,end):
        # tuplo de lista dos nomes com custo do caminho pretendido
        path = ([start],0)
        
        #set com nomes dos nodos
        visited = {start}

        # String nextNode
        currentNode = start 
        
        # vizinhos mantém as ligações ao nodo que estamos a analisar
        vizinhos = self.getNeighbours(currentNode)

        while vizinhos:
            visited.add(currentNode)
            # heuristics guarda o nome dos nodos e a sua heuristica
            heuristics = []

            for node in vizinhos:
                if node[0] not in visited:
                    if node[0] == end:
                        changePath = path[0]
                        changePath.append(node[0])
                        changeCost = path[1]
                        changeCost += self.get_arc_cost(currentNode,node[0])
                        path = (changePath,changeCost)
                        return path
                    heuristics.append((node[0],self.getH(node[0])))
            
            heuristics.sort(key=lambda x: x[1])
            
            # nextNode = (nome,heuristic)
            nextNode = heuristics[0]

            changePath = path[0]
            changePath.append(nextNode[0])
            changeCost = path[1]
            changeCost += self.get_arc_cost(currentNode,nextNode[0])
            path = (changePath,changeCost)

            #print(path)

            if nextNode[0] == end:
                return path

            
            currentNode = nextNode[0]
            vizinhos = self.getNeighbours(nextNode[0])

        return ([],-1)
    
    def calcF(self,caminho):
        return self.calcula_custo(caminho) + self.getH(caminho[-1])

    def findMin(self,dic):
        n = None
        for a in dic:
            if n == None:
                n = a
                continue
            if n > a:
                n = a
        return a

def changeTuple(lista,custo):
    return (lista,custo)