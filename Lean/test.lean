def main : IO Unit := IO.println "Hello, world!"
#eval (1 + 1 : Nat)
#check (1 - 2 : Int)
#eval String.append "Iris says " (String.append "hello, " "world")
#eval String.append "it is " (if 1 > 2 then "yes" else "no")

def hello : String := "Hello!"
#eval String.append hello (String.append " " hello)
def addOne (n: Nat) : Nat := n + 1
#eval addOne 9

def maximum (n: Nat) (k: Nat) : Nat :=
    if n < k then
        k
    else
        n
#eval maximum 1 5

def spaceBetween (before: String) (after: String) :=
    String.append before (String.append " " after)
#check spaceBetween
#check (spaceBetween)

def joinStringsWith (first: String) (second: String) (third: String) : String :=
    String.append second (String.append first third)
#eval joinStringsWith ", " "one" "and another"
#check joinStringsWith ": "

def volume (length: Nat) (width: Nat) (height: Nat) : Nat :=
    length * width * height
#eval volume 20 10 12409

def Str : Type := String
def aStr : String := "this is an alias!"
def NaturalNumber : Type := Nat
def natNum : NaturalNumber := (38 : Nat)
abbrev N : Type := Nat
def otherNatNum : N := 38

structure Point where
    x: Float
    y: Float
deriving Repr

-- practice match statement
def or (a b : Bool) :=
    match a with
    | true => true
    | false => b
