import Cp


assocrA :: ((a,b),c) -> (a,(b,c))
assocrA = split (p1 . p1) (p2 >< id)
--assocrA (a,b)= (p1 (p1 (a,b)),(p2 a,id b))



alfa :: Either a b -> (Bool,Either a b)
alfa a = (False,a)
alfa b = (True,b)

--alfa :: Either c c -> (Bool,c)
--alfa = either (split (const False) id) (split (const True) id)


--secondSplit :: a -> c
--secondSplit a = id a + (split succ fac a)


out :: (Num a , Eq a) => a -> Either () a
out 0 = i1 ()
out n = i2 (n-1)

inn :: (Enum a,Num a, Eq a) => Either () a -> a 
inn = either (const 0) succ

fack :: Integer -> Integer
fack = either (const 1) (mul) . (id -|- (split succ fack)) . out