declare BetaReduceN BetaReduce AlphaRename EtaReduce Replace FindName Contain NotContain FreeVars Append Minus BetaReduceCus Run RunNormal

% normal order beta reduction
fun {BetaReduceN Exp}
%   {Browse 'Beta reduce normal'#Exp} % for debug purpose
   case Exp of lambda(A E) then lambda(A {BetaReduceN E})
   [] [E1 E2] then E3 E4 in
      E4 = {BetaReduceN E1} % but not reduce E2 now
      E3 = {AlphaRename E4 {Append {Vars E4} {FreeVars E2}}} % rename bound variables in E4 to avoid collision with all variables in E4 and free variables in E2; expression is now [E3 E2]
      case E3 of lambda(A E) then E5 in
	 E5 = {Replace A E E2} % replace every occurrence of A in E with E2
	 {BetaReduceN E5} % go on reducing
      else [E3 E2] % cannot apply
      end
   [] Atom then Atom
%   else raise illFormedExpression(Exp) end
   end
end

% applicative order beta reduction
fun{BetaReduce Exp}
%   {Browse 'Beta Reduce'#Exp} % for debug purpose
   case Exp of lambda(A E) then lambda(A {BetaReduce E})
   [] [E1 E2] then E3 E4 E5 in
      E5 = {BetaReduce E1}
      E3 = {BetaReduce E2}
      E4 = {AlphaRename E5 {Append {Vars E5} {FreeVars E3}}} % rename bound variables in E5 to avoid collision with all variables in E5 and free variables in E3; expression is now [E4 E3]
      case E4 of lambda(A E) then E5 in
	 E5 = {Replace A E E3} % replace every occurrence of A in E with E3
	 {BetaReduce E5} % go on reducing
      else [E4 E3] % cannot apply the function
      end
   [] Atom then Atom
%   else raise illFormedExpression(Exp 'this should never happen!') end
   end
end

% replace every free occurrence of A in E with Rep
fun {Replace A E Rep}
   case E of lambda(Atom Expr) then
      if Atom == A then E % A is bound, cannot replace
      else lambda(Atom {Replace A Expr Rep})
      end
   [] [Expr1 Expr2] then
      [{Replace A Expr1 Rep} {Replace A Expr2 Rep}]
   [] Atom then
      if Atom == A then Rep
      else Atom
      end
   end
end

% rename bound variables in Exp to avoid collision with variables in list Avoid
fun {AlphaRename Exp Avoid}
%   {Browse 'Alpha rename'#Exp#Avoid} % for debug purpose
   case Exp of lambda(A E) then A1 in
      A1 = {FindName A Avoid}
%      {Browse 'Got'#lambda(A1 {Replace A E A1})} % for debug purpose
      lambda(A1 {AlphaRename {Replace A E A1} {Append Avoid [A1]}})
   [] [E1 E2] then
      [E1 E2]
   [] Atom then
      Atom
%   else raise illFormedExpression(Exp 'This should never happen!') end
   end
end

% find a nickname for X (in the form of X#N) that is not contained in list L
fun{FindName X L}
   if {Contain L X} then
   case X of X1#N then M in
      M = N+1
      {FindName X1#M L}
   else
      {FindName X#1 L}
   end
   else X
   end
end

% whether X is contained in list L
fun{Contain L X}
   case L of nil then false
   [] H|T then
      if X == H then true
      else {Contain T X}
      end
   end
end

fun{NotContain L X}
   if {Contain L X} then false
   else true
   end
end

% find the list of free variables in expression X
fun{FreeVars X}
   case X of [E1 E2] then
      {Append {FreeVars E1} {FreeVars E2}}
   [] lambda(A E) then
      {Minus {FreeVars E} A}
   [] A then
      [A]
   end
end

% find the list of all variables in expression X
fun{Vars X}
   case X of [E1 E2] then
      {Append {Vars E1} {Vars E2}}
   [] lambda(A E) then
      {Append {Vars E} [A]}
   [] A then
      [A]
   end
end

% concatenate two lists
fun{Append L1 L2}
   case L1 of nil then
      L2
   [] H|T then
      {Append T H|L2}
   end
end

% delete all instances of X from list L
fun{Minus L X}
   case L of nil then
      nil
   [] H|T then
      if H == X then
	 {Minus T X}
      else H|{Minus T X}
      end
   else raise illFormedExpression(L) end
   end
end

% eta reduction
fun{EtaReduce Exp}
%   {Browse 'Eta reduce'#Exp} % for debug purpose
   case Exp of lambda(X [E X]) then
      if {NotContain {FreeVars E} X} then
	 {EtaReduce E}
      else lambda(X [{EtaReduce E} X])
      end
   [] [E1 E2] then
      [{EtaReduce E1} {EtaReduce E2}]
   [] lambda(A E) then
      lambda(A {EtaReduce E})
   else Exp
   end
end

fun{Run Exp}
   {EtaReduce {BetaReduce Exp}}
end

fun{RunNormal Exp}
   {EtaReduce {BetaReduceN Exp}}
end

%{Browse {BetaReduceN [lambda(y [lambda(x lambda(y [x y])) y]) [y w]]}} % unit test
%{Browse {Replace x [lambda(x lambda(y [x y])) [y w]]}} % unit test

{Browse {Run [lambda(x y) lambda(x x)]}} % Output: y
{Browse {Run [lambda(p [p j]) lambda(x [q x])]}} % Output: [q j]
{Browse {Run lambda(x [y x])}} % Output: y
{Browse {Run lambda(x lambda(y [x y]))}} % Output: lambda(x x)
{Browse {RunNormal [lambda(y x) [lambda(x [x x]) lambda(x [x x])]]}} % x  normal order test
%{Browse {Run [lambda(y x) [lambda(x [x x]) lambda(x [x x])]]}} % infinite loop
{Browse {Run [lambda(y [lambda(x lambda(y [x y])) y]) [y w]]}} % [y w]  alpha renaming, beta reduction and eta reduction all involved
{Browse {RunNormal [lambda(y [lambda(x lambda(y [x y])) y]) [y w]]}} % [y w]
{Browse {Run lambda(x lambda(y [[z z] y]))}} % lambda(x [z z])  eta reduction
{Browse {Run [lambda(z [lambda(y z) z]) [lambda(y [y y]) [[z y] y]]]}} % [[[z y] y] [[z y] y]]  randomly generated
{Browse {Run [lambda(z z) [lambda(y y) [lambda(x x) y]]]}} % y  identity combinator
{Browse {Run [[lambda(y lambda(x [y x])) lambda(x [x x])] y]}} % [y y]  application combinator
{Browse {Run [[[lambda(b lambda(t lambda(e [[b t] e]))) lambda(x lambda(y x))] x] y]}} % x  if true then x else y  lambda(x lambda(y x)) is true
{Browse {Run [[[lambda(b lambda(t lambda(e [[b t] e]))) lambda(x lambda(y y))] x] y]}} % y  if true then x else y  lambda(x lambda(y y)) is false
%declare
%F = lambda(x [x x])
%Y = lambda(F [lambda(x [F lambda(y [[x x] y])]) lambda(x [F lambda(y [[x x] y])])]) % recursion combinator
%X = [Y F]
%{Browse {Run X}} % infinite loop
%{Browse {Run [F X]}} % infinite loop
{Browse {Run lambda(x [lambda(z lambda(y y)) x])}} % lambda(x lambda(y y))
{Browse {Run [[lambda(y lambda(y [y x])) y] z]}} % [z x]
{Browse {Run [[lambda(x y) [lambda(x x) x]] lambda(z [lambda(x x) y])]}} % [y lambda(z y)]
{Browse {Run [lambda(x [[y y] x]) lambda(z [[y y] z])]}} % [[y y] [y y]]
{Browse {Run [[lambda(x lambda(y y)) lambda(z z)] y]}} % y
{Browse {Run [lambda(y [lambda(z z) [x y]]) [lambda(y [z y]) [[x x] lambda(y y)]]]}} % [x [z [[x x] lambda(y y)]]]
{Browse {Run [lambda(z lambda(x lambda(x z))) lambda(x lambda(z lambda(x x)))]}} % lambda(x lambda(x lambda(x lambda(z lambda(x x)))))
{Browse {Run [lambda(x [lambda(x [lambda(x [x x]) [x x]]) [x x]]) [x x]]}} % [[[[x x] [x x]] [[x x] [x x]]] [[[x x] [x x]] [[x x] [x x]]]]
{Browse {Run lambda(x [[lambda(x [y x]) lambda(x [z x])] x])}} % [y z]  recursive eta reduction
