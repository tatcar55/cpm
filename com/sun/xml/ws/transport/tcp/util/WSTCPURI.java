/*     */ package com.sun.xml.ws.transport.tcp.util;
/*     */ 
/*     */ import com.sun.xml.ws.transport.tcp.client.WSConnectionManager;
/*     */ import com.sun.xml.ws.transport.tcp.connectioncache.spi.transport.Connection;
/*     */ import com.sun.xml.ws.transport.tcp.connectioncache.spi.transport.ContactInfo;
/*     */ import com.sun.xml.ws.transport.tcp.resources.MessagesMessages;
/*     */ import com.sun.xml.ws.transport.tcp.servicechannel.ServiceChannelException;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.annotation.adapters.XmlAdapter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class WSTCPURI
/*     */   implements ContactInfo<ConnectionSession>
/*     */ {
/*     */   public String host;
/*     */   public int port;
/*     */   public String path;
/*  63 */   public int customPort = -1;
/*     */ 
/*     */   
/*     */   private String uri2string;
/*     */ 
/*     */   
/*     */   private Map<String, String> params;
/*     */ 
/*     */ 
/*     */   
/*     */   private WSTCPURI(String host, int port, String path, Map<String, String> params, String uri2string) {
/*  74 */     this.host = host;
/*  75 */     this.port = port;
/*  76 */     this.path = path;
/*  77 */     this.params = params;
/*  78 */     this.uri2string = uri2string;
/*     */   }
/*     */   
/*     */   public String getParameter(String name) {
/*  82 */     if (this.params != null) {
/*  83 */       return this.params.get(name);
/*     */     }
/*     */     
/*  86 */     return null;
/*     */   }
/*     */   
/*     */   public static WSTCPURI parse(String uri) {
/*     */     try {
/*  91 */       return parse(new URI(uri));
/*  92 */     } catch (URISyntaxException ex) {
/*  93 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static WSTCPURI parse(URI uri) {
/*  98 */     String path = uri.getPath();
/*  99 */     String query = uri.getQuery();
/* 100 */     Map<String, String> params = null;
/*     */     
/* 102 */     if (query != null && query.length() > 0) {
/* 103 */       String[] paramsStr = query.split(";");
/* 104 */       params = new HashMap<String, String>(paramsStr.length);
/* 105 */       for (String paramStr : paramsStr) {
/* 106 */         if (paramStr.length() > 0) {
/* 107 */           String[] paramAsgn = paramStr.split("=");
/* 108 */           if (paramAsgn != null && paramAsgn.length == 2 && paramAsgn[0].length() > 0 && paramAsgn[1].length() > 0) {
/* 109 */             params.put(paramAsgn[0], paramAsgn[1]);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 115 */     return new WSTCPURI(uri.getHost(), uri.getPort(), path, params, uri.toASCIIString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCustomPort() {
/* 123 */     return this.customPort;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCustomPort(int customPort) {
/* 131 */     this.customPort = customPort;
/*     */   }
/*     */   
/*     */   public int getEffectivePort() {
/* 135 */     if (this.customPort == -1) {
/* 136 */       return this.port;
/*     */     }
/*     */     
/* 139 */     return this.customPort;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 144 */     return this.uri2string;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 149 */     if (o instanceof WSTCPURI) {
/* 150 */       WSTCPURI toCmp = (WSTCPURI)o;
/* 151 */       boolean basicResult = (this.port == toCmp.port && this.host.equals(toCmp.host));
/* 152 */       if (this.customPort == -1 && toCmp.customPort == -1) {
/* 153 */         return basicResult;
/*     */       }
/* 155 */       return (basicResult && this.customPort == toCmp.customPort);
/*     */     } 
/*     */ 
/*     */     
/* 159 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 164 */     return this.host.hashCode() + (this.port << 2) + this.customPort;
/*     */   }
/*     */   
/*     */   public ConnectionSession createConnection() throws IOException {
/*     */     try {
/* 169 */       return WSConnectionManager.getInstance().createConnectionSession(this);
/* 170 */     } catch (VersionMismatchException e) {
/* 171 */       throw new IOException(e.getMessage());
/* 172 */     } catch (ServiceChannelException e) {
/* 173 */       throw new IOException(MessagesMessages.WSTCP_0024_SERVICE_CHANNEL_EXCEPTION(e.getFaultInfo().getErrorCode(), e.getMessage()));
/*     */     } 
/*     */   }
/*     */   
/*     */   public WSTCPURI() {}
/*     */   
/*     */   public static final class WSTCPURI2StringJAXBAdapter
/*     */     extends XmlAdapter<String, WSTCPURI>
/*     */   {
/*     */     public String marshal(WSTCPURI tcpURI) throws Exception {
/* 183 */       return tcpURI.toString();
/*     */     }
/*     */     
/*     */     public WSTCPURI unmarshal(String uri) throws Exception {
/* 187 */       return WSTCPURI.parse(uri);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tc\\util\WSTCPURI.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */