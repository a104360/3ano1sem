len :: [a] -> Int
len [] = 0
len (h:t) = 1 + len t


rev :: [a] -> [a]
rev [] = []
rev (h:t) = (rev t) ++ [h]

tke :: Int -> [a] -> [a]
tke _ [] = []
tke n (h:t) = tkeAux n (h:t)

tkeAux :: Int -> [a] -> [a]
tkeAux _ [] = []
tkeAux n (h:t) = if n /= 0 then (h: tkeAux (n-1) t) else [h]


mapa :: (a->b) -> [a] -> [b]
mapa _ [] = []
mapa p (h:t) = ((p h):(mapa p t))


filtro :: (a -> Bool) -> [a] -> [a]
filtro _ [] = []
filtro p (h:t) = if (p h) == True then (h : filtro p t) else (filtro p t)


uncurr :: (a -> b -> c) -> (a,b) -> c
uncurr pa (a,b) = pa a b

curr :: ((a,b) -> c) -> a -> b -> c
curr p a b = p (a,b)

flipper :: (a -> b -> c) -> b -> a -> c
flipper p b a = p a b 

f :: Num a => a -> a 
f = (*) 2

g :: Num a => a -> a
g = (+) 1