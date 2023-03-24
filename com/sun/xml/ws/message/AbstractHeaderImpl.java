/*     */ package com.sun.xml.ws.message;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.bind.api.Bridge;
/*     */ import com.sun.xml.bind.api.BridgeContext;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.streaming.XMLStreamReaderFactory;
/*     */ import com.sun.xml.ws.spi.db.XMLBridge;
/*     */ import java.util.Set;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import org.xml.sax.helpers.AttributesImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractHeaderImpl
/*     */   implements Header
/*     */ {
/*     */   public final <T> T readAsJAXB(Bridge<T> bridge, BridgeContext context) throws JAXBException {
/*  80 */     return readAsJAXB(bridge);
/*     */   }
/*     */   
/*     */   public <T> T readAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/*     */     try {
/*  85 */       return (T)unmarshaller.unmarshal(readHeader());
/*  86 */     } catch (Exception e) {
/*  87 */       throw new JAXBException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public <T> T readAsJAXB(Bridge<T> bridge) throws JAXBException {
/*     */     try {
/*  93 */       return (T)bridge.unmarshal(readHeader());
/*  94 */     } catch (XMLStreamException e) {
/*  95 */       throw new JAXBException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public <T> T readAsJAXB(XMLBridge<T> bridge) throws JAXBException {
/*     */     try {
/* 101 */       return (T)bridge.unmarshal(readHeader(), null);
/* 102 */     } catch (XMLStreamException e) {
/* 103 */       throw new JAXBException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WSEndpointReference readAsEPR(AddressingVersion expected) throws XMLStreamException {
/* 111 */     XMLStreamReader xsr = readHeader();
/* 112 */     WSEndpointReference epr = new WSEndpointReference(xsr, expected);
/* 113 */     XMLStreamReaderFactory.recycle(xsr);
/* 114 */     return epr;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isIgnorable(@NotNull SOAPVersion soapVersion, @NotNull Set<String> roles) {
/* 119 */     String v = getAttribute(soapVersion.nsUri, "mustUnderstand");
/* 120 */     if (v == null || !parseBool(v)) return true;
/*     */     
/* 122 */     if (roles == null) return true;
/*     */ 
/*     */     
/* 125 */     return !roles.contains(getRole(soapVersion));
/*     */   }
/*     */   @NotNull
/*     */   public String getRole(@NotNull SOAPVersion soapVersion) {
/* 129 */     String v = getAttribute(soapVersion.nsUri, soapVersion.roleAttributeName);
/* 130 */     if (v == null)
/* 131 */       v = soapVersion.implicitRole; 
/* 132 */     return v;
/*     */   }
/*     */   
/*     */   public boolean isRelay() {
/* 136 */     String v = getAttribute(SOAPVersion.SOAP_12.nsUri, "relay");
/* 137 */     if (v == null) return false; 
/* 138 */     return parseBool(v);
/*     */   }
/*     */   
/*     */   public String getAttribute(QName name) {
/* 142 */     return getAttribute(name.getNamespaceURI(), name.getLocalPart());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean parseBool(String value) {
/* 151 */     if (value.length() == 0) {
/* 152 */       return false;
/*     */     }
/* 154 */     char ch = value.charAt(0);
/* 155 */     return (ch == 't' || ch == '1');
/*     */   }
/*     */   
/*     */   public String getStringContent() {
/*     */     try {
/* 160 */       XMLStreamReader xsr = readHeader();
/* 161 */       xsr.nextTag();
/* 162 */       return xsr.getElementText();
/* 163 */     } catch (XMLStreamException e) {
/* 164 */       return null;
/*     */     } 
/*     */   }
/*     */   
/* 168 */   protected static final AttributesImpl EMPTY_ATTS = new AttributesImpl();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\AbstractHeaderImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */