/*    */ package com.sun.xml.rpc.processor.schema;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
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
/*    */ public class ModelGroupComponent
/*    */   extends Component
/*    */ {
/*    */   private Symbol _compositor;
/* 40 */   private List _particles = new ArrayList();
/*    */   private AnnotationComponent _annotation;
/*    */   
/*    */   public Symbol getCompositor() {
/* 44 */     return this._compositor;
/*    */   }
/*    */   
/*    */   public void setCompositor(Symbol s) {
/* 48 */     this._compositor = s;
/*    */   }
/*    */   
/*    */   public Iterator particles() {
/* 52 */     return this._particles.iterator();
/*    */   }
/*    */   
/*    */   public void addParticle(ParticleComponent c) {
/* 56 */     this._particles.add(c);
/*    */   }
/*    */   
/*    */   public void accept(ComponentVisitor visitor) throws Exception {
/* 60 */     visitor.visit(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\schema\ModelGroupComponent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */