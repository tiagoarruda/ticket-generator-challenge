package lindar.media.ticketgeneratorchallenge.ticket;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static lindar.media.ticketgeneratorchallenge.ticket.Ticket.COLUMNS_QUANTITY;
import static lindar.media.ticketgeneratorchallenge.ticket.Ticket.ROWS_QUANTITY;
import static lindar.media.ticketgeneratorchallenge.ticket.Ticket.ROW_NUMBERS;
import static lindar.media.ticketgeneratorchallenge.ticket.Ticket.getColumn;
import static lindar.media.ticketgeneratorchallenge.ticket.TicketStrip.STRIP_SIZE;

public class TicketStripGeneratorImpl implements TicketStripGenerator {
    private final List<Integer> numbers = IntStream.range(1, 91).boxed().collect(Collectors.toList());

    @Override
    public TicketStrip generate() {
        List<Integer> shuffled = new ArrayList<>(numbers);
        Collections.shuffle(shuffled);

        return new TicketStrip.TicketStripBuilder()
                .tickets(createStrip(shuffled))
                .build();
    }

    @SneakyThrows
    private Ticket[] createStrip(List<Integer> numbers) {
        Ticket[] tickets = new Ticket[STRIP_SIZE];
        List<Map<Integer, Integer>> rows = new ArrayList<>();

        for(int i = 0; i < ROWS_QUANTITY * STRIP_SIZE; i++) {
            rows.add(new TreeMap<>());
        }

        while(!numbers.isEmpty()) {
            int number = numbers.remove(0);
            int column = getColumn(number);

            rows.stream()
                    .filter(row -> !row.keySet().contains(column) && row.size() < ROW_NUMBERS)
                    .findAny()
                    .ifPresentOrElse(row -> row.put(column, number),() -> swapNumbers(number, rows));
        }

        for (int t = 0; t < STRIP_SIZE; t++) {
            int[] row1 = rows.remove(0).values().stream().mapToInt(i -> i).toArray();
            int[] row2 = rows.remove(0).values().stream().mapToInt(i -> i).toArray();
            int[] row3 = rows.remove(0).values().stream().mapToInt(i -> i).toArray();

            tickets[t] = new Ticket.TicketBuilder()
                    .rows(new int[][]{row1, row2, row3})
                    .build();
        }

        return tickets;
    }

    private void swapNumbers(int number, List<Map<Integer, Integer>> rows) {
        int column = getColumn(number);

        Map<Integer, Integer> row = rows.stream()
                .filter(r -> r.size() < ROW_NUMBERS)
                .findAny()
                .get();

        int missingColumn = getMissingColumns(row).get(0);

        Map<Integer, Integer> row_2 = rows.stream()
                .filter(r -> r.size() == ROW_NUMBERS && r.keySet().contains(missingColumn) && !r.keySet().contains(column))
                .findAny()
                .get();

        row.put(missingColumn, row_2.remove(missingColumn));
        row_2.put(column, number);
    }

    private List<Integer> getMissingColumns(Map<Integer, Integer> row) {
        List<Integer> missingColumns = new ArrayList<>();
        for(int column = 0; column < COLUMNS_QUANTITY; column++) {
            if (!row.keySet().contains(column)) {
                missingColumns.add(column);
            }
        }
        return missingColumns;
    }

    @Override
    public List<TicketStrip> batch(int size) {
        List<TicketStrip> strips = new ArrayList<>();
        while(strips.size() != size) {
            strips.add(generate());
        }

        return strips;
    }
}
