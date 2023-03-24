/*     */ package com.sun.xml.wss.core;
/*     */ 
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.XMLUtil;
/*     */ import com.sun.xml.wss.impl.misc.SecurityHeaderBlockImpl;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Iterator;
/*     */ import java.util.TimeZone;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import org.w3c.dom.Node;
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
/*     */ 
/*     */ 
/*     */ public class Timestamp
/*     */   extends SecurityHeaderBlockImpl
/*     */ {
/*  71 */   public static final SimpleDateFormat calendarFormatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
/*     */ 
/*     */   
/*  74 */   public static final SimpleDateFormat calendarFormatter2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'SSS'Z'");
/*     */ 
/*     */   
/*  77 */   private static final TimeZone utc = TimeZone.getTimeZone("UTC");
/*  78 */   private static Calendar utcCalendar = new GregorianCalendar(utc);
/*  79 */   private static final SimpleDateFormat utcCalendarFormatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
/*     */ 
/*     */   
/*     */   static {
/*  83 */     utcCalendarFormatter1.setTimeZone(utc);
/*     */   }
/*     */   
/*  86 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   private static String XSD_DATE_TIME = "xsd:dateTime";
/*     */ 
/*     */   
/*     */   public static final long MAX_CLOCK_SKEW = 300000L;
/*     */ 
/*     */   
/*     */   public static final long TIMESTAMP_FRESHNESS_LIMIT = 300000L;
/*     */   
/*     */   private String created;
/*     */   
/* 102 */   private long timeout = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   private String createdValueType = XSD_DATE_TIME;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   private String expires = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   private String expiresValueType = XSD_DATE_TIME;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   private String wsuId = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Timestamp(SOAPElement element) throws XWSSecurityException {
/* 135 */     if (!element.getLocalName().equals("Timestamp") || !XMLUtil.inWsuNS(element)) {
/*     */       
/* 137 */       log.log(Level.SEVERE, "WSS0385.error.creating.timestamp", element.getTagName());
/* 138 */       throw new XWSSecurityException("Invalid timestamp element passed");
/*     */     } 
/*     */     
/* 141 */     setSOAPElement(element);
/*     */ 
/*     */     
/* 144 */     Iterator<Node> children = element.getChildElements();
/* 145 */     while (children.hasNext()) {
/*     */       
/* 147 */       Node object = children.next();
/*     */       
/* 149 */       if (object.getNodeType() == 1) {
/* 150 */         SOAPElement subElement = (SOAPElement)object;
/* 151 */         if ("Created".equals(subElement.getLocalName()) && XMLUtil.inWsuNS(subElement)) {
/*     */ 
/*     */           
/* 154 */           if (isBSP() && this.created != null) {
/*     */             
/* 156 */             log.log(Level.SEVERE, "BSP3203.Onecreated.Timestamp");
/* 157 */             throw new XWSSecurityException("There can be only one wsu:Created element under Timestamp");
/*     */           } 
/*     */           
/* 160 */           this.created = subElement.getValue();
/* 161 */           this.createdValueType = subElement.getAttribute("ValueType");
/*     */           
/* 163 */           if (isBSP() && this.createdValueType != null && this.createdValueType.length() > 0) {
/*     */             
/* 165 */             log.log(Level.SEVERE, "BSP3225.createdValueType.Timestamp");
/* 166 */             throw new XWSSecurityException("A wsu:Created element within a TIMESTAMP MUST NOT include a ValueType attribute.");
/*     */           } 
/* 168 */           if ("".equalsIgnoreCase(this.createdValueType)) {
/* 169 */             this.createdValueType = null;
/*     */           }
/*     */         } 
/*     */         
/* 173 */         if ("Expires".equals(subElement.getLocalName()) && XMLUtil.inWsuNS(subElement)) {
/*     */ 
/*     */           
/* 176 */           if (isBSP() && this.expires != null) {
/*     */             
/* 178 */             log.log(Level.SEVERE, "BSP3224.Oneexpires.Timestamp");
/* 179 */             throw new XWSSecurityException("There can be only one wsu:Expires element under Timestamp");
/*     */           } 
/*     */           
/* 182 */           if (isBSP() && this.created == null) {
/*     */             
/* 184 */             log.log(Level.SEVERE, "BSP3221.CreatedBeforeExpires.Timestamp");
/* 185 */             throw new XWSSecurityException("wsu:Expires must appear after wsu:Created in the Timestamp");
/*     */           } 
/*     */           
/* 188 */           this.expires = subElement.getValue();
/*     */           
/* 190 */           this.expiresValueType = subElement.getAttribute("ValueType");
/*     */           
/* 192 */           if (isBSP() && this.expiresValueType != null && this.expiresValueType.length() > 0) {
/*     */             
/* 194 */             log.log(Level.SEVERE, "BSP3226.expiresValueType.Timestamp");
/* 195 */             throw new XWSSecurityException("A wsu:Expires element within a TIMESTAMP MUST NOT include a ValueType attribute.");
/*     */           } 
/* 197 */           if ("".equalsIgnoreCase(this.expiresValueType)) {
/* 198 */             this.expiresValueType = null;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 203 */     this.wsuId = element.getAttribute("wsu:Id");
/* 204 */     if ("".equalsIgnoreCase(this.wsuId)) {
/* 205 */       this.wsuId = null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCreated() {
/* 213 */     return this.created;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCreated(String created) {
/* 220 */     this.created = created;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCreatedValueType() {
/* 227 */     return this.createdValueType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCreatedValueType(String createdValueType) {
/* 234 */     this.createdValueType = createdValueType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTimeout(long timeout) {
/* 241 */     this.timeout = timeout;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExpires() {
/* 248 */     return this.expires;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExpires(String expires) {
/* 255 */     this.expires = expires;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExpiresValueType() {
/* 262 */     return this.expiresValueType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExpiresValueType(String expiresValueType) {
/* 269 */     this.expiresValueType = expiresValueType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 276 */     return this.wsuId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(String wsuId) {
/* 284 */     this.wsuId = wsuId;
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement getAsSoapElement() throws XWSSecurityException {
/*     */     SOAPElement timestamp;
/* 290 */     createDateTime();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 295 */       timestamp = getSoapFactory().createElement("Timestamp", "wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 301 */       timestamp.addNamespaceDeclaration("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
/*     */ 
/*     */       
/* 304 */       SOAPElement createdElement = timestamp.addChildElement("Created", "wsu").addTextNode(this.created);
/*     */ 
/*     */ 
/*     */       
/* 308 */       if (this.createdValueType != null && !XSD_DATE_TIME.equalsIgnoreCase(this.createdValueType))
/*     */       {
/* 310 */         createdElement.setAttribute("ValueType", this.createdValueType);
/*     */       }
/*     */       
/* 313 */       if (this.expires != null) {
/* 314 */         SOAPElement expiresElement = timestamp.addChildElement("Expires", "wsu").addTextNode(this.expires);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 319 */         if (this.expiresValueType != null && !XSD_DATE_TIME.equalsIgnoreCase(this.expiresValueType))
/*     */         {
/* 321 */           expiresElement.setAttribute("ValueType", this.expiresValueType);
/*     */         }
/*     */       } 
/*     */       
/* 325 */       if (this.wsuId != null) {
/* 326 */         setWsuIdAttr(timestamp, getId());
/*     */       }
/*     */     }
/* 329 */     catch (SOAPException se) {
/* 330 */       log.log(Level.SEVERE, "WSS0386.error.creating.timestamp", se.getMessage());
/* 331 */       throw new XWSSecurityException("There was an error creating  Timestamp " + se.getMessage());
/*     */     } 
/*     */ 
/*     */     
/* 335 */     setSOAPElement(timestamp);
/* 336 */     return timestamp;
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
/*     */   public void createDateTime() throws XWSSecurityException {
/* 348 */     if (this.created == null)
/* 349 */       synchronized (utcCalendar) {
/*     */         
/* 351 */         long currentTime = System.currentTimeMillis();
/* 352 */         utcCalendar.setTimeInMillis(currentTime);
/*     */         
/* 354 */         setCreated(utcCalendarFormatter1.format(utcCalendar.getTime()));
/*     */         
/* 356 */         utcCalendar.setTimeInMillis(currentTime + this.timeout);
/* 357 */         setExpires(utcCalendarFormatter1.format(utcCalendar.getTime()));
/*     */       }  
/*     */   }
/*     */   
/*     */   public Timestamp() {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\Timestamp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */