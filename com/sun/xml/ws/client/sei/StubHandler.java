/*     */ package com.sun.xml.ws.client.sei;
/*     */ 
/*     */ import com.oracle.webservices.api.databinding.JavaCallInfo;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.databinding.ClientCallBridge;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.MessageContextFactory;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.JavaMethod;
/*     */ import com.sun.xml.ws.api.model.ParameterBinding;
/*     */ import com.sun.xml.ws.fault.SOAPFaultBuilder;
/*     */ import com.sun.xml.ws.model.CheckedExceptionImpl;
/*     */ import com.sun.xml.ws.model.JavaMethodImpl;
/*     */ import com.sun.xml.ws.model.ParameterImpl;
/*     */ import com.sun.xml.ws.model.WrapperParameter;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StubHandler
/*     */   implements ClientCallBridge
/*     */ {
/*     */   private final BodyBuilder bodyBuilder;
/*     */   private final MessageFiller[] inFillers;
/*     */   protected final String soapAction;
/*     */   protected final boolean isOneWay;
/*     */   protected final JavaMethodImpl javaMethod;
/*     */   protected final Map<QName, CheckedExceptionImpl> checkedExceptions;
/*  95 */   protected SOAPVersion soapVersion = SOAPVersion.SOAP_11;
/*     */   
/*     */   protected ResponseBuilder responseBuilder;
/*     */   protected MessageContextFactory packetFactory;
/*     */   
/*     */   public StubHandler(JavaMethodImpl method, MessageContextFactory mcf) {
/* 101 */     this.checkedExceptions = new HashMap<QName, CheckedExceptionImpl>();
/* 102 */     for (CheckedExceptionImpl ce : method.getCheckedExceptions()) {
/* 103 */       this.checkedExceptions.put((ce.getBond().getTypeInfo()).tagName, ce);
/*     */     }
/*     */     
/* 106 */     String soapActionFromBinding = method.getBinding().getSOAPAction();
/* 107 */     if (method.getInputAction() != null && soapActionFromBinding != null && !soapActionFromBinding.equals("")) {
/* 108 */       this.soapAction = method.getInputAction();
/*     */     } else {
/* 110 */       this.soapAction = soapActionFromBinding;
/*     */     } 
/* 112 */     this.javaMethod = method;
/* 113 */     this.packetFactory = mcf;
/*     */     
/* 115 */     this.soapVersion = this.javaMethod.getBinding().getSOAPVersion();
/*     */ 
/*     */     
/* 118 */     List<ParameterImpl> rp = method.getRequestParameters();
/*     */     
/* 120 */     BodyBuilder bodyBuilder = null;
/* 121 */     List<MessageFiller> fillers = new ArrayList<MessageFiller>();
/*     */     
/* 123 */     for (ParameterImpl param : rp) {
/* 124 */       ValueGetter getter = getValueGetterFactory().get(param);
/*     */       
/* 126 */       switch ((param.getInBinding()).kind) {
/*     */         case SOAP_11:
/* 128 */           if (param.isWrapperStyle()) {
/* 129 */             if (param.getParent().getBinding().isRpcLit()) {
/* 130 */               bodyBuilder = new BodyBuilder.RpcLit((WrapperParameter)param, this.soapVersion, getValueGetterFactory()); continue;
/*     */             } 
/* 132 */             bodyBuilder = new BodyBuilder.DocLit((WrapperParameter)param, this.soapVersion, getValueGetterFactory()); continue;
/*     */           } 
/* 134 */           bodyBuilder = new BodyBuilder.Bare(param, this.soapVersion, getter);
/*     */           continue;
/*     */         
/*     */         case SOAP_12:
/* 138 */           fillers.add(new MessageFiller.Header(param.getIndex(), param.getXMLBridge(), getter));
/*     */           continue;
/*     */ 
/*     */ 
/*     */         
/*     */         case null:
/* 144 */           fillers.add(MessageFiller.AttachmentFiller.createAttachmentFiller(param, getter));
/*     */           continue;
/*     */         case null:
/*     */           continue;
/*     */       } 
/* 149 */       throw new AssertionError();
/*     */     } 
/*     */ 
/*     */     
/* 153 */     if (bodyBuilder == null)
/*     */     {
/* 155 */       switch (this.soapVersion) {
/*     */         case SOAP_11:
/* 157 */           bodyBuilder = BodyBuilder.EMPTY_SOAP11;
/*     */           break;
/*     */         case SOAP_12:
/* 160 */           bodyBuilder = BodyBuilder.EMPTY_SOAP12;
/*     */           break;
/*     */         default:
/* 163 */           throw new AssertionError();
/*     */       } 
/*     */     
/*     */     }
/* 167 */     this.bodyBuilder = bodyBuilder;
/* 168 */     this.inFillers = fillers.<MessageFiller>toArray(new MessageFiller[fillers.size()]);
/*     */ 
/*     */     
/* 171 */     this.isOneWay = method.getMEP().isOneWay();
/* 172 */     this.responseBuilder = buildResponseBuilder(method, ValueSetterFactory.SYNC);
/*     */   }
/*     */ 
/*     */   
/*     */   ResponseBuilder buildResponseBuilder(JavaMethodImpl method, ValueSetterFactory setterFactory) {
/* 177 */     List<ParameterImpl> rp = method.getResponseParameters();
/* 178 */     List<ResponseBuilder> builders = new ArrayList<ResponseBuilder>();
/*     */     
/* 180 */     for (ParameterImpl param : rp) {
/*     */       ValueSetter setter;
/* 182 */       switch ((param.getOutBinding()).kind) {
/*     */         case SOAP_11:
/* 184 */           if (param.isWrapperStyle()) {
/* 185 */             if (param.getParent().getBinding().isRpcLit()) {
/* 186 */               builders.add(new ResponseBuilder.RpcLit((WrapperParameter)param, setterFactory)); continue;
/*     */             } 
/* 188 */             builders.add(new ResponseBuilder.DocLit((WrapperParameter)param, setterFactory)); continue;
/*     */           } 
/* 190 */           setter = setterFactory.get(param);
/* 191 */           builders.add(new ResponseBuilder.Body(param.getXMLBridge(), setter));
/*     */           continue;
/*     */         
/*     */         case SOAP_12:
/* 195 */           setter = setterFactory.get(param);
/* 196 */           builders.add(new ResponseBuilder.Header(this.soapVersion, param, setter));
/*     */           continue;
/*     */         case null:
/* 199 */           setter = setterFactory.get(param);
/* 200 */           builders.add(ResponseBuilder.AttachmentBuilder.createAttachmentBuilder(param, setter));
/*     */           continue;
/*     */         case null:
/* 203 */           setter = setterFactory.get(param);
/* 204 */           builders.add(new ResponseBuilder.NullSetter(setter, ResponseBuilder.getVMUninitializedValue((param.getTypeInfo()).type)));
/*     */           continue;
/*     */       } 
/*     */       
/* 208 */       throw new AssertionError();
/*     */     } 
/*     */ 
/*     */     
/* 212 */     switch (builders.size())
/*     */     { case 0:
/* 214 */         rb = ResponseBuilder.NONE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 222 */         return rb;case 1: rb = builders.get(0); return rb; }  ResponseBuilder rb = new ResponseBuilder.Composite(builders); return rb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet createRequestPacket(JavaCallInfo args) {
/* 232 */     Message msg = this.bodyBuilder.createMessage(args.getParameters());
/*     */     
/* 234 */     for (MessageFiller filler : this.inFillers) filler.fillIn(args.getParameters(), msg);
/*     */     
/* 236 */     Packet req = (Packet)this.packetFactory.createContext(msg);
/* 237 */     req.setState(Packet.State.ClientRequest);
/* 238 */     req.soapAction = this.soapAction;
/* 239 */     req.expectReply = Boolean.valueOf(!this.isOneWay);
/* 240 */     req.getMessage().assertOneWay(this.isOneWay);
/* 241 */     req.setWSDLOperation(getOperationName());
/* 242 */     return req;
/*     */   }
/*     */   
/*     */   ValueGetterFactory getValueGetterFactory() {
/* 246 */     return ValueGetterFactory.SYNC;
/*     */   }
/*     */   
/*     */   public JavaCallInfo readResponse(Packet p, JavaCallInfo call) throws Throwable {
/* 250 */     Message msg = p.getMessage();
/* 251 */     if (msg.isFault()) {
/* 252 */       SOAPFaultBuilder faultBuilder = SOAPFaultBuilder.create(msg);
/* 253 */       Throwable t = faultBuilder.createException(this.checkedExceptions);
/* 254 */       call.setException(t);
/* 255 */       throw t;
/*     */     } 
/* 257 */     initArgs(call.getParameters());
/* 258 */     Object ret = this.responseBuilder.readResponse(msg, call.getParameters());
/* 259 */     call.setReturnValue(ret);
/* 260 */     return call;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getOperationName() {
/* 266 */     return this.javaMethod.getOperationQName();
/*     */   }
/*     */   
/*     */   public String getSoapAction() {
/* 270 */     return this.soapAction;
/*     */   }
/*     */   
/*     */   public boolean isOneWay() {
/* 274 */     return this.isOneWay;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initArgs(Object[] args) throws Exception {}
/*     */   
/*     */   public Method getMethod() {
/* 281 */     return this.javaMethod.getMethod();
/*     */   }
/*     */   
/*     */   public JavaMethod getOperationModel() {
/* 285 */     return (JavaMethod)this.javaMethod;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\sei\StubHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */