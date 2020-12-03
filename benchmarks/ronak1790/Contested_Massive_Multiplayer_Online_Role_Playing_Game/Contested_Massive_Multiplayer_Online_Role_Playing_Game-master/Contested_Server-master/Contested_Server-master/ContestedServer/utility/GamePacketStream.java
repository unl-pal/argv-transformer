package utility;

// Java Imports
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.text.SimpleDateFormat;

/**
 * The GamePacketStream contain methods that pushes the response information as
 * bytes into the output stream. Data is then sent to the client.
 */
/** filtered by PAClab */
 public class GamePacketStream extends ByteArrayOutputStream {

    public byte getChecksum() {
        byte[] bytes = super.toByteArray();
        Integer checksum = 0, sum = 0;

        for (int i = 0; i < bytes.length; i++) {
            sum += bytes[i];
        }

        // Take the least significant byte of sum, and take the two's complement
        checksum = -(sum & 0xFF);

        if (((sum + checksum) & 0xFF) == 0x00) {
            return checksum.byteValue();
        } else {
            return 0;
        }
    }
}
