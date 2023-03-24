/*     */ package com.sun.xml.rpc.encoding;
/*     */ 
/*     */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*     */ import com.sun.xml.rpc.soap.SOAPEncodingConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.Stack;
/*     */ import javax.xml.soap.SOAPMessage;
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
/*     */ public class SOAPSerializationContext
/*     */ {
/*     */   protected HashMap map;
/*     */   protected Map properties;
/*     */   protected LinkedList list;
/*     */   protected String prefix;
/*     */   protected long next;
/*  54 */   protected Stack encodingStyleContext = new Stack();
/*  55 */   protected String curEncodingStyle = null;
/*     */   
/*     */   protected Set activeObjects;
/*     */   protected SOAPMessage message;
/*  59 */   private SOAPVersion soapVer = SOAPVersion.SOAP_11;
/*     */   
/*     */   private void init(SOAPVersion ver) {
/*  62 */     this.soapEncodingConstants = SOAPConstantsFactory.getSOAPEncodingConstants(ver);
/*     */     
/*  64 */     this.soapVer = ver;
/*     */   }
/*     */   private SOAPEncodingConstants soapEncodingConstants;
/*     */   public SOAPSerializationContext() {
/*  68 */     this(null);
/*     */   }
/*     */   
/*     */   public SOAPSerializationContext(String prefix) {
/*  72 */     this(prefix, SOAPVersion.SOAP_11);
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
/*     */   public SOAPSerializationState registerObject(Object obj, ReferenceableSerializer serializer) {
/*  92 */     MapKey key = new MapKey(obj);
/*  93 */     SOAPSerializationState state = (SOAPSerializationState)this.map.get(key);
/*     */     
/*  95 */     if (state == null) {
/*  96 */       state = new SOAPSerializationState(obj, nextID(), serializer);
/*  97 */       this.map.put(key, state);
/*  98 */       this.list.add(state);
/*     */     } 
/*     */     
/* 101 */     return state;
/*     */   }
/*     */   
/*     */   public boolean isRegistered(Object obj) {
/* 105 */     return (this.map.get(new MapKey(obj)) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPSerializationState lookupObject(Object obj) {
/* 110 */     MapKey key = new MapKey(obj);
/* 111 */     return (SOAPSerializationState)this.map.get(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeMultiRefObjects(XMLWriter writer) {
/* 116 */     while (!this.list.isEmpty()) {
/* 117 */       SOAPSerializationState state = this.list.removeFirst();
/*     */       
/* 119 */       Object obj = state.getObject();
/* 120 */       ReferenceableSerializer ser = state.getSerializer();
/* 121 */       ser.serializeInstance(obj, null, true, writer, this);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String nextID() {
/* 126 */     return this.prefix + this.next++;
/*     */   }
/*     */   
/*     */   private static class MapKey {
/*     */     Object obj;
/*     */     
/*     */     public MapKey(Object obj) {
/* 133 */       this.obj = obj;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 137 */       if (!(o instanceof MapKey)) {
/* 138 */         return false;
/*     */       }
/*     */       
/* 141 */       return (this.obj == ((MapKey)o).obj);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 145 */       return System.identityHashCode(this.obj);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean setImplicitEncodingStyle(String newEncodingStyle) {
/* 150 */     if (newEncodingStyle == this.curEncodingStyle || newEncodingStyle.equals(this.curEncodingStyle))
/*     */     {
/* 152 */       return false;
/*     */     }
/* 154 */     this.encodingStyleContext.push(newEncodingStyle);
/* 155 */     initEncodingStyleInfo();
/* 156 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean pushEncodingStyle(String newEncodingStyle, XMLWriter writer) throws Exception {
/* 162 */     if (newEncodingStyle == this.curEncodingStyle || newEncodingStyle.equals(this.curEncodingStyle))
/*     */     {
/* 164 */       return false;
/*     */     }
/* 166 */     writer.writeAttribute(this.soapEncodingConstants.getQNameEnvelopeEncodingStyle(), newEncodingStyle);
/*     */ 
/*     */     
/* 169 */     this.encodingStyleContext.push(newEncodingStyle);
/* 170 */     initEncodingStyleInfo();
/* 171 */     return true;
/*     */   }
/*     */   
/*     */   public void popEncodingStyle() {
/* 175 */     this.encodingStyleContext.pop();
/* 176 */     initEncodingStyleInfo();
/*     */   }
/*     */   
/*     */   private void initEncodingStyleInfo() {
/* 180 */     if (this.encodingStyleContext.empty()) {
/* 181 */       this.curEncodingStyle = null;
/*     */     } else {
/* 183 */       this.curEncodingStyle = this.encodingStyleContext.peek();
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getEncodingStyle() {
/* 188 */     return this.curEncodingStyle;
/*     */   }
/*     */   
/*     */   public void setProperty(String key, Object value) {
/* 192 */     this.properties.put(key, value);
/*     */   }
/*     */   
/*     */   public Object getProperty(String key) {
/* 196 */     return this.properties.get(key);
/*     */   }
/*     */   
/*     */   public void removeProperty(String key) {
/* 200 */     this.properties.remove(key);
/*     */   }
/*     */   
/*     */   public void beginFragment() {
/* 204 */     this.activeObjects = new HashSet();
/*     */   }
/*     */   
/*     */   public void beginSerializing(Object obj) throws SerializationException {
/* 208 */     if (obj != null && this.activeObjects != null) {
/* 209 */       if (this.activeObjects.contains(obj)) {
/* 210 */         throw new SerializationException("soap.circularReferenceDetected", new Object[] { obj });
/*     */       }
/*     */ 
/*     */       
/* 214 */       this.activeObjects.add(obj);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void doneSerializing(Object obj) throws SerializationException {
/* 220 */     if (obj != null && this.activeObjects != null) {
/* 221 */       this.activeObjects.remove(obj);
/*     */     }
/*     */   }
/*     */   
/*     */   public void endFragment() {
/* 226 */     this.activeObjects = null;
/*     */   }
/*     */   
/*     */   public void setMessage(SOAPMessage m) {
/* 230 */     this.message = m;
/*     */   }
/*     */   
/*     */   public SOAPMessage getMessage() {
/* 234 */     return this.message;
/*     */   }
/*     */   
/*     */   public SOAPVersion getSOAPVersion() {
/* 238 */     return this.soapVer;
/*     */   }
/*     */   public SOAPSerializationContext(String prefix, SOAPVersion ver) {
/* 241 */     this.soapEncodingConstants = null;
/*     */     init(ver);
/*     */     if (prefix == null)
/*     */       prefix = "ID"; 
/*     */     this.map = new HashMap<Object, Object>();
/*     */     this.properties = new HashMap<Object, Object>();
/*     */     this.list = new LinkedList();
/*     */     this.prefix = prefix;
/*     */     this.next = 1L;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\SOAPSerializationContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */