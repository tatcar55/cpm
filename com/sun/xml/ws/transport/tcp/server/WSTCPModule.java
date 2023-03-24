/*    */ package com.sun.xml.ws.transport.tcp.server;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.xml.ws.api.BindingID;
/*    */ import com.sun.xml.ws.api.WSBinding;
/*    */ import com.sun.xml.ws.api.server.WSEndpoint;
/*    */ import com.sun.xml.ws.transport.tcp.resources.MessagesMessages;
/*    */ import com.sun.xml.ws.transport.tcp.servicechannel.ServiceChannelWSImpl;
/*    */ import java.util.List;
/*    */ import java.util.logging.Logger;
/*    */ import javax.xml.namespace.QName;
/*    */ import org.xml.sax.EntityResolver;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WSTCPModule
/*    */ {
/*    */   private static volatile WSTCPModule instance;
/* 62 */   protected static final Logger logger = Logger.getLogger("com.sun.metro.transport.tcp.server");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public static WSTCPModule getInstance() {
/* 70 */     if (instance == null) {
/* 71 */       throw new IllegalStateException(MessagesMessages.WSTCP_0007_TRANSPORT_MODULE_NOT_INITIALIZED());
/*    */     }
/*    */     
/* 74 */     return instance;
/*    */   }
/*    */   
/*    */   protected static void setInstance(WSTCPModule instance) {
/* 78 */     WSTCPModule.instance = instance;
/*    */   }
/*    */   
/*    */   public WSEndpoint<ServiceChannelWSImpl> createServiceChannelEndpoint() {
/* 82 */     QName serviceName = WSEndpoint.getDefaultServiceName(ServiceChannelWSImpl.class);
/* 83 */     QName portName = WSEndpoint.getDefaultPortName(serviceName, ServiceChannelWSImpl.class);
/* 84 */     BindingID bindingId = BindingID.parse(ServiceChannelWSImpl.class);
/* 85 */     WSBinding binding = bindingId.createBinding();
/*    */     
/* 87 */     return WSEndpoint.create(ServiceChannelWSImpl.class, true, null, serviceName, portName, null, binding, null, null, (EntityResolver)null, true);
/*    */   }
/*    */   
/*    */   public abstract void register(@NotNull String paramString, @NotNull List<TCPAdapter> paramList);
/*    */   
/*    */   public abstract void free(@NotNull String paramString, @NotNull List<TCPAdapter> paramList);
/*    */   
/*    */   public abstract int getPort();
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\WSTCPModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */