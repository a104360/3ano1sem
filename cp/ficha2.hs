import Cp
import Data.Char

f :: Bool -> Bool -> Bool -> Bool
f a b c = ((&&) a b) /= c 

--g :: 
--g = split p1 f 

data X = B Bool | P (Bool,Int)

f1 :: (Bool -> Bool) -> ((Bool,Int) -> X) -> Either Bool (Bool,Int) -> Either Bool X
f1 = (-|-)


acronym :: [Char] -> [Char] 
acronym = filter (isUpper) 

short :: String -> [Char]
--short a = (head $ words a) ++ " " ++ (last $ words a)
-- uncurry aplica uma função a um par e torna num valor único
short = uncurry ((++) . (++" ")) . split head last . words 