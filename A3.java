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

/**
 * Runs the program
 * */
   public static void main(String[] args) {
      A3 a3 = new A3();
      a3.run();
   }

   /**
    * Method to run the program
    * */
   public void run() {
      readInput();
      createdAlternativeOrderBSTs();
      printResults();
   }

   /**
    * Creates alternative order BSTs based on different criteria.
    * This method iterates through the alphabetical BST, modifies it, and also adds Avengers to other BSTs
    * based on different sorting.
    */
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
    * Reads the input stream and keeps track of how many times Avengers are mentioned by alias,
    * last name, or performer name.
    * This method processes each word from the input, cleans it, and updates the alphabeticalBST
    * if the word matches an Avenger's details.
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

   /**
    * Finds an Avenger in the avengerRoster based on a given word.
    * This method checks if the provided word matches any Avenger's alias, last name, or performer name
    * in the avengerRoster.
    *
    * @param word The word that is going to be matched with Avenger details.
    * @return An Avenger object if a match is found; null otherwise.
    */
   private Avenger findAvengerInRoster(String word) {
      for (String[] avenger : avengerRoster) {
         if (avenger[0].equals(word) || avenger[1].equals(word) || avenger[2].equals(
               word)) {
            return new Avenger(avenger[0], avenger[1], avenger[2]);
         }
      }
      return null;
   }

  /**
    * Calculates the optimal height of BST for a given list of elements.
    *
    * @param The BST
    * @return The optimal height of the BST.
    */
   private int getOptimalHeight(BST<Avenger> list) {
      return (int) Math.floor((Math.log(list.size()) / Math.log(2)));
   }

   /**
    * Cleans a given string by removing all non-alphabetic characters and converting it to lower case.
    * If an apostrophe is present, only the part of the string before the apostrophe is considered.
    *
    * @param next The string to be cleaned.
    * @return The cleaned string.
    */
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

   /**
    * Prints each element in BST of Avenger objects.
    * This method iterates through the BST and prints each Avenger object.
    *
    * @param list The BST of Avenger objects to be printed.
    */
   private void printList(BST<Avenger> list) {
      for (Avenger hero: list) {
         System.out.println(hero);
      }
   }

   /**
    * Prints the top 'N' elements from a BST of Avenger objects.
    * The method iterates through the BST and stops after printing 'topN' elements.
    *
    * @param list The BST of Avenger objects from which the top elements are to be printed.
    */
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
    * Prints each element in a binary search tree (BST) of Avenger objects.
    * This method iterates through the BST and prints each Avenger object.
    *
    * @param list The BST of Avenger objects to be printed.
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
