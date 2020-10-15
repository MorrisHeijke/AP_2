package nl.vu.labs.phoenix.ap;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.math.BigInteger;
import java.util.HashMap;
import java.io.PrintStream;

public class Interpreter<T extends SetInterface<BigInteger>> implements InterpreterInterface<T> {

	private static char UNION_OPERATOR = '+', COMPLEMENT_OPERATOR = '-', SYMMETRIC_OPERATOR = '|',
			INTERSECTION_OPERATOR = '*', PRINT_STATEMENT = '?', COMMENT = '/', ASSIGNMENT = '=', SET_OPENER = '{',
			SET_CLOSER = '}', COMPLEX_OPENER = '(', COMPLEX_CLOSER = ')', NATURALNUMBER_SEPARATOR = ',';

	HashMap<Identifier, T> map;
	PrintStream out;

	public Interpreter() {
		out = new PrintStream(System.out);
		map = new HashMap<Identifier, T>();
	}

	@Override
	public T getMemory(String v) {
		Scanner input = new Scanner(v);
		input.useDelimiter("");
		Identifier identifier;
		try {
			identifier = identifier(input);
		} catch (APException e) {
			out.println(e.getMessage());
			return null;
		}
		return map.get(identifier);
	}

	@Override
	public T eval(String s) {
		T result = null;
		Scanner input = new Scanner(s);
		noSpaces(input);

		try {
			if (nextCharIs(input, COMMENT) || nextCharIsLetter(input)) {
				statement(input);
			} else if (nextCharIs(input, PRINT_STATEMENT)) {
				result = statement(input);
			}
		} catch (APException e) {
			out.println(e.getMessage());
		}

		return result;
	}

	private Identifier identifier(Scanner input) throws APException {
		if (!nextCharIsLetter(input)) {
			throw new APException("Error: Character is not a letter");
		}

		Identifier identifier = new Identifier(nextChar(input));
		input.useDelimiter("");
		while (nextCharIsLetter(input) || nextCharIsDigit(input)) {
			identifier.add(nextChar(input));
		}

		noSpaces(input);
		return identifier;
	}

	void eoln(Scanner in) throws APException {
		if (in.hasNext()) {
			throw new APException("Error: No end of line");
		}
	}

	private T printStatement(Scanner input) throws APException {
		noSpaces(input);
		goToNextChar(input, PRINT_STATEMENT);
		T printStatement = expression(input);
		eoln(input);

		return printStatement;
	}

	private T statement(Scanner input) throws APException {
		noSpaces(input);
		if (nextCharIsLetter(input)) {
			assignment(input);
		} else if (nextCharIs(input, PRINT_STATEMENT)) {
			return printStatement(input);
		} else if (nextCharIs(input, COMMENT)) {
			comment(input);
		} else {
			throw new APException("Error: Input is not an assignment, print statement or comment");
		}
		return null;
	}

	private void assignment(Scanner input) throws APException {
		Identifier identifier = identifier(input);
		goToNextChar(input, ASSIGNMENT);
		T expression = expression(input);
		eoln(input);
		map.put(identifier, expression);	
	}

	private void comment(Scanner input) throws APException {
		return;
	}

	private T expression(Scanner input) throws APException {
		T term1 = term(input);
		T term2 = (T) new Set<BigInteger>();
		char operator;

		while (nextCharIs(input, UNION_OPERATOR) || nextCharIs(input, SYMMETRIC_OPERATOR)
				|| nextCharIs(input, COMPLEMENT_OPERATOR)) {
			operator = nextChar(input);
			term2 = term(input);
			term1 = additiveOperator(term1, term2, operator);
		}

		return term1;
	}

	private T term(Scanner input) throws APException {
		T factor1 = factor(input);
		T factor2 = null;
		char operator;

		while (nextCharIs(input, INTERSECTION_OPERATOR)) {
			operator = nextChar(input);
			factor2 = factor(input);
			factor1 = multiplicativeOperator(factor1, factor2, operator);
		}

		return factor1;
	}

	private T factor(Scanner input) throws APException {
		if (nextCharIsLetter(input)) {
			Identifier identifier = identifier(input);
			if (map.containsKey(identifier)) {
				return map.get(identifier);
			} else {
				throw new APException("Error: Input is not an identifier");
			}
		} else if (nextCharIs(input, SET_OPENER)) {
			return readSet(input);
		} else if (nextCharIs(input, COMPLEX_OPENER)) {
			return complexFactor(input);
		} else {
			throw new APException("Error: Input is not a set, identifier or complex factor");
		}
	}

	private T complexFactor(Scanner input) throws APException {
		goToNextChar(input, COMPLEX_OPENER);
		T complexFactor = expression(input);
		goToNextChar(input, COMPLEX_CLOSER);

		return complexFactor;
	}

	private T multiplicativeOperator(T set1, T set2, char operator)
			throws APException {

		if (operator == INTERSECTION_OPERATOR) {
			return (T) set1.intersection(set2);
		} else {
			throw new APException("Error" + operator + " is an invalid operator");
		}

	}

	private T additiveOperator(T set1, T set2, char operator)
			throws APException {

		if (operator == UNION_OPERATOR) {
			return (T) set1.union(set2);
		} else if (operator == COMPLEMENT_OPERATOR) {
			return (T) set1.complement(set2);
		} else if (operator == SYMMETRIC_OPERATOR) {
			return (T) set1.symmetricDifference(set2);
		} else {
			throw new APException("Error" + operator + " is an invalid operator");
		}
	}

	private T readSet(Scanner input) throws APException {
		T set = (T) new Set<BigInteger>();
		goToNextChar(input, SET_OPENER);
		
		if (nextCharIsDigit(input)) {
			set.add(naturalNumber(input));
			while(nextCharIs(input, NATURALNUMBER_SEPARATOR)) {
				goToNextChar(input, NATURALNUMBER_SEPARATOR);
				set.add(naturalNumber(input));
			}
		}

		goToNextChar(input,SET_CLOSER);
		return set;
	}

	private BigInteger naturalNumber(Scanner input) throws APException {
		if (nextCharIs(input, '0')) {
			nextChar(input);
			return new BigInteger("0");
		} else if (nextCharIsDigit(input)) {
			return positiveNumber(input);
		} else {
			throw new APException("Error: This is not a natural number");
		}
	}

	private BigInteger positiveNumber(Scanner input) throws APException {
		StringBuffer number = new StringBuffer();
		number.append(nextChar(input));
		input.useDelimiter("");
		while (nextCharIsDigit(input)) {
			number.append(nextChar(input));
		}

		noSpaces(input);
		BigInteger bigInt = new BigInteger(number.toString());
		return bigInt;
	}

	//Splits on spaces and multiple spaces. Therefore, it ignores all spaces.
	private void noSpaces(Scanner input) {
		input.useDelimiter("\\s*");
	}

	private void goToNextChar(Scanner in, char c) throws APException {
		if (!in.hasNext()) {
			throw new APException("Error: Input missing " + c);
		}
		if (!nextCharIs(in, c)) {
			throw new APException("Error: Expected } but got " + in.next().charAt(0));
		}
		nextChar(in);
	}

	char nextChar(Scanner in) {
		return in.next().charAt(0);
	}

	boolean nextCharIsDigit(Scanner in) {
		return in.hasNext("[0-9]");
	}

	boolean nextCharIsLetter(Scanner in) {
		return in.hasNext("[a-zA-Z]");
	}

	boolean nextCharIs(Scanner in, char c) {
		return in.hasNext(Pattern.quote(c + ""));
	}
}