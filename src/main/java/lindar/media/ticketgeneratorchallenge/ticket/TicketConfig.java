package lindar.media.ticketgeneratorchallenge.ticket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TicketConfig {
    @Bean
    public TicketStripGenerator ticketStripGenerator() {
        return new TicketStripGeneratorImpl();
    }
}
