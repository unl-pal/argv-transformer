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

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;

import org.noo4j.core.BtcAccount;
import org.noo4j.core.BtcAddedNode;
import org.noo4j.core.BtcAddress;
import org.noo4j.core.BtcApi;
import org.noo4j.core.BtcBlock;
import org.noo4j.core.BtcBlockSubmission;
import org.noo4j.core.BtcBlockTemplate;
import org.noo4j.core.BtcException;
import org.noo4j.core.BtcLastBlock;
import org.noo4j.core.BtcMiningInfo;
import org.noo4j.core.BtcMultiSignatureAddress;
import org.noo4j.core.BtcNode;
import org.noo4j.core.BtcOutput;
import org.noo4j.core.BtcOutputPart;
import org.noo4j.core.BtcOutputSet;
import org.noo4j.core.BtcPeer;
import org.noo4j.core.BtcInfo;
import org.noo4j.core.BtcRawTransaction;
import org.noo4j.core.BtcTransaction;
import org.noo4j.core.BtcUtil;
import org.noo4j.core.BtcWork;

public class BtcDaemon extends BtcJsonRpcHttpClient implements BtcApi {
	private static final String BTCAPI_SUBMIT_VOTE = "submitvote";
	private static final String BTCAPI_SUBMIT_WORK = "submitwork";
	private static final String BTCAPI_ADD_MULTISIGNATURE_ADDRESS = "addmultisigaddress";
	private static final String BTCAPI_ADD_NODE = "addnode";
	private static final String BTCAPI_BACKUP_WALLET = "backupwallet";
	private static final String BTCAPI_CREATE_MULTISIGNATURE_ADDRESS = "createmultisig";
	private static final String BTCAPI_CREATE_RAW_TRANSACTION = "createrawtransaction";
	private static final String BTCAPI_DECODE_RAW_TRANSACTION = "decoderawtransaction";
	private static final String BTCAPI_DUMP_PRIVATE_KEY = "dumpprivkey";
	private static final String BTCAPI_GET_ACCOUNT = "getaccount";
	private static final String BTCAPI_GET_ACCOUNT_ADDRESS = "getaccountaddress";
	private static final String BTCAPI_GET_ADDED_NODE_INFORMATION = "getaddednodeinfo";
	private static final String BTCAPI_GET_ADDRESSES_BY_ACCOUNT = "getaddressesbyaccount";
	private static final String BTCAPI_GET_COINAGE = "getcoinage";
	private static final String BTCAPI_GET_BALANCE = "getbalance";
	private static final String BTCAPI_GET_BLOCK = "getblock";
	private static final String BTCAPI_GET_BLOCK_COUNT = "getblockcount";
	private static final String BTCAPI_GET_BLOCK_HASH = "getblockhash";
	private static final String BTCAPI_GET_BLOCK_TEMPLATE = "getblocktemplate";
	private static final String BTCAPI_GET_CONNECTION_COUNT = "getconnectioncount";
	private static final String BTCAPI_GET_DIFFICULTY = "getdifficulty";
	private static final String BTCAPI_GET_GENERATE = "getgenerate";
	private static final String BTCAPI_GET_HASHES_PER_SECOND = "gethashespersec";
	private static final String BTCAPI_GET_INFORMATION = "getinfo";
	private static final String BTCAPI_GET_MINING_INFORMATION = "getmininginfo";
	private static final String BTCAPI_GET_NEW_ADDRESS = "getnewaddress";
	private static final String BTCAPI_GET_PEER_INFORMATION = "getpeerinfo";
	private static final String BTCAPI_GET_RAW_MEMORY_POOL = "getrawmempool";
	private static final String BTCAPI_GET_RAW_TRANSACTION = "getrawtransaction";
	private static final String BTCAPI_GET_RECEIVED_BY_ACCOUNT = "getreceivedbyaccount";
	private static final String BTCAPI_GET_RECEIVED_BY_ADDRESS = "getreceivedbyaddress";
	private static final String BTCAPI_GET_TRANSACTION = "gettransaction";
	private static final String BTCAPI_GET_TRANSACTION_OUTPUT = "gettxout";
	private static final String BTCAPI_GET_TRANSACTION_OUTPUTSET_INFORMATION = "gettxoutsetinfo";
	private static final String BTCAPI_GET_WORK = "getwork";
	private static final String BTCAPI_GET_HELP = "help";
	private static final String BTCAPI_IMPORT_PRIVATE_KEY = "importprivkey";
	private static final String BTCAPI_KEYPOOL_REFILL = "keypoolrefill";
	private static final String BTCAPI_LIST_ACCOUNTS = "listaccounts";
	private static final String BTCAPI_LIST_ADDRESS_GROUPINGS = "listaddressgroupings";
	private static final String BTCAPI_LIST_LOCK_UNSPENT = "listlockunspent";
	private static final String BTCAPI_LIST_RECEIVED_BY_ACCOUNT = "listreceivedbyaccount";
	private static final String BTCAPI_LIST_RECEIVED_BY_ADDRESS = "listreceivedbyaddress";
	private static final String BTCAPI_LIST_SINCE_BLOCK = "listsinceblock";
	private static final String BTCAPI_LIST_TRANSACTIONS = "listtransactions";
	private static final String BTCAPI_LIST_UNSPENT = "listunspent";
	private static final String BTCAPI_LOCK_UNSPENT = "lockunspent";
	private static final String BTCAPI_MOVE_FUNDS = "move";
	private static final String BTCAPI_SEND_FROM = "sendfrom";
	private static final String BTCAPI_SEND_MANY = "sendmany";
	private static final String BTCAPI_SEND_RAW_TRANSACTION = "sendrawtransaction";
	private static final String BTCAPI_SEND_TO_ADDRESS = "sendtoaddress";
	private static final String BTCAPI_SET_ACCOUNT = "setaccount";
	private static final String BTCAPI_SET_GENERATE = "setgenerate";
	private static final String BTCAPI_SET_TRANSACTION_FEE = "settxfee";
	private static final String BTCAPI_SIGN_MESSAGE = "signmessage";
	private static final String BTCAPI_SIGN_RAW_TRANSACTION = "signrawtransaction";
	private static final String BTCAPI_STOP_DAEMON = "stop";
	private static final String BTCAPI_SUBMIT_BLOCK = "submitblock";
	private static final String BTCAPI_VALIDATE_ADDRESS = "validateaddress";
	private static final String BTCAPI_VERIFY_MESSAGE = "verifymessage";
	private static final String BTCAPI_WALLET_LOCK = "walletlock";
	private static final String BTCAPI_WALLET_PASSPHRASE = "walletpassphrase";
	private static final String BTCAPI_WALLET_PASSPHRASE_CHANGE = "walletpassphrasechange";
	private static final String[] BTC4J_DAEMON_VERSIONS = { "0.8.6" };
	private static final long BTCAPI_WALLET_TIMEOUT = 120;
	private BtcAlertListener alertListener;
	private Thread alertThread;
	private BtcBlockListener blockListener;
	private Thread blockThread;
	private BtcWalletListener walletListener;
	private Thread walletThread;
}