/*     */ package com.sun.xml.ws.model.wsdl;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLExtension;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLModel;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLService;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamReader;
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
/*     */ public final class WSDLServiceImpl
/*     */   extends AbstractExtensibleImpl
/*     */   implements WSDLService
/*     */ {
/*     */   private final QName name;
/*     */   private final Map<QName, WSDLPortImpl> ports;
/*     */   private final WSDLModelImpl parent;
/*     */   
/*     */   public WSDLServiceImpl(XMLStreamReader xsr, WSDLModelImpl parent, QName name) {
/*  64 */     super(xsr);
/*  65 */     this.parent = parent;
/*  66 */     this.name = name;
/*  67 */     this.ports = new LinkedHashMap<QName, WSDLPortImpl>();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public WSDLModelImpl getParent() {
/*  72 */     return this.parent;
/*     */   }
/*     */   
/*     */   public QName getName() {
/*  76 */     return this.name;
/*     */   }
/*     */   
/*     */   public WSDLPortImpl get(QName portName) {
/*  80 */     return this.ports.get(portName);
/*     */   }
/*     */   
/*     */   public WSDLPort getFirstPort() {
/*  84 */     if (this.ports.isEmpty()) {
/*  85 */       return null;
/*     */     }
/*  87 */     return this.ports.values().iterator().next();
/*     */   }
/*     */   
/*     */   public Iterable<WSDLPortImpl> getPorts() {
/*  91 */     return this.ports.values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public WSDLPortImpl getMatchingPort(QName portTypeName) {
/*  99 */     for (WSDLPortImpl port : getPorts()) {
/* 100 */       QName ptName = port.getBinding().getPortTypeName();
/* 101 */       assert ptName != null;
/* 102 */       if (ptName.equals(portTypeName))
/* 103 */         return port; 
/*     */     } 
/* 105 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(QName portName, WSDLPortImpl port) {
/* 116 */     if (portName == null || port == null)
/* 117 */       throw new NullPointerException(); 
/* 118 */     this.ports.put(portName, port);
/*     */   }
/*     */   
/*     */   void freeze(WSDLModelImpl root) {
/* 122 */     for (WSDLPortImpl port : this.ports.values())
/* 123 */       port.freeze(root); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\wsdl\WSDLServiceImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */