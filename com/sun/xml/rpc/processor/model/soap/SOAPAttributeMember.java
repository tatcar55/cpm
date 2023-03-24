/*    */ package com.sun.xml.rpc.processor.model.soap;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
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
/*    */ public class SOAPAttributeMember
/*    */ {
/*    */   private QName _name;
/*    */   private SOAPType _type;
/*    */   private JavaStructureMember _javaStructureMember;
/*    */   private boolean _required;
/*    */   
/*    */   public SOAPAttributeMember() {}
/*    */   
/*    */   public SOAPAttributeMember(QName name, SOAPType type) {
/* 41 */     this(name, type, null);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SOAPAttributeMember(QName name, SOAPType type, JavaStructureMember javaStructureMember) {
/* 47 */     this._name = name;
/* 48 */     this._type = type;
/* 49 */     this._javaStructureMember = javaStructureMember;
/*    */   }
/*    */   
/*    */   public QName getName() {
/* 53 */     return this._name;
/*    */   }
/*    */   
/*    */   public void setName(QName n) {
/* 57 */     this._name = n;
/*    */   }
/*    */   
/*    */   public SOAPType getType() {
/* 61 */     return this._type;
/*    */   }
/*    */   
/*    */   public void setType(SOAPType t) {
/* 65 */     this._type = t;
/*    */   }
/*    */   
/*    */   public JavaStructureMember getJavaStructureMember() {
/* 69 */     return this._javaStructureMember;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setJavaStructureMember(JavaStructureMember javaStructureMember) {
/* 75 */     this._javaStructureMember = javaStructureMember;
/*    */   }
/*    */   
/*    */   public boolean isRequired() {
/* 79 */     return this._required;
/*    */   }
/*    */   
/*    */   public void setRequired(boolean b) {
/* 83 */     this._required = b;
/*    */   }
/*    */   
/*    */   public boolean isInherited() {
/* 87 */     return this.isInherited;
/*    */   }
/*    */   
/*    */   public void setInherited(boolean b) {
/* 91 */     this.isInherited = b;
/*    */   }
/*    */   
/*    */   private boolean isInherited = false;
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\soap\SOAPAttributeMember.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */