/*     */ package com.sun.xml.bind.v2.runtime.property;
/*     */ 
/*     */ import com.sun.xml.bind.api.AccessorException;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.bind.v2.runtime.XMLSerializer;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.Accessor;
/*     */ import java.io.IOException;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import org.xml.sax.SAXException;
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
/*     */ abstract class PropertyImpl<BeanT>
/*     */   implements Property<BeanT>
/*     */ {
/*     */   protected final String fieldName;
/*  63 */   private RuntimePropertyInfo propertyInfo = null;
/*     */   private boolean hiddenByOverride = false;
/*     */   
/*     */   public PropertyImpl(JAXBContextImpl context, RuntimePropertyInfo prop) {
/*  67 */     this.fieldName = prop.getName();
/*  68 */     if (context.retainPropertyInfo) {
/*  69 */       this.propertyInfo = prop;
/*     */     }
/*     */   }
/*     */   
/*     */   public RuntimePropertyInfo getInfo() {
/*  74 */     return this.propertyInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeBody(BeanT o, XMLSerializer w, Object outerPeer) throws SAXException, AccessorException, IOException, XMLStreamException {}
/*     */ 
/*     */   
/*     */   public void serializeURIs(BeanT o, XMLSerializer w) throws SAXException, AccessorException {}
/*     */   
/*     */   public boolean hasSerializeURIAction() {
/*  84 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Accessor getElementPropertyAccessor(String nsUri, String localName) {
/*  89 */     return null;
/*     */   }
/*     */   
/*     */   public void wrapUp() {}
/*     */   
/*     */   public boolean isHiddenByOverride() {
/*  95 */     return this.hiddenByOverride;
/*     */   }
/*     */   
/*     */   public void setHiddenByOverride(boolean hidden) {
/*  99 */     this.hiddenByOverride = hidden;
/*     */   }
/*     */   
/*     */   public String getFieldName() {
/* 103 */     return this.fieldName;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\property\PropertyImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */