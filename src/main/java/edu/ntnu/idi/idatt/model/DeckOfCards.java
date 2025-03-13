package edu.ntnu.idi.idatt.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DeckOfCards {
  private List<PlayingCard> deck;
  private Random rand = new Random();
  private final char[] suit = {'H', 'S', 'D', 'C'};

  public DeckOfCards() {
    deck = new ArrayList<>();
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
    if (n > deck.size()) {
      throw new IllegalArgumentException("n > deck.size");
    }
    return rand.ints(0, deck.size())
        .distinct()
        .limit(n)
        .mapToObj(deck::get)
        .collect(Collectors.toList());
  }


}
