/*     */ package com.sun.xml.ws.rx.mc.runtime;
/*     */ 
/*     */ import com.sun.xml.bind.api.JAXBRIContext;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.rx.RxRuntimeException;
/*     */ import com.sun.xml.ws.rx.mc.api.McProtocolVersion;
/*     */ import com.sun.xml.ws.rx.mc.protocol.wsmc200702.MakeConnectionElement;
/*     */ import com.sun.xml.ws.rx.mc.protocol.wsmc200702.MessagePendingElement;
/*     */ import com.sun.xml.ws.rx.mc.protocol.wsmc200702.UnsupportedSelectionType;
/*     */ import com.sun.xml.ws.rx.util.JaxbContextRepository;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum McRuntimeVersion
/*     */ {
/*  56 */   WSMC200702(McProtocolVersion.WSMC200702, new Class[] { MakeConnectionElement.class, MessagePendingElement.class, UnsupportedSelectionType.class });
/*     */   
/*     */   private final JaxbContextRepository jaxbContextRepository;
/*     */   
/*     */   public final McProtocolVersion protocolVersion;
/*     */ 
/*     */   
/*     */   public static McRuntimeVersion forProtocolVersion(McProtocolVersion protocolVersion) {
/*  64 */     for (McRuntimeVersion version : values()) {
/*  65 */       if (version.protocolVersion == protocolVersion) {
/*  66 */         return version;
/*     */       }
/*     */     } 
/*     */     
/*  70 */     assert false : "Unsupported WS-MakeConnection protocol version definition detected";
/*     */     
/*  72 */     return null;
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
/*     */   McRuntimeVersion(McProtocolVersion protocolVersion, Class<?>... protocolClasses) {
/*  85 */     this.protocolVersion = protocolVersion;
/*  86 */     this.jaxbContextRepository = new JaxbContextRepository(protocolClasses);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getClientId(String eprAddress) {
/*  96 */     String mcAnnonymousAddressPrefix = this.protocolVersion.protocolNamespaceUri + "/anonymous?id=";
/*  97 */     if (eprAddress.startsWith(mcAnnonymousAddressPrefix)) {
/*  98 */       return eprAddress.substring(mcAnnonymousAddressPrefix.length());
/*     */     }
/* 100 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAnonymousAddress(String uuid) {
/* 109 */     return this.protocolVersion.protocolNamespaceUri + "/anonymous?id=" + uuid;
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
/*     */   public Unmarshaller getUnmarshaller(AddressingVersion av) throws RxRuntimeException {
/* 127 */     return this.jaxbContextRepository.getUnmarshaller(av);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JAXBRIContext getJaxbContext(AddressingVersion av) {
/* 138 */     return this.jaxbContextRepository.getJaxbContext(av);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\mc\runtime\McRuntimeVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */