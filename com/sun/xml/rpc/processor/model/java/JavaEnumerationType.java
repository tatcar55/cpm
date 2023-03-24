/*    */ package com.sun.xml.rpc.processor.model.java;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JavaEnumerationType
/*    */   extends JavaType
/*    */ {
/*    */   public JavaEnumerationType() {}
/*    */   
/*    */   public JavaEnumerationType(String name, JavaType baseType, boolean present) {
/* 44 */     super(name, present, "null");
/* 45 */     this.baseType = baseType;
/*    */   }
/*    */   
/*    */   public JavaType getBaseType() {
/* 49 */     return this.baseType;
/*    */   }
/*    */   
/*    */   public void setBaseType(JavaType t) {
/* 53 */     this.baseType = t;
/*    */   }
/*    */   
/*    */   public void add(JavaEnumerationEntry e) {
/* 57 */     this.entries.add(e);
/*    */   }
/*    */   
/*    */   public Iterator getEntries() {
/* 61 */     return this.entries.iterator();
/*    */   }
/*    */   
/*    */   public int getEntriesCount() {
/* 65 */     return this.entries.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public List getEntriesList() {
/* 70 */     return this.entries;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setEntriesList(List l) {
/* 75 */     this.entries = l;
/*    */   }
/*    */   
/* 78 */   private List entries = new ArrayList();
/*    */   private JavaType baseType;
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\java\JavaEnumerationType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */