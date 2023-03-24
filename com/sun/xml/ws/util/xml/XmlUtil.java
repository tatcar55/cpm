/*     */ package com.sun.xml.ws.util.xml;
/*     */ 
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.org.apache.xml.internal.resolver.Catalog;
/*     */ import com.sun.org.apache.xml.internal.resolver.CatalogManager;
/*     */ import com.sun.org.apache.xml.internal.resolver.tools.CatalogResolver;
/*     */ import com.sun.xml.ws.server.ServerRtException;
/*     */ import com.sun.xml.ws.util.ByteArrayBuffer;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Writer;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.sax.SAXTransformerFactory;
/*     */ import javax.xml.transform.sax.TransformerHandler;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ import org.xml.sax.XMLReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XmlUtil
/*     */ {
/*     */   private static final String LEXICAL_HANDLER_PROPERTY = "http://xml.org/sax/properties/lexical-handler";
/*     */   
/*     */   public static String getPrefix(String s) {
/*  94 */     int i = s.indexOf(':');
/*  95 */     if (i == -1)
/*  96 */       return null; 
/*  97 */     return s.substring(0, i);
/*     */   }
/*     */   
/*     */   public static String getLocalPart(String s) {
/* 101 */     int i = s.indexOf(':');
/* 102 */     if (i == -1)
/* 103 */       return s; 
/* 104 */     return s.substring(i + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getAttributeOrNull(Element e, String name) {
/* 110 */     Attr a = e.getAttributeNode(name);
/* 111 */     if (a == null)
/* 112 */       return null; 
/* 113 */     return a.getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getAttributeNSOrNull(Element e, String name, String nsURI) {
/* 120 */     Attr a = e.getAttributeNodeNS(nsURI, name);
/* 121 */     if (a == null)
/* 122 */       return null; 
/* 123 */     return a.getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getAttributeNSOrNull(Element e, QName name) {
/* 129 */     Attr a = e.getAttributeNodeNS(name.getNamespaceURI(), name.getLocalPart());
/* 130 */     if (a == null)
/* 131 */       return null; 
/* 132 */     return a.getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Iterator getAllChildren(Element element) {
/* 164 */     return new NodeListIterator(element.getChildNodes());
/*     */   }
/*     */   
/*     */   public static Iterator getAllAttributes(Element element) {
/* 168 */     return new NamedNodeMapIterator(element.getAttributes());
/*     */   }
/*     */   
/*     */   public static List<String> parseTokenList(String tokenList) {
/* 172 */     List<String> result = new ArrayList<String>();
/* 173 */     StringTokenizer tokenizer = new StringTokenizer(tokenList, " ");
/* 174 */     while (tokenizer.hasMoreTokens()) {
/* 175 */       result.add(tokenizer.nextToken());
/*     */     }
/* 177 */     return result;
/*     */   }
/*     */   
/*     */   public static String getTextForNode(Node node) {
/* 181 */     StringBuilder sb = new StringBuilder();
/*     */     
/* 183 */     NodeList children = node.getChildNodes();
/* 184 */     if (children.getLength() == 0) {
/* 185 */       return null;
/*     */     }
/* 187 */     for (int i = 0; i < children.getLength(); i++) {
/* 188 */       Node n = children.item(i);
/*     */       
/* 190 */       if (n instanceof org.w3c.dom.Text) {
/* 191 */         sb.append(n.getNodeValue());
/* 192 */       } else if (n instanceof org.w3c.dom.EntityReference) {
/* 193 */         String s = getTextForNode(n);
/* 194 */         if (s == null) {
/* 195 */           return null;
/*     */         }
/* 197 */         sb.append(s);
/*     */       } else {
/* 199 */         return null;
/*     */       } 
/*     */     } 
/* 202 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static InputStream getUTF8Stream(String s) {
/*     */     try {
/* 207 */       ByteArrayBuffer bab = new ByteArrayBuffer();
/* 208 */       Writer w = new OutputStreamWriter((OutputStream)bab, "utf-8");
/* 209 */       w.write(s);
/* 210 */       w.close();
/* 211 */       return bab.newInputStream();
/* 212 */     } catch (IOException e) {
/* 213 */       throw new RuntimeException("should not happen");
/*     */     } 
/*     */   }
/*     */   
/* 217 */   static final TransformerFactory transformerFactory = TransformerFactory.newInstance();
/*     */   
/* 219 */   static final SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
/*     */   
/*     */   static {
/* 222 */     saxParserFactory.setNamespaceAware(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Transformer newTransformer() {
/*     */     try {
/* 230 */       return transformerFactory.newTransformer();
/* 231 */     } catch (TransformerConfigurationException tex) {
/* 232 */       throw new IllegalStateException("Unable to create a JAXP transformer");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends Result> T identityTransform(Source src, T result) throws TransformerException, SAXException, ParserConfigurationException, IOException {
/* 241 */     if (src instanceof StreamSource) {
/*     */ 
/*     */       
/* 244 */       StreamSource ssrc = (StreamSource)src;
/* 245 */       TransformerHandler th = ((SAXTransformerFactory)transformerFactory).newTransformerHandler();
/* 246 */       th.setResult((Result)result);
/* 247 */       XMLReader reader = saxParserFactory.newSAXParser().getXMLReader();
/* 248 */       reader.setContentHandler(th);
/* 249 */       reader.setProperty("http://xml.org/sax/properties/lexical-handler", th);
/* 250 */       reader.parse(toInputSource(ssrc));
/*     */     } else {
/* 252 */       newTransformer().transform(src, (Result)result);
/*     */     } 
/* 254 */     return result;
/*     */   }
/*     */   
/*     */   private static InputSource toInputSource(StreamSource src) {
/* 258 */     InputSource is = new InputSource();
/* 259 */     is.setByteStream(src.getInputStream());
/* 260 */     is.setCharacterStream(src.getReader());
/* 261 */     is.setPublicId(src.getPublicId());
/* 262 */     is.setSystemId(src.getSystemId());
/* 263 */     return is;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EntityResolver createEntityResolver(@Nullable URL catalogUrl) {
/* 271 */     CatalogManager manager = new CatalogManager();
/* 272 */     manager.setIgnoreMissingProperties(true);
/*     */     
/* 274 */     manager.setUseStaticCatalog(false);
/* 275 */     Catalog catalog = manager.getCatalog();
/*     */     try {
/* 277 */       if (catalogUrl != null) {
/* 278 */         catalog.parseCatalog(catalogUrl);
/*     */       }
/* 280 */     } catch (IOException e) {
/* 281 */       throw new ServerRtException("server.rt.err", new Object[] { e });
/*     */     } 
/* 283 */     return workaroundCatalogResolver(catalog);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EntityResolver createDefaultCatalogResolver() {
/* 292 */     CatalogManager manager = new CatalogManager();
/* 293 */     manager.setIgnoreMissingProperties(true);
/*     */     
/* 295 */     manager.setUseStaticCatalog(false);
/*     */     
/* 297 */     ClassLoader cl = Thread.currentThread().getContextClassLoader();
/*     */     
/* 299 */     Catalog catalog = manager.getCatalog(); try {
/*     */       Enumeration<URL> catalogEnum;
/* 301 */       if (cl == null) {
/* 302 */         catalogEnum = ClassLoader.getSystemResources("META-INF/jax-ws-catalog.xml");
/*     */       } else {
/* 304 */         catalogEnum = cl.getResources("META-INF/jax-ws-catalog.xml");
/*     */       } 
/*     */       
/* 307 */       while (catalogEnum.hasMoreElements()) {
/* 308 */         URL url = catalogEnum.nextElement();
/* 309 */         catalog.parseCatalog(url);
/*     */       } 
/* 311 */     } catch (IOException e) {
/* 312 */       throw new WebServiceException(e);
/*     */     } 
/*     */     
/* 315 */     return workaroundCatalogResolver(catalog);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static CatalogResolver workaroundCatalogResolver(final Catalog catalog) {
/* 327 */     CatalogManager manager = new CatalogManager()
/*     */       {
/*     */         public Catalog getCatalog() {
/* 330 */           return catalog;
/*     */         }
/*     */       };
/* 333 */     manager.setIgnoreMissingProperties(true);
/*     */     
/* 335 */     manager.setUseStaticCatalog(false);
/*     */     
/* 337 */     return new CatalogResolver(manager);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 343 */   public static final ErrorHandler DRACONIAN_ERROR_HANDLER = new ErrorHandler()
/*     */     {
/*     */       public void warning(SAXParseException exception) {}
/*     */ 
/*     */ 
/*     */       
/*     */       public void error(SAXParseException exception) throws SAXException {
/* 350 */         throw exception;
/*     */       }
/*     */ 
/*     */       
/*     */       public void fatalError(SAXParseException exception) throws SAXException {
/* 355 */         throw exception;
/*     */       }
/*     */     };
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\xml\XmlUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */