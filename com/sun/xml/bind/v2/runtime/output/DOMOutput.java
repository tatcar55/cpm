/*    */ package com.sun.xml.bind.v2.runtime.output;
/*    */ 
/*    */ import com.sun.xml.bind.marshaller.SAX2DOMEx;
/*    */ import com.sun.xml.bind.v2.runtime.AssociationMap;
/*    */ import org.w3c.dom.Node;
/*    */ import org.xml.sax.ContentHandler;
/*    */ import org.xml.sax.SAXException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DOMOutput
/*    */   extends SAXOutput
/*    */ {
/*    */   private final AssociationMap assoc;
/*    */   
/*    */   public DOMOutput(Node node, AssociationMap assoc) {
/* 61 */     super((ContentHandler)new SAX2DOMEx(node));
/* 62 */     this.assoc = assoc;
/* 63 */     assert assoc != null;
/*    */   }
/*    */   
/*    */   private SAX2DOMEx getBuilder() {
/* 67 */     return (SAX2DOMEx)this.out;
/*    */   }
/*    */ 
/*    */   
/*    */   public void endStartTag() throws SAXException {
/* 72 */     super.endStartTag();
/*    */     
/* 74 */     Object op = this.nsContext.getCurrent().getOuterPeer();
/* 75 */     if (op != null) {
/* 76 */       this.assoc.addOuter(getBuilder().getCurrentElement(), op);
/*    */     }
/* 78 */     Object ip = this.nsContext.getCurrent().getInnerPeer();
/* 79 */     if (ip != null)
/* 80 */       this.assoc.addInner(getBuilder().getCurrentElement(), ip); 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\output\DOMOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */