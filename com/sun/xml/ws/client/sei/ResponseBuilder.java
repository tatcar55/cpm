/*     */ package com.sun.xml.ws.client.sei;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ResponseBuilder
/*     */ {
/*     */   static final class WrappedPartBuilder
/*     */   {
/*     */     private final XMLBridge bridge;
/*     */     private final ValueSetter setter;
/*     */     
/*     */     public WrappedPartBuilder(XMLBridge bridge, ValueSetter setter) {
/* 118 */       this.bridge = bridge;
/* 119 */       this.setter = setter;
/*     */     }
/*     */     final Object readResponse(Object[] args, XMLStreamReader r, AttachmentSet att) throws JAXBException {
/*     */       Object obj;
/* 123 */       AttachmentUnmarshallerImpl au = (att != null) ? new AttachmentUnmarshallerImpl(att) : null;
/* 124 */       if (this.bridge instanceof RepeatedElementBridge) {
/* 125 */         RepeatedElementBridge rbridge = (RepeatedElementBridge)this.bridge;
/* 126 */         ArrayList<Object> list = new ArrayList();
/* 127 */         QName name = r.getName();
/* 128 */         while (r.getEventType() == 1 && name.equals(r.getName())) {
/* 129 */           list.add(rbridge.unmarshal(r, (AttachmentUnmarshaller)au));
/* 130 */           XMLStreamReaderUtil.toNextTag(r, name);
/*     */         } 
/* 132 */         obj = rbridge.collectionHandler().convert(list);
/*     */       } else {
/* 134 */         obj = this.bridge.unmarshal(r, (AttachmentUnmarshaller)au);
/*     */       } 
/* 136 */       return this.setter.put(obj, args);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 142 */   protected Map<QName, WrappedPartBuilder> wrappedParts = null;
/*     */   protected QName wrapperName;
/*     */   
/*     */   protected Object readWrappedResponse(Message msg, Object[] args) throws JAXBException, XMLStreamException {
/* 146 */     Object retVal = null;
/*     */     
/* 148 */     if (!msg.hasPayload()) {
/* 149 */       throw new WebServiceException("No payload. Expecting payload with " + this.wrapperName + " element");
/*     */     }
/* 151 */     XMLStreamReader reader = msg.readPayload();
/* 152 */     XMLStreamReaderUtil.verifyTag(reader, this.wrapperName);
/* 153 */     reader.nextTag();
/*     */     
/* 155 */     while (reader.getEventType() == 1) {
/*     */       
/* 157 */       WrappedPartBuilder part = this.wrappedParts.get(reader.getName());
/* 158 */       if (part == null) {
/*     */         
/* 160 */         XMLStreamReaderUtil.skipElement(reader);
/* 161 */         reader.nextTag();
/*     */       } else {
/* 163 */         Object o = part.readResponse(args, reader, msg.getAttachments());
/*     */         
/* 165 */         if (o != null) {
/* 166 */           assert retVal == null;
/* 167 */           retVal = o;
/*     */         } 
/*     */       } 
/*     */       
/* 171 */       if (reader.getEventType() != 1 && reader.getEventType() != 2)
/*     */       {
/* 173 */         XMLStreamReaderUtil.nextElementContent(reader);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 178 */     reader.close();
/* 179 */     XMLStreamReaderFactory.recycle(reader);
/*     */     
/* 181 */     return retVal;
/*     */   }
/*     */   
/*     */   static final class None
/*     */     extends ResponseBuilder {
/*     */     private None() {}
/*     */     
/*     */     public Object readResponse(Message msg, Object[] args) {
/* 189 */       msg.consume();
/* 190 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 198 */   public static final ResponseBuilder NONE = new None();
/*     */ 
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
/* 210 */     return primitiveUninitializedValues.get(type);
/*     */   }
/*     */   
/* 213 */   private static final Map<Class, Object> primitiveUninitializedValues = (Map)new HashMap<Class<?>, Object>();
/*     */   
/*     */   static {
/* 216 */     Map<Class<?>, Object> m = primitiveUninitializedValues;
/* 217 */     m.put(int.class, Integer.valueOf(0));
/* 218 */     m.put(char.class, Character.valueOf(false));
/* 219 */     m.put(byte.class, Byte.valueOf((byte)0));
/* 220 */     m.put(short.class, Short.valueOf((short)0));
/* 221 */     m.put(long.class, Long.valueOf(0L));
/* 222 */     m.put(float.class, Float.valueOf(0.0F));
/* 223 */     m.put(double.class, Double.valueOf(0.0D));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class NullSetter
/*     */     extends ResponseBuilder
/*     */   {
/*     */     private final ValueSetter setter;
/*     */     private final Object nullValue;
/*     */     
/*     */     public NullSetter(ValueSetter setter, Object nullValue) {
/* 234 */       assert setter != null;
/* 235 */       this.nullValue = nullValue;
/* 236 */       this.setter = setter;
/*     */     }
/*     */     
/*     */     public Object readResponse(Message msg, Object[] args) {
/* 240 */       return this.setter.put(this.nullValue, args);
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
/*     */     extends ResponseBuilder
/*     */   {
/*     */     private final ResponseBuilder[] builders;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Composite(ResponseBuilder... builders) {
/* 263 */       this.builders = builders;
/*     */     }
/*     */     
/*     */     public Composite(Collection<? extends ResponseBuilder> builders) {
/* 267 */       this(builders.<ResponseBuilder>toArray(new ResponseBuilder[builders.size()]));
/*     */     }
/*     */ 
/*     */     
/*     */     public Object readResponse(Message msg, Object[] args) throws JAXBException, XMLStreamException {
/* 272 */       Object retVal = null;
/* 273 */       for (ResponseBuilder builder : this.builders) {
/* 274 */         Object r = builder.readResponse(msg, args);
/*     */         
/* 276 */         if (r != null) {
/* 277 */           assert retVal == null;
/* 278 */           retVal = r;
/*     */         } 
/*     */       } 
/* 281 */       return retVal;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static abstract class AttachmentBuilder
/*     */     extends ResponseBuilder
/*     */   {
/*     */     protected final ValueSetter setter;
/*     */     protected final ParameterImpl param;
/*     */     private final String pname;
/*     */     private final String pname1;
/*     */     
/*     */     AttachmentBuilder(ParameterImpl param, ValueSetter setter) {
/* 295 */       this.setter = setter;
/* 296 */       this.param = param;
/* 297 */       this.pname = param.getPartName();
/* 298 */       this.pname1 = "<" + this.pname;
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
/*     */     public static ResponseBuilder createAttachmentBuilder(ParameterImpl param, ValueSetter setter) {
/* 311 */       Class<?> type = (Class)(param.getTypeInfo()).type;
/* 312 */       if (DataHandler.class.isAssignableFrom(type))
/* 313 */         return new ResponseBuilder.DataHandlerBuilder(param, setter); 
/* 314 */       if (byte[].class == type)
/* 315 */         return new ResponseBuilder.ByteArrayBuilder(param, setter); 
/* 316 */       if (Source.class.isAssignableFrom(type))
/* 317 */         return new ResponseBuilder.SourceBuilder(param, setter); 
/* 318 */       if (Image.class.isAssignableFrom(type))
/* 319 */         return new ResponseBuilder.ImageBuilder(param, setter); 
/* 320 */       if (InputStream.class == type)
/* 321 */         return new ResponseBuilder.InputStreamBuilder(param, setter); 
/* 322 */       if (ResponseBuilder.isXMLMimeType(param.getBinding().getMimeType()))
/* 323 */         return new ResponseBuilder.JAXBBuilder(param, setter); 
/* 324 */       if (String.class.isAssignableFrom(type)) {
/* 325 */         return new ResponseBuilder.StringBuilder(param, setter);
/*     */       }
/* 327 */       throw new UnsupportedOperationException("Unexpected Attachment type =" + type);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object readResponse(Message msg, Object[] args) throws JAXBException, XMLStreamException {
/* 334 */       for (Attachment att : msg.getAttachments()) {
/* 335 */         String part = getWSDLPartName(att);
/* 336 */         if (part == null) {
/*     */           continue;
/*     */         }
/* 339 */         if (part.equals(this.pname) || part.equals(this.pname1)) {
/* 340 */           return mapAttachment(att, args);
/*     */         }
/*     */       } 
/* 343 */       return null;
/*     */     }
/*     */     
/*     */     abstract Object mapAttachment(Attachment param1Attachment, Object[] param1ArrayOfObject) throws JAXBException;
/*     */   }
/*     */   
/*     */   private static final class DataHandlerBuilder extends AttachmentBuilder {
/*     */     DataHandlerBuilder(ParameterImpl param, ValueSetter setter) {
/* 351 */       super(param, setter);
/*     */     }
/*     */ 
/*     */     
/*     */     Object mapAttachment(Attachment att, Object[] args) {
/* 356 */       return this.setter.put(att.asDataHandler(), args);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class StringBuilder extends AttachmentBuilder {
/*     */     StringBuilder(ParameterImpl param, ValueSetter setter) {
/* 362 */       super(param, setter);
/*     */     }
/*     */ 
/*     */     
/*     */     Object mapAttachment(Attachment att, Object[] args) {
/* 367 */       att.getContentType();
/* 368 */       StringDataContentHandler sdh = new StringDataContentHandler();
/*     */       try {
/* 370 */         String str = (String)sdh.getContent((DataSource)new DataHandlerDataSource(att.asDataHandler()));
/* 371 */         return this.setter.put(str, args);
/* 372 */       } catch (Exception e) {
/* 373 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class ByteArrayBuilder
/*     */     extends AttachmentBuilder {
/*     */     ByteArrayBuilder(ParameterImpl param, ValueSetter setter) {
/* 381 */       super(param, setter);
/*     */     }
/*     */ 
/*     */     
/*     */     Object mapAttachment(Attachment att, Object[] args) {
/* 386 */       return this.setter.put(att.asByteArray(), args);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class SourceBuilder extends AttachmentBuilder {
/*     */     SourceBuilder(ParameterImpl param, ValueSetter setter) {
/* 392 */       super(param, setter);
/*     */     }
/*     */ 
/*     */     
/*     */     Object mapAttachment(Attachment att, Object[] args) {
/* 397 */       return this.setter.put(att.asSource(), args);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class ImageBuilder extends AttachmentBuilder {
/*     */     ImageBuilder(ParameterImpl param, ValueSetter setter) {
/* 403 */       super(param, setter);
/*     */     }
/*     */ 
/*     */     
/*     */     Object mapAttachment(Attachment att, Object[] args) {
/*     */       Image image;
/* 409 */       InputStream is = null;
/*     */       try {
/* 411 */         is = att.asInputStream();
/* 412 */         image = ImageIO.read(is);
/* 413 */       } catch (IOException ioe) {
/* 414 */         throw new WebServiceException(ioe);
/*     */       } finally {
/* 416 */         if (is != null) {
/*     */           try {
/* 418 */             is.close();
/* 419 */           } catch (IOException ioe) {
/* 420 */             throw new WebServiceException(ioe);
/*     */           } 
/*     */         }
/*     */       } 
/* 424 */       return this.setter.put(image, args);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class InputStreamBuilder extends AttachmentBuilder {
/*     */     InputStreamBuilder(ParameterImpl param, ValueSetter setter) {
/* 430 */       super(param, setter);
/*     */     }
/*     */ 
/*     */     
/*     */     Object mapAttachment(Attachment att, Object[] args) {
/* 435 */       return this.setter.put(att.asInputStream(), args);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class JAXBBuilder extends AttachmentBuilder {
/*     */     JAXBBuilder(ParameterImpl param, ValueSetter setter) {
/* 441 */       super(param, setter);
/*     */     }
/*     */ 
/*     */     
/*     */     Object mapAttachment(Attachment att, Object[] args) throws JAXBException {
/* 446 */       Object obj = this.param.getXMLBridge().unmarshal(att.asInputStream());
/* 447 */       return this.setter.put(obj, args);
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
/*     */   
/*     */   public static final String getWSDLPartName(Attachment att) {
/* 478 */     String cId = att.getContentId();
/*     */     
/* 480 */     int index = cId.lastIndexOf('@', cId.length());
/* 481 */     if (index == -1) {
/* 482 */       return null;
/*     */     }
/* 484 */     String localPart = cId.substring(0, index);
/* 485 */     index = localPart.lastIndexOf('=', localPart.length());
/* 486 */     if (index == -1) {
/* 487 */       return null;
/*     */     }
/*     */     try {
/* 490 */       return URLDecoder.decode(localPart.substring(0, index), "UTF-8");
/* 491 */     } catch (UnsupportedEncodingException e) {
/* 492 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Header
/*     */     extends ResponseBuilder
/*     */   {
/*     */     private final XMLBridge<?> bridge;
/*     */ 
/*     */ 
/*     */     
/*     */     private final ValueSetter setter;
/*     */ 
/*     */     
/*     */     private final QName headerName;
/*     */ 
/*     */     
/*     */     private final SOAPVersion soapVersion;
/*     */ 
/*     */ 
/*     */     
/*     */     public Header(SOAPVersion soapVersion, QName name, XMLBridge<?> bridge, ValueSetter setter) {
/* 517 */       this.soapVersion = soapVersion;
/* 518 */       this.headerName = name;
/* 519 */       this.bridge = bridge;
/* 520 */       this.setter = setter;
/*     */     }
/*     */     
/*     */     public Header(SOAPVersion soapVersion, ParameterImpl param, ValueSetter setter) {
/* 524 */       this(soapVersion, (param.getTypeInfo()).tagName, param.getXMLBridge(), setter);
/*     */ 
/*     */ 
/*     */       
/* 528 */       assert param.getOutBinding() == ParameterBinding.HEADER;
/*     */     }
/*     */     
/*     */     private SOAPFaultException createDuplicateHeaderException() {
/*     */       try {
/* 533 */         SOAPFault fault = this.soapVersion.getSOAPFactory().createFault();
/* 534 */         fault.setFaultCode(this.soapVersion.faultCodeServer);
/* 535 */         fault.setFaultString(ServerMessages.DUPLICATE_PORT_KNOWN_HEADER(this.headerName));
/* 536 */         return new SOAPFaultException(fault);
/* 537 */       } catch (SOAPException e) {
/* 538 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Object readResponse(Message msg, Object[] args) throws JAXBException {
/* 544 */       com.sun.xml.ws.api.message.Header header = null;
/* 545 */       Iterator<com.sun.xml.ws.api.message.Header> it = msg.getMessageHeaders().getHeaders(this.headerName, true);
/*     */       
/* 547 */       if (it.hasNext()) {
/* 548 */         header = it.next();
/* 549 */         if (it.hasNext()) {
/* 550 */           throw createDuplicateHeaderException();
/*     */         }
/*     */       } 
/*     */       
/* 554 */       if (header != null) {
/* 555 */         return this.setter.put(header.readAsJAXB(this.bridge), args);
/*     */       }
/*     */       
/* 558 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Body
/*     */     extends ResponseBuilder
/*     */   {
/*     */     private final XMLBridge<?> bridge;
/*     */ 
/*     */     
/*     */     private final ValueSetter setter;
/*     */ 
/*     */ 
/*     */     
/*     */     public Body(XMLBridge<?> bridge, ValueSetter setter) {
/* 576 */       this.bridge = bridge;
/* 577 */       this.setter = setter;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object readResponse(Message msg, Object[] args) throws JAXBException {
/* 582 */       return this.setter.put(msg.readPayloadAsJAXB(this.bridge), args);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class DocLit
/*     */     extends ResponseBuilder
/*     */   {
/*     */     private final PartBuilder[] parts;
/*     */ 
/*     */     
/*     */     private final XMLBridge wrapper;
/*     */ 
/*     */     
/*     */     private boolean dynamicWrapper;
/*     */ 
/*     */     
/*     */     public DocLit(WrapperParameter wp, ValueSetterFactory setterFactory) {
/* 601 */       this.wrapperName = wp.getName();
/* 602 */       this.wrapper = wp.getXMLBridge();
/* 603 */       Class wrapperType = (Class)(this.wrapper.getTypeInfo()).type;
/* 604 */       this.dynamicWrapper = WrapperComposite.class.equals(wrapperType);
/*     */       
/* 606 */       List<PartBuilder> tempParts = new ArrayList<PartBuilder>();
/*     */       
/* 608 */       List<ParameterImpl> children = wp.getWrapperChildren();
/* 609 */       for (ParameterImpl p : children) {
/* 610 */         if (p.isIN())
/*     */           continue; 
/* 612 */         QName name = p.getName();
/* 613 */         if (this.dynamicWrapper) {
/* 614 */           if (this.wrappedParts == null) this.wrappedParts = new HashMap<QName, ResponseBuilder.WrappedPartBuilder>(); 
/* 615 */           XMLBridge xmlBridge = p.getInlinedRepeatedElementBridge();
/* 616 */           if (xmlBridge == null) xmlBridge = p.getXMLBridge(); 
/* 617 */           this.wrappedParts.put(p.getName(), new ResponseBuilder.WrappedPartBuilder(xmlBridge, setterFactory.get(p))); continue;
/*     */         } 
/*     */         try {
/* 620 */           tempParts.add(new PartBuilder(wp.getOwner().getBindingContext().getElementPropertyAccessor(wrapperType, name.getNamespaceURI(), p.getName().getLocalPart()), setterFactory.get(p)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 629 */           assert p.getBinding() == ParameterBinding.BODY;
/* 630 */         } catch (JAXBException e) {
/* 631 */           throw new WebServiceException(wrapperType + " do not have a property of the name " + name, e);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 636 */       this.parts = tempParts.<PartBuilder>toArray(new PartBuilder[tempParts.size()]);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object readResponse(Message msg, Object[] args) throws JAXBException, XMLStreamException {
/* 641 */       if (this.dynamicWrapper) return readWrappedResponse(msg, args); 
/* 642 */       Object retVal = null;
/*     */       
/* 644 */       if (this.parts.length > 0) {
/* 645 */         if (!msg.hasPayload()) {
/* 646 */           throw new WebServiceException("No payload. Expecting payload with " + this.wrapperName + " element");
/*     */         }
/* 648 */         XMLStreamReader reader = msg.readPayload();
/* 649 */         XMLStreamReaderUtil.verifyTag(reader, this.wrapperName);
/* 650 */         Object wrapperBean = this.wrapper.unmarshal(reader, (msg.getAttachments() != null) ? (AttachmentUnmarshaller)new AttachmentUnmarshallerImpl(msg.getAttachments()) : null);
/*     */ 
/*     */         
/*     */         try {
/* 654 */           for (PartBuilder part : this.parts) {
/* 655 */             Object o = part.readResponse(args, wrapperBean);
/*     */ 
/*     */             
/* 658 */             if (o != null) {
/* 659 */               assert retVal == null;
/* 660 */               retVal = o;
/*     */             } 
/*     */           } 
/* 663 */         } catch (DatabindingException e) {
/*     */           
/* 665 */           throw new WebServiceException(e);
/*     */         } 
/*     */ 
/*     */         
/* 669 */         reader.close();
/* 670 */         XMLStreamReaderFactory.recycle(reader);
/*     */       } else {
/* 672 */         msg.consume();
/*     */       } 
/*     */       
/* 675 */       return retVal;
/*     */     }
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
/*     */       private final ValueSetter setter;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public PartBuilder(PropertyAccessor accessor, ValueSetter setter) {
/* 693 */         this.accessor = accessor;
/* 694 */         this.setter = setter;
/* 695 */         assert accessor != null && setter != null;
/*     */       }
/*     */       
/*     */       final Object readResponse(Object[] args, Object wrapperBean) {
/* 699 */         Object obj = this.accessor.get(wrapperBean);
/* 700 */         return this.setter.put(obj, args);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class RpcLit
/*     */     extends ResponseBuilder
/*     */   {
/*     */     public RpcLit(WrapperParameter wp, ValueSetterFactory setterFactory) {
/* 713 */       assert (wp.getTypeInfo()).type == WrapperComposite.class;
/* 714 */       this.wrapperName = wp.getName();
/* 715 */       this.wrappedParts = new HashMap<QName, ResponseBuilder.WrappedPartBuilder>();
/* 716 */       List<ParameterImpl> children = wp.getWrapperChildren();
/* 717 */       for (ParameterImpl p : children) {
/* 718 */         this.wrappedParts.put(p.getName(), new ResponseBuilder.WrappedPartBuilder(p.getXMLBridge(), setterFactory.get(p)));
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 723 */         assert p.getBinding() == ParameterBinding.BODY;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Object readResponse(Message msg, Object[] args) throws JAXBException, XMLStreamException {
/* 729 */       return readWrappedResponse(msg, args);
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean isXMLMimeType(String mimeType) {
/* 734 */     return (mimeType.equals("text/xml") || mimeType.equals("application/xml"));
/*     */   }
/*     */   
/*     */   public abstract Object readResponse(Message paramMessage, Object[] paramArrayOfObject) throws JAXBException, XMLStreamException;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\sei\ResponseBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */