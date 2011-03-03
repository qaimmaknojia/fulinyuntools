import java.util.Random;


public class LambdaGenerator {

	public static void main(String[] args) {
//		new Application(new Function(new Atom('x'), new Atom('x')), new Atom('y')).print();
//		System.out.println();
		generate("xyz", 4).print();
	}
	
	/**
	 * 
	 * @param charset the character set for atoms, each atom has a one-character name
	 * @param depth bigger the depth, more complex the expression
	 * @return the randomly generated lambda expression
	 */
	public static Expression generate(String charset, int depth) {
		Random r = new Random();
		if (depth == 0) {
			char a = charset.charAt(r.nextInt(charset.length()));
			return new Atom(a);
		}
		int type = r.nextInt(3);
		switch (type) {
		case 0:
			char a = charset.charAt(r.nextInt(charset.length()));			
			return new Atom(a);
		case 1:
			Atom at = new Atom(charset.charAt(r.nextInt(charset.length())));
			Expression e = generate(charset, depth-1);
			return new Function(at, e);
		case 2:
			Expression e1 = generate(charset, depth-1);
			Expression e2 = generate(charset, depth-1);
			return new Application(e1, e2);
		}
		return null; // should never reach here
	}
}

class Expression {
	public void print() {
	
	}
	
	public Expression generate() {
		return null;
	}
}

class Atom extends Expression {
	public char name;
	
	public Atom(char n) {
		name = n;
	}
	
	public void print() {
		System.out.print(name);
	}
}

class Function extends Expression {
	public Atom a;
	public Expression e;
	
	public Function(Atom atom, Expression expr) {
		a = atom;
		e = expr;
	}
	
	public void print() {
		System.out.print("lambda(");
		a.print();
		System.out.print(" ");
		e.print();
		System.out.print(")");
	}
}

class Application extends Expression {
	public Expression e1;
	public Expression e2;
	
	public Application(Expression ex1, Expression ex2) {
		e1 = ex1;
		e2 = ex2;
	}
	
	public void print() {
		System.out.print("[");
		e1.print();
		System.out.print(" ");
		e2.print();
		System.out.print("]");
	}
}
