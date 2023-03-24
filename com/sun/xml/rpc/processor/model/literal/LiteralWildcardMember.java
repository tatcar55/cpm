/*    */ package com.sun.xml.rpc.processor.model.literal;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LiteralWildcardMember
/*    */   extends LiteralElementMember
/*    */ {
/*    */   private String _excludedNamespaceName;
/*    */   
/*    */   public LiteralWildcardMember() {}
/*    */   
/*    */   public LiteralWildcardMember(LiteralType type) {
/* 40 */     this(type, null);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public LiteralWildcardMember(LiteralType type, JavaStructureMember javaStructureMember) {
/* 46 */     super(null, type, javaStructureMember);
/*    */   }
/*    */   
/*    */   public String getExcludedNamespaceName() {
/* 50 */     return this._excludedNamespaceName;
/*    */   }
/*    */   
/*    */   public void setExcludedNamespaceName(String s) {
/* 54 */     this._excludedNamespaceName = s;
/*    */   }
/*    */   
/*    */   public boolean isWildcard() {
/* 58 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\literal\LiteralWildcardMember.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */