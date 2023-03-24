/*    */ package com.sun.xml.security.core.xenc;
/*    */ 
/*    */ import com.sun.xml.ws.security.opt.impl.enc.CryptoProcessor;
/*    */ import javax.activation.CommandMap;
/*    */ import javax.activation.DataHandler;
/*    */ import javax.activation.MailcapCommandMap;
/*    */ import javax.xml.bind.annotation.adapters.XmlAdapter;
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
/*    */ public class CVAdapter
/*    */   extends XmlAdapter<DataHandler, byte[]>
/*    */ {
/*    */   private CryptoProcessor cp;
/*    */   
/*    */   static {
/* 64 */     CommandMap map = CommandMap.getDefaultCommandMap();
/* 65 */     if (map instanceof MailcapCommandMap) {
/* 66 */       MailcapCommandMap mailMap = (MailcapCommandMap)map;
/* 67 */       mailMap.addMailcap("application/ciphervalue;;x-java-content-handler=com.sun.xml.ws.security.opt.impl.util.CVDataHandler");
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public CVAdapter() {}
/*    */ 
/*    */   
/*    */   public CVAdapter(CryptoProcessor cp) {
/* 77 */     this.cp = cp;
/*    */   }
/*    */   
/*    */   public DataHandler marshal(byte[] value) {
/* 81 */     return new DataHandler(this.cp, "application/ciphervalue");
/*    */   }
/*    */   
/*    */   public byte[] unmarshal(DataHandler dh) {
/* 85 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\security\core\xenc\CVAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */