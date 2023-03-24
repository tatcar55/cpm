/*     */ package org.glassfish.gmbal.typelib;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.glassfish.gmbal.generic.Graph;
/*     */ import org.glassfish.gmbal.generic.Predicate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EvaluatedClassAnalyzer
/*     */ {
/* 149 */   private static final Graph.Finder<EvaluatedClassDeclaration> finder = new Graph.Finder<EvaluatedClassDeclaration>()
/*     */     {
/*     */ 
/*     */       
/*     */       public List<EvaluatedClassDeclaration> evaluate(EvaluatedClassDeclaration arg)
/*     */       {
/* 155 */         return arg.inheritance();
/*     */       }
/*     */     };
/*     */   
/*     */   private final List<EvaluatedClassDeclaration> classInheritance;
/* 160 */   private String contents = null;
/*     */   
/*     */   private EvaluatedClassAnalyzer(Graph<EvaluatedClassDeclaration> gr) {
/* 163 */     List<EvaluatedClassDeclaration> result = new ArrayList<EvaluatedClassDeclaration>(gr.getPostorderList());
/*     */     
/* 165 */     Collections.reverse(result);
/* 166 */     this.classInheritance = result;
/*     */   }
/*     */   
/*     */   public EvaluatedClassAnalyzer(EvaluatedClassDeclaration cls) {
/* 170 */     this(new Graph(cls, finder));
/*     */   }
/*     */ 
/*     */   
/*     */   public EvaluatedClassAnalyzer(List<EvaluatedClassDeclaration> decls) {
/* 175 */     this(new Graph(decls, finder));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<EvaluatedClassDeclaration> findClasses(Predicate<EvaluatedClassDeclaration> pred) {
/* 181 */     List<EvaluatedClassDeclaration> result = new ArrayList<EvaluatedClassDeclaration>();
/*     */ 
/*     */     
/* 184 */     for (EvaluatedClassDeclaration c : this.classInheritance) {
/* 185 */       if (pred.evaluate(c)) {
/* 186 */         result.add(c);
/*     */       }
/*     */     } 
/*     */     
/* 190 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<EvaluatedMethodDeclaration> findMethods(Predicate<EvaluatedMethodDeclaration> pred) {
/* 198 */     List<EvaluatedMethodDeclaration> result = new ArrayList<EvaluatedMethodDeclaration>();
/*     */ 
/*     */     
/* 201 */     for (EvaluatedClassDeclaration c : this.classInheritance) {
/* 202 */       for (EvaluatedMethodDeclaration m : c.methods()) {
/* 203 */         if (pred.evaluate(m)) {
/* 204 */           result.add(m);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 209 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<EvaluatedFieldDeclaration> findFields(Predicate<EvaluatedFieldDeclaration> pred) {
/* 215 */     List<EvaluatedFieldDeclaration> result = new ArrayList<EvaluatedFieldDeclaration>();
/*     */ 
/*     */     
/* 218 */     for (EvaluatedClassDeclaration c : this.classInheritance) {
/* 219 */       for (EvaluatedFieldDeclaration f : c.fields()) {
/* 220 */         if (pred.evaluate(f)) {
/* 221 */           result.add(f);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 226 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized String toString() {
/* 231 */     if (this.contents == null) {
/* 232 */       StringBuilder sb = new StringBuilder();
/*     */       
/* 234 */       boolean first = true;
/* 235 */       sb.append("ClassAnalyzer[");
/* 236 */       for (EvaluatedClassDeclaration cls : this.classInheritance) {
/* 237 */         if (first) {
/* 238 */           first = false;
/*     */         } else {
/* 240 */           sb.append(" ");
/*     */         } 
/* 242 */         sb.append(cls.name());
/*     */       } 
/* 244 */       sb.append("]");
/* 245 */       this.contents = sb.toString();
/*     */     } 
/*     */     
/* 248 */     return this.contents;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\typelib\EvaluatedClassAnalyzer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */