package nrs;

import nrs.impl.NumberRangeSummarizerImpl;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NumberRangeSummarizerApplicationTests {

	private final NumberRangeSummarizer summarizer = new NumberRangeSummarizerImpl();

	@Test
	void contextLoads() {
	}

	@Nested
	class CollectIntegers {

		@Test
		void collectShouldReturnSortedNumbersFromValidInput() {
			String input = "1,3,6,7,8,12,13,15,14,21,22,23,24,31";
			Collection<Integer> result = summarizer.collect(input);
			Integer[] expected = {1, 3, 6, 7, 8, 12, 13, 14, 15, 21, 22, 23, 24, 31};
			System.out.println(Arrays.toString(expected));
			assertEquals(Arrays.asList(expected), result);
		}

		@Test
		void collectShouldTrimWhitespace() {
			String input = " 1 ,  2,  3 ";
			Collection<Integer> result = summarizer.collect(input);
			Integer[] expected = {1, 2, 3};
			assertEquals(Arrays.asList(expected), result);
		}

		@Test
		void collectShouldIgnoreEmptyValues() {
			String input = "1,,2, ,3";
			Collection<Integer> result = summarizer.collect(input);
			Integer[] expected = {1, 2, 3};
			assertEquals(Arrays.asList(expected), result);
		}

		@Test
		void collectShouldThrowForNullInput() {
			assertThrows(IllegalArgumentException.class, () -> summarizer.collect(null));
		}

		@Test
		void collectShouldThrowForNonIntegerInput() {
			assertThrows(IllegalArgumentException.class, () -> summarizer.collect("1,2,a,4"));
		}
	}

	@Nested
	class SummarizeCollections {

		@Test
		void summarizeShouldReturnExpectedSummaryFromMixedRanges() {
			Collection<Integer> input = summarizer.collect("1,3,6,7,8,12,13,14,15,21,22,23,24,31");
			String result = summarizer.summarizeCollection(input);
			assertEquals("1,3,6-8,12-15,21-24,31", result);
		}

		@Test
		void summarizeShouldHandleNegativeNumbers() {
			String result = summarizer.summarizeCollection(Arrays.asList(-3, -2, -1, 0, 1));
			assertEquals("-3-1", result);
		}

		@Test
		void summarizeShouldReturnSingleNumberAsIs() {
			String result = summarizer.summarizeCollection(List.of(42));
			assertEquals("42", result);
		}

		@Test
		void summarizeShouldCollapseConsecutiveNumbersIntoRange() {
			String result = summarizer.summarizeCollection(List.of(1,2,3,4,5));
			assertEquals("1-5", result);
		}

		@Test
		void summarizeShouldReturnAllSinglesWhenNoConsecutiveNumbers() {
			String result = summarizer.summarizeCollection(List.of(1,3,5,7));
			assertEquals("1,3,5,7", result);
		}

		@Test
		void summarizeShouldHandleDuplicatesCorrectly() {
			String result = summarizer.summarizeCollection(Arrays.asList(1,1,2,2,3,3,5,5));
			assertEquals("1,1-2,2-3,3,5,5", result);
		}

		@Test
		void summarizeShouldHandleUnsortedInput() {
			String result = summarizer.summarizeCollection(Arrays.asList(8,7,6,3,1,15,14,13,12,24,23,22,21,31));
			assertEquals("1,3,6-8,12-15,21-24,31", result);
		}
	}
}
