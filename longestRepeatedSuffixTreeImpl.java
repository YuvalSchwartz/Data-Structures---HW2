
public class longestRepeatedSuffixTreeImpl extends longestRepeatedSuffixTree {

	public longestRepeatedSuffixTreeImpl(String word) {
		super(word);
	}
	
	private void findLongest(SuffixTreeNode root, SuffixTreeNode curr_node) {
		if (root.numOfOccurrences(curr_node.restoreStringAlongPath().toCharArray(), 0) > 1){
			if (curr_node.getTotalWordLength() > this.maxLength) {
				this.maxLength = curr_node.getTotalWordLength();
				this.substringStartNode = curr_node;
			}
		}
		for(int i=0;i<curr_node.numOfChildren;i++) {
			this.findLongest(root, curr_node.children[i]);
		}
	}
	
	@Override
	public void createLongestRepeatedSubstring() {
		// TODO Auto-generated method stub
		this.maxLength = 0;
		this.substringStartNode = null;
		this.findLongest(this.root, this.root);
	}

	@Override
	public String getLongestRepeatedSubstring() {
		if (this.substringStartNode == null) {
			return "X";
		}
		else {
			return this.substringStartNode.restoreStringAlongPath();
		}
	}

}
