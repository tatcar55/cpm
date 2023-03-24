/*    */ package com.sun.xml.ws.message;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class AttachmentSetImpl
/*    */   implements AttachmentSet
/*    */ {
/* 60 */   private final ArrayList<Attachment> attList = new ArrayList<Attachment>();
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
/* 72 */     for (Attachment a : base)
/* 73 */       add(a); 
/*    */   }
/*    */   
/*    */   public Attachment get(String contentId) {
/* 77 */     for (int i = this.attList.size() - 1; i >= 0; i--) {
/* 78 */       Attachment a = this.attList.get(i);
/* 79 */       if (a.getContentId().equals(contentId))
/* 80 */         return a; 
/*    */     } 
/* 82 */     return null;
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 86 */     return this.attList.isEmpty();
/*    */   }
/*    */   
/*    */   public void add(Attachment att) {
/* 90 */     this.attList.add(att);
/*    */   }
/*    */   
/*    */   public Iterator<Attachment> iterator() {
/* 94 */     return this.attList.iterator();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\AttachmentSetImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */