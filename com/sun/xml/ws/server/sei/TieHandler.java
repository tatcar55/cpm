/*     */ package com.sun.xml.ws.server.sei;
/*     */ 
/*     */ import com.oracle.webservices.api.databinding.JavaCallInfo;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.databinding.EndpointCallBridge;
/*     */ import com.sun.xml.ws.api.databinding.JavaCallInfo;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.MessageContextFactory;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.JavaMethod;
/*     */ import com.sun.xml.ws.api.model.ParameterBinding;
/*     */ import com.sun.xml.ws.fault.SOAPFaultBuilder;
/*     */ import com.sun.xml.ws.model.JavaMethodImpl;
/*     */ import com.sun.xml.ws.model.ParameterImpl;
/*     */ import com.sun.xml.ws.model.WrapperParameter;
/*     */ import com.sun.xml.ws.wsdl.DispatchException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.jws.WebParam;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.stream.XMLStreamException;
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
/*     */ public final class TieHandler
/*     */   implements EndpointCallBridge
/*     */ {
/*     */   private final SOAPVersion soapVersion;
/*     */   private final Method method;
/*     */   private final int noOfArgs;
/*     */   private final JavaMethodImpl javaMethodModel;
/*     */   private final Boolean isOneWay;
/*     */   private final EndpointArgumentsBuilder argumentsBuilder;
/*     */   private final EndpointResponseMessageBuilder bodyBuilder;
/*     */   private final MessageFiller[] outFillers;
/*     */   protected MessageContextFactory packetFactory;
/*     */   
/*     */   public TieHandler(JavaMethodImpl method, WSBinding binding, MessageContextFactory mcf) {
/* 112 */     this.soapVersion = binding.getSOAPVersion();
/* 113 */     this.method = method.getMethod();
/* 114 */     this.javaMethodModel = method;
/* 115 */     this.argumentsBuilder = createArgumentsBuilder();
/* 116 */     List<MessageFiller> fillers = new ArrayList<MessageFiller>();
/* 117 */     this.bodyBuilder = createResponseMessageBuilder(fillers);
/* 118 */     this.outFillers = fillers.<MessageFiller>toArray(new MessageFiller[fillers.size()]);
/* 119 */     this.isOneWay = Boolean.valueOf(method.getMEP().isOneWay());
/* 120 */     this.noOfArgs = (this.method.getParameterTypes()).length;
/* 121 */     this.packetFactory = mcf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EndpointArgumentsBuilder createArgumentsBuilder() {
/* 132 */     List<ParameterImpl> rp = this.javaMethodModel.getRequestParameters();
/* 133 */     List<EndpointArgumentsBuilder> builders = new ArrayList<EndpointArgumentsBuilder>();
/*     */     
/* 135 */     for (ParameterImpl param : rp) {
/* 136 */       EndpointValueSetter setter = EndpointValueSetter.get(param);
/* 137 */       switch ((param.getInBinding()).kind) {
/*     */         case SOAP_11:
/* 139 */           if (param.isWrapperStyle()) {
/* 140 */             if (param.getParent().getBinding().isRpcLit()) {
/* 141 */               builders.add(new EndpointArgumentsBuilder.RpcLit((WrapperParameter)param)); continue;
/*     */             } 
/* 143 */             builders.add(new EndpointArgumentsBuilder.DocLit((WrapperParameter)param, WebParam.Mode.OUT)); continue;
/*     */           } 
/* 145 */           builders.add(new EndpointArgumentsBuilder.Body(param.getXMLBridge(), setter));
/*     */           continue;
/*     */         
/*     */         case SOAP_12:
/* 149 */           builders.add(new EndpointArgumentsBuilder.Header(this.soapVersion, param, setter));
/*     */           continue;
/*     */         case null:
/* 152 */           builders.add(EndpointArgumentsBuilder.AttachmentBuilder.createAttachmentBuilder(param, setter));
/*     */           continue;
/*     */         case null:
/* 155 */           builders.add(new EndpointArgumentsBuilder.NullSetter(setter, EndpointArgumentsBuilder.getVMUninitializedValue((param.getTypeInfo()).type)));
/*     */           continue;
/*     */       } 
/*     */       
/* 159 */       throw new AssertionError();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 164 */     List<ParameterImpl> resp = this.javaMethodModel.getResponseParameters();
/* 165 */     for (ParameterImpl param : resp) {
/* 166 */       if (param.isWrapperStyle()) {
/* 167 */         WrapperParameter wp = (WrapperParameter)param;
/* 168 */         List<ParameterImpl> children = wp.getWrapperChildren();
/* 169 */         for (ParameterImpl p : children) {
/* 170 */           if (p.isOUT() && p.getIndex() != -1) {
/* 171 */             EndpointValueSetter setter = EndpointValueSetter.get(p);
/* 172 */             builders.add(new EndpointArgumentsBuilder.NullSetter(setter, null));
/*     */           } 
/*     */         }  continue;
/* 175 */       }  if (param.isOUT() && param.getIndex() != -1) {
/* 176 */         EndpointValueSetter setter = EndpointValueSetter.get(param);
/* 177 */         builders.add(new EndpointArgumentsBuilder.NullSetter(setter, null));
/*     */       } 
/*     */     } 
/*     */     
/* 181 */     switch (builders.size())
/*     */     { case 0:
/* 183 */         argsBuilder = EndpointArgumentsBuilder.NONE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 191 */         return argsBuilder;case 1: argsBuilder = builders.get(0); return argsBuilder; }  EndpointArgumentsBuilder argsBuilder = new EndpointArgumentsBuilder.Composite(builders); return argsBuilder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EndpointResponseMessageBuilder createResponseMessageBuilder(List<MessageFiller> fillers) {
/* 199 */     EndpointResponseMessageBuilder tmpBodyBuilder = null;
/* 200 */     List<ParameterImpl> rp = this.javaMethodModel.getResponseParameters();
/*     */     
/* 202 */     for (ParameterImpl param : rp) {
/* 203 */       ValueGetter getter = ValueGetter.get(param);
/*     */       
/* 205 */       switch ((param.getOutBinding()).kind) {
/*     */         case SOAP_11:
/* 207 */           if (param.isWrapperStyle()) {
/* 208 */             if (param.getParent().getBinding().isRpcLit()) {
/* 209 */               tmpBodyBuilder = new EndpointResponseMessageBuilder.RpcLit((WrapperParameter)param, this.soapVersion);
/*     */               continue;
/*     */             } 
/* 212 */             tmpBodyBuilder = new EndpointResponseMessageBuilder.DocLit((WrapperParameter)param, this.soapVersion);
/*     */             
/*     */             continue;
/*     */           } 
/* 216 */           tmpBodyBuilder = new EndpointResponseMessageBuilder.Bare(param, this.soapVersion);
/*     */           continue;
/*     */         
/*     */         case SOAP_12:
/* 220 */           fillers.add(new MessageFiller.Header(param.getIndex(), param.getXMLBridge(), getter));
/*     */           continue;
/*     */         case null:
/* 223 */           fillers.add(MessageFiller.AttachmentFiller.createAttachmentFiller(param, getter));
/*     */           continue;
/*     */         case null:
/*     */           continue;
/*     */       } 
/* 228 */       throw new AssertionError();
/*     */     } 
/*     */ 
/*     */     
/* 232 */     if (tmpBodyBuilder == null)
/*     */     
/* 234 */     { switch (this.soapVersion)
/*     */       { case SOAP_11:
/* 236 */           tmpBodyBuilder = EndpointResponseMessageBuilder.EMPTY_SOAP11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 245 */           return tmpBodyBuilder;case SOAP_12: tmpBodyBuilder = EndpointResponseMessageBuilder.EMPTY_SOAP12; return tmpBodyBuilder; }  throw new AssertionError(); }  return tmpBodyBuilder;
/*     */   }
/*     */   
/*     */   public Object[] readRequest(Message reqMsg) {
/* 249 */     Object[] args = new Object[this.noOfArgs];
/*     */     try {
/* 251 */       this.argumentsBuilder.readRequest(reqMsg, args);
/* 252 */     } catch (JAXBException e) {
/* 253 */       throw new WebServiceException(e);
/* 254 */     } catch (XMLStreamException e) {
/* 255 */       throw new WebServiceException(e);
/*     */     } 
/* 257 */     return args;
/*     */   }
/*     */   
/*     */   public Message createResponse(JavaCallInfo call) {
/*     */     Message responseMessage;
/* 262 */     if (call.getException() == null) {
/* 263 */       responseMessage = this.isOneWay.booleanValue() ? null : createResponseMessage(call.getParameters(), call.getReturnValue());
/*     */     } else {
/* 265 */       Throwable e = call.getException();
/* 266 */       Throwable serviceException = getServiceException(e);
/* 267 */       if (e instanceof java.lang.reflect.InvocationTargetException || serviceException != null) {
/*     */ 
/*     */         
/* 270 */         if (serviceException != null) {
/*     */           
/* 272 */           LOGGER.log(Level.FINE, serviceException.getMessage(), serviceException);
/* 273 */           responseMessage = SOAPFaultBuilder.createSOAPFaultMessage(this.soapVersion, this.javaMethodModel.getCheckedException(serviceException.getClass()), serviceException);
/*     */         } else {
/*     */           
/* 276 */           Throwable cause = e.getCause();
/* 277 */           if (cause instanceof javax.xml.ws.ProtocolException) {
/*     */             
/* 279 */             LOGGER.log(Level.FINE, cause.getMessage(), cause);
/*     */           } else {
/*     */             
/* 282 */             LOGGER.log(Level.SEVERE, cause.getMessage(), cause);
/*     */           } 
/* 284 */           responseMessage = SOAPFaultBuilder.createSOAPFaultMessage(this.soapVersion, null, cause);
/*     */         } 
/* 286 */       } else if (e instanceof DispatchException) {
/* 287 */         responseMessage = ((DispatchException)e).fault;
/*     */       } else {
/* 289 */         LOGGER.log(Level.SEVERE, e.getMessage(), e);
/* 290 */         responseMessage = SOAPFaultBuilder.createSOAPFaultMessage(this.soapVersion, null, e);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 295 */     return responseMessage;
/*     */   }
/*     */   
/*     */   Throwable getServiceException(Throwable throwable) {
/* 299 */     if (this.javaMethodModel.getCheckedException(throwable.getClass()) != null) return throwable; 
/* 300 */     if (throwable.getCause() != null) {
/* 301 */       Throwable cause = throwable.getCause();
/*     */       
/* 303 */       if (this.javaMethodModel.getCheckedException(cause.getClass()) != null) return cause;
/*     */     
/*     */     } 
/*     */     
/* 307 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Message createResponseMessage(Object[] args, Object returnValue) {
/* 316 */     Message msg = this.bodyBuilder.createMessage(args, returnValue);
/*     */     
/* 318 */     for (MessageFiller filler : this.outFillers) {
/* 319 */       filler.fillIn(args, returnValue, msg);
/*     */     }
/* 321 */     return msg;
/*     */   }
/*     */   
/*     */   public Method getMethod() {
/* 325 */     return this.method;
/*     */   }
/*     */   
/* 328 */   private static final Logger LOGGER = Logger.getLogger(TieHandler.class.getName());
/*     */ 
/*     */   
/*     */   public JavaCallInfo deserializeRequest(Packet req) {
/* 332 */     JavaCallInfo call = new JavaCallInfo();
/* 333 */     call.setMethod(getMethod());
/* 334 */     Object[] args = readRequest(req.getMessage());
/* 335 */     call.setParameters(args);
/* 336 */     return (JavaCallInfo)call;
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet serializeResponse(JavaCallInfo call) {
/* 341 */     Message msg = createResponse(call);
/* 342 */     Packet p = (msg == null) ? (Packet)this.packetFactory.createContext() : (Packet)this.packetFactory.createContext(msg);
/* 343 */     p.setState(Packet.State.ServerResponse);
/* 344 */     return p;
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaMethod getOperationModel() {
/* 349 */     return (JavaMethod)this.javaMethodModel;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\sei\TieHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */