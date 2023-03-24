/*     */ package com.sun.xml.ws.tx.coord.v11;
/*     */ 
/*     */ import com.sun.xml.bind.api.JAXBRIContext;
/*     */ import com.sun.xml.ws.tx.coord.common.types.BaseExpires;
/*     */ import com.sun.xml.ws.tx.coord.common.types.BaseIdentifier;
/*     */ import com.sun.xml.ws.tx.coord.common.types.BaseRegisterResponseType;
/*     */ import com.sun.xml.ws.tx.coord.common.types.BaseRegisterType;
/*     */ import com.sun.xml.ws.tx.coord.common.types.CoordinationContextIF;
/*     */ import com.sun.xml.ws.tx.coord.common.types.CoordinationContextTypeIF;
/*     */ import com.sun.xml.ws.tx.coord.v11.types.CoordinationContext;
/*     */ import com.sun.xml.ws.tx.coord.v11.types.CoordinationContextType;
/*     */ import com.sun.xml.ws.tx.coord.v11.types.Expires;
/*     */ import com.sun.xml.ws.tx.coord.v11.types.RegisterResponseType;
/*     */ import com.sun.xml.ws.tx.coord.v11.types.RegisterType;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.wsaddressing.W3CEndpointReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XmlTypeAdapter
/*     */ {
/*     */   public static BaseExpires<Expires> adapt(Expires delegate) {
/*  62 */     if (delegate == null) return null; 
/*  63 */     return new ExpiresImpl(delegate);
/*     */   }
/*     */   
/*     */   public static BaseIdentifier<CoordinationContextType.Identifier> adapt(CoordinationContextType.Identifier delegate) {
/*  67 */     if (delegate == null) return null; 
/*  68 */     return new IdentifierImpl(delegate);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CoordinationContextIF<W3CEndpointReference, Expires, CoordinationContextType.Identifier, CoordinationContextType> adapt(CoordinationContext delegate) {
/*  79 */     if (delegate == null) return null; 
/*  80 */     return new CoordinationContextImpl(delegate);
/*     */   }
/*     */ 
/*     */   
/*     */   public static BaseRegisterType<W3CEndpointReference, RegisterType> adapt(RegisterType delegate) {
/*  85 */     if (delegate == null) return null; 
/*  86 */     return new RegisterTypeImpl(delegate);
/*     */   }
/*     */   
/*     */   public static BaseRegisterType<W3CEndpointReference, RegisterType> newRegisterType() {
/*  90 */     return new RegisterTypeImpl(new RegisterType());
/*     */   }
/*     */   
/*     */   public static BaseRegisterResponseType<W3CEndpointReference, RegisterResponseType> adapt(RegisterResponseType delegate) {
/*  94 */     if (delegate == null) return null; 
/*  95 */     return new RegisterResponseTypeImpl(delegate);
/*     */   }
/*     */   
/*     */   public static BaseRegisterResponseType newRegisterResponseType() {
/*  99 */     return new RegisterResponseTypeImpl(new RegisterResponseType());
/*     */   }
/*     */   
/*     */   static class ExpiresImpl
/*     */     extends BaseExpires<Expires> {
/*     */     protected ExpiresImpl(Expires delegate) {
/* 105 */       super(delegate);
/*     */     }
/*     */     
/*     */     public long getValue() {
/* 109 */       return ((Expires)this.delegate).getValue();
/*     */     }
/*     */     
/*     */     public void setValue(long value) {
/* 113 */       ((Expires)this.delegate).setValue(value);
/*     */     }
/*     */     
/*     */     public Map getOtherAttributes() {
/* 117 */       return ((Expires)this.delegate).getOtherAttributes();
/*     */     }
/*     */   }
/*     */   
/*     */   static class IdentifierImpl
/*     */     extends BaseIdentifier<CoordinationContextType.Identifier> {
/*     */     protected IdentifierImpl(CoordinationContextType.Identifier delegate) {
/* 124 */       super(delegate);
/*     */     }
/*     */     
/*     */     public String getValue() {
/* 128 */       return ((CoordinationContextType.Identifier)this.delegate).getValue();
/*     */     }
/*     */     
/*     */     public void setValue(String value) {
/* 132 */       ((CoordinationContextType.Identifier)this.delegate).setValue(value);
/*     */     }
/*     */     
/*     */     public Map<QName, String> getOtherAttributes() {
/* 136 */       return ((CoordinationContextType.Identifier)this.delegate).getOtherAttributes();
/*     */     }
/*     */     
/*     */     public QName getQName() {
/* 140 */       return new QName("http://docs.oasis-open.org/ws-tx/wscoor/2006/06", "Identifier");
/*     */     }
/*     */   }
/*     */   
/*     */   public static class CoordinationContextTypeImpl
/*     */     implements CoordinationContextTypeIF<W3CEndpointReference, Expires, CoordinationContextType.Identifier, CoordinationContextType>
/*     */   {
/*     */     private CoordinationContextType delegate;
/*     */     
/*     */     public CoordinationContextTypeImpl(CoordinationContextType delegate) {
/* 150 */       this.delegate = delegate;
/*     */     }
/*     */     
/*     */     public BaseIdentifier<CoordinationContextType.Identifier> getIdentifier() {
/* 154 */       return XmlTypeAdapter.adapt(this.delegate.getIdentifier());
/*     */     }
/*     */     
/*     */     public void setIdentifier(BaseIdentifier<CoordinationContextType.Identifier> value) {
/* 158 */       this.delegate.setIdentifier((CoordinationContextType.Identifier)value.getDelegate());
/*     */     }
/*     */     
/*     */     public BaseExpires<Expires> getExpires() {
/* 162 */       return XmlTypeAdapter.adapt(this.delegate.getExpires());
/*     */     }
/*     */     
/*     */     public void setExpires(BaseExpires<Expires> value) {
/* 166 */       this.delegate.setExpires((Expires)value.getDelegate());
/*     */     }
/*     */ 
/*     */     
/*     */     public String getCoordinationType() {
/* 171 */       return this.delegate.getCoordinationType();
/*     */     }
/*     */     
/*     */     public void setCoordinationType(String value) {
/* 175 */       this.delegate.setCoordinationType(value);
/*     */     }
/*     */     
/*     */     public W3CEndpointReference getRegistrationService() {
/* 179 */       return this.delegate.getRegistrationService();
/*     */     }
/*     */     
/*     */     public void setRegistrationService(W3CEndpointReference value) {
/* 183 */       this.delegate.setRegistrationService(value);
/*     */     }
/*     */     
/*     */     public Map<QName, String> getOtherAttributes() {
/* 187 */       return this.delegate.getOtherAttributes();
/*     */     }
/*     */     
/*     */     public CoordinationContextType getDelegate() {
/* 191 */       return this.delegate;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class CoordinationContextImpl extends CoordinationContextTypeImpl implements CoordinationContextIF<W3CEndpointReference, Expires, CoordinationContextType.Identifier, CoordinationContextType> {
/* 196 */     static final JAXBRIContext jaxbContext = getCoordinationContextJaxbContext();
/*     */     private static JAXBRIContext getCoordinationContextJaxbContext() {
/*     */       try {
/* 199 */         return (JAXBRIContext)JAXBRIContext.newInstance(new Class[] { CoordinationContext.class });
/* 200 */       } catch (JAXBException e) {
/* 201 */         throw new WebServiceException("Error creating JAXBContext for CoordinationContext. ", e);
/*     */       } 
/*     */     }
/*     */     public CoordinationContextImpl(CoordinationContext delegate) {
/* 205 */       super((CoordinationContextType)delegate);
/*     */     }
/*     */     
/*     */     public List<Object> getAny() {
/* 209 */       return getDelegate().getAny();
/*     */     }
/*     */     
/*     */     public JAXBRIContext getJAXBRIContext() {
/* 213 */       return jaxbContext;
/*     */     }
/*     */ 
/*     */     
/*     */     public CoordinationContext getDelegate() {
/* 218 */       return (CoordinationContext)super.getDelegate();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class RegisterTypeImpl
/*     */     extends BaseRegisterType<W3CEndpointReference, RegisterType>
/*     */   {
/*     */     RegisterTypeImpl(RegisterType delegate) {
/* 226 */       super(delegate);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getProtocolIdentifier() {
/* 231 */       return ((RegisterType)this.delegate).getProtocolIdentifier();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setProtocolIdentifier(String value) {
/* 236 */       ((RegisterType)this.delegate).setProtocolIdentifier(value);
/*     */     }
/*     */ 
/*     */     
/*     */     public W3CEndpointReference getParticipantProtocolService() {
/* 241 */       return ((RegisterType)this.delegate).getParticipantProtocolService();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setParticipantProtocolService(W3CEndpointReference value) {
/* 246 */       ((RegisterType)this.delegate).setParticipantProtocolService(value);
/*     */     }
/*     */ 
/*     */     
/*     */     public List<Object> getAny() {
/* 251 */       return ((RegisterType)this.delegate).getAny();
/*     */     }
/*     */ 
/*     */     
/*     */     public Map<QName, String> getOtherAttributes() {
/* 256 */       return ((RegisterType)this.delegate).getOtherAttributes();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isDurable() {
/* 261 */       return "http://docs.oasis-open.org/ws-tx/wsat/2006/06/Durable2PC".equals(((RegisterType)this.delegate).getProtocolIdentifier());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isVolatile() {
/* 266 */       return "http://docs.oasis-open.org/ws-tx/wsat/2006/06/Durable2PC".equals(((RegisterType)this.delegate).getProtocolIdentifier());
/*     */     }
/*     */   }
/*     */   
/*     */   static class RegisterResponseTypeImpl
/*     */     extends BaseRegisterResponseType<W3CEndpointReference, RegisterResponseType> {
/*     */     RegisterResponseTypeImpl(RegisterResponseType delegate) {
/* 273 */       super(delegate);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public W3CEndpointReference getCoordinatorProtocolService() {
/* 279 */       return ((RegisterResponseType)this.delegate).getCoordinatorProtocolService();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setCoordinatorProtocolService(W3CEndpointReference value) {
/* 284 */       ((RegisterResponseType)this.delegate).setCoordinatorProtocolService(value);
/*     */     }
/*     */ 
/*     */     
/*     */     public List<Object> getAny() {
/* 289 */       return ((RegisterResponseType)this.delegate).getAny();
/*     */     }
/*     */ 
/*     */     
/*     */     public Map<QName, String> getOtherAttributes() {
/* 294 */       return ((RegisterResponseType)this.delegate).getOtherAttributes();
/*     */     }
/*     */ 
/*     */     
/*     */     public RegisterResponseType getDelegate() {
/* 299 */       return (RegisterResponseType)this.delegate;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\v11\XmlTypeAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */