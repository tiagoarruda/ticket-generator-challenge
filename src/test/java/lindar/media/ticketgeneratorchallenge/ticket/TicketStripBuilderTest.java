package lindar.media.ticketgeneratorchallenge.ticket;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TicketStripBuilderTest {

    @Test
    void build_noTickets() {
        assertThrows(IllegalArgumentException.class, () -> {
            new TicketStrip.TicketStripBuilder()
                    .build();
        });
    }

    @Test
    void build_missingTickets() {
        assertThrows(IllegalArgumentException.class, () -> {
            new TicketStrip.TicketStripBuilder()
                    .tickets(new Ticket[]{createTicket()})
                    .build();
        });
    }

    @Test
    void build_repeatedNumbersNotAllowed() {
        Ticket ticket = createTicket();
        assertThrows(IllegalArgumentException.class, () -> {
            new TicketStrip.TicketStripBuilder()
                    .tickets(new Ticket[]{ticket, ticket, ticket, ticket, ticket, ticket})
                    .build();
        });
    }

    @Test
    void build_noValidationTrue_allowsAnything() {
        new TicketStrip.TicketStripBuilder(true)
                .tickets(null)
                .build();
    }

    private Ticket createTicket() {
        int[][] rows = new int[3][5];
        IntStream.range(1, 6).forEach(pos -> {
            rows[0][pos - 1] = pos * 10;
            rows[1][pos - 1] = pos * 10 + 1;
            rows[2][pos - 1] =  pos * 10 + 2;
        });

        return new Ticket.TicketBuilder().rows(rows).build();
    }
}