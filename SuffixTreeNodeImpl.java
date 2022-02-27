public class SuffixTreeNodeImpl extends SuffixTreeNode{

	public SuffixTreeNodeImpl() {
		super();
	}
	
	public SuffixTreeNodeImpl(CharLinkedList chars, SuffixTreeNode parent) {
		super(chars, parent);
	}
	
	@Override
	public SuffixTreeNode search(char c) {
		return binarySearch(c, 0, this.numOfChildren - 1);
	}

	@Override
	public SuffixTreeNode binarySearch(char target, int left, int right) {
		if (left<=right) {
			int middle = (left + right) / 2;
			char middle_char = this.children[middle].getChars().getFirst().getChar();
			if (middle_char == target) {
				return this.children[middle];
			}
			if (middle_char > target) {
				return binarySearch(target, left, middle - 1);
			}
			if (middle_char < target) {
				return binarySearch(target, middle + 1, right);
			}
		}
		return null;
	}

	@Override
	public void shiftChildren(int until) {
		for (int i = this.numOfChildren - 1 ; i >= until ; i--) {
			this.children[i+1]=this.children[i];
		}
	}

	@Override
	public void addChild(SuffixTreeNode node) {
		char nodes_char = node.getChars().getFirst().getChar();
		int index = this.numOfChildren;
		for (int i = 0 ; i < this.numOfChildren - 1 ; i++) {
			char current_char = this.children[i].chars.getFirst().getChar();
			char next_char = this.children[i+1].chars.getFirst().getChar();
			if (nodes_char > current_char && nodes_char < next_char) {
				index = i + 1;
			}
		}
		if (this.children[0] != null && nodes_char!='$') {
			if (nodes_char < this.children[0].chars.getFirst().getChar() ) {
				index = 0;
			}
		}
		this.shiftChildren(index);
		this.children[index] = node;
		this.numOfChildren++;
		node.parent=this;
	}

	@Override
	public void addSuffix(char[] word, int from) {
		if (from == word.length) {
			return;
		}
		else {
			char c = word[from];
			SuffixTreeNode temp = this.search(c);
			if (temp == null) {
				CharLinkedList temp_chars = CharLinkedList.from(c);
				temp = new SuffixTreeNodeImpl(temp_chars, this);
				this.addChild(temp);
			}
			temp.addSuffix(word, from + 1);
		}
		
	}
	
	private void real_compress(){
		if (this.numOfChildren == 1){
			if (this.chars==null) {
				SuffixTreeNodeImpl child = (SuffixTreeNodeImpl) this.children[0];
				child.real_compress();
			}
			else {
				SuffixTreeNode temp = this.children[0];
				this.chars.append(temp.chars);
				this.numOfChildren=temp.numOfChildren;
				this.children=temp.children;
				this.real_compress();
			}
		}
		if (this.numOfChildren>1){
			for(int i=0;i<this.numOfChildren;i++) {
				this.children[i].parent=this;
				SuffixTreeNodeImpl child_i = (SuffixTreeNodeImpl) this.children[i];
				child_i.real_compress();
			}
		}
	}
	
	
	private void calc () {
		if (this.getChars() == null) {
			this.totalWordLength = 0;
		}
		else {
			this.totalWordLength = this.restoreStringAlongPath().length();
		}
		
		if (this.numOfChildren == 0) {
			this.descendantLeaves = 1;
		}
		else {
			for(int i=0;i<this.numOfChildren;i++) {
				SuffixTreeNodeImpl child_i = (SuffixTreeNodeImpl) this.children[i];
				child_i.calc();
				this.descendantLeaves = this.getDescendantLeaves() + child_i.getDescendantLeaves();
			}
		}
	}
	

	
	@Override
	public void compress() {
		this.real_compress();
		this.calc();
	}



	@Override
	public int numOfOccurrences(char[] subword, int from) {
		// TODO Auto-generated method stub
		if (subword.length == 0) {
			return 0;
		}
		
		if (this.chars == null || this.chars.getFirst().getChar() != subword[from]){
			SuffixTreeNode son = this.search(subword[from]);
			 if (son == null) {
				 return 0;
			 }
			 else {
				 return son.numOfOccurrences(subword, from);
			 }
		}
		else {
			ICharLinkedListNode curr_c = this.getChars().getFirst();
			while (subword[from] == curr_c.getChar()) {
				if (from == (subword.length-1)) {
					return this.getDescendantLeaves();
				}
				if (curr_c.getNext() == null) {
					SuffixTreeNode son = this.search(subword[from+1]);
					if (son == null) {
						return 0;
					}
					else {
						return son.numOfOccurrences(subword, from+1);
					}
				}
				from = from +1;
				curr_c = curr_c.getNext();
			}		
		}
		return 0;
				
		
	}

}
