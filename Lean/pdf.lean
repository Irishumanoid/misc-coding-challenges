import Lean.Data.AssocList
-- different constructors
inductive BoolExpr where
  | var (name : String)
  | val (b : Bool)
  | or (p q : BoolExpr)
  | not (p : BoolExpr)

def simplify: BoolExpr → BoolExpr
  | BoolExpr.or p q => mkOr (simplify p) (simplify q)
  | BoolExpr.not p => mkNot (simplify p)
  | e => e
  where
    mkOr : BoolExpr → BoolExpr → BoolExpr
      | p, BoolExpr.val true => BoolExpr.val true
      | p, BoolExpr.val false => p
      | BoolExpr.val true, p => BoolExpr.val true
      | BoolExpr.val false, p => p
      | p, q  => BoolExpr.or p q
    mkNot : BoolExpr → BoolExpr
      | BoolExpr.val b => BoolExpr.val (not b)
      | p => BoolExpr.not p

abbrev Context := Lean.AssocList String Bool -- association list that assigns strings to booleans
def denote (ctx : Context) : BoolExpr → Bool
  | BoolExpr.or p q => denote ctx p || denote ctx q
  | BoolExpr.not p => !denote ctx p
  | BoolExpr.val b => b
  -- macro that condenses match statement (either gets val of string from assoc list or returns false by default)
  | BoolExpr.var x => if let some b := ctx.find? x then b else false

-- allow inference of default value by using Inhabited typeclass (at least one element)
instance : Inhabited BoolExpr where
  default := BoolExpr.val false


-- deriving auto-generates an instance
deriving instance DecidableEq for BoolExpr
#eval decide (BoolExpr.val true == BoolExpr.val false)
