/*    */ package org.glassfish.gmbal.typelib;
/*    */ 
/*    */ import java.util.List;
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
/*    */ public abstract class EvaluatedClassDeclarationBase
/*    */   extends EvaluatedDeclarationBase
/*    */   implements EvaluatedClassDeclaration
/*    */ {
/*    */   void makeRepresentation(StringBuilder sb, ObjectSet set) {
/* 54 */     sb.append(name());
/* 55 */     if (instantiations() != null && !set.contains(this)) {
/* 56 */       set.add(this);
/* 57 */       handleList(sb, "<", castList(instantiations(), EvaluatedTypeBase.class), ",", ">", set);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   boolean myEquals(Object obj, ObjectSet set) {
/* 64 */     EvaluatedClassDeclaration other = (EvaluatedClassDeclaration)obj;
/* 65 */     if (!name().equals(other.name())) {
/* 66 */       return false;
/*    */     }
/*    */     
/* 69 */     return equalList(instantiations(), other.instantiations(), set);
/*    */   }
/*    */   
/*    */   public int hashCode(ObjectSet set) {
/* 73 */     set.add(this);
/* 74 */     int result = name().hashCode();
/* 75 */     List<EvaluatedType> list = instantiations();
/* 76 */     if (list == null) {
/* 77 */       return result;
/*    */     }
/* 79 */     for (EvaluatedType et : list) {
/* 80 */       EvaluatedTypeBase etb = (EvaluatedTypeBase)et;
/* 81 */       if (!set.contains(et)) {
/* 82 */         result = 31 * result + etb.hashCode(set);
/*    */       }
/*    */     } 
/*    */     
/* 86 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <R> R accept(Visitor<R> visitor) {
/* 92 */     return visitor.visitEvaluatedClassDeclaration(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\typelib\EvaluatedClassDeclarationBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */