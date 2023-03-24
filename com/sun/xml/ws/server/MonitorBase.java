/*     */ package com.sun.xml.ws.server;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.EndpointAddress;
/*     */ import com.sun.xml.ws.api.config.management.policy.ManagedClientAssertion;
/*     */ import com.sun.xml.ws.api.config.management.policy.ManagedServiceAssertion;
/*     */ import com.sun.xml.ws.api.config.management.policy.ManagementAssertion;
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.client.Stub;
/*     */ import java.io.IOException;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.management.ObjectName;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import org.glassfish.external.amx.AMXGlassfish;
/*     */ import org.glassfish.gmbal.Description;
/*     */ import org.glassfish.gmbal.InheritedAttributes;
/*     */ import org.glassfish.gmbal.ManagedData;
/*     */ import org.glassfish.gmbal.ManagedObjectManager;
/*     */ import org.glassfish.gmbal.ManagedObjectManagerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MonitorBase
/*     */ {
/*  78 */   private static final Logger logger = Logger.getLogger("com.sun.xml.ws.monitoring");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ManagedObjectManager createManagedObjectManager(WSEndpoint endpoint) {
/* 103 */     String rootName = endpoint.getServiceName().getLocalPart() + "-" + endpoint.getPortName().getLocalPart();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     if (rootName.equals("-")) {
/* 109 */       rootName = "provider";
/*     */     }
/*     */ 
/*     */     
/* 113 */     String contextPath = getContextPath(endpoint);
/* 114 */     if (contextPath != null) {
/* 115 */       rootName = contextPath + "-" + rootName;
/*     */     }
/*     */     
/* 118 */     ManagedServiceAssertion assertion = ManagedServiceAssertion.getAssertion(endpoint);
/*     */     
/* 120 */     if (assertion != null) {
/* 121 */       String id = assertion.getId();
/* 122 */       if (id != null) {
/* 123 */         rootName = id;
/*     */       }
/* 125 */       if (assertion.monitoringAttribute() == ManagementAssertion.Setting.OFF) {
/* 126 */         return disabled("This endpoint", rootName);
/*     */       }
/*     */     } 
/*     */     
/* 130 */     if (endpointMonitoring.equals(ManagementAssertion.Setting.OFF)) {
/* 131 */       return disabled("Global endpoint", rootName);
/*     */     }
/* 133 */     return createMOMLoop(rootName, 0);
/*     */   }
/*     */   
/*     */   private String getContextPath(WSEndpoint endpoint) {
/*     */     try {
/* 138 */       Container container = endpoint.getContainer();
/* 139 */       Method getSPI = container.getClass().getDeclaredMethod("getSPI", new Class[] { Class.class });
/*     */       
/* 141 */       getSPI.setAccessible(true);
/* 142 */       Class<?> servletContextClass = Class.forName("javax.servlet.ServletContext");
/*     */       
/* 144 */       Object servletContext = getSPI.invoke(container, new Object[] { servletContextClass });
/*     */       
/* 146 */       if (servletContext != null) {
/* 147 */         Method getContextPath = servletContextClass.getDeclaredMethod("getContextPath", new Class[0]);
/* 148 */         getContextPath.setAccessible(true);
/* 149 */         return (String)getContextPath.invoke(servletContext, new Object[0]);
/*     */       } 
/* 151 */       return null;
/* 152 */     } catch (Throwable t) {
/* 153 */       logger.log(Level.FINEST, "getContextPath", t);
/*     */       
/* 155 */       return null;
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
/*     */   
/*     */   @NotNull
/*     */   public ManagedObjectManager createManagedObjectManager(Stub stub) {
/* 172 */     EndpointAddress ea = stub.requestContext.getEndpointAddress();
/* 173 */     if (ea == null) {
/* 174 */       return ManagedObjectManagerFactory.createNOOP();
/*     */     }
/*     */     
/* 177 */     String rootName = ea.toString();
/*     */     
/* 179 */     ManagedClientAssertion assertion = ManagedClientAssertion.getAssertion(stub.getPortInfo());
/*     */     
/* 181 */     if (assertion != null) {
/* 182 */       String id = assertion.getId();
/* 183 */       if (id != null) {
/* 184 */         rootName = id;
/*     */       }
/* 186 */       if (assertion.monitoringAttribute() == ManagementAssertion.Setting.OFF)
/* 187 */         return disabled("This client", rootName); 
/* 188 */       if (assertion.monitoringAttribute() == ManagementAssertion.Setting.ON && clientMonitoring != ManagementAssertion.Setting.OFF)
/*     */       {
/* 190 */         return createMOMLoop(rootName, 0);
/*     */       }
/*     */     } 
/*     */     
/* 194 */     if (clientMonitoring == ManagementAssertion.Setting.NOT_SET || clientMonitoring == ManagementAssertion.Setting.OFF)
/*     */     {
/*     */       
/* 197 */       return disabled("Global client", rootName);
/*     */     }
/* 199 */     return createMOMLoop(rootName, 0);
/*     */   }
/*     */   @NotNull
/*     */   private ManagedObjectManager disabled(String x, String rootName) {
/* 203 */     String msg = x + " monitoring disabled. " + rootName + " will not be monitored";
/* 204 */     logger.log(Level.CONFIG, msg);
/* 205 */     return ManagedObjectManagerFactory.createNOOP();
/*     */   }
/*     */   @NotNull
/*     */   private ManagedObjectManager createMOMLoop(String rootName, int unique) {
/* 209 */     boolean isFederated = (AMXGlassfish.getGlassfishVersion() != null);
/* 210 */     ManagedObjectManager mom = createMOM(isFederated);
/* 211 */     mom = initMOM(mom);
/* 212 */     mom = createRoot(mom, rootName, unique);
/* 213 */     return mom;
/*     */   }
/*     */   @NotNull
/*     */   private ManagedObjectManager createMOM(boolean isFederated) {
/*     */     try {
/* 218 */       return new RewritingMOM(isFederated ? ManagedObjectManagerFactory.createFederated(AMXGlassfish.DEFAULT.serverMon(AMXGlassfish.DEFAULT.dasName())) : ManagedObjectManagerFactory.createStandalone("com.sun.metro"));
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 223 */     catch (Throwable t) {
/* 224 */       if (isFederated) {
/* 225 */         logger.log(Level.CONFIG, "Problem while attempting to federate with GlassFish AMX monitoring.  Trying standalone.", t);
/* 226 */         return createMOM(false);
/*     */       } 
/* 228 */       logger.log(Level.WARNING, "Ignoring exception - starting up without monitoring", t);
/* 229 */       return ManagedObjectManagerFactory.createNOOP();
/*     */     } 
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   private ManagedObjectManager initMOM(ManagedObjectManager mom) {
/*     */     try {
/* 236 */       if (typelibDebug != -1) {
/* 237 */         mom.setTypelibDebug(typelibDebug);
/*     */       }
/* 239 */       if (registrationDebug.equals("FINE")) {
/* 240 */         mom.setRegistrationDebug(ManagedObjectManager.RegistrationDebugLevel.FINE);
/* 241 */       } else if (registrationDebug.equals("NORMAL")) {
/* 242 */         mom.setRegistrationDebug(ManagedObjectManager.RegistrationDebugLevel.NORMAL);
/*     */       } else {
/* 244 */         mom.setRegistrationDebug(ManagedObjectManager.RegistrationDebugLevel.NONE);
/*     */       } 
/*     */       
/* 247 */       mom.setRuntimeDebug(runtimeDebug);
/*     */ 
/*     */ 
/*     */       
/* 251 */       mom.suppressDuplicateRootReport(true);
/*     */       
/* 253 */       mom.stripPrefix(new String[] { "com.sun.xml.ws.server", "com.sun.xml.ws.rx.rm.runtime.sequence" });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 258 */       mom.addAnnotation(WebServiceFeature.class, (Annotation)DummyWebServiceFeature.class.getAnnotation(ManagedData.class));
/* 259 */       mom.addAnnotation(WebServiceFeature.class, (Annotation)DummyWebServiceFeature.class.getAnnotation(Description.class));
/* 260 */       mom.addAnnotation(WebServiceFeature.class, (Annotation)DummyWebServiceFeature.class.getAnnotation(InheritedAttributes.class));
/*     */ 
/*     */ 
/*     */       
/* 264 */       mom.suspendJMXRegistration();
/*     */     }
/* 266 */     catch (Throwable t) {
/*     */       try {
/* 268 */         mom.close();
/* 269 */       } catch (IOException e) {
/* 270 */         logger.log(Level.CONFIG, "Ignoring exception caught when closing unused ManagedObjectManager", e);
/*     */       } 
/* 272 */       logger.log(Level.WARNING, "Ignoring exception - starting up without monitoring", t);
/* 273 */       return ManagedObjectManagerFactory.createNOOP();
/*     */     } 
/* 275 */     return mom;
/*     */   }
/*     */   
/*     */   private ManagedObjectManager createRoot(ManagedObjectManager mom, String rootName, int unique) {
/* 279 */     String name = rootName + ((unique == 0) ? "" : ("-" + String.valueOf(unique)));
/*     */     try {
/* 281 */       Object ignored = mom.createRoot(this, name);
/* 282 */       if (ignored != null) {
/* 283 */         ObjectName ignoredName = mom.getObjectName(mom.getRoot());
/*     */         
/* 285 */         if (ignoredName != null) {
/* 286 */           logger.log(Level.INFO, "Metro monitoring rootname successfully set to: {0}", ignoredName);
/*     */         }
/* 288 */         return mom;
/*     */       } 
/*     */       try {
/* 291 */         mom.close();
/* 292 */       } catch (IOException e) {
/* 293 */         logger.log(Level.CONFIG, "Ignoring exception caught when closing unused ManagedObjectManager", e);
/*     */       } 
/* 295 */       String basemsg = "Duplicate Metro monitoring rootname: " + name + " : ";
/* 296 */       if (unique > maxUniqueEndpointRootNameRetries) {
/* 297 */         String str = basemsg + "Giving up.";
/* 298 */         logger.log(Level.INFO, str);
/* 299 */         return ManagedObjectManagerFactory.createNOOP();
/*     */       } 
/* 301 */       String msg = basemsg + "Will try to make unique";
/* 302 */       logger.log(Level.CONFIG, msg);
/* 303 */       return createMOMLoop(rootName, ++unique);
/* 304 */     } catch (Throwable t) {
/* 305 */       logger.log(Level.WARNING, "Error while creating monitoring root with name: " + rootName, t);
/* 306 */       return ManagedObjectManagerFactory.createNOOP();
/*     */     } 
/*     */   }
/*     */   
/* 310 */   private static ManagementAssertion.Setting clientMonitoring = ManagementAssertion.Setting.NOT_SET;
/* 311 */   private static ManagementAssertion.Setting endpointMonitoring = ManagementAssertion.Setting.NOT_SET;
/* 312 */   private static int typelibDebug = -1;
/* 313 */   private static String registrationDebug = "NONE";
/*     */   private static boolean runtimeDebug = false;
/* 315 */   private static int maxUniqueEndpointRootNameRetries = 100;
/*     */   private static final String monitorProperty = "com.sun.xml.ws.monitoring.";
/*     */   
/*     */   private static ManagementAssertion.Setting propertyToSetting(String propName) {
/* 319 */     String s = System.getProperty(propName);
/* 320 */     if (s == null) {
/* 321 */       return ManagementAssertion.Setting.NOT_SET;
/*     */     }
/* 323 */     s = s.toLowerCase();
/* 324 */     if (s.equals("false") || s.equals("off"))
/* 325 */       return ManagementAssertion.Setting.OFF; 
/* 326 */     if (s.equals("true") || s.equals("on")) {
/* 327 */       return ManagementAssertion.Setting.ON;
/*     */     }
/* 329 */     return ManagementAssertion.Setting.NOT_SET;
/*     */   }
/*     */   
/*     */   static {
/*     */     try {
/* 334 */       endpointMonitoring = propertyToSetting("com.sun.xml.ws.monitoring.endpoint");
/*     */       
/* 336 */       clientMonitoring = propertyToSetting("com.sun.xml.ws.monitoring.client");
/*     */       
/* 338 */       Integer i = Integer.getInteger("com.sun.xml.ws.monitoring.typelibDebug");
/* 339 */       if (i != null) {
/* 340 */         typelibDebug = i.intValue();
/*     */       }
/*     */       
/* 343 */       String s = System.getProperty("com.sun.xml.ws.monitoring.registrationDebug");
/* 344 */       if (s != null) {
/* 345 */         registrationDebug = s.toUpperCase();
/*     */       }
/*     */       
/* 348 */       s = System.getProperty("com.sun.xml.ws.monitoring.runtimeDebug");
/* 349 */       if (s != null && s.toLowerCase().equals("true")) {
/* 350 */         runtimeDebug = true;
/*     */       }
/*     */       
/* 353 */       i = Integer.getInteger("com.sun.xml.ws.monitoring.maxUniqueEndpointRootNameRetries");
/* 354 */       if (i != null) {
/* 355 */         maxUniqueEndpointRootNameRetries = i.intValue();
/*     */       }
/* 357 */     } catch (Exception e) {
/* 358 */       logger.log(Level.WARNING, "Error while reading monitoring properties", e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\MonitorBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */