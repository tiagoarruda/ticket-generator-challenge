package lindar.media.ticketgeneratorchallenge;

import lindar.media.ticketgeneratorchallenge.ticket.TicketStripGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.time.Instant;

@SpringBootApplication
@Slf4j
public class TicketGeneratorChallengeApplication implements CommandLineRunner {

	@Value("${batch-size:#{null}}")
	private Integer batch;

	private final TicketStripGenerator generator;

	public TicketGeneratorChallengeApplication(TicketStripGenerator generator) {
		this.generator = generator;
	}

	public static void main(String[] args) {
		SpringApplication.run(TicketGeneratorChallengeApplication.class, args);
	}

	@Override
	public void run(String... args) {
		log.info("Starting ticket generation");
		if (batch != null && batch >= 0) {
			log.info(String.format("Batch of %s ticket strips starting", batch));

			Instant one = Instant.now();
			generator.batch(batch);
			Instant two = Instant.now();
			Duration res = Duration.between(one, two);
			System.out.println(String.format("Duration: %s", res));
		} else {
			Instant one = Instant.now();
			generator.generate();
			Instant two = Instant.now();
			Duration res = Duration.between(one, two);
			System.out.println(String.format("Duration: %s", res));
		}
	}
}
