/*     */ package com.sun.xml.ws.client;
/*     */ 
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.model.wsdl.WSDLServiceImpl;
/*     */ import com.sun.xml.ws.server.MonitorBase;
/*     */ import java.net.URL;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ManagedObject
/*     */ @Description("Metro Web Service client")
/*     */ @AMXMetadata(type = "WSClient")
/*     */ public final class MonitorRootClient
/*     */   extends MonitorBase
/*     */ {
/*     */   private final Stub stub;
/*     */   
/*     */   MonitorRootClient(Stub stub) {
/*  72 */     this.stub = stub;
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
/*     */   @ManagedAttribute
/*     */   private Container getContainer() {
/*  85 */     return this.stub.owner.getContainer();
/*     */   } @ManagedAttribute
/*     */   private Map<QName, PortInfo> qnameToPortInfoMap() {
/*  88 */     return this.stub.owner.getQNameToPortInfoMap();
/*     */   } @ManagedAttribute
/*     */   private QName serviceName() {
/*  91 */     return this.stub.owner.getServiceName();
/*     */   } @ManagedAttribute
/*     */   private Class serviceClass() {
/*  94 */     return this.stub.owner.getServiceClass();
/*     */   } @ManagedAttribute
/*     */   private URL wsdlDocumentLocation() {
/*  97 */     return this.stub.owner.getWSDLDocumentLocation();
/*     */   } @ManagedAttribute
/*     */   private WSDLServiceImpl wsdlService() {
/* 100 */     return this.stub.owner.getWsdlService();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\MonitorRootClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */