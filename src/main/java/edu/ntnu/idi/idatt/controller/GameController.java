package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.model.DeckOfCards;
import edu.ntnu.idi.idatt.model.PlayerHand;
import edu.ntnu.idi.idatt.model.PlayingCard;
import edu.ntnu.idi.idatt.view.GameView;
import java.util.List;
import java.util.stream.Collectors;

public class GameController {
  private final PlayerHand hand;
  private final DeckOfCards deck;
  private final GameView gameView;

  public GameController(PlayerHand playerHand, DeckOfCards deckOfCards, GameView gameView) {
    this.hand = playerHand;
    this.deck = deckOfCards;
    this.gameView = gameView;

    hand.addPropertyChangeListener(gameView);  // ✅ Now GameView listens for updates
    gameView.getDealHandButton().setOnAction(e -> dealHand());
    gameView.getCheckHandButton().setOnAction(e -> checkHand());

  }

  private void dealHand() {
    hand.clearHand();
    List<PlayingCard> newHand = (List) deck.dealHand(10);
    for (PlayingCard card : newHand) {
      hand.addCard(card);
    }

    hand.notifyHandChanged();
  }

  private void checkHand() {
    int sum = hand.sumOfAllFaces();

    String hearts = hand.onlyCardsOfHearts().stream()
        .map(card -> "H" + card.getValue())
        .collect(Collectors.joining(" "));

    boolean flush = !hand.checkFlush().isEmpty();
    boolean queenOfSpades = hand.queenOfSpadesInHand();

    // 🚀 Notify GameView with hand analysis
    hand.notifyHandChecked(sum, hearts, flush, queenOfSpades);
  }





}
