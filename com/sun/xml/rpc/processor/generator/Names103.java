/*    */ package com.sun.xml.rpc.processor.generator;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.model.Port;
/*    */ import com.sun.xml.rpc.processor.model.java.JavaType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Names103
/*    */   extends Names
/*    */ {
/*    */   public String holderClassName(Port port, JavaType type) {
/* 38 */     if (type.getHolderName() != null)
/* 39 */       return type.getHolderName(); 
/* 40 */     return holderClassName(port, type.getName());
/*    */   }
/*    */   
/*    */   protected String holderClassName(Port port, String typeName) {
/* 44 */     String holderTypeName = (String)holderClassNames.get(typeName);
/* 45 */     if (holderTypeName == null) {
/*    */       
/* 47 */       String className = port.getJavaInterface().getName();
/* 48 */       String packageName = getPackageName(className);
/* 49 */       if (packageName.length() > 0) {
/* 50 */         packageName = packageName + ".holders.";
/*    */       } else {
/* 52 */         packageName = "holders.";
/*    */       } 
/*    */       
/* 55 */       if (!isInJavaOrJavaxPackage(typeName)) {
/* 56 */         typeName = stripQualifier(typeName);
/*    */       }
/* 58 */       int idx = typeName.indexOf("[]");
/* 59 */       while (idx > 0) {
/* 60 */         typeName = typeName.substring(0, idx) + "Array" + typeName.substring(idx + 2);
/*    */ 
/*    */ 
/*    */         
/* 64 */         idx = typeName.indexOf("[]");
/*    */       } 
/* 66 */       holderTypeName = packageName + typeName + "Holder";
/*    */     } 
/* 68 */     return holderTypeName;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\Names103.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */