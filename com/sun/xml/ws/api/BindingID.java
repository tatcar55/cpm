/*     */ package com.sun.xml.ws.api;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.binding.BindingImpl;
/*     */ import com.sun.xml.ws.binding.WebServiceFeatureList;
/*     */ import com.sun.xml.ws.encoding.SOAPBindingCodec;
/*     */ import com.sun.xml.ws.encoding.XMLHTTPBindingCodec;
/*     */ import com.sun.xml.ws.util.ServiceFinder;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLDecoder;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.ws.BindingType;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.soap.MTOMFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BindingID
/*     */ {
/*     */   @NotNull
/*     */   public final WSBinding createBinding() {
/* 114 */     return (WSBinding)BindingImpl.create(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getTransport() {
/* 126 */     return "http://schemas.xmlsoap.org/soap/http";
/*     */   }
/*     */   @NotNull
/*     */   public final WSBinding createBinding(WebServiceFeature... features) {
/* 130 */     return (WSBinding)BindingImpl.create(this, features);
/*     */   }
/*     */   @NotNull
/*     */   public final WSBinding createBinding(WSFeatureList features) {
/* 134 */     return createBinding(features.toArray());
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
/*     */   public abstract SOAPVersion getSOAPVersion();
/*     */ 
/*     */ 
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
/*     */   public abstract Codec createEncoder(@NotNull WSBinding paramWSBinding);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String toString();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebServiceFeatureList createBuiltinFeatureList() {
/* 186 */     return new WebServiceFeatureList();
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
/*     */   public boolean canGenerateWSDL() {
/* 199 */     return false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getParameter(String parameterName, String defaultValue) {
/* 229 */     return defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 237 */     if (!(obj instanceof BindingID))
/* 238 */       return false; 
/* 239 */     return toString().equals(obj.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 244 */     return toString().hashCode();
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
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static BindingID parse(String lexical) {
/* 262 */     if (lexical.equals(XML_HTTP.toString()))
/* 263 */       return XML_HTTP; 
/* 264 */     if (lexical.equals(REST_HTTP.toString()))
/* 265 */       return REST_HTTP; 
/* 266 */     if (belongsTo(lexical, SOAP11_HTTP.toString()))
/* 267 */       return customize(lexical, SOAP11_HTTP); 
/* 268 */     if (belongsTo(lexical, SOAP12_HTTP.toString()))
/* 269 */       return customize(lexical, SOAP12_HTTP); 
/* 270 */     if (belongsTo(lexical, "http://java.sun.com/xml/ns/jaxws/2003/05/soap/bindings/HTTP/")) {
/* 271 */       return customize(lexical, X_SOAP12_HTTP);
/*     */     }
/*     */     
/* 274 */     for (BindingIDFactory f : ServiceFinder.find(BindingIDFactory.class)) {
/* 275 */       BindingID r = f.parse(lexical);
/* 276 */       if (r != null) {
/* 277 */         return r;
/*     */       }
/*     */     } 
/*     */     
/* 281 */     throw new WebServiceException("Wrong binding ID: " + lexical);
/*     */   }
/*     */   
/*     */   private static boolean belongsTo(String lexical, String id) {
/* 285 */     return (lexical.equals(id) || lexical.startsWith(id + '?'));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static SOAPHTTPImpl customize(String lexical, SOAPHTTPImpl base) {
/* 292 */     if (lexical.equals(base.toString())) {
/* 293 */       return base;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 299 */     SOAPHTTPImpl r = new SOAPHTTPImpl(base.getSOAPVersion(), lexical, base.canGenerateWSDL());
/*     */     
/*     */     try {
/* 302 */       if (lexical.indexOf('?') == -1) {
/* 303 */         return r;
/*     */       }
/* 305 */       String query = URLDecoder.decode(lexical.substring(lexical.indexOf('?') + 1), "UTF-8");
/* 306 */       for (String token : query.split("&")) {
/* 307 */         int idx = token.indexOf('=');
/* 308 */         if (idx < 0)
/* 309 */           throw new WebServiceException("Malformed binding ID (no '=' in " + token + ")"); 
/* 310 */         r.parameters.put(token.substring(0, idx), token.substring(idx + 1));
/*     */       } 
/* 312 */     } catch (UnsupportedEncodingException e) {
/* 313 */       throw new AssertionError(e);
/*     */     } 
/*     */     
/* 316 */     return r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static BindingID parse(Class<?> implClass) {
/* 328 */     BindingType bindingType = implClass.<BindingType>getAnnotation(BindingType.class);
/* 329 */     if (bindingType != null) {
/* 330 */       String bindingId = bindingType.value();
/* 331 */       if (bindingId.length() > 0) {
/* 332 */         return parse(bindingId);
/*     */       }
/*     */     } 
/* 335 */     return SOAP11_HTTP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 342 */   public static final SOAPHTTPImpl X_SOAP12_HTTP = new SOAPHTTPImpl(SOAPVersion.SOAP_12, "http://java.sun.com/xml/ns/jaxws/2003/05/soap/bindings/HTTP/", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 348 */   public static final SOAPHTTPImpl SOAP12_HTTP = new SOAPHTTPImpl(SOAPVersion.SOAP_12, "http://www.w3.org/2003/05/soap/bindings/HTTP/", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 353 */   public static final SOAPHTTPImpl SOAP11_HTTP = new SOAPHTTPImpl(SOAPVersion.SOAP_11, "http://schemas.xmlsoap.org/wsdl/soap/http", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 359 */   public static final SOAPHTTPImpl SOAP12_HTTP_MTOM = new SOAPHTTPImpl(SOAPVersion.SOAP_12, "http://www.w3.org/2003/05/soap/bindings/HTTP/?mtom=true", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 364 */   public static final SOAPHTTPImpl SOAP11_HTTP_MTOM = new SOAPHTTPImpl(SOAPVersion.SOAP_11, "http://schemas.xmlsoap.org/wsdl/soap/http?mtom=true", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 371 */   public static final BindingID XML_HTTP = new Impl(SOAPVersion.SOAP_11, "http://www.w3.org/2004/08/wsdl/http", false)
/*     */     {
/*     */       public Codec createEncoder(WSBinding binding) {
/* 374 */         return (Codec)new XMLHTTPBindingCodec(binding.getFeatures());
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 381 */   private static final BindingID REST_HTTP = new Impl(SOAPVersion.SOAP_11, "http://jax-ws.dev.java.net/rest", true)
/*     */     {
/*     */       public Codec createEncoder(WSBinding binding) {
/* 384 */         return (Codec)new XMLHTTPBindingCodec(binding.getFeatures());
/*     */       }
/*     */     };
/*     */   
/*     */   private static abstract class Impl extends BindingID {
/*     */     final SOAPVersion version;
/*     */     private final String lexical;
/*     */     private final boolean canGenerateWSDL;
/*     */     
/*     */     public Impl(SOAPVersion version, String lexical, boolean canGenerateWSDL) {
/* 394 */       this.version = version;
/* 395 */       this.lexical = lexical;
/* 396 */       this.canGenerateWSDL = canGenerateWSDL;
/*     */     }
/*     */ 
/*     */     
/*     */     public SOAPVersion getSOAPVersion() {
/* 401 */       return this.version;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 406 */       return this.lexical;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean canGenerateWSDL() {
/* 412 */       return this.canGenerateWSDL;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class SOAPHTTPImpl
/*     */     extends Impl
/*     */     implements Cloneable
/*     */   {
/* 420 */     Map<String, String> parameters = new HashMap<String, String>();
/*     */     
/*     */     static final String MTOM_PARAM = "mtom";
/*     */     
/*     */     public SOAPHTTPImpl(SOAPVersion version, String lexical, boolean canGenerateWSDL) {
/* 425 */       super(version, lexical, canGenerateWSDL);
/*     */     }
/*     */ 
/*     */     
/*     */     public SOAPHTTPImpl(SOAPVersion version, String lexical, boolean canGenerateWSDL, boolean mtomEnabled) {
/* 430 */       this(version, lexical, canGenerateWSDL);
/* 431 */       String mtomStr = mtomEnabled ? "true" : "false";
/* 432 */       this.parameters.put("mtom", mtomStr);
/*     */     }
/*     */     @NotNull
/*     */     public Codec createEncoder(WSBinding binding) {
/* 436 */       return (Codec)new SOAPBindingCodec(binding.getFeatures());
/*     */     }
/*     */     
/*     */     private Boolean isMTOMEnabled() {
/* 440 */       String mtom = this.parameters.get("mtom");
/* 441 */       return (mtom == null) ? null : Boolean.valueOf(mtom);
/*     */     }
/*     */ 
/*     */     
/*     */     public WebServiceFeatureList createBuiltinFeatureList() {
/* 446 */       WebServiceFeatureList r = super.createBuiltinFeatureList();
/* 447 */       Boolean mtom = isMTOMEnabled();
/* 448 */       if (mtom != null)
/* 449 */         r.add(new MTOMFeature(mtom.booleanValue())); 
/* 450 */       return r;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getParameter(String parameterName, String defaultValue) {
/* 455 */       if (this.parameters.get(parameterName) == null)
/* 456 */         return super.getParameter(parameterName, defaultValue); 
/* 457 */       return this.parameters.get(parameterName);
/*     */     }
/*     */ 
/*     */     
/*     */     public SOAPHTTPImpl clone() throws CloneNotSupportedException {
/* 462 */       return (SOAPHTTPImpl)super.clone();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\BindingID.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */