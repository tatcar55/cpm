/*    */ package com.sun.xml.ws.security.opt.crypto.dsig.keyinfo;
/*    */ 
/*    */ import com.sun.xml.security.core.dsig.SPKIDataType;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlRootElement(name = "SPKIData", namespace = "http://www.w3.org/2000/09/xmldsig#")
/*    */ public class SPKIData
/*    */   extends SPKIDataType
/*    */ {
/*    */   public void setSpkiSexpAndAny(List<Object> spkiSexpAndAny) {
/* 67 */     this.spkiSexpAndAny = spkiSexpAndAny;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\dsig\keyinfo\SPKIData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */