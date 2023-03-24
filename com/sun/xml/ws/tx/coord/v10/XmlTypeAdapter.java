/*     */ package com.sun.xml.ws.tx.coord.v10;
/*     */ 
/*     */ import com.sun.xml.bind.api.JAXBRIContext;
/*     */ import com.sun.xml.ws.developer.MemberSubmissionEndpointReference;
/*     */ import com.sun.xml.ws.tx.coord.common.types.BaseExpires;
/*     */ import com.sun.xml.ws.tx.coord.common.types.BaseIdentifier;
/*     */ import com.sun.xml.ws.tx.coord.common.types.BaseRegisterResponseType;
/*     */ import com.sun.xml.ws.tx.coord.common.types.BaseRegisterType;
/*     */ import com.sun.xml.ws.tx.coord.common.types.CoordinationContextIF;
/*     */ import com.sun.xml.ws.tx.coord.common.types.CoordinationContextTypeIF;
/*     */ import com.sun.xml.ws.tx.coord.v10.types.CoordinationContext;
/*     */ import com.sun.xml.ws.tx.coord.v10.types.CoordinationContextType;
/*     */ import com.sun.xml.ws.tx.coord.v10.types.Expires;
/*     */ import com.sun.xml.ws.tx.coord.v10.types.RegisterResponseType;
/*     */ import com.sun.xml.ws.tx.coord.v10.types.RegisterType;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.EndpointReference;
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
/*     */   public static CoordinationContextIF<MemberSubmissionEndpointReference, Expires, CoordinationContextType.Identifier, CoordinationContextType> adapt(CoordinationContext delegate) {
/*  72 */     if (delegate == null) return null; 
/*  73 */     return new CoordinationContextImpl(delegate);
/*     */   }
/*     */ 
/*     */   
/*     */   public static BaseRegisterType<MemberSubmissionEndpointReference, RegisterType> adapt(RegisterType delegate) {
/*  78 */     if (delegate == null) return null; 
/*  79 */     return new RegisterTypeImpl(delegate);
/*     */   }
/*     */   
/*     */   public static BaseRegisterType<MemberSubmissionEndpointReference, RegisterType> newRegisterType() {
/*  83 */     return new RegisterTypeImpl(new RegisterType());
/*     */   }
/*     */   
/*     */   public static BaseRegisterResponseType<MemberSubmissionEndpointReference, RegisterResponseType> adapt(RegisterResponseType delegate) {
/*  87 */     if (delegate == null) return null; 
/*  88 */     return new RegisterResponseTypeImpl(delegate);
/*     */   }
/*     */   
/*     */   public static BaseRegisterResponseType newRegisterResponseType() {
/*  92 */     return new RegisterResponseTypeImpl(new RegisterResponseType());
/*     */   }
/*     */   
/*     */   static class ExpiresImpl
/*     */     extends BaseExpires<Expires> {
/*     */     protected ExpiresImpl(Expires delegate) {
/*  98 */       super(delegate);
/*     */     }
/*     */     
/*     */     public long getValue() {
/* 102 */       return ((Expires)this.delegate).getValue();
/*     */     }
/*     */     
/*     */     public void setValue(long value) {
/* 106 */       ((Expires)this.delegate).setValue(value);
/*     */     }
/*     */     
/*     */     public Map getOtherAttributes() {
/* 110 */       return ((Expires)this.delegate).getOtherAttributes();
/*     */     }
/*     */   }
/*     */   
/*     */   static class IdentifierImpl
/*     */     extends BaseIdentifier<CoordinationContextType.Identifier> {
/*     */     protected IdentifierImpl(CoordinationContextType.Identifier delegate) {
/* 117 */       super(delegate);
/*     */     }
/*     */     
/*     */     public String getValue() {
/* 121 */       return ((CoordinationContextType.Identifier)this.delegate).getValue();
/*     */     }
/*     */     
/*     */     public void setValue(String value) {
/* 125 */       ((CoordinationContextType.Identifier)this.delegate).setValue(value);
/*     */     }
/*     */     
/*     */     public Map<QName, String> getOtherAttributes() {
/* 129 */       return ((CoordinationContextType.Identifier)this.delegate).getOtherAttributes();
/*     */     }
/*     */     
/*     */     public QName getQName() {
/* 133 */       return new QName("http://schemas.xmlsoap.org/ws/2004/10/wscoor", "Identifier");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class CoordinationContextTypeImpl
/*     */     implements CoordinationContextTypeIF<MemberSubmissionEndpointReference, Expires, CoordinationContextType.Identifier, CoordinationContextType>
/*     */   {
/*     */     private CoordinationContextType delegate;
/*     */     
/*     */     public CoordinationContextTypeImpl(CoordinationContextType delegate) {
/* 144 */       this.delegate = delegate;
/*     */     }
/*     */     
/*     */     public BaseIdentifier<CoordinationContextType.Identifier> getIdentifier() {
/* 148 */       return XmlTypeAdapter.adapt(this.delegate.getIdentifier());
/*     */     }
/*     */     
/*     */     public void setIdentifier(BaseIdentifier<CoordinationContextType.Identifier> value) {
/* 152 */       this.delegate.setIdentifier((CoordinationContextType.Identifier)value.getDelegate());
/*     */     }
/*     */     
/*     */     public BaseExpires<Expires> getExpires() {
/* 156 */       return XmlTypeAdapter.adapt(this.delegate.getExpires());
/*     */     }
/*     */     
/*     */     public void setExpires(BaseExpires<Expires> value) {
/* 160 */       this.delegate.setExpires((Expires)value.getDelegate());
/*     */     }
/*     */ 
/*     */     
/*     */     public String getCoordinationType() {
/* 165 */       return this.delegate.getCoordinationType();
/*     */     }
/*     */     
/*     */     public void setCoordinationType(String value) {
/* 169 */       this.delegate.setCoordinationType(value);
/*     */     }
/*     */     
/*     */     public MemberSubmissionEndpointReference getRegistrationService() {
/* 173 */       return this.delegate.getRegistrationService();
/*     */     }
/*     */     
/*     */     public void setRegistrationService(MemberSubmissionEndpointReference value) {
/* 177 */       this.delegate.setRegistrationService(value);
/*     */     }
/*     */     
/*     */     public Map<QName, String> getOtherAttributes() {
/* 181 */       return this.delegate.getOtherAttributes();
/*     */     }
/*     */     
/*     */     public CoordinationContextType getDelegate() {
/* 185 */       return this.delegate;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class CoordinationContextImpl extends CoordinationContextTypeImpl implements CoordinationContextIF<MemberSubmissionEndpointReference, Expires, CoordinationContextType.Identifier, CoordinationContextType> {
/* 190 */     static final JAXBRIContext jaxbContext = getCoordinationContextJaxbContext();
/*     */     private static JAXBRIContext getCoordinationContextJaxbContext() {
/*     */       try {
/* 193 */         return (JAXBRIContext)JAXBRIContext.newInstance(new Class[] { CoordinationContext.class });
/* 194 */       } catch (JAXBException e) {
/* 195 */         throw new WebServiceException("Error creating JAXBContext for CoordinationContext. ", e);
/*     */       } 
/*     */     }
/*     */     public CoordinationContextImpl(CoordinationContext delegate) {
/* 199 */       super((CoordinationContextType)delegate);
/*     */     }
/*     */     
/*     */     public List<Object> getAny() {
/* 203 */       return getDelegate().getAny();
/*     */     }
/*     */     
/*     */     public JAXBRIContext getJAXBRIContext() {
/* 207 */       return jaxbContext;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public CoordinationContext getDelegate() {
/* 213 */       return (CoordinationContext)super.getDelegate();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class RegisterTypeImpl
/*     */     extends BaseRegisterType<MemberSubmissionEndpointReference, RegisterType>
/*     */   {
/*     */     RegisterTypeImpl(RegisterType delegate) {
/* 221 */       super(delegate);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getProtocolIdentifier() {
/* 226 */       return ((RegisterType)this.delegate).getProtocolIdentifier();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setProtocolIdentifier(String value) {
/* 231 */       ((RegisterType)this.delegate).setProtocolIdentifier(value);
/*     */     }
/*     */ 
/*     */     
/*     */     public MemberSubmissionEndpointReference getParticipantProtocolService() {
/* 236 */       return ((RegisterType)this.delegate).getParticipantProtocolService();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setParticipantProtocolService(MemberSubmissionEndpointReference value) {
/* 241 */       ((RegisterType)this.delegate).setParticipantProtocolService(value);
/*     */     }
/*     */ 
/*     */     
/*     */     public List<Object> getAny() {
/* 246 */       return ((RegisterType)this.delegate).getAny();
/*     */     }
/*     */ 
/*     */     
/*     */     public Map<QName, String> getOtherAttributes() {
/* 251 */       return ((RegisterType)this.delegate).getOtherAttributes();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isDurable() {
/* 256 */       return "http://schemas.xmlsoap.org/ws/2004/10/wsat/Durable2PC".equals(((RegisterType)this.delegate).getProtocolIdentifier());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isVolatile() {
/* 261 */       return "http://schemas.xmlsoap.org/ws/2004/10/wsat/Volatile2PC".equals(((RegisterType)this.delegate).getProtocolIdentifier());
/*     */     }
/*     */   }
/*     */   
/*     */   static class RegisterResponseTypeImpl
/*     */     extends BaseRegisterResponseType<MemberSubmissionEndpointReference, RegisterResponseType> {
/*     */     RegisterResponseTypeImpl(RegisterResponseType delegate) {
/* 268 */       super(delegate);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public MemberSubmissionEndpointReference getCoordinatorProtocolService() {
/* 274 */       return ((RegisterResponseType)this.delegate).getCoordinatorProtocolService();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setCoordinatorProtocolService(MemberSubmissionEndpointReference value) {
/* 279 */       ((RegisterResponseType)this.delegate).setCoordinatorProtocolService(value);
/*     */     }
/*     */ 
/*     */     
/*     */     public List<Object> getAny() {
/* 284 */       return ((RegisterResponseType)this.delegate).getAny();
/*     */     }
/*     */ 
/*     */     
/*     */     public Map<QName, String> getOtherAttributes() {
/* 289 */       return ((RegisterResponseType)this.delegate).getOtherAttributes();
/*     */     }
/*     */ 
/*     */     
/*     */     public RegisterResponseType getDelegate() {
/* 294 */       return (RegisterResponseType)this.delegate;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\v10\XmlTypeAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */