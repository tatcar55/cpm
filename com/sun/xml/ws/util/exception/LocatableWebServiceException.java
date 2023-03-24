/*     */ package com.sun.xml.ws.util.exception;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.resources.UtilMessages;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.helpers.LocatorImpl;
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
/*     */ 
/*     */ public class LocatableWebServiceException
/*     */   extends WebServiceException
/*     */ {
/*     */   private final Locator[] location;
/*     */   
/*     */   public LocatableWebServiceException(String message, Locator... location) {
/*  71 */     this(message, (Throwable)null, location);
/*     */   }
/*     */   
/*     */   public LocatableWebServiceException(String message, Throwable cause, Locator... location) {
/*  75 */     super(appendLocationInfo(message, location), cause);
/*  76 */     this.location = location;
/*     */   }
/*     */   
/*     */   public LocatableWebServiceException(Throwable cause, Locator... location) {
/*  80 */     this(cause.toString(), cause, location);
/*     */   }
/*     */   
/*     */   public LocatableWebServiceException(String message, XMLStreamReader locationSource) {
/*  84 */     this(message, new Locator[] { toLocation(locationSource) });
/*     */   }
/*     */   
/*     */   public LocatableWebServiceException(String message, Throwable cause, XMLStreamReader locationSource) {
/*  88 */     this(message, cause, new Locator[] { toLocation(locationSource) });
/*     */   }
/*     */   
/*     */   public LocatableWebServiceException(Throwable cause, XMLStreamReader locationSource) {
/*  92 */     this(cause, new Locator[] { toLocation(locationSource) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<Locator> getLocation() {
/* 102 */     return Arrays.asList(this.location);
/*     */   }
/*     */   
/*     */   private static String appendLocationInfo(String message, Locator[] location) {
/* 106 */     StringBuilder buf = new StringBuilder(message);
/* 107 */     for (Locator loc : location)
/* 108 */       buf.append('\n').append(UtilMessages.UTIL_LOCATION(Integer.valueOf(loc.getLineNumber()), loc.getSystemId())); 
/* 109 */     return buf.toString();
/*     */   }
/*     */   
/*     */   private static Locator toLocation(XMLStreamReader xsr) {
/* 113 */     LocatorImpl loc = new LocatorImpl();
/* 114 */     Location in = xsr.getLocation();
/* 115 */     loc.setSystemId(in.getSystemId());
/* 116 */     loc.setPublicId(in.getPublicId());
/* 117 */     loc.setLineNumber(in.getLineNumber());
/* 118 */     loc.setColumnNumber(in.getColumnNumber());
/* 119 */     return loc;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\exception\LocatableWebServiceException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */