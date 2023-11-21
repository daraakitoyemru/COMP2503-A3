import java.util.Comparator;

/**
 * Comparator to sort Avenger objects by number of times mentioned.
 */
public class AvengerComparatorFreqDesc implements Comparator<Avenger>
{
   /**
    * Compares 2 Avengers, first by total number of mentions, then by
    * performer.
    *
    * @param avenger1 - the first Avenger object to compare.
    * @param avenger2 - the second Avenger object to compare.
    * @return - an int representing the prescribed ordering.
    */
   @Override
   public int compare(Avenger avenger1, Avenger avenger2)
   {
      if (avenger1 == avenger2)
      {
         return 0;
      }

      int result = avenger2.getTotalMentions() - avenger1.getTotalMentions();

      if (result == 0)
      {
         result = avenger1.getPerformer().compareTo(avenger2.getPerformer());
      }

      return result;
   }
}
