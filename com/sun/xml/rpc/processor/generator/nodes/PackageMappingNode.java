/*    */ package com.sun.xml.rpc.processor.generator.nodes;
/*    */ 
/*    */ import org.w3c.dom.Element;
/*    */ import org.w3c.dom.Node;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PackageMappingNode
/*    */   extends JaxRpcMappingNode
/*    */ {
/*    */   private static final String MYNAME = "PackageMappingNode";
/*    */   
/*    */   public Node write(Node parent, String nodeName, String packageName, String namespace) throws Exception {
/* 55 */     debug("PackageMappingNode", "packageName = " + packageName);
/* 56 */     debug("PackageMappingNode", "namespace = " + namespace);
/*    */     
/* 58 */     Element node = appendChild(parent, nodeName);
/* 59 */     if (packageName != null && namespace != null) {
/* 60 */       appendTextChild(node, "package-type", packageName);
/*    */ 
/*    */ 
/*    */       
/* 64 */       appendTextChild(node, "namespaceURI", namespace);
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 70 */     return node;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\nodes\PackageMappingNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */