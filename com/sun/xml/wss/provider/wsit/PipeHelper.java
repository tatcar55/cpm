/*     */ package com.sun.xml.wss.provider.wsit;
/*     */ 
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.WSService;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Messages;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.server.BoundEndpoint;
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.api.server.Module;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.security.spi.SecurityContext;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.message.AuthException;
/*     */ import javax.security.auth.message.MessageInfo;
/*     */ import javax.security.auth.message.config.ClientAuthConfig;
/*     */ import javax.security.auth.message.config.ClientAuthContext;
/*     */ import javax.security.auth.message.config.ServerAuthConfig;
/*     */ import javax.security.auth.message.config.ServerAuthContext;
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
/*     */ 
/*     */ public class PipeHelper
/*     */   extends ConfigHelper
/*     */ {
/*     */   private SEIModel seiModel;
/*     */   private SOAPVersion soapVersion;
/*     */   private static final String SECURITY_CONTEXT_PROP = "META-INF/services/com.sun.xml.ws.security.spi.SecurityContext";
/*  88 */   private Class secCntxt = null;
/*  89 */   private SecurityContext context = null;
/*     */   
/*     */   public PipeHelper(String layer, Map<Object, Object> map, CallbackHandler cbh) {
/*  92 */     init(layer, getAppCtxt(map), map, cbh);
/*     */     
/*  94 */     this.seiModel = (SEIModel)map.get("SEI_MODEL");
/*  95 */     WSBinding binding = (WSBinding)map.get("BINDING");
/*  96 */     if (binding == null) {
/*  97 */       WSEndpoint endPoint = (WSEndpoint)map.get("ENDPOINT");
/*  98 */       if (endPoint != null) {
/*  99 */         binding = endPoint.getBinding();
/*     */       }
/*     */     } 
/* 102 */     this.soapVersion = (binding != null) ? binding.getSOAPVersion() : SOAPVersion.SOAP_11;
/*     */     
/* 104 */     URL url = loadFromClasspath("META-INF/services/com.sun.xml.ws.security.spi.SecurityContext");
/* 105 */     if (url != null) {
/* 106 */       InputStream is = null;
/*     */       try {
/* 108 */         is = url.openStream();
/* 109 */         ByteArrayOutputStream os = new ByteArrayOutputStream();
/* 110 */         int val = is.read();
/* 111 */         while (val != -1) {
/* 112 */           os.write(val);
/* 113 */           val = is.read();
/*     */         } 
/* 115 */         String className = os.toString();
/* 116 */         this.secCntxt = Class.forName(className, true, Thread.currentThread().getContextClassLoader());
/* 117 */         if (this.secCntxt != null) {
/* 118 */           this.context = this.secCntxt.newInstance();
/*     */         }
/* 120 */       } catch (Exception e) {
/* 121 */         throw new WebServiceException(e);
/*     */       } finally {
/*     */         try {
/* 124 */           is.close();
/* 125 */         } catch (IOException ex) {
/* 126 */           Logger.getLogger(PipeHelper.class.getName()).log(Level.WARNING, (String)null, ex);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientAuthContext getClientAuthContext(MessageInfo info, Subject s) throws AuthException {
/* 135 */     ClientAuthConfig c = (ClientAuthConfig)getAuthConfig(false);
/* 136 */     if (c != null) {
/* 137 */       addModel(info, this.map);
/* 138 */       return c.getAuthContext(c.getAuthContextID(info), s, this.map);
/*     */     } 
/* 140 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerAuthContext getServerAuthContext(MessageInfo info, Subject s) throws AuthException {
/* 146 */     ServerAuthConfig c = (ServerAuthConfig)getAuthConfig(true);
/* 147 */     if (c != null) {
/* 148 */       addModel(info, this.map);
/* 149 */       return c.getAuthContext(c.getAuthContextID(info), s, this.map);
/*     */     } 
/* 151 */     return null;
/*     */   }
/*     */   
/*     */   public static URL loadFromClasspath(String configFileName) {
/* 155 */     ClassLoader loader = Thread.currentThread().getContextClassLoader();
/* 156 */     if (loader == null) {
/* 157 */       return ClassLoader.getSystemResource(configFileName);
/*     */     }
/* 159 */     return loader.getResource(configFileName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Subject getClientSubject() {
/* 165 */     Subject s = null;
/* 166 */     if (this.context != null) {
/* 167 */       s = this.context.getSubject();
/*     */     }
/*     */     
/* 170 */     if (s == null) {
/* 171 */       s = Subject.getSubject(AccessController.getContext());
/*     */     }
/* 173 */     if (s == null) {
/* 174 */       s = new Subject();
/*     */     }
/* 176 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSessionToken(Map<Object, Object> m, MessageInfo info, Subject s) throws AuthException {
/* 182 */     ClientAuthConfig c = (ClientAuthConfig)getAuthConfig(false);
/* 183 */     if (c != null) {
/* 184 */       m.putAll(this.map);
/* 185 */       addModel(info, this.map);
/* 186 */       c.getAuthContext(c.getAuthContextID(info), s, m);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getModelName() {
/* 193 */     WSDLPort wsdlModel = (WSDLPort)getProperty("WSDL_MODEL");
/* 194 */     return (wsdlModel == null) ? "unknown" : wsdlModel.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet makeFaultResponse(Packet response, Throwable t) {
/* 201 */     if (!(t instanceof WebServiceException)) {
/* 202 */       t = new WebServiceException(t);
/*     */     }
/* 204 */     if (response == null) {
/* 205 */       response = new Packet();
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 210 */       return response.createResponse(Messages.create(t, this.soapVersion));
/* 211 */     } catch (Exception e) {
/* 212 */       response = new Packet();
/*     */       
/* 214 */       return response.createResponse(Messages.create(t, this.soapVersion));
/*     */     } 
/*     */   }
/*     */   public boolean isTwoWay(boolean twoWayIsDefault, Packet request) {
/* 218 */     boolean twoWay = twoWayIsDefault;
/* 219 */     Message m = request.getMessage();
/* 220 */     if (m != null) {
/* 221 */       WSDLPort wsdlModel = (WSDLPort)getProperty("WSDL_MODEL");
/*     */       
/* 223 */       if (wsdlModel != null) {
/* 224 */         twoWay = !m.isOneWay(wsdlModel);
/*     */       }
/*     */     } 
/* 227 */     return twoWay;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet getFaultResponse(Packet request, Packet response, Throwable t) {
/* 233 */     boolean twoWay = true;
/*     */     try {
/* 235 */       twoWay = isTwoWay(true, request);
/* 236 */     } catch (Exception e) {}
/*     */ 
/*     */     
/* 239 */     if (twoWay) {
/* 240 */       return makeFaultResponse(response, t);
/*     */     }
/* 242 */     return new Packet();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void disable() {
/* 248 */     this.listenerWrapper.disableWithRefCount();
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getAppCtxt(Map map) {
/* 253 */     String rvalue = null;
/* 254 */     WSEndpoint wse = (WSEndpoint)map.get("ENDPOINT");
/*     */     
/* 256 */     Container container = (Container)map.get("CONTAINER");
/*     */     
/* 258 */     if (wse != null) {
/* 259 */       if (container != null) {
/* 260 */         Module module = (Module)container.getSPI(Module.class);
/* 261 */         if (module != null) {
/* 262 */           List<BoundEndpoint> beList = module.getBoundEndpoints();
/* 263 */           for (BoundEndpoint be : beList) {
/* 264 */             WSEndpoint wsep = be.getEndpoint();
/* 265 */             if (wse.getPortName().equals(wsep.getPortName())) {
/* 266 */               rvalue = be.getAddress().toASCIIString();
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 272 */       if (rvalue == null) {
/* 273 */         rvalue = wse.getPortName().toString();
/*     */       }
/*     */     } else {
/*     */       
/* 277 */       WSService service = (WSService)map.get("SERVICE");
/* 278 */       if (service != null) {
/* 279 */         rvalue = service.getServiceName().toString();
/*     */       }
/*     */     } 
/*     */     
/* 283 */     return rvalue;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addModel(MessageInfo info, Map<Object, Object> map) {
/* 288 */     Object model = map.get("WSDL_MODEL");
/* 289 */     if (model != null) {
/* 290 */       info.getMap().put("WSDL_MODEL", model);
/*     */     }
/*     */   }
/*     */   
/*     */   private static String getServerName(WSEndpoint wse) {
/* 295 */     return "localhost";
/*     */   }
/*     */   
/*     */   private static String getEndpointURI(WSEndpoint wse) {
/* 299 */     return wse.getPort().getAddress().getURI().toASCIIString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void authorize(Packet request) {
/* 306 */     Subject s = (Subject)request.invocationProperties.get("CLIENT_SUBJECT");
/* 307 */     if (s == null) {
/* 308 */       s = Subject.getSubject(AccessController.getContext());
/*     */     }
/*     */     
/* 311 */     if (this.context != null)
/* 312 */       this.context.setSubject(s); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\wsit\PipeHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */