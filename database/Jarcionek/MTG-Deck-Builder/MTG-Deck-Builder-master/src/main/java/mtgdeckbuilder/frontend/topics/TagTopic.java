package mtgdeckbuilder.frontend.topics;

import java.util.HashSet;
import java.util.Set;

public class TagTopic {

    private final Set<Subscriber> subscribers = new HashSet<>();

    public static interface Subscriber {
    }

}
