package edu.ntnu.idi.idatt.view;

import edu.ntnu.idi.idatt.model.DeckOfCards;
import edu.ntnu.idi.idatt.model.PlayingCard;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Collection;
import java.util.stream.Collectors;

public class MainUI extends Application {
  private DeckOfCards deck;
  private Collection<PlayingCard> currentHand;

  @Override
  public void start(Stage primaryStage) {
    // Create the deck
    deck = new DeckOfCards();

    // Create the UI
    BorderPane root = GameView.createLayout();

    // Set up the button action
    GameView.getDealHandButton().setOnAction(e -> dealHand());
    GameView.getCheckHandButton().setOnAction(e -> checkHand());

    // Create the scene
    Scene scene = new Scene(root, 700, 450);
    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.setTitle("Card Game");
    primaryStage.show();
  }

  private void dealHand() {
    // Deal 5 cards from the deck
    currentHand = deck.dealHand(5);

    // Display the cards
    GameView.displayCards(currentHand);

    // Reset the info fields
    GameView.updateInfo(0, "", false, false);
  }

  private void checkHand() {
    if (currentHand == null || currentHand.isEmpty()) {
      return;
    }

    // Calculate sum of faces
    int sum = currentHand.stream()
        .mapToInt(PlayingCard::getValue)
        .sum();

    // Get hearts cards
    String hearts = currentHand.stream()
        .filter(card -> card.getSuit() == 'H')
        .map(card -> "H" + card.getValue())
        .collect(Collectors.joining(" "));

    // Check for flush (all same suit)
    boolean flush = currentHand.stream()
        .map(PlayingCard::getSuit)
        .distinct()
        .count() == 1;

    // Check for queen of spades
    boolean queenOfSpades = currentHand.stream()
        .anyMatch(card -> card.getSuit() == 'S' && card.getValue() == 12);

    // Update the info display
    GameView.updateInfo(sum, hearts, flush, queenOfSpades);
  }

  public static void main(String[] args) {
    launch(args);
  }
}