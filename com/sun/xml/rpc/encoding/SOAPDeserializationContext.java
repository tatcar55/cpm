/*     */ package com.sun.xml.rpc.encoding;
/*     */ 
/*     */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*     */ import com.sun.xml.rpc.soap.SOAPNamespaceConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.streaming.Attributes;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import com.sun.xml.rpc.util.xml.XmlUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class SOAPDeserializationContext
/*     */ {
/*  53 */   protected Stack encodingStyleContext = new Stack();
/*  54 */   protected String curEncodingStyle = null;
/*     */   protected boolean isSOAPEncodingStyle = false;
/*  56 */   protected List encodingStyleURIs = null;
/*     */   protected SOAPMessage message;
/*  58 */   protected Map stateIds = new HashMap<Object, Object>();
/*     */   
/*  60 */   private SOAPVersion soapVer = SOAPVersion.SOAP_11;
/*  61 */   private SOAPNamespaceConstants soapNamespaceConstants = null; private Hashtable idTable;
/*     */   
/*     */   private void init(SOAPVersion ver) {
/*  64 */     this.soapNamespaceConstants = SOAPConstantsFactory.getSOAPNamespaceConstants(ver);
/*     */     
/*  66 */     this.soapVer = ver;
/*     */   }
/*     */   private ArrayList idResolver; private int idResolverLength;
/*     */   public SOAPDeserializationContext() {
/*  70 */     this(SOAPVersion.SOAP_11);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPDeserializationState getStateFor(String id) {
/*  79 */     if (id == null) {
/*  80 */       return null;
/*     */     }
/*     */     
/*  83 */     SOAPDeserializationState elementState = (SOAPDeserializationState)this.stateIds.get(id);
/*     */     
/*  85 */     if (elementState == null) {
/*  86 */       elementState = new SOAPDeserializationState();
/*  87 */       this.stateIds.put(id, elementState);
/*     */     } 
/*  89 */     return elementState;
/*     */   }
/*     */ 
/*     */   
/*     */   public void deserializeMultiRefObjects(XMLReader reader) {
/*     */     try {
/*  95 */       while (reader.nextElementContent() == 1) {
/*  96 */         String id = reader.getAttributes().getValue("", "id");
/*  97 */         if (id == null) {
/*  98 */           throw new MissingTrailingBlockIDException("soap.missingTrailingBlockID");
/*     */         }
/*     */         
/* 101 */         SOAPDeserializationState elementState = getStateFor(id);
/* 102 */         elementState.deserialize(null, reader, this);
/*     */       } 
/* 104 */     } catch (MissingTrailingBlockIDException e) {
/* 105 */       throw e;
/* 106 */     } catch (JAXRPCExceptionBase e) {
/* 107 */       throw new DeserializationException(e);
/* 108 */     } catch (Exception e) {
/* 109 */       throw new DeserializationException(new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void doneDeserializing() {
/* 115 */     for (Iterator<SOAPDeserializationState> iter = this.stateIds.values().iterator(); iter.hasNext(); ) {
/* 116 */       SOAPDeserializationState state = iter.next();
/*     */       
/* 118 */       state.promoteToCompleteOrFail();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setMessage(SOAPMessage m) {
/* 123 */     this.message = m;
/*     */   }
/*     */   
/*     */   public SOAPMessage getMessage() {
/* 127 */     return this.message;
/*     */   }
/*     */   
/*     */   public void pushEncodingStyle(String newEncodingStyle) {
/* 131 */     this.encodingStyleContext.push(newEncodingStyle);
/* 132 */     initEncodingStyleInfo();
/*     */   }
/*     */   
/*     */   public void popEncodingStyle() {
/* 136 */     this.encodingStyleContext.pop();
/* 137 */     initEncodingStyleInfo();
/*     */   }
/*     */   
/*     */   public String getEncodingStyle() {
/* 141 */     return this.curEncodingStyle;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processEncodingStyle(XMLReader reader) throws Exception {
/* 146 */     Attributes attrs = reader.getAttributes();
/* 147 */     String newEncodingStyle = attrs.getValue(this.soapNamespaceConstants.getEnvelope(), this.soapNamespaceConstants.getAttrEncodingStyle());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     if (newEncodingStyle == null) {
/* 153 */       return false;
/*     */     }
/* 155 */     pushEncodingStyle(newEncodingStyle);
/* 156 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void verifyEncodingStyle(String expectedEncodingStyle) {
/* 161 */     if (expectedEncodingStyle == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 166 */     if (expectedEncodingStyle == this.soapNamespaceConstants.getEncoding() || expectedEncodingStyle.equals(this.soapNamespaceConstants.getEncoding())) {
/*     */ 
/*     */       
/* 169 */       if (this.isSOAPEncodingStyle) {
/*     */         return;
/*     */       }
/* 172 */     } else if (this.encodingStyleURIs == null) {
/* 173 */       if (this.curEncodingStyle.startsWith(expectedEncodingStyle)) {
/*     */         return;
/*     */       }
/*     */     } else {
/* 177 */       for (int i = 0; i < this.encodingStyleURIs.size(); i++) {
/* 178 */         String uri = this.encodingStyleURIs.get(i);
/* 179 */         if (uri.startsWith(expectedEncodingStyle)) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 185 */     throw new DeserializationException("soap.unexpectedEncodingStyle", new Object[] { expectedEncodingStyle, this.curEncodingStyle });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initEncodingStyleInfo() {
/* 191 */     this.curEncodingStyle = this.encodingStyleContext.peek();
/*     */     
/* 193 */     if (this.curEncodingStyle.indexOf(' ') == -1) {
/* 194 */       this.encodingStyleURIs = null;
/* 195 */       this.isSOAPEncodingStyle = this.curEncodingStyle.startsWith(this.soapNamespaceConstants.getEncoding());
/*     */     }
/*     */     else {
/*     */       
/* 199 */       this.encodingStyleURIs = XmlUtil.parseTokenList(this.curEncodingStyle);
/* 200 */       this.isSOAPEncodingStyle = false;
/* 201 */       for (int i = 0; i < this.encodingStyleURIs.size(); i++) {
/* 202 */         String uri = this.encodingStyleURIs.get(i);
/* 203 */         if (uri.startsWith(this.soapNamespaceConstants.getEncoding())) {
/* 204 */           this.isSOAPEncodingStyle = true;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public SOAPVersion getSOAPVersion() {
/* 212 */     return this.soapVer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addXSDIdObjectSerializer(String id, Object obj) {
/* 217 */     if (this.idTable == null)
/* 218 */       this.idTable = new Hashtable<Object, Object>(); 
/* 219 */     if (!this.idTable.containsKey(id)) {
/* 220 */       this.idTable.put(id, obj);
/*     */     } else {
/* 222 */       throw new DeserializationException("xsd.duplicateID", new Object[] { id });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getXSDIdObjectSerializer(String id) {
/* 228 */     if (this.idTable == null)
/* 229 */       return null; 
/* 230 */     return this.idTable.get(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addPostDeserializationAction(PostDeserializationAction action) {
/* 235 */     if (this.idResolver == null)
/* 236 */       this.idResolver = new ArrayList(); 
/* 237 */     this.idResolver.add(action);
/*     */   }
/*     */ 
/*     */   
/*     */   public void runPostDeserializationAction() {
/* 242 */     if (this.idResolver != null) {
/* 243 */       Iterator<PostDeserializationAction> iter = this.idResolver.iterator();
/* 244 */       while (iter.hasNext()) {
/* 245 */         ((PostDeserializationAction)iter.next()).run(this);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public SOAPDeserializationContext(SOAPVersion ver) {
/* 251 */     this.idResolverLength = 0;
/*     */     init(ver);
/*     */     pushEncodingStyle("");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\SOAPDeserializationContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */