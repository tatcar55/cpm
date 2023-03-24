/*    */ package com.sun.xml.ws.security.trust.impl.elements.str;
/*    */ 
/*    */ import com.sun.xml.ws.security.secconv.impl.WSSCVersion10;
/*    */ import com.sun.xml.ws.security.secconv.impl.wssx.WSSCVersion13;
/*    */ import com.sun.xml.ws.security.secext10.ReferenceType;
/*    */ import com.sun.xml.ws.security.trust.elements.str.DirectReference;
/*    */ import java.net.URI;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DirectReferenceImpl
/*    */   extends ReferenceType
/*    */   implements DirectReference
/*    */ {
/* 59 */   private static final QName _WSC_INSTANCE_10_Type_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "Instance");
/* 60 */   private static final QName _WSC_INSTANCE_13_Type_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "Instance");
/*    */   
/*    */   public DirectReferenceImpl(String valueType, String uri) {
/* 63 */     setValueType(valueType);
/* 64 */     setURI(uri);
/*    */   }
/*    */   private static final String WSC_INSTANCE = "wsc:Instance";
/*    */   public DirectReferenceImpl(String valueType, String uri, String instance) {
/* 68 */     setValueType(valueType);
/* 69 */     setURI(uri);
/* 70 */     if (WSSCVersion10.WSSC_10.getSCTTokenTypeURI().equals(valueType)) {
/* 71 */       getOtherAttributes().put(_WSC_INSTANCE_10_Type_QNAME, instance);
/* 72 */     } else if (WSSCVersion13.WSSC_13.getSCTTokenTypeURI().equals(valueType)) {
/* 73 */       getOtherAttributes().put(_WSC_INSTANCE_13_Type_QNAME, instance);
/*    */     } 
/*    */   }
/*    */   
/*    */   public DirectReferenceImpl(ReferenceType refType) {
/* 78 */     this(refType.getValueType(), refType.getURI());
/*    */   }
/*    */   
/*    */   public URI getURIAttr() {
/* 82 */     return URI.create(getURI());
/*    */   }
/*    */   
/*    */   public URI getValueTypeURI() {
/* 86 */     return URI.create(getValueType());
/*    */   }
/*    */   
/*    */   public String getType() {
/* 90 */     return "Reference";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\elements\str\DirectReferenceImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */