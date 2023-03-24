/*     */ package com.sun.xml.ws.client.sei;
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
/*     */ abstract class BodyBuilder
/*     */ {
/*  69 */   static final BodyBuilder EMPTY_SOAP11 = new Empty(SOAPVersion.SOAP_11);
/*  70 */   static final BodyBuilder EMPTY_SOAP12 = new Empty(SOAPVersion.SOAP_12);
/*     */   
/*     */   abstract Message createMessage(Object[] paramArrayOfObject);
/*     */   
/*     */   private static final class Empty extends BodyBuilder {
/*     */     public Empty(SOAPVersion soapVersion) {
/*  76 */       this.soapVersion = soapVersion;
/*     */     }
/*     */     private final SOAPVersion soapVersion;
/*     */     Message createMessage(Object[] methodArgs) {
/*  80 */       return Messages.createEmpty(this.soapVersion);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static abstract class JAXB
/*     */     extends BodyBuilder
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
/*     */     final Message createMessage(Object[] methodArgs) {
/* 103 */       return JAXBMessage.create(this.bridge, build(methodArgs), this.soapVersion);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     abstract Object build(Object[] param1ArrayOfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class Bare
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
/*     */     Bare(ParameterImpl p, SOAPVersion soapVersion, ValueGetter getter) {
/* 128 */       super(p.getXMLBridge(), soapVersion);
/* 129 */       this.methodPos = p.getIndex();
/* 130 */       this.getter = getter;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Object build(Object[] methodArgs) {
/* 137 */       return this.getter.get(methodArgs[this.methodPos]);
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
/*     */     
/*     */     protected Wrapped(WrapperParameter wp, SOAPVersion soapVersion, ValueGetterFactory getter) {
/* 170 */       super(wp.getXMLBridge(), soapVersion);
/* 171 */       this.children = wp.getWrapperChildren();
/* 172 */       this.indices = new int[this.children.size()];
/* 173 */       this.getters = new ValueGetter[this.children.size()];
/* 174 */       for (int i = 0; i < this.indices.length; i++) {
/* 175 */         ParameterImpl p = this.children.get(i);
/* 176 */         this.indices[i] = p.getIndex();
/* 177 */         this.getters[i] = getter.get(p);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected WrapperComposite buildWrapperComposite(Object[] methodArgs) {
/* 185 */       WrapperComposite cs = new WrapperComposite();
/* 186 */       cs.bridges = this.parameterBridges;
/* 187 */       cs.values = new Object[this.parameterBridges.length];
/*     */ 
/*     */       
/* 190 */       for (int i = this.indices.length - 1; i >= 0; i--) {
/* 191 */         Object arg = this.getters[i].get(methodArgs[this.indices[i]]);
/* 192 */         if (arg == null) {
/* 193 */           throw new WebServiceException("Method Parameter: " + ((ParameterImpl)this.children.get(i)).getName() + " cannot be null. This is BP 1.1 R2211 violation.");
/*     */         }
/*     */         
/* 196 */         cs.values[i] = arg;
/*     */       } 
/*     */       
/* 199 */       return cs;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class DocLit
/*     */     extends Wrapped
/*     */   {
/*     */     private final PropertyAccessor[] accessors;
/*     */ 
/*     */ 
/*     */     
/*     */     private final Class wrapper;
/*     */ 
/*     */ 
/*     */     
/*     */     private BindingContext bindingContext;
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean dynamicWrapper;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     DocLit(WrapperParameter wp, SOAPVersion soapVersion, ValueGetterFactory getter) {
/* 228 */       super(wp, soapVersion, getter);
/* 229 */       this.bindingContext = wp.getOwner().getBindingContext();
/* 230 */       this.wrapper = (Class)(wp.getXMLBridge().getTypeInfo()).type;
/* 231 */       this.dynamicWrapper = WrapperComposite.class.equals(this.wrapper);
/* 232 */       this.parameterBridges = new XMLBridge[this.children.size()];
/* 233 */       this.accessors = new PropertyAccessor[this.children.size()];
/* 234 */       for (int i = 0; i < this.accessors.length; i++) {
/* 235 */         ParameterImpl p = this.children.get(i);
/* 236 */         QName name = p.getName();
/* 237 */         if (this.dynamicWrapper) {
/* 238 */           this.parameterBridges[i] = ((ParameterImpl)this.children.get(i)).getInlinedRepeatedElementBridge();
/* 239 */           if (this.parameterBridges[i] == null) this.parameterBridges[i] = ((ParameterImpl)this.children.get(i)).getXMLBridge(); 
/*     */         } else {
/*     */           try {
/* 242 */             this.accessors[i] = p.getOwner().getBindingContext().getElementPropertyAccessor(this.wrapper, name.getNamespaceURI(), name.getLocalPart());
/*     */           }
/* 244 */           catch (JAXBException e) {
/* 245 */             throw new WebServiceException(this.wrapper + " do not have a property of the name " + name, e);
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
/*     */     Object build(Object[] methodArgs) {
/* 257 */       if (this.dynamicWrapper) return buildWrapperComposite(methodArgs);
/*     */       
/*     */       try {
/* 260 */         Object bean = this.bindingContext.newWrapperInstace(this.wrapper);
/*     */ 
/*     */         
/* 263 */         for (int i = this.indices.length - 1; i >= 0; i--) {
/* 264 */           this.accessors[i].set(bean, this.getters[i].get(methodArgs[this.indices[i]]));
/*     */         }
/*     */         
/* 267 */         return bean;
/* 268 */       } catch (InstantiationException e) {
/*     */         
/* 270 */         Error x = new InstantiationError(e.getMessage());
/* 271 */         x.initCause(e);
/* 272 */         throw x;
/* 273 */       } catch (IllegalAccessException e) {
/*     */         
/* 275 */         Error x = new IllegalAccessError(e.getMessage());
/* 276 */         x.initCause(e);
/* 277 */         throw x;
/* 278 */       } catch (DatabindingException e) {
/*     */         
/* 280 */         throw new WebServiceException(e);
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
/*     */   static final class RpcLit
/*     */     extends Wrapped
/*     */   {
/*     */     RpcLit(WrapperParameter wp, SOAPVersion soapVersion, ValueGetterFactory getter) {
/* 300 */       super(wp, soapVersion, getter);
/*     */       
/* 302 */       assert (wp.getTypeInfo()).type == WrapperComposite.class;
/*     */       
/* 304 */       this.parameterBridges = new XMLBridge[this.children.size()];
/* 305 */       for (int i = 0; i < this.parameterBridges.length; i++)
/* 306 */         this.parameterBridges[i] = ((ParameterImpl)this.children.get(i)).getXMLBridge(); 
/*     */     }
/*     */     
/*     */     Object build(Object[] methodArgs) {
/* 310 */       return buildWrapperComposite(methodArgs);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\sei\BodyBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */