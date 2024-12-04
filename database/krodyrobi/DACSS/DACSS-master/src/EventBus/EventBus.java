package EventBus;

import java.util.ArrayList;
import java.util.List;

public class EventBus {
    private static EventBus instance;
    private List<EventBundle> eventBundles;


    class EventBundle {
        Event event;
        Subscriber subscriber;
    }
}
