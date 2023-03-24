/*     */ package com.sun.xml.ws.spi;
/*     */ 
/*     */ import com.sun.xml.ws.api.BindingID;
/*     */ import com.sun.xml.ws.api.WSService;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.client.WSServiceDelegate;
/*     */ import com.sun.xml.ws.developer.MemberSubmissionEndpointReference;
/*     */ import com.sun.xml.ws.resources.ProviderApiMessages;
/*     */ import com.sun.xml.ws.transport.http.server.EndpointImpl;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.Endpoint;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import javax.xml.ws.Service;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.spi.Invoker;
/*     */ import javax.xml.ws.spi.Provider;
/*     */ import javax.xml.ws.spi.ServiceDelegate;
/*     */ import javax.xml.ws.wsaddressing.W3CEndpointReference;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProviderImpl
/*     */   extends Provider
/*     */ {
/*  96 */   private static final JAXBContext eprjc = getEPRJaxbContext();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   public static final ProviderImpl INSTANCE = new ProviderImpl();
/*     */ 
/*     */   
/*     */   public Endpoint createEndpoint(String bindingId, Object implementor) {
/* 105 */     return (Endpoint)new EndpointImpl((bindingId != null) ? BindingID.parse(bindingId) : BindingID.parse(implementor.getClass()), implementor, new WebServiceFeature[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServiceDelegate createServiceDelegate(URL wsdlDocumentLocation, QName serviceName, Class serviceClass) {
/* 112 */     return (ServiceDelegate)new WSServiceDelegate(wsdlDocumentLocation, serviceName, serviceClass, new WebServiceFeature[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public ServiceDelegate createServiceDelegate(URL wsdlDocumentLocation, QName serviceName, Class serviceClass, WebServiceFeature... features) {
/* 117 */     for (WebServiceFeature feature : features) {
/* 118 */       if (!(feature instanceof com.sun.xml.ws.api.ServiceSharedFeatureMarker))
/* 119 */         throw new WebServiceException("Doesn't support any Service specific features"); 
/*     */     } 
/* 121 */     return (ServiceDelegate)new WSServiceDelegate(wsdlDocumentLocation, serviceName, serviceClass, features);
/*     */   }
/*     */   
/*     */   public ServiceDelegate createServiceDelegate(Source wsdlSource, QName serviceName, Class serviceClass) {
/* 125 */     return (ServiceDelegate)new WSServiceDelegate(wsdlSource, serviceName, serviceClass, new WebServiceFeature[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Endpoint createAndPublishEndpoint(String address, Object implementor) {
/* 131 */     EndpointImpl endpointImpl = new EndpointImpl(BindingID.parse(implementor.getClass()), implementor, new WebServiceFeature[0]);
/*     */ 
/*     */     
/* 134 */     endpointImpl.publish(address);
/* 135 */     return (Endpoint)endpointImpl;
/*     */   }
/*     */   
/*     */   public Endpoint createEndpoint(String bindingId, Object implementor, WebServiceFeature... features) {
/* 139 */     return (Endpoint)new EndpointImpl((bindingId != null) ? BindingID.parse(bindingId) : BindingID.parse(implementor.getClass()), implementor, features);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Endpoint createAndPublishEndpoint(String address, Object implementor, WebServiceFeature... features) {
/* 145 */     EndpointImpl endpointImpl = new EndpointImpl(BindingID.parse(implementor.getClass()), implementor, features);
/*     */     
/* 147 */     endpointImpl.publish(address);
/* 148 */     return (Endpoint)endpointImpl;
/*     */   }
/*     */   
/*     */   public Endpoint createEndpoint(String bindingId, Class implementorClass, Invoker invoker, WebServiceFeature... features) {
/* 152 */     return (Endpoint)new EndpointImpl((bindingId != null) ? BindingID.parse(bindingId) : BindingID.parse(implementorClass), implementorClass, invoker, features);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EndpointReference readEndpointReference(final Source eprInfoset) {
/* 161 */     return AccessController.<EndpointReference>doPrivileged(new PrivilegedAction<EndpointReference>() {
/*     */           public EndpointReference run() {
/*     */             try {
/* 164 */               Unmarshaller unmarshaller = ProviderImpl.eprjc.createUnmarshaller();
/* 165 */               return (EndpointReference)unmarshaller.unmarshal(eprInfoset);
/* 166 */             } catch (JAXBException e) {
/* 167 */               throw new WebServiceException("Error creating Marshaller or marshalling.", e);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getPort(EndpointReference endpointReference, Class<T> clazz, WebServiceFeature... webServiceFeatures) {
/*     */     WSService service;
/* 179 */     if (endpointReference == null)
/* 180 */       throw new WebServiceException(ProviderApiMessages.NULL_EPR()); 
/* 181 */     WSEndpointReference wsepr = new WSEndpointReference(endpointReference);
/* 182 */     WSEndpointReference.Metadata metadata = wsepr.getMetaData();
/*     */     
/* 184 */     if (metadata.getWsdlSource() != null) {
/* 185 */       service = (WSService)createServiceDelegate(metadata.getWsdlSource(), metadata.getServiceName(), Service.class);
/*     */     } else {
/* 187 */       throw new WebServiceException("WSDL metadata is missing in EPR");
/* 188 */     }  return (T)service.getPort(wsepr, clazz, webServiceFeatures);
/*     */   }
/*     */   
/*     */   public W3CEndpointReference createW3CEndpointReference(String address, QName serviceName, QName portName, List<Element> metadata, String wsdlDocumentLocation, List<Element> referenceParameters) {
/* 192 */     return createW3CEndpointReference(address, null, serviceName, portName, metadata, wsdlDocumentLocation, referenceParameters, null, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public W3CEndpointReference createW3CEndpointReference(String address, QName interfaceName, QName serviceName, QName portName, List<Element> metadata, String wsdlDocumentLocation, List<Element> referenceParameters, List<Element> elements, Map<QName, String> attributes) {
/*     */     // Byte code:
/*     */     //   0: invokestatic getInstance : ()Lcom/sun/xml/ws/api/server/ContainerResolver;
/*     */     //   3: invokevirtual getContainer : ()Lcom/sun/xml/ws/api/server/Container;
/*     */     //   6: astore #10
/*     */     //   8: aload_1
/*     */     //   9: ifnonnull -> 159
/*     */     //   12: aload_3
/*     */     //   13: ifnull -> 21
/*     */     //   16: aload #4
/*     */     //   18: ifnonnull -> 32
/*     */     //   21: new java/lang/IllegalStateException
/*     */     //   24: dup
/*     */     //   25: invokestatic NULL_ADDRESS_SERVICE_ENDPOINT : ()Ljava/lang/String;
/*     */     //   28: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   31: athrow
/*     */     //   32: aload #10
/*     */     //   34: ldc_w com/sun/xml/ws/api/server/Module
/*     */     //   37: invokevirtual getSPI : (Ljava/lang/Class;)Ljava/lang/Object;
/*     */     //   40: checkcast com/sun/xml/ws/api/server/Module
/*     */     //   43: astore #11
/*     */     //   45: aload #11
/*     */     //   47: ifnull -> 144
/*     */     //   50: aload #11
/*     */     //   52: invokevirtual getBoundEndpoints : ()Ljava/util/List;
/*     */     //   55: astore #12
/*     */     //   57: aload #12
/*     */     //   59: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   64: astore #13
/*     */     //   66: aload #13
/*     */     //   68: invokeinterface hasNext : ()Z
/*     */     //   73: ifeq -> 144
/*     */     //   76: aload #13
/*     */     //   78: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   83: checkcast com/sun/xml/ws/api/server/BoundEndpoint
/*     */     //   86: astore #14
/*     */     //   88: aload #14
/*     */     //   90: invokeinterface getEndpoint : ()Lcom/sun/xml/ws/api/server/WSEndpoint;
/*     */     //   95: astore #15
/*     */     //   97: aload #15
/*     */     //   99: invokevirtual getServiceName : ()Ljavax/xml/namespace/QName;
/*     */     //   102: aload_3
/*     */     //   103: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   106: ifeq -> 141
/*     */     //   109: aload #15
/*     */     //   111: invokevirtual getPortName : ()Ljavax/xml/namespace/QName;
/*     */     //   114: aload #4
/*     */     //   116: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   119: ifeq -> 141
/*     */     //   122: aload #14
/*     */     //   124: invokeinterface getAddress : ()Ljava/net/URI;
/*     */     //   129: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   132: astore_1
/*     */     //   133: goto -> 144
/*     */     //   136: astore #16
/*     */     //   138: goto -> 144
/*     */     //   141: goto -> 66
/*     */     //   144: aload_1
/*     */     //   145: ifnonnull -> 159
/*     */     //   148: new java/lang/IllegalStateException
/*     */     //   151: dup
/*     */     //   152: invokestatic NULL_ADDRESS : ()Ljava/lang/String;
/*     */     //   155: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   158: athrow
/*     */     //   159: aload_3
/*     */     //   160: ifnonnull -> 179
/*     */     //   163: aload #4
/*     */     //   165: ifnull -> 179
/*     */     //   168: new java/lang/IllegalStateException
/*     */     //   171: dup
/*     */     //   172: invokestatic NULL_SERVICE : ()Ljava/lang/String;
/*     */     //   175: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   178: athrow
/*     */     //   179: aconst_null
/*     */     //   180: astore #11
/*     */     //   182: aload #6
/*     */     //   184: ifnull -> 350
/*     */     //   187: invokestatic createDefaultCatalogResolver : ()Lorg/xml/sax/EntityResolver;
/*     */     //   190: astore #12
/*     */     //   192: new java/net/URL
/*     */     //   195: dup
/*     */     //   196: aload #6
/*     */     //   198: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   201: astore #13
/*     */     //   203: aload #13
/*     */     //   205: new javax/xml/transform/stream/StreamSource
/*     */     //   208: dup
/*     */     //   209: aload #13
/*     */     //   211: invokevirtual toExternalForm : ()Ljava/lang/String;
/*     */     //   214: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   217: aload #12
/*     */     //   219: iconst_1
/*     */     //   220: aload #10
/*     */     //   222: ldc_w com/sun/xml/ws/api/wsdl/parser/WSDLParserExtension
/*     */     //   225: invokestatic find : (Ljava/lang/Class;)Lcom/sun/xml/ws/util/ServiceFinder;
/*     */     //   228: invokevirtual toArray : ()[Ljava/lang/Object;
/*     */     //   231: checkcast [Lcom/sun/xml/ws/api/wsdl/parser/WSDLParserExtension;
/*     */     //   234: invokestatic parse : (Ljava/net/URL;Ljavax/xml/transform/Source;Lorg/xml/sax/EntityResolver;ZLcom/sun/xml/ws/api/server/Container;[Lcom/sun/xml/ws/api/wsdl/parser/WSDLParserExtension;)Lcom/sun/xml/ws/model/wsdl/WSDLModelImpl;
/*     */     //   237: astore #14
/*     */     //   239: aload_3
/*     */     //   240: ifnull -> 316
/*     */     //   243: aload #14
/*     */     //   245: aload_3
/*     */     //   246: invokevirtual getService : (Ljavax/xml/namespace/QName;)Lcom/sun/xml/ws/model/wsdl/WSDLServiceImpl;
/*     */     //   249: astore #15
/*     */     //   251: aload #15
/*     */     //   253: ifnonnull -> 270
/*     */     //   256: new java/lang/IllegalStateException
/*     */     //   259: dup
/*     */     //   260: aload_3
/*     */     //   261: aload #6
/*     */     //   263: invokestatic NOTFOUND_SERVICE_IN_WSDL : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   266: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   269: athrow
/*     */     //   270: aload #4
/*     */     //   272: ifnull -> 307
/*     */     //   275: aload #15
/*     */     //   277: aload #4
/*     */     //   279: invokeinterface get : (Ljavax/xml/namespace/QName;)Lcom/sun/xml/ws/api/model/wsdl/WSDLPort;
/*     */     //   284: astore #16
/*     */     //   286: aload #16
/*     */     //   288: ifnonnull -> 307
/*     */     //   291: new java/lang/IllegalStateException
/*     */     //   294: dup
/*     */     //   295: aload #4
/*     */     //   297: aload_3
/*     */     //   298: aload #6
/*     */     //   300: invokestatic NOTFOUND_PORT_IN_WSDL : (Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   303: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   306: athrow
/*     */     //   307: aload_3
/*     */     //   308: invokevirtual getNamespaceURI : ()Ljava/lang/String;
/*     */     //   311: astore #11
/*     */     //   313: goto -> 330
/*     */     //   316: aload #14
/*     */     //   318: invokevirtual getFirstServiceName : ()Ljavax/xml/namespace/QName;
/*     */     //   321: astore #15
/*     */     //   323: aload #15
/*     */     //   325: invokevirtual getNamespaceURI : ()Ljava/lang/String;
/*     */     //   328: astore #11
/*     */     //   330: goto -> 350
/*     */     //   333: astore #12
/*     */     //   335: new java/lang/IllegalStateException
/*     */     //   338: dup
/*     */     //   339: aload #6
/*     */     //   341: invokestatic ERROR_WSDL : (Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   344: aload #12
/*     */     //   346: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
/*     */     //   349: athrow
/*     */     //   350: aload #5
/*     */     //   352: ifnull -> 368
/*     */     //   355: aload #5
/*     */     //   357: invokeinterface size : ()I
/*     */     //   362: ifne -> 368
/*     */     //   365: aconst_null
/*     */     //   366: astore #5
/*     */     //   368: new com/sun/xml/ws/api/addressing/WSEndpointReference
/*     */     //   371: dup
/*     */     //   372: ldc_w javax/xml/ws/wsaddressing/W3CEndpointReference
/*     */     //   375: invokestatic fromSpecClass : (Ljava/lang/Class;)Lcom/sun/xml/ws/api/addressing/AddressingVersion;
/*     */     //   378: aload_1
/*     */     //   379: aload_3
/*     */     //   380: aload #4
/*     */     //   382: aload_2
/*     */     //   383: aload #5
/*     */     //   385: aload #6
/*     */     //   387: aload #11
/*     */     //   389: aload #7
/*     */     //   391: aload #8
/*     */     //   393: aload #9
/*     */     //   395: invokespecial <init> : (Lcom/sun/xml/ws/api/addressing/AddressingVersion;Ljava/lang/String;Ljavax/xml/namespace/QName;Ljavax/xml/namespace/QName;Ljavax/xml/namespace/QName;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/util/List;Ljava/util/Map;)V
/*     */     //   398: ldc_w javax/xml/ws/wsaddressing/W3CEndpointReference
/*     */     //   401: invokevirtual toSpec : (Ljava/lang/Class;)Ljavax/xml/ws/EndpointReference;
/*     */     //   404: checkcast javax/xml/ws/wsaddressing/W3CEndpointReference
/*     */     //   407: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #198	-> 0
/*     */     //   #199	-> 8
/*     */     //   #200	-> 12
/*     */     //   #201	-> 21
/*     */     //   #204	-> 32
/*     */     //   #205	-> 45
/*     */     //   #206	-> 50
/*     */     //   #207	-> 57
/*     */     //   #208	-> 88
/*     */     //   #209	-> 97
/*     */     //   #211	-> 122
/*     */     //   #215	-> 133
/*     */     //   #212	-> 136
/*     */     //   #216	-> 138
/*     */     //   #218	-> 141
/*     */     //   #221	-> 144
/*     */     //   #222	-> 148
/*     */     //   #225	-> 159
/*     */     //   #226	-> 168
/*     */     //   #229	-> 179
/*     */     //   #230	-> 182
/*     */     //   #232	-> 187
/*     */     //   #234	-> 192
/*     */     //   #235	-> 203
/*     */     //   #237	-> 239
/*     */     //   #238	-> 243
/*     */     //   #239	-> 251
/*     */     //   #240	-> 256
/*     */     //   #242	-> 270
/*     */     //   #243	-> 275
/*     */     //   #244	-> 286
/*     */     //   #245	-> 291
/*     */     //   #248	-> 307
/*     */     //   #249	-> 313
/*     */     //   #250	-> 316
/*     */     //   #251	-> 323
/*     */     //   #255	-> 330
/*     */     //   #253	-> 333
/*     */     //   #254	-> 335
/*     */     //   #258	-> 350
/*     */     //   #259	-> 365
/*     */     //   #261	-> 368
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   138	0	16	e	Ljavax/xml/ws/WebServiceException;
/*     */     //   97	44	15	wse	Lcom/sun/xml/ws/api/server/WSEndpoint;
/*     */     //   88	53	14	be	Lcom/sun/xml/ws/api/server/BoundEndpoint;
/*     */     //   66	78	13	i$	Ljava/util/Iterator;
/*     */     //   57	87	12	beList	Ljava/util/List;
/*     */     //   45	114	11	module	Lcom/sun/xml/ws/api/server/Module;
/*     */     //   286	21	16	wsdlPort	Lcom/sun/xml/ws/api/model/wsdl/WSDLPort;
/*     */     //   251	62	15	wsdlService	Lcom/sun/xml/ws/api/model/wsdl/WSDLService;
/*     */     //   323	7	15	firstService	Ljavax/xml/namespace/QName;
/*     */     //   192	138	12	er	Lorg/xml/sax/EntityResolver;
/*     */     //   203	127	13	wsdlLoc	Ljava/net/URL;
/*     */     //   239	91	14	wsdlDoc	Lcom/sun/xml/ws/model/wsdl/WSDLModelImpl;
/*     */     //   335	15	12	e	Ljava/lang/Exception;
/*     */     //   0	408	0	this	Lcom/sun/xml/ws/spi/ProviderImpl;
/*     */     //   0	408	1	address	Ljava/lang/String;
/*     */     //   0	408	2	interfaceName	Ljavax/xml/namespace/QName;
/*     */     //   0	408	3	serviceName	Ljavax/xml/namespace/QName;
/*     */     //   0	408	4	portName	Ljavax/xml/namespace/QName;
/*     */     //   0	408	5	metadata	Ljava/util/List;
/*     */     //   0	408	6	wsdlDocumentLocation	Ljava/lang/String;
/*     */     //   0	408	7	referenceParameters	Ljava/util/List;
/*     */     //   0	408	8	elements	Ljava/util/List;
/*     */     //   0	408	9	attributes	Ljava/util/Map;
/*     */     //   8	400	10	container	Lcom/sun/xml/ws/api/server/Container;
/*     */     //   182	226	11	wsdlTargetNamespace	Ljava/lang/String;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   57	87	12	beList	Ljava/util/List<Lcom/sun/xml/ws/api/server/BoundEndpoint;>;
/*     */     //   0	408	5	metadata	Ljava/util/List<Lorg/w3c/dom/Element;>;
/*     */     //   0	408	7	referenceParameters	Ljava/util/List<Lorg/w3c/dom/Element;>;
/*     */     //   0	408	8	elements	Ljava/util/List<Lorg/w3c/dom/Element;>;
/*     */     //   0	408	9	attributes	Ljava/util/Map<Ljavax/xml/namespace/QName;Ljava/lang/String;>;
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   122	133	136	javax/xml/ws/WebServiceException
/*     */     //   187	330	333	java/lang/Exception
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static JAXBContext getEPRJaxbContext() {
/* 271 */     return AccessController.<JAXBContext>doPrivileged(new PrivilegedAction<JAXBContext>() {
/*     */           public JAXBContext run() {
/*     */             try {
/* 274 */               return JAXBContext.newInstance(new Class[] { MemberSubmissionEndpointReference.class, W3CEndpointReference.class });
/* 275 */             } catch (JAXBException e) {
/* 276 */               throw new WebServiceException("Error creating JAXBContext for W3CEndpointReference. ", e);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\spi\ProviderImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */