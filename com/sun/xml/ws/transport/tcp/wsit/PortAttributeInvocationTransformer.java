/*     */ package com.sun.xml.ws.transport.tcp.wsit;
/*     */ 
/*     */ import com.sun.xml.ws.transport.tcp.resources.MessagesMessages;
/*     */ import com.sun.xml.ws.transport.tcp.server.WSTCPModule;
/*     */ import com.sun.xml.ws.xmlfilter.Invocation;
/*     */ import com.sun.xml.ws.xmlfilter.InvocationTransformer;
/*     */ import com.sun.xml.ws.xmlfilter.XmlFilteringUtils;
/*     */ import com.sun.xml.ws.xmlfilter.XmlStreamWriterMethodType;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PortAttributeInvocationTransformer
/*     */   implements InvocationTransformer
/*     */ {
/*     */   private static final String RUNTIME_PORT_CHANGE_VALUE = "SET_BY_RUNTIME";
/*  70 */   private static final Logger logger = Logger.getLogger("com.sun.metro.transport.tcp.server");
/*     */ 
/*     */   
/*  73 */   private Collection<Invocation> invocationWrapper = new ArrayList<Invocation>(4);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isProcessingWSTCPAssertion;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile Invocation addPortAttributeInvocation;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<Invocation> transform(Invocation invocation) {
/*  89 */     Invocation resultInvocation = invocation;
/*  90 */     switch (invocation.getMethodType()) {
/*     */       case WRITE_START_ELEMENT:
/*  92 */         if (!this.isProcessingWSTCPAssertion) {
/*  93 */           this.isProcessingWSTCPAssertion = startBuffering(invocation);
/*     */         }
/*     */         break;
/*     */       case WRITE_END_ELEMENT:
/*  97 */         this.isProcessingWSTCPAssertion = false;
/*     */         break;
/*     */       case WRITE_ATTRIBUTE:
/* 100 */         if (this.isProcessingWSTCPAssertion && isReplacePortAttribute(invocation)) {
/*     */           try {
/* 102 */             initializeAddPortAttributeIfRequired();
/* 103 */             if (this.addPortAttributeInvocation == null && WSTCPModule.getInstance().getPort() == -1)
/*     */             {
/* 105 */               if (logger.isLoggable(Level.WARNING)) {
/* 106 */                 logger.log(Level.WARNING, MessagesMessages.WSTCP_1162_UNSUPPORTED_PORT_ATTRIBUTE());
/*     */               }
/*     */             }
/*     */           }
/* 110 */           catch (Exception e) {
/* 111 */             if (logger.isLoggable(Level.WARNING)) {
/* 112 */               logger.log(Level.WARNING, MessagesMessages.WSTCP_1161_ADD_PORT_ATTR_INIT_FAIL(), e);
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 117 */           resultInvocation = this.addPortAttributeInvocation;
/*     */         } 
/*     */         break;
/*     */       case CLOSE:
/* 121 */         this.isProcessingWSTCPAssertion = false;
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 127 */     this.invocationWrapper.clear();
/* 128 */     if (resultInvocation != null) {
/* 129 */       this.invocationWrapper.add(resultInvocation);
/*     */     }
/*     */     
/* 132 */     return this.invocationWrapper;
/*     */   }
/*     */   
/*     */   private void initializeAddPortAttributeIfRequired() throws Exception {
/*     */     int port;
/* 137 */     if (this.addPortAttributeInvocation == null && (port = WSTCPModule.getInstance().getPort()) != -1)
/*     */     {
/* 139 */       synchronized (this) {
/* 140 */         if (this.addPortAttributeInvocation == null) {
/* 141 */           this.addPortAttributeInvocation = Invocation.createInvocation(XMLStreamWriter.class.getMethod(XmlStreamWriterMethodType.WRITE_ATTRIBUTE.getMethodName(), new Class[] { String.class, String.class }), new Object[] { TCPConstants.TCPTRANSPORT_PORT_ATTRIBUTE.getLocalPart(), Integer.toString(port) });
/*     */         }
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
/*     */   private boolean isReplacePortAttribute(Invocation invocation) {
/* 155 */     XmlFilteringUtils.AttributeInfo attr = XmlFilteringUtils.getAttributeNameToWrite(invocation, "");
/* 156 */     if (TCPConstants.TCPTRANSPORT_PORT_ATTRIBUTE.equals(attr.getName())) {
/* 157 */       if ("SET_BY_RUNTIME".equals(attr.getValue())) return true;
/*     */ 
/*     */       
/* 160 */       String attrValue = attr.getValue();
/* 161 */       int portNumber = -1;
/* 162 */       if (attrValue != null) {
/*     */         try {
/* 164 */           portNumber = Integer.parseInt(attrValue);
/* 165 */         } catch (NumberFormatException e) {}
/*     */       }
/*     */ 
/*     */       
/* 169 */       if (portNumber > 0) return false;
/*     */       
/* 171 */       if (logger.isLoggable(Level.WARNING)) {
/* 172 */         logger.log(Level.WARNING, MessagesMessages.WSTCP_1160_PORT_ATTR_INVALID_VALUE(attrValue));
/*     */       }
/*     */       
/* 175 */       return true;
/*     */     } 
/*     */     
/* 178 */     return false;
/*     */   }
/*     */   
/*     */   private boolean startBuffering(Invocation invocation) {
/* 182 */     QName elementName = XmlFilteringUtils.getElementNameToWrite(invocation, "");
/* 183 */     return TCPConstants.TCPTRANSPORT_POLICY_ASSERTION.equals(elementName);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\wsit\PortAttributeInvocationTransformer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */