package edu.ntnu.idi.idatt.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerHand {
  private List<PlayingCard> hand;
  private final PropertyChangeSupport support;

  public PlayerHand() {
    hand = new ArrayList<>();
    support = new PropertyChangeSupport(this);
  }

  public void addCard(PlayingCard card) {
    hand.add(card);
  }

  public List<List<PlayingCard>> checkFlush() {
    return hand.stream()
        .collect(Collectors.groupingBy(PlayingCard::getSuit))  // Group by suit
        .entrySet().stream()
        .filter(entry -> entry.getValue().size() >= 5)  // checkc the value (list) of the entry, keep only with 5+ cards
        .map(entry -> entry.getValue().stream()         // Extract the list of cards, no need for the suit
            .sorted(Comparator.comparingInt(PlayingCard::getValue).reversed())
            .limit(5)  // Keep the top 5 highest-ranked cards
            .collect(Collectors.toList()))  // Collect as a list
        .collect(Collectors.toList());  // Return All flushes
  }

  public int sumOfAllFaces() {
    return hand.stream().mapToInt(PlayingCard::getValue).sum();
  }

  public List<PlayingCard> onlyCardsOfHearts() {
    return hand.stream().filter(card -> card.getSuit() == 'H').
        collect(Collectors.toList());
  }

  public boolean queenOfSpadesInHand() {
    return hand.stream()
        .anyMatch(card -> card.getSuit() == 'S' && card.getValue() == 12);
  }




  // Method for adding/connecting the observable class PlayerHand to a Listener
  // in this case GameView will be the subscribed listener
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    support.addPropertyChangeListener(listener);
  }

  public void clearHand() {
    hand.clear();
  }


  public List<PlayingCard> getHand() {
    return hand;
  }

  public void notifyHandChanged() {
    support.firePropertyChange("handChanged", null, new ArrayList<>(hand));  // 🔥 Notify UI ONCE
  }

  public void notifyHandChecked(int sum, String hearts, boolean flush, boolean queenOfSpades) {
    support.firePropertyChange("handAnalysis", null, new Object[]{sum, hearts, flush, queenOfSpades});
  }


}
