/*     */ package com.sun.xml.ws.security.opt.impl.incoming;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.AbstractCreatorProcessor;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferException;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferMark;
/*     */ import com.sun.xml.stream.buffer.stax.StreamReaderBufferCreator;
/*     */ import com.sun.xml.ws.security.opt.api.NamespaceContextInfo;
/*     */ import com.sun.xml.ws.security.opt.api.PolicyBuilder;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.api.TokenValidator;
/*     */ import com.sun.xml.ws.security.opt.api.tokens.UsernameToken;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.processor.UsernameTokenProcessor;
/*     */ import com.sun.xml.ws.security.opt.impl.util.SOAPUtil;
/*     */ import com.sun.xml.ws.security.opt.impl.util.XMLStreamReaderFactory;
/*     */ import com.sun.xml.wss.NonceManager;
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.FilterProcessingContext;
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import com.sun.xml.wss.impl.misc.DefaultSecurityEnvironmentImpl;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import com.sun.xml.wss.logging.impl.filter.LogStringsMessages;
/*     */ import java.io.OutputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.stream.StreamFilter;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UsernameTokenHeader
/*     */   implements UsernameToken, SecurityHeaderElement, TokenValidator, PolicyBuilder, NamespaceContextInfo, SecurityElementWriter
/*     */ {
/*  82 */   private static Logger log = Logger.getLogger("com.sun.xml.wss.logging.impl.filter", "com.sun.xml.wss.logging.impl.filter.LogStrings");
/*     */ 
/*     */ 
/*     */   
/*  86 */   private String localPart = null;
/*  87 */   private String namespaceURI = null;
/*  88 */   private String id = "";
/*     */   
/*  90 */   private XMLStreamBuffer mark = null;
/*  91 */   private UsernameTokenProcessor filter = new UsernameTokenProcessor();
/*     */   
/*  93 */   private AuthenticationTokenPolicy.UsernameTokenBinding utPolicy = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private HashMap<String, String> nsDecls;
/*     */ 
/*     */ 
/*     */   
/*     */   public UsernameTokenHeader(XMLStreamReader reader, StreamReaderBufferCreator creator, HashMap<String, String> nsDecls, XMLInputFactory staxIF) throws XMLStreamException, XMLStreamBufferException {
/* 102 */     this.localPart = reader.getLocalName();
/* 103 */     this.namespaceURI = reader.getNamespaceURI();
/* 104 */     this.id = reader.getAttributeValue("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
/*     */     
/* 106 */     this.mark = (XMLStreamBuffer)new XMLStreamBufferMark(nsDecls, (AbstractCreatorProcessor)creator);
/* 107 */     XMLStreamReader utReader = XMLStreamReaderFactory.createFilteredXMLStreamReader(reader, (StreamFilter)this.filter);
/* 108 */     creator.createElementFragment(utReader, true);
/* 109 */     this.nsDecls = nsDecls;
/*     */     
/* 111 */     this.utPolicy = new AuthenticationTokenPolicy.UsernameTokenBinding();
/* 112 */     this.utPolicy.setUUID(this.id);
/*     */     
/* 114 */     this.utPolicy.setUsername(this.filter.getUsername());
/* 115 */     this.utPolicy.setPassword(this.filter.getPassword());
/* 116 */     if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest".equals(this.filter.getPasswordType())) {
/* 117 */       this.utPolicy.setDigestOn(true);
/*     */     }
/* 119 */     if (this.filter.getNonce() != null) {
/* 120 */       this.utPolicy.setUseNonce(true);
/*     */     }
/* 122 */     if (this.filter.getCreated() != null) {
/* 123 */       this.utPolicy.setUseCreated(true);
/*     */     }
/*     */   }
/*     */   
/*     */   public void validate(ProcessingContext context) throws XWSSecurityException {
/* 128 */     boolean authenticated = false;
/* 129 */     if (this.filter.getPassword() == null && this.filter.getPasswordDigest() == null) {
/* 130 */       this.utPolicy.setNoPassword(true);
/*     */     }
/* 132 */     if (this.filter.getSalt() != null) {
/* 133 */       this.utPolicy.setNoPassword(false);
/*     */     }
/*     */     
/* 136 */     if (this.filter.getPassword() == null && this.filter.getCreated() == null && "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest".equals(this.filter.getPasswordType()))
/*     */     {
/* 138 */       throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY, "Cannot validate Password Digest since Creation Time was not Specified", null, true);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     if (this.filter.getNonce() != null || this.filter.getCreated() != null) {
/* 145 */       validateNonceOrCreated(context);
/*     */     }
/*     */     
/* 148 */     if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest".equals(this.filter.getPasswordType())) {
/* 149 */       authenticated = context.getSecurityEnvironment().authenticateUser(context.getExtraneousProperties(), this.filter.getUsername(), this.filter.getPasswordDigest(), this.filter.getNonce(), this.filter.getCreated());
/*     */ 
/*     */       
/* 152 */       if (!authenticated) {
/* 153 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1408_FAILED_SENDER_AUTHENTICATION());
/* 154 */         throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_FAILED_AUTHENTICATION, "Authentication of Username Password Token Failed", null, true);
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 159 */     else if (this.filter.getPassword() != null) {
/* 160 */       authenticated = context.getSecurityEnvironment().authenticateUser(context.getExtraneousProperties(), this.filter.getUsername(), this.filter.getPassword());
/*     */       
/* 162 */       if (!authenticated) {
/* 163 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1408_FAILED_SENDER_AUTHENTICATION());
/* 164 */         throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_FAILED_AUTHENTICATION, "Authentication of Username Password Token Failed", null, true);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 177 */     context.getSecurityEnvironment().updateOtherPartySubject(DefaultSecurityEnvironmentImpl.getSubject((FilterProcessingContext)context), this.filter.getUsername(), this.filter.getPassword());
/*     */   }
/*     */ 
/*     */   
/*     */   public WSSPolicy getPolicy() {
/* 182 */     return (WSSPolicy)this.utPolicy;
/*     */   }
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/* 186 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getId() {
/* 190 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 194 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 198 */     return this.namespaceURI;
/*     */   }
/*     */   
/*     */   public String getLocalPart() {
/* 202 */     return this.localPart;
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 207 */     return (XMLStreamReader)this.mark.readAsXMLStreamReader();
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream os) {
/* 211 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/* 215 */     this.mark.writeToXMLStreamWriter(streamWriter);
/*     */   }
/*     */   
/*     */   public String getUsernameValue() {
/* 219 */     return this.filter.getUsername();
/*     */   }
/*     */   
/*     */   public void setUsernameValue(String username) {
/* 223 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getPasswordValue() {
/* 227 */     return this.filter.getPassword();
/*     */   }
/*     */   
/*     */   public void setPasswordValue(String passwd) {
/* 231 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   public void setSalt(String receivedSalt) {
/* 234 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   public String getSalt() {
/* 237 */     return this.filter.getSalt();
/*     */   }
/*     */   public void setIterations(int iterate) {
/* 240 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   public String getIterations() {
/* 243 */     return this.filter.getIterations();
/*     */   }
/*     */   public HashMap<String, String> getInscopeNSContext() {
/* 246 */     return this.nsDecls;
/*     */   }
/*     */   public void writeTo(XMLStreamWriter streamWriter, HashMap props) throws XMLStreamException {
/* 249 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   private void validateNonceOrCreated(ProcessingContext context) throws XWSSecurityException {
/* 253 */     if (this.filter.getCreated() != null) {
/* 254 */       context.getSecurityEnvironment().validateCreationTime(context.getExtraneousProperties(), this.filter.getCreated(), 300000L, 300000L);
/*     */     }
/*     */ 
/*     */     
/* 258 */     if (this.filter.getNonce() != null)
/*     */       try {
/* 260 */         if (!context.getSecurityEnvironment().validateAndCacheNonce(context.getExtraneousProperties(), this.filter.getNonce(), this.filter.getCreated(), 900000L))
/*     */         {
/* 262 */           XWSSecurityException xwse = new XWSSecurityException("Invalid/Repeated Nonce value for Username Token");
/*     */ 
/*     */           
/* 265 */           throw DefaultSecurityEnvironmentImpl.newSOAPFaultException(MessageConstants.WSSE_FAILED_AUTHENTICATION, "Invalid/Repeated Nonce value for Username Token", xwse);
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 270 */       catch (com.sun.xml.wss.NonceManager.NonceException ex) {
/* 271 */         throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_FAILED_AUTHENTICATION, "Invalid/Repeated Nonce value for Username Token", ex, true);
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\UsernameTokenHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */