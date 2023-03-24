/*     */ package com.sun.xml.rpc.encoding;
/*     */ 
/*     */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*     */ import com.sun.xml.rpc.soap.SOAPEncodingConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.streaming.Attributes;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
/*     */ import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReferenceableSerializerImpl
/*     */   extends SerializerBase
/*     */   implements Initializable, ReferenceableSerializer, SerializerCallback
/*     */ {
/*     */   private CombinedSerializer serializer;
/*     */   private boolean serializeAsRef;
/*  51 */   private SOAPEncodingConstants soapEncodingConstants = null;
/*     */ 
/*     */   
/*     */   private void init(SOAPVersion ver) {
/*  55 */     this.soapEncodingConstants = SOAPConstantsFactory.getSOAPEncodingConstants(ver);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceableSerializerImpl(boolean serializeAsRef, CombinedSerializer serializer) {
/*  62 */     this(serializeAsRef, serializer, SOAPVersion.SOAP_11);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceableSerializerImpl(boolean serializeAsRef, CombinedSerializer serializer, SOAPVersion ver) {
/*  69 */     super(serializer.getXmlType(), serializer.getEncodeType(), serializer.isNullable(), serializer.getEncodingStyle());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     init(ver);
/*  75 */     this.serializer = serializer;
/*  76 */     this.serializeAsRef = serializeAsRef;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialize(InternalTypeMappingRegistry registry) throws Exception {
/*  81 */     if (this.serializer instanceof Initializable)
/*  82 */       ((Initializable)this.serializer).initialize(registry); 
/*     */   }
/*     */   
/*     */   public CombinedSerializer getInnermostSerializer() {
/*  86 */     return this.serializer.getInnermostSerializer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(Object obj, QName name, SerializerCallback callback, XMLWriter writer, SOAPSerializationContext context) {
/*  95 */     boolean pushedEncodingStyle = false;
/*     */     try {
/*  97 */       if (!this.serializeAsRef || obj == null) {
/*  98 */         this.serializer.serialize(obj, name, (SerializerCallback)null, writer, context);
/*     */       }
/* 100 */       else if (!context.isRegistered(obj) && context.getSOAPVersion() == SOAPVersion.SOAP_12) {
/*     */         
/* 102 */         context.registerObject(obj, this);
/* 103 */         this.serializer.serialize(obj, name, this, writer, context);
/*     */       } else {
/* 105 */         SOAPSerializationState state = context.registerObject(obj, this);
/*     */         
/* 107 */         writer.startElement((name != null) ? name : this.type);
/* 108 */         if (typeIsEmpty()) {
/* 109 */           throw new SerializationException("soap.unspecifiedType");
/*     */         }
/* 111 */         if (this.encodingStyle != null) {
/* 112 */           pushedEncodingStyle = context.pushEncodingStyle(this.encodingStyle, writer);
/*     */         }
/* 114 */         if (context.getSOAPVersion() == SOAPVersion.SOAP_11) {
/* 115 */           writer.writeAttribute(this.soapEncodingConstants.getQNameAttrHREF(), "#" + state.getID());
/*     */         
/*     */         }
/* 118 */         else if (context.getSOAPVersion() == SOAPVersion.SOAP_12) {
/* 119 */           writer.writeAttribute(this.soapEncodingConstants.getQNameAttrHREF(), state.getID());
/*     */         } 
/*     */ 
/*     */         
/* 123 */         writer.endElement();
/*     */       }
/*     */     
/* 126 */     } catch (SerializationException e) {
/* 127 */       throw e;
/* 128 */     } catch (JAXRPCExceptionBase e) {
/* 129 */       throw new SerializationException(e);
/* 130 */     } catch (Exception e) {
/* 131 */       throw new SerializationException(new LocalizableExceptionAdapter(e));
/*     */     } finally {
/*     */       
/* 134 */       if (pushedEncodingStyle) {
/* 135 */         context.popEncodingStyle();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserialize(QName name, XMLReader reader, SOAPDeserializationContext context) {
/*     */     try {
/* 145 */       String href = null;
/* 146 */       if (context.getSOAPVersion() == SOAPVersion.SOAP_11) {
/* 147 */         href = getHRef(reader);
/* 148 */       } else if (context.getSOAPVersion() == SOAPVersion.SOAP_12) {
/* 149 */         href = getIDRef(reader);
/*     */       } 
/* 151 */       if (href != null) {
/*     */         
/* 153 */         if (href.startsWith("cid:")) {
/* 154 */           return this.serializer.deserialize(name, reader, context);
/*     */         }
/* 156 */         skipEmptyContent(reader);
/* 157 */         SOAPDeserializationState state = context.getStateFor(href);
/* 158 */         state.setDeserializer(this);
/*     */         
/* 160 */         if (state.isComplete()) {
/* 161 */           return state.getInstance();
/*     */         }
/* 163 */         return state;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 168 */       String id = getID(reader);
/* 169 */       boolean isNull = getNullStatus(reader);
/* 170 */       if (!isNull) {
/* 171 */         SOAPDeserializationState state = null;
/* 172 */         Object instance = this.serializer.deserialize(name, reader, context);
/*     */         
/* 174 */         if (id != null) {
/* 175 */           state = context.getStateFor(id);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 187 */         if (instance instanceof SOAPDeserializationState) {
/* 188 */           state = (SOAPDeserializationState)instance;
/* 189 */           state.setDeserializer(this);
/* 190 */         } else if (state != null) {
/* 191 */           state.setInstance(instance);
/* 192 */           state.setDeserializer(this);
/*     */         } 
/*     */         
/* 195 */         if (state != null) {
/* 196 */           state.doneReading();
/* 197 */           return state;
/*     */         } 
/*     */         
/* 200 */         return instance;
/*     */       } 
/* 202 */       this.serializer.deserialize(name, reader, context);
/*     */       
/* 204 */       if (id != null) {
/* 205 */         SOAPDeserializationState state = context.getStateFor(id);
/* 206 */         state.setDeserializer(this);
/* 207 */         state.setInstance(null);
/* 208 */         state.doneReading();
/*     */       } 
/*     */       
/* 211 */       return null;
/*     */     }
/* 213 */     catch (DeserializationException e) {
/* 214 */       throw e;
/* 215 */     } catch (JAXRPCExceptionBase e) {
/* 216 */       throw new DeserializationException(e);
/* 217 */     } catch (Exception e) {
/* 218 */       throw new DeserializationException(new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserialize(DataHandler dataHandler, SOAPDeserializationContext context) {
/* 226 */     return this.serializer.deserialize(dataHandler, context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeInstance(Object obj, QName name, boolean isMultiRef, XMLWriter writer, SOAPSerializationContext context) {
/* 235 */     SerializerCallback callback = isMultiRef ? this : null;
/* 236 */     this.serializer.serialize(obj, name, callback, writer, context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onStartTag(Object obj, QName name, XMLWriter writer, SOAPSerializationContext context) {
/* 245 */     if (!this.serializeAsRef) {
/*     */       return;
/*     */     }
/*     */     try {
/* 249 */       SOAPSerializationState state = context.registerObject(obj, this);
/* 250 */       writer.writeAttribute(this.soapEncodingConstants.getQNameAttrID(), state.getID());
/*     */     
/*     */     }
/* 253 */     catch (SerializationException e) {
/* 254 */       throw e;
/* 255 */     } catch (JAXRPCExceptionBase e) {
/* 256 */       throw new SerializationException(e);
/* 257 */     } catch (Exception e) {
/* 258 */       throw new SerializationException(new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getHRef(XMLReader reader) throws Exception {
/* 264 */     String href = null;
/*     */     
/* 266 */     Attributes attrs = reader.getAttributes();
/* 267 */     href = attrs.getValue("", "href");
/*     */     
/* 269 */     if (href != null)
/*     */     {
/* 271 */       if (href.charAt(0) == '#') {
/* 272 */         href = href.substring(1);
/* 273 */       } else if (!href.startsWith("cid:")) {
/* 274 */         throw new DeserializationException("soap.nonLocalReference", href);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 280 */     return href;
/*     */   }
/*     */   
/*     */   private String getIDRef(XMLReader reader) throws Exception {
/* 284 */     String href = null;
/*     */     
/* 286 */     Attributes attrs = reader.getAttributes();
/* 287 */     href = attrs.getValue("", this.soapEncodingConstants.getQNameAttrHREF().getLocalPart());
/*     */ 
/*     */ 
/*     */     
/* 291 */     return href;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\ReferenceableSerializerImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */