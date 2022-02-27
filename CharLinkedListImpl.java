public class CharLinkedListImpl extends CharLinkedList{

	public CharLinkedListImpl() {
		super();
	}
	
	@Override
	public void add(char c) {
		ICharLinkedListNode new_node = new CharLinkedListNodeImpl(c);
		if (this.first == null){
			this.first = new_node;
			this.last = new_node;
		}
		else {
			this.last.setNext(new_node);
			this.last = new_node;
		}
	}

	@Override
	public char firstChar() {
		if (this.first == null) {
			return (Character)null;
		}
		else {
			return this.first.getChar();
		}
	}

	@Override
	public int size() {
		if (this.first == null) {
			return 0;
		}
		else {
			int counter=1;
			ICharLinkedListNode t = this.first;
			while (t!=this.last) {
				counter++;
				t = t.getNext();
			}
			return counter;
		}
	}

	@Override
	public void append(CharLinkedList toAppend) {
		if (this.first == null) {
			this.first = toAppend.getFirst();
		}
		else {
			this.last.setNext(toAppend.getFirst());
		}
		this.last = toAppend.getLast();
	}

}
