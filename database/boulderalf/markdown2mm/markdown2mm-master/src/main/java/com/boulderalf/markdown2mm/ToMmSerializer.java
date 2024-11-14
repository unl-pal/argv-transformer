package com.boulderalf.markdown2mm;

import generated.Html;
import generated.Map;
import generated.Richcontent;
import org.pegdown.ast.*;
import org.pegdown.ast.Node;
import org.w3c.dom.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.parboiled.common.Preconditions.checkArgNotNull;

public class ToMmSerializer implements Visitor {
	protected Stack<MmNodeWrapper> stack = new Stack<MmNodeWrapper>();

	protected Map map = new Map();

	protected StringBuilder currentStringBuilder = null;
	protected StringBuilder mainStringBuilder = null;
	protected Stack<Integer> currentNumberOfTableColumns = new Stack<Integer>();


}
