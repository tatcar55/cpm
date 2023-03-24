/*     */ package com.sun.xml.ws.api.pipe;
/*     */ 
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.WSService;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.api.client.WSPortInfo;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.binding.BindingImpl;
/*     */ import com.sun.xml.ws.client.WSServiceDelegate;
/*     */ import com.sun.xml.ws.client.dispatch.DataSourceDispatch;
/*     */ import com.sun.xml.ws.client.dispatch.DispatchImpl;
/*     */ import com.sun.xml.ws.client.dispatch.JAXBDispatch;
/*     */ import com.sun.xml.ws.client.dispatch.MessageDispatch;
/*     */ import com.sun.xml.ws.client.dispatch.PacketDispatch;
/*     */ import com.sun.xml.ws.client.dispatch.SOAPMessageDispatch;
/*     */ import com.sun.xml.ws.client.sei.SEIStub;
/*     */ import com.sun.xml.ws.developer.WSBindingProvider;
/*     */ import com.sun.xml.ws.model.SOAPSEIModel;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Proxy;
/*     */ import javax.activation.DataSource;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.Dispatch;
/*     */ import javax.xml.ws.Service;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Stubs
/*     */ {
/*     */   @Deprecated
/*     */   public static Dispatch<SOAPMessage> createSAAJDispatch(QName portName, WSService owner, WSBinding binding, Service.Mode mode, Tube next, @Nullable WSEndpointReference epr) {
/* 135 */     DispatchImpl.checkValidSOAPMessageDispatch(binding, mode);
/* 136 */     return (Dispatch<SOAPMessage>)new SOAPMessageDispatch(portName, mode, (WSServiceDelegate)owner, next, (BindingImpl)binding, epr);
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
/*     */   public static Dispatch<SOAPMessage> createSAAJDispatch(WSPortInfo portInfo, WSBinding binding, Service.Mode mode, @Nullable WSEndpointReference epr) {
/* 148 */     DispatchImpl.checkValidSOAPMessageDispatch(binding, mode);
/* 149 */     return (Dispatch<SOAPMessage>)new SOAPMessageDispatch(portInfo, mode, (BindingImpl)binding, epr);
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
/*     */   @Deprecated
/*     */   public static Dispatch<DataSource> createDataSourceDispatch(QName portName, WSService owner, WSBinding binding, Service.Mode mode, Tube next, @Nullable WSEndpointReference epr) {
/* 162 */     DispatchImpl.checkValidDataSourceDispatch(binding, mode);
/* 163 */     return (Dispatch<DataSource>)new DataSourceDispatch(portName, mode, (WSServiceDelegate)owner, next, (BindingImpl)binding, epr);
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
/*     */   public static Dispatch<DataSource> createDataSourceDispatch(WSPortInfo portInfo, WSBinding binding, Service.Mode mode, @Nullable WSEndpointReference epr) {
/* 175 */     DispatchImpl.checkValidDataSourceDispatch(binding, mode);
/* 176 */     return (Dispatch<DataSource>)new DataSourceDispatch(portInfo, mode, (BindingImpl)binding, epr);
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
/*     */   @Deprecated
/*     */   public static Dispatch<Source> createSourceDispatch(QName portName, WSService owner, WSBinding binding, Service.Mode mode, Tube next, @Nullable WSEndpointReference epr) {
/* 189 */     return DispatchImpl.createSourceDispatch(portName, mode, (WSServiceDelegate)owner, next, (BindingImpl)binding, epr);
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
/*     */   public static Dispatch<Source> createSourceDispatch(WSPortInfo portInfo, WSBinding binding, Service.Mode mode, @Nullable WSEndpointReference epr) {
/* 201 */     return DispatchImpl.createSourceDispatch(portInfo, mode, (BindingImpl)binding, epr);
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
/*     */   public static <T> Dispatch<T> createDispatch(QName portName, WSService owner, WSBinding binding, Class<T> clazz, Service.Mode mode, Tube next, @Nullable WSEndpointReference epr) {
/* 231 */     if (clazz == SOAPMessage.class)
/* 232 */       return (Dispatch)createSAAJDispatch(portName, owner, binding, mode, next, epr); 
/* 233 */     if (clazz == Source.class)
/* 234 */       return (Dispatch)createSourceDispatch(portName, owner, binding, mode, next, epr); 
/* 235 */     if (clazz == DataSource.class)
/* 236 */       return (Dispatch)createDataSourceDispatch(portName, owner, binding, mode, next, epr); 
/* 237 */     if (clazz == Message.class) {
/* 238 */       if (mode == Service.Mode.MESSAGE) {
/* 239 */         return (Dispatch)createMessageDispatch(portName, owner, binding, next, epr);
/*     */       }
/* 241 */       throw new WebServiceException(mode + " not supported with Dispatch<Message>");
/* 242 */     }  if (clazz == Packet.class) {
/* 243 */       return (Dispatch)createPacketDispatch(portName, owner, binding, next, epr);
/*     */     }
/* 245 */     throw new WebServiceException("Unknown class type " + clazz.getName());
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
/*     */   public static <T> Dispatch<T> createDispatch(WSPortInfo portInfo, WSService owner, WSBinding binding, Class<T> clazz, Service.Mode mode, @Nullable WSEndpointReference epr) {
/* 272 */     if (clazz == SOAPMessage.class)
/* 273 */       return (Dispatch)createSAAJDispatch(portInfo, binding, mode, epr); 
/* 274 */     if (clazz == Source.class)
/* 275 */       return (Dispatch)createSourceDispatch(portInfo, binding, mode, epr); 
/* 276 */     if (clazz == DataSource.class)
/* 277 */       return (Dispatch)createDataSourceDispatch(portInfo, binding, mode, epr); 
/* 278 */     if (clazz == Message.class) {
/* 279 */       if (mode == Service.Mode.MESSAGE) {
/* 280 */         return (Dispatch)createMessageDispatch(portInfo, binding, epr);
/*     */       }
/* 282 */       throw new WebServiceException(mode + " not supported with Dispatch<Message>");
/* 283 */     }  if (clazz == Packet.class) {
/* 284 */       if (mode == Service.Mode.MESSAGE) {
/* 285 */         return (Dispatch)createPacketDispatch(portInfo, binding, epr);
/*     */       }
/* 287 */       throw new WebServiceException(mode + " not supported with Dispatch<Packet>");
/*     */     } 
/* 289 */     throw new WebServiceException("Unknown class type " + clazz.getName());
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
/*     */   @Deprecated
/*     */   public static Dispatch<Object> createJAXBDispatch(QName portName, WSService owner, WSBinding binding, JAXBContext jaxbContext, Service.Mode mode, Tube next, @Nullable WSEndpointReference epr) {
/* 316 */     return (Dispatch<Object>)new JAXBDispatch(portName, jaxbContext, mode, (WSServiceDelegate)owner, next, (BindingImpl)binding, epr);
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
/*     */   public static Dispatch<Object> createJAXBDispatch(WSPortInfo portInfo, WSBinding binding, JAXBContext jaxbContext, Service.Mode mode, @Nullable WSEndpointReference epr) {
/* 333 */     return (Dispatch<Object>)new JAXBDispatch(portInfo, jaxbContext, mode, (BindingImpl)binding, epr);
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
/*     */   @Deprecated
/*     */   public static Dispatch<Message> createMessageDispatch(QName portName, WSService owner, WSBinding binding, Tube next, @Nullable WSEndpointReference epr) {
/* 356 */     return (Dispatch<Message>)new MessageDispatch(portName, (WSServiceDelegate)owner, next, (BindingImpl)binding, epr);
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
/*     */   public static Dispatch<Message> createMessageDispatch(WSPortInfo portInfo, WSBinding binding, @Nullable WSEndpointReference epr) {
/* 374 */     return (Dispatch<Message>)new MessageDispatch(portInfo, (BindingImpl)binding, epr);
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
/*     */   public static Dispatch<Packet> createPacketDispatch(QName portName, WSService owner, WSBinding binding, Tube next, @Nullable WSEndpointReference epr) {
/* 394 */     return (Dispatch<Packet>)new PacketDispatch(portName, (WSServiceDelegate)owner, next, (BindingImpl)binding, epr);
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
/*     */   public static Dispatch<Packet> createPacketDispatch(WSPortInfo portInfo, WSBinding binding, @Nullable WSEndpointReference epr) {
/* 411 */     return (Dispatch<Packet>)new PacketDispatch(portInfo, (BindingImpl)binding, epr);
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
/*     */   public <T> T createPortProxy(WSService service, WSBinding binding, SEIModel model, Class<T> portInterface, Tube next, @Nullable WSEndpointReference epr) {
/* 435 */     SEIStub ps = new SEIStub((WSServiceDelegate)service, (BindingImpl)binding, (SOAPSEIModel)model, next, epr);
/* 436 */     return portInterface.cast(Proxy.newProxyInstance(portInterface.getClassLoader(), new Class[] { portInterface, WSBindingProvider.class }, (InvocationHandler)ps));
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
/*     */   public <T> T createPortProxy(WSPortInfo portInfo, WSBinding binding, SEIModel model, Class<T> portInterface, @Nullable WSEndpointReference epr) {
/* 460 */     SEIStub ps = new SEIStub(portInfo, (BindingImpl)binding, (SOAPSEIModel)model, epr);
/* 461 */     return portInterface.cast(Proxy.newProxyInstance(portInterface.getClassLoader(), new Class[] { portInterface, WSBindingProvider.class }, (InvocationHandler)ps));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\pipe\Stubs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */