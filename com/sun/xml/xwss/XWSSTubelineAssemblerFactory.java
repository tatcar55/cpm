/*     */ package com.sun.xml.xwss;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.BindingID;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.WSService;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.ClientTubeAssemblerContext;
/*     */ import com.sun.xml.ws.api.pipe.ServerTubeAssemblerContext;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubelineAssembler;
/*     */ import com.sun.xml.ws.api.pipe.TubelineAssemblerFactory;
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XWSSTubelineAssemblerFactory
/*     */   extends TubelineAssemblerFactory
/*     */ {
/*     */   private static final String SERVLET_CONTEXT_CLASSNAME = "javax.servlet.ServletContext";
/*     */   private static final String addrVersionClass = "com.sun.xml.ws.api.addressing.AddressingVersion";
/*  73 */   private static final boolean disable = Boolean.getBoolean("DISABLE_XWSS_SECURITY");
/*     */   
/*     */   private static class XWSSTubelineAssembler
/*     */     implements TubelineAssembler {
/*     */     private final BindingID bindingId;
/*     */     
/*     */     XWSSTubelineAssembler(BindingID bindingId) {
/*  80 */       this.bindingId = bindingId;
/*     */     }
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public Tube createClient(@NotNull ClientTubeAssemblerContext context) {
/*  86 */       Tube p = context.createTransportTube();
/*  87 */       if (isSecurityConfigPresent(context)) {
/*  88 */         p = initializeXWSSClientTube(context.getWsdlModel(), context.getService(), context.getBinding(), p);
/*     */       }
/*     */ 
/*     */       
/*  92 */       p = context.createClientMUTube(p);
/*  93 */       p = context.createHandlerTube(p);
/*     */       
/*  95 */       if (isAddressingEnabled(context.getWsdlModel(), context.getBinding())) {
/*  96 */         p = context.createWsaTube(p);
/*     */       }
/*     */       
/*  99 */       return p;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Tube createServer(@NotNull ServerTubeAssemblerContext context) {
/* 104 */       Tube p = context.getTerminalTube();
/* 105 */       p = context.createHandlerTube(p);
/* 106 */       p = context.createServerMUTube(p);
/* 107 */       p = context.createMonitoringTube(p);
/*     */ 
/*     */       
/* 110 */       if (isAddressingEnabled(context.getWsdlModel(), context.getEndpoint().getBinding())) {
/* 111 */         p = context.createWsaTube(p);
/*     */       }
/*     */       
/* 114 */       if (isSecurityConfigPresent(context)) {
/* 115 */         p = initializeXWSSServerTube(context.getEndpoint(), context.getWsdlModel(), p);
/*     */       }
/*     */       
/* 118 */       return p;
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean isAddressingEnabled(WSDLPort port, WSBinding binding) {
/* 123 */       Class<?> clazz = null;
/*     */       try {
/* 125 */         clazz = Thread.currentThread().getContextClassLoader().loadClass("com.sun.xml.ws.api.addressing.AddressingVersion");
/* 126 */       } catch (ClassNotFoundException ex) {
/* 127 */         return false;
/*     */       } 
/* 129 */       if (clazz != null) {
/*     */         try {
/* 131 */           Method meth = clazz.getMethod("isEnabled", new Class[] { WSBinding.class });
/* 132 */           Object result = meth.invoke(null, new Object[] { binding });
/* 133 */           if (result instanceof Boolean) {
/* 134 */             boolean ret = ((Boolean)result).booleanValue();
/* 135 */             return ret;
/*     */           } 
/* 137 */         } catch (IllegalAccessException ex) {
/* 138 */           throw new WebServiceException(ex);
/* 139 */         } catch (IllegalArgumentException ex) {
/* 140 */           throw new WebServiceException(ex);
/* 141 */         } catch (InvocationTargetException ex) {
/* 142 */           throw new WebServiceException(ex);
/* 143 */         } catch (NoSuchMethodException ex) {
/* 144 */           throw new WebServiceException(ex);
/* 145 */         } catch (SecurityException ex) {
/* 146 */           throw new WebServiceException(ex);
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 152 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static boolean isSecurityConfigPresent(ClientTubeAssemblerContext context) {
/* 158 */       String configUrl = "META-INF/client_security_config.xml";
/* 159 */       URL url = SecurityUtil.loadFromClasspath(configUrl);
/* 160 */       if (url != null) {
/* 161 */         return true;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 167 */       if (XWSSTubelineAssemblerFactory.disable) {
/* 168 */         return false;
/*     */       }
/* 170 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     private static boolean isSecurityConfigPresent(ServerTubeAssemblerContext context) {
/* 175 */       QName serviceQName = context.getEndpoint().getServiceName();
/*     */       
/* 177 */       String serviceLocalName = serviceQName.getLocalPart();
/* 178 */       Container container = context.getEndpoint().getContainer();
/*     */       
/* 180 */       Object ctxt = null;
/* 181 */       if (container != null) {
/*     */         try {
/* 183 */           Class<?> contextClass = Class.forName("javax.servlet.ServletContext");
/* 184 */           ctxt = container.getSPI(contextClass);
/* 185 */         } catch (ClassNotFoundException e) {}
/*     */       }
/*     */ 
/*     */       
/* 189 */       String serverName = "server";
/* 190 */       if (ctxt != null) {
/* 191 */         String serverConfig = "/WEB-INF/" + serverName + "_" + "security_config.xml";
/* 192 */         URL url = SecurityUtil.loadFromContext(serverConfig, ctxt);
/*     */         
/* 194 */         if (url == null) {
/* 195 */           serverConfig = "/WEB-INF/" + serviceLocalName + "_" + "security_config.xml";
/* 196 */           url = SecurityUtil.loadFromContext(serverConfig, ctxt);
/*     */         } 
/*     */         
/* 199 */         if (url != null) {
/* 200 */           return true;
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 205 */         String serverConfig = "META-INF/" + serverName + "_" + "security_config.xml";
/* 206 */         URL url = SecurityUtil.loadFromClasspath(serverConfig);
/* 207 */         if (url == null) {
/* 208 */           serverConfig = "META-INF/" + serviceLocalName + "_" + "security_config.xml";
/* 209 */           url = SecurityUtil.loadFromClasspath(serverConfig);
/*     */         } 
/*     */         
/* 212 */         if (url != null) {
/* 213 */           return true;
/*     */         }
/*     */       } 
/* 216 */       return false;
/*     */     }
/*     */     
/*     */     private static Tube initializeXWSSClientTube(WSDLPort prt, WSService svc, WSBinding bnd, Tube nextP) {
/* 220 */       return (Tube)new XWSSClientTube(prt, svc, bnd, nextP);
/*     */     }
/*     */ 
/*     */     
/*     */     private static Tube initializeXWSSServerTube(WSEndpoint epoint, WSDLPort prt, Tube nextP) {
/* 225 */       return (Tube)new XWSSServerTube(epoint, prt, nextP);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TubelineAssembler doCreate(BindingID bindingId) {
/* 232 */     return new XWSSTubelineAssembler(bindingId);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\xwss\XWSSTubelineAssemblerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */