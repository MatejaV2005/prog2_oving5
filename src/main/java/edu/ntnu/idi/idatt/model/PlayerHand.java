package edu.ntnu.idi.idatt.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerHand {
  private List<PlayingCard> hand;

  PlayerHand() {
    hand = new ArrayList<PlayingCard>();
  }

  public void addCard(PlayingCard card) {
    hand.add(card);
  }

  public List<List<PlayingCard>> checkFlush() {
    return hand.stream()
        .collect(Collectors.groupingBy(PlayingCard::getType))  // Group by suit
        .entrySet().stream()
        .filter(entry -> entry.getValue().size() >= 5)  // checkc the value (list) of the entry, keep only with 5+ cards
        .map(entry -> entry.getValue().stream()         // Extract the list of cards, no need for the suit
            .sorted(Comparator.comparingInt(PlayingCard::getValue).reversed())
            .limit(5)  // Keep the top 5 highest-ranked cards
            .collect(Collectors.toList()))  // Collect as a list
        .collect(Collectors.toList());  // Return All flushes
  }

  public void addCardToHand(PlayingCard card) {
    hand.add(card);
  }

  public List<PlayingCard> getHand() {
    return hand;
  }

}
