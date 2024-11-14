import EventBus.EventBus;
import EventBus.events.Arrived;
import EventBus.events.Done_P;
import EventBus.participants.*;
import EventBus.Subscriber;
import EventBus.Event;
import blackboard.Blackboard;
import blackboard.Controller;
import blackboard.Resource;
import blackboard.bases.*;
import reflection.Inspector;

import java.io.IOException;
import java.util.List;

public class Main {
    static class END implements Subscriber {
    }
}
