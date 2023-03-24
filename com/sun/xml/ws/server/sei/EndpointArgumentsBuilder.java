/*     */ package com.sun.xml.ws.server.sei;
/*     */ 
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.message.Attachment;
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.model.ParameterBinding;
/*     */ import com.sun.xml.ws.api.streaming.XMLStreamReaderFactory;
/*     */ import com.sun.xml.ws.encoding.DataHandlerDataSource;
/*     */ import com.sun.xml.ws.encoding.StringDataContentHandler;
/*     */ import com.sun.xml.ws.message.AttachmentUnmarshallerImpl;
/*     */ import com.sun.xml.ws.model.ParameterImpl;
/*     */ import com.sun.xml.ws.model.WrapperParameter;
/*     */ import com.sun.xml.ws.resources.ServerMessages;
/*     */ import com.sun.xml.ws.spi.db.DatabindingException;
/*     */ import com.sun.xml.ws.spi.db.PropertyAccessor;
/*     */ import com.sun.xml.ws.spi.db.RepeatedElementBridge;
/*     */ import com.sun.xml.ws.spi.db.WrapperComposite;
/*     */ import com.sun.xml.ws.spi.db.XMLBridge;
/*     */ import com.sun.xml.ws.streaming.XMLStreamReaderUtil;
/*     */ import java.awt.Image;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.net.URLDecoder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.activation.DataSource;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.jws.WebParam;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.attachment.AttachmentUnmarshaller;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFault;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.soap.SOAPFaultException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EndpointArgumentsBuilder
/*     */ {
/*     */   static final class None
/*     */     extends EndpointArgumentsBuilder
/*     */   {
/*     */     private None() {}
/*     */     
/*     */     public void readRequest(Message msg, Object[] args) {
/* 116 */       msg.consume();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 124 */   public static final EndpointArgumentsBuilder NONE = new None();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object getVMUninitializedValue(Type type) {
/* 136 */     return primitiveUninitializedValues.get(type);
/*     */   }
/*     */   
/* 139 */   private static final Map<Class, Object> primitiveUninitializedValues = (Map)new HashMap<Class<?>, Object>();
/*     */   
/*     */   static {
/* 142 */     Map<Class<?>, Object> m = primitiveUninitializedValues;
/* 143 */     m.put(int.class, Integer.valueOf(0));
/* 144 */     m.put(char.class, Character.valueOf(false));
/* 145 */     m.put(byte.class, Byte.valueOf((byte)0));
/* 146 */     m.put(short.class, Short.valueOf((short)0));
/* 147 */     m.put(long.class, Long.valueOf(0L));
/* 148 */     m.put(float.class, Float.valueOf(0.0F));
/* 149 */     m.put(double.class, Double.valueOf(0.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected QName wrapperName;
/*     */ 
/*     */   
/*     */   static final class WrappedPartBuilder
/*     */   {
/*     */     private final XMLBridge bridge;
/*     */     
/*     */     private final EndpointValueSetter setter;
/*     */ 
/*     */     
/*     */     public WrappedPartBuilder(XMLBridge bridge, EndpointValueSetter setter) {
/* 165 */       this.bridge = bridge;
/* 166 */       this.setter = setter;
/*     */     }
/*     */     
/*     */     void readRequest(Object[] args, XMLStreamReader r, AttachmentSet att) throws JAXBException {
/* 170 */       Object obj = null;
/* 171 */       AttachmentUnmarshallerImpl au = (att != null) ? new AttachmentUnmarshallerImpl(att) : null;
/* 172 */       if (this.bridge instanceof RepeatedElementBridge) {
/* 173 */         RepeatedElementBridge rbridge = (RepeatedElementBridge)this.bridge;
/* 174 */         ArrayList<Object> list = new ArrayList();
/* 175 */         QName name = r.getName();
/* 176 */         while (r.getEventType() == 1 && name.equals(r.getName())) {
/* 177 */           list.add(rbridge.unmarshal(r, (AttachmentUnmarshaller)au));
/* 178 */           XMLStreamReaderUtil.toNextTag(r, name);
/*     */         } 
/* 180 */         obj = rbridge.collectionHandler().convert(list);
/*     */       } else {
/* 182 */         obj = this.bridge.unmarshal(r, (AttachmentUnmarshaller)au);
/*     */       } 
/* 184 */       this.setter.put(obj, args);
/*     */     }
/*     */   }
/*     */   
/* 188 */   protected Map<QName, WrappedPartBuilder> wrappedParts = null;
/*     */   
/*     */   protected void readWrappedRequest(Message msg, Object[] args) throws JAXBException, XMLStreamException {
/* 191 */     if (!msg.hasPayload()) {
/* 192 */       throw new WebServiceException("No payload. Expecting payload with " + this.wrapperName + " element");
/*     */     }
/* 194 */     XMLStreamReader reader = msg.readPayload();
/* 195 */     XMLStreamReaderUtil.verifyTag(reader, this.wrapperName);
/* 196 */     reader.nextTag();
/* 197 */     while (reader.getEventType() == 1) {
/*     */       
/* 199 */       QName name = reader.getName();
/* 200 */       WrappedPartBuilder part = this.wrappedParts.get(name);
/* 201 */       if (part == null) {
/*     */         
/* 203 */         XMLStreamReaderUtil.skipElement(reader);
/* 204 */         reader.nextTag();
/*     */       } else {
/* 206 */         part.readRequest(args, reader, msg.getAttachments());
/*     */       } 
/* 208 */       XMLStreamReaderUtil.toNextTag(reader, name);
/*     */     } 
/*     */ 
/*     */     
/* 212 */     reader.close();
/* 213 */     XMLStreamReaderFactory.recycle(reader);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class NullSetter
/*     */     extends EndpointArgumentsBuilder
/*     */   {
/*     */     private final EndpointValueSetter setter;
/*     */     private final Object nullValue;
/*     */     
/*     */     public NullSetter(EndpointValueSetter setter, Object nullValue) {
/* 224 */       assert setter != null;
/* 225 */       this.nullValue = nullValue;
/* 226 */       this.setter = setter;
/*     */     }
/*     */     public void readRequest(Message msg, Object[] args) {
/* 229 */       this.setter.put(this.nullValue, args);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Composite
/*     */     extends EndpointArgumentsBuilder
/*     */   {
/*     */     private final EndpointArgumentsBuilder[] builders;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Composite(EndpointArgumentsBuilder... builders) {
/* 252 */       this.builders = builders;
/*     */     }
/*     */     
/*     */     public Composite(Collection<? extends EndpointArgumentsBuilder> builders) {
/* 256 */       this(builders.<EndpointArgumentsBuilder>toArray(new EndpointArgumentsBuilder[builders.size()]));
/*     */     }
/*     */     
/*     */     public void readRequest(Message msg, Object[] args) throws JAXBException, XMLStreamException {
/* 260 */       for (EndpointArgumentsBuilder builder : this.builders) {
/* 261 */         builder.readRequest(msg, args);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static abstract class AttachmentBuilder
/*     */     extends EndpointArgumentsBuilder
/*     */   {
/*     */     protected final EndpointValueSetter setter;
/*     */     
/*     */     protected final ParameterImpl param;
/*     */     protected final String pname;
/*     */     protected final String pname1;
/*     */     
/*     */     AttachmentBuilder(ParameterImpl param, EndpointValueSetter setter) {
/* 277 */       this.setter = setter;
/* 278 */       this.param = param;
/* 279 */       this.pname = param.getPartName();
/* 280 */       this.pname1 = "<" + this.pname;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static EndpointArgumentsBuilder createAttachmentBuilder(ParameterImpl param, EndpointValueSetter setter) {
/* 293 */       Class<?> type = (Class)(param.getTypeInfo()).type;
/* 294 */       if (DataHandler.class.isAssignableFrom(type))
/* 295 */         return new EndpointArgumentsBuilder.DataHandlerBuilder(param, setter); 
/* 296 */       if (byte[].class == type)
/* 297 */         return new EndpointArgumentsBuilder.ByteArrayBuilder(param, setter); 
/* 298 */       if (Source.class.isAssignableFrom(type))
/* 299 */         return new EndpointArgumentsBuilder.SourceBuilder(param, setter); 
/* 300 */       if (Image.class.isAssignableFrom(type))
/* 301 */         return new EndpointArgumentsBuilder.ImageBuilder(param, setter); 
/* 302 */       if (InputStream.class == type)
/* 303 */         return new EndpointArgumentsBuilder.InputStreamBuilder(param, setter); 
/* 304 */       if (EndpointArgumentsBuilder.isXMLMimeType(param.getBinding().getMimeType()))
/* 305 */         return new EndpointArgumentsBuilder.JAXBBuilder(param, setter); 
/* 306 */       if (String.class.isAssignableFrom(type)) {
/* 307 */         return new EndpointArgumentsBuilder.StringBuilder(param, setter);
/*     */       }
/* 309 */       throw new UnsupportedOperationException("Unknown Type=" + type + " Attachment is not mapped.");
/*     */     }
/*     */ 
/*     */     
/*     */     public void readRequest(Message msg, Object[] args) throws JAXBException, XMLStreamException {
/* 314 */       boolean foundAttachment = false;
/*     */       
/* 316 */       for (Attachment att : msg.getAttachments()) {
/* 317 */         String part = getWSDLPartName(att);
/* 318 */         if (part == null) {
/*     */           continue;
/*     */         }
/* 321 */         if (part.equals(this.pname) || part.equals(this.pname1)) {
/* 322 */           foundAttachment = true;
/* 323 */           mapAttachment(att, args);
/*     */           break;
/*     */         } 
/*     */       } 
/* 327 */       if (!foundAttachment)
/* 328 */         throw new WebServiceException("Missing Attachment for " + this.pname); 
/*     */     }
/*     */     
/*     */     abstract void mapAttachment(Attachment param1Attachment, Object[] param1ArrayOfObject) throws JAXBException;
/*     */   }
/*     */   
/*     */   private static final class DataHandlerBuilder
/*     */     extends AttachmentBuilder {
/*     */     DataHandlerBuilder(ParameterImpl param, EndpointValueSetter setter) {
/* 337 */       super(param, setter);
/*     */     }
/*     */     
/*     */     void mapAttachment(Attachment att, Object[] args) {
/* 341 */       this.setter.put(att.asDataHandler(), args);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class ByteArrayBuilder extends AttachmentBuilder {
/*     */     ByteArrayBuilder(ParameterImpl param, EndpointValueSetter setter) {
/* 347 */       super(param, setter);
/*     */     }
/*     */     
/*     */     void mapAttachment(Attachment att, Object[] args) {
/* 351 */       this.setter.put(att.asByteArray(), args);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class SourceBuilder extends AttachmentBuilder {
/*     */     SourceBuilder(ParameterImpl param, EndpointValueSetter setter) {
/* 357 */       super(param, setter);
/*     */     }
/*     */     
/*     */     void mapAttachment(Attachment att, Object[] args) {
/* 361 */       this.setter.put(att.asSource(), args);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class ImageBuilder extends AttachmentBuilder {
/*     */     ImageBuilder(ParameterImpl param, EndpointValueSetter setter) {
/* 367 */       super(param, setter);
/*     */     }
/*     */     
/*     */     void mapAttachment(Attachment att, Object[] args) {
/*     */       Image image;
/* 372 */       InputStream is = null;
/*     */       try {
/* 374 */         is = att.asInputStream();
/* 375 */         image = ImageIO.read(is);
/* 376 */       } catch (IOException ioe) {
/* 377 */         throw new WebServiceException(ioe);
/*     */       } finally {
/* 379 */         if (is != null) {
/*     */           try {
/* 381 */             is.close();
/* 382 */           } catch (IOException ioe) {
/* 383 */             throw new WebServiceException(ioe);
/*     */           } 
/*     */         }
/*     */       } 
/* 387 */       this.setter.put(image, args);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class InputStreamBuilder extends AttachmentBuilder {
/*     */     InputStreamBuilder(ParameterImpl param, EndpointValueSetter setter) {
/* 393 */       super(param, setter);
/*     */     }
/*     */     
/*     */     void mapAttachment(Attachment att, Object[] args) {
/* 397 */       this.setter.put(att.asInputStream(), args);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class JAXBBuilder extends AttachmentBuilder {
/*     */     JAXBBuilder(ParameterImpl param, EndpointValueSetter setter) {
/* 403 */       super(param, setter);
/*     */     }
/*     */     
/*     */     void mapAttachment(Attachment att, Object[] args) throws JAXBException {
/* 407 */       Object obj = this.param.getXMLBridge().unmarshal(att.asInputStream());
/* 408 */       this.setter.put(obj, args);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class StringBuilder extends AttachmentBuilder {
/*     */     StringBuilder(ParameterImpl param, EndpointValueSetter setter) {
/* 414 */       super(param, setter);
/*     */     }
/*     */     
/*     */     void mapAttachment(Attachment att, Object[] args) {
/* 418 */       att.getContentType();
/* 419 */       StringDataContentHandler sdh = new StringDataContentHandler();
/*     */       try {
/* 421 */         String str = (String)sdh.getContent((DataSource)new DataHandlerDataSource(att.asDataHandler()));
/* 422 */         this.setter.put(str, args);
/* 423 */       } catch (Exception e) {
/* 424 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String getWSDLPartName(Attachment att) {
/* 455 */     String cId = att.getContentId();
/*     */     
/* 457 */     int index = cId.lastIndexOf('@', cId.length());
/* 458 */     if (index == -1) {
/* 459 */       return null;
/*     */     }
/* 461 */     String localPart = cId.substring(0, index);
/* 462 */     index = localPart.lastIndexOf('=', localPart.length());
/* 463 */     if (index == -1) {
/* 464 */       return null;
/*     */     }
/*     */     try {
/* 467 */       return URLDecoder.decode(localPart.substring(0, index), "UTF-8");
/* 468 */     } catch (UnsupportedEncodingException e) {
/* 469 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Header
/*     */     extends EndpointArgumentsBuilder
/*     */   {
/*     */     private final XMLBridge<?> bridge;
/*     */ 
/*     */ 
/*     */     
/*     */     private final EndpointValueSetter setter;
/*     */ 
/*     */     
/*     */     private final QName headerName;
/*     */ 
/*     */     
/*     */     private final SOAPVersion soapVersion;
/*     */ 
/*     */ 
/*     */     
/*     */     public Header(SOAPVersion soapVersion, QName name, XMLBridge<?> bridge, EndpointValueSetter setter) {
/* 494 */       this.soapVersion = soapVersion;
/* 495 */       this.headerName = name;
/* 496 */       this.bridge = bridge;
/* 497 */       this.setter = setter;
/*     */     }
/*     */     
/*     */     public Header(SOAPVersion soapVersion, ParameterImpl param, EndpointValueSetter setter) {
/* 501 */       this(soapVersion, (param.getTypeInfo()).tagName, param.getXMLBridge(), setter);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 506 */       assert param.getOutBinding() == ParameterBinding.HEADER;
/*     */     }
/*     */     
/*     */     private SOAPFaultException createDuplicateHeaderException() {
/*     */       try {
/* 511 */         SOAPFault fault = this.soapVersion.getSOAPFactory().createFault();
/* 512 */         fault.setFaultCode(this.soapVersion.faultCodeClient);
/* 513 */         fault.setFaultString(ServerMessages.DUPLICATE_PORT_KNOWN_HEADER(this.headerName));
/* 514 */         return new SOAPFaultException(fault);
/* 515 */       } catch (SOAPException e) {
/* 516 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void readRequest(Message msg, Object[] args) throws JAXBException {
/* 521 */       com.sun.xml.ws.api.message.Header header = null;
/* 522 */       Iterator<com.sun.xml.ws.api.message.Header> it = msg.getMessageHeaders().getHeaders(this.headerName, true);
/*     */       
/* 524 */       if (it.hasNext()) {
/* 525 */         header = it.next();
/* 526 */         if (it.hasNext()) {
/* 527 */           throw createDuplicateHeaderException();
/*     */         }
/*     */       } 
/*     */       
/* 531 */       if (header != null) {
/* 532 */         this.setter.put(header.readAsJAXB(this.bridge), args);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Body
/*     */     extends EndpointArgumentsBuilder
/*     */   {
/*     */     private final XMLBridge<?> bridge;
/*     */ 
/*     */ 
/*     */     
/*     */     private final EndpointValueSetter setter;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Body(XMLBridge<?> bridge, EndpointValueSetter setter) {
/* 553 */       this.bridge = bridge;
/* 554 */       this.setter = setter;
/*     */     }
/*     */     
/*     */     public void readRequest(Message msg, Object[] args) throws JAXBException {
/* 558 */       this.setter.put(msg.readPayloadAsJAXB(this.bridge), args);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class DocLit
/*     */     extends EndpointArgumentsBuilder
/*     */   {
/*     */     private final PartBuilder[] parts;
/*     */ 
/*     */     
/*     */     private final XMLBridge wrapper;
/*     */     
/*     */     private boolean dynamicWrapper;
/*     */ 
/*     */     
/*     */     public DocLit(WrapperParameter wp, WebParam.Mode skipMode) {
/* 576 */       this.wrapperName = wp.getName();
/* 577 */       this.wrapper = wp.getXMLBridge();
/* 578 */       Class wrapperType = (Class)(this.wrapper.getTypeInfo()).type;
/* 579 */       this.dynamicWrapper = WrapperComposite.class.equals(wrapperType);
/* 580 */       List<PartBuilder> parts = new ArrayList<PartBuilder>();
/* 581 */       List<ParameterImpl> children = wp.getWrapperChildren();
/* 582 */       for (ParameterImpl p : children) {
/* 583 */         if (p.getMode() == skipMode) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 590 */         QName name = p.getName();
/*     */         try {
/* 592 */           if (this.dynamicWrapper) {
/* 593 */             if (this.wrappedParts == null) this.wrappedParts = new HashMap<QName, EndpointArgumentsBuilder.WrappedPartBuilder>(); 
/* 594 */             XMLBridge xmlBridge = p.getInlinedRepeatedElementBridge();
/* 595 */             if (xmlBridge == null) xmlBridge = p.getXMLBridge(); 
/* 596 */             this.wrappedParts.put(p.getName(), new EndpointArgumentsBuilder.WrappedPartBuilder(xmlBridge, EndpointValueSetter.get(p))); continue;
/*     */           } 
/* 598 */           parts.add(new PartBuilder(wp.getOwner().getBindingContext().getElementPropertyAccessor(wrapperType, name.getNamespaceURI(), p.getName().getLocalPart()), EndpointValueSetter.get(p)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 607 */           assert p.getBinding() == ParameterBinding.BODY;
/*     */         }
/* 609 */         catch (JAXBException e) {
/* 610 */           throw new WebServiceException(wrapperType + " do not have a property of the name " + name, e);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 615 */       this.parts = parts.<PartBuilder>toArray(new PartBuilder[parts.size()]);
/*     */     }
/*     */     
/*     */     public void readRequest(Message msg, Object[] args) throws JAXBException, XMLStreamException {
/* 619 */       if (this.dynamicWrapper) {
/* 620 */         readWrappedRequest(msg, args);
/*     */       }
/* 622 */       else if (this.parts.length > 0) {
/* 623 */         if (!msg.hasPayload()) {
/* 624 */           throw new WebServiceException("No payload. Expecting payload with " + this.wrapperName + " element");
/*     */         }
/* 626 */         XMLStreamReader reader = msg.readPayload();
/* 627 */         XMLStreamReaderUtil.verifyTag(reader, this.wrapperName);
/* 628 */         Object wrapperBean = this.wrapper.unmarshal(reader, (msg.getAttachments() != null) ? (AttachmentUnmarshaller)new AttachmentUnmarshallerImpl(msg.getAttachments()) : null);
/*     */ 
/*     */         
/*     */         try {
/* 632 */           for (PartBuilder part : this.parts) {
/* 633 */             part.readRequest(args, wrapperBean);
/*     */           }
/* 635 */         } catch (DatabindingException e) {
/*     */           
/* 637 */           throw new WebServiceException(e);
/*     */         } 
/*     */ 
/*     */         
/* 641 */         reader.close();
/* 642 */         XMLStreamReaderFactory.recycle(reader);
/*     */       } else {
/* 644 */         msg.consume();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static final class PartBuilder
/*     */     {
/*     */       private final PropertyAccessor accessor;
/*     */ 
/*     */ 
/*     */       
/*     */       private final EndpointValueSetter setter;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public PartBuilder(PropertyAccessor accessor, EndpointValueSetter setter) {
/* 664 */         this.accessor = accessor;
/* 665 */         this.setter = setter;
/* 666 */         assert accessor != null && setter != null;
/*     */       }
/*     */       
/*     */       final void readRequest(Object[] args, Object wrapperBean) {
/* 670 */         Object obj = this.accessor.get(wrapperBean);
/* 671 */         this.setter.put(obj, args);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class RpcLit
/*     */     extends EndpointArgumentsBuilder
/*     */   {
/*     */     public RpcLit(WrapperParameter wp) {
/* 684 */       assert (wp.getTypeInfo()).type == WrapperComposite.class;
/*     */       
/* 686 */       this.wrapperName = wp.getName();
/* 687 */       this.wrappedParts = new HashMap<QName, EndpointArgumentsBuilder.WrappedPartBuilder>();
/* 688 */       List<ParameterImpl> children = wp.getWrapperChildren();
/* 689 */       for (ParameterImpl p : children) {
/* 690 */         this.wrappedParts.put(p.getName(), new EndpointArgumentsBuilder.WrappedPartBuilder(p.getXMLBridge(), EndpointValueSetter.get(p)));
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 695 */         assert p.getBinding() == ParameterBinding.BODY;
/*     */       } 
/*     */     }
/*     */     
/*     */     public void readRequest(Message msg, Object[] args) throws JAXBException, XMLStreamException {
/* 700 */       readWrappedRequest(msg, args);
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean isXMLMimeType(String mimeType) {
/* 705 */     return (mimeType.equals("text/xml") || mimeType.equals("application/xml"));
/*     */   }
/*     */   
/*     */   public abstract void readRequest(Message paramMessage, Object[] paramArrayOfObject) throws JAXBException, XMLStreamException;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\sei\EndpointArgumentsBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */