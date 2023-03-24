/*    */ package com.sun.xml.bind.util;
/*    */ 
/*    */ import com.sun.xml.bind.ValidationEventLocatorEx;
/*    */ import javax.xml.bind.helpers.ValidationEventLocatorImpl;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ValidationEventLocatorExImpl
/*    */   extends ValidationEventLocatorImpl
/*    */   implements ValidationEventLocatorEx
/*    */ {
/*    */   private final String fieldName;
/*    */   
/*    */   public ValidationEventLocatorExImpl(Object target, String fieldName) {
/* 59 */     super(target);
/* 60 */     this.fieldName = fieldName;
/*    */   }
/*    */   
/*    */   public String getFieldName() {
/* 64 */     return this.fieldName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 71 */     StringBuffer buf = new StringBuffer();
/* 72 */     buf.append("[url=");
/* 73 */     buf.append(getURL());
/* 74 */     buf.append(",line=");
/* 75 */     buf.append(getLineNumber());
/* 76 */     buf.append(",column=");
/* 77 */     buf.append(getColumnNumber());
/* 78 */     buf.append(",node=");
/* 79 */     buf.append(getNode());
/* 80 */     buf.append(",object=");
/* 81 */     buf.append(getObject());
/* 82 */     buf.append(",field=");
/* 83 */     buf.append(getFieldName());
/* 84 */     buf.append("]");
/*    */     
/* 86 */     return buf.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bin\\util\ValidationEventLocatorExImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */