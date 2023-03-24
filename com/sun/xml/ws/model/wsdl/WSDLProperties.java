/*    */ package com.sun.xml.ws.model.wsdl;
/*    */ 
/*    */ import com.oracle.webservices.api.message.BasePropertySet;
/*    */ import com.oracle.webservices.api.message.PropertySet.Property;
/*    */ import com.sun.istack.Nullable;
/*    */ import com.sun.xml.ws.api.model.SEIModel;
/*    */ import javax.xml.namespace.QName;
/*    */ import org.xml.sax.InputSource;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WSDLProperties
/*    */   extends BasePropertySet
/*    */ {
/* 65 */   private static final BasePropertySet.PropertyMap model = parse(WSDLProperties.class);
/*    */   
/*    */   @Nullable
/*    */   private final SEIModel seiModel;
/*    */   
/*    */   protected WSDLProperties(@Nullable SEIModel seiModel) {
/* 71 */     this.seiModel = seiModel;
/*    */   }
/*    */   
/*    */   @Property({"javax.xml.ws.wsdl.service"})
/*    */   public abstract QName getWSDLService();
/*    */   
/*    */   @Property({"javax.xml.ws.wsdl.port"})
/*    */   public abstract QName getWSDLPort();
/*    */   
/*    */   @Property({"javax.xml.ws.wsdl.interface"})
/*    */   public abstract QName getWSDLPortType();
/*    */   
/*    */   @Property({"javax.xml.ws.wsdl.description"})
/*    */   public InputSource getWSDLDescription() {
/* 85 */     return (this.seiModel != null) ? new InputSource(this.seiModel.getWSDLLocation()) : null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected BasePropertySet.PropertyMap getPropertyMap() {
/* 90 */     return model;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\wsdl\WSDLProperties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */