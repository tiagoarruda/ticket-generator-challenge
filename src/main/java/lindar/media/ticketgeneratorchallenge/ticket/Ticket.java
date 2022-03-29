package lindar.media.ticketgeneratorchallenge.ticket;

import lombok.Getter;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Ticket {
    public static final int ROWS_QUANTITY = 3;
    public static final int ROW_NUMBERS = 5;
    private static final int ROW_BLANKS = 4;
    public static final int COLUMNS_QUANTITY = 9;
    private static final int NUMBERS_QUANTITY = 15;
    private static final int NUMBERS_LOW_BOUNDARY = 1;
    private static final int NUMBERS_HIGH_BOUNDARY = 90;

    private final int[][] rows;

    private Ticket() {
        throw new RuntimeException("This constructor is not supposed to be used.");
    }

    private Ticket(int[][] rows) {
        this.rows = rows;
    }

    public static class TicketBuilder {
        private int[][] rows;
        private boolean noValidation = false;

        public TicketBuilder() {}

        public TicketBuilder(boolean noValidation) {
            this.noValidation = noValidation;
        }

        public TicketBuilder rows(int[][] rows) {
            this.rows = rows;
            return this;
        }

        public Ticket build() {
            validate();
            return new Ticket(rows);
        }

        private void validate() {
            if (!noValidation) {
                Assert.notNull(this.rows, "Can't create a ticket without numbers.");
                validateRows();
                validateNumbers();
            }
        }

        private void validateNumbers() {
            List<Integer> numbers = new ArrayList<>();
            int[] columnCount = new int[COLUMNS_QUANTITY];
            for(int i = 0; i < ROW_NUMBERS; i++) {
                validateNumber(numbers, columnCount, rows[0][i]);
                validateNumber(numbers, columnCount, rows[1][i]);
                validateNumber(numbers, columnCount, rows[2][i]);
            }
        }

        private void validateNumber(List<Integer> numbers, int[] columnCount, int row1_number1) {
            int row1_number = row1_number1;
            Assert.isTrue(row1_number >= NUMBERS_LOW_BOUNDARY && row1_number <= NUMBERS_HIGH_BOUNDARY,
                    String.format("Invalid number %s on row 1", row1_number));
            Assert.isTrue(!numbers.contains(row1_number),
                    String.format("Repeated number %s on row 1", row1_number));
            numbers.add(row1_number);

            int row1_number_column = getColumn(row1_number);
            columnCount[row1_number_column] = columnCount[row1_number_column] + 1;
            Assert.isTrue(columnCount[row1_number_column] <= ROWS_QUANTITY,
                    String.format("Each ticket cannot contain more than %s rows", ROWS_QUANTITY));
        }

        private void validateRows() {
            Assert.isTrue(this.rows.length == ROWS_QUANTITY,
                    String.format("Each ticket must contain %s rows. See current numbers distribution bellow: %s", ROWS_QUANTITY, this.toString()));

            for(int[] row: rows) {
                Assert.isTrue(row.length == ROW_NUMBERS,
                        String.format("Each ticket row must contain %s rows. See current numbers distribution bellow: %s", ROW_NUMBERS, this.toString()));
            }
        }
    }

    public static int getColumn(int row1_number) {
        return row1_number == NUMBERS_HIGH_BOUNDARY ? (row1_number - 1) / 10 : row1_number / 10;
    }
}
