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
/*    */ public abstract class EvaluatedMethodDeclarationBase
/*    */   extends EvaluatedDeclarationBase
/*    */   implements EvaluatedMethodDeclaration
/*    */ {
/*    */   public <R> R accept(Visitor<R> visitor) {
/* 55 */     return visitor.visitEvaluatedMethodDeclaration(this);
/*    */   }
/*    */   
/*    */   public void containingClass(EvaluatedClassDeclaration cdecl) {
/* 59 */     throw new IllegalArgumentException("Operation not permitted");
/*    */   }
/*    */   
/*    */   void makeRepresentation(StringBuilder sb, ObjectSet set) {
/* 63 */     handleModifier(sb, modifiers());
/* 64 */     sb.append(" ");
/* 65 */     sb.append(returnType().toString());
/* 66 */     sb.append(" ");
/* 67 */     sb.append(name());
/* 68 */     handleList(sb, "(", castList(parameterTypes(), EvaluatedTypeBase.class), ",", ")", set);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean myEquals(Object obj, ObjectSet set) {
/* 74 */     EvaluatedMethodDeclaration other = (EvaluatedMethodDeclaration)obj;
/* 75 */     if (!((EvaluatedTypeBase)returnType()).myEquals(other.returnType(), set) || !name().equals(other.name()))
/*    */     {
/*    */       
/* 78 */       return false;
/*    */     }
/*    */     
/* 81 */     return equalList(parameterTypes(), other.parameterTypes(), set);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode(ObjectSet set) {
/* 86 */     return returnType().hashCode() ^ parameterTypes().hashCode() ^ name().hashCode();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\typelib\EvaluatedMethodDeclarationBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */