/*     */ package com.sun.xml.ws.server;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.BindingID;
/*     */ import com.sun.xml.ws.api.EndpointAddress;
/*     */ import com.sun.xml.ws.api.WSFeatureList;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.transport.http.HttpAdapter;
/*     */ import com.sun.xml.ws.util.RuntimeVersion;
/*     */ import java.net.URL;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ import org.glassfish.gmbal.AMXMetadata;
/*     */ import org.glassfish.gmbal.Description;
/*     */ import org.glassfish.gmbal.ManagedAttribute;
/*     */ import org.glassfish.gmbal.ManagedObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ManagedObject
/*     */ @Description("Metro Web Service endpoint")
/*     */ @AMXMetadata(type = "WSEndpoint")
/*     */ public final class MonitorRootService
/*     */   extends MonitorBase
/*     */ {
/*     */   private final WSEndpoint endpoint;
/*     */   
/*     */   MonitorRootService(WSEndpoint endpoint) {
/*  70 */     this.endpoint = endpoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("Policy associated with Endpoint")
/*     */   public String policy() {
/*  80 */     return (this.endpoint.getPolicyMap() != null) ? this.endpoint.getPolicyMap().toString() : null;
/*     */   }
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("Container")
/*     */   @NotNull
/*     */   public Container container() {
/*  87 */     return this.endpoint.getContainer();
/*     */   }
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("Port name")
/*     */   @NotNull
/*     */   public QName portName() {
/*  94 */     return this.endpoint.getPortName();
/*     */   }
/*     */   @ManagedAttribute
/*     */   @Description("Service name")
/*     */   @NotNull
/*     */   public QName serviceName() {
/* 100 */     return this.endpoint.getServiceName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("Binding SOAP Version")
/*     */   public String soapVersionHttpBindingId() {
/* 110 */     return (this.endpoint.getBinding().getSOAPVersion()).httpBindingId;
/*     */   }
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("Binding Addressing Version")
/*     */   public AddressingVersion addressingVersion() {
/* 116 */     return this.endpoint.getBinding().getAddressingVersion();
/*     */   }
/*     */   @ManagedAttribute
/*     */   @Description("Binding Identifier")
/*     */   @NotNull
/*     */   public BindingID bindingID() {
/* 122 */     return this.endpoint.getBinding().getBindingId();
/*     */   }
/*     */   @ManagedAttribute
/*     */   @Description("Binding features")
/*     */   @NotNull
/*     */   public WSFeatureList features() {
/* 128 */     return this.endpoint.getBinding().getFeatures();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("WSDLPort bound port type")
/*     */   public QName wsdlPortTypeName() {
/* 138 */     return (this.endpoint.getPort() != null) ? this.endpoint.getPort().getBinding().getPortTypeName() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("Endpoint address")
/*     */   public EndpointAddress wsdlEndpointAddress() {
/* 145 */     return (this.endpoint.getPort() != null) ? this.endpoint.getPort().getAddress() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("Documents referenced")
/*     */   public Set<String> serviceDefinitionImports() {
/* 156 */     return (this.endpoint.getServiceDefinition() != null) ? this.endpoint.getServiceDefinition().getPrimary().getImports() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("System ID where document is taken from")
/*     */   public URL serviceDefinitionURL() {
/* 163 */     return (this.endpoint.getServiceDefinition() != null) ? this.endpoint.getServiceDefinition().getPrimary().getURL() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("SEI model WSDL location")
/*     */   public String seiModelWSDLLocation() {
/* 174 */     return (this.endpoint.getSEIModel() != null) ? this.endpoint.getSEIModel().getWSDLLocation() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("JAX-WS runtime version")
/*     */   public String jaxwsRuntimeVersion() {
/* 185 */     return RuntimeVersion.VERSION.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("If true: show what goes across HTTP transport")
/*     */   public boolean dumpHTTPMessages() {
/* 194 */     return HttpAdapter.dump;
/*     */   }
/*     */   @ManagedAttribute
/*     */   @Description("Show what goes across HTTP transport")
/*     */   public void dumpHTTPMessages(boolean x) {
/* 199 */     HttpAdapter.setDump(x);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\MonitorRootService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */