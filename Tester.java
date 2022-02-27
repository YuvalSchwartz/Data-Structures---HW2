/**
 * This is a testing framework. Use it extensively to verify that your code is working
 * properly.
 */
public class Tester {

	private static boolean testPassed = true;
	private static int testNum = 0;
	
	/**
	 * This entry function will test all classes created in this assignment.
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
	
		// Each function here should test a different class.

		//CharLinkedListNode
		testCharLinkedListNode();
		
		// CharLinkedList
		testCharLinkedList();

		// SuffixTreeNode
		testSuffixTreeNode(); 
		
		// SuffixTree
		testSuffixTree(); 
		
		// longestRepeatedSuffixTree
		testLongestRepeatedSuffixTree();
		
		// Notifying the user that the code have passed all tests. 
		if (testPassed) {
			System.out.println("All " + testNum + " tests passed!");
		}


	}

	/**
	 * This utility function will count the number of times it was invoked. 
	 * In addition, if a test fails the function will print the error message.  
	 * @param exp The actual test condition
	 * @param msg An error message, will be printed to the screen in case the test fails.
	 */
	private static void test(boolean exp, String msg) {
		testNum++;
		
		if (!exp) {
			testPassed = false;
			System.out.println("Test " + testNum + " failed: "  + msg);
		}
	}

	/**
	 * Checks the CharLinkedList class.
	 */
	private static void testCharLinkedListNode() {
		// constructor CharLinkedListNodeImpl(char c)
		ICharLinkedListNode node = new CharLinkedListNodeImpl('a');
		ICharLinkedListNode next_node = new CharLinkedListNodeImpl('m');
		// getChar()
		test(node.getChar() == 'a', "The char should be 'a'");
		
		//setNext(ICharLinkesListNode next)
		node.setNext(next_node);
		
		// getNext()
		test(node.getNext() == next_node, "This is not the next node");
		test(node.getNext().getNext() == null, "The next node sholud be null");
		test(node.getNext().getChar() == 'm', "The char should be 'm'");
	}
	
	/**
	 * Checks the CharLinkedList class.
	 */
	private static void testCharLinkedList(){
		CharLinkedList list = new CharLinkedListImpl();
		
		// size()
		test(list.size() == 0, "The size of the list should be 0");
		
		// add(char c)
		list.add('a');
		test(list.size() == 1, "The size of the list should be 1");
		
		// firstChar()
		test(list.firstChar() == 'a', "The first char should be 'a'");
		
		// append(CharLinkedList)
		CharLinkedList list2 = new CharLinkedListImpl();
		list2.add('h');
		list2.add('e');
		list2.add('y');
		
		list.append(list2);
		test(list.size() == 4, "The size should be 4");
		test(list.getLast().getChar() == 'y', "The last char should be 'y'");
		
	}

	/**
	 * Checks the SuffixTreeNode class.
	 */
	private static void testSuffixTreeNode() {
		// test empty root
		SuffixTreeNode node = new SuffixTreeNodeImpl();
		test(node.getTotalWordLength() == 0, "node word length should be 0");
		test(node.getNumOfChildren() == 0, "node num of children should be 0");

		// test search, binary search, shiftChildren and addChild
		SuffixTreeNode child1 = new SuffixTreeNodeImpl(CharLinkedList.from("abc"), node);
		SuffixTreeNode child2 = new SuffixTreeNodeImpl(CharLinkedList.from("bcd"), node);
		SuffixTreeNode child3 = new SuffixTreeNodeImpl(CharLinkedList.from("hello"), node);
		SuffixTreeNode child4 = new SuffixTreeNodeImpl(CharLinkedList.from("mmmmm"), node);
		node.setChildren(new SuffixTreeNode[]{child1, child2, child3, child4, null, null, null, null}, 4);

		// binary search
		test(node.binarySearch('b', 0, 3) == child2, "search for 'b' should find child2");

		// search
		test(node.search('a') == child1, "search for 'a' should return child1");
		test(node.search('x') == null, "search for 'x' should fail");

		// add child
		SuffixTreeNode child5 = new SuffixTreeNodeImpl(CharLinkedList.from("dog"), node);
		node.addChild(child5);
		test(node.getChildren()[2] == child5, "3rd child should be child5");
		
		// addSuffix(char[] word, int from)
		SuffixTreeNode node2 = new SuffixTreeNodeImpl();
		node2.addSuffix("cbda".toCharArray(), 0);
		node2.addSuffix("aa".toCharArray(), 0);
		node2.addSuffix("sadab".toCharArray(), 1);
		node2.addSuffix("b".toCharArray(), 0);
		node2.addSuffix("cbbc".toCharArray(), 0);
		test(node2.children[0].getChars().getFirst().getChar() == 'a', "The char is 'a'");

		//search
		test(node2.search('b') == node2.children[1], "This should be 2nd child.");
		test(node2.binarySearch('b', 2,2) == null, "This should be null.");
		test(node2.children[1].search('b') == node2.children[1].children[0], "This should be 1st child.");
		test(node2.children[1].search('d') == node2.children[1].children[1], "This should be 2nd child.");
		test(node2.children[1].search('s') == null, "This should be null.");
		
		//compress()
		node2.compress();
		test(node2.children[2].getChars().getFirst().getChar() == 'c', "The char is 'c'");
		test(node2.children[4] == null, "The children is null'");
		
		// descendantLeaves
		test(node2.getDescendantLeaves() == 5, "The descendant leaves  number should be 5.");
		test(node2.getChildren()[0].getDescendantLeaves() == 2, "The descendant leaves  number should be 2.");
		test(node2.getChildren()[0].getParent().getDescendantLeaves() == node2.getDescendantLeaves(), "The descendant leaves  number should be like its parent (5).");

		// parent
		test(node2.getChildren()[0].getChildren()[1].getParent() == node2.getChildren()[0], "This is not the parent!");
	
		// num of children
		int i = 0;
		while (node2.getChildren()[0].getChildren()[i] != null) {
			i++;
		}
		test(node2.getChildren()[0].getNumOfChildren() == i, "This is not the num of children.");
	}
	/**
	 * Checks the SuffixTree class.
	 */
	private static void testSuffixTree() {
		// constructor SuffixTreeImpl(String node)
		SuffixTree tree = new SuffixTreeImpl("zfgseaaags");
		SuffixTree tree2 = new SuffixTreeImpl("mississippi");
//		System.out.println(tree.getRoot().toString());
		test(tree.getRoot().children[tree.getRoot().getNumOfChildren()-1].getChars().getFirst().getChar() == '$', "The last child is $!");
		
		// contains(String subword)
		test(tree.contains("fgs") == true, "This should be true - fgs");
		test(tree.contains("aa") == true, "This should be true - aa");
		test(tree.contains("aaaa") == false, "This should be false - aaa");
		
		// numOfOccurences(String subword)
		test(tree.numOfOccurrences("aa") == 2, "The num of occurences should be 2. - aa");
		test(tree.numOfOccurrences("gs") == 2, "The num of occurences should be 2. - gs");
		test(tree.numOfOccurrences("fgs") == 1, "The num of occurences should be 1. - fgs");
		test(tree.numOfOccurrences("ss") == 0, "The num of occurences should be 0. - ss");

		test(tree2.numOfOccurrences("issi") == 2, "The num of occurences should be 2. - issi");
		test(tree2.getRoot().children[0].numOfOccurrences("ssi".toCharArray(), 0) == 2, "The num of occurences should be 2.");
		
		test(tree2.getRoot().getChildren()[3].getDescendantLeaves() == 4, "The descendant leaves  number should be 4");
		test(tree2.getRoot().getChildren()[3].getChildren()[1].getTotalWordLength() == 3, "The word length should be 3");
		test(tree.getRoot().getChildren()[0].getDescendantLeaves() == 3, "The descendant leaves  number should be 3");
		test(tree.getRoot().getChildren()[0].getChildren()[1].getParent().getChildren()[0] == tree.getRoot().getChildren()[0].getChildren()[0], "This should be it's brother!");
	}
	

	private static void testLongestRepeatedSuffixTree(){
		testLongestRepeated("mississippi", "issi");
		testLongestRepeated("abc", "X");
		testLongestRepeated("abbc", "b");
		testLongestRepeated("mippissippi", "ippi");
		testLongestRepeated("aaaaaaaa", "aaaaaaa");
		testLongestRepeated("zzqwyyxzzyy", "yy");
		testLongestRepeated("aabbbcc", "bb");
		testLongestRepeated("aabbcc", "a");
	}

	private static void testLongestRepeated(String word, String expected){
		test(new longestRepeatedSuffixTreeImpl(word).getLongestRepeatedSubstring().equals(expected), "Longest repeated substring should be " + expected);
	}

}