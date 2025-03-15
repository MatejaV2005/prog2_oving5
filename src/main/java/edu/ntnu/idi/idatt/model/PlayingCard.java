package edu.ntnu.idi.idatt.model;

public class PlayingCard {
  // a playing card has a value and a type (e.g 7 of hearts: H7, queen of spades: S12
  private final int value;
  private final char type;

  public PlayingCard(int value, char type) {
    this.value = value;
    this.type = type;
  }

  public int getValue() {
    return value;
  }

  public char getSuit() {
    return type;
  }


}
