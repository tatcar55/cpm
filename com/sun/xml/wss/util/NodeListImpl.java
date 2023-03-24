/*    */ package com.sun.xml.wss.util;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.w3c.dom.Node;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NodeListImpl
/*    */   implements NodeList
/*    */ {
/* 69 */   private List<Node> nodes = new ArrayList<Node>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLength() {
/* 76 */     return this.nodes.size();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Node item(int i) {
/* 83 */     return this.nodes.get(i);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void add(Node node) {
/* 90 */     this.nodes.add(node);
/*    */   }
/*    */   
/*    */   public void merge(NodeList nodeList) {
/* 94 */     for (int i = 0; i < nodeList.getLength(); i++)
/* 95 */       this.nodes.add(nodeList.item(i)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\\util\NodeListImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */