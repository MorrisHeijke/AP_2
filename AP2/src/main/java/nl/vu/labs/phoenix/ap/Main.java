package nl.vu.labs.phoenix.ap;

import java.io.PrintStream;
import java.util.Scanner;
import java.math.BigInteger;

public class Main {

	PrintStream out;
	InterpreterInterface<Set<BigInteger>> interpreter;

	public Main() {
		out = new PrintStream(System.out);
	}

	private void start() {
		interpreter = new Interpreter<Set<BigInteger>>();
		out.println("Enter input: ");
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
			Set<BigInteger> set = interpreter.eval(in.nextLine());
			if (set != null) {
				printSet(set);
			}
		}
		in.close();
	}

	private void printSet(Set<BigInteger> set) {
		SetInterface<BigInteger> copy = set.copy();
		if (copy.isEmpty()) {
			out.println("");
			return;
		}
		while (copy.size() > 1) {
			out.printf("%s ", copy.get());
			copy.remove(copy.get());
		}
		out.printf("%s\n", copy.get());
		copy.remove(copy.get());
	}

	public static void main(String[] args) {
		new Main().start();
	}
}