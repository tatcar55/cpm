/*     */ package com.sun.xml.ws.client;
/*     */ 
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.ws.api.server.SDDocument;
/*     */ import com.sun.xml.ws.util.MetadataUtil;
/*     */ import com.sun.xml.ws.util.pipe.AbstractSchemaValidationTube;
/*     */ import com.sun.xml.ws.wsdl.SDDocumentResolver;
/*     */ import java.util.Map;
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
/*     */ public class ClientSchemaValidationTube
/*     */   extends AbstractSchemaValidationTube
/*     */ {
/*  69 */   private static final Logger LOGGER = Logger.getLogger(ClientSchemaValidationTube.class.getName());
/*     */   
/*     */   private final Schema schema;
/*     */   private final Validator validator;
/*     */   private final boolean noValidation;
/*     */   private final WSDLPort port;
/*     */   
/*     */   public ClientSchemaValidationTube(WSBinding binding, WSDLPort port, Tube next) {
/*  77 */     super(binding, next);
/*  78 */     this.port = port;
/*  79 */     if (port != null) {
/*  80 */       String primaryWsdl = port.getOwner().getParent().getLocation().getSystemId();
/*  81 */       AbstractSchemaValidationTube.MetadataResolverImpl mdresolver = new AbstractSchemaValidationTube.MetadataResolverImpl(this);
/*  82 */       Map<String, SDDocument> docs = MetadataUtil.getMetadataClosure(primaryWsdl, (SDDocumentResolver)mdresolver, true);
/*  83 */       mdresolver = new AbstractSchemaValidationTube.MetadataResolverImpl(this, docs.values());
/*  84 */       Source[] sources = getSchemaSources(docs.values(), mdresolver);
/*  85 */       for (Source source : sources) {
/*  86 */         LOGGER.fine("Constructing client validation schema from = " + source.getSystemId());
/*     */       }
/*     */       
/*  89 */       if (sources.length != 0) {
/*  90 */         this.noValidation = false;
/*  91 */         this.sf.setResourceResolver((LSResourceResolver)mdresolver);
/*     */         try {
/*  93 */           this.schema = this.sf.newSchema(sources);
/*  94 */         } catch (SAXException e) {
/*  95 */           throw new WebServiceException(e);
/*     */         } 
/*  97 */         this.validator = this.schema.newValidator();
/*     */         return;
/*     */       } 
/*     */     } 
/* 101 */     this.noValidation = true;
/* 102 */     this.schema = null;
/* 103 */     this.validator = null;
/*     */   }
/*     */   
/*     */   protected Validator getValidator() {
/* 107 */     return this.validator;
/*     */   }
/*     */   
/*     */   protected boolean isNoValidation() {
/* 111 */     return this.noValidation;
/*     */   }
/*     */   
/*     */   protected ClientSchemaValidationTube(ClientSchemaValidationTube that, TubeCloner cloner) {
/* 115 */     super(that, cloner);
/* 116 */     this.port = that.port;
/* 117 */     this.schema = that.schema;
/* 118 */     this.validator = this.schema.newValidator();
/* 119 */     this.noValidation = that.noValidation;
/*     */   }
/*     */   
/*     */   public AbstractTubeImpl copy(TubeCloner cloner) {
/* 123 */     return (AbstractTubeImpl)new ClientSchemaValidationTube(this, cloner);
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processRequest(Packet request) {
/* 128 */     if (isNoValidation() || !this.feature.isOutbound() || !request.getMessage().hasPayload() || request.getMessage().isFault()) {
/* 129 */       return super.processRequest(request);
/*     */     }
/*     */     try {
/* 132 */       doProcess(request);
/* 133 */     } catch (SAXException se) {
/* 134 */       throw new WebServiceException(se);
/*     */     } 
/* 136 */     return super.processRequest(request);
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processResponse(Packet response) {
/* 141 */     if (isNoValidation() || !this.feature.isInbound() || response.getMessage() == null || !response.getMessage().hasPayload() || response.getMessage().isFault()) {
/* 142 */       return super.processResponse(response);
/*     */     }
/*     */     try {
/* 145 */       doProcess(response);
/* 146 */     } catch (SAXException se) {
/* 147 */       throw new WebServiceException(se);
/*     */     } 
/* 149 */     return super.processResponse(response);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\ClientSchemaValidationTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */