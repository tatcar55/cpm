/*     */ package com.sun.xml.ws.handler;
/*     */ 
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.message.DOMMessage;
/*     */ import com.sun.xml.ws.message.EmptyMessageImpl;
/*     */ import com.sun.xml.ws.message.jaxb.JAXBMessage;
/*     */ import com.sun.xml.ws.message.source.PayloadSourceMessage;
/*     */ import com.sun.xml.ws.spi.db.BindingContext;
/*     */ import com.sun.xml.ws.spi.db.BindingContextFactory;
/*     */ import com.sun.xml.ws.util.xml.XmlUtil;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.util.JAXBSource;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.ws.LogicalMessage;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class LogicalMessageImpl
/*     */   implements LogicalMessage
/*     */ {
/*     */   private Packet packet;
/*     */   protected BindingContext defaultJaxbContext;
/*  89 */   private ImmutableLM lm = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LogicalMessageImpl(BindingContext defaultJaxbContext, Packet packet) {
/*  95 */     this.packet = packet;
/*  96 */     this.defaultJaxbContext = defaultJaxbContext;
/*     */   }
/*     */   
/*     */   public Source getPayload() {
/* 100 */     if (this.lm == null) {
/* 101 */       Source payload = this.packet.getMessage().copy().readPayloadAsSource();
/* 102 */       if (payload instanceof DOMSource) {
/* 103 */         this.lm = createLogicalMessageImpl(payload);
/*     */       }
/* 105 */       return payload;
/*     */     } 
/* 107 */     return this.lm.getPayload();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPayload(Source payload) {
/* 112 */     this.lm = createLogicalMessageImpl(payload);
/*     */   }
/*     */   
/*     */   private ImmutableLM createLogicalMessageImpl(Source payload) {
/* 116 */     if (payload == null) {
/* 117 */       this.lm = new EmptyLogicalMessageImpl();
/* 118 */     } else if (payload instanceof DOMSource) {
/* 119 */       this.lm = new DOMLogicalMessageImpl((DOMSource)payload);
/*     */     } else {
/* 121 */       this.lm = new SourceLogicalMessageImpl(payload);
/*     */     } 
/* 123 */     return this.lm;
/*     */   }
/*     */   public Object getPayload(BindingContext context) {
/*     */     Object object;
/* 127 */     if (context == null) {
/* 128 */       context = this.defaultJaxbContext;
/*     */     }
/* 130 */     if (context == null) {
/* 131 */       throw new WebServiceException("JAXBContext parameter cannot be null");
/*     */     }
/*     */     
/* 134 */     if (this.lm == null) {
/*     */       try {
/* 136 */         object = this.packet.getMessage().copy().readPayloadAsJAXB(context.createUnmarshaller());
/* 137 */       } catch (JAXBException e) {
/* 138 */         throw new WebServiceException(e);
/*     */       } 
/*     */     } else {
/* 141 */       object = this.lm.getPayload(context);
/* 142 */       this.lm = new JAXBLogicalMessageImpl(context.getJAXBContext(), object);
/*     */     } 
/* 144 */     return object;
/*     */   }
/*     */   public Object getPayload(JAXBContext context) {
/*     */     Object object;
/* 148 */     if (context == null) {
/* 149 */       return getPayload(this.defaultJaxbContext);
/*     */     }
/* 151 */     if (context == null) {
/* 152 */       throw new WebServiceException("JAXBContext parameter cannot be null");
/*     */     }
/*     */     
/* 155 */     if (this.lm == null) {
/*     */       try {
/* 157 */         object = this.packet.getMessage().copy().readPayloadAsJAXB(context.createUnmarshaller());
/* 158 */       } catch (JAXBException e) {
/* 159 */         throw new WebServiceException(e);
/*     */       } 
/*     */     } else {
/* 162 */       object = this.lm.getPayload(context);
/* 163 */       this.lm = new JAXBLogicalMessageImpl(context, object);
/*     */     } 
/* 165 */     return object;
/*     */   }
/*     */   
/*     */   public void setPayload(Object payload, BindingContext context) {
/* 169 */     if (context == null) {
/* 170 */       context = this.defaultJaxbContext;
/*     */     }
/* 172 */     if (payload == null) {
/* 173 */       this.lm = new EmptyLogicalMessageImpl();
/*     */     } else {
/* 175 */       this.lm = new JAXBLogicalMessageImpl(context.getJAXBContext(), payload);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setPayload(Object payload, JAXBContext context) {
/* 180 */     if (context == null) {
/* 181 */       setPayload(payload, this.defaultJaxbContext);
/*     */     }
/* 183 */     if (payload == null) {
/* 184 */       this.lm = new EmptyLogicalMessageImpl();
/*     */     } else {
/* 186 */       this.lm = new JAXBLogicalMessageImpl(context, payload);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isPayloadModifed() {
/* 191 */     return (this.lm != null);
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
/*     */   public Message getMessage(HeaderList headers, AttachmentSet attachments, WSBinding binding) {
/* 203 */     assert isPayloadModifed();
/* 204 */     if (isPayloadModifed()) {
/* 205 */       return this.lm.getMessage(headers, attachments, binding);
/*     */     }
/* 207 */     return this.packet.getMessage();
/*     */   }
/*     */   
/*     */   private abstract class ImmutableLM {
/*     */     private ImmutableLM() {}
/*     */     
/*     */     public abstract Source getPayload();
/*     */     
/*     */     public abstract Object getPayload(BindingContext param1BindingContext);
/*     */     
/*     */     public abstract Object getPayload(JAXBContext param1JAXBContext);
/*     */     
/*     */     public abstract Message getMessage(HeaderList param1HeaderList, AttachmentSet param1AttachmentSet, WSBinding param1WSBinding); }
/*     */   
/*     */   private class DOMLogicalMessageImpl extends SourceLogicalMessageImpl {
/*     */     private DOMSource dom;
/*     */     
/*     */     public DOMLogicalMessageImpl(DOMSource dom) {
/* 225 */       super(dom);
/* 226 */       this.dom = dom;
/*     */     }
/*     */ 
/*     */     
/*     */     public Source getPayload() {
/* 231 */       return this.dom;
/*     */     }
/*     */     
/*     */     public Message getMessage(HeaderList headers, AttachmentSet attachments, WSBinding binding) {
/* 235 */       Node n = this.dom.getNode();
/* 236 */       if (n.getNodeType() == 9) {
/* 237 */         n = ((Document)n).getDocumentElement();
/*     */       }
/* 239 */       return (Message)new DOMMessage(binding.getSOAPVersion(), headers, (Element)n, attachments);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class EmptyLogicalMessageImpl
/*     */     extends ImmutableLM
/*     */   {
/*     */     public Source getPayload() {
/* 250 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object getPayload(JAXBContext context) {
/* 255 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object getPayload(BindingContext context) {
/* 260 */       return null;
/*     */     }
/*     */     
/*     */     public Message getMessage(HeaderList headers, AttachmentSet attachments, WSBinding binding) {
/* 264 */       return (Message)new EmptyMessageImpl(headers, attachments, binding.getSOAPVersion());
/*     */     }
/*     */   }
/*     */   
/*     */   private class JAXBLogicalMessageImpl extends ImmutableLM {
/*     */     private JAXBContext ctxt;
/*     */     private Object o;
/*     */     
/*     */     public JAXBLogicalMessageImpl(JAXBContext ctxt, Object o) {
/* 273 */       this.ctxt = ctxt;
/* 274 */       this.o = o;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Source getPayload() {
/* 280 */       JAXBContext context = this.ctxt;
/* 281 */       if (context == null) {
/* 282 */         context = LogicalMessageImpl.this.defaultJaxbContext.getJAXBContext();
/*     */       }
/*     */       try {
/* 285 */         return new JAXBSource(context, this.o);
/* 286 */       } catch (JAXBException e) {
/* 287 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getPayload(JAXBContext context) {
/*     */       try {
/* 297 */         Source payloadSrc = getPayload();
/* 298 */         if (payloadSrc == null)
/* 299 */           return null; 
/* 300 */         Unmarshaller unmarshaller = context.createUnmarshaller();
/* 301 */         return unmarshaller.unmarshal(payloadSrc);
/* 302 */       } catch (JAXBException e) {
/* 303 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getPayload(BindingContext context) {
/*     */       try {
/* 311 */         Source payloadSrc = getPayload();
/* 312 */         if (payloadSrc == null)
/* 313 */           return null; 
/* 314 */         Unmarshaller unmarshaller = context.createUnmarshaller();
/* 315 */         return unmarshaller.unmarshal(payloadSrc);
/* 316 */       } catch (JAXBException e) {
/* 317 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public Message getMessage(HeaderList headers, AttachmentSet attachments, WSBinding binding) {
/* 322 */       return JAXBMessage.create(BindingContextFactory.create(this.ctxt), this.o, binding.getSOAPVersion(), headers, attachments);
/*     */     }
/*     */   }
/*     */   
/*     */   private class SourceLogicalMessageImpl extends ImmutableLM {
/*     */     private Source payloadSrc;
/*     */     
/*     */     public SourceLogicalMessageImpl(Source source) {
/* 330 */       this.payloadSrc = source;
/*     */     }
/*     */     
/*     */     public Source getPayload() {
/* 334 */       assert !(this.payloadSrc instanceof DOMSource);
/*     */       try {
/* 336 */         Transformer transformer = XmlUtil.newTransformer();
/* 337 */         DOMResult domResult = new DOMResult();
/* 338 */         transformer.transform(this.payloadSrc, domResult);
/* 339 */         DOMSource dom = new DOMSource(domResult.getNode());
/* 340 */         LogicalMessageImpl.this.lm = new LogicalMessageImpl.DOMLogicalMessageImpl(dom);
/* 341 */         this.payloadSrc = null;
/* 342 */         return dom;
/* 343 */       } catch (TransformerException te) {
/* 344 */         throw new WebServiceException(te);
/*     */       } 
/*     */     }
/*     */     
/*     */     public Object getPayload(JAXBContext context) {
/*     */       try {
/* 350 */         Source payloadSrc = getPayload();
/* 351 */         if (payloadSrc == null)
/* 352 */           return null; 
/* 353 */         Unmarshaller unmarshaller = context.createUnmarshaller();
/* 354 */         return unmarshaller.unmarshal(payloadSrc);
/* 355 */       } catch (JAXBException e) {
/* 356 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Object getPayload(BindingContext context) {
/*     */       try {
/* 363 */         Source payloadSrc = getPayload();
/* 364 */         if (payloadSrc == null)
/* 365 */           return null; 
/* 366 */         Unmarshaller unmarshaller = context.createUnmarshaller();
/* 367 */         return unmarshaller.unmarshal(payloadSrc);
/* 368 */       } catch (JAXBException e) {
/* 369 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Message getMessage(HeaderList headers, AttachmentSet attachments, WSBinding binding) {
/* 375 */       assert this.payloadSrc != null;
/* 376 */       return (Message)new PayloadSourceMessage(headers, this.payloadSrc, attachments, binding.getSOAPVersion());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\handler\LogicalMessageImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */