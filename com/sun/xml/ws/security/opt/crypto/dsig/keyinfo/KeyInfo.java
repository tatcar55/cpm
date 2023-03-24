/*    */ package com.sun.xml.ws.security.opt.crypto.dsig.keyinfo;
/*    */ 
/*    */ import com.sun.xml.security.core.dsig.KeyInfoType;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ import javax.xml.crypto.MarshalException;
/*    */ import javax.xml.crypto.XMLCryptoContext;
/*    */ import javax.xml.crypto.XMLStructure;
/*    */ import javax.xml.crypto.dsig.keyinfo.KeyInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlRootElement(name = "KeyInfo", namespace = "http://www.w3.org/2000/09/xmldsig#")
/*    */ @XmlType(name = "KeyInfoType")
/*    */ public class KeyInfo
/*    */   extends KeyInfoType
/*    */   implements KeyInfo
/*    */ {
/*    */   public void marshal(XMLStructure xMLStructure, XMLCryptoContext xMLCryptoContext) throws MarshalException {}
/*    */   
/*    */   public boolean isFeatureSupported(String string) {
/* 73 */     return false;
/*    */   }
/*    */   
/*    */   public void setContent(List<Object> content) {
/* 77 */     this.content = content;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\dsig\keyinfo\KeyInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */