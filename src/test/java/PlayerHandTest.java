

import edu.ntnu.idi.idatt.model.PlayerHand;
import edu.ntnu.idi.idatt.model.PlayingCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerHandTest {

  private PlayerHand playerHand;

  @BeforeEach
  public void setUp() {
    playerHand = new PlayerHand();
  }

  @Test
  public void testAddCard() {
    PlayingCard card = new PlayingCard(10, 'H');
    playerHand.addCard(card);

    List<PlayingCard> hand = playerHand.getHand();
    assertEquals(1, hand.size(), "Hand should contain one card");
    assertEquals(card, hand.get(0), "Card in hand should be the same as added");
  }

  @Test
  public void testClearHand() {
    // Add some cards
    playerHand.addCard(new PlayingCard(10, 'H'));
    playerHand.addCard(new PlayingCard(2, 'S'));

    // Clear hand
    playerHand.clearHand();

    // Check if hand is empty
    List<PlayingCard> hand = playerHand.getHand();
    assertTrue(hand.isEmpty(), "Hand should be empty after clear");
  }

  @Test
  public void testSumOfAllFaces() {
    playerHand.addCard(new PlayingCard(10, 'H'));
    playerHand.addCard(new PlayingCard(5, 'S'));
    playerHand.addCard(new PlayingCard(13, 'D'));

    int sum = playerHand.sumOfAllFaces();
    assertEquals(28, sum, "Sum should be calculated correctly");
  }

  @Test
  public void testOnlyCardsOfHearts() {
    // Add cards of different suits
    playerHand.addCard(new PlayingCard(10, 'H'));
    playerHand.addCard(new PlayingCard(5, 'S'));
    playerHand.addCard(new PlayingCard(2, 'H'));
    playerHand.addCard(new PlayingCard(13, 'D'));
    playerHand.addCard(new PlayingCard(8, 'H'));

    List<PlayingCard> heartCards = playerHand.onlyCardsOfHearts();

    assertEquals(3, heartCards.size(), "Should find 3 hearts cards");
    for (PlayingCard card : heartCards) {
      assertEquals('H', card.getSuit(), "All cards should be hearts");
    }
  }

  @Test
  public void testQueenOfSpadesInHand_Present() {
    // Add various cards including queen of spades
    playerHand.addCard(new PlayingCard(10, 'H'));
    playerHand.addCard(new PlayingCard(12, 'S')); // Queen of Spades
    playerHand.addCard(new PlayingCard(2, 'D'));

    assertTrue(playerHand.queenOfSpadesInHand(), "Should detect Queen of Spades");
  }

  @Test
  public void testQueenOfSpadesInHand_NotPresent() {
    // Add various cards excluding queen of spades
    playerHand.addCard(new PlayingCard(10, 'H'));
    playerHand.addCard(new PlayingCard(11, 'S')); // Jack of Spades, not Queen
    playerHand.addCard(new PlayingCard(12, 'H')); // Queen of Hearts, not Spades

    assertFalse(playerHand.queenOfSpadesInHand(), "Should not detect Queen of Spades");
  }

  @Test
  public void testCheckFlush_HasFlush() {
    // Add 5 hearts (should be a flush)
    playerHand.addCard(new PlayingCard(2, 'H'));
    playerHand.addCard(new PlayingCard(5, 'H'));
    playerHand.addCard(new PlayingCard(8, 'H'));
    playerHand.addCard(new PlayingCard(10, 'H'));
    playerHand.addCard(new PlayingCard(13, 'H'));

    // Add some other cards
    playerHand.addCard(new PlayingCard(2, 'S'));
    playerHand.addCard(new PlayingCard(3, 'D'));

    List<List<PlayingCard>> flushes = playerHand.checkFlush();
    assertEquals(1, flushes.size(), "Should detect one flush");
    assertEquals(5, flushes.get(0).size(), "Flush should have 5 cards");

    // Check all cards in flush are hearts
    for (PlayingCard card : flushes.get(0)) {
      assertEquals('H', card.getSuit(), "All cards in flush should be hearts");
    }

    // Check the flush cards are sorted by value (descending)
    List<PlayingCard> flush = flushes.get(0);
    assertEquals(13, flush.get(0).getValue(), "First card should be highest value");
    assertEquals(10, flush.get(1).getValue(), "Second card should be second highest value");
  }

  @Test
  public void testCheckFlush_NoFlush() {
    // Add cards of different suits (no 5 of the same suit)
    playerHand.addCard(new PlayingCard(2, 'H'));
    playerHand.addCard(new PlayingCard(5, 'H'));
    playerHand.addCard(new PlayingCard(8, 'S'));
    playerHand.addCard(new PlayingCard(10, 'H'));
    playerHand.addCard(new PlayingCard(13, 'H'));
    playerHand.addCard(new PlayingCard(2, 'S'));
    playerHand.addCard(new PlayingCard(3, 'D'));

    List<List<PlayingCard>> flushes = playerHand.checkFlush();
    assertTrue(flushes.isEmpty(), "Should not detect a flush");
  }

  @Test
  public void testCheckFlush_MultipleFlushes() {
    // Add 5 hearts
    playerHand.addCard(new PlayingCard(2, 'H'));
    playerHand.addCard(new PlayingCard(5, 'H'));
    playerHand.addCard(new PlayingCard(8, 'H'));
    playerHand.addCard(new PlayingCard(10, 'H'));
    playerHand.addCard(new PlayingCard(13, 'H'));

    // Add 5 spades
    playerHand.addCard(new PlayingCard(1, 'S'));
    playerHand.addCard(new PlayingCard(4, 'S'));
    playerHand.addCard(new PlayingCard(7, 'S'));
    playerHand.addCard(new PlayingCard(9, 'S'));
    playerHand.addCard(new PlayingCard(12, 'S'));

    List<List<PlayingCard>> flushes = playerHand.checkFlush();
    assertEquals(2, flushes.size(), "Should detect two flushes");
  }

  @Test
  public void testPropertyChangeListenerForHandChanged() {
    final AtomicBoolean listenerCalled = new AtomicBoolean(false);
    final List<PlayingCard> notifiedCards = new ArrayList<>();

    PropertyChangeListener listener = evt -> {
      if ("handChanged".equals(evt.getPropertyName())) {
        listenerCalled.set(true);
        @SuppressWarnings("unchecked")
        List<PlayingCard> cards = (List<PlayingCard>) evt.getNewValue();
        notifiedCards.addAll(cards);
      }
    };

    playerHand.addPropertyChangeListener(listener);

    // Add cards
    PlayingCard card1 = new PlayingCard(10, 'H');
    PlayingCard card2 = new PlayingCard(5, 'S');
    playerHand.addCard(card1);
    playerHand.addCard(card2);

    // Fire notification
    playerHand.notifyHandChanged();

    assertTrue(listenerCalled.get(), "Listener should be called");
    assertEquals(2, notifiedCards.size(), "Notified cards should match hand size");
    assertTrue(notifiedCards.contains(card1) && notifiedCards.contains(card2),
        "Notified cards should match added cards");
  }

  @Test
  public void testPropertyChangeListenerForHandAnalysis() {
    final AtomicBoolean listenerCalled = new AtomicBoolean(false);
    final AtomicInteger sumValue = new AtomicInteger(0);
    final AtomicBoolean flushValue = new AtomicBoolean(false);
    final String[] heartsValue = new String[1];
    final AtomicBoolean queenOfSpadesValue = new AtomicBoolean(false);

    PropertyChangeListener listener = evt -> {
      if ("handAnalysis".equals(evt.getPropertyName())) {
        listenerCalled.set(true);
        Object[] values = (Object[]) evt.getNewValue();
        sumValue.set((Integer) values[0]);
        heartsValue[0] = (String) values[1];
        flushValue.set((Boolean) values[2]);
        queenOfSpadesValue.set((Boolean) values[3]);
      }
    };

    playerHand.addPropertyChangeListener(listener);

    // Add cards that would trigger interesting analysis results
    playerHand.addCard(new PlayingCard(10, 'H')); // Heart, value 10
    playerHand.addCard(new PlayingCard(12, 'S')); // Queen of Spades
    playerHand.addCard(new PlayingCard(5, 'H')); // Heart, value 5

    // Calculate expected values
    int expectedSum = 27; // 10 + 12 + 5
    String expectedHearts = "10H, 5H"; // Not sure of exact format, modify as needed
    boolean expectedFlush = false; // Not enough cards for a flush
    boolean expectedQueenOfSpades = true;

    // Fire notification with these values
    playerHand.notifyHandChecked(expectedSum, expectedHearts, expectedFlush, expectedQueenOfSpades);

    // Verify listener was called and values were passed correctly
    assertTrue(listenerCalled.get(), "Listener should be called");
    assertEquals(expectedSum, sumValue.get(), "Sum value should match");
    assertEquals(expectedHearts, heartsValue[0], "Hearts value should match");
    assertEquals(expectedFlush, flushValue.get(), "Flush value should match");
    assertEquals(expectedQueenOfSpades, queenOfSpadesValue.get(),
        "Queen of Spades value should match");
  }
}