package nrs;

import nrs.impl.NumberRangeSummarizerImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collection;

@SpringBootApplication
public class NumberRangeSummarizerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(NumberRangeSummarizerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		NumberRangeSummarizer summarizer = new NumberRangeSummarizerImpl();

		String input = "1,3,6,7,8,12,13,14,15,21,22,23,24,31";
		Collection<Integer> collected = summarizer.collect(input);
		String result = summarizer.summarizeCollection(collected);

		System.out.println("Input: " + input);
		System.out.println("Output: " + result);
	}
}
