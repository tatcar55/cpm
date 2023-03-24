/*     */ package com.sun.xml.ws.server;
/*     */ 
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.fault.SOAPFaultBuilder;
/*     */ import com.sun.xml.ws.util.pipe.AbstractSchemaValidationTube;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.validation.Schema;
/*     */ import javax.xml.validation.Validator;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.w3c.dom.ls.LSResourceResolver;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerSchemaValidationTube
/*     */   extends AbstractSchemaValidationTube
/*     */ {
/*  72 */   private static final Logger LOGGER = Logger.getLogger(ServerSchemaValidationTube.class.getName());
/*     */   
/*     */   private final Schema schema;
/*     */   
/*     */   private final Validator validator;
/*     */   
/*     */   private final boolean noValidation;
/*     */   private final SEIModel seiModel;
/*     */   private final WSDLPort wsdlPort;
/*     */   
/*     */   public ServerSchemaValidationTube(WSEndpoint endpoint, WSBinding binding, SEIModel seiModel, WSDLPort wsdlPort, Tube next) {
/*  83 */     super(binding, next);
/*  84 */     this.seiModel = seiModel;
/*  85 */     this.wsdlPort = wsdlPort;
/*     */     
/*  87 */     if (endpoint.getServiceDefinition() != null) {
/*  88 */       AbstractSchemaValidationTube.MetadataResolverImpl mdresolver = new AbstractSchemaValidationTube.MetadataResolverImpl(this, (Iterable)endpoint.getServiceDefinition());
/*  89 */       Source[] sources = getSchemaSources((Iterable)endpoint.getServiceDefinition(), mdresolver);
/*  90 */       for (Source source : sources) {
/*  91 */         LOGGER.fine("Constructing service validation schema from = " + source.getSystemId());
/*     */       }
/*     */       
/*  94 */       if (sources.length != 0) {
/*  95 */         this.noValidation = false;
/*  96 */         this.sf.setResourceResolver((LSResourceResolver)mdresolver);
/*     */         try {
/*  98 */           this.schema = this.sf.newSchema(sources);
/*  99 */         } catch (SAXException e) {
/* 100 */           throw new WebServiceException(e);
/*     */         } 
/* 102 */         this.validator = this.schema.newValidator();
/*     */         return;
/*     */       } 
/*     */     } 
/* 106 */     this.noValidation = true;
/* 107 */     this.schema = null;
/* 108 */     this.validator = null;
/*     */   }
/*     */   
/*     */   protected Validator getValidator() {
/* 112 */     return this.validator;
/*     */   }
/*     */   
/*     */   protected boolean isNoValidation() {
/* 116 */     return this.noValidation;
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processRequest(Packet request) {
/* 121 */     if (isNoValidation() || !this.feature.isInbound() || !request.getMessage().hasPayload() || request.getMessage().isFault()) {
/* 122 */       return super.processRequest(request);
/*     */     }
/*     */     try {
/* 125 */       doProcess(request);
/* 126 */     } catch (SAXException se) {
/* 127 */       LOGGER.log(Level.WARNING, "Client Request doesn't pass Service's Schema Validation", se);
/*     */ 
/*     */ 
/*     */       
/* 131 */       SOAPVersion soapVersion = this.binding.getSOAPVersion();
/* 132 */       Message faultMsg = SOAPFaultBuilder.createSOAPFaultMessage(soapVersion, null, se, soapVersion.faultCodeClient);
/*     */       
/* 134 */       return doReturnWith(request.createServerResponse(faultMsg, this.wsdlPort, this.seiModel, this.binding));
/*     */     } 
/*     */     
/* 137 */     return super.processRequest(request);
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processResponse(Packet response) {
/* 142 */     if (isNoValidation() || !this.feature.isOutbound() || response.getMessage() == null || !response.getMessage().hasPayload() || response.getMessage().isFault()) {
/* 143 */       return super.processResponse(response);
/*     */     }
/*     */     try {
/* 146 */       doProcess(response);
/* 147 */     } catch (SAXException se) {
/*     */       
/* 149 */       throw new WebServiceException(se);
/*     */     } 
/* 151 */     return super.processResponse(response);
/*     */   }
/*     */   
/*     */   protected ServerSchemaValidationTube(ServerSchemaValidationTube that, TubeCloner cloner) {
/* 155 */     super(that, cloner);
/*     */     
/* 157 */     this.schema = that.schema;
/* 158 */     this.validator = this.schema.newValidator();
/* 159 */     this.noValidation = that.noValidation;
/* 160 */     this.seiModel = that.seiModel;
/* 161 */     this.wsdlPort = that.wsdlPort;
/*     */   }
/*     */   
/*     */   public AbstractTubeImpl copy(TubeCloner cloner) {
/* 165 */     return (AbstractTubeImpl)new ServerSchemaValidationTube(this, cloner);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\ServerSchemaValidationTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */