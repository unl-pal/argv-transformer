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

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.noo4j.core.BtcAccount;
import org.noo4j.core.BtcAddedNode;
import org.noo4j.core.BtcAddress;
import org.noo4j.core.BtcBlock;
import org.noo4j.core.BtcBlockSubmission;
import org.noo4j.core.BtcBlockTemplate;
import org.noo4j.core.BtcCoinbase;
import org.noo4j.core.BtcException;
import org.noo4j.core.BtcInfo;
import org.noo4j.core.BtcLastBlock;
import org.noo4j.core.BtcMiningInfo;
import org.noo4j.core.BtcMultiSignatureAddress;
import org.noo4j.core.BtcNode;
import org.noo4j.core.BtcOutputPart;
import org.noo4j.core.BtcPeer;
import org.noo4j.core.BtcRawTransaction;
import org.noo4j.core.BtcScript;
import org.noo4j.core.BtcTransaction;
import org.noo4j.core.BtcTransactionDetail;
import org.noo4j.core.BtcInput;
import org.noo4j.core.BtcOutput;
import org.noo4j.core.BtcOutputSet;
import org.noo4j.core.BtcTransactionTemplate;
import org.noo4j.core.BtcWork;

public class BtcJsonRpcHttpClient {
	private static final String BTC4J_DAEMON_DATA_INVALID_ID = "invalid json id";
	private static final String BTC4J_DAEMON_DATA_NULL_JSON = "json value is empty";
	private static final String BTC4J_DAEMON_DATA_NULL_URL = "server URL is null";
	private static final String BTC4J_DAEMON_DATA_INVALID_TYPE = "unexpected return type ";
	private static final String BTC4J_DAEMON_HTTP_HEADER = "Content-Type";
	private static final String BTC4J_DAEMON_JSON_CONTENT_TYPE = "application/json";
	private static final String BTC4J_DAEMON_JSONRPC_CONTENT_TYPE = "application/json-rpc";
	private static final String BTC4J_DAEMON_CHARSET = "UTF-8";
	private static final int BTC4J_DAEMON_TIMEOUT = 60000;
	private static final String BTCOBJ_ACCOUNT_ACCOUNT = "account";
	private static final String BTCOBJ_ACCOUNT_AMOUNT = "amount";
	private static final String BTCOBJ_ACCOUNT_CONFIRMATIONS = "confirmations";
	private static final String BTCOBJ_ADDRESS_ACCOUNT = "account";
	private static final String BTCOBJ_ADDRESS_ADDRESS = "address";
	private static final String BTCOBJ_ADDRESS_AMOUNT = "amount";
	private static final String BTCOBJ_ADDRESS_COMPRESSED = "iscompressed";
	private static final String BTCOBJ_ADDRESS_CONFIRMATIONS = "confirmations";
	private static final String BTCOBJ_ADDRESS_MINE = "ismine";
	private static final String BTCOBJ_ADDRESS_PUBLIC_KEY = "pubkey";
	private static final String BTCOBJ_ADDRESS_REDEEM_SCRIPT = "redeemScript";
	private static final String BTCOBJ_ADDRESS_SCRIPT = "isscript";
	private static final String BTCOBJ_ADDRESS_VALID = "isvalid";
	private static final String BTCOBJ_BLOCK_BITS = "bits";
	private static final String BTCOBJ_BLOCK_CONFIRMATIONS = "confirmations";
	private static final String BTCOBJ_BLOCK_DIFFICULTY = "difficulty";
	private static final String BTCOBJ_BLOCK_HASH = "hash";
	private static final String BTCOBJ_BLOCK_HEIGHT = "height";
	private static final String BTCOBJ_BLOCK_MERKLE_ROOT = "merkleroot";
	private static final String BTCOBJ_BLOCK_NEXT_BLOCK_HASH = "nextblockhash";
	private static final String BTCOBJ_BLOCK_NONCE = "nonce";
	private static final String BTCOBJ_BLOCK_PREVIOUS_BLOCK_HASH = "previousblockhash";
	private static final String BTCOBJ_BLOCK_SIZE = "size";
	private static final String BTCOBJ_BLOCK_TIME = "time";
	private static final String BTCOBJ_BLOCK_TRANSACTIONS = "tx";
	private static final String BTCOBJ_BLOCK_VERSION = "version";
	private static final String BTCOBJ_BLOCK_TEMPLATE_TRANSACTIONS = "transactions";
	private static final String BTCOBJ_BLOCK_TEMPLATE_TARGET = "target";
	private static final String BTCOBJ_BLOCK_TEMPLATE_MIN_TIME = "mintime";
	private static final String BTCOBJ_BLOCK_TEMPLATE_MUTABLE = "mutable";
	private static final String BTCOBJ_BLOCK_TEMPLATE_NONCE_RANGE = "noncerange";
	private static final String BTCOBJ_BLOCK_TEMPLATE_SIGNATURE_OPERATIONS = "sigoplimit";
	private static final String BTCOBJ_BLOCK_TEMPLATE_SIZE = "sizelimit";
	private static final String BTCOBJ_BLOCK_TEMPLATE_TIME = "curtime";
	private static final String BTCOBJ_COIN_AUX = "coinbaseaux";
	private static final String BTCOBJ_COIN_VALUE = "coinbasevalue";
	private static final String BTCOBJ_INFO_COINAGE = "coinage";
	private static final String BTCOBJ_INFO_BALANCE = "balance";
	private static final String BTCOBJ_INFO_UNCONFIRMED = "unconfirmed";
	private static final String BTCOBJ_INFO_NEWMINT = "newmint";
	private static final String BTCOBJ_INFO_STAKE = "stake";
	private static final String BTCOBJ_INFO_MONEY_SUPPLY = "moneysupply";
	private static final String BTCOBJ_INFO_BLOCKS = "blocks";
	private static final String BTCOBJ_INFO_CONNECTIONS = "connections";
	private static final String BTCOBJ_INFO_CURRENT_BLOCK_SIZE = "currentblocksize";
	private static final String BTCOBJ_INFO_CURRENT_BLOCK_TRANSACTIONS = "currentblocktx";
	private static final String BTCOBJ_INFO_DIFFICULTY = "difficulty";
	private static final String BTCOBJ_INFO_ERRORS = "errors";
	private static final String BTCOBJ_INFO_GENERATE = "generate";
	private static final String BTCOBJ_INFO_HASHES_PER_SECOND = "hashespersec";
	private static final String BTCOBJ_INFO_NETWORK_GIGA_HASHES_PER_SECOND = "networkghps";
	private static final String BTCOBJ_INFO_KEYPOOL_OLDEST = "keypoololdest";
	private static final String BTCOBJ_INFO_KEYPOOL_SIZE = "keypoolsize";
	private static final String BTCOBJ_INFO_POOLED_TRANSACTIONS = "pooledtx";
	private static final String BTCOBJ_INFO_PROCESSOR_LIMIT = "genproclimit";
	private static final String BTCOBJ_INFO_PROTOCOL_VERSION = "protocolversion";
	private static final String BTCOBJ_INFO_PROXY = "proxy";
	private static final String BTCOBJ_INFO_IP = "ip";
	private static final String BTCOBJ_INFO_TESTNET = "testnet";
	//private static final String BTCOBJ_INFO_TIME_OFFSET = "timeoffset";
	private static final String BTCOBJ_INFO_TRANSACTION_FEE = "paytxfee";
	private static final String BTCOBJ_INFO_VERSION = "version";
	private static final String BTCOBJ_INFO_WALLET_VERSION = "walletversion";
	private static final String BTCOBJ_LAST_BLOCK_LAST_BLOCK = "lastblock";
	private static final String BTCOBJ_LAST_BLOCK_TRANSACTIONS = "transactions";
	private static final String BTCOBJ_NODE_ADDED_NODE = "addednode";
	private static final String BTCOBJ_NODE_CONNECTED = "connected";
	private static final String BTCOBJ_NODE_ADDRESSES = "addresses";
	private static final String BTCOBJ_NODE_ADDRESS = "address";
	private static final String BTCOBJ_PEER_ADDRESS = "addr";
	private static final String BTCOBJ_PEER_BAN_SCORE = "banscore";
	private static final String BTCOBJ_PEER_BYTES_RECEIVED = "bytesrecv";
	private static final String BTCOBJ_PEER_BYTES_SENT = "bytessent";
	private static final String BTCOBJ_PEER_CONNECTION_TIME = "conntime";
	private static final String BTCOBJ_PEER_INBOUND = "inbound";
	private static final String BTCOBJ_PEER_LAST_RECEIVED = "lastrecv";
	private static final String BTCOBJ_PEER_LAST_SEND = "lastsend";
	private static final String BTCOBJ_PEER_SERVICES = "services";
	private static final String BTCOBJ_PEER_START_HEIGHT = "startingheight";
	private static final String BTCOBJ_PEER_SUBVERSION = "subver";
	private static final String BTCOBJ_PEER_SYNC_NODE = "syncnode";
	private static final String BTCOBJ_PEER_VERSION = "version";
	private static final String BTCOBJ_SCRIPT_ASM = "asm";
	private static final String BTCOBJ_SCRIPT_PUBLIC_KEY = "hex";
	private static final String BTCOBJ_SCRIPT_REQUIRED_SIGNATURES = "reqSigs";
	private static final String BTCOBJ_SCRIPT_TYPE = "type";
	private static final String BTCOBJ_SCRIPT_ADDRESSES = "addresses";
	private static final String BTCOBJ_TX_AMOUNT = "amount";
	private static final String BTCOBJ_TX_FEE = "fee";
	private static final String BTCOBJ_TX_BLOCK_HASH = "blockhash";
	private static final String BTCOBJ_TX_BLOCK_INDEX = "blockindex";
	private static final String BTCOBJ_TX_BLOCK_TIME = "blocktime";
	private static final String BTCOBJ_TX_CONFIRMATIONS = "confirmations";
	private static final String BTCOBJ_TX_DETAILS = "details";
	private static final String BTCOBJ_TX_TIME = "time";
	private static final String BTCOBJ_TX_TIME_RECEIVED = "timereceived";
	private static final String BTCOBJ_TX_TRANSACTION = "txid";
	private static final String BTCOBJ_TX_HEX = "hex";
	private static final String BTCOBJ_TX_VERSION = "version";
	private static final String BTCOBJ_TX_LOCK_TIME = "locktime";
	private static final String BTCOBJ_TX_INPUTS = "vin";
	private static final String BTCOBJ_TX_OUTPUTS = "vout";
	private static final String BTCOBJ_TX_COMPLETE = "complete";
	private static final String BTCOBJ_TX_DETAIL_ACCOUNT = "account";
	private static final String BTCOBJ_TX_DETAIL_ADDRESS = "address";
	private static final String BTCOBJ_TX_DETAIL_AMOUNT = "amount";
	private static final String BTCOBJ_TX_DETAIL_CATEGORY = "category";
	private static final String BTCOBJ_TX_DETAIL_FEE = "fee";
	private static final String BTCOBJ_TX_INPUT_TRANSACTION = "txid";
	private static final String BTCOBJ_TX_INPUT_OUTPUT = "vout";
	private static final String BTCOBJ_TX_INPUT_SCRIPT_SIGNATURE = "scriptSig";
	private static final String BTCOBJ_TX_INPUT_SEQUENCE = "sequence";
	private static final String BTCOBJ_TX_OUTPUT_TRANSACTION = "txid";
	private static final String BTCOBJ_TX_OUTPUT_BEST_BLOCK = "bestblock";
	private static final String BTCOBJ_TX_OUTPUT_CONFIRMATIONS = "confirmations";
	private static final String BTCOBJ_TX_OUTPUT_VALUE = "value";
	private static final String BTCOBJ_TX_OUTPUT_INDEX = "n";
	private static final String BTCOBJ_TX_OUTPUT_SCRIPT_PUBLIC_KEY = "scriptPubKey";
	private static final String BTCOBJ_TX_OUTPUT_VERSION = "version";
	private static final String BTCOBJ_TX_OUTPUT_COINBASE = "coinbase";
	private static final String BTCOBJ_TX_OUTPUT_OUTPUT = "vout";
	private static final String BTCOBJ_TX_OUTPUT_SET_BEST_BLOCK = "bestblock";
	private static final String BTCOBJ_TX_OUTPUT_SET_BYTES_SERIALIZED = "bytes_serialized";
	private static final String BTCOBJ_TX_OUTPUT_SET_HASH_SERIALIZED = "hash_serialized";
	private static final String BTCOBJ_TX_OUTPUT_SET_HEIGHT = "height";
	private static final String BTCOBJ_TX_OUTPUT_SET_OUTPUT_TRANSACTIONS = "txouts";
	private static final String BTCOBJ_TX_OUTPUT_SET_TOTAL_AMOUNT = "total_amount";
	private static final String BTCOBJ_TX_OUTPUT_SET_TRANSACTIONS = "transactions";
	private static final String BTCOBJ_WORK_MIDSTATE = "midstate";
	private static final String BTCOBJ_WORK_DATA = "data";
	private static final String BTCOBJ_WORK_HASH = "hash1";
	private static final String BTCOBJ_WORK_TARGET = "target";
	private static final String JSONRPC_CODE = "code";
	private static final String JSONRPC_DATA = "data";
	private static final String JSONRPC_ERROR = "error";
	private static final String JSONRPC_ID = "id";
	private static final String JSONRPC_REALM = "jsonrpc";
	private static final String JSONRPC_MESSAGE = "message";
	private static final String JSONRPC_METHOD = "method";
	private static final String JSONRPC_PARAMS = "params";
	private static final String JSONRPC_RESULT = "result";
	private static final String JSONRPC_VERSION = "2.0";
	private final static Logger LOG = Logger
			.getLogger(BtcJsonRpcHttpClient.class.getName());
	private CredentialsProvider credentialsProvider;
	private RequestConfig requestConfig;
	private URL url;
}