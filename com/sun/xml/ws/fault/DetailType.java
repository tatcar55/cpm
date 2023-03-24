/*    */ package com.sun.xml.ws.fault;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.istack.Nullable;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAnyElement;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class DetailType
/*    */ {
/*    */   @XmlAnyElement
/* 71 */   private final List<Element> detailEntry = new ArrayList<Element>();
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   List<Element> getDetails() {
/* 76 */     return this.detailEntry;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   Node getDetail(int n) {
/* 84 */     if (n < this.detailEntry.size()) {
/* 85 */       return this.detailEntry.get(n);
/*    */     }
/* 87 */     return null;
/*    */   }
/*    */   
/*    */   DetailType(Element detailObject) {
/* 91 */     if (detailObject != null)
/* 92 */       this.detailEntry.add(detailObject); 
/*    */   }
/*    */   
/*    */   DetailType() {}
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\fault\DetailType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */