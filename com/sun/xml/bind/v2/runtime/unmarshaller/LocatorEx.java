/*     */ package com.sun.xml.bind.v2.runtime.unmarshaller;
/*     */ 
/*     */ import java.net.URL;
/*     */ import javax.xml.bind.ValidationEventLocator;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.Locator;
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
/*     */ public interface LocatorEx
/*     */   extends Locator
/*     */ {
/*     */   ValidationEventLocator getLocation();
/*     */   
/*     */   public static final class Snapshot
/*     */     implements LocatorEx, ValidationEventLocator
/*     */   {
/*     */     private final int columnNumber;
/*     */     private final int lineNumber;
/*     */     private final int offset;
/*     */     private final String systemId;
/*     */     private final String publicId;
/*     */     private final URL url;
/*     */     private final Object object;
/*     */     private final Node node;
/*     */     
/*     */     public Snapshot(LocatorEx loc) {
/*  72 */       this.columnNumber = loc.getColumnNumber();
/*  73 */       this.lineNumber = loc.getLineNumber();
/*  74 */       this.systemId = loc.getSystemId();
/*  75 */       this.publicId = loc.getPublicId();
/*     */       
/*  77 */       ValidationEventLocator vel = loc.getLocation();
/*  78 */       this.offset = vel.getOffset();
/*  79 */       this.url = vel.getURL();
/*  80 */       this.object = vel.getObject();
/*  81 */       this.node = vel.getNode();
/*     */     }
/*     */     
/*     */     public Object getObject() {
/*  85 */       return this.object;
/*     */     }
/*     */     
/*     */     public Node getNode() {
/*  89 */       return this.node;
/*     */     }
/*     */     
/*     */     public int getOffset() {
/*  93 */       return this.offset;
/*     */     }
/*     */     
/*     */     public URL getURL() {
/*  97 */       return this.url;
/*     */     }
/*     */     
/*     */     public int getColumnNumber() {
/* 101 */       return this.columnNumber;
/*     */     }
/*     */     
/*     */     public int getLineNumber() {
/* 105 */       return this.lineNumber;
/*     */     }
/*     */     
/*     */     public String getSystemId() {
/* 109 */       return this.systemId;
/*     */     }
/*     */     
/*     */     public String getPublicId() {
/* 113 */       return this.publicId;
/*     */     }
/*     */     
/*     */     public ValidationEventLocator getLocation() {
/* 117 */       return this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\LocatorEx.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */