/*     */ package com.sun.xml.rpc.processor.generator.nodes;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.Configuration;
/*     */ import com.sun.xml.rpc.processor.config.ModelInfo;
/*     */ import com.sun.xml.rpc.processor.config.NamespaceMappingInfo;
/*     */ import com.sun.xml.rpc.processor.config.NamespaceMappingRegistryInfo;
/*     */ import com.sun.xml.rpc.processor.config.RmiModelInfo;
/*     */ import com.sun.xml.rpc.processor.config.WSDLModelInfo;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.Block;
/*     */ import com.sun.xml.rpc.processor.model.Fault;
/*     */ import com.sun.xml.rpc.processor.model.Model;
/*     */ import com.sun.xml.rpc.processor.model.Port;
/*     */ import com.sun.xml.rpc.processor.model.Service;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralStructuredType;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JavaWsdlMappingNode
/*     */   extends JaxRpcMappingNode
/*     */ {
/*     */   private static final String JAXRPC_MAPPING_SCHEMA_VERSION = "1.1";
/*     */   private static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
/*     */   private static final String W3C_XML_SCHEMA_INSTANCE = "http://www.w3.org/2001/XMLSchema-instance";
/*     */   private static final String SCHEMA_LOCATION_TAG = "xsi:schemaLocation";
/*     */   private static final String JAXRPC_MAPPING_SCHEMA_LOCATION = "http://java.sun.com/xml/ns/j2ee    http://www.ibm.com/webservices/xsd/j2ee_jaxrpc_mapping_1_1.xsd";
/*     */   private static final String MYNAME = "JavaWsdlMappingNode";
/*     */   
/*     */   public Node write(Node parent, String nodeName, Model model, Configuration config) throws Exception {
/*  86 */     Element node = appendChild(parent, nodeName);
/*  87 */     addNodeAttributes(node);
/*     */     
/*  89 */     TypeVisitor visitor = new TypeVisitor(config);
/*  90 */     visitor.visit(model);
/*     */ 
/*     */     
/*  93 */     writePackageMapping(node, model, config, visitor);
/*     */ 
/*     */     
/*  96 */     writeJavaXmlTypeMapping(node, model, config, visitor);
/*     */ 
/*     */     
/*  99 */     writeExceptionMapping(node, model, visitor);
/*     */ 
/*     */     
/* 102 */     for (Iterator<Service> iter = model.getServices(); iter.hasNext(); ) {
/* 103 */       Service service = iter.next();
/*     */ 
/*     */       
/* 106 */       ServiceInterfaceMappingNode siNode = new ServiceInterfaceMappingNode();
/*     */       
/* 108 */       siNode.write(node, "service-interface-mapping", config, service);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 115 */       for (Iterator<Port> portIter = service.getPorts(); portIter.hasNext(); ) {
/* 116 */         Port port = portIter.next();
/*     */         
/* 118 */         QName bindingQName = (QName)port.getProperty("com.sun.xml.rpc.processor.model.WSDLBindingName");
/*     */ 
/*     */ 
/*     */         
/* 122 */         if (!this._bindingSet.contains(bindingQName)) {
/* 123 */           this._bindingSet.add(bindingQName);
/*     */           
/* 125 */           ServiceEndpointInterfaceMappingNode seiNode = new ServiceEndpointInterfaceMappingNode();
/*     */           
/* 127 */           seiNode.write(node, "service-endpoint-interface-mapping", config, port);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 137 */     return node;
/*     */   }
/*     */   
/*     */   private void addNodeAttributes(Element node) {
/* 141 */     node.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "http://java.sun.com/xml/ns/j2ee");
/*     */ 
/*     */ 
/*     */     
/* 145 */     node.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
/*     */ 
/*     */ 
/*     */     
/* 149 */     node.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation", "http://java.sun.com/xml/ns/j2ee    http://www.ibm.com/webservices/xsd/j2ee_jaxrpc_mapping_1_1.xsd");
/*     */ 
/*     */ 
/*     */     
/* 153 */     node.setAttribute("version", "1.1");
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
/*     */   private void writePackageMapping(Node parent, Model model, Configuration config, TypeVisitor visitor) throws Exception {
/* 165 */     ModelInfo modelInfo = (ModelInfo)config.getModelInfo();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 171 */     String targetNamespace = null;
/* 172 */     String javaPackage = null;
/* 173 */     if (modelInfo instanceof WSDLModelInfo) {
/* 174 */       WSDLModelInfo wsdlModelInfo = (WSDLModelInfo)modelInfo;
/* 175 */       targetNamespace = model.getTargetNamespaceURI();
/* 176 */       javaPackage = wsdlModelInfo.getJavaPackageName();
/* 177 */     } else if (modelInfo instanceof RmiModelInfo) {
/* 178 */       String typeNamespace = null;
/* 179 */       RmiModelInfo rmiModelInfo = (RmiModelInfo)modelInfo;
/* 180 */       targetNamespace = rmiModelInfo.getTargetNamespaceURI();
/* 181 */       typeNamespace = rmiModelInfo.getTypeNamespaceURI();
/* 182 */       javaPackage = rmiModelInfo.getJavaPackageName();
/*     */       
/* 184 */       PackageMappingNode packageMappingNode = new PackageMappingNode();
/* 185 */       packageMappingNode.write(parent, "package-mapping", javaPackage, typeNamespace);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 190 */       this._namespaceSet.add(typeNamespace);
/*     */     } 
/*     */     
/* 193 */     PackageMappingNode pmNode = new PackageMappingNode();
/* 194 */     pmNode.write(parent, "package-mapping", javaPackage, targetNamespace);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 199 */     this._namespaceSet.add(targetNamespace);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 205 */     NamespaceMappingRegistryInfo nsInfo = modelInfo.getNamespaceMappingRegistry();
/*     */ 
/*     */     
/* 208 */     if (nsInfo != null) {
/* 209 */       for (Iterator<NamespaceMappingInfo> iterator = nsInfo.getNamespaceMappings(); iterator.hasNext(); ) {
/* 210 */         NamespaceMappingInfo ns = iterator.next();
/* 211 */         String namespace = ns.getNamespaceURI();
/* 212 */         String packageName = ns.getJavaPackageName();
/*     */         
/* 214 */         if (!this._namespaceSet.contains(namespace)) {
/* 215 */           pmNode = new PackageMappingNode();
/* 216 */           pmNode.write(parent, "package-mapping", packageName, namespace);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 231 */     Set namespaces = visitor.getNamespacePackages().keySet();
/* 232 */     for (Iterator<String> i = namespaces.iterator(); i.hasNext(); ) {
/* 233 */       String namespace = i.next();
/* 234 */       if (!this._namespaceSet.contains(namespace)) {
/* 235 */         String packageName = (String)visitor.getNamespacePackages().get(namespace);
/*     */         
/* 237 */         pmNode = new PackageMappingNode();
/* 238 */         pmNode.write(parent, "package-mapping", packageName, namespace);
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
/*     */   
/*     */   private void writeJavaXmlTypeMapping(Node parent, Model model, Configuration config, TypeVisitor visitor) throws Exception {
/* 254 */     Set complexTypeSet = visitor.getComplexTypes();
/* 255 */     for (Iterator<AbstractType> iterator1 = complexTypeSet.iterator(); iterator1.hasNext(); ) {
/* 256 */       AbstractType type = iterator1.next();
/* 257 */       JavaXmlTypeMappingNode javaxmlNode = new JavaXmlTypeMappingNode();
/* 258 */       javaxmlNode.write(parent, "java-xml-type-mapping", type, config, false);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 269 */     for (Iterator<AbstractType> it = complexTypeSet.iterator(); it.hasNext(); ) {
/* 270 */       AbstractType type = it.next();
/* 271 */       if (type instanceof LiteralStructuredType && type.getProperty("com.sun.xml.rpc.processor.model.AnonymousArrayTypeName") != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 276 */         LiteralStructuredType litStructType = (LiteralStructuredType)type;
/*     */         
/* 278 */         JavaXmlTypeMappingNode javaxmlNode = new JavaXmlTypeMappingNode();
/*     */         
/* 280 */         javaxmlNode.writeAnonymousArrayType(parent, "java-xml-type-mapping", litStructType, config, false);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 289 */     Set simpleTypeSet = visitor.getSimpleTypes();
/* 290 */     for (Iterator<AbstractType> iterator2 = simpleTypeSet.iterator(); iterator2.hasNext(); ) {
/* 291 */       AbstractType type = iterator2.next();
/* 292 */       JavaXmlTypeMappingNode javaxmlNode = new JavaXmlTypeMappingNode();
/* 293 */       javaxmlNode.write(parent, "java-xml-type-mapping", type, config, true);
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
/*     */   
/*     */   private void writeExceptionMapping(Node parent, Model model, TypeVisitor visitor) throws Exception {
/* 308 */     Set faultSet = visitor.getFaults();
/* 309 */     for (Iterator<Fault> it = faultSet.iterator(); it.hasNext(); ) {
/*     */       
/* 311 */       Fault fault = it.next();
/* 312 */       Block block = fault.getBlock();
/* 313 */       QName wsdlMsg = block.getName();
/* 314 */       if (!this._faultSet.contains(wsdlMsg)) {
/* 315 */         this._faultSet.add(wsdlMsg);
/* 316 */         ExceptionMappingNode exceptionNode = new ExceptionMappingNode();
/* 317 */         exceptionNode.write(parent, "exception-mapping", fault);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 326 */   private Set _faultSet = new HashSet();
/* 327 */   private Set _bindingSet = new HashSet();
/*     */   
/* 329 */   private Set _namespaceSet = new HashSet();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\nodes\JavaWsdlMappingNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */