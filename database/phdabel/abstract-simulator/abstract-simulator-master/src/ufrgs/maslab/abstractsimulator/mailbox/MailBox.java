package ufrgs.maslab.abstractsimulator.mailbox;

import java.util.ArrayList;

import ufrgs.maslab.abstractsimulator.mailbox.message.Message;

public class MailBox {
	
	/**
	 * <ul>
	 * <li>IDs of the messages in the inbox of the agent</li>
	 * </ul>
	 */
	private ArrayList<Message> InBox = new ArrayList<Message>();

	/**
	 * <ul>
	 * <li>IDs of the messages in the sent box of the agent</li>
	 * </ul>
	 */
	private ArrayList<Message> Sent = new ArrayList<Message>();
	
	/**
	 * <ul>
	 * <li>IDs of the messages in the trash of the agent</li>
	 * </ul>
	 */
	private ArrayList<Message> Trash = new ArrayList<Message>();

	/**
	 * <ul>
	 * <li>IDs of the outgoing messages</li>
	 * </ul>
	 */
	private ArrayList<Message> Outgoing = new ArrayList<Message>();

	
	
}
