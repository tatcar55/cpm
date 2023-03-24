/*    */ package com.sun.xml.rpc.processor.model;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
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
/*    */ public class Response
/*    */   extends Message
/*    */ {
/*    */   public void addFaultBlock(Block b) {
/* 42 */     if (this._faultBlocks.containsKey(b.getName())) {
/* 43 */       throw new ModelException("model.uniqueness");
/*    */     }
/* 45 */     this._faultBlocks.put(b.getName(), b);
/*    */   }
/*    */   
/*    */   public Iterator getFaultBlocks() {
/* 49 */     return this._faultBlocks.values().iterator();
/*    */   }
/*    */   
/*    */   public int getFaultBlockCount() {
/* 53 */     return this._faultBlocks.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public Map getFaultBlocksMap() {
/* 58 */     return this._faultBlocks;
/*    */   }
/*    */   
/*    */   public void setFaultBlocksMap(Map m) {
/* 62 */     this._faultBlocks = m;
/*    */   }
/*    */   
/*    */   public void accept(ModelVisitor visitor) throws Exception {
/* 66 */     visitor.visit(this);
/*    */   }
/*    */   
/* 69 */   private Map _faultBlocks = new HashMap<Object, Object>();
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\Response.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */