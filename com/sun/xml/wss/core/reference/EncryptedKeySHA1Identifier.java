/*    */ package com.sun.xml.wss.core.reference;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*    */ import com.sun.xml.wss.XWSSecurityException;
/*    */ import com.sun.xml.wss.impl.SecurityHeaderException;
/*    */ import com.sun.xml.wss.impl.misc.Base64;
/*    */ import java.util.logging.Level;
/*    */ import javax.xml.soap.SOAPElement;
/*    */ import org.w3c.dom.Document;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EncryptedKeySHA1Identifier
/*    */   extends KeyIdentifier
/*    */ {
/* 72 */   private String encodingType = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary";
/*    */   
/* 74 */   private String valueType = "http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKeySHA1";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EncryptedKeySHA1Identifier(Document doc) throws XWSSecurityException {
/* 81 */     super(doc);
/*    */     
/* 83 */     setAttribute("EncodingType", this.encodingType);
/* 84 */     setAttribute("ValueType", this.valueType);
/*    */   }
/*    */ 
/*    */   
/*    */   public EncryptedKeySHA1Identifier(SOAPElement element) throws XWSSecurityException {
/* 89 */     super(element);
/*    */   }
/*    */   
/*    */   public byte[] getDecodedBase64EncodedValue() throws XWSSecurityException {
/*    */     try {
/* 94 */       return Base64.decode(getReferenceValue());
/* 95 */     } catch (Base64DecodingException e) {
/* 96 */       log.log(Level.SEVERE, "WSS0144.unableto.decode.base64.data", new Object[] { e.getMessage() });
/*    */       
/* 98 */       throw new SecurityHeaderException("Unable to decode Base64 encoded data", e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\reference\EncryptedKeySHA1Identifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */