/*     */ package com.sun.xml.rpc.processor.generator.nodes;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.Configuration;
/*     */ import com.sun.xml.rpc.processor.generator.Names;
/*     */ import com.sun.xml.rpc.processor.model.Port;
/*     */ import com.sun.xml.rpc.processor.model.Service;
/*     */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.QName;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServiceInterfaceMappingNode
/*     */   extends JaxRpcMappingNode
/*     */ {
/*     */   private static final String MYNAME = "ServiceInterfaceMappingNode";
/*     */   
/*     */   public Node write(Node parent, String nodeName, Configuration config, Service service) throws Exception {
/*  66 */     Element node = appendChild(parent, nodeName);
/*     */     
/*  68 */     ProcessorEnvironment env = (ProcessorEnvironment)config.getEnvironment();
/*     */ 
/*     */     
/*  71 */     QName serviceQName = service.getName();
/*  72 */     String serviceNS = serviceQName.getNamespaceURI();
/*  73 */     String serviceJavaName = env.getNames().customJavaTypeClassName(service.getJavaInterface());
/*     */ 
/*     */ 
/*     */     
/*  77 */     appendTextChild(node, "service-interface", serviceJavaName);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  84 */     Element wsdlServiceName = (Element)appendTextChild(node, "wsdl-service-name", "serviceNS:" + serviceQName.getLocalPart());
/*     */ 
/*     */ 
/*     */     
/*  88 */     wsdlServiceName.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:serviceNS", serviceNS);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     for (Iterator<Port> portIter = service.getPorts(); portIter.hasNext(); ) {
/*  95 */       Port port = portIter.next();
/*  96 */       QName portQName = (QName)port.getProperty("com.sun.xml.rpc.processor.model.WSDLPortName");
/*     */ 
/*     */       
/*  99 */       String portName = portQName.getLocalPart();
/* 100 */       String portJavaName = Names.getPortName(port);
/*     */ 
/*     */       
/* 103 */       Node portMappingNode = appendChild(node, "port-mapping");
/*     */ 
/*     */ 
/*     */       
/* 107 */       appendTextChild(portMappingNode, "port-name", portName);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 113 */       appendTextChild(portMappingNode, "java-port-name", portJavaName);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     return node;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\nodes\ServiceInterfaceMappingNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */