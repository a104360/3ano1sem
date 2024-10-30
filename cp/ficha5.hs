import Cp
import Data.List


aux :: (a,[l]) -> [l]
aux (a,l) = if elem a l then a : l else l

--f :: [a] -> Either a1 (a2,[a2])

nube :: [a] -> [a]
nube l = 

store c = take 10 . nub . (c:)