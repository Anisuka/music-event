package adeo.leroymerlin.cdp;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public interface HelperFunction {

    BiFunction<String, Integer, String> newTitle = (nodeTitle, childNodeCount) -> nodeTitle + " [" + childNodeCount + "]";

    static Predicate<Member> memberNameContains(String query) {
        return person -> person.getName().contains(query);
    }
}
