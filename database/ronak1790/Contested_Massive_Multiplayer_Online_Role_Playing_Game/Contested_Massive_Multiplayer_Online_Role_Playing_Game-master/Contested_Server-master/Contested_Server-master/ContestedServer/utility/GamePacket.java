package utility;

/**
 * GamePacket sent by the server.
 * BigEndian conversion is done within GamePacketStream.
 *
 * Method names are mostly matched with PyDatagram
 *
 * Example:
 * 	GamePacket packet = new GamePacket(Constants.SMSG_DICE_RES);
 *      packet.addUint16((short)result);
 *      return packet.getBytes();
 *
 *      This makes internally prepares the packet to send
 *
 *      byte[0] = Lo of Packet Length = 0x04
 *      byte[1] = Hi of Packet Length = 0x00
 *      byte[2] = Lo of Constants.SMSG_DICE_RES = 0x1f
 *      byte[3] = Hi of Constants.SMSG_DICE_RES = 0x00
 *      byte[4] = Lo of Result = ?
 *      byte[5] = Hi of Result = 0x00
 */
public class GamePacket {
    private GamePacketStream buf;
}