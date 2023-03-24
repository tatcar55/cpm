/*     */ package com.sun.xml.bind.v2.runtime;
/*     */ 
/*     */ import com.sun.istack.FinalArrayList;
/*     */ import com.sun.xml.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.bind.api.AccessorException;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Loader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.helpers.ValidationEventImpl;
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
/*     */ final class ValueListBeanInfoImpl
/*     */   extends JaxBeanInfo
/*     */ {
/*     */   private final Class itemType;
/*     */   private final Transducer xducer;
/*     */   private final Loader loader;
/*     */   
/*     */   public ValueListBeanInfoImpl(JAXBContextImpl owner, Class<BeanT> arrayType) throws JAXBException {
/*  71 */     super(owner, null, arrayType, false, true, false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     this.loader = new Loader(true)
/*     */       {
/*     */         public void text(UnmarshallingContext.State state, CharSequence text) throws SAXException {
/*  80 */           FinalArrayList finalArrayList = new FinalArrayList();
/*     */           
/*  82 */           int idx = 0;
/*  83 */           int len = text.length();
/*     */           
/*     */           while (true) {
/*  86 */             int p = idx;
/*  87 */             while (p < len && !WhiteSpaceProcessor.isWhiteSpace(text.charAt(p))) {
/*  88 */               p++;
/*     */             }
/*  90 */             CharSequence token = text.subSequence(idx, p);
/*  91 */             if (!token.equals("")) {
/*     */               try {
/*  93 */                 finalArrayList.add(ValueListBeanInfoImpl.this.xducer.parse(token));
/*  94 */               } catch (AccessorException e) {
/*  95 */                 handleGenericException((Exception)e, true);
/*     */                 continue;
/*     */               } 
/*     */             }
/*  99 */             if (p == len)
/*     */               break; 
/* 101 */             while (p < len && WhiteSpaceProcessor.isWhiteSpace(text.charAt(p)))
/* 102 */               p++; 
/* 103 */             if (p == len)
/*     */               break; 
/* 105 */             idx = p;
/*     */           } 
/*     */           
/* 108 */           state.target = ValueListBeanInfoImpl.this.toArray((List)finalArrayList); }
/*     */       };
/*     */     this.itemType = this.jaxbType.getComponentType();
/*     */     this.xducer = owner.getBeanInfo(arrayType.getComponentType(), true).getTransducer();
/*     */     assert this.xducer != null; } private Object toArray(List list) {
/* 113 */     int len = list.size();
/* 114 */     Object array = Array.newInstance(this.itemType, len);
/* 115 */     for (int i = 0; i < len; i++)
/* 116 */       Array.set(array, i, list.get(i)); 
/* 117 */     return array;
/*     */   }
/*     */   
/*     */   public void serializeBody(Object array, XMLSerializer target) throws SAXException, IOException, XMLStreamException {
/* 121 */     int len = Array.getLength(array);
/* 122 */     for (int i = 0; i < len; i++) {
/* 123 */       Object item = Array.get(array, i);
/*     */       try {
/* 125 */         this.xducer.writeText(target, item, "arrayItem");
/* 126 */       } catch (AccessorException e) {
/* 127 */         target.reportError("arrayItem", (Throwable)e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void serializeURIs(Object array, XMLSerializer target) throws SAXException {
/* 133 */     if (this.xducer.useNamespace()) {
/* 134 */       int len = Array.getLength(array);
/* 135 */       for (int i = 0; i < len; i++) {
/* 136 */         Object item = Array.get(array, i);
/*     */         try {
/* 138 */           this.xducer.declareNamespace(item, target);
/* 139 */         } catch (AccessorException e) {
/* 140 */           target.reportError("arrayItem", (Throwable)e);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public final String getElementNamespaceURI(Object array) {
/* 147 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public final String getElementLocalName(Object array) {
/* 151 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public final Object createInstance(UnmarshallingContext context) {
/* 155 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public final boolean reset(Object array, UnmarshallingContext context) {
/* 159 */     return false;
/*     */   }
/*     */   
/*     */   public final String getId(Object array, XMLSerializer target) {
/* 163 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void serializeAttributes(Object array, XMLSerializer target) {}
/*     */ 
/*     */   
/*     */   public final void serializeRoot(Object array, XMLSerializer target) throws SAXException {
/* 171 */     target.reportError(new ValidationEventImpl(1, Messages.UNABLE_TO_MARSHAL_NON_ELEMENT.format(new Object[] { array.getClass().getName() }, ), null, null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Transducer getTransducer() {
/* 180 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final Loader getLoader(JAXBContextImpl context, boolean typeSubstitutionCapable) {
/* 185 */     return this.loader;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\ValueListBeanInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */