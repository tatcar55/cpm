/*     */ package com.sun.xml.ws.config.metro.parser.jsr109;
/*     */ 
/*     */ import com.sun.xml.bind.Locatable;
/*     */ import com.sun.xml.bind.annotation.XmlLocation;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlID;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlTransient;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
/*     */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "port-componentType", propOrder = {"description", "displayName", "icon", "portComponentName", "wsdlService", "wsdlPort", "enableMtom", "mtomThreshold", "addressing", "respectBinding", "protocolBinding", "serviceEndpointInterface", "serviceImplBean", "handler", "handlerChains"})
/*     */ public class PortComponentType
/*     */   implements Locatable
/*     */ {
/*     */   protected DescriptionType description;
/*     */   @XmlElement(name = "display-name")
/*     */   protected DisplayNameType displayName;
/*     */   protected IconType icon;
/*     */   @XmlElement(name = "port-component-name", required = true)
/*     */   protected String portComponentName;
/*     */   @XmlElement(name = "wsdl-service")
/*     */   protected XsdQNameType wsdlService;
/*     */   @XmlElement(name = "wsdl-port")
/*     */   protected XsdQNameType wsdlPort;
/*     */   @XmlElement(name = "enable-mtom")
/*     */   protected TrueFalseType enableMtom;
/*     */   @XmlElement(name = "mtom-threshold")
/*     */   protected XsdNonNegativeIntegerType mtomThreshold;
/*     */   protected AddressingType addressing;
/*     */   @XmlElement(name = "respect-binding")
/*     */   protected RespectBindingType respectBinding;
/*     */   @XmlElement(name = "protocol-binding")
/*     */   protected java.lang.String protocolBinding;
/*     */   @XmlElement(name = "service-endpoint-interface")
/*     */   protected FullyQualifiedClassType serviceEndpointInterface;
/*     */   @XmlElement(name = "service-impl-bean", required = true)
/*     */   protected ServiceImplBeanType serviceImplBean;
/*     */   protected List<HandlerType> handler;
/*     */   @XmlElement(name = "handler-chains")
/*     */   protected HandlerChainsType handlerChains;
/*     */   @XmlAttribute(name = "id")
/*     */   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/*     */   @XmlID
/*     */   @XmlSchemaType(name = "ID")
/*     */   protected java.lang.String id;
/*     */   @XmlLocation
/*     */   @XmlTransient
/*     */   protected Locator locator;
/*     */   
/*     */   public DescriptionType getDescription() {
/* 183 */     return this.description;
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
/*     */   public void setDescription(DescriptionType value) {
/* 195 */     this.description = value;
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
/*     */   public DisplayNameType getDisplayName() {
/* 207 */     return this.displayName;
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
/*     */   public void setDisplayName(DisplayNameType value) {
/* 219 */     this.displayName = value;
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
/*     */   public IconType getIcon() {
/* 231 */     return this.icon;
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
/*     */   public void setIcon(IconType value) {
/* 243 */     this.icon = value;
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
/*     */   public String getPortComponentName() {
/* 255 */     return this.portComponentName;
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
/*     */   public void setPortComponentName(String value) {
/* 267 */     this.portComponentName = value;
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
/*     */   public XsdQNameType getWsdlService() {
/* 279 */     return this.wsdlService;
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
/*     */   public void setWsdlService(XsdQNameType value) {
/* 291 */     this.wsdlService = value;
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
/*     */   public XsdQNameType getWsdlPort() {
/* 303 */     return this.wsdlPort;
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
/*     */   public void setWsdlPort(XsdQNameType value) {
/* 315 */     this.wsdlPort = value;
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
/*     */   public TrueFalseType getEnableMtom() {
/* 327 */     return this.enableMtom;
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
/*     */   public void setEnableMtom(TrueFalseType value) {
/* 339 */     this.enableMtom = value;
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
/*     */   public XsdNonNegativeIntegerType getMtomThreshold() {
/* 351 */     return this.mtomThreshold;
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
/*     */   public void setMtomThreshold(XsdNonNegativeIntegerType value) {
/* 363 */     this.mtomThreshold = value;
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
/*     */   public AddressingType getAddressing() {
/* 375 */     return this.addressing;
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
/*     */   public void setAddressing(AddressingType value) {
/* 387 */     this.addressing = value;
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
/*     */   public RespectBindingType getRespectBinding() {
/* 399 */     return this.respectBinding;
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
/*     */   public void setRespectBinding(RespectBindingType value) {
/* 411 */     this.respectBinding = value;
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
/*     */   public java.lang.String getProtocolBinding() {
/* 423 */     return this.protocolBinding;
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
/*     */   public void setProtocolBinding(java.lang.String value) {
/* 435 */     this.protocolBinding = value;
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
/*     */   public FullyQualifiedClassType getServiceEndpointInterface() {
/* 447 */     return this.serviceEndpointInterface;
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
/*     */   public void setServiceEndpointInterface(FullyQualifiedClassType value) {
/* 459 */     this.serviceEndpointInterface = value;
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
/*     */   public ServiceImplBeanType getServiceImplBean() {
/* 471 */     return this.serviceImplBean;
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
/*     */   public void setServiceImplBean(ServiceImplBeanType value) {
/* 483 */     this.serviceImplBean = value;
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
/*     */   public List<HandlerType> getHandler() {
/* 509 */     if (this.handler == null) {
/* 510 */       this.handler = new ArrayList<HandlerType>();
/*     */     }
/* 512 */     return this.handler;
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
/*     */   public HandlerChainsType getHandlerChains() {
/* 524 */     return this.handlerChains;
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
/*     */   public void setHandlerChains(HandlerChainsType value) {
/* 536 */     this.handlerChains = value;
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
/*     */   public java.lang.String getId() {
/* 548 */     return this.id;
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
/*     */   public void setId(java.lang.String value) {
/* 560 */     this.id = value;
/*     */   }
/*     */   
/*     */   public Locator sourceLocation() {
/* 564 */     return this.locator;
/*     */   }
/*     */   
/*     */   public void setSourceLocation(Locator newLocator) {
/* 568 */     this.locator = newLocator;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\config\metro\parser\jsr109\PortComponentType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */