package nl.vu.labs.phoenix.ap;

public class LinkedList<E extends Comparable<E>> implements ListInterface<E> {

	int numberOfElements;
	Node firstNode = new Node(null);
	Node lastNode = new Node(null);
	Node currentNode = new Node(null);

	public LinkedList() {
		numberOfElements = 0;
	}

	private class Node {

		E data;
		Node prior, next;

		public Node(E data) {
			this(data, null, null);
		}

		public Node(E data, Node prior, Node next) {
			this.data = data == null ? null : data;
			this.prior = prior;
			this.next = next;
		}

	}

	@Override
	public boolean isEmpty() {
		return numberOfElements == 0;
	}

	@Override
	public ListInterface<E> init() {
		firstNode = lastNode = currentNode = null;
		numberOfElements = 0;
		return this;
	}

	@Override
	public int size() {
		return numberOfElements;
	}

	@Override
	public ListInterface<E> insert(E d) {
		if (isEmpty()) { 
			currentNode = lastNode = firstNode = new Node(d);
			numberOfElements++;
			return this;
		} else if (d.compareTo(firstNode.data) <= 0) { 
			currentNode = firstNode = firstNode.prior = new Node(d, firstNode.prior, firstNode);
			numberOfElements++;
			return this;
		} else if (d.compareTo(lastNode.data) >= 0) { 
			currentNode = lastNode = lastNode.next = new Node(d, lastNode, lastNode.next);
			numberOfElements++;
			return this;
		} else { 
			while (d.compareTo(currentNode.data) < 0) {
				goToPrevious();
			}
			while (d.compareTo(currentNode.data) >= 0) {
				goToNext();
			}
			currentNode = currentNode.prior = currentNode.prior.next = new Node(d, currentNode.prior, currentNode);
			numberOfElements++;
			return this;	
		}
	}	
	
	@Override
	public E retrieve() {
		return currentNode.data;
	}

	@Override
	public ListInterface<E> remove() {
		if (isEmpty()) {
			return this;
		}
		if (size() == 1) {
			currentNode = firstNode = lastNode = new Node(null);
		} else if (currentNode == firstNode) {
			currentNode = firstNode = currentNode.next;
			currentNode.prior = null;
		} else if (currentNode == lastNode) {
			currentNode = lastNode = currentNode.prior;
			currentNode.next = null;
		} else {
			currentNode.next.prior = currentNode.prior;
			currentNode = currentNode.prior.next = currentNode.next;
		}

		numberOfElements--;
		return this;
	}

	@Override
	public boolean find(E d) {
		if (isEmpty()) {
			return false;
		}

		goToFirst();

		boolean canGoNext = true;
		
		while (canGoNext) {
			if (currentNode.data.compareTo(d) == 0) {
				return true;
			} else if (currentNode.data.compareTo(d) > 0) {
				goToPrevious();
				return false;
			} 

			canGoNext = goToNext();
		}

		return false;
	}

	@Override
	public boolean goToFirst() {
		if (!isEmpty()) {
			currentNode = firstNode;
			return true;
		}
		return false;
	}

	@Override
	public boolean goToLast() {
		if (!isEmpty()) {
			currentNode = lastNode;
			return true;
		}
		return false;
	}

	@Override
	public boolean goToNext() {
		if (currentNode != lastNode && !isEmpty() && currentNode.next != null) {
			currentNode = currentNode.next;
			return true;
		}
		return false;
	}

	@Override
	public boolean goToPrevious() {
		if (currentNode != firstNode && !isEmpty()) {
			currentNode = currentNode.prior;
			return true;
		}
		return false;
	}

	@Override
	public ListInterface<E> copy() {
		Node temp = currentNode;
		LinkedList<E> copy = new LinkedList<E>();
		if (isEmpty()) {
			return copy;
		}

		goToFirst();
		for (int i = 0; i < numberOfElements; i++) {
			copy.insert(currentNode.data);
			goToNext();
		}
		currentNode = temp;
		return copy;
	}
}