import java.util.Iterator;
import java.util.Scanner;

/**
 * COMP 2503 Fall 2023 Assignment 3 Avenger Statistics
 * <p>
 * This program reads a input stream and keeps track of the statistics for avengers
 * mentioned by name, alias, or performer's last name.
 * The program uses a BST
 * for storing the data. Multiple BSTs with alternative orderings are
 * constructed to show the required output.
 *
 * @author Maryam Elahi
 * @date Fall 2023
 */

public class A3 {

   public String[][] avengerRoster = {
         {"captainamerica", "rogers", "evans"},
         {"ironman", "stark", "downey"},
         {"blackwidow", "romanoff", "johansson"},
         {"hulk", "banner", "ruffalo"},
         {"blackpanther", "tchalla", "boseman"},
         {"thor", "odinson", "hemsworth"},
         {"hawkeye", "barton", "renner"},
         {"warmachine", "rhodes", "cheadle"},
         {"spiderman", "parker", "holland"},
         {"wintersoldier", "barnes", "stan"},
         };

   private int topN = 4;
   private int totalwordcount = 0;
   private Scanner input = new Scanner(System.in);
   private int mentionIndex;

   private BST<Avenger> alphabticalBST = new BST<>();
   private BST<Avenger> mentionBST =
         new BST<Avenger>(new AvengerComparatorMentionOrder());
   private BST<Avenger> mostPopularAvengerBST =
         new BST<Avenger>(new AvengerComparatorFreqDesc());
   private BST<Avenger> mostPopularPerformerBST =
         new BST<Avenger>(new AvengerPerformerComparatorFreqDesc());

   public static void main(String[] args) {
      A3 a3 = new A3();
      a3.run();
   }

   public void run() {
      readInput();
      createdAlternativeOrderBSTs();
      printResults();
   }

   private void createdAlternativeOrderBSTs() {

      for (Avenger avenger : alphabticalBST) {
         if (avenger.getHeroName() == "barton" || avenger.getHeroName() == "banner") {
            alphabticalBST.delete(avenger);
         }
         else {
            mentionBST.add(avenger);
            mostPopularAvengerBST.add(avenger);
            mostPopularPerformerBST.add(avenger);
         }
      }
   }

   /**
    * read the input stream and keep track how many times avengers are mentioned by
    * alias or last name or performer name.
    */
   private void readInput() {

      mentionIndex = 0;
      while (input.hasNext()) {
         String word = cleanWord(input.next());

         if (word.isEmpty()) {
            continue;
         }

         totalwordcount++;

         Avenger current = findAvengerInRoster(word);
         if (current == null) {
            continue;
         }

         Avenger avengerInTree = alphabticalBST.find(current);

         if (avengerInTree == null) {
            // Adding new Avenger to the BST
            alphabticalBST.add(current);
            current.setMentionIndex(++mentionIndex);
            avengerInTree = current;
         }

         avengerInTree.setMention(word);

      }
   }

   private Avenger findAvengerInRoster(String word) {
      for (String[] avenger : avengerRoster) {
         if (avenger[0].equals(word) || avenger[1].equals(word) || avenger[2].equals(
               word)) {
            return new Avenger(avenger[0], avenger[1], avenger[2]);
         }
      }
      return null;
   }

   //we also need to add helper methods from previous assignments

   private int getOptimalHeight(BST<Avenger> list) {
      return (int) Math.floor((Math.log(list.size()) / Math.log(2)));
   }

   private String cleanWord(String next) {
      String ret;
      int inx = next.indexOf('\'');
      if (inx != -1) ret =
            next
                  .substring(0, inx)
                  .toLowerCase()
                  .trim()
                  .replaceAll("[^a-z]", "");
      else ret =
            next.toLowerCase().trim().replaceAll("[^a-z]", "");
      return ret;
   }

   private void printList(BST<Avenger> list) {
      for (Avenger hero: list) {
         System.out.println(hero);
      }
   }

   private void topList(BST<Avenger> list) {
      int heroCount = 0;
      for (Avenger hero: list) {
         System.out.println(hero);
         if (++heroCount >= topN) {
            break;
         }
      }
   }

   /**
    * print the results
    */
   private void printResults() {
      // Prints the total number of words
      System.out.println("Total number of words: " + totalwordcount);

      //Prints the number of mentioned avengers after deleting "barton" and "banner".
      System.out.println("Number of Avengers Mentioned: " + alphabticalBST.size());
      System.out.println();

      System.out.println("All avengers in the order they appeared in the input stream:");

      // Make sure you follow the formatting example in the sample output
      printList(mentionBST);
      System.out.println();

      System.out.println("Top " + topN + " most popular avengers:");
      // Prints the most popular avengers
      topList(mostPopularAvengerBST);
      System.out.println();

      System.out.println("Top " + topN + " most popular performers:");
      // Prints the most popular performers
      topList(mostPopularPerformerBST);
      System.out.println();

      System.out.println("All mentioned avengers in alphabetical order:");
      printList(alphabticalBST);

      System.out.println();

      System.out.println(
            "Height of the mention order tree is : " + mentionBST.height() + " (Optimal" +
                  " height for this tree is : " + getOptimalHeight(
                  mentionBST) + ")");
      System.out.println(
            "Height of the alphabetical tree is : " + alphabticalBST.height() +
                  " " + "(Optimal height for this tree is : " + getOptimalHeight(
                  alphabticalBST) + ")");
      System.out.println("Height of the most frequent tree is : " + mostPopularAvengerBST
            .height() + " (Optimal height for this tree is : " + getOptimalHeight(
            mostPopularAvengerBST) + ")");
      System.out.println("Height of the most frequent performer tree is : " +
                               mostPopularPerformerBST.height()
                               + " (Optimal height for this tree is : " + getOptimalHeight(
            mostPopularPerformerBST) + ")");
   }
}
