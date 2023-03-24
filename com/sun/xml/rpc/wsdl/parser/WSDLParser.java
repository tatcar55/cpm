/*      */ package com.sun.xml.rpc.wsdl.parser;
/*      */ 
/*      */ import com.sun.xml.rpc.spi.tools.WSDLDocument;
/*      */ import com.sun.xml.rpc.spi.tools.WSDLParser;
/*      */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*      */ import com.sun.xml.rpc.util.localization.LocalizableMessageFactory;
/*      */ import com.sun.xml.rpc.util.localization.Localizer;
/*      */ import com.sun.xml.rpc.util.xml.NullEntityResolver;
/*      */ import com.sun.xml.rpc.util.xml.XmlUtil;
/*      */ import com.sun.xml.rpc.wsdl.document.Binding;
/*      */ import com.sun.xml.rpc.wsdl.document.BindingFault;
/*      */ import com.sun.xml.rpc.wsdl.document.BindingInput;
/*      */ import com.sun.xml.rpc.wsdl.document.BindingOperation;
/*      */ import com.sun.xml.rpc.wsdl.document.BindingOutput;
/*      */ import com.sun.xml.rpc.wsdl.document.Definitions;
/*      */ import com.sun.xml.rpc.wsdl.document.Documentation;
/*      */ import com.sun.xml.rpc.wsdl.document.Fault;
/*      */ import com.sun.xml.rpc.wsdl.document.Import;
/*      */ import com.sun.xml.rpc.wsdl.document.Input;
/*      */ import com.sun.xml.rpc.wsdl.document.Message;
/*      */ import com.sun.xml.rpc.wsdl.document.MessagePart;
/*      */ import com.sun.xml.rpc.wsdl.document.Operation;
/*      */ import com.sun.xml.rpc.wsdl.document.OperationStyle;
/*      */ import com.sun.xml.rpc.wsdl.document.Output;
/*      */ import com.sun.xml.rpc.wsdl.document.Port;
/*      */ import com.sun.xml.rpc.wsdl.document.PortType;
/*      */ import com.sun.xml.rpc.wsdl.document.Service;
/*      */ import com.sun.xml.rpc.wsdl.document.Types;
/*      */ import com.sun.xml.rpc.wsdl.document.WSDLConstants;
/*      */ import com.sun.xml.rpc.wsdl.document.WSDLDocument;
/*      */ import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
/*      */ import com.sun.xml.rpc.wsdl.document.schema.SchemaKinds;
/*      */ import com.sun.xml.rpc.wsdl.framework.AbstractDocument;
/*      */ import com.sun.xml.rpc.wsdl.framework.Defining;
/*      */ import com.sun.xml.rpc.wsdl.framework.Entity;
/*      */ import com.sun.xml.rpc.wsdl.framework.Extensible;
/*      */ import com.sun.xml.rpc.wsdl.framework.ParseException;
/*      */ import com.sun.xml.rpc.wsdl.framework.ParserContext;
/*      */ import com.sun.xml.rpc.wsdl.framework.ParserListener;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URL;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.parsers.DocumentBuilder;
/*      */ import javax.xml.parsers.DocumentBuilderFactory;
/*      */ import javax.xml.parsers.FactoryConfigurationError;
/*      */ import javax.xml.parsers.ParserConfigurationException;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.xml.sax.EntityResolver;
/*      */ import org.xml.sax.ErrorHandler;
/*      */ import org.xml.sax.InputSource;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.SAXParseException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class WSDLParser
/*      */   implements WSDLParser
/*      */ {
/*      */   private boolean _followImports;
/*      */   private String _targetNamespaceURI;
/*      */   private Map _extensionHandlers;
/*      */   private ArrayList _listeners;
/*      */   private boolean _useWSIBasicProfile;
/*      */   private LocalizableMessageFactory _messageFactory;
/*      */   private Localizer _localizer;
/*      */   private HashSet hSet;
/*      */   
/*      */   public WSDLParser() {
/* 1316 */     this._useWSIBasicProfile = false;
/* 1317 */     this._messageFactory = null;
/*      */     
/* 1319 */     this.hSet = null;
/*      */     this._extensionHandlers = new HashMap<Object, Object>();
/*      */     this.hSet = new HashSet();
/*      */     register(new SOAPExtensionHandler());
/*      */     register(new HTTPExtensionHandler());
/*      */     register(new MIMEExtensionHandler());
/*      */     register(new SchemaExtensionHandler());
/*      */   }
/*      */   
/*      */   public void register(ExtensionHandler h) {
/*      */     this._extensionHandlers.put(h.getNamespaceURI(), h);
/*      */     h.setExtensionHandlers(this._extensionHandlers);
/*      */   }
/*      */   
/*      */   public void unregister(ExtensionHandler h) {
/*      */     this._extensionHandlers.put(h.getNamespaceURI(), null);
/*      */     h.setExtensionHandlers(null);
/*      */   }
/*      */   
/*      */   public void unregister(String uri) {
/*      */     this._extensionHandlers.put(uri, null);
/*      */   }
/*      */   
/*      */   public boolean getFollowImports() {
/*      */     return this._followImports;
/*      */   }
/*      */   
/*      */   public void setFollowImports(boolean b) {
/*      */     this._followImports = b;
/*      */   }
/*      */   
/*      */   public WSDLDocument getWSDLDocument(URL wsdlURL) {
/*      */     return (WSDLDocument)getWSDLDocumentInternal(wsdlURL);
/*      */   }
/*      */   
/*      */   private WSDLDocument getWSDLDocumentInternal(URL wsdlURL) {
/*      */     InputStream wsdlInputStream = null;
/*      */     try {
/*      */       wsdlInputStream = new BufferedInputStream(wsdlURL.openStream());
/*      */     } catch (IOException e) {
/*      */       throw new ParseException("parsing.ioException", new LocalizableExceptionAdapter(e));
/*      */     } 
/*      */     InputSource wsdlDocumentSource = new InputSource(wsdlInputStream);
/*      */     setFollowImports(true);
/*      */     addParserListener(new ParserListener() {
/*      */           public void ignoringExtension(QName name, QName parent) {
/*      */             if (parent.equals(WSDLConstants.QNAME_TYPES))
/*      */               if (name.getLocalPart().equals("schema") && !name.getNamespaceURI().equals(""))
/*      */                 WSDLParser.this.warn("wsdlmodeler.warning.ignoringUnrecognizedSchemaExtension", name.getNamespaceURI());  
/*      */           }
/*      */           
/*      */           public void doneParsingEntity(QName element, Entity entity) {}
/*      */         });
/*      */     WSDLDocument wsdlDoc = parse(wsdlDocumentSource, true);
/*      */     Iterator importedDocs = wsdlDoc.getDefinitions().imports();
/*      */     wsdlDoc = parseImportedDocuments(importedDocs, wsdlDoc);
/*      */     try {
/*      */       wsdlInputStream.close();
/*      */     } catch (IOException ioe) {
/*      */       throw new ParseException("parsing.ioException", new LocalizableExceptionAdapter(ioe));
/*      */     } 
/*      */     return wsdlDoc;
/*      */   }
/*      */   
/*      */   private WSDLDocument parseImportedDocuments(Iterator<Import> imports, WSDLDocument wsdlDoc) {
/*      */     Definitions wsdlDefinitions = wsdlDoc.getDefinitions();
/*      */     for (Iterator<Import> iter = imports; iter.hasNext(); ) {
/*      */       Import mport = iter.next();
/*      */       try {
/*      */         WSDLDocument importDoc = getWSDLDocumentInternal(new URL(mport.getLocation()));
/*      */         Definitions definitions = importDoc.getDefinitions();
/*      */         Iterator<Service> siter = definitions.services();
/*      */         while (siter.hasNext())
/*      */           wsdlDefinitions.addServiceOveride(siter.next()); 
/*      */       } catch (MalformedURLException e) {
/*      */         e.printStackTrace();
/*      */       } 
/*      */     } 
/*      */     return wsdlDoc;
/*      */   }
/*      */   
/*      */   public void addParserListener(ParserListener l) {
/*      */     if (this._listeners == null)
/*      */       this._listeners = new ArrayList(); 
/*      */     this._listeners.add(l);
/*      */   }
/*      */   
/*      */   public void removeParserListener(ParserListener l) {
/*      */     if (this._listeners == null)
/*      */       return; 
/*      */     this._listeners.remove(l);
/*      */   }
/*      */   
/*      */   public WSDLDocument parse(InputSource source) {
/*      */     return parse(source, false);
/*      */   }
/*      */   
/*      */   public WSDLDocument parse(InputSource source, boolean useWSIBasicProfile) {
/*      */     this._useWSIBasicProfile = useWSIBasicProfile;
/*      */     this._messageFactory = new LocalizableMessageFactory("com.sun.xml.rpc.resources.wsdl");
/*      */     this._localizer = new Localizer();
/*      */     WSDLDocument document = new WSDLDocument();
/*      */     document.setSystemId(source.getSystemId());
/*      */     ParserContext context = new ParserContext((AbstractDocument)document, this._listeners);
/*      */     context.setFollowImports(this._followImports);
/*      */     document.setDefinitions(parseDefinitions(context, source, null));
/*      */     return document;
/*      */   }
/*      */   
/*      */   protected Definitions parseDefinitions(ParserContext context, InputSource source, String expectedTargetNamespaceURI) {
/*      */     context.pushWSDLLocation();
/*      */     context.setWSDLLocation(source.getSystemId());
/*      */     Definitions definitions = parseDefinitionsNoImport(context, source, expectedTargetNamespaceURI);
/*      */     processImports(context, source, definitions);
/*      */     context.popWSDLLocation();
/*      */     return definitions;
/*      */   }
/*      */   
/*      */   protected void processImports(ParserContext context, InputSource source, Definitions definitions) {
/*      */     for (Iterator<Import> iter = definitions.imports(); iter.hasNext(); ) {
/*      */       Import i = iter.next();
/*      */       String location = i.getLocation();
/*      */       if (location != null) {
/*      */         String adjustedLocation = (source.getSystemId() == null) ? ((context.getDocument().getSystemId() == null) ? location : Util.processSystemIdWithBase(context.getDocument().getSystemId(), location)) : Util.processSystemIdWithBase(source.getSystemId(), location);
/*      */         try {
/*      */           if (!context.getDocument().isImportedDocument(adjustedLocation)) {
/*      */             context.getDocument().addImportedEntity((Entity)parseDefinitions(context, new InputSource(adjustedLocation), i.getNamespace()));
/*      */             context.getDocument().addImportedDocument(adjustedLocation);
/*      */           } 
/*      */         } catch (ParseException e) {
/*      */           if (e.getKey().equals("parsing.incorrectRootElement")) {
/*      */             if (this._useWSIBasicProfile)
/*      */               warn("warning.wsi.r2001", adjustedLocation); 
/*      */             try {
/*      */               SchemaParser parser = new SchemaParser();
/*      */               context.getDocument().addImportedEntity((Entity)parser.parseSchema(context, new InputSource(adjustedLocation), i.getNamespace()));
/*      */             } catch (ParseException e2) {
/*      */               if (e2.getKey().equals("parsing.incorrectRootElement")) {
/*      */                 Util.fail("parsing.unknownImportedDocumentType", location);
/*      */                 continue;
/*      */               } 
/*      */               throw e2;
/*      */             } 
/*      */             continue;
/*      */           } 
/*      */           throw e;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected Definitions parseDefinitionsNoImport(ParserContext context, InputSource source, String expectedTargetNamespaceURI) {
/*      */     try {
/*      */       DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
/*      */       builderFactory.setNamespaceAware(true);
/*      */       builderFactory.setValidating(false);
/*      */       DocumentBuilder builder = builderFactory.newDocumentBuilder();
/*      */       builder.setErrorHandler(new ErrorHandler() {
/*      */             public void error(SAXParseException e) throws SAXParseException {
/*      */               throw e;
/*      */             }
/*      */             
/*      */             public void fatalError(SAXParseException e) throws SAXParseException {
/*      */               throw e;
/*      */             }
/*      */             
/*      */             public void warning(SAXParseException err) throws SAXParseException {}
/*      */           });
/*      */       builder.setEntityResolver((EntityResolver)new NullEntityResolver());
/*      */       try {
/*      */         Document document = builder.parse(source);
/*      */         return parseDefinitionsNoImport(context, document, expectedTargetNamespaceURI);
/*      */       } catch (IOException e) {
/*      */         if (source.getSystemId() != null)
/*      */           throw new ParseException("parsing.ioExceptionWithSystemId", source.getSystemId(), new LocalizableExceptionAdapter(e)); 
/*      */         throw new ParseException("parsing.ioException", new LocalizableExceptionAdapter(e));
/*      */       } catch (SAXException e) {
/*      */         if (source.getSystemId() != null)
/*      */           throw new ParseException("parsing.saxExceptionWithSystemId", source.getSystemId(), new LocalizableExceptionAdapter(e)); 
/*      */         throw new ParseException("parsing.saxException", new LocalizableExceptionAdapter(e));
/*      */       } 
/*      */     } catch (ParserConfigurationException e) {
/*      */       throw new ParseException("parsing.parserConfigException", new LocalizableExceptionAdapter(e));
/*      */     } catch (FactoryConfigurationError e) {
/*      */       throw new ParseException("parsing.factoryConfigException", new LocalizableExceptionAdapter(e));
/*      */     } 
/*      */   }
/*      */   
/*      */   protected Definitions parseDefinitionsNoImport(ParserContext context, Document doc, String expectedTargetNamespaceURI) {
/*      */     this._targetNamespaceURI = null;
/*      */     Element root = doc.getDocumentElement();
/*      */     Util.verifyTagNSRootElement(root, WSDLConstants.QNAME_DEFINITIONS);
/*      */     return parseDefinitionsNoImport(context, root, expectedTargetNamespaceURI);
/*      */   }
/*      */   
/*      */   protected Definitions parseDefinitionsNoImport(ParserContext context, Element e, String expectedTargetNamespaceURI) {
/*      */     context.push();
/*      */     context.registerNamespaces(e);
/*      */     Definitions definitions = new Definitions(context.getDocument());
/*      */     String name = XmlUtil.getAttributeOrNull(e, "name");
/*      */     definitions.setName(name);
/*      */     this._targetNamespaceURI = XmlUtil.getAttributeOrNull(e, "targetNamespace");
/*      */     if (expectedTargetNamespaceURI != null && !expectedTargetNamespaceURI.equals(this._targetNamespaceURI) && this._useWSIBasicProfile)
/*      */       warn("warning.wsi.r2002", new Object[] { this._targetNamespaceURI, expectedTargetNamespaceURI }); 
/*      */     definitions.setTargetNamespaceURI(this._targetNamespaceURI);
/*      */     boolean gotDocumentation = false;
/*      */     boolean gotTypes = false;
/*      */     for (Iterator iter = XmlUtil.getAllChildren(e); iter.hasNext(); ) {
/*      */       Element e2 = Util.nextElement(iter);
/*      */       if (e2 == null)
/*      */         break; 
/*      */       if (XmlUtil.matchesTagNS(e2, WSDLConstants.QNAME_DOCUMENTATION)) {
/*      */         if (gotDocumentation)
/*      */           Util.fail("parsing.onlyOneDocumentationAllowed", e.getLocalName()); 
/*      */         gotDocumentation = true;
/*      */         definitions.setDocumentation(getDocumentationFor(e2));
/*      */         continue;
/*      */       } 
/*      */       if (XmlUtil.matchesTagNS(e2, WSDLConstants.QNAME_TYPES)) {
/*      */         if (gotTypes)
/*      */           Util.fail("parsing.onlyOneTypesAllowed", "definitions"); 
/*      */         definitions.setTypes(parseTypes(context, definitions, e2));
/*      */         continue;
/*      */       } 
/*      */       if (XmlUtil.matchesTagNS(e2, WSDLConstants.QNAME_MESSAGE)) {
/*      */         Message message = parseMessage(context, definitions, e2);
/*      */         definitions.add(message);
/*      */         continue;
/*      */       } 
/*      */       if (XmlUtil.matchesTagNS(e2, WSDLConstants.QNAME_PORT_TYPE)) {
/*      */         PortType portType = parsePortType(context, definitions, e2);
/*      */         definitions.add(portType);
/*      */         continue;
/*      */       } 
/*      */       if (XmlUtil.matchesTagNS(e2, WSDLConstants.QNAME_BINDING)) {
/*      */         Binding binding = parseBinding(context, definitions, e2);
/*      */         definitions.add(binding);
/*      */         continue;
/*      */       } 
/*      */       if (XmlUtil.matchesTagNS(e2, WSDLConstants.QNAME_SERVICE)) {
/*      */         Service service = parseService(context, definitions, e2);
/*      */         definitions.add(service);
/*      */         continue;
/*      */       } 
/*      */       if (XmlUtil.matchesTagNS(e2, WSDLConstants.QNAME_IMPORT)) {
/*      */         definitions.add(parseImport(context, definitions, e2));
/*      */         continue;
/*      */       } 
/*      */       if (this._useWSIBasicProfile && XmlUtil.matchesTagNS(e2, SchemaConstants.QNAME_IMPORT)) {
/*      */         warn("warning.wsi.r2003");
/*      */         continue;
/*      */       } 
/*      */       checkNotWsdlElement(e2);
/*      */       if (!handleExtension(context, (Extensible)definitions, e2))
/*      */         checkNotWsdlRequired(e2); 
/*      */     } 
/*      */     context.pop();
/*      */     context.fireDoneParsingEntity(WSDLConstants.QNAME_DEFINITIONS, (Entity)definitions);
/*      */     return definitions;
/*      */   }
/*      */   
/*      */   protected Message parseMessage(ParserContext context, Definitions definitions, Element e) {
/*      */     context.push();
/*      */     context.registerNamespaces(e);
/*      */     Message message = new Message((Defining)definitions);
/*      */     String name = Util.getRequiredAttribute(e, "name");
/*      */     message.setName(name);
/*      */     boolean gotDocumentation = false;
/*      */     for (Iterator iter = XmlUtil.getAllChildren(e); iter.hasNext(); ) {
/*      */       Element e2 = Util.nextElement(iter);
/*      */       if (e2 == null)
/*      */         break; 
/*      */       if (XmlUtil.matchesTagNS(e2, WSDLConstants.QNAME_DOCUMENTATION)) {
/*      */         if (gotDocumentation)
/*      */           Util.fail("parsing.onlyOneDocumentationAllowed", e.getLocalName()); 
/*      */         gotDocumentation = true;
/*      */         message.setDocumentation(getDocumentationFor(e2));
/*      */         continue;
/*      */       } 
/*      */       if (XmlUtil.matchesTagNS(e2, WSDLConstants.QNAME_PART)) {
/*      */         MessagePart part = parseMessagePart(context, e2);
/*      */         message.add(part);
/*      */         continue;
/*      */       } 
/*      */       Util.fail("parsing.invalidElement", e2.getTagName(), e2.getNamespaceURI());
/*      */     } 
/*      */     context.pop();
/*      */     context.fireDoneParsingEntity(WSDLConstants.QNAME_MESSAGE, (Entity)message);
/*      */     return message;
/*      */   }
/*      */   
/*      */   protected MessagePart parseMessagePart(ParserContext context, Element e) {
/*      */     context.push();
/*      */     context.registerNamespaces(e);
/*      */     MessagePart part = new MessagePart();
/*      */     String partName = Util.getRequiredAttribute(e, "name");
/*      */     part.setName(partName);
/*      */     String elementAttr = XmlUtil.getAttributeOrNull(e, "element");
/*      */     String typeAttr = XmlUtil.getAttributeOrNull(e, "type");
/*      */     if (elementAttr != null) {
/*      */       if (typeAttr != null)
/*      */         Util.fail("parsing.onlyOneOfElementOrTypeRequired", partName); 
/*      */       part.setDescriptor(context.translateQualifiedName(elementAttr));
/*      */       part.setDescriptorKind(SchemaKinds.XSD_ELEMENT);
/*      */     } else if (typeAttr != null) {
/*      */       part.setDescriptor(context.translateQualifiedName(typeAttr));
/*      */       part.setDescriptorKind(SchemaKinds.XSD_TYPE);
/*      */     } else {
/*      */       Util.fail("parsing.elementOrTypeRequired", partName);
/*      */     } 
/*      */     context.pop();
/*      */     context.fireDoneParsingEntity(WSDLConstants.QNAME_PART, (Entity)part);
/*      */     return part;
/*      */   }
/*      */   
/*      */   protected PortType parsePortType(ParserContext context, Definitions definitions, Element e) {
/*      */     context.push();
/*      */     context.registerNamespaces(e);
/*      */     PortType portType = new PortType((Defining)definitions);
/*      */     String name = Util.getRequiredAttribute(e, "name");
/*      */     portType.setName(name);
/*      */     boolean gotDocumentation = false;
/*      */     for (Iterator iter = XmlUtil.getAllChildren(e); iter.hasNext(); ) {
/*      */       Element e2 = Util.nextElement(iter);
/*      */       if (e2 == null)
/*      */         break; 
/*      */       if (XmlUtil.matchesTagNS(e2, WSDLConstants.QNAME_DOCUMENTATION)) {
/*      */         if (gotDocumentation)
/*      */           Util.fail("parsing.onlyOneDocumentationAllowed", e.getLocalName()); 
/*      */         gotDocumentation = true;
/*      */         portType.setDocumentation(getDocumentationFor(e2));
/*      */         continue;
/*      */       } 
/*      */       if (XmlUtil.matchesTagNS(e2, WSDLConstants.QNAME_OPERATION)) {
/*      */         Operation op = parsePortTypeOperation(context, e2);
/*      */         portType.add(op);
/*      */         continue;
/*      */       } 
/*      */       Util.fail("parsing.invalidElement", e2.getTagName(), e2.getNamespaceURI());
/*      */     } 
/*      */     context.pop();
/*      */     context.fireDoneParsingEntity(WSDLConstants.QNAME_PORT_TYPE, (Entity)portType);
/*      */     return portType;
/*      */   }
/*      */   
/*      */   protected Operation parsePortTypeOperation(ParserContext context, Element e) {
/*      */     context.push();
/*      */     context.registerNamespaces(e);
/*      */     Operation operation = new Operation();
/*      */     String name = Util.getRequiredAttribute(e, "name");
/*      */     operation.setName(name);
/*      */     String parameterOrderAttr = XmlUtil.getAttributeOrNull(e, "parameterOrder");
/*      */     operation.setParameterOrder(parameterOrderAttr);
/*      */     boolean gotDocumentation = false;
/*      */     boolean gotInput = false;
/*      */     boolean gotOutput = false;
/*      */     boolean gotFault = false;
/*      */     boolean inputBeforeOutput = false;
/*      */     for (Iterator iter = XmlUtil.getAllChildren(e); iter.hasNext(); ) {
/*      */       Element e2 = Util.nextElement(iter);
/*      */       if (e2 == null)
/*      */         break; 
/*      */       if (XmlUtil.matchesTagNS(e2, WSDLConstants.QNAME_DOCUMENTATION)) {
/*      */         if (gotDocumentation)
/*      */           Util.fail("parsing.onlyOneDocumentationAllowed", e.getLocalName()); 
/*      */         gotDocumentation = true;
/*      */         operation.setDocumentation(getDocumentationFor(e2));
/*      */         continue;
/*      */       } 
/*      */       if (XmlUtil.matchesTagNS(e2, WSDLConstants.QNAME_INPUT)) {
/*      */         if (gotInput)
/*      */           Util.fail("parsing.tooManyElements", new Object[] { "input", "operation", name }); 
/*      */         context.push();
/*      */         context.registerNamespaces(e2);
/*      */         Input input = new Input();
/*      */         String messageAttr = Util.getRequiredAttribute(e2, "message");
/*      */         input.setMessage(context.translateQualifiedName(messageAttr));
/*      */         String nameAttr = XmlUtil.getAttributeOrNull(e2, "name");
/*      */         input.setName(nameAttr);
/*      */         operation.setInput(input);
/*      */         gotInput = true;
/*      */         if (gotOutput)
/*      */           inputBeforeOutput = false; 
/*      */         boolean gotDocumentation2 = false;
/*      */         Iterator iter2 = XmlUtil.getAllChildren(e2);
/*      */         while (iter2.hasNext()) {
/*      */           Element e3 = Util.nextElement(iter2);
/*      */           if (e3 == null)
/*      */             break; 
/*      */           if (XmlUtil.matchesTagNS(e3, WSDLConstants.QNAME_DOCUMENTATION)) {
/*      */             if (gotDocumentation2)
/*      */               Util.fail("parsing.onlyOneDocumentationAllowed", e.getLocalName()); 
/*      */             gotDocumentation2 = true;
/*      */             input.setDocumentation(getDocumentationFor(e3));
/*      */             continue;
/*      */           } 
/*      */           Util.fail("parsing.invalidElement", e3.getTagName(), e3.getNamespaceURI());
/*      */         } 
/*      */         context.pop();
/*      */         continue;
/*      */       } 
/*      */       if (XmlUtil.matchesTagNS(e2, WSDLConstants.QNAME_OUTPUT)) {
/*      */         if (gotOutput)
/*      */           Util.fail("parsing.tooManyElements", new Object[] { "output", "operation", name }); 
/*      */         context.push();
/*      */         context.registerNamespaces(e2);
/*      */         Output output = new Output();
/*      */         String messageAttr = Util.getRequiredAttribute(e2, "message");
/*      */         output.setMessage(context.translateQualifiedName(messageAttr));
/*      */         String nameAttr = XmlUtil.getAttributeOrNull(e2, "name");
/*      */         output.setName(nameAttr);
/*      */         operation.setOutput(output);
/*      */         gotOutput = true;
/*      */         if (gotInput)
/*      */           inputBeforeOutput = true; 
/*      */         boolean gotDocumentation2 = false;
/*      */         Iterator iter2 = XmlUtil.getAllChildren(e2);
/*      */         while (iter2.hasNext()) {
/*      */           Element e3 = Util.nextElement(iter2);
/*      */           if (e3 == null)
/*      */             break; 
/*      */           if (XmlUtil.matchesTagNS(e3, WSDLConstants.QNAME_DOCUMENTATION)) {
/*      */             if (gotDocumentation2)
/*      */               Util.fail("parsing.onlyOneDocumentationAllowed", e.getLocalName()); 
/*      */             gotDocumentation2 = true;
/*      */             output.setDocumentation(getDocumentationFor(e3));
/*      */             continue;
/*      */           } 
/*      */           Util.fail("parsing.invalidElement", e3.getTagName(), e3.getNamespaceURI());
/*      */         } 
/*      */         context.pop();
/*      */         continue;
/*      */       } 
/*      */       if (XmlUtil.matchesTagNS(e2, WSDLConstants.QNAME_FAULT)) {
/*      */         context.push();
/*      */         context.registerNamespaces(e2);
/*      */         Fault fault = new Fault();
/*      */         String messageAttr = Util.getRequiredAttribute(e2, "message");
/*      */         fault.setMessage(context.translateQualifiedName(messageAttr));
/*      */         String nameAttr = XmlUtil.getAttributeOrNull(e2, "name");
/*      */         fault.setName(nameAttr);
/*      */         operation.addFault(fault);
/*      */         gotFault = true;
/*      */         boolean gotDocumentation2 = false;
/*      */         Iterator iter2 = XmlUtil.getAllChildren(e2);
/*      */         while (iter2.hasNext()) {
/*      */           Element e3 = Util.nextElement(iter2);
/*      */           if (e3 == null)
/*      */             break; 
/*      */           if (XmlUtil.matchesTagNS(e3, WSDLConstants.QNAME_DOCUMENTATION)) {
/*      */             if (gotDocumentation2)
/*      */               Util.fail("parsing.onlyOneDocumentationAllowed", e.getLocalName()); 
/*      */             gotDocumentation2 = true;
/*      */             fault.setDocumentation(getDocumentationFor(e3));
/*      */             continue;
/*      */           } 
/*      */           Util.fail("parsing.invalidElement", e3.getTagName(), e3.getNamespaceURI());
/*      */         } 
/*      */         context.pop();
/*      */         continue;
/*      */       } 
/*      */       Util.fail("parsing.invalidElement", e2.getTagName(), e2.getNamespaceURI());
/*      */     } 
/*      */     if (gotInput && !gotOutput && !gotFault) {
/*      */       operation.setStyle(OperationStyle.ONE_WAY);
/*      */     } else if (gotInput && gotOutput && inputBeforeOutput) {
/*      */       operation.setStyle(OperationStyle.REQUEST_RESPONSE);
/*      */     } else if (gotInput && gotOutput && !inputBeforeOutput) {
/*      */       operation.setStyle(OperationStyle.SOLICIT_RESPONSE);
/*      */     } else if (gotOutput && !gotInput && !gotFault) {
/*      */       operation.setStyle(OperationStyle.NOTIFICATION);
/*      */     } else {
/*      */       Util.fail("parsing.invalidOperationStyle", name);
/*      */     } 
/*      */     context.pop();
/*      */     context.fireDoneParsingEntity(WSDLConstants.QNAME_OPERATION, (Entity)operation);
/*      */     return operation;
/*      */   }
/*      */   
/*      */   protected Binding parseBinding(ParserContext context, Definitions definitions, Element e) {
/*      */     context.push();
/*      */     context.registerNamespaces(e);
/*      */     Binding binding = new Binding((Defining)definitions);
/*      */     String name = Util.getRequiredAttribute(e, "name");
/*      */     binding.setName(name);
/*      */     String typeAttr = Util.getRequiredAttribute(e, "type");
/*      */     binding.setPortType(context.translateQualifiedName(typeAttr));
/*      */     boolean gotDocumentation = false;
/*      */     for (Iterator iter = XmlUtil.getAllChildren(e); iter.hasNext(); ) {
/*      */       Element e2 = Util.nextElement(iter);
/*      */       if (e2 == null)
/*      */         break; 
/*      */       if (XmlUtil.matchesTagNS(e2, WSDLConstants.QNAME_DOCUMENTATION)) {
/*      */         if (gotDocumentation)
/*      */           Util.fail("parsing.onlyOneDocumentationAllowed", e.getLocalName()); 
/*      */         gotDocumentation = true;
/*      */         binding.setDocumentation(getDocumentationFor(e2));
/*      */         continue;
/*      */       } 
/*      */       if (XmlUtil.matchesTagNS(e2, WSDLConstants.QNAME_OPERATION)) {
/*      */         BindingOperation op = parseBindingOperation(context, e2);
/*      */         binding.add(op);
/*      */         continue;
/*      */       } 
/*      */       checkNotWsdlElement(e2);
/*      */       if (!handleExtension(context, (Extensible)binding, e2))
/*      */         checkNotWsdlRequired(e2); 
/*      */     } 
/*      */     context.pop();
/*      */     context.fireDoneParsingEntity(WSDLConstants.QNAME_BINDING, (Entity)binding);
/*      */     return binding;
/*      */   }
/*      */   
/*      */   protected BindingOperation parseBindingOperation(ParserContext context, Element e) {
/*      */     context.push();
/*      */     context.registerNamespaces(e);
/*      */     BindingOperation operation = new BindingOperation();
/*      */     String name = Util.getRequiredAttribute(e, "name");
/*      */     operation.setName(name);
/*      */     boolean gotDocumentation = false;
/*      */     boolean gotInput = false;
/*      */     boolean gotOutput = false;
/*      */     boolean gotFault = false;
/*      */     boolean inputBeforeOutput = false;
/*      */     for (Iterator iter = XmlUtil.getAllChildren(e); iter.hasNext(); ) {
/*      */       Element e2 = Util.nextElement(iter);
/*      */       if (e2 == null)
/*      */         break; 
/*      */       if (XmlUtil.matchesTagNS(e2, WSDLConstants.QNAME_DOCUMENTATION)) {
/*      */         if (gotDocumentation)
/*      */           Util.fail("parsing.onlyOneDocumentationAllowed", e.getLocalName()); 
/*      */         gotDocumentation = true;
/*      */         operation.setDocumentation(getDocumentationFor(e2));
/*      */         continue;
/*      */       } 
/*      */       if (XmlUtil.matchesTagNS(e2, WSDLConstants.QNAME_INPUT)) {
/*      */         if (gotInput)
/*      */           Util.fail("parsing.tooManyElements", new Object[] { "input", "operation", name }); 
/*      */         Iterator itere2 = XmlUtil.getAllChildren(e2);
/*      */         Element ee = Util.nextElement(itere2);
/*      */         if (this.hSet.isEmpty()) {
/*      */           this.hSet.add(ee.getAttribute("use"));
/*      */         } else if (!this.hSet.contains(ee.getAttribute("use")) && ee.getAttribute("use") != "") {
/*      */           this.hSet.add(ee.getAttribute("use"));
/*      */         } 
/*      */         context.push();
/*      */         context.registerNamespaces(e2);
/*      */         BindingInput input = new BindingInput();
/*      */         String nameAttr = XmlUtil.getAttributeOrNull(e2, "name");
/*      */         input.setName(nameAttr);
/*      */         operation.setInput(input);
/*      */         gotInput = true;
/*      */         if (gotOutput)
/*      */           inputBeforeOutput = false; 
/*      */         boolean gotDocumentation2 = false;
/*      */         Iterator iter2 = XmlUtil.getAllChildren(e2);
/*      */         while (iter2.hasNext()) {
/*      */           Element e3 = Util.nextElement(iter2);
/*      */           if (e3 == null)
/*      */             break; 
/*      */           if (XmlUtil.matchesTagNS(e3, WSDLConstants.QNAME_DOCUMENTATION)) {
/*      */             if (gotDocumentation2)
/*      */               Util.fail("parsing.onlyOneDocumentationAllowed", e.getLocalName()); 
/*      */             gotDocumentation2 = true;
/*      */             input.setDocumentation(getDocumentationFor(e3));
/*      */             continue;
/*      */           } 
/*      */           checkNotWsdlElement(e3);
/*      */           if (!handleExtension(context, (Extensible)input, e3))
/*      */             checkNotWsdlRequired(e3); 
/*      */         } 
/*      */         context.pop();
/*      */         continue;
/*      */       } 
/*      */       if (XmlUtil.matchesTagNS(e2, WSDLConstants.QNAME_OUTPUT)) {
/*      */         if (gotOutput)
/*      */           Util.fail("parsing.tooManyElements", new Object[] { "output", "operation", name }); 
/*      */         context.push();
/*      */         context.registerNamespaces(e2);
/*      */         BindingOutput output = new BindingOutput();
/*      */         String nameAttr = XmlUtil.getAttributeOrNull(e2, "name");
/*      */         output.setName(nameAttr);
/*      */         operation.setOutput(output);
/*      */         gotOutput = true;
/*      */         if (gotInput)
/*      */           inputBeforeOutput = true; 
/*      */         boolean gotDocumentation2 = false;
/*      */         Iterator iter2 = XmlUtil.getAllChildren(e2);
/*      */         while (iter2.hasNext()) {
/*      */           Element e3 = Util.nextElement(iter2);
/*      */           if (e3 == null)
/*      */             break; 
/*      */           if (XmlUtil.matchesTagNS(e3, WSDLConstants.QNAME_DOCUMENTATION)) {
/*      */             if (gotDocumentation2)
/*      */               Util.fail("parsing.onlyOneDocumentationAllowed", e.getLocalName()); 
/*      */             gotDocumentation2 = true;
/*      */             output.setDocumentation(getDocumentationFor(e3));
/*      */             continue;
/*      */           } 
/*      */           checkNotWsdlElement(e3);
/*      */           if (!handleExtension(context, (Extensible)output, e3))
/*      */             checkNotWsdlRequired(e3); 
/*      */         } 
/*      */         context.pop();
/*      */         continue;
/*      */       } 
/*      */       if (XmlUtil.matchesTagNS(e2, WSDLConstants.QNAME_FAULT)) {
/*      */         context.push();
/*      */         context.registerNamespaces(e2);
/*      */         BindingFault fault = new BindingFault();
/*      */         String nameAttr = Util.getRequiredAttribute(e2, "name");
/*      */         fault.setName(nameAttr);
/*      */         operation.addFault(fault);
/*      */         gotFault = true;
/*      */         boolean gotDocumentation2 = false;
/*      */         Iterator iter2 = XmlUtil.getAllChildren(e2);
/*      */         while (iter2.hasNext()) {
/*      */           Element e3 = Util.nextElement(iter2);
/*      */           if (e3 == null)
/*      */             break; 
/*      */           if (XmlUtil.matchesTagNS(e3, WSDLConstants.QNAME_DOCUMENTATION)) {
/*      */             if (gotDocumentation2)
/*      */               Util.fail("parsing.onlyOneDocumentationAllowed", e.getLocalName()); 
/*      */             gotDocumentation2 = true;
/*      */             fault.setDocumentation(getDocumentationFor(e3));
/*      */             continue;
/*      */           } 
/*      */           checkNotWsdlElement(e3);
/*      */           if (!handleExtension(context, (Extensible)fault, e3))
/*      */             checkNotWsdlRequired(e3); 
/*      */         } 
/*      */         context.pop();
/*      */         continue;
/*      */       } 
/*      */       checkNotWsdlElement(e2);
/*      */       if (!handleExtension(context, (Extensible)operation, e2))
/*      */         checkNotWsdlRequired(e2); 
/*      */     } 
/*      */     if (gotInput && !gotOutput && !gotFault) {
/*      */       operation.setStyle(OperationStyle.ONE_WAY);
/*      */     } else if (gotInput && gotOutput && inputBeforeOutput) {
/*      */       operation.setStyle(OperationStyle.REQUEST_RESPONSE);
/*      */     } else if (gotInput && gotOutput && !inputBeforeOutput) {
/*      */       operation.setStyle(OperationStyle.SOLICIT_RESPONSE);
/*      */     } else if (gotOutput && !gotInput && !gotFault) {
/*      */       operation.setStyle(OperationStyle.NOTIFICATION);
/*      */     } else {
/*      */       Util.fail("parsing.invalidOperationStyle", name);
/*      */     } 
/*      */     context.pop();
/*      */     context.fireDoneParsingEntity(WSDLConstants.QNAME_OPERATION, (Entity)operation);
/*      */     return operation;
/*      */   }
/*      */   
/*      */   protected Import parseImport(ParserContext context, Definitions definitions, Element e) {
/*      */     context.push();
/*      */     context.registerNamespaces(e);
/*      */     Import anImport = new Import();
/*      */     String namespace = Util.getRequiredAttribute(e, "namespace");
/*      */     anImport.setNamespace(namespace);
/*      */     String location = Util.getRequiredAttribute(e, "location");
/*      */     anImport.setLocation(location);
/*      */     boolean gotDocumentation = false;
/*      */     for (Iterator iter = XmlUtil.getAllChildren(e); iter.hasNext(); ) {
/*      */       Element e2 = Util.nextElement(iter);
/*      */       if (e2 == null)
/*      */         break; 
/*      */       if (XmlUtil.matchesTagNS(e2, WSDLConstants.QNAME_DOCUMENTATION)) {
/*      */         if (gotDocumentation)
/*      */           Util.fail("parsing.onlyOneDocumentationAllowed", e.getLocalName()); 
/*      */         gotDocumentation = true;
/*      */         anImport.setDocumentation(getDocumentationFor(e2));
/*      */         continue;
/*      */       } 
/*      */       Util.fail("parsing.invalidElement", e2.getTagName(), e2.getNamespaceURI());
/*      */     } 
/*      */     context.pop();
/*      */     context.fireDoneParsingEntity(WSDLConstants.QNAME_IMPORT, (Entity)anImport);
/*      */     return anImport;
/*      */   }
/*      */   
/*      */   protected Service parseService(ParserContext context, Definitions definitions, Element e) {
/*      */     context.push();
/*      */     context.registerNamespaces(e);
/*      */     Service service = new Service((Defining)definitions);
/*      */     String name = Util.getRequiredAttribute(e, "name");
/*      */     service.setName(name);
/*      */     boolean gotDocumentation = false;
/*      */     for (Iterator iter = XmlUtil.getAllChildren(e); iter.hasNext(); ) {
/*      */       Element e2 = Util.nextElement(iter);
/*      */       if (e2 == null)
/*      */         break; 
/*      */       if (XmlUtil.matchesTagNS(e2, WSDLConstants.QNAME_DOCUMENTATION)) {
/*      */         if (gotDocumentation)
/*      */           Util.fail("parsing.onlyOneDocumentationAllowed", e.getLocalName()); 
/*      */         gotDocumentation = true;
/*      */         service.setDocumentation(getDocumentationFor(e2));
/*      */         continue;
/*      */       } 
/*      */       if (XmlUtil.matchesTagNS(e2, WSDLConstants.QNAME_PORT)) {
/*      */         Port port = parsePort(context, definitions, e2);
/*      */         service.add(port);
/*      */         continue;
/*      */       } 
/*      */       checkNotWsdlElement(e2);
/*      */       if (!handleExtension(context, (Extensible)service, e2))
/*      */         checkNotWsdlRequired(e2); 
/*      */     } 
/*      */     context.pop();
/*      */     context.fireDoneParsingEntity(WSDLConstants.QNAME_SERVICE, (Entity)service);
/*      */     return service;
/*      */   }
/*      */   
/*      */   protected Port parsePort(ParserContext context, Definitions definitions, Element e) {
/*      */     context.push();
/*      */     context.registerNamespaces(e);
/*      */     Port port = new Port((Defining)definitions);
/*      */     String name = Util.getRequiredAttribute(e, "name");
/*      */     port.setName(name);
/*      */     String bindingAttr = Util.getRequiredAttribute(e, "binding");
/*      */     port.setBinding(context.translateQualifiedName(bindingAttr));
/*      */     boolean gotDocumentation = false;
/*      */     for (Iterator iter = XmlUtil.getAllChildren(e); iter.hasNext(); ) {
/*      */       Element e2 = Util.nextElement(iter);
/*      */       if (e2 == null)
/*      */         break; 
/*      */       if (XmlUtil.matchesTagNS(e2, WSDLConstants.QNAME_DOCUMENTATION)) {
/*      */         if (gotDocumentation)
/*      */           Util.fail("parsing.onlyOneDocumentationAllowed", e.getLocalName()); 
/*      */         gotDocumentation = true;
/*      */         port.setDocumentation(getDocumentationFor(e2));
/*      */         continue;
/*      */       } 
/*      */       checkNotWsdlElement(e2);
/*      */       if (!handleExtension(context, (Extensible)port, e2))
/*      */         checkNotWsdlRequired(e2); 
/*      */     } 
/*      */     context.pop();
/*      */     context.fireDoneParsingEntity(WSDLConstants.QNAME_PORT, (Entity)port);
/*      */     return port;
/*      */   }
/*      */   
/*      */   protected Types parseTypes(ParserContext context, Definitions definitions, Element e) {
/*      */     context.push();
/*      */     context.registerNamespaces(e);
/*      */     Types types = new Types();
/*      */     boolean gotDocumentation = false;
/*      */     for (Iterator iter = XmlUtil.getAllChildren(e); iter.hasNext(); ) {
/*      */       Element e2 = Util.nextElement(iter);
/*      */       if (e2 == null)
/*      */         break; 
/*      */       if (XmlUtil.matchesTagNS(e2, WSDLConstants.QNAME_DOCUMENTATION)) {
/*      */         if (gotDocumentation)
/*      */           Util.fail("parsing.onlyOneDocumentationAllowed", e.getLocalName()); 
/*      */         gotDocumentation = true;
/*      */         types.setDocumentation(getDocumentationFor(e2));
/*      */         continue;
/*      */       } 
/*      */       if (this._useWSIBasicProfile && XmlUtil.matchesTagNS(e2, SchemaConstants.QNAME_IMPORT)) {
/*      */         warn("warning.wsi.r2003");
/*      */         continue;
/*      */       } 
/*      */       checkNotWsdlElement(e2);
/*      */       try {
/*      */         if (!handleExtension(context, (Extensible)types, e2))
/*      */           checkNotWsdlRequired(e2); 
/*      */       } catch (ParseException pe) {
/*      */         if (pe.getKey().equals("parsing.incorrectRootElement")) {
/*      */           if (this._useWSIBasicProfile)
/*      */             warn("warning.wsi.r2004"); 
/*      */           throw pe;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     context.pop();
/*      */     context.fireDoneParsingEntity(WSDLConstants.QNAME_TYPES, (Entity)types);
/*      */     return types;
/*      */   }
/*      */   
/*      */   protected boolean handleExtension(ParserContext context, Extensible entity, Element e) {
/*      */     ExtensionHandler h = (ExtensionHandler)this._extensionHandlers.get(e.getNamespaceURI());
/*      */     if (h == null) {
/*      */       context.fireIgnoringExtension(new QName(e.getNamespaceURI(), e.getLocalName()), ((Entity)entity).getElementName());
/*      */       return false;
/*      */     } 
/*      */     return h.doHandleExtension(context, entity, e);
/*      */   }
/*      */   
/*      */   protected void checkNotWsdlElement(Element e) {
/*      */     if (e.getNamespaceURI().equals("http://schemas.xmlsoap.org/wsdl/"))
/*      */       Util.fail("parsing.invalidWsdlElement", e.getTagName()); 
/*      */   }
/*      */   
/*      */   protected void checkNotWsdlRequired(Element e) {
/*      */     String required = XmlUtil.getAttributeNSOrNull(e, "required", "http://schemas.xmlsoap.org/wsdl/");
/*      */     if (required != null && required.equals("true"))
/*      */       Util.fail("parsing.requiredExtensibilityElement", e.getTagName(), e.getNamespaceURI()); 
/*      */   }
/*      */   
/*      */   protected Documentation getDocumentationFor(Element e) {
/*      */     String s = XmlUtil.getTextForNode(e);
/*      */     if (s == null)
/*      */       return null; 
/*      */     return new Documentation(s);
/*      */   }
/*      */   
/*      */   protected void error(String key) {
/*      */     System.err.println(this._localizer.localize(this._messageFactory.getMessage(key)));
/*      */   }
/*      */   
/*      */   public HashSet getUse() {
/*      */     return this.hSet;
/*      */   }
/*      */   
/*      */   protected void warn(String key) {
/*      */     System.err.println(this._localizer.localize(this._messageFactory.getMessage(key)));
/*      */   }
/*      */   
/*      */   protected void warn(String key, String arg) {
/*      */     System.err.println(this._localizer.localize(this._messageFactory.getMessage(key, arg)));
/*      */   }
/*      */   
/*      */   protected void warn(String key, Object[] args) {
/*      */     System.err.println(this._localizer.localize(this._messageFactory.getMessage(key, args)));
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\parser\WSDLParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */