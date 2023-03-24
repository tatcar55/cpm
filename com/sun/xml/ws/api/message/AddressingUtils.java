/*     */ package com.sun.xml.ws.api.message;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.addressing.WsaTubeHelper;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.addressing.AddressingPropertySet;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.addressing.OneWayFeature;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.message.RelatesToHeader;
/*     */ import com.sun.xml.ws.message.StringHeader;
/*     */ import com.sun.xml.ws.resources.AddressingMessages;
/*     */ import com.sun.xml.ws.resources.ClientMessages;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.QName;
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
/*     */ public class AddressingUtils
/*     */ {
/*     */   public static void fillRequestAddressingHeaders(MessageHeaders headers, Packet packet, AddressingVersion av, SOAPVersion sv, boolean oneway, String action) {
/*  68 */     fillRequestAddressingHeaders(headers, packet, av, sv, oneway, action, false);
/*     */   }
/*     */   public static void fillRequestAddressingHeaders(MessageHeaders headers, Packet packet, AddressingVersion av, SOAPVersion sv, boolean oneway, String action, boolean mustUnderstand) {
/*  71 */     fillCommonAddressingHeaders(headers, packet, av, sv, action, mustUnderstand);
/*     */ 
/*     */ 
/*     */     
/*  75 */     if (!oneway) {
/*  76 */       WSEndpointReference epr = av.anonymousEpr;
/*  77 */       if (headers.get(av.replyToTag, false) == null) {
/*  78 */         headers.add(epr.createHeader(av.replyToTag));
/*     */       }
/*     */ 
/*     */       
/*  82 */       if (headers.get(av.faultToTag, false) == null) {
/*  83 */         headers.add(epr.createHeader(av.faultToTag));
/*     */       }
/*     */ 
/*     */       
/*  87 */       if (packet.getMessage().getMessageHeaders().get(av.messageIDTag, false) == null && 
/*  88 */         headers.get(av.messageIDTag, false) == null) {
/*  89 */         StringHeader stringHeader = new StringHeader(av.messageIDTag, Message.generateMessageID());
/*  90 */         headers.add((Header)stringHeader);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void fillRequestAddressingHeaders(MessageHeaders headers, WSDLPort wsdlPort, WSBinding binding, Packet packet) {
/*  97 */     if (binding == null) {
/*  98 */       throw new IllegalArgumentException(AddressingMessages.NULL_BINDING());
/*     */     }
/*     */     
/* 101 */     if (binding.isFeatureEnabled(SuppressAutomaticWSARequestHeadersFeature.class)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 106 */     MessageHeaders hl = packet.getMessage().getMessageHeaders();
/* 107 */     String action = getAction(hl, binding.getAddressingVersion(), binding.getSOAPVersion());
/* 108 */     if (action != null) {
/*     */       return;
/*     */     }
/*     */     
/* 112 */     AddressingVersion addressingVersion = binding.getAddressingVersion();
/*     */     
/* 114 */     WsaTubeHelper wsaHelper = addressingVersion.getWsaHelper(wsdlPort, null, binding);
/*     */ 
/*     */     
/* 117 */     String effectiveInputAction = wsaHelper.getEffectiveInputAction(packet);
/* 118 */     if (effectiveInputAction == null || (effectiveInputAction.equals("") && binding.getSOAPVersion() == SOAPVersion.SOAP_11)) {
/* 119 */       throw new WebServiceException(ClientMessages.INVALID_SOAP_ACTION());
/*     */     }
/* 121 */     boolean oneway = !packet.expectReply.booleanValue();
/* 122 */     if (wsdlPort != null)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 127 */       if (!oneway && packet.getMessage() != null && packet.getWSDLOperation() != null) {
/* 128 */         WSDLBoundOperation wbo = wsdlPort.getBinding().get(packet.getWSDLOperation());
/* 129 */         if (wbo != null && wbo.getAnonymous() == WSDLBoundOperation.ANONYMOUS.prohibited) {
/* 130 */           throw new WebServiceException(AddressingMessages.WSAW_ANONYMOUS_PROHIBITED());
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 135 */     OneWayFeature oneWayFeature = (OneWayFeature)binding.getFeature(OneWayFeature.class);
/* 136 */     AddressingPropertySet addressingPropertySet = (AddressingPropertySet)packet.getSatellite(AddressingPropertySet.class);
/* 137 */     oneWayFeature = (addressingPropertySet == null) ? oneWayFeature : new OneWayFeature(addressingPropertySet, addressingVersion);
/*     */     
/* 139 */     if (oneWayFeature == null || !oneWayFeature.isEnabled()) {
/*     */       
/* 141 */       fillRequestAddressingHeaders(headers, packet, addressingVersion, binding.getSOAPVersion(), oneway, effectiveInputAction, AddressingVersion.isRequired(binding));
/*     */     } else {
/*     */       
/* 144 */       fillRequestAddressingHeaders(headers, packet, addressingVersion, binding.getSOAPVersion(), oneWayFeature, oneway, effectiveInputAction);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String getAction(@NotNull MessageHeaders headers, @NotNull AddressingVersion av, @NotNull SOAPVersion sv) {
/* 149 */     if (av == null) {
/* 150 */       throw new IllegalArgumentException(AddressingMessages.NULL_ADDRESSING_VERSION());
/*     */     }
/*     */     
/* 153 */     String action = null;
/* 154 */     Header h = getFirstHeader(headers, av.actionTag, true, sv);
/* 155 */     if (h != null) {
/* 156 */       action = h.getStringContent();
/*     */     }
/*     */     
/* 159 */     return action;
/*     */   }
/*     */   
/*     */   public static WSEndpointReference getFaultTo(@NotNull MessageHeaders headers, @NotNull AddressingVersion av, @NotNull SOAPVersion sv) {
/* 163 */     if (av == null) {
/* 164 */       throw new IllegalArgumentException(AddressingMessages.NULL_ADDRESSING_VERSION());
/*     */     }
/*     */     
/* 167 */     Header h = getFirstHeader(headers, av.faultToTag, true, sv);
/* 168 */     WSEndpointReference faultTo = null;
/* 169 */     if (h != null) {
/*     */       try {
/* 171 */         faultTo = h.readAsEPR(av);
/* 172 */       } catch (XMLStreamException e) {
/* 173 */         throw new WebServiceException(AddressingMessages.FAULT_TO_CANNOT_PARSE(), e);
/*     */       } 
/*     */     }
/*     */     
/* 177 */     return faultTo;
/*     */   }
/*     */   
/*     */   public static String getMessageID(@NotNull MessageHeaders headers, @NotNull AddressingVersion av, @NotNull SOAPVersion sv) {
/* 181 */     if (av == null) {
/* 182 */       throw new IllegalArgumentException(AddressingMessages.NULL_ADDRESSING_VERSION());
/*     */     }
/*     */     
/* 185 */     Header h = getFirstHeader(headers, av.messageIDTag, true, sv);
/* 186 */     String messageId = null;
/* 187 */     if (h != null) {
/* 188 */       messageId = h.getStringContent();
/*     */     }
/*     */     
/* 191 */     return messageId;
/*     */   }
/*     */   public static String getRelatesTo(@NotNull MessageHeaders headers, @NotNull AddressingVersion av, @NotNull SOAPVersion sv) {
/* 194 */     if (av == null) {
/* 195 */       throw new IllegalArgumentException(AddressingMessages.NULL_ADDRESSING_VERSION());
/*     */     }
/*     */     
/* 198 */     Header h = getFirstHeader(headers, av.relatesToTag, true, sv);
/* 199 */     String relatesTo = null;
/* 200 */     if (h != null) {
/* 201 */       relatesTo = h.getStringContent();
/*     */     }
/*     */     
/* 204 */     return relatesTo;
/*     */   } public static WSEndpointReference getReplyTo(@NotNull MessageHeaders headers, @NotNull AddressingVersion av, @NotNull SOAPVersion sv) {
/*     */     WSEndpointReference wSEndpointReference;
/* 207 */     if (av == null) {
/* 208 */       throw new IllegalArgumentException(AddressingMessages.NULL_ADDRESSING_VERSION());
/*     */     }
/*     */     
/* 211 */     Header h = getFirstHeader(headers, av.replyToTag, true, sv);
/*     */     
/* 213 */     if (h != null) {
/*     */       try {
/* 215 */         wSEndpointReference = h.readAsEPR(av);
/* 216 */       } catch (XMLStreamException e) {
/* 217 */         throw new WebServiceException(AddressingMessages.REPLY_TO_CANNOT_PARSE(), e);
/*     */       } 
/*     */     } else {
/* 220 */       wSEndpointReference = av.anonymousEpr;
/*     */     } 
/*     */     
/* 223 */     return wSEndpointReference;
/*     */   } public static String getTo(MessageHeaders headers, AddressingVersion av, SOAPVersion sv) {
/*     */     String to;
/* 226 */     if (av == null) {
/* 227 */       throw new IllegalArgumentException(AddressingMessages.NULL_ADDRESSING_VERSION());
/*     */     }
/*     */     
/* 230 */     Header h = getFirstHeader(headers, av.toTag, true, sv);
/*     */     
/* 232 */     if (h != null) {
/* 233 */       to = h.getStringContent();
/*     */     } else {
/* 235 */       to = av.anonymousUri;
/*     */     } 
/*     */     
/* 238 */     return to;
/*     */   }
/*     */   
/*     */   public static Header getFirstHeader(MessageHeaders headers, QName name, boolean markUnderstood, SOAPVersion sv) {
/* 242 */     if (sv == null) {
/* 243 */       throw new IllegalArgumentException(AddressingMessages.NULL_SOAP_VERSION());
/*     */     }
/*     */     
/* 246 */     Iterator<Header> iter = headers.getHeaders(name.getNamespaceURI(), name.getLocalPart(), markUnderstood);
/* 247 */     while (iter.hasNext()) {
/* 248 */       Header h = iter.next();
/* 249 */       if (h.getRole(sv).equals(sv.implicitRole)) {
/* 250 */         return h;
/*     */       }
/*     */     } 
/*     */     
/* 254 */     return null;
/*     */   }
/*     */   
/*     */   private static void fillRequestAddressingHeaders(@NotNull MessageHeaders headers, @NotNull Packet packet, @NotNull AddressingVersion av, @NotNull SOAPVersion sv, @NotNull OneWayFeature oneWayFeature, boolean oneway, @NotNull String action) {
/* 258 */     if (!oneway && !oneWayFeature.isUseAsyncWithSyncInvoke() && Boolean.TRUE.equals(packet.isSynchronousMEP)) {
/* 259 */       fillRequestAddressingHeaders(headers, packet, av, sv, oneway, action);
/*     */     } else {
/* 261 */       fillCommonAddressingHeaders(headers, packet, av, sv, action, false);
/*     */       
/* 263 */       boolean isMessageIdAdded = false;
/*     */ 
/*     */ 
/*     */       
/* 267 */       if (headers.get(av.replyToTag, false) == null) {
/* 268 */         WSEndpointReference replyToEpr = oneWayFeature.getReplyTo();
/* 269 */         if (replyToEpr != null) {
/* 270 */           headers.add(replyToEpr.createHeader(av.replyToTag));
/*     */           
/* 272 */           if (packet.getMessage().getMessageHeaders().get(av.messageIDTag, false) == null) {
/*     */             
/* 274 */             String newID = (oneWayFeature.getMessageId() == null) ? Message.generateMessageID() : oneWayFeature.getMessageId();
/* 275 */             headers.add((Header)new StringHeader(av.messageIDTag, newID));
/* 276 */             isMessageIdAdded = true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 282 */       String messageId = oneWayFeature.getMessageId();
/* 283 */       if (!isMessageIdAdded && messageId != null) {
/* 284 */         headers.add((Header)new StringHeader(av.messageIDTag, messageId));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 289 */       if (headers.get(av.faultToTag, false) == null) {
/* 290 */         WSEndpointReference faultToEpr = oneWayFeature.getFaultTo();
/* 291 */         if (faultToEpr != null) {
/* 292 */           headers.add(faultToEpr.createHeader(av.faultToTag));
/*     */           
/* 294 */           if (headers.get(av.messageIDTag, false) == null) {
/* 295 */             headers.add((Header)new StringHeader(av.messageIDTag, Message.generateMessageID()));
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 301 */       if (oneWayFeature.getFrom() != null) {
/* 302 */         headers.addOrReplace(oneWayFeature.getFrom().createHeader(av.fromTag));
/*     */       }
/*     */ 
/*     */       
/* 306 */       if (oneWayFeature.getRelatesToID() != null) {
/* 307 */         headers.addOrReplace((Header)new RelatesToHeader(av.relatesToTag, oneWayFeature.getRelatesToID()));
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
/*     */   private static void fillCommonAddressingHeaders(MessageHeaders headers, Packet packet, @NotNull AddressingVersion av, @NotNull SOAPVersion sv, @NotNull String action, boolean mustUnderstand) {
/* 322 */     if (packet == null) {
/* 323 */       throw new IllegalArgumentException(AddressingMessages.NULL_PACKET());
/*     */     }
/*     */     
/* 326 */     if (av == null) {
/* 327 */       throw new IllegalArgumentException(AddressingMessages.NULL_ADDRESSING_VERSION());
/*     */     }
/*     */     
/* 330 */     if (sv == null) {
/* 331 */       throw new IllegalArgumentException(AddressingMessages.NULL_SOAP_VERSION());
/*     */     }
/*     */     
/* 334 */     if (action == null && !sv.httpBindingId.equals("http://www.w3.org/2003/05/soap/bindings/HTTP/")) {
/* 335 */       throw new IllegalArgumentException(AddressingMessages.NULL_ACTION());
/*     */     }
/*     */ 
/*     */     
/* 339 */     if (headers.get(av.toTag, false) == null) {
/* 340 */       StringHeader h = new StringHeader(av.toTag, packet.endpointAddress.toString());
/* 341 */       headers.add((Header)h);
/*     */     } 
/*     */ 
/*     */     
/* 345 */     if (action != null) {
/* 346 */       packet.soapAction = action;
/* 347 */       if (headers.get(av.actionTag, false) == null) {
/*     */ 
/*     */         
/* 350 */         StringHeader h = new StringHeader(av.actionTag, action, sv, mustUnderstand);
/* 351 */         headers.add((Header)h);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\message\AddressingUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */