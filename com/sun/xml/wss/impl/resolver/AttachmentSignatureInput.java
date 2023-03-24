/*    */ package com.sun.xml.wss.impl.resolver;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.util.Iterator;
/*    */ import java.util.Vector;
/*    */ import javax.xml.soap.AttachmentPart;
/*    */ import javax.xml.soap.SOAPException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AttachmentSignatureInput
/*    */   extends XMLSignatureInput
/*    */ {
/* 55 */   private String _cType = null;
/* 56 */   private Vector _mhs = new Vector();
/*    */   
/*    */   public AttachmentSignatureInput(byte[] input) {
/* 59 */     super(input);
/*    */   }
/*    */   
/*    */   public void setMimeHeaders(Vector mimeHeaders) {
/* 63 */     this._mhs = mimeHeaders;
/*    */   }
/*    */   
/*    */   public Vector getMimeHeaders() {
/* 67 */     return this._mhs;
/*    */   }
/*    */   
/*    */   public void setContentType(String _cType) {
/* 71 */     this._cType = _cType;
/*    */   }
/*    */   
/*    */   public String getContentType() {
/* 75 */     return this._cType;
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Object[] _getSignatureInput(AttachmentPart _part) throws SOAPException, IOException {
/* 80 */     Iterator mhItr = _part.getAllMimeHeaders();
/*    */     
/* 82 */     Vector mhs = new Vector();
/* 83 */     for (; mhItr.hasNext(); mhs.add(mhItr.next()));
/*    */ 
/*    */ 
/*    */     
/* 87 */     OutputStream baos = new ByteArrayOutputStream();
/* 88 */     _part.getDataHandler().writeTo(baos);
/*    */     
/* 90 */     return new Object[] { mhs, ((ByteArrayOutputStream)baos).toByteArray() };
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\resolver\AttachmentSignatureInput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */