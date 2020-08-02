

-------------------------
-------- PART A ---------
-------------------------
import Data.List

type Var = String

data Term =
    Variable Var
  | Lambda   Var  Term
  | Apply    Term Term
--  deriving Show

--comment next two lines to remove pretty
instance Show Term where
  show = pretty

example :: Term
example = Lambda "a" (Lambda "x" (Apply (Apply (Lambda "y" (Apply (Variable "a") (Variable "c"))) (Variable "x")) (Variable "b")))

pretty :: Term -> String
pretty = f 0
    where
      f i (Variable x) = x
      f i (Lambda x m) = if i /= 0 then "(" ++ s ++ ")" else s where s = "\\" ++ x ++ ". " ++ f 0 m
      f i (Apply  n m) = if i == 2 then "(" ++ s ++ ")" else s where s = f 1 n ++ " " ++ f 2 m


------------------------- Assignment 1

numeral :: Int -> Term
numeral i = Lambda "f" (Lambda "x" (numeral' i))
  where
    numeral' i
      | i <= 0    = Variable "x"
      | otherwise = Apply (Variable "f") (numeral' (i-1))


-------------------------

merge :: Ord a => [a] -> [a] -> [a]
merge xs [] = xs
merge [] ys = ys
merge (x:xs) (y:ys)
    | x == y    = x : merge xs ys
    | x <= y    = x : merge xs (y:ys)
    | otherwise = y : merge (x:xs) ys


------------------------- Assignment 2

variables :: [Var]
variables = map (:[]) ['a'..'z'] ++ [ x : show i | i <- [1..] , x <- ['a'..'z'] ]

filterVariables :: [Var] -> [Var] -> [Var]
filterVariables xs [] = xs
filterVariables xs (y:ys) = filter (/=y) (filterVariables xs ys)

fresh :: [Var] -> Var
fresh = head . filterVariables variables

used :: Term -> [Var]
used (Variable x) = [x]
used (Lambda x n) = merge [x] (used n)
used (Apply  n m) = merge (used n) (used m)


------------------------- Assignment 3

rename :: Var -> Var -> Term -> Term
rename x y (Variable z)
    | z == x    = Variable y
    | otherwise = Variable z
rename x y (Lambda z n)
    | z == x    = Lambda z n
    | otherwise = Lambda z (rename x y n)
rename x y (Apply n m) = Apply (rename x y n) (rename x y m)


substitute :: Var -> Term -> Term -> Term
substitute x n (Variable y)
    | x == y    = n
    | otherwise = Variable y
substitute x n (Lambda y m)
    | x == y    = Lambda y m
    | otherwise = Lambda z (substitute x n (rename y z m))
    where z = fresh (used n `merge` used m `merge` [x,y])
substitute x n (Apply m p) = Apply(substitute x n m) (substitute x n p)

------------------------- Assignment 4

beta :: Term -> [Term]
beta (Apply (Lambda x n) m) =
  [substitute x m n] ++
  [Apply (Lambda x n') m  | n' <- beta n] ++
  [Apply (Lambda x n)  m' | m' <- beta m]
beta (Apply n m) =
  [Apply n' m  | n' <- beta n] ++
  [Apply n  m' | m' <- beta m]
beta (Lambda x n) = [Lambda x n' | n' <- beta n]
beta (Variable _) = []


normalize :: Term -> Term
normalize n
  | null ns   = n
  | otherwise = normalize (head ns)
  where ns = beta n

run :: Term -> IO ()
run n = do
  print n
  let ns = beta n
  if null ns then
    return ()
  else
    run (head ns)


-------------------------

suc    = Lambda "n" (Lambda "f" (Lambda "x" (Apply (Variable "f") (Apply (Apply (Variable "n") (Variable "f")) (Variable "x")))))
add    = Lambda "m" (Lambda "n" (Lambda "f" (Lambda "x" (Apply (Apply (Variable "m") (Variable "f")) (Apply (Apply (Variable "n") (Variable "f")) (Variable "x"))))))
mul    = Lambda "m" (Lambda "n" (Lambda "f" (Lambda "x" (Apply (Apply (Variable "m") (Apply (Variable "n") (Variable "f"))) (Variable "x")))))
dec    = Lambda "n" (Lambda "f" (Lambda "x" (Apply (Apply (Apply (Variable "n") (Lambda "g" (Lambda "h" (Apply (Variable "h") (Apply (Variable "g") (Variable "f")))))) (Lambda "u" (Variable "x"))) (Lambda "x" (Variable "x")))))
minus  = Lambda "n" (Lambda "m" (Apply (Apply (Variable "m") dec) (Variable "n")))



---- Implementation of operations above to use with Ints instead of numeral(n)
---- But will still return correct term if it exists
newSuc :: Int -> IO ()
newSuc n = run(Apply suc (numeral n))

newAdd :: Int -> Int -> IO ()
newAdd m n = run(Apply(Apply add (numeral m))(numeral n))

newMult :: Int -> Int -> IO ()
newMult m n
    | n<0        = error "There are no negative church numerals"
    | m<0        = error "There are no negative church numerals"
    | otherwise  = run(Apply(Apply mul (numeral m))(numeral n))

newMinus :: Int -> Int -> IO ()
newMinus m n
    | m<n        = error "Please change the order of arguments"
    | otherwise  = run(Apply(Apply minus (numeral m))(numeral n))

newDec :: Int -> IO ()
newDec n =  run(Apply dec (numeral n))



-------------------------
-------- PART B ---------
-------------------------

------------------------- Assignment 5


free :: Term -> [Var]
free (Variable x) = [x]
free (Lambda x n) = delete x (free n)
free (Apply  n m) = merge (free n) (free m)

abstractions :: Term -> [Var] -> Term
abstractions m (x:xs) = Lambda x (abstractions m xs)
abstractions m []     = m

applications :: Term -> [Term] -> Term
applications m (x:xs) =  applications(Apply m x) xs
applications m []     = m

bar :: [Var] -> [Term]
bar cs = map foo cs

foo :: Var -> Term
foo x = Variable x

lift :: Term -> Term
lift m = applications (abstractions m (free m)) (bar(free m))

super :: Term -> Term
super (Variable x) = Variable x
super (Apply m n)  = Apply (super m) (super n)
super (Lambda x n) = lift(Lambda x (auxiliary n))

auxiliary :: Term -> Term
auxiliary (Variable x) = Variable x
auxiliary (Apply m n)  = Apply (super m) (super n)
auxiliary (Lambda x n) = Lambda x (auxiliary n)


------------------------- Assignment 6

data Expr =
    V Var
  | A Expr Expr


toTerm :: Expr -> Term
toTerm (V v) = Variable v
toTerm (A m n) = Apply (toTerm m) (toTerm n)

instance Show Expr where
  show = show . toTerm

type Inst = (Var,[Var],Expr)

data Prog = Prog [Inst]


instance Show Prog where
  show (Prog ls) = unlines (map showInst ks)
    where
      ks = map showParts ls
      n  = maximum (map (length . fst) ks)
      showParts (x,xs,e) = (x ++ " " ++ unwords xs , show e)
      showInst (s,t) = take n (s ++ repeat ' ') ++ " = " ++ t


names = ['$':show i | i <- [1..] ]

-------------------------

stripAbs :: Term -> ([Var],Term)
stripAbs (Variable x) = ((createList (Variable x)),(findNonAbs (Variable x)))
stripAbs (Lambda x n) = ((createList (Lambda x n)),(findNonAbs (Lambda x n)))
stripAbs (Apply m n)  = ((createList (Apply m n)),(findNonAbs (Apply m n)))

createList :: Term -> [Var]
createList (Variable x) = []
createList (Lambda x n) = x:(createList n)
createList (Apply m n)  = []


findNonAbs :: Term -> Term
findNonAbs (Variable x) = Variable x
findNonAbs (Lambda x n) = findNonAbs n
findNonAbs (Apply m n)  = Apply m n


takeAbs :: Term -> [Term]
takeAbs (Variable x) = []
takeAbs (Lambda x n) = [(Lambda x n)]
takeAbs (Apply m n)  = (++)(takeAbs m) (takeAbs n)

toExpr :: [Var] -> Term -> Expr
toExpr = undefined
--toExpr (x:xs) (Variable z ) = undefined
--toExpr (x:xs) (Lambda y n)  = undefined
--toExpr (x:xs) (Apply m n)   = undefined
--toExpr (x:xs) (Variable z ) = undefined
--toExpr (x:xs) (Lambda y n)  = undefined
--toExpr (x:xs) (Apply m n)   = undefined


toInst :: [Var] -> (Var,Term) -> (Inst,[(Var,Term)],[Var])
toInst = undefined

prog :: Term -> Prog
prog = undefined
  where
    aux :: [Var] -> [(Var,Term)] -> [Inst]
    aux = undefined

example2 = Apply (Variable "S") (Apply (Apply example (numeral 0)) (Variable "0"))
example3 = Apply (Apply add (numeral 1)) (Apply (Apply mul (numeral 2)) (numeral 3))
example4 = Apply (Apply example3 (Variable "S")) (Variable "0")

------------------------- Assignment 7

sub :: [(Var,Expr)] -> Expr -> Expr
sub = undefined

step :: [Inst] -> [Expr] -> IO [Expr]
step = undefined

supernormalize :: Term -> IO ()
supernormalize = undefined
