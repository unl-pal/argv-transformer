package fr.ac_versailles.crdp.apiscol.seek;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.ac_versailles.crdp.apiscol.UsedNamespaces;
import fr.ac_versailles.crdp.apiscol.utils.LogUtility;
import fr.ac_versailles.crdp.apiscol.utils.XMLUtils;

public class WebServicesResponseMerger {

	private static NamespaceContext ctx = new NamespaceContext() {
		public String getNamespaceURI(String prefix) {
			String uri;
			if (prefix.equals(UsedNamespaces.ATOM.getShortHand()))
				uri = UsedNamespaces.ATOM.getUri();
			else if (prefix.equals(UsedNamespaces.APISCOL.getShortHand())) {
				uri = UsedNamespaces.APISCOL.getUri();
			} else
				uri = null;
			return uri;
		}

		public Iterator getPrefixes(String val) {
			return null;
		}

		public String getPrefix(String uri) {
			return null;
		}
	};
	private static XPathFactory xPathFactory = XPathFactory.newInstance();
	private static XPath xpath = xPathFactory.newXPath();

	private static Logger logger;

}
