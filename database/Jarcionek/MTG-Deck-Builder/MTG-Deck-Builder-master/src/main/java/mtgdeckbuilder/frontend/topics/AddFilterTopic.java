package mtgdeckbuilder.frontend.topics;

import mtgdeckbuilder.data.Filter;

import java.util.HashSet;
import java.util.Set;

public class AddFilterTopic {

    private final Set<Subscriber> subscribers = new HashSet<>();

    public static interface Subscriber {
    }

}
