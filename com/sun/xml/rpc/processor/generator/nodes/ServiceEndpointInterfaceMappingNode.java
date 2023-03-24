/*     */ package com.sun.xml.rpc.processor.generator.nodes;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.Configuration;
/*     */ import com.sun.xml.rpc.processor.generator.StubTieGeneratorBase;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.Block;
/*     */ import com.sun.xml.rpc.processor.model.Operation;
/*     */ import com.sun.xml.rpc.processor.model.Parameter;
/*     */ import com.sun.xml.rpc.processor.model.Port;
/*     */ import com.sun.xml.rpc.processor.model.Request;
/*     */ import com.sun.xml.rpc.processor.model.Response;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaInterface;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaMethod;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaParameter;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureType;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralArrayWrapperType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralElementMember;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralSequenceType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureMember;
/*     */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPStyle;
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
/*     */ 
/*     */ 
/*     */ public class ServiceEndpointInterfaceMappingNode
/*     */   extends JaxRpcMappingNode
/*     */ {
/*     */   private static final String MYNAME = "ServiceEndpointInterfaceMappingNode";
/*     */   
/*     */   public Node write(Node parent, String nodeName, Configuration config, Port port) throws Exception {
/*  84 */     Element node = appendChild(parent, nodeName);
/*     */     
/*  86 */     ProcessorEnvironment env = (ProcessorEnvironment)config.getEnvironment();
/*     */ 
/*     */ 
/*     */     
/*  90 */     JavaInterface intf = port.getJavaInterface();
/*  91 */     String className = env.getNames().customJavaTypeClassName(intf);
/*  92 */     QName portTypeQName = (QName)port.getProperty("com.sun.xml.rpc.processor.model.WSDLPortTypeName");
/*     */ 
/*     */     
/*  95 */     QName bindingQName = (QName)port.getProperty("com.sun.xml.rpc.processor.model.WSDLBindingName");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     appendTextChild(node, "service-endpoint-interface", className);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     Element wsdlPortTypeNode = (Element)appendTextChild(node, "wsdl-port-type", "portTypeNS:" + portTypeQName.getLocalPart());
/*     */ 
/*     */ 
/*     */     
/* 111 */     wsdlPortTypeNode.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:portTypeNS", portTypeQName.getNamespaceURI());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     Element wsdlBindingNode = (Element)appendTextChild(node, "wsdl-binding", "bindingNS:" + bindingQName.getLocalPart());
/*     */ 
/*     */ 
/*     */     
/* 122 */     wsdlBindingNode.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:bindingNS", bindingQName.getNamespaceURI());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 128 */     for (Iterator<Operation> iter = port.getOperations(); iter.hasNext(); ) {
/* 129 */       String retType; Operation operation = iter.next();
/* 130 */       boolean isDocument = (operation.getStyle() != null && operation.getStyle().equals(SOAPStyle.DOCUMENT));
/*     */ 
/*     */       
/* 133 */       JavaMethod method = operation.getJavaMethod();
/* 134 */       String methodName = method.getName();
/* 135 */       Request request = operation.getRequest();
/*     */       
/* 137 */       debug("ServiceEndpointInterfaceMappingNode", "0000 request.class = " + request.getClass().getName());
/*     */ 
/*     */ 
/*     */       
/* 141 */       Iterator<Block> blocks = request.getBodyBlocks();
/* 142 */       int i = 0;
/* 143 */       while (blocks.hasNext()) {
/* 144 */         Block b = blocks.next();
/* 145 */         debug("ServiceEndpointInterfaceMappingNode", "int i = " + b.getName() + "; type = " + b.getLocation());
/*     */ 
/*     */         
/* 148 */         i++;
/*     */       } 
/*     */       
/* 151 */       QName inputMsgQName = (QName)request.getProperty("com.sun.xml.rpc.processor.model.WSDLMessageName");
/*     */ 
/*     */       
/* 154 */       Response response = operation.getResponse();
/* 155 */       QName outputMsgQName = null;
/* 156 */       if (response != null) {
/* 157 */         outputMsgQName = (QName)response.getProperty("com.sun.xml.rpc.processor.model.WSDLMessageName");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 164 */       boolean isWrapped = false;
/* 165 */       Block requestBlock = null;
/* 166 */       AbstractType requestType = null;
/* 167 */       if (request.getBodyBlockCount() > 0) {
/* 168 */         requestBlock = request.getBodyBlocks().next();
/* 169 */         requestType = requestBlock.getType();
/*     */       } 
/* 171 */       if (isDocument && request.getBodyBlockCount() > 0 && 
/* 172 */         requestType instanceof LiteralSequenceType) {
/* 173 */         isWrapped = ((LiteralSequenceType)requestType).isUnwrapped();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 179 */       Node semNode = appendChild(node, "service-endpoint-method-mapping");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 185 */       appendTextChild(semNode, "java-method-name", methodName);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 191 */       appendTextChild(semNode, "wsdl-operation", operation.getName().getLocalPart());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 197 */       if (isWrapped) {
/* 198 */         forceAppendTextChild(semNode, "wrapped-element", null);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 204 */       int paramPos = 0;
/* 205 */       Iterator<JavaParameter> iter2 = method.getParameters();
/* 206 */       for (; iter2.hasNext(); 
/* 207 */         paramPos++) {
/* 208 */         JavaParameter javaParam = iter2.next();
/* 209 */         Parameter param = javaParam.getParameter();
/* 210 */         Block paramBlock = param.getBlock();
/* 211 */         boolean soapHeader = (paramBlock.getLocation() == 2);
/*     */         
/* 213 */         String paramType = null;
/* 214 */         String paramMode = null;
/* 215 */         QName wsdlMsgQName = null;
/* 216 */         if (javaParam.isHolder()) {
/* 217 */           paramType = javaParam.getType().getName();
/* 218 */           if (param.getLinkedParameter() != null) {
/*     */             
/* 220 */             paramMode = "INOUT";
/* 221 */             wsdlMsgQName = inputMsgQName;
/* 222 */             debug("ServiceEndpointInterfaceMappingNode", "0100 prameMode INOUT");
/*     */           } else {
/*     */             
/* 225 */             paramMode = "OUT";
/* 226 */             wsdlMsgQName = outputMsgQName;
/* 227 */             debug("ServiceEndpointInterfaceMappingNode", "0200 prameMode OUT");
/*     */           } 
/*     */         } else {
/*     */           
/* 231 */           paramMode = "IN";
/*     */           
/* 233 */           paramType = getJavaTypeName(javaParam.getType());
/* 234 */           wsdlMsgQName = inputMsgQName;
/* 235 */           debug("ServiceEndpointInterfaceMappingNode", "0300 prameMode IN");
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 240 */         if (soapHeader) {
/* 241 */           wsdlMsgQName = (QName)paramBlock.getProperty("com.sun.xml.rpc.processor.model.WSDLMessageName");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 246 */         debug("ServiceEndpointInterfaceMappingNode", "0301 paramName = " + javaParam.getName() + "; soapHeader = " + soapHeader);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 253 */         String partName = null;
/*     */         
/* 255 */         partName = (String)param.getProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName");
/*     */ 
/*     */         
/* 258 */         debug("ServiceEndpointInterfaceMappingNode", "0206 property partName = " + partName);
/*     */         
/* 260 */         if (partName == null) {
/* 261 */           if (soapHeader) {
/*     */ 
/*     */             
/* 264 */             partName = paramBlock.getType().getName().getLocalPart();
/*     */ 
/*     */             
/* 267 */             debug("ServiceEndpointInterfaceMappingNode", "0302 header partName = " + partName);
/*     */           } else {
/* 269 */             JavaStructureMember javaStructMember = StubTieGeneratorBase.getJavaMember(param);
/*     */ 
/*     */             
/* 272 */             if (javaStructMember == null) {
/* 273 */               partName = javaParam.getName();
/*     */             } else {
/*     */               
/* 276 */               Object owner = javaStructMember.getOwner();
/* 277 */               debug("ServiceEndpointInterfaceMappingNode", "0400 owner.class = " + owner.getClass().getName());
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 282 */               if (owner instanceof SOAPStructureMember) {
/*     */                 
/* 284 */                 SOAPStructureMember soapStructMember = (SOAPStructureMember)owner;
/*     */                 
/* 286 */                 partName = soapStructMember.getName().getLocalPart();
/*     */               }
/* 288 */               else if (owner instanceof LiteralElementMember) {
/*     */ 
/*     */                 
/* 291 */                 LiteralElementMember litMember = (LiteralElementMember)owner;
/*     */ 
/*     */                 
/* 294 */                 debug("ServiceEndpointInterfaceMappingNode", "0500 LiteralElementMember litMember.getName = " + litMember.getName());
/*     */ 
/*     */ 
/*     */                 
/* 298 */                 debug("ServiceEndpointInterfaceMappingNode", "0600 partName= " + (String)param.getProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 305 */                 if (owner instanceof com.sun.xml.rpc.processor.model.literal.LiteralWildcardMember) {
/* 306 */                   partName = (String)param.getProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName");
/*     */                 
/*     */                 }
/*     */                 else {
/*     */                   
/* 311 */                   partName = litMember.getName().getLocalPart();
/*     */                 } 
/*     */ 
/*     */                 
/* 315 */                 debug("ServiceEndpointInterfaceMappingNode", "0700 LiteralElementMember partName = " + partName);
/*     */               
/*     */               }
/*     */               else {
/*     */ 
/*     */                 
/* 321 */                 partName = (String)param.getProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName");
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 326 */                 debug("ServiceEndpointInterfaceMappingNode", "0800 partName = " + partName);
/*     */               } 
/* 328 */               debug("ServiceEndpointInterfaceMappingNode", "0303 not header partName = " + partName);
/*     */             } 
/*     */           } 
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 335 */         if (wsdlMsgQName == null) {
/* 336 */           debug("ServiceEndpointInterfaceMappingNode", "0900 wsdlMsgQName NULL");
/*     */         } else {
/* 338 */           debug("ServiceEndpointInterfaceMappingNode", "1000 wsdlMsgQName NOT NULL" + wsdlMsgQName);
/*     */         } 
/*     */         
/* 341 */         debug("ServiceEndpointInterfaceMappingNode", "1101 param-position = " + paramPos);
/* 342 */         debug("ServiceEndpointInterfaceMappingNode", "1102 param-type = " + paramType);
/* 343 */         debug("ServiceEndpointInterfaceMappingNode", "1103 partname = " + partName);
/* 344 */         debug("ServiceEndpointInterfaceMappingNode", "1104 paramMode = " + paramMode);
/*     */ 
/*     */         
/* 347 */         Node methodParamPartsNode = appendChild(semNode, "method-param-parts-mapping");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 353 */         appendTextChild(methodParamPartsNode, "param-position", paramPos);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 359 */         appendTextChild(methodParamPartsNode, "param-type", paramType);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 365 */         Node wsdlMsgMappingNode = appendChild(methodParamPartsNode, "wsdl-message-mapping");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 372 */         Element wsdlMsg = (Element)appendTextChild(wsdlMsgMappingNode, "wsdl-message", "wsdlMsgNS:" + wsdlMsgQName.getLocalPart());
/*     */ 
/*     */ 
/*     */         
/* 376 */         wsdlMsg.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:wsdlMsgNS", wsdlMsgQName.getNamespaceURI());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 382 */         appendTextChild(wsdlMsgMappingNode, "wsdl-message-part-name", partName);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 388 */         appendTextChild(wsdlMsgMappingNode, "parameter-mode", paramMode);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 394 */         if (soapHeader) {
/* 395 */           forceAppendTextChild(wsdlMsgMappingNode, "soap-header", null);
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 404 */       JavaType returnType = method.getReturnType();
/* 405 */       boolean hasReturn = (returnType != null && !returnType.getName().equals("void"));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 410 */       if (!hasReturn) {
/* 411 */         retType = "void";
/*     */       } else {
/*     */         
/* 414 */         retType = getJavaTypeName(method.getReturnType());
/*     */       } 
/*     */ 
/*     */       
/* 418 */       if (hasReturn) {
/*     */ 
/*     */         
/* 421 */         Node wsdlReturnValueNode = appendChild(semNode, "wsdl-return-value-mapping");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 427 */         appendTextChild(wsdlReturnValueNode, "method-return-value", retType);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 433 */         Parameter resultParam = response.getParameters().next();
/*     */         
/* 435 */         JavaStructureMember resultJavaStructMember = StubTieGeneratorBase.getJavaMember(resultParam);
/*     */ 
/*     */         
/* 438 */         String retPartName = null;
/*     */         
/* 440 */         retPartName = (String)resultParam.getProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName");
/*     */ 
/*     */ 
/*     */         
/* 444 */         debug("ServiceEndpointInterfaceMappingNode", "RET.  partName = " + retPartName);
/*     */         
/* 446 */         if (retPartName == null) {
/* 447 */           if (resultJavaStructMember == null) {
/* 448 */             retPartName = resultParam.getName();
/*     */           } else {
/*     */             
/* 451 */             Object owner = resultJavaStructMember.getOwner();
/* 452 */             debug("ServiceEndpointInterfaceMappingNode", "return.owner.class = " + owner.getClass().getName());
/*     */ 
/*     */ 
/*     */             
/* 456 */             if (owner instanceof SOAPStructureMember) {
/*     */               
/* 458 */               retPartName = ((SOAPStructureMember)owner).getName().getLocalPart();
/*     */ 
/*     */             
/*     */             }
/* 462 */             else if (owner instanceof LiteralElementMember) {
/*     */ 
/*     */               
/* 465 */               LiteralElementMember litMember = (LiteralElementMember)owner;
/*     */               
/* 467 */               retPartName = litMember.getName().getLocalPart();
/* 468 */               debug("ServiceEndpointInterfaceMappingNode", "RET. LiteralElementMember partName = " + retPartName);
/*     */ 
/*     */             
/*     */             }
/*     */             else {
/*     */ 
/*     */               
/* 475 */               retPartName = (String)resultParam.getProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName");
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 480 */               debug("ServiceEndpointInterfaceMappingNode", "RET. doc lit partName = " + retPartName);
/*     */             } 
/*     */           } 
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 489 */         Element wsdlMsgNode = (Element)appendTextChild(wsdlReturnValueNode, "wsdl-message", "wsdlMsgNS:" + outputMsgQName.getLocalPart());
/*     */ 
/*     */ 
/*     */         
/* 493 */         wsdlMsgNode.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:wsdlMsgNS", outputMsgQName.getNamespaceURI());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 499 */         appendTextChild(wsdlReturnValueNode, "wsdl-message-part-name", retPartName);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 507 */       Iterator<String> exceptions = method.getExceptions();
/*     */       
/* 509 */       while (exceptions.hasNext()) {
/* 510 */         String exception = exceptions.next();
/*     */       }
/*     */     } 
/*     */     
/* 514 */     return node;
/*     */   }
/*     */   
/*     */   private String getJavaTypeName(JavaType type) {
/*     */     String typeName;
/* 519 */     if (type instanceof JavaStructureType && ((JavaStructureType)type).getOwner() instanceof LiteralArrayWrapperType) {
/*     */ 
/*     */       
/* 522 */       typeName = ((LiteralArrayWrapperType)((JavaStructureType)type).getOwner()).getJavaArrayType().getName();
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 529 */       typeName = type.getName();
/*     */     } 
/* 531 */     debug("ServiceEndpointInterfaceMappingNode", "getting JavaTypeName: " + type.getName() + " returned: " + typeName);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 537 */     return typeName;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\nodes\ServiceEndpointInterfaceMappingNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */