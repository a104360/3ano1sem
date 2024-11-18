filho(joao,jose).
filho(jose,manuel).
filho(carlos,jose).

avo(A,N) :- 
    filho(N,X),
    filho(X,A).

bisavo(A,D) :-
    descendente(D,A,3).

descendente(D,A) :-
    filho(D,A).
descendente(D,A) :-
    filho(D,X),
    descendente(X,A).

descendente(D,A,1) :-
    filho(D,A).
descendente(D,A,G) :-
    filho(D,X),
    descendente(X,A,N),
    G is N-1.