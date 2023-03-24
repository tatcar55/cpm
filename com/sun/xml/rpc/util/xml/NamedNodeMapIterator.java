/*    */ package com.sun.xml.rpc.util.xml;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import org.w3c.dom.NamedNodeMap;
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
/*    */ public class NamedNodeMapIterator
/*    */   implements Iterator
/*    */ {
/*    */   protected NamedNodeMap _map;
/*    */   protected int _index;
/*    */   
/*    */   public NamedNodeMapIterator(NamedNodeMap map) {
/* 42 */     this._map = map;
/* 43 */     this._index = 0;
/*    */   }
/*    */   
/*    */   public boolean hasNext() {
/* 47 */     if (this._map == null)
/* 48 */       return false; 
/* 49 */     return (this._index < this._map.getLength());
/*    */   }
/*    */   
/*    */   public Object next() {
/* 53 */     Object obj = this._map.item(this._index);
/* 54 */     if (obj != null)
/* 55 */       this._index++; 
/* 56 */     return obj;
/*    */   }
/*    */   
/*    */   public void remove() {
/* 60 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\xml\NamedNodeMapIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */