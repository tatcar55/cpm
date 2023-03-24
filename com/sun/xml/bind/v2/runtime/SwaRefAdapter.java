/*    */ package com.sun.xml.bind.v2.runtime;
/*    */ 
/*    */ import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*    */ import javax.activation.DataHandler;
/*    */ import javax.xml.bind.annotation.adapters.XmlAdapter;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SwaRefAdapter
/*    */   extends XmlAdapter<String, DataHandler>
/*    */ {
/*    */   public DataHandler unmarshal(String cid) {
/* 75 */     AttachmentUnmarshaller au = (UnmarshallingContext.getInstance()).parent.getAttachmentUnmarshaller();
/*    */     
/* 77 */     return au.getAttachmentAsDataHandler(cid);
/*    */   }
/*    */   
/*    */   public String marshal(DataHandler data) {
/* 81 */     if (data == null) return null; 
/* 82 */     AttachmentMarshaller am = (XMLSerializer.getInstance()).attachmentMarshaller;
/*    */     
/* 84 */     return am.addSwaRefAttachment(data);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\SwaRefAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */