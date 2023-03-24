/*    */ package com.sun.xml.ws.api.model.wsdl;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.istack.Nullable;
/*    */ import java.util.Map;
/*    */ import javax.jws.WebParam;
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
/*    */ public interface WSDLBoundOperation
/*    */   extends WSDLObject, WSDLExtensible
/*    */ {
/*    */   @NotNull
/*    */   QName getName();
/*    */   
/*    */   @NotNull
/*    */   String getSOAPAction();
/*    */   
/*    */   @NotNull
/*    */   WSDLOperation getOperation();
/*    */   
/*    */   @NotNull
/*    */   WSDLBoundPortType getBoundPortType();
/*    */   
/*    */   ANONYMOUS getAnonymous();
/*    */   
/*    */   @Nullable
/*    */   WSDLPart getPart(@NotNull String paramString, @NotNull WebParam.Mode paramMode);
/*    */   
/*    */   @NotNull
/*    */   Map<String, WSDLPart> getInParts();
/*    */   
/*    */   @NotNull
/*    */   Map<String, WSDLPart> getOutParts();
/*    */   
/*    */   @NotNull
/*    */   Iterable<? extends WSDLBoundFault> getFaults();
/*    */   
/*    */   @Nullable
/*    */   QName getReqPayloadName();
/*    */   
/*    */   @Nullable
/*    */   QName getResPayloadName();
/*    */   
/*    */   String getRequestNamespace();
/*    */   
/*    */   String getResponseNamespace();
/*    */   
/*    */   public enum ANONYMOUS
/*    */   {
/* 87 */     optional, required, prohibited;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\model\wsdl\WSDLBoundOperation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */