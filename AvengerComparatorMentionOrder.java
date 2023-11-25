import java.util.Comparator;

public class AvengerComparatorMentionOrder implements Comparator<Avenger> {

   /**
    * Compares number of occurrences in input stream for each Avenger
    *
    * @param o1 - Avenger object 1
    * @param o2 - Avenger object 2
    * @return - int representing the prescribed ordering
    */
   @Override
   public int compare(Avenger o1, Avenger o2) {

      return o1.getMentionIndex() - o2.getMentionIndex();
   }


}
