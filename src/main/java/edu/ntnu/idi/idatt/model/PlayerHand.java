package edu.ntnu.idi.idatt.model;

import java.util.ArrayList;
import java.util.List;

public class PlayerHand {
  private List<PlayingCard> hand;

  PlayerHand() {
    hand = new ArrayList<PlayingCard>();
  }

  public void addCard(PlayingCard card) {
    hand.add(card);
  }

  public void checkFlush() {

  }

  public List<PlayingCard> getHand() {
    return hand;
  }

}
