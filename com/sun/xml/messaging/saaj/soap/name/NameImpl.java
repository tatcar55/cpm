/*     */ package com.sun.xml.messaging.saaj.soap.name;
/*     */ 
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Name;
/*     */ import org.w3c.dom.Element;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NameImpl
/*     */   implements Name
/*     */ {
/*     */   public static final String XML_NAMESPACE_PREFIX = "xml";
/*     */   public static final String XML_SCHEMA_NAMESPACE_PREFIX = "xs";
/*     */   public static final String SOAP_ENVELOPE_PREFIX = "SOAP-ENV";
/*     */   public static final String XML_NAMESPACE = "http://www.w3.org/XML/1998/namespace";
/*     */   public static final String SOAP11_NAMESPACE = "http://schemas.xmlsoap.org/soap/envelope/";
/*     */   public static final String SOAP12_NAMESPACE = "http://www.w3.org/2003/05/soap-envelope";
/*     */   public static final String XML_SCHEMA_NAMESPACE = "http://www.w3.org/2001/XMLSchema";
/*  68 */   protected String uri = "";
/*  69 */   protected String localName = "";
/*  70 */   protected String prefix = "";
/*  71 */   private String qualifiedName = null;
/*     */   
/*  73 */   protected static final Logger log = Logger.getLogger("com.sun.xml.messaging.saaj.soap.name", "com.sun.xml.messaging.saaj.soap.name.LocalStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   public static final String XMLNS_URI = "http://www.w3.org/2000/xmlns/".intern();
/*     */   
/*     */   protected NameImpl(String name) {
/*  85 */     this.localName = (name == null) ? "" : name;
/*     */   }
/*     */   
/*     */   protected NameImpl(String name, String prefix, String uri) {
/*  89 */     this.uri = (uri == null) ? "" : uri;
/*  90 */     this.localName = (name == null) ? "" : name;
/*  91 */     this.prefix = (prefix == null) ? "" : prefix;
/*     */     
/*  93 */     if (this.prefix.equals("xmlns") && this.uri.equals("")) {
/*  94 */       this.uri = XMLNS_URI;
/*     */     }
/*  96 */     if (this.uri.equals(XMLNS_URI) && this.prefix.equals("")) {
/*  97 */       this.prefix = "xmlns";
/*     */     }
/*     */   }
/*     */   
/*     */   public static Name convertToName(QName qname) {
/* 102 */     return new NameImpl(qname.getLocalPart(), qname.getPrefix(), qname.getNamespaceURI());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static QName convertToQName(Name name) {
/* 108 */     return new QName(name.getURI(), name.getLocalName(), name.getPrefix());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static NameImpl createFromUnqualifiedName(String name) {
/* 114 */     return new NameImpl(name);
/*     */   }
/*     */   
/*     */   public static Name createFromTagName(String tagName) {
/* 118 */     return createFromTagAndUri(tagName, "");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Name createFromQualifiedName(String qualifiedName, String uri) {
/* 124 */     return createFromTagAndUri(qualifiedName, uri);
/*     */   }
/*     */   
/*     */   protected static Name createFromTagAndUri(String tagName, String uri) {
/* 128 */     if (tagName == null) {
/* 129 */       log.severe("SAAJ0201.name.not.created.from.null.tag");
/* 130 */       throw new IllegalArgumentException("Cannot create a name from a null tag.");
/*     */     } 
/* 132 */     int index = tagName.indexOf(':');
/* 133 */     if (index < 0) {
/* 134 */       return new NameImpl(tagName, "", uri);
/*     */     }
/* 136 */     return new NameImpl(tagName.substring(index + 1), tagName.substring(0, index), uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static int getPrefixSeparatorIndex(String qualifiedName) {
/* 144 */     int index = qualifiedName.indexOf(':');
/* 145 */     if (index < 0) {
/* 146 */       log.log(Level.SEVERE, "SAAJ0202.name.invalid.arg.format", (Object[])new String[] { qualifiedName });
/*     */ 
/*     */ 
/*     */       
/* 150 */       throw new IllegalArgumentException("Argument \"" + qualifiedName + "\" must be of the form: \"prefix:localName\"");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 155 */     return index;
/*     */   }
/*     */   
/*     */   public static String getPrefixFromQualifiedName(String qualifiedName) {
/* 159 */     return qualifiedName.substring(0, getPrefixSeparatorIndex(qualifiedName));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getLocalNameFromQualifiedName(String qualifiedName) {
/* 165 */     return qualifiedName.substring(getPrefixSeparatorIndex(qualifiedName) + 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getPrefixFromTagName(String tagName) {
/* 170 */     if (isQualified(tagName)) {
/* 171 */       return getPrefixFromQualifiedName(tagName);
/*     */     }
/* 173 */     return "";
/*     */   }
/*     */   
/*     */   public static String getLocalNameFromTagName(String tagName) {
/* 177 */     if (isQualified(tagName)) {
/* 178 */       return getLocalNameFromQualifiedName(tagName);
/*     */     }
/* 180 */     return tagName;
/*     */   }
/*     */   
/*     */   public static boolean isQualified(String tagName) {
/* 184 */     return (tagName.indexOf(':') >= 0);
/*     */   }
/*     */   
/*     */   public static NameImpl create(String name, String prefix, String uri) {
/* 188 */     if (prefix == null)
/* 189 */       prefix = ""; 
/* 190 */     if (uri == null)
/* 191 */       uri = ""; 
/* 192 */     if (name == null) {
/* 193 */       name = "";
/*     */     }
/* 195 */     if (!uri.equals("") && !name.equals("")) {
/* 196 */       if (uri.equals("http://schemas.xmlsoap.org/soap/envelope/")) {
/* 197 */         if (name.equalsIgnoreCase("Envelope"))
/* 198 */           return createEnvelope1_1Name(prefix); 
/* 199 */         if (name.equalsIgnoreCase("Header"))
/* 200 */           return createHeader1_1Name(prefix); 
/* 201 */         if (name.equalsIgnoreCase("Body"))
/* 202 */           return createBody1_1Name(prefix); 
/* 203 */         if (name.equalsIgnoreCase("Fault")) {
/* 204 */           return createFault1_1Name(prefix);
/*     */         }
/* 206 */         return new SOAP1_1Name(name, prefix);
/* 207 */       }  if (uri.equals("http://www.w3.org/2003/05/soap-envelope")) {
/* 208 */         if (name.equalsIgnoreCase("Envelope"))
/* 209 */           return createEnvelope1_2Name(prefix); 
/* 210 */         if (name.equalsIgnoreCase("Header"))
/* 211 */           return createHeader1_2Name(prefix); 
/* 212 */         if (name.equalsIgnoreCase("Body"))
/* 213 */           return createBody1_2Name(prefix); 
/* 214 */         if (name.equals("Fault") || name.equals("Reason") || name.equals("Detail"))
/*     */         {
/*     */ 
/*     */           
/* 218 */           return createFault1_2Name(name, prefix); } 
/* 219 */         if (name.equals("Code") || name.equals("Subcode")) {
/* 220 */           return createCodeSubcode1_2Name(prefix, name);
/*     */         }
/* 222 */         return new SOAP1_2Name(name, prefix);
/*     */       } 
/*     */     } 
/*     */     
/* 226 */     return new NameImpl(name, prefix, uri);
/*     */   }
/*     */   
/*     */   public static String createQName(String prefix, String localName) {
/* 230 */     if (prefix == null || prefix.equals("")) {
/* 231 */       return localName;
/*     */     }
/* 233 */     return prefix + ":" + localName;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/* 237 */     if (!(obj instanceof Name)) {
/* 238 */       return false;
/*     */     }
/*     */     
/* 241 */     Name otherName = (Name)obj;
/*     */     
/* 243 */     if (!this.uri.equals(otherName.getURI())) {
/* 244 */       return false;
/*     */     }
/*     */     
/* 247 */     if (!this.localName.equals(otherName.getLocalName())) {
/* 248 */       return false;
/*     */     }
/*     */     
/* 251 */     return true;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 255 */     return this.localName.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalName() {
/* 264 */     return this.localName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrefix() {
/* 275 */     return this.prefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getURI() {
/* 284 */     return this.uri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getQualifiedName() {
/* 291 */     if (this.qualifiedName == null) {
/* 292 */       if (this.prefix != null && this.prefix.length() > 0) {
/* 293 */         this.qualifiedName = this.prefix + ":" + this.localName;
/*     */       } else {
/* 295 */         this.qualifiedName = this.localName;
/*     */       } 
/*     */     }
/* 298 */     return this.qualifiedName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NameImpl createEnvelope1_1Name(String prefix) {
/* 305 */     return new Envelope1_1Name(prefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NameImpl createEnvelope1_2Name(String prefix) {
/* 312 */     return new Envelope1_2Name(prefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NameImpl createHeader1_1Name(String prefix) {
/* 319 */     return new Header1_1Name(prefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NameImpl createHeader1_2Name(String prefix) {
/* 326 */     return new Header1_2Name(prefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NameImpl createBody1_1Name(String prefix) {
/* 333 */     return new Body1_1Name(prefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NameImpl createBody1_2Name(String prefix) {
/* 340 */     return new Body1_2Name(prefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NameImpl createFault1_1Name(String prefix) {
/* 347 */     return new Fault1_1Name(prefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NameImpl createNotUnderstood1_2Name(String prefix) {
/* 354 */     return new NotUnderstood1_2Name(prefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NameImpl createUpgrade1_2Name(String prefix) {
/* 361 */     return new Upgrade1_2Name(prefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NameImpl createSupportedEnvelope1_2Name(String prefix) {
/* 368 */     return new SupportedEnvelope1_2Name(prefix);
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
/*     */   public static NameImpl createFault1_2Name(String localName, String prefix) {
/* 380 */     return new Fault1_2Name(localName, prefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NameImpl createCodeSubcode1_2Name(String prefix, String localName) {
/* 391 */     return new CodeSubcode1_2Name(localName, prefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NameImpl createDetail1_1Name() {
/* 398 */     return new Detail1_1Name();
/*     */   }
/*     */   
/*     */   public static NameImpl createDetail1_1Name(String prefix) {
/* 402 */     return new Detail1_1Name(prefix);
/*     */   }
/*     */   
/*     */   public static NameImpl createFaultElement1_1Name(String localName) {
/* 406 */     return new FaultElement1_1Name(localName);
/*     */   }
/*     */ 
/*     */   
/*     */   public static NameImpl createFaultElement1_1Name(String localName, String prefix) {
/* 411 */     return new FaultElement1_1Name(localName, prefix);
/*     */   }
/*     */   
/*     */   public static NameImpl createSOAP11Name(String string) {
/* 415 */     return new SOAP1_1Name(string, null);
/*     */   }
/*     */   public static NameImpl createSOAP12Name(String string) {
/* 418 */     return new SOAP1_2Name(string, null);
/*     */   }
/*     */   
/*     */   public static NameImpl createSOAP12Name(String localName, String prefix) {
/* 422 */     return new SOAP1_2Name(localName, prefix);
/*     */   }
/*     */   
/*     */   public static NameImpl createXmlName(String localName) {
/* 426 */     return new NameImpl(localName, "xml", "http://www.w3.org/XML/1998/namespace");
/*     */   }
/*     */   
/*     */   public static Name copyElementName(Element element) {
/* 430 */     String localName = element.getLocalName();
/* 431 */     String prefix = element.getPrefix();
/* 432 */     String uri = element.getNamespaceURI();
/* 433 */     return create(localName, prefix, uri);
/*     */   }
/*     */   
/*     */   static class SOAP1_1Name
/*     */     extends NameImpl {
/*     */     SOAP1_1Name(String name, String prefix) {
/* 439 */       super(name, (prefix == null || prefix.equals("")) ? "SOAP-ENV" : prefix, "http://schemas.xmlsoap.org/soap/envelope/");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class Envelope1_1Name
/*     */     extends SOAP1_1Name
/*     */   {
/*     */     Envelope1_1Name(String prefix) {
/* 450 */       super("Envelope", prefix);
/*     */     }
/*     */   }
/*     */   
/*     */   static class Header1_1Name extends SOAP1_1Name {
/*     */     Header1_1Name(String prefix) {
/* 456 */       super("Header", prefix);
/*     */     }
/*     */   }
/*     */   
/*     */   static class Body1_1Name extends SOAP1_1Name {
/*     */     Body1_1Name(String prefix) {
/* 462 */       super("Body", prefix);
/*     */     }
/*     */   }
/*     */   
/*     */   static class Fault1_1Name extends NameImpl {
/*     */     Fault1_1Name(String prefix) {
/* 468 */       super("Fault", (prefix == null || prefix.equals("")) ? "SOAP-ENV" : prefix, "http://schemas.xmlsoap.org/soap/envelope/");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class Detail1_1Name
/*     */     extends NameImpl
/*     */   {
/*     */     Detail1_1Name() {
/* 479 */       super("detail");
/*     */     }
/*     */     
/*     */     Detail1_1Name(String prefix) {
/* 483 */       super("detail", prefix, "");
/*     */     }
/*     */   }
/*     */   
/*     */   static class FaultElement1_1Name extends NameImpl {
/*     */     FaultElement1_1Name(String localName) {
/* 489 */       super(localName);
/*     */     }
/*     */     
/*     */     FaultElement1_1Name(String localName, String prefix) {
/* 493 */       super(localName, prefix, "");
/*     */     }
/*     */   }
/*     */   
/*     */   static class SOAP1_2Name extends NameImpl {
/*     */     SOAP1_2Name(String name, String prefix) {
/* 499 */       super(name, (prefix == null || prefix.equals("")) ? "env" : prefix, "http://www.w3.org/2003/05/soap-envelope");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class Envelope1_2Name
/*     */     extends SOAP1_2Name
/*     */   {
/*     */     Envelope1_2Name(String prefix) {
/* 510 */       super("Envelope", prefix);
/*     */     }
/*     */   }
/*     */   
/*     */   static class Header1_2Name extends SOAP1_2Name {
/*     */     Header1_2Name(String prefix) {
/* 516 */       super("Header", prefix);
/*     */     }
/*     */   }
/*     */   
/*     */   static class Body1_2Name extends SOAP1_2Name {
/*     */     Body1_2Name(String prefix) {
/* 522 */       super("Body", prefix);
/*     */     }
/*     */   }
/*     */   
/*     */   static class Fault1_2Name extends NameImpl {
/*     */     Fault1_2Name(String name, String prefix) {
/* 528 */       super((name == null || name.equals("")) ? "Fault" : name, (prefix == null || prefix.equals("")) ? "env" : prefix, "http://www.w3.org/2003/05/soap-envelope");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class NotUnderstood1_2Name
/*     */     extends NameImpl
/*     */   {
/*     */     NotUnderstood1_2Name(String prefix) {
/* 539 */       super("NotUnderstood", (prefix == null || prefix.equals("")) ? "env" : prefix, "http://www.w3.org/2003/05/soap-envelope");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class Upgrade1_2Name
/*     */     extends NameImpl
/*     */   {
/*     */     Upgrade1_2Name(String prefix) {
/* 550 */       super("Upgrade", (prefix == null || prefix.equals("")) ? "env" : prefix, "http://www.w3.org/2003/05/soap-envelope");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class SupportedEnvelope1_2Name
/*     */     extends NameImpl
/*     */   {
/*     */     SupportedEnvelope1_2Name(String prefix) {
/* 561 */       super("SupportedEnvelope", (prefix == null || prefix.equals("")) ? "env" : prefix, "http://www.w3.org/2003/05/soap-envelope");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class CodeSubcode1_2Name
/*     */     extends SOAP1_2Name
/*     */   {
/*     */     CodeSubcode1_2Name(String prefix, String localName) {
/* 572 */       super(prefix, localName);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\name\NameImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */