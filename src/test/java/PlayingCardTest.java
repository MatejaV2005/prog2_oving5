package edu.ntnu.idi.idatt.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayingCardTest {

  @Test
  public void testConstructor() {
    PlayingCard card = new PlayingCard(1, 'H');
    assertNotNull(card, "PlayingCard should be created successfully");
  }

  @Test
  public void testGetValue() {
    PlayingCard card = new PlayingCard(10, 'D');
    assertEquals(10, card.getValue(), "getValue should return the correct value");
  }

  @Test
  public void testGetSuit() {
    PlayingCard card = new PlayingCard(12, 'S');
    assertEquals('S', card.getSuit(), "getSuit should return the correct suit");
  }

  @Test
  public void testBoundaryValues() {
    // Test lowest card value
    PlayingCard aceCard = new PlayingCard(1, 'C');
    assertEquals(1, aceCard.getValue(), "Ace should have value 1");

    // Test highest card value
    PlayingCard kingCard = new PlayingCard(13, 'H');
    assertEquals(13, kingCard.getValue(), "King should have value 13");
  }

  @Test
  public void testAllSuits() {
    PlayingCard heartCard = new PlayingCard(7, 'H');
    assertEquals('H', heartCard.getSuit(), "Heart suit should be 'H'");

    PlayingCard spadeCard = new PlayingCard(7, 'S');
    assertEquals('S', spadeCard.getSuit(), "Spade suit should be 'S'");

    PlayingCard diamondCard = new PlayingCard(7, 'D');
    assertEquals('D', diamondCard.getSuit(), "Diamond suit should be 'D'");

    PlayingCard clubCard = new PlayingCard(7, 'C');
    assertEquals('C', clubCard.getSuit(), "Club suit should be 'C'");
  }
}