package reflection;

import blackboard.Blackboard;
import blackboard.KnowledgeBase;
import blackboard.Resource;

import java.util.ArrayList;
import java.util.List;

public class TestLoader implements Cloneable {
    private Blackboard blackboard;
    private List<KnowledgeBase> kbs;
    public String test = "10";

    class Reservation {
        KnowledgeBase kb;
        Resource resource;
    }
}
