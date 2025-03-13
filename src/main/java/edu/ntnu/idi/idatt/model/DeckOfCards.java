package edu.ntnu.idi.idatt.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DeckOfCards {
  private List<PlayingCard> deck;
  private final char[] suit = {'H', 'S', 'D', 'C'};

  DeckOfCards() {
    deck = new ArrayList<PlayingCard>();
    initCards();
  }

  private void initCards() {
    for (Character c : suit) {
      for (int i = 1; i <= 13; i++) {
        PlayingCard card = new PlayingCard(i, c);
        deck.add(card);
      }
    }
  }

  public Collection<PlayingCard> dealHand(int n) {
    return null; //TODO
  }


}
