/*     */ package com.sun.xml.rpc.processor.generator.nodes;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.Block;
/*     */ import com.sun.xml.rpc.processor.model.Fault;
/*     */ import com.sun.xml.rpc.processor.model.HeaderFault;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaException;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralAttributeMember;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralElementMember;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralWildcardMember;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureMember;
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
/*     */ public class ExceptionMappingNode
/*     */   extends JaxRpcMappingNode
/*     */ {
/*     */   private static final String MYNAME = "ExceptionMappingNode";
/*     */   
/*     */   public Node write(Node parent, String nodeName, Fault fault) throws Exception {
/*     */     String wsdlMsgStr;
/*  68 */     Element node = appendChild(parent, nodeName);
/*     */     
/*  70 */     Block block = fault.getBlock();
/*  71 */     JavaException javaEx = fault.getJavaException();
/*  72 */     String javaExName = javaEx.getName();
/*     */     
/*  74 */     QName wsdlMsg = null;
/*  75 */     if (fault instanceof HeaderFault) {
/*  76 */       wsdlMsg = ((HeaderFault)fault).getMessage();
/*  77 */       wsdlMsgStr = wsdlMsg.getLocalPart();
/*     */     } else {
/*  79 */       wsdlMsg = block.getName();
/*  80 */       wsdlMsgStr = fault.getName();
/*     */     } 
/*     */ 
/*     */     
/*  84 */     appendTextChild(node, "exception-type", javaExName);
/*     */ 
/*     */ 
/*     */     
/*  88 */     Element wsdlMessage = (Element)appendTextChild(node, "wsdl-message", "exMsgNS:" + wsdlMsgStr);
/*     */ 
/*     */ 
/*     */     
/*  92 */     wsdlMessage.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:exMsgNS", wsdlMsg.getNamespaceURI());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     if (fault instanceof HeaderFault) {
/*  99 */       appendTextChild(node, "wsdl-message-part-name", ((HeaderFault)fault).getPart());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 105 */     AbstractType faultType = block.getType();
/*     */     
/* 107 */     debug("ExceptionMappingNode", "000 exName = " + javaExName);
/* 108 */     debug("ExceptionMappingNode", "111 faultType = " + faultType.getClass().getName());
/* 109 */     debug("ExceptionMappingNode", "333 fault.block = " + fault.getBlock().getName());
/* 110 */     debug("ExceptionMappingNode", "555 fault = " + fault.getClass().getName());
/*     */     
/* 112 */     if (faultType instanceof com.sun.xml.rpc.processor.model.soap.SOAPStructureType) {
/* 113 */       debug("ExceptionMappingNode", "222. found soapstructuretype");
/*     */ 
/*     */ 
/*     */       
/* 117 */       JavaException javaException = fault.getJavaException();
/* 118 */       if (javaException.getMembersCount() > 0)
/*     */       {
/*     */         
/* 121 */         Node constParamOrderNode = appendChild(node, "constructor-parameter-order");
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 126 */         for (Iterator<JavaStructureMember> i = javaException.getMembers(); i.hasNext(); )
/*     */         {
/* 128 */           JavaStructureMember o = i.next();
/* 129 */           SOAPStructureMember owner = (SOAPStructureMember)o.getOwner();
/*     */           
/* 131 */           String elem = owner.getName().getLocalPart();
/*     */ 
/*     */           
/* 134 */           appendTextChild(constParamOrderNode, "element-name", elem);
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 140 */     else if (faultType instanceof com.sun.xml.rpc.processor.model.literal.LiteralStructuredType) {
/* 141 */       debug("ExceptionMappingNode", "333. found literalstructuredtype");
/*     */       
/* 143 */       JavaException javaException = fault.getJavaException();
/* 144 */       if (javaException.getMembersCount() > 0) {
/*     */ 
/*     */         
/* 147 */         Node constParamOrderNode = appendChild(node, "constructor-parameter-order");
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 152 */         for (Iterator<JavaStructureMember> i = javaException.getMembers(); i.hasNext(); ) {
/*     */           
/* 154 */           Object o = ((JavaStructureMember)i.next()).getOwner();
/* 155 */           debug("ExceptionMappingNode", "666 owner type = " + o.getClass().getName());
/*     */           
/* 157 */           String elemName = null;
/* 158 */           if (o instanceof LiteralWildcardMember) {
/* 159 */             LiteralWildcardMember owner = (LiteralWildcardMember)o;
/* 160 */             elemName = owner.getJavaStructureMember().getName();
/* 161 */           } else if (o instanceof LiteralElementMember) {
/* 162 */             LiteralElementMember owner = (LiteralElementMember)o;
/* 163 */             elemName = owner.getName().getLocalPart();
/* 164 */           } else if (o instanceof LiteralAttributeMember) {
/* 165 */             LiteralAttributeMember owner = (LiteralAttributeMember)o;
/*     */             
/* 167 */             elemName = owner.getName().getLocalPart();
/*     */           } 
/*     */           
/* 170 */           if (elemName != null)
/*     */           {
/* 172 */             appendTextChild(constParamOrderNode, "element-name", elemName);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 181 */     return node;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\nodes\ExceptionMappingNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */