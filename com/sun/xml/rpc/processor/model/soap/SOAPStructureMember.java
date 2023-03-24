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
/*    */ 
/*    */ 
/*    */ public class SOAPStructureMember
/*    */ {
/*    */   private QName _name;
/*    */   private SOAPType _type;
/*    */   private JavaStructureMember _javaStructureMember;
/*    */   
/*    */   public SOAPStructureMember() {}
/*    */   
/*    */   public SOAPStructureMember(QName name, SOAPType type) {
/* 42 */     this(name, type, null);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SOAPStructureMember(QName name, SOAPType type, JavaStructureMember javaStructureMember) {
/* 48 */     this._name = name;
/* 49 */     this._type = type;
/* 50 */     this._javaStructureMember = javaStructureMember;
/*    */   }
/*    */   
/*    */   public QName getName() {
/* 54 */     return this._name;
/*    */   }
/*    */   
/*    */   public void setName(QName n) {
/* 58 */     this._name = n;
/*    */   }
/*    */   
/*    */   public SOAPType getType() {
/* 62 */     return this._type;
/*    */   }
/*    */   
/*    */   public void setType(SOAPType t) {
/* 66 */     this._type = t;
/*    */   }
/*    */   
/*    */   public boolean isInherited() {
/* 70 */     return this.isInherited;
/*    */   }
/*    */   
/*    */   public void setInherited(boolean b) {
/* 74 */     this.isInherited = b;
/*    */   }
/*    */   
/*    */   public JavaStructureMember getJavaStructureMember() {
/* 78 */     return this._javaStructureMember;
/*    */   }
/*    */   
/*    */   public void setJavaStructureMember(JavaStructureMember javaStructureMember) {
/* 82 */     this._javaStructureMember = javaStructureMember;
/*    */   }
/*    */   
/*    */   private boolean isInherited = false;
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\soap\SOAPStructureMember.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */