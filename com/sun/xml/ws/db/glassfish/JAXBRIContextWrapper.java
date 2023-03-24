/*     */ package com.sun.xml.ws.db.glassfish;
/*     */ 
/*     */ import com.sun.xml.bind.api.Bridge;
/*     */ import com.sun.xml.bind.api.JAXBRIContext;
/*     */ import com.sun.xml.bind.api.TypeReference;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeTypeInfoSet;
/*     */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.ws.spi.db.BindingContext;
/*     */ import com.sun.xml.ws.spi.db.PropertyAccessor;
/*     */ import com.sun.xml.ws.spi.db.TypeInfo;
/*     */ import com.sun.xml.ws.spi.db.WrapperComposite;
/*     */ import com.sun.xml.ws.spi.db.XMLBridge;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.SchemaOutputResolver;
/*     */ import javax.xml.bind.Unmarshaller;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class JAXBRIContextWrapper
/*     */   implements BindingContext
/*     */ {
/*     */   private Map<TypeInfo, TypeReference> typeRefs;
/*     */   private Map<TypeReference, TypeInfo> typeInfos;
/*     */   private JAXBRIContext context;
/*     */   
/*     */   JAXBRIContextWrapper(JAXBRIContext cxt, Map<TypeInfo, TypeReference> refs) {
/*  67 */     this.context = cxt;
/*  68 */     this.typeRefs = refs;
/*  69 */     if (refs != null) {
/*  70 */       this.typeInfos = new HashMap<TypeReference, TypeInfo>();
/*  71 */       for (TypeInfo ti : refs.keySet()) {
/*  72 */         this.typeInfos.put(this.typeRefs.get(ti), ti);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   TypeReference typeReference(TypeInfo ti) {
/*  78 */     return (this.typeRefs != null) ? this.typeRefs.get(ti) : null;
/*     */   }
/*     */   
/*     */   TypeInfo typeInfo(TypeReference tr) {
/*  82 */     return (this.typeInfos != null) ? this.typeInfos.get(tr) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Marshaller createMarshaller() throws JAXBException {
/*  87 */     return this.context.createMarshaller();
/*     */   }
/*     */ 
/*     */   
/*     */   public Unmarshaller createUnmarshaller() throws JAXBException {
/*  92 */     return this.context.createUnmarshaller();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void generateSchema(SchemaOutputResolver outputResolver) throws IOException {
/*  98 */     this.context.generateSchema(outputResolver);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBuildId() {
/* 103 */     return this.context.getBuildId();
/*     */   }
/*     */ 
/*     */   
/*     */   public QName getElementName(Class o) throws JAXBException {
/* 108 */     return this.context.getElementName(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public QName getElementName(Object o) throws JAXBException {
/* 113 */     return this.context.getElementName(o);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <B, V> PropertyAccessor<B, V> getElementPropertyAccessor(Class<B> wrapperBean, String nsUri, String localName) throws JAXBException {
/* 120 */     return new RawAccessorWrapper(this.context.getElementPropertyAccessor(wrapperBean, nsUri, localName));
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getKnownNamespaceURIs() {
/* 125 */     return this.context.getKnownNamespaceURIs();
/*     */   }
/*     */   
/*     */   public RuntimeTypeInfoSet getRuntimeTypeInfoSet() {
/* 129 */     return this.context.getRuntimeTypeInfoSet();
/*     */   }
/*     */   
/*     */   public QName getTypeName(TypeReference tr) {
/* 133 */     return this.context.getTypeName(tr);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 138 */     return this.context.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 143 */     if (obj == null) {
/* 144 */       return false;
/*     */     }
/* 146 */     if (getClass() != obj.getClass()) {
/* 147 */       return false;
/*     */     }
/* 149 */     JAXBRIContextWrapper other = (JAXBRIContextWrapper)obj;
/* 150 */     if (this.context != other.context && (this.context == null || !this.context.equals(other.context))) {
/* 151 */       return false;
/*     */     }
/* 153 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasSwaRef() {
/* 158 */     return this.context.hasSwaRef();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 163 */     return JAXBRIContextWrapper.class.getName() + " : " + this.context.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLBridge createBridge(TypeInfo ti) {
/* 168 */     TypeReference tr = this.typeRefs.get(ti);
/* 169 */     Bridge<?> b = this.context.createBridge(tr);
/* 170 */     return WrapperComposite.class.equals(ti.type) ? new WrapperBridge(this, b) : new BridgeWrapper(this, b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JAXBContext getJAXBContext() {
/* 177 */     return (JAXBContext)this.context;
/*     */   }
/*     */ 
/*     */   
/*     */   public QName getTypeName(TypeInfo ti) {
/* 182 */     TypeReference tr = this.typeRefs.get(ti);
/* 183 */     return this.context.getTypeName(tr);
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLBridge createFragmentBridge() {
/* 188 */     return new MarshallerBridge((JAXBContextImpl)this.context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object newWrapperInstace(Class<?> wrapperType) throws InstantiationException, IllegalAccessException {
/* 194 */     return wrapperType.newInstance();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\db\glassfish\JAXBRIContextWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */