package agent.test;

import agent.MASLABAbstractAgent;
import agent.MASLABAbstractAgent;
import java.util.Collection;
import java.util.EnumSet;

import rescuecore2.worldmodel.ChangeSet;
import rescuecore2.worldmodel.Entity;
import rescuecore2.worldmodel.EntityID;
import rescuecore2.messages.Command;
import rescuecore2.registry.Registry;
import rescuecore2.registry.EntityFactory;

import rescuecore2.standard.entities.StandardEntityURN;
import rescuecore2.standard.entities.StandardEntityFactory;
import rescuecore2.standard.entities.Human;

import rescuecore2.log.Logger;

/**
 * An agent for testing custom registry objects.
 */
public class CustomRegistryTestAgent extends MASLABAbstractAgent<Human> {
	
	private static class CustomEntityFactory implements EntityFactory {
		private StandardEntityFactory downstream = StandardEntityFactory.INSTANCE;
	}
}
