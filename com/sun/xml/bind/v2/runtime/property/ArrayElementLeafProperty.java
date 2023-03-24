/*    */ package com.sun.xml.bind.v2.runtime.property;
/*    */ 
/*    */ import com.sun.xml.bind.api.AccessorException;
/*    */ import com.sun.xml.bind.v2.model.runtime.RuntimeElementPropertyInfo;
/*    */ import com.sun.xml.bind.v2.model.runtime.RuntimeTypeRef;
/*    */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
/*    */ import com.sun.xml.bind.v2.runtime.JaxBeanInfo;
/*    */ import com.sun.xml.bind.v2.runtime.Transducer;
/*    */ import com.sun.xml.bind.v2.runtime.XMLSerializer;
/*    */ import java.io.IOException;
/*    */ import javax.xml.stream.XMLStreamException;
/*    */ import org.xml.sax.SAXException;
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
/*    */ final class ArrayElementLeafProperty<BeanT, ListT, ItemT>
/*    */   extends ArrayElementProperty<BeanT, ListT, ItemT>
/*    */ {
/*    */   private final Transducer<ItemT> xducer;
/*    */   
/*    */   public ArrayElementLeafProperty(JAXBContextImpl p, RuntimeElementPropertyInfo prop) {
/* 70 */     super(p, prop);
/*    */ 
/*    */     
/* 73 */     assert prop.getTypes().size() == 1;
/*    */ 
/*    */     
/* 76 */     this.xducer = ((RuntimeTypeRef)prop.getTypes().get(0)).getTransducer();
/* 77 */     assert this.xducer != null;
/*    */   }
/*    */   
/*    */   public void serializeItem(JaxBeanInfo bi, ItemT item, XMLSerializer w) throws SAXException, AccessorException, IOException, XMLStreamException {
/* 81 */     this.xducer.declareNamespace(item, w);
/* 82 */     w.endNamespaceDecls(item);
/* 83 */     w.endAttributes();
/*    */ 
/*    */     
/* 86 */     this.xducer.writeText(w, item, this.fieldName);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\property\ArrayElementLeafProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */