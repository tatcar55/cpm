/*     */ package com.sun.xml.bind.api;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.bind.v2.runtime.BridgeContextImpl;
/*     */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Bridge<T>
/*     */ {
/*     */   protected final JAXBContextImpl context;
/*     */   
/*     */   protected Bridge(JAXBContextImpl context) {
/*  84 */     this.context = context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public JAXBRIContext getContext() {
/*  95 */     return (JAXBRIContext)this.context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void marshal(T object, XMLStreamWriter output) throws JAXBException {
/* 106 */     marshal(object, output, (AttachmentMarshaller)null);
/*     */   }
/*     */   public final void marshal(T object, XMLStreamWriter output, AttachmentMarshaller am) throws JAXBException {
/* 109 */     Marshaller m = (Marshaller)this.context.marshallerPool.take();
/* 110 */     m.setAttachmentMarshaller(am);
/* 111 */     marshal(m, object, output);
/* 112 */     m.setAttachmentMarshaller(null);
/* 113 */     this.context.marshallerPool.recycle(m);
/*     */   }
/*     */   
/*     */   public final void marshal(@NotNull BridgeContext context, T object, XMLStreamWriter output) throws JAXBException {
/* 117 */     marshal((Marshaller)((BridgeContextImpl)context).marshaller, object, output);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void marshal(@NotNull Marshaller paramMarshaller, T paramT, XMLStreamWriter paramXMLStreamWriter) throws JAXBException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(T object, OutputStream output, NamespaceContext nsContext) throws JAXBException {
/* 137 */     marshal(object, output, nsContext, (AttachmentMarshaller)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(T object, OutputStream output, NamespaceContext nsContext, AttachmentMarshaller am) throws JAXBException {
/* 143 */     Marshaller m = (Marshaller)this.context.marshallerPool.take();
/* 144 */     m.setAttachmentMarshaller(am);
/* 145 */     marshal(m, object, output, nsContext);
/* 146 */     m.setAttachmentMarshaller(null);
/* 147 */     this.context.marshallerPool.recycle(m);
/*     */   }
/*     */   
/*     */   public final void marshal(@NotNull BridgeContext context, T object, OutputStream output, NamespaceContext nsContext) throws JAXBException {
/* 151 */     marshal((Marshaller)((BridgeContextImpl)context).marshaller, object, output, nsContext);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract void marshal(@NotNull Marshaller paramMarshaller, T paramT, OutputStream paramOutputStream, NamespaceContext paramNamespaceContext) throws JAXBException;
/*     */   
/*     */   public final void marshal(T object, Node output) throws JAXBException {
/* 158 */     Marshaller m = (Marshaller)this.context.marshallerPool.take();
/* 159 */     marshal(m, object, output);
/* 160 */     this.context.marshallerPool.recycle(m);
/*     */   }
/*     */   
/*     */   public final void marshal(@NotNull BridgeContext context, T object, Node output) throws JAXBException {
/* 164 */     marshal((Marshaller)((BridgeContextImpl)context).marshaller, object, output);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void marshal(@NotNull Marshaller paramMarshaller, T paramT, Node paramNode) throws JAXBException;
/*     */ 
/*     */ 
/*     */   
/*     */   public final void marshal(T object, ContentHandler contentHandler) throws JAXBException {
/* 174 */     marshal(object, contentHandler, (AttachmentMarshaller)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void marshal(T object, ContentHandler contentHandler, AttachmentMarshaller am) throws JAXBException {
/* 180 */     Marshaller m = (Marshaller)this.context.marshallerPool.take();
/* 181 */     m.setAttachmentMarshaller(am);
/* 182 */     marshal(m, object, contentHandler);
/* 183 */     m.setAttachmentMarshaller(null);
/* 184 */     this.context.marshallerPool.recycle(m);
/*     */   }
/*     */   public final void marshal(@NotNull BridgeContext context, T object, ContentHandler contentHandler) throws JAXBException {
/* 187 */     marshal((Marshaller)((BridgeContextImpl)context).marshaller, object, contentHandler);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract void marshal(@NotNull Marshaller paramMarshaller, T paramT, ContentHandler paramContentHandler) throws JAXBException;
/*     */ 
/*     */   
/*     */   public final void marshal(T object, Result result) throws JAXBException {
/* 195 */     Marshaller m = (Marshaller)this.context.marshallerPool.take();
/* 196 */     marshal(m, object, result);
/* 197 */     this.context.marshallerPool.recycle(m);
/*     */   }
/*     */   public final void marshal(@NotNull BridgeContext context, T object, Result result) throws JAXBException {
/* 200 */     marshal((Marshaller)((BridgeContextImpl)context).marshaller, object, result);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract void marshal(@NotNull Marshaller paramMarshaller, T paramT, Result paramResult) throws JAXBException;
/*     */   
/*     */   private T exit(T r, Unmarshaller u) {
/* 207 */     u.setAttachmentUnmarshaller(null);
/* 208 */     this.context.unmarshallerPool.recycle(u);
/* 209 */     return r;
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
/*     */   @NotNull
/*     */   public final T unmarshal(@NotNull XMLStreamReader in) throws JAXBException {
/* 229 */     return unmarshal(in, (AttachmentUnmarshaller)null);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public final T unmarshal(@NotNull XMLStreamReader in, @Nullable AttachmentUnmarshaller au) throws JAXBException {
/* 235 */     Unmarshaller u = (Unmarshaller)this.context.unmarshallerPool.take();
/* 236 */     u.setAttachmentUnmarshaller(au);
/* 237 */     return exit(unmarshal(u, in), u);
/*     */   } @NotNull
/*     */   public final T unmarshal(@NotNull BridgeContext context, @NotNull XMLStreamReader in) throws JAXBException {
/* 240 */     return unmarshal((Unmarshaller)((BridgeContextImpl)context).unmarshaller, in);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public abstract T unmarshal(@NotNull Unmarshaller paramUnmarshaller, @NotNull XMLStreamReader paramXMLStreamReader) throws JAXBException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public final T unmarshal(@NotNull Source in) throws JAXBException {
/* 261 */     return unmarshal(in, (AttachmentUnmarshaller)null);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public final T unmarshal(@NotNull Source in, @Nullable AttachmentUnmarshaller au) throws JAXBException {
/* 267 */     Unmarshaller u = (Unmarshaller)this.context.unmarshallerPool.take();
/* 268 */     u.setAttachmentUnmarshaller(au);
/* 269 */     return exit(unmarshal(u, in), u);
/*     */   } @NotNull
/*     */   public final T unmarshal(@NotNull BridgeContext context, @NotNull Source in) throws JAXBException {
/* 272 */     return unmarshal((Unmarshaller)((BridgeContextImpl)context).unmarshaller, in);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public abstract T unmarshal(@NotNull Unmarshaller paramUnmarshaller, @NotNull Source paramSource) throws JAXBException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public final T unmarshal(@NotNull InputStream in) throws JAXBException {
/* 293 */     Unmarshaller u = (Unmarshaller)this.context.unmarshallerPool.take();
/* 294 */     return exit(unmarshal(u, in), u);
/*     */   } @NotNull
/*     */   public final T unmarshal(@NotNull BridgeContext context, @NotNull InputStream in) throws JAXBException {
/* 297 */     return unmarshal((Unmarshaller)((BridgeContextImpl)context).unmarshaller, in);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public abstract T unmarshal(@NotNull Unmarshaller paramUnmarshaller, @NotNull InputStream paramInputStream) throws JAXBException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public final T unmarshal(@NotNull Node n) throws JAXBException {
/* 316 */     return unmarshal(n, (AttachmentUnmarshaller)null);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public final T unmarshal(@NotNull Node n, @Nullable AttachmentUnmarshaller au) throws JAXBException {
/* 322 */     Unmarshaller u = (Unmarshaller)this.context.unmarshallerPool.take();
/* 323 */     u.setAttachmentUnmarshaller(au);
/* 324 */     return exit(unmarshal(u, n), u);
/*     */   } @NotNull
/*     */   public final T unmarshal(@NotNull BridgeContext context, @NotNull Node n) throws JAXBException {
/* 327 */     return unmarshal((Unmarshaller)((BridgeContextImpl)context).unmarshaller, n);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public abstract T unmarshal(@NotNull Unmarshaller paramUnmarshaller, @NotNull Node paramNode) throws JAXBException;
/*     */   
/*     */   public abstract TypeReference getTypeReference();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\api\Bridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */