/*     */ package com.sun.xml.bind.v2.runtime;
/*     */ 
/*     */ import com.sun.xml.bind.api.CompositeStructure;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Loader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.InvocationTargetException;
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
/*     */ public class CompositeStructureBeanInfo
/*     */   extends JaxBeanInfo<CompositeStructure>
/*     */ {
/*     */   public CompositeStructureBeanInfo(JAXBContextImpl context) {
/*  61 */     super(context, null, CompositeStructure.class, false, true, false);
/*     */   }
/*     */   
/*     */   public String getElementNamespaceURI(CompositeStructure o) {
/*  65 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getElementLocalName(CompositeStructure o) {
/*  69 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public CompositeStructure createInstance(UnmarshallingContext context) throws IllegalAccessException, InvocationTargetException, InstantiationException, SAXException {
/*  73 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public boolean reset(CompositeStructure o, UnmarshallingContext context) throws SAXException {
/*  77 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getId(CompositeStructure o, XMLSerializer target) throws SAXException {
/*  81 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Loader getLoader(JAXBContextImpl context, boolean typeSubstitutionCapable) {
/*  86 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void serializeRoot(CompositeStructure o, XMLSerializer target) throws SAXException, IOException, XMLStreamException {
/*  90 */     target.reportError(new ValidationEventImpl(1, Messages.UNABLE_TO_MARSHAL_NON_ELEMENT.format(new Object[] { o.getClass().getName() }, ), null, null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(CompositeStructure o, XMLSerializer target) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(CompositeStructure o, XMLSerializer target) throws SAXException, IOException, XMLStreamException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(CompositeStructure o, XMLSerializer target) throws SAXException, IOException, XMLStreamException {
/* 107 */     int len = o.bridges.length;
/* 108 */     for (int i = 0; i < len; i++) {
/* 109 */       Object value = o.values[i];
/* 110 */       InternalBridge<Object> bi = (InternalBridge)o.bridges[i];
/* 111 */       bi.marshal(value, target);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Transducer<CompositeStructure> getTransducer() {
/* 116 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\CompositeStructureBeanInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */