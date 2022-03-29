package lindar.media.ticketgeneratorchallenge.ticket;

import java.util.List;

public interface TicketStripGenerator {
    TicketStrip generate();
    List<TicketStrip> batch(int size);
}
