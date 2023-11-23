import java.util.Comparator;

public class Avenger implements Comparable<Avenger> {

  private String heroAlias;
  private String heroName;
  private String performer;

  private int nameFreq;
  private int aliasFreq;
  private int performerFreq;
  private int mentionIndex;

  /**
   * Creates an Avenger object and initializes its properties.
   *
   * @param heroAlias - The avenger's hero name.
   * @param heroName  - The avenger's real last name.
   * @param performer - The last name of the performer who played the avenger.
   * @param mentionIndex
   */
  public Avenger(String heroAlias, String heroName, String performer) {
    this.heroAlias = heroAlias;
    this.heroName = heroName;
    this.performer = performer;
    this.nameFreq = 0;
    this.aliasFreq = 0;
    this.performerFreq = 0;
    this.mentionIndex = mentionIndex;
  }

  public String getHeroAlias() {
    return heroAlias;
  }

  public String getHeroName() {
    return heroName;
  }

  public String getPerformer() {
    return performer;
  }

  public int getAliasFreq() {
    return aliasFreq;
  }

  public int getNameFreq() {
    return nameFreq;
  }

  public int getPerformerFreq() {
    return performerFreq;
  }

  public int getMentionIndex() {
    return mentionIndex;
  }

  /**
   * Increments the counter of alias, name, or performer if it matches the input.
   *
   * @param word - The input read by the scanner after being properly formatted.
   */
  public void setMention(String word) {
    if (this.heroAlias.equals(word)) {
      aliasFreq++;
    } else if (this.heroName.equals(word)) {
      nameFreq++;
    } else if (this.performer.equals(word)) {
      performerFreq++;
    }
  }

  /**
   * Adds all the counters for name, alias and performer frequencies.
   *
   * @return - sum of all frequencies.
   */
  public int getTotalMentions() {
    return nameFreq + aliasFreq + performerFreq;
  }

  @Override
  public int compareTo(Avenger o) {
    if (o == null) {
      return -1;
    }
    return this.heroAlias.compareTo(o.heroAlias);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    Avenger a = (Avenger) o;

    if (this.getHeroAlias().equals(a.getHeroAlias())) {
      return true;
    }
    return false;
  }

  // comparator for sorting by mention index
  public static Comparator<Avenger> MentionIndexComparator = new Comparator<Avenger>() {
    @Override
    public int compare(Avenger a1, Avenger a2) {
      return Integer.compare(a1.mentionIndex, a2.mentionIndex);
    }
  };

  /**
   * Creates a string representation for the Avenger.
   */
  @Override
  public String toString() {
    return (
      heroAlias +
      " aka " +
      heroName +
      " performed by " +
      performer +
      " " +
      "mentioned " +
      "(n: " +
      nameFreq +
      " + a: " +
      aliasFreq +
      " + p: " +
      performerFreq +
      ")" +
      " time(s)"
    );
  }
}
