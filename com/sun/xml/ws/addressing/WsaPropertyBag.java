/*     */ package com.sun.xml.ws.addressing;
/*     */ 
/*     */ import com.oracle.webservices.api.message.BasePropertySet;
/*     */ import com.oracle.webservices.api.message.PropertySet.Property;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.api.message.AddressingUtils;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WsaPropertyBag
/*     */   extends BasePropertySet
/*     */ {
/*     */   public static final String WSA_REPLYTO_FROM_REQUEST = "com.sun.xml.ws.addressing.WsaPropertyBag.ReplyToFromRequest";
/*     */   public static final String WSA_FAULTTO_FROM_REQUEST = "com.sun.xml.ws.addressing.WsaPropertyBag.FaultToFromRequest";
/*     */   public static final String WSA_MSGID_FROM_REQUEST = "com.sun.xml.ws.addressing.WsaPropertyBag.MessageIdFromRequest";
/*     */   public static final String WSA_TO = "com.sun.xml.ws.addressing.WsaPropertyBag.To";
/*     */   @NotNull
/*     */   private final AddressingVersion addressingVersion;
/*     */   @NotNull
/*     */   private final SOAPVersion soapVersion;
/*     */   @NotNull
/*     */   private final Packet packet;
/*     */   
/*     */   public WsaPropertyBag(AddressingVersion addressingVersion, SOAPVersion soapVersion, Packet packet) {
/* 178 */     this._replyToFromRequest = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 189 */     this._faultToFromRequest = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 200 */     this._msgIdFromRequest = null; this.addressingVersion = addressingVersion; this.soapVersion = soapVersion; this.packet = packet;
/*     */   }
/*     */   @Property({"com.sun.xml.ws.api.addressing.to"}) public String getTo() throws XMLStreamException { if (this.packet.getMessage() == null) return null;  Header h = this.packet.getMessage().getMessageHeaders().get(this.addressingVersion.toTag, false); if (h == null) return null;  return h.getStringContent(); }
/*     */   @Property({"com.sun.xml.ws.addressing.WsaPropertyBag.To"}) public WSEndpointReference getToAsReference() throws XMLStreamException { if (this.packet.getMessage() == null) return null;  Header h = this.packet.getMessage().getMessageHeaders().get(this.addressingVersion.toTag, false); if (h == null) return null;  return new WSEndpointReference(h.getStringContent(), this.addressingVersion); }
/* 204 */   @Property({"com.sun.xml.ws.api.addressing.from"}) public WSEndpointReference getFrom() throws XMLStreamException { return getEPR(this.addressingVersion.fromTag); } @Property({"com.sun.xml.ws.api.addressing.action"}) public String getAction() { if (this.packet.getMessage() == null) return null;  Header h = this.packet.getMessage().getMessageHeaders().get(this.addressingVersion.actionTag, false); if (h == null) return null;  return h.getStringContent(); } @Property({"com.sun.xml.ws.api.addressing.messageId", "com.sun.xml.ws.addressing.request.messageID"}) public String getMessageID() { if (this.packet.getMessage() == null) return null;  return AddressingUtils.getMessageID(this.packet.getMessage().getMessageHeaders(), this.addressingVersion, this.soapVersion); } private WSEndpointReference getEPR(QName tag) throws XMLStreamException { if (this.packet.getMessage() == null) return null;  Header h = this.packet.getMessage().getMessageHeaders().get(tag, false); if (h == null) return null;  return h.readAsEPR(this.addressingVersion); } protected BasePropertySet.PropertyMap getPropertyMap() { return model; } @Property({"com.sun.xml.ws.addressing.WsaPropertyBag.MessageIdFromRequest"}) public String getMessageIdFromRequest() { return this._msgIdFromRequest; }
/*     */   private static final BasePropertySet.PropertyMap model = parse(WsaPropertyBag.class);
/*     */   private WSEndpointReference _replyToFromRequest;
/*     */   private WSEndpointReference _faultToFromRequest;
/* 208 */   private String _msgIdFromRequest; @Property({"com.sun.xml.ws.addressing.WsaPropertyBag.ReplyToFromRequest"}) public WSEndpointReference getReplyToFromRequest() { return this._replyToFromRequest; } public void setReplyToFromRequest(WSEndpointReference ref) { this._replyToFromRequest = ref; } @Property({"com.sun.xml.ws.addressing.WsaPropertyBag.FaultToFromRequest"}) public WSEndpointReference getFaultToFromRequest() { return this._faultToFromRequest; } public void setFaultToFromRequest(WSEndpointReference ref) { this._faultToFromRequest = ref; } public void setMessageIdFromRequest(String id) { this._msgIdFromRequest = id; }
/*     */ 
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\addressing\WsaPropertyBag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */