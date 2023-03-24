/*     */ package com.sun.xml.ws.server.sei;
/*     */ 
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Messages;
/*     */ import com.sun.xml.ws.message.jaxb.JAXBMessage;
/*     */ import com.sun.xml.ws.model.ParameterImpl;
/*     */ import com.sun.xml.ws.model.WrapperParameter;
/*     */ import com.sun.xml.ws.spi.db.BindingContext;
/*     */ import com.sun.xml.ws.spi.db.DatabindingException;
/*     */ import com.sun.xml.ws.spi.db.PropertyAccessor;
/*     */ import com.sun.xml.ws.spi.db.WrapperComposite;
/*     */ import com.sun.xml.ws.spi.db.XMLBridge;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EndpointResponseMessageBuilder
/*     */ {
/*  69 */   public static final EndpointResponseMessageBuilder EMPTY_SOAP11 = new Empty(SOAPVersion.SOAP_11);
/*  70 */   public static final EndpointResponseMessageBuilder EMPTY_SOAP12 = new Empty(SOAPVersion.SOAP_12);
/*     */   
/*     */   public abstract Message createMessage(Object[] paramArrayOfObject, Object paramObject);
/*     */   
/*     */   private static final class Empty extends EndpointResponseMessageBuilder {
/*     */     public Empty(SOAPVersion soapVersion) {
/*  76 */       this.soapVersion = soapVersion;
/*     */     }
/*     */     private final SOAPVersion soapVersion;
/*     */     public Message createMessage(Object[] methodArgs, Object returnValue) {
/*  80 */       return Messages.createEmpty(this.soapVersion);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static abstract class JAXB
/*     */     extends EndpointResponseMessageBuilder
/*     */   {
/*     */     private final XMLBridge bridge;
/*     */ 
/*     */     
/*     */     private final SOAPVersion soapVersion;
/*     */ 
/*     */ 
/*     */     
/*     */     protected JAXB(XMLBridge bridge, SOAPVersion soapVersion) {
/*  97 */       assert bridge != null;
/*  98 */       this.bridge = bridge;
/*  99 */       this.soapVersion = soapVersion;
/*     */     }
/*     */     
/*     */     public final Message createMessage(Object[] methodArgs, Object returnValue) {
/* 103 */       return JAXBMessage.create(this.bridge, build(methodArgs, returnValue), this.soapVersion);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     abstract Object build(Object[] param1ArrayOfObject, Object param1Object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Bare
/*     */     extends JAXB
/*     */   {
/*     */     private final int methodPos;
/*     */ 
/*     */ 
/*     */     
/*     */     private final ValueGetter getter;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Bare(ParameterImpl p, SOAPVersion soapVersion) {
/* 128 */       super(p.getXMLBridge(), soapVersion);
/* 129 */       this.methodPos = p.getIndex();
/* 130 */       this.getter = ValueGetter.get(p);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Object build(Object[] methodArgs, Object returnValue) {
/* 137 */       if (this.methodPos == -1) {
/* 138 */         return returnValue;
/*     */       }
/* 140 */       return this.getter.get(methodArgs[this.methodPos]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static abstract class Wrapped
/*     */     extends JAXB
/*     */   {
/*     */     protected final int[] indices;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final ValueGetter[] getters;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected XMLBridge[] parameterBridges;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected List<ParameterImpl> children;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Wrapped(WrapperParameter wp, SOAPVersion soapVersion) {
/* 172 */       super(wp.getXMLBridge(), soapVersion);
/*     */       
/* 174 */       this.children = wp.getWrapperChildren();
/*     */       
/* 176 */       this.indices = new int[this.children.size()];
/* 177 */       this.getters = new ValueGetter[this.children.size()];
/* 178 */       for (int i = 0; i < this.indices.length; i++) {
/* 179 */         ParameterImpl p = this.children.get(i);
/* 180 */         this.indices[i] = p.getIndex();
/* 181 */         this.getters[i] = ValueGetter.get(p);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     WrapperComposite buildWrapperComposite(Object[] methodArgs, Object returnValue) {
/* 189 */       WrapperComposite cs = new WrapperComposite();
/* 190 */       cs.bridges = this.parameterBridges;
/* 191 */       cs.values = new Object[this.parameterBridges.length];
/*     */ 
/*     */       
/* 194 */       for (int i = this.indices.length - 1; i >= 0; i--) {
/*     */         Object v;
/* 196 */         if (this.indices[i] == -1) {
/* 197 */           v = this.getters[i].get(returnValue);
/*     */         } else {
/* 199 */           v = this.getters[i].get(methodArgs[this.indices[i]]);
/*     */         } 
/* 201 */         if (v == null) {
/* 202 */           throw new WebServiceException("Method Parameter: " + ((ParameterImpl)this.children.get(i)).getName() + " cannot be null. This is BP 1.1 R2211 violation.");
/*     */         }
/*     */         
/* 205 */         cs.values[i] = v;
/*     */       } 
/*     */       
/* 208 */       return cs;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class DocLit
/*     */     extends Wrapped
/*     */   {
/*     */     private final PropertyAccessor[] accessors;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Class wrapper;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean dynamicWrapper;
/*     */ 
/*     */ 
/*     */     
/*     */     private BindingContext bindingContext;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DocLit(WrapperParameter wp, SOAPVersion soapVersion) {
/* 239 */       super(wp, soapVersion);
/* 240 */       this.bindingContext = wp.getOwner().getBindingContext();
/* 241 */       this.wrapper = (Class)(wp.getXMLBridge().getTypeInfo()).type;
/* 242 */       this.dynamicWrapper = WrapperComposite.class.equals(this.wrapper);
/* 243 */       this.children = wp.getWrapperChildren();
/* 244 */       this.parameterBridges = new XMLBridge[this.children.size()];
/* 245 */       this.accessors = new PropertyAccessor[this.children.size()];
/* 246 */       for (int i = 0; i < this.accessors.length; i++) {
/* 247 */         ParameterImpl p = this.children.get(i);
/* 248 */         QName name = p.getName();
/* 249 */         if (this.dynamicWrapper) {
/* 250 */           this.parameterBridges[i] = ((ParameterImpl)this.children.get(i)).getInlinedRepeatedElementBridge();
/* 251 */           if (this.parameterBridges[i] == null) this.parameterBridges[i] = ((ParameterImpl)this.children.get(i)).getXMLBridge(); 
/*     */         } else {
/*     */           try {
/* 254 */             this.accessors[i] = this.dynamicWrapper ? null : p.getOwner().getBindingContext().getElementPropertyAccessor(this.wrapper, name.getNamespaceURI(), name.getLocalPart());
/*     */           
/*     */           }
/* 257 */           catch (JAXBException e) {
/* 258 */             throw new WebServiceException(this.wrapper + " do not have a property of the name " + name, e);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Object build(Object[] methodArgs, Object returnValue) {
/* 270 */       if (this.dynamicWrapper) return buildWrapperComposite(methodArgs, returnValue);
/*     */       
/*     */       try {
/* 273 */         Object bean = this.bindingContext.newWrapperInstace(this.wrapper);
/*     */ 
/*     */         
/* 276 */         for (int i = this.indices.length - 1; i >= 0; i--) {
/* 277 */           if (this.indices[i] == -1) {
/* 278 */             this.accessors[i].set(bean, returnValue);
/*     */           } else {
/* 280 */             this.accessors[i].set(bean, this.getters[i].get(methodArgs[this.indices[i]]));
/*     */           } 
/*     */         } 
/*     */         
/* 284 */         return bean;
/* 285 */       } catch (InstantiationException e) {
/*     */         
/* 287 */         Error x = new InstantiationError(e.getMessage());
/* 288 */         x.initCause(e);
/* 289 */         throw x;
/* 290 */       } catch (IllegalAccessException e) {
/*     */         
/* 292 */         Error x = new IllegalAccessError(e.getMessage());
/* 293 */         x.initCause(e);
/* 294 */         throw x;
/* 295 */       } catch (DatabindingException e) {
/*     */         
/* 297 */         throw new WebServiceException(e);
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
/*     */   public static final class RpcLit
/*     */     extends Wrapped
/*     */   {
/*     */     public RpcLit(WrapperParameter wp, SOAPVersion soapVersion) {
/* 317 */       super(wp, soapVersion);
/*     */       
/* 319 */       assert (wp.getTypeInfo()).type == WrapperComposite.class;
/*     */       
/* 321 */       this.parameterBridges = new XMLBridge[this.children.size()];
/* 322 */       for (int i = 0; i < this.parameterBridges.length; i++) {
/* 323 */         this.parameterBridges[i] = ((ParameterImpl)this.children.get(i)).getXMLBridge();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     Object build(Object[] methodArgs, Object returnValue) {
/* 330 */       return buildWrapperComposite(methodArgs, returnValue);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\sei\EndpointResponseMessageBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */