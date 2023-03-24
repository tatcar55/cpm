/*    */ package com.sun.xml.wss.impl.transform;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*    */ import com.sun.org.apache.xml.internal.security.transforms.TransformSpi;
/*    */ import com.sun.org.apache.xml.internal.security.transforms.TransformationException;
/*    */ import com.sun.xml.wss.impl.c14n.Canonicalizer;
/*    */ import com.sun.xml.wss.impl.c14n.CanonicalizerFactory;
/*    */ import com.sun.xml.wss.impl.resolver.AttachmentSignatureInput;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AttachmentContentOnlyTransform
/*    */   extends TransformSpi
/*    */ {
/*    */   private static final String implementedTransformURI = "http://docs.oasis-open.org/wss/2004/XX/oasis-2004XX-wss-swa-profile-1.0#Attachment-Content-Only-Transform";
/*    */   
/*    */   protected String engineGetURI() {
/* 65 */     return "http://docs.oasis-open.org/wss/2004/XX/oasis-2004XX-wss-swa-profile-1.0#Attachment-Content-Only-Transform";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected XMLSignatureInput enginePerformTransform(XMLSignatureInput input) throws TransformationException {
/*    */     try {
/* 72 */       return new XMLSignatureInput(_canonicalize(input));
/* 73 */     } catch (Exception e) {
/*    */       
/* 75 */       throw new TransformationException(e.getMessage(), e);
/*    */     } 
/*    */   }
/*    */   
/*    */   private byte[] _canonicalize(XMLSignatureInput input) throws Exception {
/* 80 */     byte[] inputContentBytes = input.getBytes();
/*    */ 
/*    */     
/* 83 */     Canonicalizer canonicalizer = CanonicalizerFactory.getCanonicalizer(((AttachmentSignatureInput)input).getContentType());
/*    */ 
/*    */ 
/*    */     
/* 87 */     return canonicalizer.canonicalize(inputContentBytes);
/*    */   }
/*    */   
/* 90 */   public boolean wantsOctetStream() { return true; }
/* 91 */   public boolean wantsNodeSet() { return true; }
/* 92 */   public boolean returnsOctetStream() { return true; } public boolean returnsNodeSet() {
/* 93 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\transform\AttachmentContentOnlyTransform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */