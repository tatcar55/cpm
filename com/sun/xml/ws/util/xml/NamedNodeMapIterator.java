/*    */ package com.sun.xml.ws.util.xml;
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
/* 56 */     this._map = map;
/* 57 */     this._index = 0;
/*    */   }
/*    */   
/*    */   public boolean hasNext() {
/* 61 */     if (this._map == null)
/* 62 */       return false; 
/* 63 */     return (this._index < this._map.getLength());
/*    */   }
/*    */   
/*    */   public Object next() {
/* 67 */     Object obj = this._map.item(this._index);
/* 68 */     if (obj != null)
/* 69 */       this._index++; 
/* 70 */     return obj;
/*    */   }
/*    */   
/*    */   public void remove() {
/* 74 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\xml\NamedNodeMapIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */