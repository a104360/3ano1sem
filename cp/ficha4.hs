import Cp

--coassocla :: Either b (Either a c) -> Either (Either b a) c
--coassocla (Left _) = (i1.i1)
--coassocla (Right _) = (i2 -|- id)


data LTree a = Leaf a | Fork (LTree a,LTree a) deriving Show


out :: LTree a -> Either (a) ((LTree a,LTree a))
out (Leaf a) = i1 a 
out (Fork (x,y)) = i2 (x,y)

inn :: Either a (LTree a,LTree a) -> LTree a
inn = either (Leaf) (Fork)