package nrs;

import java.util.Collection;

public interface NumberRangeSummarizer {

    Collection<Integer> collect(String input);

    //get the summarized string
    String summarizeCollection(Collection<Integer> input);
}
