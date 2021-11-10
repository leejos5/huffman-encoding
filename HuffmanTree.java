// Joshua Lee
// CSE 143 BH
// TA: RanDair Porter
// Assignment #8: Huffman (HuffmanTree)

// This program will use Huffman coding to compress and expand text files based on the
// frequency of the characters.

import java.util.*;
import java.io.*;

public class HuffmanTree {
   private HuffmanNode overallRoot; // reference to the top of the Huffman Tree
   
   // post: uses the passed list of character frequencies to create the Huffman Tree with
   //       each character and their frequency, with integer values representing the character.
   //       In case of equal values, first value will be left subtree, second value will be right.
   public HuffmanTree(int[] count) {
      Queue<HuffmanNode> tree = new PriorityQueue<HuffmanNode>();
      for (int i = 0; i < count.length; i++) {
         if (count[i] > 0) {
            tree.add(new HuffmanNode(i, count[i]));
         }
      }
      tree.add(new HuffmanNode(count.length, 1));
      while(tree.size() > 1) {
         HuffmanNode first = tree.remove();
         HuffmanNode second = tree.remove();
         tree.add(new HuffmanNode(-1, first.data + second.data, first, second));
      }
      overallRoot = tree.remove();
   }
   
   // pre: Scanner contains a tree stored in standard format (otherwise loss of functionality)
   // post: builds the Huffman Tree from a given input file. No longer accounts for
   //       frequencies of characters since tree is already in format.
   public HuffmanTree(Scanner input) {
      overallRoot = new HuffmanNode(-1, 0);
      while (input.hasNextLine()) {
         int n = Integer.parseInt(input.nextLine());
         buildTree(input, overallRoot, input.nextLine(), n);
      }
   }
   
   // post: helps build the Huffman Tree from a given input file, returning a root representing
   //       the added node, a tracker of its code, and the integer value of the character.
   private HuffmanNode buildTree(Scanner input, HuffmanNode root, String code, int n) {
      if (code.isEmpty()) {
         root.character = n;
      } else {
         if (code.charAt(0) == '0') {
            if (root.left == null) {
               root.left = new HuffmanNode(-1, 0);
            }
            root = buildTree(input, root.left, code.substring(1), n);
         } else {
            if (root.right == null) {
               root.right = new HuffmanNode(-1, 0);
            }
            root = buildTree(input, root.right, code.substring(1), n);
         }
      }
      return root;
   }
   
   // post: Writes the Huffman Tree to the given output file in standard format, with each
   //       character represented by its integer value and standard traversal code.
   public void write(PrintStream output) {
      write(output, overallRoot, "");
   }
   
   // post: helps write the Huffman Tree to the given output file, returning a root node
   //       representing the character, as well as a tracker of that character's traversal code.
   private void write(PrintStream output, HuffmanNode root, String soFar) {
      if (root != null) {
         write(output, root.left, soFar + 0);
         if (root.left == null && root.right == null) {
            output.println(root.character);
            output.println(soFar);
         }
         write(output, root.right, soFar + 1);
      }
   }
   
   // pre: Input stream's characters are legally encoded with this Huffman Tree
   //      (otherwise loses functionality)
   // post: Reads bits from the input stream to write the respective characters to the
   //       output file. Uses a psedo "eof" value to signal the end of the input that is
   //       not included in the output.
   public void decode(BitInputStream input, PrintStream output, int eof) {
      HuffmanNode curr = overallRoot;
      while (curr.character != eof) {
         curr = overallRoot;
         while (curr.left != null && curr.right != null) {
            int path = input.readBit();
            if (path == 0) {
               curr = curr.left;
            } else {
               curr = curr.right;
            }
         }
         if (curr.character != eof) {
            output.write(curr.character);
         }
      }
   }
}