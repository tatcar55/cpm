/*     */ package com.sun.xml.rpc.processor.generator.nodes;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.Configuration;
/*     */ import com.sun.xml.rpc.processor.config.RmiModelInfo;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralAttributeMember;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralElementMember;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralEnumerationType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralStructuredType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralWildcardMember;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPEnumerationType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureMember;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureType;
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
/*     */ 
/*     */ 
/*     */ public class JavaXmlTypeMappingNode
/*     */   extends JaxRpcMappingNode
/*     */ {
/*     */   private static final String MYNAME = "JavaXmlTypeMappingNode";
/*     */   
/*     */   public Node write(Node parent, String nodeName, AbstractType type, Configuration config, boolean isSimpleType) throws Exception {
/*  78 */     if (isSimpleType) {
/*  79 */       return writeSimpleType(parent, nodeName, type);
/*     */     }
/*  81 */     return writeComplexType(parent, nodeName, type, config);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Node writeComplexType(Node parent, String nodeName, AbstractType type, Configuration config) {
/*  91 */     Element node = null;
/*     */     
/*  93 */     if (type instanceof com.sun.xml.rpc.processor.model.soap.RPCRequestOrderedStructureType || type instanceof com.sun.xml.rpc.processor.model.soap.RPCRequestUnorderedStructureType || type instanceof com.sun.xml.rpc.processor.model.soap.RPCResponseStructureType || (type instanceof LiteralStructuredType && ((LiteralStructuredType)type).isRpcWrapper()))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  99 */       return node;
/*     */     }
/*     */     
/* 102 */     debug("JavaXmlTypeMappingNode", "ComplexType = " + type.getClass().getName());
/*     */     
/* 104 */     if (type instanceof SOAPStructureType) {
/*     */       
/* 106 */       node = appendChild(parent, nodeName);
/*     */       
/* 108 */       SOAPStructureType soapStructType = (SOAPStructureType)type;
/* 109 */       QName qname = soapStructType.getName();
/* 110 */       String namespaceURI = qname.getNamespaceURI();
/*     */ 
/*     */       
/* 113 */       appendTextChild(node, "java-type", soapStructType.getJavaType().getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 120 */       Element rootTypeNode = (Element)appendTextChild(node, "root-type-qname", "typeNS:" + qname.getLocalPart());
/*     */ 
/*     */ 
/*     */       
/* 124 */       rootTypeNode.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:typeNS", namespaceURI);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 130 */       appendTextChild(node, "qname-scope", "complexType");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 136 */       Iterator iter = soapStructType.getMembers();
/* 137 */       while (iter.hasNext())
/*     */       {
/* 139 */         Object o = iter.next();
/* 140 */         if (o != null) {
/* 141 */           debug("JavaXmlTypeMappingNode", "SOAPStructureMemberType = " + o.getClass().getName());
/*     */         }
/*     */         else {
/*     */           
/* 145 */           debug("JavaXmlTypeMappingNode", "SOAPStructureMemberType == NULL");
/*     */         } 
/*     */         
/* 148 */         SOAPStructureMember member = (SOAPStructureMember)o;
/*     */         
/* 150 */         String memberName = member.getName().getLocalPart();
/* 151 */         JavaStructureMember javaMember = member.getJavaStructureMember();
/*     */         
/* 153 */         String javaMemberName = javaMember.getName();
/*     */ 
/*     */         
/* 156 */         Node variableMappingNode = appendChild(node, "variable-mapping");
/*     */ 
/*     */ 
/*     */         
/* 160 */         appendTextChild(variableMappingNode, "java-variable-name", javaMemberName);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 166 */         if (javaMember.isPublic()) {
/* 167 */           forceAppendTextChild(variableMappingNode, "data-member", null);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 174 */         appendTextChild(variableMappingNode, "xml-element-name", memberName);
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 179 */     else if (type instanceof LiteralStructuredType) {
/*     */       
/* 181 */       debug("JavaXmlTypeMappingNode", "TYPE = " + type.getName() + "; ANONY = " + (String)type.getProperty("com.sun.xml.rpc.processor.model.AnonymousTypeName"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 189 */       node = appendChild(parent, nodeName);
/*     */       
/* 191 */       LiteralStructuredType litStructType = (LiteralStructuredType)type;
/* 192 */       QName qname = litStructType.getName();
/* 193 */       String namespaceURI = qname.getNamespaceURI();
/*     */ 
/*     */       
/* 196 */       appendTextChild(node, "java-type", litStructType.getJavaType().getName());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 201 */       if (litStructType.getProperty("com.sun.xml.rpc.processor.model.AnonymousTypeName") == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 206 */         if (namespaceURI == null || "".equals(namespaceURI))
/*     */         {
/*     */           
/* 209 */           if (config.getModelInfo() instanceof RmiModelInfo) {
/* 210 */             namespaceURI = ((RmiModelInfo)config.getModelInfo()).getTypeNamespaceURI();
/*     */           }
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 216 */         Element rootTypeNode = (Element)appendTextChild(node, "root-type-qname", "typeNS:" + qname.getLocalPart());
/*     */ 
/*     */ 
/*     */         
/* 220 */         rootTypeNode.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:typeNS", namespaceURI);
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 227 */         String name = namespaceURI + ":" + (String)litStructType.getProperty("com.sun.xml.rpc.processor.model.AnonymousTypeName");
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 232 */         Element anonymousTypeNode = (Element)appendTextChild(node, "anonymous-type-qname", name);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 239 */       appendTextChild(node, "qname-scope", "complexType");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 245 */       JavaStructureType t = (JavaStructureType)litStructType.getJavaType();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 250 */       for (Iterator<JavaStructureMember> iter = t.getMembers(); iter.hasNext(); ) {
/*     */         
/* 252 */         Object o = ((JavaStructureMember)iter.next()).getOwner();
/* 253 */         if (o != null) {
/* 254 */           debug("JavaXmlTypeMappingNode", "LiteralElementMemberType = " + o.getClass().getName());
/*     */         }
/*     */         else {
/*     */           
/* 258 */           debug("JavaXmlTypeMappingNode", "LiteralElementMemberType == NULL");
/*     */         } 
/*     */         
/* 261 */         if (o instanceof LiteralWildcardMember) {
/*     */           
/* 263 */           debug("JavaXmlTypeMappingNode", "LiteralWildcardMember = " + o);
/*     */           
/* 265 */           LiteralWildcardMember member = (LiteralWildcardMember)o;
/*     */           
/* 267 */           String memberName = member.getExcludedNamespaceName();
/*     */           
/* 269 */           JavaStructureMember javaMember = member.getJavaStructureMember();
/*     */           
/* 271 */           String javaMemberName = javaMember.getName();
/*     */           
/* 273 */           Node variableMappingNode = appendChild(node, "variable-mapping");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 279 */           appendTextChild(variableMappingNode, "java-variable-name", javaMemberName);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 285 */           if (javaMember.isPublic()) {
/* 286 */             forceAppendTextChild(variableMappingNode, "data-member", null);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 293 */           forceAppendTextChild(variableMappingNode, "xml-wildcard", null);
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 298 */         if (o instanceof LiteralElementMember) {
/* 299 */           LiteralElementMember member = (LiteralElementMember)o;
/*     */           
/* 301 */           String memberName = member.getName().getLocalPart();
/* 302 */           JavaStructureMember javaMember = member.getJavaStructureMember();
/*     */           
/* 304 */           String javaMemberName = javaMember.getName();
/*     */           
/* 306 */           Node variableMappingNode = appendChild(node, "variable-mapping");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 312 */           appendTextChild(variableMappingNode, "java-variable-name", javaMemberName);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 318 */           if (javaMember.isPublic()) {
/* 319 */             forceAppendTextChild(variableMappingNode, "data-member", null);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 326 */           appendTextChild(variableMappingNode, "xml-element-name", memberName);
/*     */           
/*     */           continue;
/*     */         } 
/* 330 */         if (o instanceof LiteralAttributeMember) {
/* 331 */           LiteralAttributeMember member = (LiteralAttributeMember)o;
/*     */           
/* 333 */           String memberName = member.getName().getLocalPart();
/* 334 */           JavaStructureMember javaMember = member.getJavaStructureMember();
/*     */           
/* 336 */           String javaMemberName = javaMember.getName();
/*     */           
/* 338 */           Node variableMappingNode = appendChild(node, "variable-mapping");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 344 */           appendTextChild(variableMappingNode, "java-variable-name", javaMemberName);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 350 */           if (javaMember.isPublic()) {
/* 351 */             forceAppendTextChild(variableMappingNode, "data-member", null);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 358 */           appendTextChild(variableMappingNode, "xml-attribute-name", memberName);
/*     */           
/*     */           continue;
/*     */         } 
/* 362 */         if (o instanceof com.sun.xml.rpc.processor.model.literal.LiteralContentMember) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 368 */         System.err.println("NOT SUPPORTED TYPE = " + o.getClass().getName());
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 374 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Node writeSimpleType(Node parent, String nodeName, AbstractType type) {
/* 382 */     Element node = null;
/*     */     
/* 384 */     if (type instanceof SOAPEnumerationType) {
/*     */       
/* 386 */       node = appendChild(parent, nodeName);
/*     */       
/* 388 */       SOAPEnumerationType soapEnumType = (SOAPEnumerationType)type;
/* 389 */       QName qname = type.getName();
/* 390 */       String namespaceURI = qname.getNamespaceURI();
/*     */ 
/*     */       
/* 393 */       appendTextChild(node, "java-type", soapEnumType.getJavaType().getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 400 */       Element rootTypeNode = (Element)appendTextChild(node, "root-type-qname", "typeNS:" + qname.getLocalPart());
/*     */ 
/*     */ 
/*     */       
/* 404 */       rootTypeNode.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:typeNS", namespaceURI);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 410 */       appendTextChild(node, "qname-scope", "simpleType");
/*     */ 
/*     */     
/*     */     }
/* 414 */     else if (type instanceof LiteralEnumerationType) {
/* 415 */       node = appendChild(parent, nodeName);
/*     */       
/* 417 */       LiteralEnumerationType litEnumType = (LiteralEnumerationType)type;
/* 418 */       QName qname = type.getName();
/* 419 */       String namespaceURI = qname.getNamespaceURI();
/*     */ 
/*     */       
/* 422 */       appendTextChild(node, "java-type", litEnumType.getJavaType().getName());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 427 */       if (litEnumType.getProperty("com.sun.xml.rpc.processor.model.AnonymousTypeName") == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 432 */         Element rootTypeNode = (Element)appendTextChild(node, "root-type-qname", "typeNS:" + qname.getLocalPart());
/*     */ 
/*     */ 
/*     */         
/* 436 */         rootTypeNode.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:typeNS", namespaceURI);
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 442 */         String name = namespaceURI + ":" + (String)litEnumType.getProperty("com.sun.xml.rpc.processor.model.AnonymousTypeName");
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 447 */         Element anonymousTypeNode = (Element)appendTextChild(node, "anonymous-type-qname", name);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 454 */       appendTextChild(node, "qname-scope", "simpleType");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 462 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node writeAnonymousArrayType(Node parent, String nodeName, LiteralStructuredType litStructType, Configuration config, boolean isSimpleType) {
/* 472 */     Element node = appendChild(parent, nodeName);
/*     */     
/* 474 */     QName qname = litStructType.getName();
/* 475 */     String namespaceURI = qname.getNamespaceURI();
/*     */ 
/*     */     
/* 478 */     String javaType = (String)litStructType.getProperty("com.sun.xml.rpc.processor.model.AnonymousArrayJavaType");
/*     */ 
/*     */     
/* 481 */     appendTextChild(node, "java-type", javaType);
/*     */ 
/*     */     
/* 484 */     String name = namespaceURI + ":" + (String)litStructType.getProperty("com.sun.xml.rpc.processor.model.AnonymousArrayTypeName");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 489 */     Element anonymousTypeNode = (Element)appendTextChild(node, "anonymous-type-qname", name);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 495 */     if (isSimpleType) {
/* 496 */       appendTextChild(node, "qname-scope", "simpleType");
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 501 */       appendTextChild(node, "qname-scope", "complexType");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 507 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\nodes\JavaXmlTypeMappingNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */