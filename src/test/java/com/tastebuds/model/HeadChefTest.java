package com.tastebuds.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.*;


class HeadChefTest {

    @Test
    @DisplayName("HeadChef should prepare order correctly")
    void testHeadChefPrepareOrder() {
        HeadChef headChef = new HeadChef("Master Chef");
        Order order = new Order("001", "C001", "Pizza", 800.0, 800.0, 1);

        headChef.prepareOrder(order, OrderCategory.PRIORITY, "Chef A, Chef B", 20);

        assertThat(order.getStatus()).isEqualTo("PREPARING");
        assertThat(order.getCategory()).isEqualTo(OrderCategory.PRIORITY);
        assertThat(order.getAssignedChefs()).isEqualTo("Chef A, Chef B");
        assertThat(order.getEstimatedTime()).isEqualTo(20);
    }

    @Test
    @DisplayName("HeadChef should mark order ready")
    void testHeadChefMarkReady() {
        HeadChef headChef = new HeadChef("Master Chef");
        Order order = new Order("002", "C002", "Burger", 500.0, 500.0, 2);

        order.markAsPreparing(OrderCategory.NORMAL, "Chef C", 15);
        headChef.markOrderReady(order);

        assertThat(order.getStatus()).isEqualTo("READY");
    }
}