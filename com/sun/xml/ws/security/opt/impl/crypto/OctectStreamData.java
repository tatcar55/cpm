/*    */ package com.sun.xml.ws.security.opt.impl.crypto;
/*    */ 
/*    */ import javax.xml.crypto.Data;
/*    */ import javax.xml.stream.XMLStreamException;
/*    */ import javax.xml.stream.XMLStreamWriter;
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
/*    */ public class OctectStreamData
/*    */   implements Data
/*    */ {
/* 59 */   private String data = null;
/*    */   
/*    */   public OctectStreamData(String data) {
/* 62 */     this.data = data;
/*    */   }
/*    */   
/*    */   public void write(XMLStreamWriter writer) throws XMLStreamException {
/* 66 */     writer.writeCharacters(this.data);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\crypto\OctectStreamData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */