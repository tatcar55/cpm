/*    */ package com.sun.xml.rpc.util.xml;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import org.w3c.dom.NodeList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NodeListIterator
/*    */   implements Iterator
/*    */ {
/*    */   protected NodeList _list;
/*    */   protected int _index;
/*    */   
/*    */   public NodeListIterator(NodeList list) {
/* 42 */     this._list = list;
/* 43 */     this._index = 0;
/*    */   }
/*    */   
/*    */   public boolean hasNext() {
/* 47 */     if (this._list == null)
/* 48 */       return false; 
/* 49 */     return (this._index < this._list.getLength());
/*    */   }
/*    */   
/*    */   public Object next() {
/* 53 */     Object obj = this._list.item(this._index);
/* 54 */     if (obj != null)
/* 55 */       this._index++; 
/* 56 */     return obj;
/*    */   }
/*    */   
/*    */   public void remove() {
/* 60 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\xml\NodeListIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */