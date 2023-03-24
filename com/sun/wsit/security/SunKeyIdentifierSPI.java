/*    */ package com.sun.wsit.security;
/*    */ 
/*    */ import com.sun.xml.wss.core.reference.KeyIdentifierSPI;
/*    */ import java.io.IOException;
/*    */ import java.security.cert.X509Certificate;
/*    */ import sun.security.util.DerInputStream;
/*    */ import sun.security.util.DerValue;
/*    */ import sun.security.x509.KeyIdentifier;
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
/*    */ public class SunKeyIdentifierSPI
/*    */   extends KeyIdentifierSPI
/*    */ {
/*    */   public byte[] getSubjectKeyIdentifier(X509Certificate cert) throws KeyIdentifierSPI.KeyIdentifierSPIException {
/* 56 */     byte[] subjectKeyIdentifier = cert.getExtensionValue("2.5.29.14");
/*    */     
/* 58 */     if (subjectKeyIdentifier == null) {
/* 59 */       return null;
/*    */     }
/*    */     try {
/* 62 */       KeyIdentifier keyId = null;
/*    */       
/* 64 */       DerValue derVal = new DerValue((new DerInputStream(subjectKeyIdentifier)).getOctetString());
/*    */ 
/*    */       
/* 67 */       keyId = new KeyIdentifier(derVal.getOctetString());
/* 68 */       return keyId.getIdentifier();
/* 69 */     } catch (NoClassDefFoundError ncde) {
/*    */ 
/*    */       
/* 72 */       byte[] dest = new byte[subjectKeyIdentifier.length - 4];
/* 73 */       System.arraycopy(subjectKeyIdentifier, 4, dest, 0, subjectKeyIdentifier.length - 4);
/*    */       
/* 75 */       return dest;
/*    */     }
/* 77 */     catch (IOException e) {
/*    */       
/* 79 */       throw new KeyIdentifierSPI.KeyIdentifierSPIException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\wsit\security\SunKeyIdentifierSPI.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */