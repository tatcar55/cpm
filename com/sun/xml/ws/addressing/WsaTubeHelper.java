/*     */ package com.sun.xml.ws.addressing;
/*     */ 
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.addressing.model.InvalidAddressingHeaderException;
/*     */ import com.sun.xml.ws.addressing.model.MissingAddressingHeaderException;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.message.AddressingUtils;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.JavaMethod;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.model.WSDLOperationMapping;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLFault;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLOutput;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.model.CheckedExceptionImpl;
/*     */ import com.sun.xml.ws.model.JavaMethodImpl;
/*     */ import com.sun.xml.ws.model.wsdl.WSDLOperationImpl;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Detail;
/*     */ import javax.xml.soap.SOAPConstants;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFactory;
/*     */ import javax.xml.soap.SOAPFault;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WsaTubeHelper
/*     */ {
/*     */   protected SEIModel seiModel;
/*     */   protected WSDLPort wsdlPort;
/*     */   protected WSBinding binding;
/*     */   protected final SOAPVersion soapVer;
/*     */   protected final AddressingVersion addVer;
/*     */   
/*     */   public WsaTubeHelper(WSBinding binding, SEIModel seiModel, WSDLPort wsdlPort) {
/*  80 */     this.binding = binding;
/*  81 */     this.wsdlPort = wsdlPort;
/*  82 */     this.seiModel = seiModel;
/*  83 */     this.soapVer = binding.getSOAPVersion();
/*  84 */     this.addVer = binding.getAddressingVersion();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFaultAction(Packet requestPacket, Packet responsePacket) {
/*  89 */     String action = null;
/*  90 */     if (this.seiModel != null) {
/*  91 */       action = getFaultActionFromSEIModel(requestPacket, responsePacket);
/*     */     }
/*  93 */     if (action != null) {
/*  94 */       return action;
/*     */     }
/*  96 */     action = this.addVer.getDefaultFaultAction();
/*     */     
/*  98 */     if (this.wsdlPort != null) {
/*  99 */       WSDLOperationMapping wsdlOp = requestPacket.getWSDLOperationMapping();
/* 100 */       if (wsdlOp != null) {
/* 101 */         WSDLBoundOperation wbo = wsdlOp.getWSDLBoundOperation();
/* 102 */         return getFaultAction(wbo, responsePacket);
/*     */       } 
/*     */     } 
/* 105 */     return action;
/*     */   }
/*     */   
/*     */   String getFaultActionFromSEIModel(Packet requestPacket, Packet responsePacket) {
/* 109 */     String action = null;
/* 110 */     if (this.seiModel == null || this.wsdlPort == null) {
/* 111 */       return action;
/*     */     }
/*     */     
/*     */     try {
/* 115 */       SOAPMessage sm = responsePacket.getMessage().copy().readAsSOAPMessage();
/* 116 */       if (sm == null) {
/* 117 */         return action;
/*     */       }
/*     */       
/* 120 */       if (sm.getSOAPBody() == null) {
/* 121 */         return action;
/*     */       }
/*     */       
/* 124 */       if (sm.getSOAPBody().getFault() == null) {
/* 125 */         return action;
/*     */       }
/*     */       
/* 128 */       Detail detail = sm.getSOAPBody().getFault().getDetail();
/* 129 */       if (detail == null) {
/* 130 */         return action;
/*     */       }
/*     */       
/* 133 */       String ns = detail.getFirstChild().getNamespaceURI();
/* 134 */       String name = detail.getFirstChild().getLocalName();
/*     */       
/* 136 */       WSDLOperationMapping wsdlOp = requestPacket.getWSDLOperationMapping();
/* 137 */       JavaMethodImpl jm = (wsdlOp != null) ? (JavaMethodImpl)wsdlOp.getJavaMethod() : null;
/* 138 */       if (jm != null) {
/* 139 */         for (CheckedExceptionImpl ce : jm.getCheckedExceptions()) {
/* 140 */           if ((ce.getDetailType()).tagName.getLocalPart().equals(name) && (ce.getDetailType()).tagName.getNamespaceURI().equals(ns))
/*     */           {
/* 142 */             return ce.getFaultAction();
/*     */           }
/*     */         } 
/*     */       }
/* 146 */       return action;
/* 147 */     } catch (SOAPException e) {
/* 148 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   String getFaultAction(@Nullable WSDLBoundOperation wbo, Packet responsePacket) {
/* 153 */     String action = AddressingUtils.getAction(responsePacket.getMessage().getMessageHeaders(), this.addVer, this.soapVer);
/* 154 */     if (action != null) {
/* 155 */       return action;
/*     */     }
/*     */     
/* 158 */     action = this.addVer.getDefaultFaultAction();
/* 159 */     if (wbo == null) {
/* 160 */       return action;
/*     */     }
/*     */     
/*     */     try {
/* 164 */       SOAPMessage sm = responsePacket.getMessage().copy().readAsSOAPMessage();
/* 165 */       if (sm == null) {
/* 166 */         return action;
/*     */       }
/*     */       
/* 169 */       if (sm.getSOAPBody() == null) {
/* 170 */         return action;
/*     */       }
/*     */       
/* 173 */       if (sm.getSOAPBody().getFault() == null) {
/* 174 */         return action;
/*     */       }
/*     */       
/* 177 */       Detail detail = sm.getSOAPBody().getFault().getDetail();
/* 178 */       if (detail == null) {
/* 179 */         return action;
/*     */       }
/*     */       
/* 182 */       String ns = detail.getFirstChild().getNamespaceURI();
/* 183 */       String name = detail.getFirstChild().getLocalName();
/*     */       
/* 185 */       WSDLOperation o = wbo.getOperation();
/*     */       
/* 187 */       WSDLFault fault = o.getFault(new QName(ns, name));
/* 188 */       if (fault == null) {
/* 189 */         return action;
/*     */       }
/*     */       
/* 192 */       action = fault.getAction();
/*     */       
/* 194 */       return action;
/* 195 */     } catch (SOAPException e) {
/* 196 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getInputAction(Packet packet) {
/* 201 */     String action = null;
/*     */     
/* 203 */     if (this.wsdlPort != null) {
/* 204 */       WSDLOperationMapping wsdlOp = packet.getWSDLOperationMapping();
/* 205 */       if (wsdlOp != null) {
/* 206 */         WSDLBoundOperation wbo = wsdlOp.getWSDLBoundOperation();
/* 207 */         WSDLOperation op = wbo.getOperation();
/* 208 */         action = op.getInput().getAction();
/*     */       } 
/*     */     } 
/*     */     
/* 212 */     return action;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEffectiveInputAction(Packet packet) {
/*     */     String action;
/* 224 */     if (packet.soapAction != null && !packet.soapAction.equals("")) {
/* 225 */       return packet.soapAction;
/*     */     }
/*     */ 
/*     */     
/* 229 */     if (this.wsdlPort != null) {
/* 230 */       WSDLOperationMapping wsdlOp = packet.getWSDLOperationMapping();
/* 231 */       if (wsdlOp != null) {
/* 232 */         WSDLBoundOperation wbo = wsdlOp.getWSDLBoundOperation();
/* 233 */         WSDLOperation op = wbo.getOperation();
/* 234 */         action = op.getInput().getAction();
/*     */       } else {
/* 236 */         action = packet.soapAction;
/*     */       } 
/*     */     } else {
/* 239 */       action = packet.soapAction;
/*     */     } 
/* 241 */     return action;
/*     */   }
/*     */   
/*     */   public boolean isInputActionDefault(Packet packet) {
/* 245 */     if (this.wsdlPort == null) {
/* 246 */       return false;
/*     */     }
/* 248 */     WSDLOperationMapping wsdlOp = packet.getWSDLOperationMapping();
/* 249 */     if (wsdlOp == null) {
/* 250 */       return false;
/*     */     }
/* 252 */     WSDLBoundOperation wbo = wsdlOp.getWSDLBoundOperation();
/* 253 */     WSDLOperation op = wbo.getOperation();
/* 254 */     return ((WSDLOperationImpl)op).getInput().isDefaultAction();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSOAPAction(Packet packet) {
/* 259 */     String action = "";
/*     */     
/* 261 */     if (packet == null || packet.getMessage() == null) {
/* 262 */       return action;
/*     */     }
/*     */     
/* 265 */     if (this.wsdlPort == null) {
/* 266 */       return action;
/*     */     }
/*     */     
/* 269 */     WSDLOperationMapping wsdlOp = packet.getWSDLOperationMapping();
/* 270 */     if (wsdlOp == null) {
/* 271 */       return action;
/*     */     }
/*     */     
/* 274 */     WSDLBoundOperation op = wsdlOp.getWSDLBoundOperation();
/* 275 */     action = op.getSOAPAction();
/* 276 */     return action;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getOutputAction(Packet packet) {
/* 281 */     String action = null;
/* 282 */     WSDLOperationMapping wsdlOp = packet.getWSDLOperationMapping();
/* 283 */     if (wsdlOp != null) {
/* 284 */       JavaMethod javaMethod = wsdlOp.getJavaMethod();
/* 285 */       if (javaMethod != null) {
/* 286 */         JavaMethodImpl jm = (JavaMethodImpl)javaMethod;
/* 287 */         if (jm != null && jm.getOutputAction() != null && !jm.getOutputAction().equals("")) {
/* 288 */           return jm.getOutputAction();
/*     */         }
/*     */       } 
/* 291 */       WSDLBoundOperation wbo = wsdlOp.getWSDLBoundOperation();
/* 292 */       if (wbo != null) return getOutputAction(wbo); 
/*     */     } 
/* 294 */     return action;
/*     */   }
/*     */   
/*     */   String getOutputAction(@Nullable WSDLBoundOperation wbo) {
/* 298 */     String action = "http://jax-ws.dev.java.net/addressing/output-action-not-set";
/* 299 */     if (wbo != null) {
/* 300 */       WSDLOutput op = wbo.getOperation().getOutput();
/* 301 */       if (op != null) {
/* 302 */         action = op.getAction();
/*     */       }
/*     */     } 
/* 305 */     return action;
/*     */   }
/*     */   
/*     */   public SOAPFault createInvalidAddressingHeaderFault(InvalidAddressingHeaderException e, AddressingVersion av) {
/* 309 */     QName name = e.getProblemHeader();
/* 310 */     QName subsubcode = e.getSubsubcode();
/* 311 */     QName subcode = av.invalidMapTag;
/* 312 */     String faultstring = String.format(av.getInvalidMapText(), new Object[] { name, subsubcode });
/*     */     
/*     */     try {
/*     */       SOAPFault fault;
/*     */       
/* 317 */       if (this.soapVer == SOAPVersion.SOAP_12) {
/* 318 */         SOAPFactory factory = SOAPVersion.SOAP_12.getSOAPFactory();
/* 319 */         fault = factory.createFault();
/* 320 */         fault.setFaultCode(SOAPConstants.SOAP_SENDER_FAULT);
/* 321 */         fault.appendFaultSubcode(subcode);
/* 322 */         fault.appendFaultSubcode(subsubcode);
/* 323 */         getInvalidMapDetail(name, fault.addDetail());
/*     */       } else {
/* 325 */         SOAPFactory factory = SOAPVersion.SOAP_11.getSOAPFactory();
/* 326 */         fault = factory.createFault();
/* 327 */         fault.setFaultCode(subsubcode);
/*     */       } 
/*     */       
/* 330 */       fault.setFaultString(faultstring);
/*     */       
/* 332 */       return fault;
/* 333 */     } catch (SOAPException se) {
/* 334 */       throw new WebServiceException(se);
/*     */     } 
/*     */   }
/*     */   
/*     */   public SOAPFault newMapRequiredFault(MissingAddressingHeaderException e) {
/* 339 */     QName subcode = this.addVer.mapRequiredTag;
/* 340 */     QName subsubcode = this.addVer.mapRequiredTag;
/* 341 */     String faultstring = this.addVer.getMapRequiredText();
/*     */     
/*     */     try {
/*     */       SOAPFault fault;
/*     */       
/* 346 */       if (this.soapVer == SOAPVersion.SOAP_12) {
/* 347 */         SOAPFactory factory = SOAPVersion.SOAP_12.getSOAPFactory();
/* 348 */         fault = factory.createFault();
/* 349 */         fault.setFaultCode(SOAPConstants.SOAP_SENDER_FAULT);
/* 350 */         fault.appendFaultSubcode(subcode);
/* 351 */         fault.appendFaultSubcode(subsubcode);
/* 352 */         getMapRequiredDetail(e.getMissingHeaderQName(), fault.addDetail());
/*     */       } else {
/* 354 */         SOAPFactory factory = SOAPVersion.SOAP_11.getSOAPFactory();
/* 355 */         fault = factory.createFault();
/* 356 */         fault.setFaultCode(subsubcode);
/*     */       } 
/*     */       
/* 359 */       fault.setFaultString(faultstring);
/*     */       
/* 361 */       return fault;
/* 362 */     } catch (SOAPException se) {
/* 363 */       throw new WebServiceException(se);
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract void getProblemActionDetail(String paramString, Element paramElement);
/*     */   
/*     */   public abstract void getInvalidMapDetail(QName paramQName, Element paramElement);
/*     */   
/*     */   public abstract void getMapRequiredDetail(QName paramQName, Element paramElement);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\addressing\WsaTubeHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */