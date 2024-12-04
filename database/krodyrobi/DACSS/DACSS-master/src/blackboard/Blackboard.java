package blackboard;

import java.util.ArrayList;
import java.util.List;

public class Blackboard {
    private static Blackboard ourInstance = new Blackboard();
    private Controller controller;
    private List<Resource> res;
}
