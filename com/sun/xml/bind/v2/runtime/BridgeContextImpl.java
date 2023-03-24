/*    */ package com.sun.xml.bind.v2.runtime;
/*    */ 
/*    */ import com.sun.xml.bind.api.BridgeContext;
/*    */ import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallerImpl;
/*    */ import javax.xml.bind.JAXBException;
/*    */ import javax.xml.bind.ValidationEventHandler;
/*    */ import javax.xml.bind.attachment.AttachmentMarshaller;
/*    */ import javax.xml.bind.attachment.AttachmentUnmarshaller;
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
/*    */ public final class BridgeContextImpl
/*    */   extends BridgeContext
/*    */ {
/*    */   public final UnmarshallerImpl unmarshaller;
/*    */   public final MarshallerImpl marshaller;
/*    */   
/*    */   BridgeContextImpl(JAXBContextImpl context) {
/* 62 */     this.unmarshaller = context.createUnmarshaller();
/* 63 */     this.marshaller = context.createMarshaller();
/*    */   }
/*    */   
/*    */   public void setErrorHandler(ValidationEventHandler handler) {
/*    */     try {
/* 68 */       this.unmarshaller.setEventHandler(handler);
/* 69 */       this.marshaller.setEventHandler(handler);
/* 70 */     } catch (JAXBException e) {
/*    */       
/* 72 */       throw new Error(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setAttachmentMarshaller(AttachmentMarshaller m) {
/* 77 */     this.marshaller.setAttachmentMarshaller(m);
/*    */   }
/*    */   
/*    */   public void setAttachmentUnmarshaller(AttachmentUnmarshaller u) {
/* 81 */     this.unmarshaller.setAttachmentUnmarshaller(u);
/*    */   }
/*    */   
/*    */   public AttachmentMarshaller getAttachmentMarshaller() {
/* 85 */     return this.marshaller.getAttachmentMarshaller();
/*    */   }
/*    */   
/*    */   public AttachmentUnmarshaller getAttachmentUnmarshaller() {
/* 89 */     return this.unmarshaller.getAttachmentUnmarshaller();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\BridgeContextImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */