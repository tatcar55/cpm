/*     */ package com.sun.xml.ws.security.opt.impl.incoming;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.xml.stream.buffer.AbstractCreatorProcessor;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferException;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferMark;
/*     */ import com.sun.xml.stream.buffer.stax.StreamReaderBufferCreator;
/*     */ import com.sun.xml.stream.buffer.stax.StreamReaderBufferProcessor;
/*     */ import com.sun.xml.ws.security.opt.api.NamespaceContextInfo;
/*     */ import com.sun.xml.ws.security.opt.api.PolicyBuilder;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.api.TokenValidator;
/*     */ import com.sun.xml.ws.security.opt.api.keyinfo.KerberosBinarySecurityToken;
/*     */ import com.sun.xml.ws.security.opt.impl.util.StreamUtil;
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import com.sun.xml.wss.logging.impl.opt.LogStringsMessages;
/*     */ import java.io.OutputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.jvnet.staxex.Base64Data;
/*     */ import org.jvnet.staxex.XMLStreamReaderEx;
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
/*     */ 
/*     */ public class KerberosBinarySecurityToken
/*     */   implements KerberosBinarySecurityToken, SecurityHeaderElement, PolicyBuilder, TokenValidator, NamespaceContextInfo, SecurityElementWriter
/*     */ {
/*  91 */   private String valueType = null;
/*  92 */   private String encodingType = null;
/*  93 */   private String id = "";
/*  94 */   private XMLStreamBuffer mark = null;
/*  95 */   private String namespaceURI = null;
/*  96 */   private String localPart = null;
/*     */   
/*  98 */   private AuthenticationTokenPolicy.KerberosTokenBinding ktPolicy = null;
/*     */   private HashMap<String, String> nsDecls;
/* 100 */   private byte[] bstValue = null;
/*     */   
/* 102 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt", "com.sun.xml.wss.logging.impl.opt.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KerberosBinarySecurityToken(XMLStreamReader reader, StreamReaderBufferCreator creator, HashMap<String, String> nsDecl, XMLInputFactory staxIF) throws XMLStreamException, XMLStreamBufferException {
/* 109 */     this.localPart = reader.getLocalName();
/* 110 */     this.namespaceURI = reader.getNamespaceURI();
/* 111 */     this.id = reader.getAttributeValue("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
/* 112 */     this.valueType = reader.getAttributeValue(null, "ValueType");
/* 113 */     this.encodingType = reader.getAttributeValue(null, "EncodingType");
/* 114 */     this.mark = (XMLStreamBuffer)new XMLStreamBufferMark(nsDecl, (AbstractCreatorProcessor)creator);
/* 115 */     creator.createElementFragment(reader, true);
/* 116 */     this.ktPolicy = new AuthenticationTokenPolicy.KerberosTokenBinding();
/* 117 */     this.ktPolicy.setUUID(this.id);
/* 118 */     this.ktPolicy.setValueType(this.valueType);
/* 119 */     this.ktPolicy.setEncodingType(this.encodingType);
/* 120 */     this.nsDecls = nsDecl;
/* 121 */     StreamReaderBufferProcessor streamReaderBufferProcessor = this.mark.readAsXMLStreamReader();
/* 122 */     streamReaderBufferProcessor.next();
/* 123 */     digestBST((XMLStreamReader)streamReaderBufferProcessor);
/*     */   }
/*     */   
/*     */   public String getValueType() {
/* 127 */     return this.valueType;
/*     */   }
/*     */   
/*     */   public String getEncodingType() {
/* 131 */     return this.encodingType;
/*     */   }
/*     */   
/*     */   public byte[] getTokenValue() {
/* 135 */     return this.bstValue;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 139 */     return this.id;
/*     */   }
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/* 143 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 147 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 151 */     return this.namespaceURI;
/*     */   }
/*     */   
/*     */   public String getLocalPart() {
/* 155 */     return this.localPart;
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 159 */     return (XMLStreamReader)this.mark.readAsXMLStreamReader();
/*     */   }
/*     */   
/*     */   public WSSPolicy getPolicy() {
/* 163 */     return (WSSPolicy)this.ktPolicy;
/*     */   }
/*     */ 
/*     */   
/*     */   public void validate(ProcessingContext context) throws XWSSecurityException {}
/*     */ 
/*     */   
/*     */   public HashMap<String, String> getInscopeNSContext() {
/* 171 */     return this.nsDecls;
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/* 175 */     this.mark.writeToXMLStreamWriter(streamWriter);
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter, HashMap props) throws XMLStreamException {
/* 179 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream os) {
/* 183 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   private void digestBST(XMLStreamReader reader) throws XMLStreamException {
/* 187 */     if (reader.getEventType() == 1) {
/* 188 */       reader.next();
/*     */     }
/* 190 */     if (reader.getEventType() == 4) {
/* 191 */       if (reader instanceof XMLStreamReaderEx) {
/* 192 */         CharSequence data = ((XMLStreamReaderEx)reader).getPCDATA();
/* 193 */         if (data instanceof Base64Data) {
/* 194 */           Base64Data binaryData = (Base64Data)data;
/* 195 */           this.bstValue = binaryData.getExact();
/*     */           return;
/*     */         } 
/*     */       } 
/*     */       try {
/* 200 */         this.bstValue = Base64.decode(StreamUtil.getCV(reader));
/*     */       }
/* 202 */       catch (Base64DecodingException ex) {
/* 203 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1604_ERROR_DECODING_BASE_64_DATA(ex));
/* 204 */         throw new XWSSecurityRuntimeException(LogStringsMessages.WSS_1604_ERROR_DECODING_BASE_64_DATA(ex));
/* 205 */       } catch (XMLStreamException ex) {
/* 206 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1604_ERROR_DECODING_BASE_64_DATA(ex));
/* 207 */         throw new XWSSecurityRuntimeException(LogStringsMessages.WSS_1604_ERROR_DECODING_BASE_64_DATA(ex));
/*     */       } 
/*     */     } else {
/* 210 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1603_ERROR_READING_STREAM(null));
/* 211 */       throw new XWSSecurityRuntimeException(LogStringsMessages.WSS_1603_ERROR_READING_STREAM(null));
/*     */     } 
/*     */     
/* 214 */     if (reader.getEventType() != 2)
/* 215 */       reader.next(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\KerberosBinarySecurityToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */