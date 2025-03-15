package edu.ntnu.idi.idatt.view;

import edu.ntnu.idi.idatt.model.PlayingCard;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Collection;

public class GameView {
  private static Pane cardDisplayArea;
  private static Button dealHandButton;
  private static Button checkHandButton;
  private static TextField sumField;
  private static TextField heartsField;
  private static TextField flushField;
  private static TextField queenField;

  public static BorderPane createLayout() {
    BorderPane layout = new BorderPane();

    // Center: Card display area
    cardDisplayArea = createCardDisplayArea();
    layout.setCenter(cardDisplayArea);

    // Right: Buttons
    VBox buttonBox = createButtonBox();
    layout.setRight(buttonBox);

    // Bottom: Info box with game state
    GridPane infoBox = createInfoBox();
    layout.setBottom(infoBox);

    // Add padding around the entire layout
    layout.setPadding(new Insets(10));

    return layout;
  }

  private static Pane createCardDisplayArea() {
    // Create a pane with a light gray background and a border
    Pane displayArea = new Pane();
    Rectangle background = new Rectangle(500, 300);
    background.setFill(Color.LIGHTGRAY);
    background.setStroke(Color.BLACK);
    displayArea.getChildren().add(background);

    // Add the instructional text
    Text instruction = new Text("Your cards will be displayed here");
    instruction.setX(background.getWidth() / 2 - 100);
    instruction.setY(background.getHeight() / 2);
    displayArea.getChildren().add(instruction);

    return displayArea;
  }

  private static VBox createButtonBox() {
    // Create buttons with specific sizes to match the wireframe
    dealHandButton = new Button("Deal hand");
    dealHandButton.setPrefWidth(100);

    checkHandButton = new Button("Check hand");
    checkHandButton.setPrefWidth(100);

    // Add buttons to a VBox with spacing
    VBox buttonBox = new VBox(20, dealHandButton, checkHandButton);
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.setPadding(new Insets(10));

    return buttonBox;
  }

  private static GridPane createInfoBox() {
    // Create a GridPane for the info fields
    GridPane infoGrid = new GridPane();
    infoGrid.setHgap(10);
    infoGrid.setVgap(10);
    infoGrid.setPadding(new Insets(20, 10, 10, 10));

    // First row: Sum of the faces & Cards of hearts
    Label sumLabel = new Label("Sum of the faces:");
    sumField = new TextField("0");
    sumField.setPrefWidth(60);
    sumField.setEditable(false);

    Label heartsLabel = new Label("Cards of hearts:");
    heartsField = new TextField("");
    heartsField.setPrefWidth(120);
    heartsField.setEditable(false);

    // Second row: Flush & Queen of spades
    Label flushLabel = new Label("Flush:");
    flushField = new TextField("No");
    flushField.setPrefWidth(60);
    flushField.setEditable(false);

    Label queenLabel = new Label("Queen of spades:");
    queenField = new TextField("No");
    queenField.setPrefWidth(60);
    queenField.setEditable(false);

    // Add components to the grid
    infoGrid.add(sumLabel, 0, 0);
    infoGrid.add(sumField, 1, 0);
    infoGrid.add(heartsLabel, 2, 0);
    infoGrid.add(heartsField, 3, 0);

    infoGrid.add(flushLabel, 0, 1);
    infoGrid.add(flushField, 1, 1);
    infoGrid.add(queenLabel, 2, 1);
    infoGrid.add(queenField, 3, 1);

    return infoGrid;
  }

  // Method to display cards in the card area
  public static void
  displayCards(Collection<PlayingCard> cards) {
    // Clear the display area except for the background
    Rectangle background = (Rectangle) cardDisplayArea.getChildren().get(0);
    cardDisplayArea.getChildren().clear();
    cardDisplayArea.getChildren().add(background);

    if (cards == null || cards.isEmpty()) {
      // No cards to display
      Text instruction = new Text("No cards to display");
      instruction.setX(background.getWidth() / 2 - 50);
      instruction.setY(background.getHeight() / 2);
      cardDisplayArea.getChildren().add(instruction);
      return;
    }

    // Position cards with some spacing
    int cardIndex = 0;
    int rowCards = 5; // Number of cards per row
    int xOffset = 20;
    int yOffset = 20;
    int xSpacing = 85;
    int ySpacing = 130;

    for (PlayingCard card : cards) {
      CardView cardView = new CardView(card);
      int row = cardIndex / rowCards;
      int col = cardIndex % rowCards;

      cardView.setTranslateX(xOffset + col * xSpacing);
      cardView.setTranslateY(yOffset + row * ySpacing);
      cardDisplayArea.getChildren().add(cardView);

      cardIndex++;
    }
  }

  // Getter for the deal hand button
  public static Button getDealHandButton() {
    return dealHandButton;
  }

  // Getter for the check hand button
  public static Button getCheckHandButton() {
    return checkHandButton;
  }

  // Update the info fields with hand statistics
  public static void updateInfo(int sum, String hearts, boolean flush, boolean queenOfSpades) {
    sumField.setText(String.valueOf(sum));
    heartsField.setText(hearts);
    flushField.setText(flush ? "Yes" : "No");
    queenField.setText(queenOfSpades ? "Yes" : "No");
  }
}