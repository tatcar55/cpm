/*    */ package com.sun.xml.ws.security.opt.impl.attachment;
/*    */ 
/*    */ import com.sun.xml.ws.api.message.Attachment;
/*    */ import com.sun.xml.ws.api.message.AttachmentSet;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AttachmentSetImpl
/*    */   implements AttachmentSet
/*    */ {
/* 54 */   private final ArrayList<Attachment> attList = new ArrayList<Attachment>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AttachmentSetImpl() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AttachmentSetImpl(Iterable<Attachment> base) {
/* 66 */     for (Attachment a : base)
/* 67 */       add(a); 
/*    */   }
/*    */   
/*    */   public Attachment get(String contentId) {
/* 71 */     for (int i = this.attList.size() - 1; i >= 0; i--) {
/* 72 */       Attachment a = this.attList.get(i);
/* 73 */       if (a.getContentId().equals(contentId))
/* 74 */         return a; 
/*    */     } 
/* 76 */     return null;
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 80 */     return this.attList.isEmpty();
/*    */   }
/*    */   
/*    */   public void add(Attachment att) {
/* 84 */     this.attList.add(att);
/*    */   }
/*    */   
/*    */   public Iterator<Attachment> iterator() {
/* 88 */     return this.attList.iterator();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\attachment\AttachmentSetImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */