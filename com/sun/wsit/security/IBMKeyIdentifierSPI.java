/*    */ package com.sun.wsit.security;
/*    */ 
/*    */ import com.ibm.security.util.DerInputStream;
/*    */ import com.ibm.security.util.DerValue;
/*    */ import com.ibm.security.x509.KeyIdentifier;
/*    */ import com.sun.xml.wss.core.reference.KeyIdentifierSPI;
/*    */ import java.io.IOException;
/*    */ import java.security.cert.X509Certificate;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IBMKeyIdentifierSPI
/*    */   extends KeyIdentifierSPI
/*    */ {
/*    */   public byte[] getSubjectKeyIdentifier(X509Certificate paramX509Certificate) throws KeyIdentifierSPI.KeyIdentifierSPIException {
/* 22 */     byte[] arrayOfByte = paramX509Certificate.getExtensionValue("2.5.29.14");
/*    */     
/* 24 */     if (arrayOfByte == null) {
/* 25 */       return null;
/*    */     }
/*    */     try {
/* 28 */       KeyIdentifier keyIdentifier = null;
/*    */       
/* 30 */       DerValue derValue = new DerValue((new DerInputStream(arrayOfByte)).getOctetString());
/*    */ 
/*    */       
/* 33 */       keyIdentifier = new KeyIdentifier(derValue.getOctetString());
/* 34 */       return keyIdentifier.getIdentifier();
/* 35 */     } catch (NoClassDefFoundError noClassDefFoundError) {
/*    */ 
/*    */       
/* 38 */       byte[] arrayOfByte1 = new byte[arrayOfByte.length - 4];
/* 39 */       System.arraycopy(arrayOfByte, 4, arrayOfByte1, 0, arrayOfByte.length - 4);
/*    */       
/* 41 */       return arrayOfByte1;
/*    */     }
/* 43 */     catch (IOException iOException) {
/*    */       
/* 45 */       throw new KeyIdentifierSPI.KeyIdentifierSPIException(iOException);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\wsit\security\IBMKeyIdentifierSPI.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */