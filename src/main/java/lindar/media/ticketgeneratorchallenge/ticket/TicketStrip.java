package lindar.media.ticketgeneratorchallenge.ticket;

import lombok.Getter;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class TicketStrip {
    public static final int STRIP_SIZE = 6;
    private final Ticket[] tickets;

    private TicketStrip() {
        throw new RuntimeException("This constructor is not supported.");
    }

    private TicketStrip(Ticket[] tickets) {
        this.tickets = tickets;
    }

    public static class TicketStripBuilder {
        private Ticket[] tickets;

        public TicketStrip build() {
            validateStripSize();
            validateRepeatedNumbers();
            return new TicketStrip(tickets);
        }

        public TicketStripBuilder tickets(Ticket[] tickets) {
            this.tickets = tickets;
            return this;
        }

        private void validateStripSize() {
            Assert.notNull(this.tickets, "Can't create a ticket strip without tickets.");
            Assert.isTrue(this.tickets.length == STRIP_SIZE, "Can't create a ticket strip without tickets.");
        }

        private void validateRepeatedNumbers() {
            //The ticket already check for duplicates, we don't need to check inside the first one
            Set<Integer> numbers = new HashSet<>();
            for(int[] row: tickets[0].getRows()) {
                numbers.addAll(Arrays.stream(row).boxed().collect(Collectors.toSet()));
            }
            //5 tickets
            for(int i = 1; i < tickets.length; i++) {
                for(int[] row: tickets[i].getRows()) {
                    //5 numbers per row
                    Arrays.stream(row).forEach(number -> {
                        Assert.isTrue(numbers.add(number),
                                "Tickets in the same strip cannot have duplicate numbers");
                    });
                }
            }
        }
    }
}
