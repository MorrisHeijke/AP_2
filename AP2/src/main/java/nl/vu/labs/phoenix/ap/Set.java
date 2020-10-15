package nl.vu.labs.phoenix.ap;

public class Set<T extends Comparable<T>> implements SetInterface<T> {

	ListInterface<T> list;

	public Set() {
		list = new LinkedList<T>();
	}

	@Override
	public void init() {
		list.init();
	}

	@Override
	public void add(T t) {
		if (!list.find(t)) {
			list.insert(t);
		}
	}

	@Override
	public T get() {
		T result = list.retrieve();
		return result;
	}

	@Override
	public void remove(T t) {
		if (list.find(t)) {
			list.remove();
		}
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean contains(T t) {
		return list.find(t);
	}

	@Override
	public SetInterface<T> copy() {
		Set<T> copy = new Set<T>();
		if (isEmpty()) {
			return copy;
		} else {
			copy.list = this.list.copy();
			return copy;
		}
	}

	@Override
	public SetInterface<T> union(SetInterface<T> set) {
		Set<T> union = (Set<T>) set.copy();
		list.goToFirst();

		for (int i = 0; i < list.size(); i++) {
			T t = list.retrieve();
			union.add(t);
			list.goToNext();
		}

		return union;
	}

	@Override
	public SetInterface<T> intersection(SetInterface<T> set) {
		Set<T> intersection = new Set<T>();
		list.goToFirst();

		for (int i = 0; i < list.size(); i++) {
			T t = list.retrieve();
			if (set.contains(t)) {
				intersection.add(t);
			}

			list.goToNext();
		}

		return intersection;
	}

	@Override
	public SetInterface<T> complement(SetInterface<T> set) {
		Set<T> complement = new Set<T>();
		list.goToFirst();

		for (int i = 0; i < list.size(); i++) {
			T t = list.retrieve();
			if (!set.contains(t)) {
				complement.add(t);
			}

			list.goToNext();
		}

		return complement;
	}

	@Override
	public SetInterface<T> symmetricDifference(SetInterface<T> set) {
		return this.union(set).complement(this.intersection(set));
	}

}