package edu.ntnu.idi.idatt.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DeckOfCardsTest {

  private DeckOfCards deck;

  @BeforeEach
  public void setUp() {
    deck = new DeckOfCards();
  }

  @Test
  public void testDeckInitialization() {
    Collection<PlayingCard> allCards = deck.dealHand(52);
    assertEquals(52, allCards.size(), "Deck should contain 52 cards");

    // Check that we have correct distribution of suits and values
    int[] suitCount = new int[4]; // H, S, D, C
    int[] valueCount = new int[13]; // 1-13

    for (PlayingCard card : allCards) {
      char suit = card.getSuit();
      int value = card.getValue();

      switch (suit) {
        case 'H': suitCount[0]++; break;
        case 'S': suitCount[1]++; break;
        case 'D': suitCount[2]++; break;
        case 'C': suitCount[3]++; break;
      }

      valueCount[value-1]++;
    }

    // Check suit distribution
    for (int count : suitCount) {
      assertEquals(13, count, "Each suit should have 13 cards");
    }

    // Check value distribution
    for (int count : valueCount) {
      assertEquals(4, count, "Each value should appear 4 times (once per suit)");
    }
  }

  @Test
  public void testDealHand() {
    int handSize = 5;
    Collection<PlayingCard> hand = deck.dealHand(handSize);

    assertEquals(handSize, hand.size(), "Hand should contain exactly 5 cards");

    // Check that all cards are unique
    Set<String> uniqueCards = new HashSet<>();
    for (PlayingCard card : hand) {
      String cardIdentifier = card.getSuit() + String.valueOf(card.getValue());
      uniqueCards.add(cardIdentifier);
    }

    assertEquals(handSize, uniqueCards.size(), "All cards in hand should be unique");
  }

  @Test
  public void testDealHandWithDifferentSizes() {
    // Test with small hand
    Collection<PlayingCard> smallHand = deck.dealHand(1);
    assertEquals(1, smallHand.size(), "Hand should contain exactly 1 card");

    // Test with full deck
    Collection<PlayingCard> fullDeck = deck.dealHand(52);
    assertEquals(52, fullDeck.size(), "Hand should contain exactly 52 cards");
  }

  @Test
  public void testDealHandWithRandomness() {
    // Deal two hands and ensure they're different
    Collection<PlayingCard> hand1 = deck.dealHand(5);
    Collection<PlayingCard> hand2 = deck.dealHand(5);

    // Convert to strings for easier comparison
    Set<String> hand1Str = new HashSet<>();
    Set<String> hand2Str = new HashSet<>();

    for (PlayingCard card : hand1) {
      hand1Str.add(card.getSuit() + String.valueOf(card.getValue()));
    }

    for (PlayingCard card : hand2) {
      hand2Str.add(card.getSuit() + String.valueOf(card.getValue()));
    }

    // Find intersection between hands
    Set<String> commonCards = new HashSet<>(hand1Str);
    commonCards.retainAll(hand2Str);

    // It's statistically possible but unlikely that two random 5-card hands would be identical
    // If this test fails occasionally, it's just bad luck
    assertTrue(commonCards.size() < 5, "Two random hands should be different");
  }

  @Test
  public void testDealHandWithInvalidSize() {
    assertThrows(IllegalArgumentException.class, () -> deck.dealHand(53),
        "Dealing more cards than the deck size should throw IllegalArgumentException");
  }
}