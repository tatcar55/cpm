/*    */ package com.sun.xml.rpc.wsdl.document.mime;
/*    */ 
/*    */ import com.sun.xml.rpc.wsdl.framework.Entity;
/*    */ import com.sun.xml.rpc.wsdl.framework.EntityAction;
/*    */ import com.sun.xml.rpc.wsdl.framework.Extension;
/*    */ import com.sun.xml.rpc.wsdl.framework.ExtensionVisitor;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import javax.xml.namespace.QName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MIMEMultipartRelated
/*    */   extends Extension
/*    */ {
/* 48 */   private List _parts = new ArrayList();
/*    */ 
/*    */   
/*    */   public QName getElementName() {
/* 52 */     return MIMEConstants.QNAME_MULTIPART_RELATED;
/*    */   }
/*    */   
/*    */   public void add(MIMEPart part) {
/* 56 */     this._parts.add(part);
/*    */   }
/*    */   
/*    */   public Iterator getParts() {
/* 60 */     return this._parts.iterator();
/*    */   }
/*    */   
/*    */   public void withAllSubEntitiesDo(EntityAction action) {
/* 64 */     super.withAllSubEntitiesDo(action);
/*    */     
/* 66 */     for (Iterator<Entity> iter = this._parts.iterator(); iter.hasNext();) {
/* 67 */       action.perform(iter.next());
/*    */     }
/*    */   }
/*    */   
/*    */   public void accept(ExtensionVisitor visitor) throws Exception {
/* 72 */     visitor.preVisit(this);
/* 73 */     visitor.postVisit(this);
/*    */   }
/*    */   
/*    */   public void validateThis() {}
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\mime\MIMEMultipartRelated.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */