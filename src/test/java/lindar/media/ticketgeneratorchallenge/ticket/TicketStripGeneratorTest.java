package lindar.media.ticketgeneratorchallenge.ticket;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;

@SpringBootTest
public class TicketStripGeneratorTest {

    @Autowired
    TicketStripGenerator testObj;

    @Test
    void generate_success() {
        TicketStrip strip = testObj.generate();
        assertThat(strip).isNotNull();
        assertThat(validateRepeatedNumbers(strip.getTickets()));
    }

    @Test
    void batch_success() {
        List<TicketStrip> strips = testObj.batch(2);
        for (TicketStrip strip: strips) {
            assertThat(strip).isNotNull();
            assertThat(validateRepeatedNumbers(strip.getTickets()));
        }
    }

    private boolean validateRepeatedNumbers(Ticket[] tickets) {
        //The ticket already check for duplicates, we don't need to check inside the first one
        Set<Integer> numbers = new HashSet<>();
        for(int[] row: tickets[0].getRows()) {
            numbers.addAll(Arrays.stream(row).boxed().collect(Collectors.toSet()));
        }
        //6 tickets
        for(int i = 1; i < tickets.length; i++) {
            for(int[] row: tickets[i].getRows()) {
                //5 numbers per row
                for(int number: row) {
                    if (!numbers.add(number)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
