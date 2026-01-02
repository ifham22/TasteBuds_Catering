package com.tastebuds.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for Feedback class
 */
class FeedbackTest {

    @Test
    @DisplayName("Feedback should be created with correct values")
    void testFeedbackCreation() {
        Feedback feedback = new Feedback("001", 5, "Excellent service!");

        assertThat(feedback.getOrderNo()).isEqualTo("001");
        assertThat(feedback.getRating()).isEqualTo(5);
        assertThat(feedback.getComment()).isEqualTo("Excellent service!");
    }

    @Test
    @DisplayName("Feedback should accept valid ratings (1-5)")
    void testValidRatings() {
        Feedback feedback = new Feedback("002", 1, "Poor");
        assertThat(feedback.getRating()).isEqualTo(1);

        feedback.setRating(3);
        assertThat(feedback.getRating()).isEqualTo(3);

        feedback.setRating(5);
        assertThat(feedback.getRating()).isEqualTo(5);
    }

    @Test
    @DisplayName("Feedback should reject rating below 1")
    void testInvalidRatingBelowRange() {
        Feedback feedback = new Feedback("003", 3, "Average");

        assertThatThrownBy(() -> feedback.setRating(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Rating must be between 1 and 5");
    }

    @Test
    @DisplayName("Feedback should reject rating above 5")
    void testInvalidRatingAboveRange() {
        Feedback feedback = new Feedback("004", 4, "Good");

        assertThatThrownBy(() -> feedback.setRating(6))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Rating must be between 1 and 5");
    }

    @Test
    @DisplayName("Feedback should allow comment updates")
    void testCommentUpdate() {
        Feedback feedback = new Feedback("005", 5, "Great!");

        assertThat(feedback.getComment()).isEqualTo("Great!");

        feedback.setComment("Absolutely amazing experience!");
        assertThat(feedback.getComment()).isEqualTo("Absolutely amazing experience!");
    }

    @Test
    @DisplayName("Feedback toString should contain all information")
    void testFeedbackToString() {
        Feedback feedback = new Feedback("006", 4, "Very satisfied");
        String result = feedback.toString();

        assertThat(result).contains("006");
        assertThat(result).contains("4");
        assertThat(result).contains("Very satisfied");
    }

    @Test
    @DisplayName("Feedback should handle empty comments")
    void testEmptyComment() {
        Feedback feedback = new Feedback("007", 3, "");
        assertThat(feedback.getComment()).isEmpty();
    }

    @Test
    @DisplayName("Feedback should handle long comments")
    void testLongComment() {
        String longComment = "This is a very long comment that describes " +
                "the entire experience in great detail including " +
                "food quality, delivery time, and customer service.";
        Feedback feedback = new Feedback("008", 5, longComment);

        assertThat(feedback.getComment()).isEqualTo(longComment);
        assertThat(feedback.getComment().length()).isGreaterThan(50);
    }
}