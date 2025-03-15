package edu.ntnu.idi.idatt.view;

import edu.ntnu.idi.idatt.model.PlayingCard;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class CardView extends StackPane {
  private static final double CARD_WIDTH = 80;
  private static final double CARD_HEIGHT = 120;

  private final PlayingCard card;

  public CardView(PlayingCard card) {
    this.card = card;

    // Create card background
    Rectangle background = new Rectangle(CARD_WIDTH, CARD_HEIGHT);
    background.setFill(Color.WHITE);
    background.setStroke(Color.BLACK);
    background.setArcWidth(15);
    background.setArcHeight(15);

    // Get formatted card text
    String cardText = formatCardText(card);

    // Create card text
    Text rankText = new Text(cardText);
    rankText.setFont(Font.font("Arial", FontWeight.BOLD, 18));

    // Set color based on suit
    char suit = card.getSuit();
    if (suit == 'H' || suit == 'D') {
      rankText.setFill(Color.RED);
    } else {
      rankText.setFill(Color.BLACK);
    }

    // Add suit symbol at the center
    Text suitSymbol = new Text(getSuitSymbol(suit));
    suitSymbol.setFont(Font.font("Arial", 36));
    suitSymbol.setFill(rankText.getFill());

    // Create a rank indicator for the corner
    Text rankCorner = new Text(cardText);
    rankCorner.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    rankCorner.setFill(rankText.getFill());
    rankCorner.setTranslateX(-CARD_WIDTH/2 + 10);
    rankCorner.setTranslateY(-CARD_HEIGHT/2 + 15);

    // Add all elements to the card
    getChildren().addAll(background, suitSymbol, rankCorner);
  }

  private String formatCardText(PlayingCard card) {
    int value = card.getValue();
    String valueStr;

    switch (value) {
      case 1: valueStr = "A"; break;
      case 11: valueStr = "J"; break;
      case 12: valueStr = "Q"; break;
      case 13: valueStr = "K"; break;
      default: valueStr = String.valueOf(value);
    }

    return String.valueOf(card.getSuit()) + valueStr;
  }

  private String getSuitSymbol(char suit) {
    switch (suit) {
      case 'H': return "♥";
      case 'D': return "♦";
      case 'C': return "♣";
      case 'S': return "♠";
      default: return "";
    }
  }

  public PlayingCard getCard() {
    return card;
  }
}