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
/*    */ public abstract class EvaluatedArrayTypeBase
/*    */   extends EvaluatedTypeBase
/*    */   implements EvaluatedArrayType
/*    */ {
/*    */   void makeRepresentation(StringBuilder sb, ObjectSet set) {
/* 53 */     ((EvaluatedTypeBase)componentType()).makeRepresentation(sb, set);
/* 54 */     sb.append("[]");
/*    */   }
/*    */   
/*    */   boolean myEquals(Object obj, ObjectSet set) {
/* 58 */     EvaluatedArrayType other = (EvaluatedArrayType)obj;
/* 59 */     return ((EvaluatedTypeBase)componentType()).myEquals(other.componentType(), set);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode(ObjectSet set) {
/* 64 */     return ((EvaluatedTypeBase)componentType()).hashCode(set) * 37;
/*    */   }
/*    */ 
/*    */   
/*    */   public <R> R accept(Visitor<R> visitor) {
/* 69 */     return visitor.visitEvaluatedArrayType(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\typelib\EvaluatedArrayTypeBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */