import java.util.Comparator;

/**
 * Comparator to sort Avenger objects by performer frequency.
 */
public class AvengerPerformerComparatorFreqDesc implements Comparator<Avenger> {
   /**
    * Compares 2 Avengers, first by frequently mentioned performers, then
    * shortest to longest name, then alphabetical order of alias.
    *
    * @param avenger1 - the first Avenger to compare.
    * @param avenger2 - the second Avenger to compare.
    * @return - an int representing the appropriate ordering.
    */

   @Override
   public int compare(Avenger avenger1, Avenger avenger2) {
      if (avenger1 == avenger2) {
         return 0;
      }

      int result = avenger2.getPerformerFreq() - avenger1.getPerformerFreq();

      if (result == 0) {
         result = avenger1.getHeroName().length() - avenger2.getHeroName().length();

         if (result == 0) {
            result = avenger1.getHeroAlias().compareTo(avenger2.getHeroAlias());
         }
      }

      return result;
   }
}
