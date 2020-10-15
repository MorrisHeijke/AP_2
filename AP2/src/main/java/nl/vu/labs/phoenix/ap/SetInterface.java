package nl.vu.labs.phoenix.ap;

public interface SetInterface<T extends Comparable<T>> {

	/*
	 *
	 * Elements : objects of type T
	 * Structure: no structure
	 * Domain : all sets of objects of type T
	 *
	 * There is a default constructor that initializes the Set on the empty Set.
	 *
	 * constructors
	 *
	 * Set();
	 * PRE -
	 * POST - A new Set-object has been made and contains the empty Set.
	 *
	 */

	/**
	 * Hint:
	 * 
	 * @return true - element was inserted false - element was already present
	 */

	// @param - 
	// PRE -
	// POST - 
	// @return - An empty Set 
	void init();
	
	// @param - T t
	// PRE -
	// POST - t is in the Set
	// @return - 
	void add(T t);

	// @param -
	// PRE - Used Set is not an empty Set
	// POST - Gets last element from the Set
	// @return - Give you the certain element
	T get(); 

	// @param - T t
	// PRE -
	// POST - t is not in the Set
	// @return - 
	void remove(T t);

	// @param -
	// PRE -
	// POST - Checks size of Set
	// @return - Integer containing the number of Identifiers in the Set
	int size();

	// @param -
	// PRE -
	// POST - Checks if the Set has no elements.
	// @return - Boolean whether the set is empty.
	boolean isEmpty();

	// @param -
	// PRE -
	// POST - Creates copy of Set as new Set-object
	// @return - Copy of Set
	SetInterface<T> copy();

	// @param - T t
	// PRE -
	// POST - Success: t is in the Set
	//			Failure: t is not in the Set
	// @return - Boolean whether the Set contains List
	boolean contains(T t);

	// @param - The second set.
	// PRE -
	// POST -
	// @return - A new set containing the union.
	SetInterface<T> union(SetInterface<T> set);

	// @param - The second set.
	// PRE -
	// POST -
	// @return - A new set containing the intersection.
	SetInterface<T> intersection(SetInterface<T> set);

	// @param - The second set.
	// PRE -
	// POST - The difference between the first and second set.
	// @return - A new set containing the difference.
	SetInterface<T> complement(SetInterface<T> set);

	// @param - The second set.
	// PRE -
	// POST -
	// @return - A new set containing the symmetric difference.
	SetInterface<T> symmetricDifference(SetInterface<T> set);

}