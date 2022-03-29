package lindar.media.ticketgeneratorchallenge.ticket;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TicketBuilderTest {

    @Test
    void build_missingAllNumbers() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Ticket.TicketBuilder()
                    .build();
        });
    }

    @Test
    void build_missingRows() {
        int[][] rows = new int[][]{{1, 11, 21, 31, 41}};
        assertThrows(IllegalArgumentException.class, () -> {
            new Ticket.TicketBuilder()
                    .rows(rows)
                    .build();
        });
    }

    @Test
    void build_missingNumbersInARow() {
        int[][] rows = new int[][]{{1, 11, 21, 31, 41}, {2, 12, 22, 32, 42}, {3, 13, 23, 33}};
        assertThrows(IllegalArgumentException.class, () -> {
            new Ticket.TicketBuilder()
                    .rows(rows)
                    .build();
        });
    }

    @Test
    void build_invalidNumbersInARow() {
        int[][] rows_1 = new int[][]{{1, 11, 21, 31, 41}, {2, 12, 22, 32, 42}, {3, 13, 23, 33, 100}};
        int[][] rows_2 = new int[][]{{-1, 11, 21, 31, 41}, {2, 12, 22, 32, 42}, {3, 13, 23, 33, 43}};
        assertThrows(IllegalArgumentException.class, () -> {
            new Ticket.TicketBuilder()
                    .rows(rows_1)
                    .build();
        });


        assertThrows(IllegalArgumentException.class, () -> {
            new Ticket.TicketBuilder()
                    .rows(rows_2)
                    .build();
        });
    }

    @Test
    void build_repeatedNumbers() {
        int[][] rows = new int[][]{{1, 1, 1, 1, 1}, {2, 12, 22, 32, 42}, {3, 13, 23, 33, 43}};

        assertThrows(IllegalArgumentException.class, () -> {
            new Ticket.TicketBuilder()
                    .rows(rows)
                    .build();
        });
    }

    @Test
    void build_invalidNumberOfNumbersInAColumn() {
        int[][] rows = new int[][]{{1, 11, 21, 31, 41}, {2, 12, 22, 32, 42}, {3, 4, 23, 33, 43}};

        assertThrows(IllegalArgumentException.class, () -> {
            new Ticket.TicketBuilder()
                    .rows(rows)
                    .build();
        });
    }

    @Test
    void build_invalidNumberOfNumbersInAColumnWithHighBoundary() {
        int[][] rows = new int[][]{{1, 87, 88, 89, 90}, {2, 12, 22, 32, 42}, {3, 13, 23, 33, 43}};

        assertThrows(IllegalArgumentException.class, () -> {
            new Ticket.TicketBuilder()
                    .rows(rows)
                    .build();
        });
    }

    @Test
    void build_validNumberOfNumbersInAColumnWithTenMultipleBoundary() {
        int[][] rows = new int[][]{{1, 19, 20, 79, 90}, {2, 12, 22, 32, 42}, {3, 13, 23, 33, 43}};

        Ticket ticket = new Ticket.TicketBuilder()
                .rows(rows)
                .build();
        assertEquals(rows, ticket.getRows());
    }

    @Test
    void build_noValidationTrue_allowAnything() {
        int[][] rows = new int[][]{};

        Ticket ticket = new Ticket.TicketBuilder(true)
                .rows(rows)
                .build();
        assertEquals(rows, ticket.getRows());
    }
}