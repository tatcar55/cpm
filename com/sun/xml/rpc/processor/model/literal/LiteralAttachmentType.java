/*     */ package com.sun.xml.rpc.processor.model.literal;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.model.java.JavaType;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.xml.namespace.QName;
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
/*     */ public class LiteralAttachmentType
/*     */   extends LiteralType
/*     */ {
/*     */   private String mimeType;
/*     */   private List alternateMIMETypes;
/*     */   private String contentId;
/*     */   
/*     */   public LiteralAttachmentType() {}
/*     */   
/*     */   public LiteralAttachmentType(QName name, JavaType javaType) {
/*  47 */     super(name, javaType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void accept(LiteralTypeVisitor visitor) throws Exception {
/*  54 */     visitor.visit(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMIMEType(String mimeType) {
/*  59 */     this.mimeType = mimeType;
/*     */   }
/*     */   
/*     */   public String getMIMEType() {
/*  63 */     return this.mimeType;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAlternateMIMEType(String mimeType) {
/*  68 */     if (this.alternateMIMETypes == null) {
/*  69 */       this.alternateMIMETypes = new ArrayList();
/*     */     }
/*  71 */     this.alternateMIMETypes.add(mimeType);
/*     */   }
/*     */   
/*     */   public void addAlternateMIMEType(Iterator<String> mimeTypes) {
/*  75 */     if (this.alternateMIMETypes == null) {
/*  76 */       this.alternateMIMETypes = new ArrayList();
/*     */     }
/*  78 */     while (mimeTypes.hasNext()) {
/*  79 */       this.alternateMIMETypes.add(mimeTypes.next());
/*     */     }
/*     */   }
/*     */   
/*     */   public List getAlternateMIMETypes() {
/*  84 */     if (this.alternateMIMETypes == null) {
/*  85 */       List<String> mimeTypes = new ArrayList();
/*  86 */       mimeTypes.add(this.mimeType);
/*  87 */       return mimeTypes;
/*     */     } 
/*  89 */     return this.alternateMIMETypes;
/*     */   }
/*     */   
/*     */   public void setAlternateMIMETypes(List l) {
/*  93 */     this.alternateMIMETypes = l;
/*     */   }
/*     */   
/*     */   public void setSwaRef(boolean isSwaRef) {
/*  97 */     this.isSwaRef = isSwaRef;
/*     */   }
/*     */   
/*     */   public boolean isSwaRef() {
/* 101 */     return this.isSwaRef;
/*     */   }
/*     */   
/*     */   public String getContentID() {
/* 105 */     return this.contentId;
/*     */   }
/*     */   
/*     */   public void setContentID(String id) {
/* 109 */     this.contentId = id;
/*     */   }
/*     */   
/*     */   private boolean isSwaRef = false;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\literal\LiteralAttachmentType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */