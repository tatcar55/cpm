/*    */ package com.sun.xml.ws.tx.coord.common;
/*    */ 
/*    */ import com.sun.xml.ws.tx.at.WSATHelper;
/*    */ import com.sun.xml.ws.util.DOMUtil;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WSCUtil
/*    */ {
/*    */   public static Element referenceElementTxId(String txId) {
/* 52 */     Element ele = DOMUtil.createDom().createElementNS("http://com.sun.xml.ws.tx.at/ws/2008/10/wsat", "wsat-wsat:txId");
/* 53 */     ele.setTextContent(txId);
/* 54 */     return ele;
/*    */   }
/*    */   
/*    */   public static Element referenceElementBranchQual(String branchQual) {
/* 58 */     Element ele = DOMUtil.createDom().createElementNS("http://com.sun.xml.ws.tx.at/ws/2008/10/wsat", "wsat-wsat:branchQual");
/*    */ 
/*    */     
/* 61 */     branchQual = branchQual.replaceAll(",", "&#044;");
/* 62 */     ele.setTextContent(branchQual.trim());
/* 63 */     return ele;
/*    */   }
/*    */   
/*    */   public static Element referenceElementRoutingInfo() {
/* 67 */     String routingInfo = WSATHelper.getInstance().getRoutingAddress();
/* 68 */     Element ele = DOMUtil.createDom().createElementNS("http://com.sun.xml.ws.tx.at/ws/2008/10/wsat", "wsat-wsat:routing");
/* 69 */     ele.setTextContent(routingInfo);
/* 70 */     return ele;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\common\WSCUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */