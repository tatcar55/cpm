/*      */ package com.sun.xml.ws.api.message;
/*      */ 
/*      */ import com.oracle.webservices.api.message.BaseDistributedPropertySet;
/*      */ import com.oracle.webservices.api.message.BasePropertySet;
/*      */ import com.oracle.webservices.api.message.ContentType;
/*      */ import com.oracle.webservices.api.message.DistributedPropertySet;
/*      */ import com.oracle.webservices.api.message.MessageContext;
/*      */ import com.oracle.webservices.api.message.PropertySet;
/*      */ import com.oracle.webservices.api.message.PropertySet.Property;
/*      */ import com.sun.istack.NotNull;
/*      */ import com.sun.istack.Nullable;
/*      */ import com.sun.xml.bind.marshaller.SAX2DOMEx;
/*      */ import com.sun.xml.ws.addressing.WsaPropertyBag;
/*      */ import com.sun.xml.ws.addressing.WsaTubeHelper;
/*      */ import com.sun.xml.ws.api.Component;
/*      */ import com.sun.xml.ws.api.DistributedPropertySet;
/*      */ import com.sun.xml.ws.api.EndpointAddress;
/*      */ import com.sun.xml.ws.api.PropertySet;
/*      */ import com.sun.xml.ws.api.SOAPVersion;
/*      */ import com.sun.xml.ws.api.WSBinding;
/*      */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*      */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*      */ import com.sun.xml.ws.api.model.SEIModel;
/*      */ import com.sun.xml.ws.api.model.WSDLOperationMapping;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*      */ import com.sun.xml.ws.api.pipe.Codec;
/*      */ import com.sun.xml.ws.api.server.TransportBackChannel;
/*      */ import com.sun.xml.ws.api.server.WSEndpoint;
/*      */ import com.sun.xml.ws.api.server.WebServiceContextDelegate;
/*      */ import com.sun.xml.ws.api.streaming.XMLStreamWriterFactory;
/*      */ import com.sun.xml.ws.client.ContentNegotiation;
/*      */ import com.sun.xml.ws.client.HandlerConfiguration;
/*      */ import com.sun.xml.ws.client.Stub;
/*      */ import com.sun.xml.ws.message.RelatesToHeader;
/*      */ import com.sun.xml.ws.message.StringHeader;
/*      */ import com.sun.xml.ws.resources.AddressingMessages;
/*      */ import com.sun.xml.ws.util.DOMUtil;
/*      */ import com.sun.xml.ws.util.xml.XmlUtil;
/*      */ import com.sun.xml.ws.wsdl.DispatchException;
/*      */ import com.sun.xml.ws.wsdl.OperationDispatcher;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.nio.channels.WritableByteChannel;
/*      */ import java.util.AbstractMap;
/*      */ import java.util.AbstractSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.logging.Logger;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.soap.SOAPException;
/*      */ import javax.xml.soap.SOAPMessage;
/*      */ import javax.xml.stream.XMLStreamException;
/*      */ import javax.xml.stream.XMLStreamWriter;
/*      */ import javax.xml.ws.BindingProvider;
/*      */ import javax.xml.ws.WebServiceException;
/*      */ import javax.xml.ws.soap.MTOMFeature;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.xml.sax.ContentHandler;
/*      */ import org.xml.sax.SAXException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Packet
/*      */   extends BaseDistributedPropertySet
/*      */   implements MessageContext, MessageMetadata
/*      */ {
/*      */   private Message message;
/*      */   
/*      */   public Packet(Message request) {
/*  190 */     this();
/*  191 */     this.message = request;
/*  192 */     if (this.message != null) this.message.setMessageMedadata(this);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Packet() {
/*  199 */     this.invocationProperties = new HashMap<String, Object>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Packet(Packet that) {
/*  206 */     relatePackets(that, true);
/*  207 */     this.invocationProperties = that.invocationProperties;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Packet copy(boolean copyMessage) {
/*  221 */     Packet copy = new Packet(this);
/*  222 */     if (copyMessage && this.message != null) {
/*  223 */       copy.message = this.message.copy();
/*      */     }
/*  225 */     if (copy.message != null) copy.message.setMessageMedadata(copy); 
/*  226 */     return copy;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Message getMessage() {
/*  237 */     if (this.message != null && !(this.message instanceof MessageWrapper)) {
/*  238 */       this.message = (Message)new MessageWrapper(this, this.message);
/*      */     }
/*  240 */     return this.message;
/*      */   }
/*      */   
/*      */   public Message getInternalMessage() {
/*  244 */     return (this.message instanceof MessageWrapper) ? ((MessageWrapper)this.message).delegate : this.message;
/*      */   }
/*      */   
/*      */   public WSBinding getBinding() {
/*  248 */     if (this.endpoint != null) {
/*  249 */       return this.endpoint.getBinding();
/*      */     }
/*  251 */     if (this.proxy != null) {
/*  252 */       return (WSBinding)this.proxy.getBinding();
/*      */     }
/*  254 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMessage(Message message) {
/*  262 */     this.message = message;
/*  263 */     if (message != null) this.message.setMessageMedadata(this); 
/*      */   }
/*      */   
/*  266 */   private WSDLOperationMapping wsdlOperationMapping = null;
/*      */   
/*      */   private QName wsdlOperation;
/*      */   
/*      */   public boolean wasTransportSecure;
/*      */   public static final String INBOUND_TRANSPORT_HEADERS = "com.sun.xml.ws.api.message.packet.inbound.transport.headers";
/*      */   public static final String OUTBOUND_TRANSPORT_HEADERS = "com.sun.xml.ws.api.message.packet.outbound.transport.headers";
/*      */   public static final String HA_INFO = "com.sun.xml.ws.api.message.packet.hainfo";
/*      */   @Property({"com.sun.xml.ws.handler.config"})
/*      */   public HandlerConfiguration handlerConfig;
/*      */   @Property({"com.sun.xml.ws.client.handle"})
/*      */   public BindingProvider proxy;
/*      */   public boolean isAdapterDeliversNonAnonymousResponse;
/*      */   
/*      */   @Property({"javax.xml.ws.wsdl.operation"})
/*      */   @Nullable
/*      */   public final QName getWSDLOperation() {
/*  283 */     if (this.wsdlOperation != null) return this.wsdlOperation; 
/*  284 */     if (this.wsdlOperationMapping == null) this.wsdlOperationMapping = getWSDLOperationMapping(); 
/*  285 */     if (this.wsdlOperationMapping != null) this.wsdlOperation = this.wsdlOperationMapping.getOperationName(); 
/*  286 */     return this.wsdlOperation;
/*      */   }
/*      */   
/*      */   public WSDLOperationMapping getWSDLOperationMapping() {
/*  290 */     if (this.wsdlOperationMapping != null) return this.wsdlOperationMapping; 
/*  291 */     OperationDispatcher opDispatcher = null;
/*  292 */     if (this.endpoint != null) {
/*  293 */       opDispatcher = this.endpoint.getOperationDispatcher();
/*  294 */     } else if (this.proxy != null) {
/*  295 */       opDispatcher = ((Stub)this.proxy).getOperationDispatcher();
/*      */     } 
/*      */     
/*  298 */     if (opDispatcher != null) {
/*      */       try {
/*  300 */         this.wsdlOperationMapping = opDispatcher.getWSDLOperationMapping(this);
/*  301 */       } catch (DispatchException e) {}
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  306 */     return this.wsdlOperationMapping;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWSDLOperation(QName wsdlOp) {
/*  317 */     this.wsdlOperation = wsdlOp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean packetTakesPriorityOverRequestContext = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EndpointAddress endpointAddress;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ContentNegotiation contentNegotiation;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String acceptableMimeTypes;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WebServiceContextDelegate webServiceContextDelegate;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public TransportBackChannel transportBackChannel;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Component component;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Property({"com.sun.xml.ws.api.server.WSEndpoint"})
/*      */   public WSEndpoint endpoint;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Property({"javax.xml.ws.soap.http.soapaction.uri"})
/*      */   public String soapAction;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Property({"com.sun.xml.ws.server.OneWayOperation"})
/*      */   public Boolean expectReply;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Boolean isOneWay;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Boolean isSynchronousMEP;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Boolean nonNullAsyncHandlerGiven;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Boolean isRequestReplyMEP;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Set<String> handlerScopePropertyNames;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Map<String, Object> invocationProperties;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Property({"javax.xml.ws.service.endpoint.address"})
/*      */   public String getEndPointAddressString() {
/*  412 */     if (this.endpointAddress == null) {
/*  413 */       return null;
/*      */     }
/*  415 */     return this.endpointAddress.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setEndPointAddressString(String s) {
/*  420 */     if (s == null) {
/*  421 */       this.endpointAddress = null;
/*      */     } else {
/*  423 */       this.endpointAddress = EndpointAddress.create(s);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Property({"com.sun.xml.ws.client.ContentNegotiation"})
/*      */   public String getContentNegotiationString() {
/*  437 */     return (this.contentNegotiation != null) ? this.contentNegotiation.toString() : null;
/*      */   }
/*      */   
/*      */   public void setContentNegotiationString(String s) {
/*  441 */     if (s == null) {
/*  442 */       this.contentNegotiation = null;
/*      */     } else {
/*      */       try {
/*  445 */         this.contentNegotiation = ContentNegotiation.valueOf(s);
/*  446 */       } catch (IllegalArgumentException e) {
/*      */         
/*  448 */         this.contentNegotiation = ContentNegotiation.none;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Property({"javax.xml.ws.reference.parameters"})
/*      */   @NotNull
/*      */   public List<Element> getReferenceParameters() {
/*  464 */     Message msg = getMessage();
/*  465 */     List<Element> refParams = new ArrayList<Element>();
/*  466 */     if (msg == null) {
/*  467 */       return refParams;
/*      */     }
/*  469 */     HeaderList hl = msg.getHeaders();
/*  470 */     for (Header h : hl) {
/*  471 */       String attr = h.getAttribute(AddressingVersion.W3C.nsUri, "IsReferenceParameter");
/*  472 */       if (attr != null && (attr.equals("true") || attr.equals("1"))) {
/*  473 */         Document d = DOMUtil.createDom();
/*  474 */         SAX2DOMEx s2d = new SAX2DOMEx(d);
/*      */         try {
/*  476 */           h.writeTo((ContentHandler)s2d, XmlUtil.DRACONIAN_ERROR_HANDLER);
/*  477 */           refParams.add((Element)d.getLastChild());
/*  478 */         } catch (SAXException e) {
/*  479 */           throw new WebServiceException(e);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  493 */     return refParams;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Property({"com.sun.xml.ws.api.message.HeaderList"})
/*      */   HeaderList getHeaderList() {
/*  503 */     Message msg = getMessage();
/*  504 */     if (msg == null) {
/*  505 */       return null;
/*      */     }
/*  507 */     return msg.getHeaders();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TransportBackChannel keepTransportBackChannelOpen() {
/*  562 */     TransportBackChannel r = this.transportBackChannel;
/*  563 */     this.transportBackChannel = null;
/*  564 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Boolean isRequestReplyMEP()
/*      */   {
/*  711 */     return this.isRequestReplyMEP; } public void setRequestReplyMEP(Boolean x) {
/*  712 */     this.isRequestReplyMEP = x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Set<String> getHandlerScopePropertyNames(boolean readOnly) {
/*  758 */     Set<String> o = this.handlerScopePropertyNames;
/*  759 */     if (o == null) {
/*  760 */       if (readOnly) {
/*  761 */         return Collections.emptySet();
/*      */       }
/*  763 */       o = new HashSet<String>();
/*  764 */       this.handlerScopePropertyNames = o;
/*      */     } 
/*  766 */     return o;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Set<String> getApplicationScopePropertyNames(boolean readOnly) {
/*      */     assert false;
/*  778 */     return new HashSet<String>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Packet createResponse(Message msg) {
/*  798 */     Packet response = new Packet(this);
/*  799 */     response.setMessage(msg);
/*  800 */     return response;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Packet createClientResponse(Message msg) {
/*  814 */     Packet response = new Packet(this);
/*  815 */     response.setMessage(msg);
/*  816 */     finishCreateRelateClientResponse(response);
/*  817 */     return response;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Packet relateClientResponse(Packet response) {
/*  824 */     response.relatePackets(this, true);
/*  825 */     finishCreateRelateClientResponse(response);
/*  826 */     return response;
/*      */   }
/*      */   
/*      */   private void finishCreateRelateClientResponse(Packet response) {
/*  830 */     response.soapAction = null;
/*  831 */     response.setState(State.ClientResponse);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Packet createServerResponse(@Nullable Message responseMessage, @Nullable WSDLPort wsdlPort, @Nullable SEIModel seiModel, @NotNull WSBinding binding) {
/*  851 */     Packet r = createClientResponse(responseMessage);
/*  852 */     return relateServerResponse(r, wsdlPort, seiModel, binding);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copyPropertiesTo(@Nullable Packet response) {
/*  860 */     relatePackets(response, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void relatePackets(@Nullable Packet packet, boolean isCopy) {
/*      */     Packet request, response;
/*  876 */     if (!isCopy) {
/*  877 */       request = this;
/*  878 */       response = packet;
/*      */ 
/*      */       
/*  881 */       response.soapAction = null;
/*  882 */       response.invocationProperties.putAll(request.invocationProperties);
/*  883 */       if (getState().equals(State.ServerRequest)) {
/*  884 */         response.setState(State.ServerResponse);
/*      */       }
/*      */     } else {
/*  887 */       request = packet;
/*  888 */       response = this;
/*      */ 
/*      */       
/*  891 */       response.soapAction = request.soapAction;
/*  892 */       response.setState(request.getState());
/*      */     } 
/*      */     
/*  895 */     request.copySatelliteInto(response);
/*  896 */     response.isAdapterDeliversNonAnonymousResponse = request.isAdapterDeliversNonAnonymousResponse;
/*  897 */     response.handlerConfig = request.handlerConfig;
/*  898 */     response.handlerScopePropertyNames = request.handlerScopePropertyNames;
/*  899 */     response.contentNegotiation = request.contentNegotiation;
/*  900 */     response.wasTransportSecure = request.wasTransportSecure;
/*  901 */     response.transportBackChannel = request.transportBackChannel;
/*  902 */     response.endpointAddress = request.endpointAddress;
/*  903 */     response.wsdlOperation = request.wsdlOperation;
/*  904 */     response.wsdlOperationMapping = request.wsdlOperationMapping;
/*  905 */     response.acceptableMimeTypes = request.acceptableMimeTypes;
/*  906 */     response.endpoint = request.endpoint;
/*  907 */     response.proxy = request.proxy;
/*  908 */     response.webServiceContextDelegate = request.webServiceContextDelegate;
/*  909 */     response.expectReply = request.expectReply;
/*  910 */     response.component = request.component;
/*  911 */     response.mtomAcceptable = request.mtomAcceptable;
/*  912 */     response.mtomRequest = request.mtomRequest;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Packet relateServerResponse(@Nullable Packet r, @Nullable WSDLPort wsdlPort, @Nullable SEIModel seiModel, @NotNull WSBinding binding) {
/*  918 */     relatePackets(r, false);
/*  919 */     r.setState(State.ServerResponse);
/*  920 */     AddressingVersion av = binding.getAddressingVersion();
/*      */     
/*  922 */     if (av == null) {
/*  923 */       return r;
/*      */     }
/*      */     
/*  926 */     if (getMessage() == null) {
/*  927 */       return r;
/*      */     }
/*      */ 
/*      */     
/*  931 */     String inputAction = AddressingUtils.getAction(getMessage().getMessageHeaders(), av, binding.getSOAPVersion());
/*  932 */     if (inputAction == null) {
/*  933 */       return r;
/*      */     }
/*      */     
/*  936 */     if (r.getMessage() == null || (wsdlPort != null && getMessage().isOneWay(wsdlPort))) {
/*  937 */       return r;
/*      */     }
/*      */ 
/*      */     
/*  941 */     populateAddressingHeaders(binding, r, wsdlPort, seiModel);
/*  942 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Packet createServerResponse(@Nullable Message responseMessage, @NotNull AddressingVersion addressingVersion, @NotNull SOAPVersion soapVersion, @NotNull String action) {
/*  962 */     Packet responsePacket = createClientResponse(responseMessage);
/*  963 */     responsePacket.setState(State.ServerResponse);
/*      */     
/*  965 */     if (addressingVersion == null) {
/*  966 */       return responsePacket;
/*      */     }
/*      */     
/*  969 */     String inputAction = AddressingUtils.getAction(getMessage().getMessageHeaders(), addressingVersion, soapVersion);
/*  970 */     if (inputAction == null) {
/*  971 */       return responsePacket;
/*      */     }
/*      */     
/*  974 */     populateAddressingHeaders(responsePacket, addressingVersion, soapVersion, action, false);
/*  975 */     return responsePacket;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setResponseMessage(@NotNull Packet request, @Nullable Message responseMessage, @NotNull AddressingVersion addressingVersion, @NotNull SOAPVersion soapVersion, @NotNull String action) {
/*  989 */     Packet temp = request.createServerResponse(responseMessage, addressingVersion, soapVersion, action);
/*  990 */     setMessage(temp.getMessage());
/*      */   }
/*      */ 
/*      */   
/*      */   private void populateAddressingHeaders(Packet responsePacket, AddressingVersion av, SOAPVersion sv, String action, boolean mustUnderstand) {
/*  995 */     if (av == null) {
/*      */       return;
/*      */     }
/*  998 */     if (responsePacket.getMessage() == null) {
/*      */       return;
/*      */     }
/* 1001 */     MessageHeaders hl = responsePacket.getMessage().getMessageHeaders();
/*      */     
/* 1003 */     WsaPropertyBag wpb = (WsaPropertyBag)getSatellite(WsaPropertyBag.class);
/* 1004 */     Message msg = getMessage();
/*      */     
/* 1006 */     WSEndpointReference replyTo = null;
/* 1007 */     Header replyToFromRequestMsg = AddressingUtils.getFirstHeader(msg.getMessageHeaders(), av.replyToTag, true, sv);
/* 1008 */     Header replyToFromResponseMsg = hl.get(av.toTag, false);
/* 1009 */     boolean replaceToTag = true;
/*      */     try {
/* 1011 */       if (replyToFromRequestMsg != null) {
/* 1012 */         replyTo = replyToFromRequestMsg.readAsEPR(av);
/*      */       }
/* 1014 */       if (replyToFromResponseMsg != null && replyTo == null) {
/* 1015 */         replaceToTag = false;
/*      */       }
/* 1017 */     } catch (XMLStreamException e) {
/* 1018 */       throw new WebServiceException(AddressingMessages.REPLY_TO_CANNOT_PARSE(), e);
/*      */     } 
/* 1020 */     if (replyTo == null) {
/* 1021 */       replyTo = AddressingUtils.getReplyTo(msg.getMessageHeaders(), av, sv);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1027 */     if (AddressingUtils.getAction(responsePacket.getMessage().getMessageHeaders(), av, sv) == null)
/*      */     {
/* 1029 */       hl.add((Header)new StringHeader(av.actionTag, action, sv, mustUnderstand));
/*      */     }
/*      */ 
/*      */     
/* 1033 */     if (responsePacket.getMessage().getMessageHeaders().get(av.messageIDTag, false) == null) {
/*      */       
/* 1035 */       String newID = Message.generateMessageID();
/* 1036 */       hl.add((Header)new StringHeader(av.messageIDTag, newID));
/*      */     } 
/*      */ 
/*      */     
/* 1040 */     String mid = null;
/* 1041 */     if (wpb != null) {
/* 1042 */       mid = wpb.getMessageID();
/*      */     }
/* 1044 */     if (mid == null) {
/* 1045 */       mid = AddressingUtils.getMessageID(msg.getMessageHeaders(), av, sv);
/*      */     }
/* 1047 */     if (mid != null) {
/* 1048 */       hl.addOrReplace((Header)new RelatesToHeader(av.relatesToTag, mid));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1053 */     WSEndpointReference refpEPR = null;
/* 1054 */     if (responsePacket.getMessage().isFault()) {
/*      */       
/* 1056 */       if (wpb != null) {
/* 1057 */         refpEPR = wpb.getFaultToFromRequest();
/*      */       }
/* 1059 */       if (refpEPR == null) {
/* 1060 */         refpEPR = AddressingUtils.getFaultTo(msg.getMessageHeaders(), av, sv);
/*      */       }
/*      */       
/* 1063 */       if (refpEPR == null) {
/* 1064 */         refpEPR = replyTo;
/*      */       }
/*      */     } else {
/*      */       
/* 1068 */       refpEPR = replyTo;
/*      */     } 
/* 1070 */     if (replaceToTag && refpEPR != null) {
/* 1071 */       hl.addOrReplace((Header)new StringHeader(av.toTag, refpEPR.getAddress()));
/* 1072 */       refpEPR.addReferenceParametersToList(hl);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void populateAddressingHeaders(WSBinding binding, Packet responsePacket, WSDLPort wsdlPort, SEIModel seiModel) {
/* 1077 */     AddressingVersion addressingVersion = binding.getAddressingVersion();
/*      */     
/* 1079 */     if (addressingVersion == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1083 */     WsaTubeHelper wsaHelper = addressingVersion.getWsaHelper(wsdlPort, seiModel, binding);
/* 1084 */     String action = responsePacket.getMessage().isFault() ? wsaHelper.getFaultAction(this, responsePacket) : wsaHelper.getOutputAction(this);
/*      */ 
/*      */     
/* 1087 */     if (action == null) {
/* 1088 */       LOGGER.info("WSA headers are not added as value for wsa:Action cannot be resolved for this message");
/*      */       return;
/*      */     } 
/* 1091 */     populateAddressingHeaders(responsePacket, addressingVersion, binding.getSOAPVersion(), action, AddressingVersion.isRequired(binding));
/*      */   }
/*      */   
/*      */   public String toShortString() {
/* 1095 */     return super.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/*      */     String content;
/* 1101 */     StringBuilder buf = new StringBuilder();
/* 1102 */     buf.append(super.toString());
/*      */     
/*      */     try {
/* 1105 */       Message msg = getMessage();
/* 1106 */       if (msg != null) {
/* 1107 */         ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 1108 */         XMLStreamWriter xmlWriter = XMLStreamWriterFactory.create(baos, "UTF-8");
/* 1109 */         msg.copy().writeTo(xmlWriter);
/* 1110 */         xmlWriter.flush();
/* 1111 */         xmlWriter.close();
/* 1112 */         baos.flush();
/* 1113 */         XMLStreamWriterFactory.recycle(xmlWriter);
/*      */         
/* 1115 */         byte[] bytes = baos.toByteArray();
/*      */         
/* 1117 */         content = new String(bytes, "UTF-8");
/*      */       } else {
/* 1119 */         content = "<none>";
/*      */       } 
/* 1121 */     } catch (Throwable t) {
/* 1122 */       throw new WebServiceException(t);
/*      */     } 
/* 1124 */     buf.append(" Content: ").append(content);
/* 1125 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1132 */   private static final BasePropertySet.PropertyMap model = parse(Packet.class);
/*      */ 
/*      */ 
/*      */   
/*      */   protected BasePropertySet.PropertyMap getPropertyMap() {
/* 1137 */     return model;
/*      */   }
/*      */   
/*      */   public Map<String, Object> asMapIncludingInvocationProperties() {
/* 1141 */     final Map<String, Object> asMap = asMap();
/* 1142 */     return new AbstractMap<String, Object>()
/*      */       {
/*      */         public Object get(Object key) {
/* 1145 */           Object o = asMap.get(key);
/* 1146 */           if (o != null) {
/* 1147 */             return o;
/*      */           }
/* 1149 */           return Packet.this.invocationProperties.get(key);
/*      */         }
/*      */ 
/*      */         
/*      */         public int size() {
/* 1154 */           return asMap.size() + Packet.this.invocationProperties.size();
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean containsKey(Object key) {
/* 1159 */           if (asMap.containsKey(key))
/* 1160 */             return true; 
/* 1161 */           return Packet.this.invocationProperties.containsKey(key);
/*      */         }
/*      */ 
/*      */         
/*      */         public Set<Map.Entry<String, Object>> entrySet() {
/* 1166 */           final Set<Map.Entry<String, Object>> asMapEntries = asMap.entrySet();
/* 1167 */           final Set<Map.Entry<String, Object>> ipEntries = Packet.this.invocationProperties.entrySet();
/*      */           
/* 1169 */           return new AbstractSet<Map.Entry<String, Object>>()
/*      */             {
/*      */               public Iterator<Map.Entry<String, Object>> iterator() {
/* 1172 */                 final Iterator<Map.Entry<String, Object>> asMapIt = asMapEntries.iterator();
/* 1173 */                 final Iterator<Map.Entry<String, Object>> ipIt = ipEntries.iterator();
/*      */                 
/* 1175 */                 return new Iterator<Map.Entry<String, Object>>()
/*      */                   {
/*      */                     public boolean hasNext() {
/* 1178 */                       return (asMapIt.hasNext() || ipIt.hasNext());
/*      */                     }
/*      */ 
/*      */                     
/*      */                     public Map.Entry<String, Object> next() {
/* 1183 */                       if (asMapIt.hasNext())
/* 1184 */                         return asMapIt.next(); 
/* 1185 */                       return ipIt.next();
/*      */                     }
/*      */ 
/*      */                     
/*      */                     public void remove() {
/* 1190 */                       throw new UnsupportedOperationException();
/*      */                     }
/*      */                   };
/*      */               }
/*      */ 
/*      */               
/*      */               public int size() {
/* 1197 */                 return asMap.size() + Packet.this.invocationProperties.size();
/*      */               }
/*      */             };
/*      */         }
/*      */ 
/*      */         
/*      */         public Object put(String key, Object value) {
/* 1204 */           if (Packet.this.supports(key)) {
/* 1205 */             return asMap.put(key, value);
/*      */           }
/* 1207 */           return Packet.this.invocationProperties.put(key, value);
/*      */         }
/*      */ 
/*      */         
/*      */         public void clear() {
/* 1212 */           asMap.clear();
/* 1213 */           Packet.this.invocationProperties.clear();
/*      */         }
/*      */ 
/*      */         
/*      */         public Object remove(Object key) {
/* 1218 */           if (Packet.this.supports(key)) {
/* 1219 */             return asMap.remove(key);
/*      */           }
/* 1221 */           return Packet.this.invocationProperties.remove(key);
/*      */         }
/*      */       };
/*      */   }
/*      */   
/* 1226 */   private static final Logger LOGGER = Logger.getLogger(Packet.class.getName());
/*      */ 
/*      */   
/*      */   public SOAPMessage getSOAPMessage() throws SOAPException {
/* 1230 */     return getAsSOAPMessage();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public SOAPMessage getAsSOAPMessage() throws SOAPException {
/* 1236 */     Message msg = getMessage();
/* 1237 */     if (msg == null)
/* 1238 */       return null; 
/* 1239 */     if (msg instanceof MessageWritable)
/* 1240 */       ((MessageWritable)msg).setMTOMConfiguration(this.mtomFeature); 
/* 1241 */     return msg.readAsSOAPMessage(this, getState().isInbound());
/*      */   }
/*      */   
/* 1244 */   public Codec codec = null; private ContentType contentType; private Boolean mtomRequest; private Boolean mtomAcceptable;
/*      */   
/*      */   public Codec getCodec() {
/* 1247 */     if (this.codec != null) {
/* 1248 */       return this.codec;
/*      */     }
/* 1250 */     if (this.endpoint != null) {
/* 1251 */       this.codec = this.endpoint.createCodec();
/*      */     }
/* 1253 */     WSBinding wsb = getBinding();
/* 1254 */     if (wsb != null) {
/* 1255 */       this.codec = wsb.getBindingId().createEncoder(wsb);
/*      */     }
/* 1257 */     return this.codec;
/*      */   }
/*      */   private MTOMFeature mtomFeature; Boolean checkMtomAcceptable; private Boolean fastInfosetAcceptable;
/*      */   
/*      */   public ContentType writeTo(OutputStream out) throws IOException {
/* 1262 */     Message msg = getInternalMessage();
/* 1263 */     if (msg instanceof MessageWritable) {
/* 1264 */       ((MessageWritable)msg).setMTOMConfiguration(this.mtomFeature);
/* 1265 */       return ((MessageWritable)msg).writeTo(out);
/*      */     } 
/* 1267 */     return (ContentType)getCodec().encode(this, out);
/*      */   }
/*      */   
/*      */   public ContentType writeTo(WritableByteChannel buffer) {
/* 1271 */     return (ContentType)getCodec().encode(this, buffer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Boolean getMtomRequest() {
/* 1294 */     return this.mtomRequest;
/*      */   }
/*      */   
/*      */   public void setMtomRequest(Boolean mtomRequest) {
/* 1298 */     this.mtomRequest = mtomRequest;
/*      */   }
/*      */   
/*      */   public Boolean getMtomAcceptable() {
/* 1302 */     return this.mtomAcceptable;
/*      */   }
/*      */ 
/*      */   
/*      */   public void checkMtomAcceptable() {
/* 1307 */     if (this.checkMtomAcceptable == null) {
/* 1308 */       if (this.acceptableMimeTypes == null || this.isFastInfosetDisabled) {
/* 1309 */         this.checkMtomAcceptable = Boolean.valueOf(false);
/*      */       } else {
/* 1311 */         this.checkMtomAcceptable = Boolean.valueOf((this.acceptableMimeTypes.indexOf("application/xop+xml") != -1));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1322 */     this.mtomAcceptable = this.checkMtomAcceptable;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Boolean getFastInfosetAcceptable(String fiMimeType) {
/* 1328 */     if (this.fastInfosetAcceptable == null) {
/* 1329 */       if (this.acceptableMimeTypes == null || this.isFastInfosetDisabled) {
/* 1330 */         this.fastInfosetAcceptable = Boolean.valueOf(false);
/*      */       } else {
/* 1332 */         this.fastInfosetAcceptable = Boolean.valueOf((this.acceptableMimeTypes.indexOf(fiMimeType) != -1));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1345 */     return this.fastInfosetAcceptable;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMtomFeature(MTOMFeature mtomFeature) {
/* 1350 */     this.mtomFeature = mtomFeature;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public MTOMFeature getMtomFeature() {
/* 1356 */     WSBinding binding = getBinding();
/* 1357 */     if (binding != null) {
/* 1358 */       return (MTOMFeature)binding.getFeature(MTOMFeature.class);
/*      */     }
/* 1360 */     return this.mtomFeature;
/*      */   }
/*      */ 
/*      */   
/*      */   public ContentType getContentType() {
/* 1365 */     if (this.contentType == null) {
/* 1366 */       this.contentType = getInternalContentType();
/*      */     }
/* 1368 */     if (this.contentType == null) {
/* 1369 */       this.contentType = (ContentType)getCodec().getStaticContentType(this);
/*      */     }
/* 1371 */     if (this.contentType == null);
/*      */ 
/*      */     
/* 1374 */     return this.contentType;
/*      */   }
/*      */   
/*      */   public ContentType getInternalContentType() {
/* 1378 */     Message msg = getInternalMessage();
/* 1379 */     if (msg instanceof MessageWritable) {
/* 1380 */       return ((MessageWritable)msg).getContentType();
/*      */     }
/* 1382 */     return this.contentType;
/*      */   }
/*      */   
/*      */   public void setContentType(ContentType contentType) {
/* 1386 */     this.contentType = contentType;
/*      */   }
/*      */   
/*      */   public enum Status {
/* 1390 */     Request, Response, Unknown;
/* 1391 */     public boolean isRequest() { return Request.equals(this); } public boolean isResponse() {
/* 1392 */       return Response.equals(this);
/*      */     } }
/*      */   
/*      */   public enum State {
/* 1396 */     ServerRequest(true), ClientRequest(false), ServerResponse(false), ClientResponse(true); private boolean inbound;
/*      */     
/*      */     State(boolean inbound) {
/* 1399 */       this.inbound = inbound;
/*      */     }
/*      */     public boolean isInbound() {
/* 1402 */       return this.inbound;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1410 */   private State state = State.ServerRequest;
/*      */   private boolean isFastInfosetDisabled;
/*      */   
/*      */   public State getState() {
/* 1414 */     return this.state; } public void setState(State state) {
/* 1415 */     this.state = state;
/*      */   }
/*      */   public boolean shouldUseMtom() {
/* 1418 */     if (getState().isInbound()) {
/* 1419 */       return isMtomContentType();
/*      */     }
/* 1421 */     return shouldUseMtomOutbound();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean shouldUseMtomOutbound() {
/* 1427 */     MTOMFeature myMtomFeature = getMtomFeature();
/* 1428 */     if (myMtomFeature != null && myMtomFeature.isEnabled()) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1433 */       if (getMtomAcceptable() == null && getMtomRequest() == null) {
/* 1434 */         return true;
/*      */       }
/* 1436 */       if (getMtomAcceptable() != null && getMtomAcceptable().booleanValue() && getState().equals(State.ServerResponse)) {
/* 1437 */         return true;
/*      */       }
/* 1439 */       if (getMtomRequest() != null && getMtomRequest().booleanValue() && getState().equals(State.ServerResponse)) {
/* 1440 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1444 */     return false;
/*      */   }
/*      */   
/*      */   private boolean isMtomContentType() {
/* 1448 */     return (getInternalContentType() != null && getInternalContentType().getContentType().contains("application/xop+xml"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addSatellite(@NotNull PropertySet satellite) {
/* 1456 */     addSatellite((PropertySet)satellite);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addSatellite(@NotNull Class keyClass, @NotNull PropertySet satellite) {
/* 1463 */     addSatellite(keyClass, (PropertySet)satellite);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copySatelliteInto(@NotNull DistributedPropertySet r) {
/* 1470 */     copySatelliteInto((DistributedPropertySet)r);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeSatellite(PropertySet satellite) {
/* 1477 */     removeSatellite((PropertySet)satellite);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFastInfosetDisabled(boolean b) {
/* 1486 */     this.isFastInfosetDisabled = b;
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\message\Packet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */