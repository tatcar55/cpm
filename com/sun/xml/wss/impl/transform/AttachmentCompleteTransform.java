/*     */ package com.sun.xml.wss.impl.transform;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.TransformSpi;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.TransformationException;
/*     */ import com.sun.xml.wss.impl.c14n.Canonicalizer;
/*     */ import com.sun.xml.wss.impl.c14n.CanonicalizerFactory;
/*     */ import com.sun.xml.wss.impl.c14n.MimeHeaderCanonicalizer;
/*     */ import com.sun.xml.wss.impl.resolver.AttachmentSignatureInput;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AttachmentCompleteTransform
/*     */   extends TransformSpi
/*     */ {
/*     */   private static final String implementedTransformURI = "http://docs.oasis-open.org/wss/2004/XX/oasis-2004XX-wss-swa-profile-1.0#Attachment-Complete-Transform";
/*     */   
/*     */   protected String engineGetURI() {
/*  66 */     return "http://docs.oasis-open.org/wss/2004/XX/oasis-2004XX-wss-swa-profile-1.0#Attachment-Complete-Transform";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected XMLSignatureInput enginePerformTransform(XMLSignatureInput input) throws TransformationException {
/*     */     try {
/*  73 */       return new XMLSignatureInput(_canonicalize(input));
/*  74 */     } catch (Exception e) {
/*     */       
/*  76 */       throw new TransformationException(e.getMessage(), e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private byte[] _canonicalize(XMLSignatureInput input) throws Exception {
/*  81 */     byte[] inputContentBytes = input.getBytes();
/*     */ 
/*     */ 
/*     */     
/*  85 */     MimeHeaderCanonicalizer mHCanonicalizer = CanonicalizerFactory.getMimeHeaderCanonicalizer("US-ASCII");
/*  86 */     byte[] outputHeaderBytes = mHCanonicalizer._canonicalize(((AttachmentSignatureInput)input).getMimeHeaders().iterator());
/*     */     
/*  88 */     Canonicalizer canonicalizer = CanonicalizerFactory.getCanonicalizer(((AttachmentSignatureInput)input).getContentType());
/*     */ 
/*     */     
/*  91 */     byte[] outputContentBytes = canonicalizer.canonicalize(inputContentBytes);
/*     */     
/*  93 */     byte[] outputBytes = new byte[outputHeaderBytes.length + outputContentBytes.length];
/*  94 */     System.arraycopy(outputHeaderBytes, 0, outputBytes, 0, outputHeaderBytes.length);
/*  95 */     System.arraycopy(outputContentBytes, 0, outputBytes, outputHeaderBytes.length, outputContentBytes.length);
/*     */ 
/*     */     
/*  98 */     return outputBytes;
/*     */   }
/*     */   
/* 101 */   public boolean wantsOctetStream() { return true; }
/* 102 */   public boolean wantsNodeSet() { return true; }
/* 103 */   public boolean returnsOctetStream() { return true; } public boolean returnsNodeSet() {
/* 104 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\transform\AttachmentCompleteTransform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */