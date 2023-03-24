/*     */ package com.sun.xml.ws.db.glassfish;
/*     */ 
/*     */ import com.sun.xml.bind.api.Bridge;
/*     */ import com.sun.xml.bind.api.CompositeStructure;
/*     */ import com.sun.xml.ws.spi.db.BindingContext;
/*     */ import com.sun.xml.ws.spi.db.TypeInfo;
/*     */ import com.sun.xml.ws.spi.db.WrapperComposite;
/*     */ import com.sun.xml.ws.spi.db.XMLBridge;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.xml.bind.JAXBException;
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
/*     */ 
/*     */ public class WrapperBridge<T>
/*     */   implements XMLBridge<T>
/*     */ {
/*     */   private JAXBRIContextWrapper parent;
/*     */   private Bridge<T> bridge;
/*     */   
/*     */   public WrapperBridge(JAXBRIContextWrapper p, Bridge<T> b) {
/*  71 */     this.parent = p;
/*  72 */     this.bridge = b;
/*     */   }
/*     */ 
/*     */   
/*     */   public BindingContext context() {
/*  77 */     return this.parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  82 */     return this.bridge.equals(obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public TypeInfo getTypeInfo() {
/*  87 */     return this.parent.typeInfo(this.bridge.getTypeReference());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  92 */     return this.bridge.hashCode();
/*     */   }
/*     */   
/*     */   static CompositeStructure convert(Object o) {
/*  96 */     WrapperComposite w = (WrapperComposite)o;
/*  97 */     CompositeStructure cs = new CompositeStructure();
/*  98 */     cs.values = w.values;
/*  99 */     cs.bridges = new Bridge[w.bridges.length];
/* 100 */     for (int i = 0; i < cs.bridges.length; i++) {
/* 101 */       cs.bridges[i] = ((BridgeWrapper)w.bridges[i]).getBridge();
/*     */     }
/* 103 */     return cs;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void marshal(T object, ContentHandler contentHandler, AttachmentMarshaller am) throws JAXBException {
/* 108 */     this.bridge.marshal(convert(object), contentHandler, am);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(T object, Node output) throws JAXBException {
/* 114 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(T object, OutputStream output, NamespaceContext nsContext, AttachmentMarshaller am) throws JAXBException {
/* 121 */     this.bridge.marshal(convert(object), output, nsContext, am);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void marshal(T object, Result result) throws JAXBException {
/* 126 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void marshal(T object, XMLStreamWriter output, AttachmentMarshaller am) throws JAXBException {
/* 132 */     this.bridge.marshal(convert(object), output, am);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 137 */     return BridgeWrapper.class.getName() + " : " + this.bridge.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final T unmarshal(InputStream in) throws JAXBException {
/* 143 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T unmarshal(Node n, AttachmentUnmarshaller au) throws JAXBException {
/* 150 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T unmarshal(Source in, AttachmentUnmarshaller au) throws JAXBException {
/* 157 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T unmarshal(XMLStreamReader in, AttachmentUnmarshaller au) throws JAXBException {
/* 164 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean supportOutputStream() {
/* 170 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\db\glassfish\WrapperBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */