public class CharLinkedListNodeImpl implements ICharLinkedListNode {

	private char c;
	private ICharLinkedListNode next;
	
	// Constructor
	public CharLinkedListNodeImpl(char value) {
		this.c = value;
		this.next = null;
	}
	
	// Returns node's char
	@Override
	public char getChar() {
		return this.c;
	}

	// Returns the next node
	@Override
	public ICharLinkedListNode getNext() {
		return this.next;
	}

	// Sets the next node
	@Override
	public void setNext(ICharLinkedListNode next) {
		this.next = next;
	}
	
}
