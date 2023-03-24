/*    */ package com.sun.xml.ws.security.opt.crypto.dsig.keyinfo;
/*    */ 
/*    */ import com.sun.xml.security.core.dsig.PGPDataType;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ import javax.xml.crypto.dsig.keyinfo.PGPData;
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
/*    */ @XmlRootElement(name = "PGPData", namespace = "http://www.w3.org/2000/09/xmldsig#")
/*    */ public class PGPData
/*    */   extends PGPDataType
/*    */   implements PGPData
/*    */ {
/*    */   public byte[] getKeyId() {
/* 64 */     return null;
/*    */   }
/*    */   
/*    */   public byte[] getKeyPacket() {
/* 68 */     return null;
/*    */   }
/*    */   
/*    */   public List getExternalElements() {
/* 72 */     return null;
/*    */   }
/*    */   
/*    */   public boolean isFeatureSupported(String string) {
/* 76 */     return false;
/*    */   }
/*    */   
/*    */   public void setContent(List<Object> content) {
/* 80 */     this.content = content;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\dsig\keyinfo\PGPData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */