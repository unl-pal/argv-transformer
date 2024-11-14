/*
 The MIT License (MIT)
 
 Copyright (c) 2013, 2014 by ggbusto@gmx.com

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

package org.noo4j.daemon;

import static org.junit.Assert.*;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import org.noo4j.core.BtcAccount;
import org.noo4j.core.BtcAddedNode;
import org.noo4j.core.BtcAddress;
import org.noo4j.core.BtcBlock;
import org.noo4j.core.BtcBlockSubmission;
import org.noo4j.core.BtcBlockTemplate;
import org.noo4j.core.BtcCoinbase;
import org.noo4j.core.BtcException;
import org.noo4j.core.BtcLastBlock;
import org.noo4j.core.BtcMiningInfo;
import org.noo4j.core.BtcMultiSignatureAddress;
import org.noo4j.core.BtcNode;
import org.noo4j.core.BtcPeer;
import org.noo4j.core.BtcInfo;
import org.noo4j.core.BtcRawTransaction;
import org.noo4j.core.BtcScript;
import org.noo4j.core.BtcTransaction;
import org.noo4j.core.BtcTransactionDetail;
import org.noo4j.core.BtcOutput;
import org.noo4j.core.BtcOutputSet;
import org.noo4j.core.BtcOutputPart;
import org.noo4j.core.BtcTransactionTemplate;
import org.noo4j.core.BtcWork;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class BtcDaemonTest {
	private static final boolean BITCOIND_STOP = false;
	private static final boolean BITCOIND_SSL = true;
	private static final String BITCOIND_HOST = "127.0.0.1";
	private static final String BITCOIND_URL = "http://" + BITCOIND_HOST
			+ ":18332";
	private static final String BITCOIND_URL_SSL = "https://" + BITCOIND_HOST
			+ ":18332";
	private static final String BITCOIND_ACCOUNT = "user";
	private static final String BITCOIND_ACCOUNT_DEFAULT = "";
	private static final String BITCOIND_PASSWORD = "password";
	private static final int BITCOIND_TIMEOUT = 10000;
	private static final int BITCOIND_NOTIFICATION_SLEEP = 30000;
	private static final int BITCOIND_WALLET_TIMEOUT = 300;
	private static final int BITCOIND_ALERT_PORT = 18334;
	private static final int BITCOIND_BLOCK_PORT = 18335;
	private static final int BITCOIND_WALLET_PORT = 18336;
	private static final String BITCOIND_DIR = "E:/bitcoin/bitcoind-0.8.6";
	private static final String BITCOIND_WALLET = "wallet.dat";
	private static final String BITCOIND_ADDRESS_1 = "mteUu5qrZJAjybLJwVQpxxmpnyGFUhPYQD";
	private static final String BITCOIND_ADDRESS_2 = "mhkM5pS8Jfot5snS7H4AK2xyRbJ8erUNWc";
	private static final String BITCOIND_ADDRESS_3 = "mmqnw2wasfhRAwpKq6dD7jjBfs4ViBdkm3";
	private static final String BITCOIND_ADDRESS_4 = "n3e8rHyJHx1wxrLkQwzkeXz6poJt549nkG";
	private static final String BITCOIND_ADDRESS_5 = "muddTzjPesBasNJNyYNAS4ncqVECC18Edh";
	private static final String BITCOIND_BLOCK_1 = "000000000933ea01ad0ee984209779baaec3ced90fa3f408719526f8d77f4943";
	private static final String BITCOIND_BLOCK_2 = "00000000b873e79784647a6c82962c70d228557d24a747ea4d1b8bbe878e1206";
	private static final String BITCOIND_TRANSACTION_1 = "98dafef582ae53e8af30e8ad09577bbd472d4bf24a173121d58a5900747fd082";
	private static final String BITCOIND_TRANSACTION_2 = "5cfc16d9937e4ad36686b829942b0a7ab088750bc3008a71cd0a13ccf4ac1099";
	private static final String BITCOIND_RAW_TRANSACTION_1 = "010000000110f7af4e331b02cb2d0300bc879c65803274969da9aa305bade614058b32152d010000006b4830450220744d68a227a390e170e0f7d23ecb41ef883d6b1059d0e8b04cd6b00db2b0fd12022100d61a88715e6f0b192c4d09b8a302e93c7eb202c4346386d918a0668caa57df6c012102d94d41c62b1d0cd455772bcca8be0ffbbcee3c7bc87e6ee79689715137b96abeffffffff0240787d01000000001976a914ca8ac94e5f147ed553a0d8e6497e0bfa7e53a92588ac00e1f505000000001976a9149006180537784880b99fc57efdf25eee152cdd4588ac00000000";
	private static final String BITCOIND_RAW_TRANSACTION_2 = "e96404552c900fcf2d8ae797babc1ae0dac7e849856162da9fd90e35a18a6788";
	private static final String BITCOIND_PRIVATE_KEY = "cQ57cLoFkYRSAZJGkYMc8cTCoJhaQVEqSYNuVuUySzuLATFQ4Vcr";
	private static final String BITCOIND_ALERT = "bitcoin daemon test alert";
	private static final String BITCOIND_MESSAGE = "HELLO WORLD";
	private static final String BITCOIND_SIGNATURE = "Hwv4DIvzayZMpFp29NZxyeMeEbSE79UPhAyNrnka+glR65gES0eCP4zTMdFaq+F987KAbenGhTZCyWCneYThabo=";
	private static final String BITCOIND_PEER_1 = "213.5.71.38:18333";
	private static final String BITCOIND_PEER_2 = "54.243.211.176:18333";
	private static final String BITCOIND_PEER_3 = "212.110.31.242:18333";
	private static BtcDaemon BITCOIND_WITHOUT_LISTENER;
	private static BtcDaemon BITCOIND_WITH_LISTENER;
	private static List<String> ALERT_NOTIFICATIONS = new Vector<String>();
	private static List<BtcBlock> BLOCK_NOTIFICATIONS = new Vector<BtcBlock>();
	private static List<BtcTransaction> WALLET_NOTIFICATIONS = new Vector<BtcTransaction>();

	@AfterClass
	public static void cleanUp() throws Exception {
		for (String alert : ALERT_NOTIFICATIONS) {
			System.out.println("alert: " + alert);
		}
		for (BtcBlock block : BLOCK_NOTIFICATIONS) {
			System.out.println("block: " + block);
		}
		for (BtcTransaction transaction : WALLET_NOTIFICATIONS) {
			System.out.println("transaction: " + transaction);
		}
		assertTrue(ALERT_NOTIFICATIONS.size() > 0);
		assertEquals(BITCOIND_ALERT, ALERT_NOTIFICATIONS.get(0));
		assertTrue(BLOCK_NOTIFICATIONS.size() > 0);
		assertTrue(WALLET_NOTIFICATIONS.size() > 0);
		BITCOIND_WITH_LISTENER.stopListening();
		BITCOIND_WITHOUT_LISTENER.walletLock();
		BITCOIND_WITH_LISTENER.walletLock();
		if (BITCOIND_STOP) {
			String stop = BITCOIND_WITHOUT_LISTENER.stop();
			assertNotNull(stop);
			assertTrue(stop.length() >= 0);
		}
	}
}