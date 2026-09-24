package com.rit.devops;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class QuoteControllerTest {

    private final QuoteController controller = new QuoteController();

    @Test
    void healthReturnsOk() {
        assertEquals("OK", controller.health());
    }

    @Test
    void quoteIsNeverEmpty() {
        String quote = controller.quote();
        assertNotNull(quote);
        assertFalse(quote.isBlank());
    }
}
