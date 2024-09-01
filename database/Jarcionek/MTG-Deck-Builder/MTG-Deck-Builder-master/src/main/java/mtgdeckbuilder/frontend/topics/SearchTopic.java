package mtgdeckbuilder.frontend.topics;

import java.util.HashSet;
import java.util.Set;

public class SearchTopic {

    private final Set<Subscriber> subscribers = new HashSet<>();

    public static interface Subscriber {
    }

}
