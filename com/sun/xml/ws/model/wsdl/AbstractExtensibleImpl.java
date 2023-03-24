/*     */ package com.sun.xml.ws.model.wsdl;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLExtensible;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLExtension;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLObject;
/*     */ import com.sun.xml.ws.resources.UtilMessages;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractExtensibleImpl
/*     */   extends AbstractObjectImpl
/*     */   implements WSDLExtensible
/*     */ {
/*  70 */   protected final Set<WSDLExtension> extensions = new HashSet<WSDLExtension>();
/*     */ 
/*     */   
/*  73 */   protected List<UnknownWSDLExtension> notUnderstoodExtensions = new ArrayList<UnknownWSDLExtension>();
/*     */ 
/*     */   
/*     */   protected AbstractExtensibleImpl(XMLStreamReader xsr) {
/*  77 */     super(xsr);
/*     */   }
/*     */   
/*     */   protected AbstractExtensibleImpl(String systemId, int lineNumber) {
/*  81 */     super(systemId, lineNumber);
/*     */   }
/*     */   
/*     */   public final Iterable<WSDLExtension> getExtensions() {
/*  85 */     return this.extensions;
/*     */   }
/*     */ 
/*     */   
/*     */   public final <T extends WSDLExtension> Iterable<T> getExtensions(Class<T> type) {
/*  90 */     List<T> r = new ArrayList<T>(this.extensions.size());
/*  91 */     for (WSDLExtension e : this.extensions) {
/*  92 */       if (type.isInstance(e))
/*  93 */         r.add(type.cast(e)); 
/*     */     } 
/*  95 */     return r;
/*     */   }
/*     */   
/*     */   public <T extends WSDLExtension> T getExtension(Class<T> type) {
/*  99 */     for (WSDLExtension e : this.extensions) {
/* 100 */       if (type.isInstance(e))
/* 101 */         return type.cast(e); 
/*     */     } 
/* 103 */     return null;
/*     */   }
/*     */   
/*     */   public void addExtension(WSDLExtension ex) {
/* 107 */     if (ex == null)
/*     */     {
/* 109 */       throw new IllegalArgumentException(); } 
/* 110 */     this.extensions.add(ex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addNotUnderstoodExtension(QName extnEl, Locator locator) {
/* 120 */     this.notUnderstoodExtensions.add(new UnknownWSDLExtension(extnEl, locator));
/*     */   }
/*     */   
/*     */   protected static class UnknownWSDLExtension implements WSDLExtension, WSDLObject { private final QName extnEl;
/*     */     private final Locator locator;
/*     */     
/*     */     public UnknownWSDLExtension(QName extnEl, Locator locator) {
/* 127 */       this.extnEl = extnEl;
/* 128 */       this.locator = locator;
/*     */     }
/*     */     public QName getName() {
/* 131 */       return this.extnEl;
/*     */     } @NotNull
/*     */     public Locator getLocation() {
/* 134 */       return this.locator;
/*     */     }
/*     */     public String toString() {
/* 137 */       return this.extnEl + " " + UtilMessages.UTIL_LOCATION(Integer.valueOf(this.locator.getLineNumber()), this.locator.getSystemId());
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean areRequiredExtensionsUnderstood() {
/* 146 */     if (this.notUnderstoodExtensions.size() != 0) {
/* 147 */       StringBuilder buf = new StringBuilder("Unknown WSDL extensibility elements:");
/* 148 */       for (UnknownWSDLExtension extn : this.notUnderstoodExtensions)
/* 149 */         buf.append('\n').append(extn.toString()); 
/* 150 */       throw new WebServiceException(buf.toString());
/*     */     } 
/* 152 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\wsdl\AbstractExtensibleImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */