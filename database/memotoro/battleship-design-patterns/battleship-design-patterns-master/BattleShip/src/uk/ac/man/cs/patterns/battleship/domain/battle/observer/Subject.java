package uk.ac.man.cs.patterns.battleship.domain.battle.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Observer Pattern. Abstract class that represent the subject in the observer pattern.
 * @author Guillermo Antonio Toro Bayona
 */
public abstract class Subject {

    /**
     * List of observers
     */
    private List<Observer> observers;
}
