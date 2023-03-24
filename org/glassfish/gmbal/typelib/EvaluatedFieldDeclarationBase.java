/*    */ package org.glassfish.gmbal.typelib;
/*    */ 
/*    */ import org.glassfish.gmbal.generic.ObjectSet;
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
/*    */ public abstract class EvaluatedFieldDeclarationBase
/*    */   extends EvaluatedDeclarationBase
/*    */   implements EvaluatedFieldDeclaration
/*    */ {
/*    */   public <R> R accept(Visitor<R> visitor) {
/* 55 */     return visitor.visitEvaluatedFieldDeclaration(this);
/*    */   }
/*    */   
/*    */   public void containingClass(EvaluatedClassDeclaration cdecl) {
/* 59 */     throw new IllegalArgumentException("Operation not permitted");
/*    */   }
/*    */   
/*    */   void makeRepresentation(StringBuilder sb, ObjectSet set) {
/* 63 */     handleModifier(sb, modifiers());
/* 64 */     sb.append(" ");
/* 65 */     sb.append(fieldType().toString());
/* 66 */     sb.append(" ");
/* 67 */     sb.append(name());
/*    */   }
/*    */   
/*    */   public boolean myEquals(Object obj, ObjectSet set) {
/* 71 */     EvaluatedFieldDeclaration other = (EvaluatedFieldDeclaration)obj;
/* 72 */     if (!((EvaluatedTypeBase)fieldType()).myEquals(other.fieldType(), set) || !name().equals(other.name()))
/*    */     {
/*    */       
/* 75 */       return false;
/*    */     }
/*    */     
/* 78 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode(ObjectSet set) {
/* 83 */     return fieldType().hashCode() ^ name().hashCode();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\typelib\EvaluatedFieldDeclarationBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */