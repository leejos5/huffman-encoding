// Joshua Lee
// CSE 143 BH
// TA: RanDair Porter
// Assignment #8: Huffman (HuffmanNode)

// This program will function as a binary tree node / leaf node specifically
// for the Huffman Tree program.

public class HuffmanNode implements Comparable<HuffmanNode> {
   public int character;      // character of the node in integer representation.
   public int data;           // frequency of the character.
   public HuffmanNode left;   // reference to the left subtree.
   public HuffmanNode right;  // reference to the right subtree.
   
   // post: constructs a Huffman node with the passed data and character value.
   public HuffmanNode(int character, int data) {
      this(character, data, null, null);
   }
   
   // post: constructs a Huffman node with the passed data, character, and references.
   public HuffmanNode(int character, int data, HuffmanNode left, HuffmanNode right) {
      this.character = character;
      this.data = data;
      this.left = left;
      this.right = right;
   }
   
   // post: compares the values of two Huffman nodes, returning positive number if 
   //       the first is larger, 0 if the same, negative if smaller.
   public int compareTo(HuffmanNode other) {
      return data - other.data;
   }
}