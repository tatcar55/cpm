/*    */ package com.sun.xml.rpc.processor.modeler.rmi;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MemberInfo
/*    */ {
/*    */   private RmiType type;
/*    */   private boolean isPublic = false;
/*    */   private String readMethod;
/*    */   private String writeMethod;
/*    */   private String name;
/*    */   private Class declaringClass;
/*    */   private Class sortingClass;
/*    */   
/*    */   private MemberInfo() {}
/*    */   
/*    */   public MemberInfo(String name, RmiType type, boolean isPublic) {
/* 47 */     this.type = type;
/* 48 */     this.isPublic = isPublic;
/* 49 */     this.name = name;
/*    */   }
/*    */   public MemberInfo(RmiType type, boolean isPublic) {
/* 52 */     this.type = type;
/* 53 */     this.isPublic = isPublic;
/*    */   }
/*    */   public RmiType getType() {
/* 56 */     return this.type;
/*    */   }
/*    */   public boolean isPublic() {
/* 59 */     return this.isPublic;
/*    */   }
/*    */   public String getReadMethod() {
/* 62 */     return this.readMethod;
/*    */   }
/*    */   public void setReadMethod(String readMethod) {
/* 65 */     this.readMethod = readMethod;
/*    */   }
/*    */   public String getWriteMethod() {
/* 68 */     return this.writeMethod;
/*    */   }
/*    */   public void setWriteMethod(String writeMethod) {
/* 71 */     this.writeMethod = writeMethod;
/*    */   }
/*    */   public String getName() {
/* 74 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 77 */     this.name = name;
/*    */   }
/*    */   public Class getDeclaringClass() {
/* 80 */     return this.declaringClass;
/*    */   }
/*    */   public void setDeclaringClass(Class declaringClass) {
/* 83 */     this.declaringClass = declaringClass;
/*    */   }
/*    */   
/*    */   public Class getSortingClass() {
/* 87 */     return this.sortingClass;
/*    */   }
/*    */   public void setSortingClass(Class sortingClass) {
/* 90 */     this.sortingClass = sortingClass;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\rmi\MemberInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */