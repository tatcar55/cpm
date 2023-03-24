/*     */ package com.sun.xml.bind.v2.runtime;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.bind.api.TypeReference;
/*     */ import com.sun.xml.bind.marshaller.SAX2DOMEx;
/*     */ import com.sun.xml.bind.v2.runtime.output.SAXOutput;
/*     */ import com.sun.xml.bind.v2.runtime.output.XMLStreamWriterOutput;
/*     */ import com.sun.xml.bind.v2.runtime.output.XmlOutput;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallerImpl;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.ContentHandler;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class BridgeImpl<T>
/*     */   extends InternalBridge<T>
/*     */ {
/*     */   private final Name tagName;
/*     */   private final JaxBeanInfo<T> bi;
/*     */   private final TypeReference typeRef;
/*     */   
/*     */   public BridgeImpl(JAXBContextImpl context, Name tagName, JaxBeanInfo<T> bi, TypeReference typeRef) {
/*  86 */     super(context);
/*  87 */     this.tagName = tagName;
/*  88 */     this.bi = bi;
/*  89 */     this.typeRef = typeRef;
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller _m, T t, XMLStreamWriter output) throws JAXBException {
/*  93 */     MarshallerImpl m = (MarshallerImpl)_m;
/*  94 */     m.write(this.tagName, this.bi, t, XMLStreamWriterOutput.create(output, this.context), new StAXPostInitAction(output, m.serializer));
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller _m, T t, OutputStream output, NamespaceContext nsContext) throws JAXBException {
/*  98 */     MarshallerImpl m = (MarshallerImpl)_m;
/*     */     
/* 100 */     Runnable pia = null;
/* 101 */     if (nsContext != null) {
/* 102 */       pia = new StAXPostInitAction(nsContext, m.serializer);
/*     */     }
/* 104 */     m.write(this.tagName, this.bi, t, m.createWriter(output), pia);
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller _m, T t, Node output) throws JAXBException {
/* 108 */     MarshallerImpl m = (MarshallerImpl)_m;
/* 109 */     m.write(this.tagName, this.bi, t, (XmlOutput)new SAXOutput((ContentHandler)new SAX2DOMEx(output)), new DomPostInitAction(output, m.serializer));
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller _m, T t, ContentHandler contentHandler) throws JAXBException {
/* 113 */     MarshallerImpl m = (MarshallerImpl)_m;
/* 114 */     m.write(this.tagName, this.bi, t, (XmlOutput)new SAXOutput(contentHandler), null);
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller _m, T t, Result result) throws JAXBException {
/* 118 */     MarshallerImpl m = (MarshallerImpl)_m;
/* 119 */     m.write(this.tagName, this.bi, t, m.createXmlOutput(result), m.createPostInitAction(result));
/*     */   }
/*     */   @NotNull
/*     */   public T unmarshal(Unmarshaller _u, XMLStreamReader in) throws JAXBException {
/* 123 */     UnmarshallerImpl u = (UnmarshallerImpl)_u;
/* 124 */     return ((JAXBElement<T>)u.unmarshal0(in, this.bi)).getValue();
/*     */   }
/*     */   @NotNull
/*     */   public T unmarshal(Unmarshaller _u, Source in) throws JAXBException {
/* 128 */     UnmarshallerImpl u = (UnmarshallerImpl)_u;
/* 129 */     return ((JAXBElement<T>)u.unmarshal0(in, this.bi)).getValue();
/*     */   }
/*     */   @NotNull
/*     */   public T unmarshal(Unmarshaller _u, InputStream in) throws JAXBException {
/* 133 */     UnmarshallerImpl u = (UnmarshallerImpl)_u;
/* 134 */     return ((JAXBElement<T>)u.unmarshal0(in, this.bi)).getValue();
/*     */   }
/*     */   @NotNull
/*     */   public T unmarshal(Unmarshaller _u, Node n) throws JAXBException {
/* 138 */     UnmarshallerImpl u = (UnmarshallerImpl)_u;
/* 139 */     return ((JAXBElement<T>)u.unmarshal0(n, this.bi)).getValue();
/*     */   }
/*     */   
/*     */   public TypeReference getTypeReference() {
/* 143 */     return this.typeRef;
/*     */   }
/*     */   
/*     */   public void marshal(T value, XMLSerializer out) throws IOException, SAXException, XMLStreamException {
/* 147 */     out.startElement(this.tagName, null);
/* 148 */     if (value == null) {
/* 149 */       out.writeXsiNilTrue();
/*     */     } else {
/* 151 */       out.childAsXsiType(value, null, this.bi, false);
/*     */     } 
/* 153 */     out.endElement();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\BridgeImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */