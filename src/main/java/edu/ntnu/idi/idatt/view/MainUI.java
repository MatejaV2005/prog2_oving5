package edu.ntnu.idi.idatt.view;

import edu.ntnu.idi.idatt.controller.GameController;
import edu.ntnu.idi.idatt.model.DeckOfCards;
import edu.ntnu.idi.idatt.model.PlayerHand;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainUI extends Application {
  private DeckOfCards deck;
  private PlayerHand playerHand;
  private GameView gameView;

  @Override
  public void start(Stage primaryStage) {
    // ✅ Initialize Deck & Player Hand
    deck = new DeckOfCards();
    playerHand = new PlayerHand();
    gameView = new GameView(); // Ensure GameView exists

    // ✅ Create Game Layout
    BorderPane root = GameView.createLayout();

    // ✅ Instantiate Controller AFTER initializing objects
    GameController controller = new GameController(playerHand, deck, gameView);

    // ✅ Setup Scene
    Scene scene = new Scene(root, 700, 450);
    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.setTitle("Card Game");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
