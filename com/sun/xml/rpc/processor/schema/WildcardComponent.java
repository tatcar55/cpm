/*    */ package com.sun.xml.rpc.processor.schema;
/*    */ 
/*    */ import java.util.Set;
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
/*    */ public class WildcardComponent
/*    */   extends Component
/*    */ {
/*    */   public static final int NAMESPACE_CONSTRAINT_ANY = 1;
/*    */   public static final int NAMESPACE_CONSTRAINT_NOT = 2;
/*    */   public static final int NAMESPACE_CONSTRAINT_NOT_ABSENT = 3;
/*    */   public static final int NAMESPACE_CONSTRAINT_SET = 4;
/*    */   public static final int NAMESPACE_CONSTRAINT_SET_OR_ABSENT = 5;
/*    */   private Symbol _processContents;
/*    */   private int _namespaceConstraintTag;
/*    */   private String _namespaceName;
/*    */   private Set _namespaceSet;
/*    */   private AnnotationComponent _annotation;
/*    */   
/*    */   public void setProcessContents(Symbol s) {
/* 46 */     this._processContents = s;
/*    */   }
/*    */   
/*    */   public int getNamespaceConstraintTag() {
/* 50 */     return this._namespaceConstraintTag;
/*    */   }
/*    */   
/*    */   public void setNamespaceConstraintTag(int i) {
/* 54 */     this._namespaceConstraintTag = i;
/*    */   }
/*    */   
/*    */   public String getNamespaceName() {
/* 58 */     return this._namespaceName;
/*    */   }
/*    */   
/*    */   public void setNamespaceName(String s) {
/* 62 */     this._namespaceName = s;
/*    */   }
/*    */   
/*    */   public void accept(ComponentVisitor visitor) throws Exception {
/* 66 */     visitor.visit(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\schema\WildcardComponent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */