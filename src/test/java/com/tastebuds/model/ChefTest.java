
package com.tastebuds.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.*;


class ChefTest {

    @Test
    @DisplayName("Chef should be created with correct initial values")
    void testChefCreation() {
        Chef chef = new Chef("Chef Roni");

        assertThat(chef.getName()).isEqualTo("Chef Roni");
        assertThat(chef.isAvailable()).isTrue();
    }

    @Test
    @DisplayName("Chef availability should be changeable")
    void testChefAvailability() {
        Chef chef = new Chef("Chef Sara");

        assertThat(chef.isAvailable()).isTrue();

        chef.setAvailable(false);
        assertThat(chef.isAvailable()).isFalse();

        chef.setAvailable(true);
        assertThat(chef.isAvailable()).isTrue();
    }

    @Test
    @DisplayName("Chef toString should work correctly")
    void testChefToString() {
        Chef chef = new Chef("Chef Ahmed");
        String result = chef.toString();

        assertThat(result).contains("Chef Ahmed");
        assertThat(result).contains("true");
    }
}
