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
   * @param mentionIndex -
   */
  public Avenger(String heroAlias, String heroName, String performer) {
    this.heroAlias = heroAlias;
    this.heroName = heroName;
    this.performer = performer;
    this.nameFreq = 0;
    this.aliasFreq = 0;
    this.performerFreq = 0;
    this.mentionIndex = 0;
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
  
  public void setMentionIndex(int mentionIndex) {
	  this.mentionIndex = mentionIndex;
  }

  /**
   * Adds all the counters for name, alias and performer frequencies.
   *
   * @return - sum of all frequencies.
   */
  public int getTotalMentions() {
    return nameFreq + aliasFreq + performerFreq;
  }

  /**
   * Compares heroAliases to determine the natural ordering 
   * 
   * @param o - the Avenger object to compare against the current instance
   * @return -1 is o is null  or the compareTo result
   * */
  @Override
  public int compareTo(Avenger o) {
    if (o == null) {
      return -1;
    }
    return this.heroAlias.compareTo(o.heroAlias);
  }

  /**
   * Determines whether  given object is equal to the current Avenger
   * 
   * @param other - the object to compare
   * @return - true if equal
   * */
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
