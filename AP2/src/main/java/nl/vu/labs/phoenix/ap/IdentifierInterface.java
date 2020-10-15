package nl.vu.labs.phoenix.ap;

public interface IdentifierInterface {
	
	/*
	 * Elements: Characters of type char
	 * Structure: linear
	 * Domain: Identifiers that start with a letter and only contain alphanumeric characters.
	 *
	 * constructors
	 *
	 * Identifier(char c);
	 *  PRE - c must be a letter.
	 *  POST - A new Identifier-object has been made and contains c.
	 *  
	 *
	 * Identifier (Identifier src);
	 *  PRE - 
	 *  POST - A new Identifier-object has been made and contains a copy of src.
	 */

	/*
	 * @param - Character c
	 * PRE - Character c should be alphanumeric
	 * POST - Character is in the identifier added after the last character of the identifier
	 * @return - Boolean whether the character is added 
	 */
	boolean add(char c);

	/*
	 * @param - Object identifier
	 * PRE - 
	 * POST - Success: contents are equal
	 * 		Failure: contents are not equal or obj is null or obj is not of type Identifier
	 * @return - Boolean whether inserted parameter equals Identifier 
	 */
	boolean equals(Object obj);

	/*
	 * @param - 
	 * PRE - 
	 * POST - 
	 * @return - Get the length of the Identifier as a String
	 */
	int getLength();


	/*
	 * @param - Character c
	 * PRE - c must be a letter
	 * POST - Identifier is initialized with c and has length 1
	 * @return - Identifier containing only c
	 */
	void init(char c);
	
	/*
	 * @param - 
	 * PRE - 
	 * POST - Calculates hash value for a given key by the use of a hashing algorithm 
	 * @return - Integer containing the hashCode.
	 */
	public int hashCode();

}