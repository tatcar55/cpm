/*     */ package com.sun.xml.ws.db.glassfish;
/*     */ 
/*     */ import com.sun.xml.bind.api.Bridge;
/*     */ import com.sun.xml.bind.api.JAXBRIContext;
/*     */ import com.sun.xml.ws.spi.db.BindingContext;
/*     */ import com.sun.xml.ws.spi.db.DatabindingException;
/*     */ import com.sun.xml.ws.spi.db.TypeInfo;
/*     */ import com.sun.xml.ws.spi.db.XMLBridge;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.attachment.AttachmentMarshaller;
/*     */ import javax.xml.bind.attachment.AttachmentUnmarshaller;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.ContentHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BridgeWrapper<T>
/*     */   implements XMLBridge<T>
/*     */ {
/*     */   private JAXBRIContextWrapper parent;
/*     */   private Bridge<T> bridge;
/*     */   
/*     */   public BridgeWrapper(JAXBRIContextWrapper p, Bridge<T> b) {
/*  72 */     this.parent = p;
/*  73 */     this.bridge = b;
/*     */   }
/*     */ 
/*     */   
/*     */   public BindingContext context() {
/*  78 */     return this.parent;
/*     */   }
/*     */   
/*     */   Bridge getBridge() {
/*  82 */     return this.bridge;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  87 */     return this.bridge.equals(obj);
/*     */   }
/*     */   
/*     */   public JAXBRIContext getContext() {
/*  91 */     return this.bridge.getContext();
/*     */   }
/*     */ 
/*     */   
/*     */   public TypeInfo getTypeInfo() {
/*  96 */     return this.parent.typeInfo(this.bridge.getTypeReference());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 101 */     return this.bridge.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(Marshaller m, T object, ContentHandler contentHandler) throws JAXBException {
/* 124 */     this.bridge.marshal(m, object, contentHandler);
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller m, T object, Node output) throws JAXBException {
/* 128 */     this.bridge.marshal(m, object, output);
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller m, T object, OutputStream output, NamespaceContext nsContext) throws JAXBException {
/* 132 */     this.bridge.marshal(m, object, output, nsContext);
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller m, T object, Result result) throws JAXBException {
/* 136 */     this.bridge.marshal(m, object, result);
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller m, T object, XMLStreamWriter output) throws JAXBException {
/* 140 */     this.bridge.marshal(m, object, output);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void marshal(T object, ContentHandler contentHandler, AttachmentMarshaller am) throws JAXBException {
/* 147 */     this.bridge.marshal(object, contentHandler, am);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(T object, ContentHandler contentHandler) throws JAXBException {
/* 163 */     this.bridge.marshal(object, contentHandler);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(T object, Node output) throws JAXBException {
/* 169 */     this.bridge.marshal(object, output);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(T object, OutputStream output, NamespaceContext nsContext, AttachmentMarshaller am) throws JAXBException {
/* 176 */     this.bridge.marshal(object, output, nsContext, am);
/*     */   }
/*     */   
/*     */   public void marshal(T object, OutputStream output, NamespaceContext nsContext) throws JAXBException {
/* 180 */     this.bridge.marshal(object, output, nsContext);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void marshal(T object, Result result) throws JAXBException {
/* 186 */     this.bridge.marshal(object, result);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void marshal(T object, XMLStreamWriter output, AttachmentMarshaller am) throws JAXBException {
/* 192 */     this.bridge.marshal(object, output, am);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void marshal(T object, XMLStreamWriter output) throws JAXBException {
/* 197 */     this.bridge.marshal(object, output);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 202 */     return BridgeWrapper.class.getName() + " : " + this.bridge.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T unmarshal(InputStream in) throws JAXBException {
/* 226 */     return (T)this.bridge.unmarshal(in);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final T unmarshal(Node n, AttachmentUnmarshaller au) throws JAXBException {
/* 232 */     return (T)this.bridge.unmarshal(n, au);
/*     */   }
/*     */   
/*     */   public final T unmarshal(Node n) throws JAXBException {
/* 236 */     return (T)this.bridge.unmarshal(n);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final T unmarshal(Source in, AttachmentUnmarshaller au) throws JAXBException {
/* 242 */     return (T)this.bridge.unmarshal(in, au);
/*     */   }
/*     */   
/*     */   public final T unmarshal(Source in) throws DatabindingException {
/*     */     try {
/* 247 */       return (T)this.bridge.unmarshal(in);
/* 248 */     } catch (JAXBException e) {
/* 249 */       throw new DatabindingException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public T unmarshal(Unmarshaller u, InputStream in) throws JAXBException {
/* 254 */     return (T)this.bridge.unmarshal(u, in);
/*     */   }
/*     */   
/*     */   public T unmarshal(Unmarshaller context, Node n) throws JAXBException {
/* 258 */     return (T)this.bridge.unmarshal(context, n);
/*     */   }
/*     */   
/*     */   public T unmarshal(Unmarshaller u, Source in) throws JAXBException {
/* 262 */     return (T)this.bridge.unmarshal(u, in);
/*     */   }
/*     */   
/*     */   public T unmarshal(Unmarshaller u, XMLStreamReader in) throws JAXBException {
/* 266 */     return (T)this.bridge.unmarshal(u, in);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final T unmarshal(XMLStreamReader in, AttachmentUnmarshaller au) throws JAXBException {
/* 272 */     return (T)this.bridge.unmarshal(in, au);
/*     */   }
/*     */   
/*     */   public final T unmarshal(XMLStreamReader in) throws JAXBException {
/* 276 */     return (T)this.bridge.unmarshal(in);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean supportOutputStream() {
/* 281 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\db\glassfish\BridgeWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */