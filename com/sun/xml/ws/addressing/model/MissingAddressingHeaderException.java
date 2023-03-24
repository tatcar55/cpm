/*    */ package com.sun.xml.ws.addressing.model;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.istack.Nullable;
/*    */ import com.sun.xml.ws.api.message.Packet;
/*    */ import com.sun.xml.ws.resources.AddressingMessages;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.ws.WebServiceException;
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
/*    */ public class MissingAddressingHeaderException
/*    */   extends WebServiceException
/*    */ {
/*    */   private final QName name;
/*    */   private final transient Packet packet;
/*    */   
/*    */   public MissingAddressingHeaderException(@NotNull QName name) {
/* 65 */     this(name, null);
/*    */   }
/*    */   
/*    */   public MissingAddressingHeaderException(@NotNull QName name, @Nullable Packet p) {
/* 69 */     super(AddressingMessages.MISSING_HEADER_EXCEPTION(name));
/* 70 */     this.name = name;
/* 71 */     this.packet = p;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public QName getMissingHeaderQName() {
/* 81 */     return this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Packet getPacket() {
/* 91 */     return this.packet;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\addressing\model\MissingAddressingHeaderException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */