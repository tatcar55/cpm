/*     */ package com.sun.xml.rpc.soap.message;
/*     */ 
/*     */ import com.sun.xml.rpc.util.NullIterator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.xml.soap.SOAPMessage;
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
/*     */ public class InternalSOAPMessage
/*     */ {
/*     */   public static final int NO_OPERATION = -1;
/*     */   private SOAPMessage _message;
/*     */   private List _headers;
/*     */   private SOAPBlockInfo _body;
/*     */   private int _operationCode;
/*     */   private boolean _failure;
/*     */   private boolean _headerNotUnderstood;
/*     */   
/*     */   public InternalSOAPMessage(SOAPMessage message) {
/*  47 */     this._message = message;
/*  48 */     this._operationCode = -1;
/*     */   }
/*     */   
/*     */   public SOAPMessage getMessage() {
/*  52 */     return this._message;
/*     */   }
/*     */   
/*     */   public void add(SOAPHeaderBlockInfo headerInfo) {
/*  56 */     if (headerInfo != null) {
/*  57 */       if (this._headers == null) {
/*  58 */         this._headers = new ArrayList();
/*     */       }
/*  60 */       this._headers.add(headerInfo);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Iterator headers() {
/*  65 */     if (this._headers == null) {
/*  66 */       return (Iterator)NullIterator.getInstance();
/*     */     }
/*  68 */     return this._headers.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPBlockInfo getBody() {
/*  73 */     return this._body;
/*     */   }
/*     */   
/*     */   public void setBody(SOAPBlockInfo body) {
/*  77 */     this._body = body;
/*     */   }
/*     */   
/*     */   public int getOperationCode() {
/*  81 */     return this._operationCode;
/*     */   }
/*     */   
/*     */   public void setOperationCode(int i) {
/*  85 */     this._operationCode = i;
/*     */   }
/*     */   
/*     */   public boolean isHeaderNotUnderstood() {
/*  89 */     return this._headerNotUnderstood;
/*     */   }
/*     */   
/*     */   public void setHeaderNotUnderstood(boolean b) {
/*  93 */     this._headerNotUnderstood = b;
/*     */   }
/*     */   
/*     */   public boolean isFailure() {
/*  97 */     return this._failure;
/*     */   }
/*     */   
/*     */   public void setFailure(boolean b) {
/* 101 */     this._failure = b;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\soap\message\InternalSOAPMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */