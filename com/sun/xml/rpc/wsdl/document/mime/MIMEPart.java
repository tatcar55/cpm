/*    */ package com.sun.xml.rpc.wsdl.document.mime;
/*    */ 
/*    */ import com.sun.xml.rpc.wsdl.framework.EntityAction;
/*    */ import com.sun.xml.rpc.wsdl.framework.ExtensibilityHelper;
/*    */ import com.sun.xml.rpc.wsdl.framework.Extensible;
/*    */ import com.sun.xml.rpc.wsdl.framework.Extension;
/*    */ import java.util.Iterator;
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
/*    */ public class MIMEPart
/*    */   extends Extension
/*    */   implements Extensible
/*    */ {
/*    */   private String _name;
/* 46 */   private ExtensibilityHelper _helper = new ExtensibilityHelper();
/*    */ 
/*    */   
/*    */   public QName getElementName() {
/* 50 */     return MIMEConstants.QNAME_PART;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 54 */     return this._name;
/*    */   }
/*    */   
/*    */   public void setName(String s) {
/* 58 */     this._name = s;
/*    */   }
/*    */   
/*    */   public void addExtension(Extension e) {
/* 62 */     this._helper.addExtension(e);
/*    */   }
/*    */   
/*    */   public Iterator extensions() {
/* 66 */     return this._helper.extensions();
/*    */   }
/*    */   
/*    */   public void withAllSubEntitiesDo(EntityAction action) {
/* 70 */     this._helper.withAllSubEntitiesDo(action);
/*    */   }
/*    */   
/*    */   public void validateThis() {}
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\mime\MIMEPart.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */