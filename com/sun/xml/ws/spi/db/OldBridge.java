/*     */ package com.sun.xml.ws.spi.db;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.bind.api.BridgeContext;
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
/*     */ public abstract class OldBridge<T>
/*     */ {
/*     */   protected final JAXBContextImpl context;
/*     */   
/*     */   protected OldBridge(JAXBContextImpl context) {
/*  85 */     this.context = context;
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
/*     */   public BindingContext getContext() {
/*  97 */     return null;
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
/* 108 */     marshal(object, output, (AttachmentMarshaller)null);
/*     */   }
/*     */   public final void marshal(T object, XMLStreamWriter output, AttachmentMarshaller am) throws JAXBException {
/* 111 */     Marshaller m = (Marshaller)this.context.marshallerPool.take();
/* 112 */     m.setAttachmentMarshaller(am);
/* 113 */     marshal(m, object, output);
/* 114 */     m.setAttachmentMarshaller(null);
/* 115 */     this.context.marshallerPool.recycle(m);
/*     */   }
/*     */   
/*     */   public final void marshal(@NotNull BridgeContext context, T object, XMLStreamWriter output) throws JAXBException {
/* 119 */     marshal((Marshaller)((BridgeContextImpl)context).marshaller, object, output);
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
/* 139 */     marshal(object, output, nsContext, (AttachmentMarshaller)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(T object, OutputStream output, NamespaceContext nsContext, AttachmentMarshaller am) throws JAXBException {
/* 145 */     Marshaller m = (Marshaller)this.context.marshallerPool.take();
/* 146 */     m.setAttachmentMarshaller(am);
/* 147 */     marshal(m, object, output, nsContext);
/* 148 */     m.setAttachmentMarshaller(null);
/* 149 */     this.context.marshallerPool.recycle(m);
/*     */   }
/*     */   
/*     */   public final void marshal(@NotNull BridgeContext context, T object, OutputStream output, NamespaceContext nsContext) throws JAXBException {
/* 153 */     marshal((Marshaller)((BridgeContextImpl)context).marshaller, object, output, nsContext);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract void marshal(@NotNull Marshaller paramMarshaller, T paramT, OutputStream paramOutputStream, NamespaceContext paramNamespaceContext) throws JAXBException;
/*     */   
/*     */   public final void marshal(T object, Node output) throws JAXBException {
/* 160 */     Marshaller m = (Marshaller)this.context.marshallerPool.take();
/* 161 */     marshal(m, object, output);
/* 162 */     this.context.marshallerPool.recycle(m);
/*     */   }
/*     */   
/*     */   public final void marshal(@NotNull BridgeContext context, T object, Node output) throws JAXBException {
/* 166 */     marshal((Marshaller)((BridgeContextImpl)context).marshaller, object, output);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void marshal(@NotNull Marshaller paramMarshaller, T paramT, Node paramNode) throws JAXBException;
/*     */ 
/*     */ 
/*     */   
/*     */   public final void marshal(T object, ContentHandler contentHandler) throws JAXBException {
/* 176 */     marshal(object, contentHandler, (AttachmentMarshaller)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void marshal(T object, ContentHandler contentHandler, AttachmentMarshaller am) throws JAXBException {
/* 182 */     Marshaller m = (Marshaller)this.context.marshallerPool.take();
/* 183 */     m.setAttachmentMarshaller(am);
/* 184 */     marshal(m, object, contentHandler);
/* 185 */     m.setAttachmentMarshaller(null);
/* 186 */     this.context.marshallerPool.recycle(m);
/*     */   }
/*     */   public final void marshal(@NotNull BridgeContext context, T object, ContentHandler contentHandler) throws JAXBException {
/* 189 */     marshal((Marshaller)((BridgeContextImpl)context).marshaller, object, contentHandler);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract void marshal(@NotNull Marshaller paramMarshaller, T paramT, ContentHandler paramContentHandler) throws JAXBException;
/*     */ 
/*     */   
/*     */   public final void marshal(T object, Result result) throws JAXBException {
/* 197 */     Marshaller m = (Marshaller)this.context.marshallerPool.take();
/* 198 */     marshal(m, object, result);
/* 199 */     this.context.marshallerPool.recycle(m);
/*     */   }
/*     */   public final void marshal(@NotNull BridgeContext context, T object, Result result) throws JAXBException {
/* 202 */     marshal((Marshaller)((BridgeContextImpl)context).marshaller, object, result);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract void marshal(@NotNull Marshaller paramMarshaller, T paramT, Result paramResult) throws JAXBException;
/*     */   
/*     */   private T exit(T r, Unmarshaller u) {
/* 209 */     u.setAttachmentUnmarshaller(null);
/* 210 */     this.context.unmarshallerPool.recycle(u);
/* 211 */     return r;
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
/* 231 */     return unmarshal(in, (AttachmentUnmarshaller)null);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public final T unmarshal(@NotNull XMLStreamReader in, @Nullable AttachmentUnmarshaller au) throws JAXBException {
/* 237 */     Unmarshaller u = (Unmarshaller)this.context.unmarshallerPool.take();
/* 238 */     u.setAttachmentUnmarshaller(au);
/* 239 */     return exit(unmarshal(u, in), u);
/*     */   } @NotNull
/*     */   public final T unmarshal(@NotNull BridgeContext context, @NotNull XMLStreamReader in) throws JAXBException {
/* 242 */     return unmarshal((Unmarshaller)((BridgeContextImpl)context).unmarshaller, in);
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
/* 263 */     return unmarshal(in, (AttachmentUnmarshaller)null);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public final T unmarshal(@NotNull Source in, @Nullable AttachmentUnmarshaller au) throws JAXBException {
/* 269 */     Unmarshaller u = (Unmarshaller)this.context.unmarshallerPool.take();
/* 270 */     u.setAttachmentUnmarshaller(au);
/* 271 */     return exit(unmarshal(u, in), u);
/*     */   } @NotNull
/*     */   public final T unmarshal(@NotNull BridgeContext context, @NotNull Source in) throws JAXBException {
/* 274 */     return unmarshal((Unmarshaller)((BridgeContextImpl)context).unmarshaller, in);
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
/* 295 */     Unmarshaller u = (Unmarshaller)this.context.unmarshallerPool.take();
/* 296 */     return exit(unmarshal(u, in), u);
/*     */   } @NotNull
/*     */   public final T unmarshal(@NotNull BridgeContext context, @NotNull InputStream in) throws JAXBException {
/* 299 */     return unmarshal((Unmarshaller)((BridgeContextImpl)context).unmarshaller, in);
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
/* 318 */     return unmarshal(n, (AttachmentUnmarshaller)null);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public final T unmarshal(@NotNull Node n, @Nullable AttachmentUnmarshaller au) throws JAXBException {
/* 324 */     Unmarshaller u = (Unmarshaller)this.context.unmarshallerPool.take();
/* 325 */     u.setAttachmentUnmarshaller(au);
/* 326 */     return exit(unmarshal(u, n), u);
/*     */   } @NotNull
/*     */   public final T unmarshal(@NotNull BridgeContext context, @NotNull Node n) throws JAXBException {
/* 329 */     return unmarshal((Unmarshaller)((BridgeContextImpl)context).unmarshaller, n);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public abstract T unmarshal(@NotNull Unmarshaller paramUnmarshaller, @NotNull Node paramNode) throws JAXBException;
/*     */   
/*     */   public abstract TypeInfo getTypeReference();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\spi\db\OldBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */