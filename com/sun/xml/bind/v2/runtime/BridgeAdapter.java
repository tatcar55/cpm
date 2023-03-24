/*     */ package com.sun.xml.bind.v2.runtime;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.bind.api.TypeReference;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallerImpl;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.MarshalException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.UnmarshalException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.annotation.adapters.XmlAdapter;
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
/*     */ final class BridgeAdapter<OnWire, InMemory>
/*     */   extends InternalBridge<InMemory>
/*     */ {
/*     */   private final InternalBridge<OnWire> core;
/*     */   private final Class<? extends XmlAdapter<OnWire, InMemory>> adapter;
/*     */   
/*     */   public BridgeAdapter(InternalBridge<OnWire> core, Class<? extends XmlAdapter<OnWire, InMemory>> adapter) {
/*  79 */     super(core.getContext());
/*  80 */     this.core = core;
/*  81 */     this.adapter = adapter;
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller m, InMemory inMemory, XMLStreamWriter output) throws JAXBException {
/*  85 */     this.core.marshal(m, adaptM(m, inMemory), output);
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller m, InMemory inMemory, OutputStream output, NamespaceContext nsc) throws JAXBException {
/*  89 */     this.core.marshal(m, adaptM(m, inMemory), output, nsc);
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller m, InMemory inMemory, Node output) throws JAXBException {
/*  93 */     this.core.marshal(m, adaptM(m, inMemory), output);
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller context, InMemory inMemory, ContentHandler contentHandler) throws JAXBException {
/*  97 */     this.core.marshal(context, adaptM(context, inMemory), contentHandler);
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller context, InMemory inMemory, Result result) throws JAXBException {
/* 101 */     this.core.marshal(context, adaptM(context, inMemory), result);
/*     */   }
/*     */   
/*     */   private OnWire adaptM(Marshaller m, InMemory v) throws JAXBException {
/* 105 */     XMLSerializer serializer = ((MarshallerImpl)m).serializer;
/* 106 */     serializer.setThreadAffinity();
/* 107 */     serializer.pushCoordinator();
/*     */     try {
/* 109 */       return _adaptM(serializer, v);
/*     */     } finally {
/* 111 */       serializer.popCoordinator();
/* 112 */       serializer.resetThreadAffinity();
/*     */     } 
/*     */   }
/*     */   
/*     */   private OnWire _adaptM(XMLSerializer serializer, InMemory v) throws MarshalException {
/* 117 */     XmlAdapter<OnWire, InMemory> a = serializer.getAdapter((Class)this.adapter);
/*     */     try {
/* 119 */       return a.marshal(v);
/* 120 */     } catch (Exception e) {
/* 121 */       serializer.handleError(e, v, (String)null);
/* 122 */       throw new MarshalException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public InMemory unmarshal(Unmarshaller u, XMLStreamReader in) throws JAXBException {
/* 128 */     return adaptU(u, (OnWire)this.core.unmarshal(u, in));
/*     */   }
/*     */   @NotNull
/*     */   public InMemory unmarshal(Unmarshaller u, Source in) throws JAXBException {
/* 132 */     return adaptU(u, (OnWire)this.core.unmarshal(u, in));
/*     */   }
/*     */   @NotNull
/*     */   public InMemory unmarshal(Unmarshaller u, InputStream in) throws JAXBException {
/* 136 */     return adaptU(u, (OnWire)this.core.unmarshal(u, in));
/*     */   }
/*     */   @NotNull
/*     */   public InMemory unmarshal(Unmarshaller u, Node n) throws JAXBException {
/* 140 */     return adaptU(u, (OnWire)this.core.unmarshal(u, n));
/*     */   }
/*     */   
/*     */   public TypeReference getTypeReference() {
/* 144 */     return this.core.getTypeReference();
/*     */   }
/*     */   @NotNull
/*     */   private InMemory adaptU(Unmarshaller _u, OnWire v) throws JAXBException {
/* 148 */     UnmarshallerImpl u = (UnmarshallerImpl)_u;
/* 149 */     XmlAdapter<OnWire, InMemory> a = u.coordinator.getAdapter(this.adapter);
/* 150 */     u.coordinator.setThreadAffinity();
/* 151 */     u.coordinator.pushCoordinator();
/*     */     try {
/* 153 */       return a.unmarshal(v);
/* 154 */     } catch (Exception e) {
/* 155 */       throw new UnmarshalException(e);
/*     */     } finally {
/* 157 */       u.coordinator.popCoordinator();
/* 158 */       u.coordinator.resetThreadAffinity();
/*     */     } 
/*     */   }
/*     */   
/*     */   void marshal(InMemory o, XMLSerializer out) throws IOException, SAXException, XMLStreamException {
/*     */     try {
/* 164 */       this.core.marshal(_adaptM(XMLSerializer.getInstance(), o), out);
/* 165 */     } catch (MarshalException e) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\BridgeAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */