package nrs.impl;

import lombok.extern.slf4j.Slf4j;
import nrs.NumberRangeSummarizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
public class NumberRangeSummarizerImpl implements NumberRangeSummarizer {

    @Override
    public Collection<Integer> collect(String input) {

        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        log.info("Input string recieved {}", input);
        try {
            // Splits the input string into parts based on commas.
            String[] parts = input.split(",");
            List<Integer> numbers = new ArrayList<>();

            // Iterates through each part, trims whitespace, and parses it into an integer.
            for (String part : parts) {
                String trimmed = part.trim();
                if (!trimmed.isEmpty()) {
                    numbers.add(Integer.parseInt(trimmed));
                }
            }

            // Sort the list of integers in ascending order.
            Collections.sort(numbers);
            return numbers;

        } catch (NumberFormatException ex) {
            log.error("Input contains non-integer values: {}", input);
            throw new IllegalArgumentException("Input contains non-integer values: " + input, ex);
        }
    }

    @Override
    public String summarizeCollection(Collection<Integer> input) {

        if(input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        List<Integer> numbers = new ArrayList<>(input);
        Collections.sort(numbers);
        log.info("Sorted input numbers: {}", numbers);

        List<String> ranges = new ArrayList<>();
        int start = numbers.get(0);
        int prev = start;

        for (int i = 1; i < numbers.size(); i++) {
            int current = numbers.get(i);

            // If current number is not consecutive, finalize the current range.
            if (current != prev + 1) {
                ranges.add(formatRange(start, prev));
                //Start a new range with the current number.
                start = current;
            }

            // Update the previous number to the current one.
            prev = current;
        }

        ranges.add(formatRange(start, prev));
        return String.join(",", ranges);
    }

    private String formatRange(int firstNumber, int lastNumber) {
        if (firstNumber == lastNumber) {
            return String.valueOf(firstNumber);
        }
        //Representing the range "1-3" or "5".
        return firstNumber + "-" + lastNumber;
    }
}
