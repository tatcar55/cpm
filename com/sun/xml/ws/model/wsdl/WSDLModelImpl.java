/*     */ package com.sun.xml.ws.model.wsdl;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.model.ParameterBinding;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundPortType;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLExtension;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLMessage;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLModel;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPortType;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLService;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import javax.jws.WebParam;
/*     */ import javax.xml.namespace.QName;
/*     */ import org.xml.sax.Locator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class WSDLModelImpl
/*     */   extends AbstractExtensibleImpl
/*     */   implements WSDLModel
/*     */ {
/*  69 */   private final Map<QName, WSDLMessageImpl> messages = new HashMap<QName, WSDLMessageImpl>();
/*  70 */   private final Map<QName, WSDLPortTypeImpl> portTypes = new HashMap<QName, WSDLPortTypeImpl>();
/*  71 */   private final Map<QName, WSDLBoundPortTypeImpl> bindings = new HashMap<QName, WSDLBoundPortTypeImpl>();
/*  72 */   private final Map<QName, WSDLServiceImpl> services = new LinkedHashMap<QName, WSDLServiceImpl>();
/*     */   
/*     */   private PolicyMap policyMap;
/*  75 */   private final Map<QName, WSDLBoundPortType> unmBindings = Collections.unmodifiableMap((Map)this.bindings);
/*     */ 
/*     */ 
/*     */   
/*     */   public WSDLModelImpl(@NotNull String systemId) {
/*  80 */     super(systemId, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WSDLModelImpl() {
/*  87 */     super(null, -1);
/*     */   }
/*     */   
/*     */   public void addMessage(WSDLMessageImpl msg) {
/*  91 */     this.messages.put(msg.getName(), msg);
/*     */   }
/*     */   
/*     */   public WSDLMessageImpl getMessage(QName name) {
/*  95 */     return this.messages.get(name);
/*     */   }
/*     */   
/*     */   public void addPortType(WSDLPortTypeImpl pt) {
/*  99 */     this.portTypes.put(pt.getName(), pt);
/*     */   }
/*     */   
/*     */   public WSDLPortTypeImpl getPortType(QName name) {
/* 103 */     return this.portTypes.get(name);
/*     */   }
/*     */   
/*     */   public void addBinding(WSDLBoundPortTypeImpl boundPortType) {
/* 107 */     assert !this.bindings.containsValue(boundPortType);
/* 108 */     this.bindings.put(boundPortType.getName(), boundPortType);
/*     */   }
/*     */   
/*     */   public WSDLBoundPortTypeImpl getBinding(QName name) {
/* 112 */     return this.bindings.get(name);
/*     */   }
/*     */   
/*     */   public void addService(WSDLServiceImpl svc) {
/* 116 */     this.services.put(svc.getName(), svc);
/*     */   }
/*     */   
/*     */   public WSDLServiceImpl getService(QName name) {
/* 120 */     return this.services.get(name);
/*     */   }
/*     */   
/*     */   public Map<QName, WSDLMessageImpl> getMessages() {
/* 124 */     return this.messages;
/*     */   }
/*     */   @NotNull
/*     */   public Map<QName, WSDLPortTypeImpl> getPortTypes() {
/* 128 */     return this.portTypes;
/*     */   }
/*     */   @NotNull
/*     */   public Map<QName, WSDLBoundPortType> getBindings() {
/* 132 */     return this.unmBindings;
/*     */   }
/*     */   @NotNull
/*     */   public Map<QName, WSDLServiceImpl> getServices() {
/* 136 */     return this.services;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getFirstServiceName() {
/* 143 */     if (this.services.isEmpty())
/* 144 */       return null; 
/* 145 */     return ((WSDLServiceImpl)this.services.values().iterator().next()).getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getFirstPortName() {
/* 152 */     WSDLPort fp = getFirstPort();
/* 153 */     if (fp == null) {
/* 154 */       return null;
/*     */     }
/* 156 */     return fp.getName();
/*     */   }
/*     */   
/*     */   private WSDLPort getFirstPort() {
/* 160 */     if (this.services.isEmpty())
/* 161 */       return null; 
/* 162 */     WSDLService service = this.services.values().iterator().next();
/* 163 */     Iterator<? extends WSDLPort> iter = service.getPorts().iterator();
/* 164 */     WSDLPort port = iter.hasNext() ? iter.next() : null;
/* 165 */     return port;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WSDLPortImpl getMatchingPort(QName serviceName, QName portType) {
/* 172 */     return getService(serviceName).getMatchingPort(portType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WSDLBoundPortTypeImpl getBinding(QName serviceName, QName portName) {
/* 183 */     WSDLServiceImpl service = this.services.get(serviceName);
/* 184 */     if (service != null) {
/* 185 */       WSDLPortImpl port = service.get(portName);
/* 186 */       if (port != null)
/* 187 */         return port.getBinding(); 
/*     */     } 
/* 189 */     return null;
/*     */   }
/*     */   
/*     */   void finalizeRpcLitBinding(WSDLBoundPortTypeImpl boundPortType) {
/* 193 */     assert boundPortType != null;
/* 194 */     QName portTypeName = boundPortType.getPortTypeName();
/* 195 */     if (portTypeName == null)
/*     */       return; 
/* 197 */     WSDLPortType pt = this.portTypes.get(portTypeName);
/* 198 */     if (pt == null)
/*     */       return; 
/* 200 */     for (WSDLBoundOperationImpl bop : boundPortType.getBindingOperations()) {
/* 201 */       WSDLOperation pto = pt.get(bop.getName().getLocalPart());
/* 202 */       WSDLMessage inMsgName = pto.getInput().getMessage();
/* 203 */       if (inMsgName == null)
/*     */         continue; 
/* 205 */       WSDLMessageImpl inMsg = this.messages.get(inMsgName.getName());
/* 206 */       int bodyindex = 0;
/* 207 */       if (inMsg != null) {
/* 208 */         for (WSDLPartImpl part : inMsg.parts()) {
/* 209 */           String name = part.getName();
/* 210 */           ParameterBinding pb = bop.getInputBinding(name);
/* 211 */           if (pb.isBody()) {
/* 212 */             part.setIndex(bodyindex++);
/* 213 */             part.setBinding(pb);
/* 214 */             bop.addPart(part, WebParam.Mode.IN);
/*     */           } 
/*     */         } 
/*     */       }
/* 218 */       bodyindex = 0;
/* 219 */       if (pto.isOneWay())
/*     */         continue; 
/* 221 */       WSDLMessage outMsgName = pto.getOutput().getMessage();
/* 222 */       if (outMsgName == null)
/*     */         continue; 
/* 224 */       WSDLMessageImpl outMsg = this.messages.get(outMsgName.getName());
/* 225 */       if (outMsg != null) {
/* 226 */         for (WSDLPartImpl part : outMsg.parts()) {
/* 227 */           String name = part.getName();
/* 228 */           ParameterBinding pb = bop.getOutputBinding(name);
/* 229 */           if (pb.isBody()) {
/* 230 */             part.setIndex(bodyindex++);
/* 231 */             part.setBinding(pb);
/* 232 */             bop.addPart(part, WebParam.Mode.OUT);
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolicyMap getPolicyMap() {
/* 245 */     return this.policyMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPolicyMap(PolicyMap policyMap) {
/* 253 */     this.policyMap = policyMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void freeze() {
/* 260 */     for (WSDLServiceImpl service : this.services.values()) {
/* 261 */       service.freeze(this);
/*     */     }
/* 263 */     for (WSDLBoundPortTypeImpl bp : this.bindings.values()) {
/* 264 */       bp.freeze();
/*     */     }
/*     */     
/* 267 */     for (WSDLPortTypeImpl pt : this.portTypes.values())
/* 268 */       pt.freeze(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\wsdl\WSDLModelImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */