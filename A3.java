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
    { "captainamerica", "rogers", "evans" },
    { "ironman", "stark", "downey" },
    { "blackwidow", "romanoff", "johansson" },
    { "hulk", "banner", "ruffalo" },
    { "blackpanther", "tchalla", "boseman" },
    { "thor", "odinson", "hemsworth" },
    { "hawkeye", "barton", "renner" },
    { "warmachine", "rhodes", "cheadle" },
    { "spiderman", "parker", "holland" },
    { "wintersoldier", "barnes", "stan" },
  };

  private int topN = 4;
  private int totalwordcount = 0;
  private Scanner input = new Scanner(System.in);

  private BST<Avenger> alphabticalBST = new BST<>();
  Iterator<Avenger> BSTIterator = alphabticalBST.iterator();

  // private BST<Avenger> mentionBST = new BST<Avenger>(new AvengerComparatorMentionOrder());

  private BST<Avenger> mostPopularAvengerBST = new BST<Avenger>(
    new AvengerComparatorFreqDesc()
  );
  private BST<Avenger> mostPopularPerformerBST = new BST<Avenger>(
    new AvengerPerformerComparatorFreqDesc()
  );

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
    /* TODO:
     *   - delete the following two avengers (if they exist) from the alphabetical tree
     *   	- barton
     *   	- banner
     *   use the tree iterator to do an in-order traversal of the alphabetical tree,
     *   and add avengers to the other trees with alternative ordering
     */
    for (Avenger a : alphabticalBST) {
      if (
        a.getHeroName().equals("barton") || a.getHeroName().equals("banner")
      ) {
        alphabticalBST.delete(a);
      }
      mostPopularPerformerBST.add(a);
      mostPopularAvengerBST.add(a);
    }
  }

  /**
   * read the input stream and keep track how many times avengers are mentioned by
   * alias or last name or performer name.
   */
  private void readInput() {
    /* Create a mention index counter and initialize it to 1
     * While the scanner object has not reached end of stream,
     * 	- read a word.
     * 	- clean up the word
     * 	- if the word is not empty, add the word count.
     * 	- Check if the word is either an avenger alias or last name, or performer
     * last name then
     *		- Create a new avenger object with the corresponding alias and last name and
     *  performer last name.
     *		- check if this avenger has already been added to the alphabetically ordered
     *  tree
     *			- if yes, increase the corresponding frequency count for the object
     * already in the tree.
     *			- if no, add the newly created avenger to the alphabetically ordered BST,
     *				- remember to set the frequency and the mention index.
     * You need to think carefully about how you are keeping track of the mention
     * order by setting the mention order for each new avenger.
     */

    while (input.hasNext()) {
      String word = cleanWord(input.next());

      if (word.length() > 0) {
        totalwordcount++;

        Avenger a = createNewAvenger(word);

        if (a != null) {
          Avenger mentioned = alphabticalBST.find(a);

          if (mentioned == null) {
            alphabticalBST.add(a);
            a.setMention(word);
          } else if (mentioned != null) {
            mentioned.setMention(word);
          }
        }
      }
    }
  }

  //we also need to add helper methods from previous assignments

  private int getOptimalHeight(BST<Avenger> list) {
    return (int) (Math.log(list.size()) / Math.log(2));
  }

  private Avenger createNewAvenger(String word) {
    for (int i = 0; i < avengerRoster.length; i++) {
      if (
        avengerRoster[i][0].equals(word) ||
        avengerRoster[i][1].equals(word) ||
        avengerRoster[i][2].equals(word)
      ) {
        return new Avenger(
          avengerRoster[i][0],
          avengerRoster[i][1],
          avengerRoster[i][2]
        );
      }
    }
    return null;
  }

  private String cleanWord(String next) {
    // First, if there is an apostrophe, the substring
    // before the apostrophe is used and the rest is ignored.
    // Words are converted to all lowercase.
    // All other punctuation and numbers are skipped.
    String ret;
    int inx = next.indexOf('\'');
    if (inx != -1) ret =
      next
        .substring(0, inx)
        .toLowerCase()
        .trim()
        .replaceAll("[^a-z]", ""); else ret =
      next.toLowerCase().trim().replaceAll("[^a-z]", "");
    return ret;
  }

  /**
   * print the results
   */
  private void printResults() {
    // Todo: Print the total number of words (this total should not include words
    //  that are all digits or punctuation.)
    System.out.println("Total number of words: " + totalwordcount);
    // TODO: Print the number of mentioned avengers after deleting "barton" and
    //  "banner".
    System.out.println(
      "Number of Avengers Mentioned: " + alphabticalBST.size()
    );
    System.out.println();

    System.out.println(
      "All avengers in the order they appeared in the input stream:"
    );
    // TODO: Print the list of avengers in the order they appeared in the input
    // Make sure you follow the formatting example in the sample output
    System.out.println();

    System.out.println("Top " + topN + " most popular avengers:");
    // TODO: Print the most popular avengers, see the instructions for tie breaking
    // Make sure you follow the formatting example in the sample output
    mostPopularAvengerBST.traverseRight();
    System.out.println();

    System.out.println("Top " + topN + " most popular performers:");
    // TODO: Print the most popular performers, see the instructions for tie breaking
    // Make sure you follow the formatting example in the sample output
    System.out.println();

    System.out.println("All mentioned avengers in alphabetical order:");
    // TODO: Print the list of avengers in alphabetical order
    for (Avenger a : alphabticalBST) {
      System.out.println(a);
    }

    System.out.println();
    // TODO: Print the actual height and the optimal height for each of the four trees.
    //      System.out.println(
    //            "Height of the mention order tree is : " + mentionBST.height() + "
    //            (Optimal" +
    //                  " height for this tree is : " + getOptimalHeight(
    //                  mentionBST) + ")");
    //      System.out.println(
    //            "Height of the alphabetical tree is : " + alphabticalBST.height() +
    //            " " +
    //                  "(Optimal height for this tree is : " + getOptimalHeight(
    //                  alphabticalBST) + ")");
    //      System.out.println(
    //            "Height of the most frequent tree is : " + mostPopularAvengerBST
    //            .height() + " (Optimal height for this tree is : " + getOptimalHeight(
    //                  mostPopularAvengerBST) + ")");
    //      System.out.println(
    //            "Height of the most frequent performer tree is : " +
    //            mostPopularPerformerBST.height()
    //                  + " (Optimal height for this tree is : " + getOptimalHeight(
    //                  mostPopularPerformerBST) + ")");
  }
}
