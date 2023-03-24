/*    */ package com.sun.xml.rpc.client.dii;
/*    */ 
/*    */ import javax.xml.namespace.QName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParameterMemberInfo
/*    */ {
/*    */   String memberName;
/*    */   QName memberXmlType;
/*    */   Class memberJavaClass;
/*    */   
/*    */   public void addParameterMemberInfo(String parameterName, QName parameterXmlType, Class memberJavaType) {
/* 40 */     this.memberName = parameterName;
/* 41 */     this.memberXmlType = parameterXmlType;
/* 42 */     this.memberJavaClass = memberJavaType;
/*    */   }
/*    */   
/*    */   public String getMemberName() {
/* 46 */     return this.memberName;
/*    */   }
/*    */   
/*    */   public QName getMemberXmlType() {
/* 50 */     return this.memberXmlType;
/*    */   }
/*    */   
/*    */   public Class getMemberJavaClass() {
/* 54 */     return this.memberJavaClass;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\dii\ParameterMemberInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */