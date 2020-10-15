package nl.vu.labs.phoenix.ap;

public class Identifier implements IdentifierInterface {

	private StringBuffer sb;

	public Identifier(char c) {
		if (Character.isAlphabetic(c)) {
			sb = new StringBuffer();
			sb.append(c);
		} 
	}

	public Identifier(IdentifierInterface copy) {
		StringBuffer sb = new StringBuffer();
		sb.append(copy.toString());
	}

	@Override
	public String toString() {
		return sb.toString();
	}

	@Override
	public int getLength() {
		return sb.length();
	}

	@Override
	public boolean add(char c) {
		if (Character.isDigit(c) || Character.isAlphabetic(c)) {
			sb.append(c);
			return true;
		}

		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Identifier other = (Identifier) obj;
		if (sb == null) {
			if (other.sb != null)
				return false;
		} else if (!sb.toString().equals(other.sb.toString()))
			return false;
		return true;
	}

	@Override
	public void init(char c) {
		sb = new StringBuffer(c);
	}
	
	@Override
	public int hashCode() {
		return sb.toString().hashCode();
	}
}