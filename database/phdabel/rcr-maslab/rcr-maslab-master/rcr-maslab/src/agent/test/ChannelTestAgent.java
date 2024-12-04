package agent.test;

import agent.MASLABAbstractAgent;
import agent.MASLABAbstractAgent;
import java.util.Collection;
import java.util.EnumSet;

import rescuecore2.worldmodel.ChangeSet;
import rescuecore2.messages.Command;

import rescuecore2.standard.entities.StandardEntityURN;
import rescuecore2.standard.entities.Human;
import rescuecore2.standard.entities.FireBrigade;
import rescuecore2.standard.messages.AKSpeak;

import rescuecore2.log.Logger;

/**
 * An agent for testing communication channels.
 */
public class ChannelTestAgent extends MASLABAbstractAgent<Human> {
	private static final int CHANNEL = 4;
	private static final int N = 100;
}
