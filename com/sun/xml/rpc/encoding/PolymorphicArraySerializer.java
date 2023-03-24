/*     */ package com.sun.xml.rpc.encoding;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.xsd.XSDConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*     */ import com.sun.xml.rpc.soap.SOAPEncodingConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.streaming.Attributes;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLReaderUtil;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
/*     */ import com.sun.xml.rpc.streaming.XMLWriterUtil;
/*     */ import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import java.lang.reflect.Array;
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
/*     */ 
/*     */ public class PolymorphicArraySerializer
/*     */   extends SerializerBase
/*     */   implements Initializable
/*     */ {
/*     */   protected QName elemName;
/*     */   protected InternalTypeMappingRegistry registry;
/*     */   protected ArraySerializerHelper helper;
/*  56 */   private SOAPEncodingConstants soapEncodingConstants = null;
/*     */ 
/*     */   
/*     */   private void init(SOAPVersion ver) {
/*  60 */     if (ver.toString().equals(SOAPVersion.SOAP_12.toString())) {
/*  61 */       this.helper = new SOAP12ArraySerializerHelper();
/*     */     } else {
/*  63 */       this.helper = new SOAP11ArraySerializerHelper();
/*     */     } 
/*  65 */     this.soapEncodingConstants = SOAPConstantsFactory.getSOAPEncodingConstants(ver);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolymorphicArraySerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, QName elemName) {
/*  76 */     this(type, encodeType, isNullable, encodingStyle, elemName, SOAPVersion.SOAP_11);
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
/*     */   public PolymorphicArraySerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, QName elemName, SOAPVersion ver) {
/*  93 */     super(type, encodeType, isNullable, encodingStyle);
/*  94 */     init(ver);
/*  95 */     this.elemName = elemName;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initialize(InternalTypeMappingRegistry registry) throws Exception {
/* 100 */     this.registry = registry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(Object obj, QName name, SerializerCallback callback, XMLWriter writer, SOAPSerializationContext context) throws SerializationException {
/* 111 */     boolean pushedEncodingStyle = false;
/*     */     try {
/* 113 */       if (obj == null) {
/* 114 */         if (!this.isNullable) {
/* 115 */           throw new SerializationException("soap.unexpectedNull");
/*     */         }
/*     */         
/* 118 */         serializeNull(name, writer, context);
/*     */       } else {
/* 120 */         if (!obj.getClass().isArray()) {
/* 121 */           throw new SerializationException("type.is.not.array", new Object[] { obj.getClass().getName() });
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 127 */         writer.startElement((name != null) ? name : this.type);
/* 128 */         if (callback != null) {
/* 129 */           callback.onStartTag(obj, name, writer, context);
/*     */         }
/*     */         
/* 132 */         pushedEncodingStyle = context.pushEncodingStyle(this.encodingStyle, writer);
/*     */ 
/*     */         
/* 135 */         if (this.encodeType) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 140 */           String attrVal = XMLWriterUtil.encodeQName(writer, this.soapEncodingConstants.getQNameEncodingArray());
/*     */ 
/*     */ 
/*     */           
/* 144 */           writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_TYPE, attrVal);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 149 */         int[] dims = ArraySerializerBase.getArrayDimensions(obj, getArrayRank(obj));
/*     */ 
/*     */ 
/*     */         
/* 153 */         String encodedDims = this.helper.encodeArrayDimensions(dims);
/* 154 */         QName xmlType = this.registry.getXmlType(this.encodingStyle, Object.class);
/*     */         
/* 156 */         if (xmlType == null) {
/* 157 */           throw new SerializationException("typemapping.serializerNotRegistered", new Object[] { obj.getClass().getName() });
/*     */         }
/*     */ 
/*     */         
/* 161 */         this.helper.serializeArray(xmlType, encodedDims, writer);
/* 162 */         serializeArrayInstance(obj, dims, writer, context);
/*     */         
/* 164 */         writer.endElement();
/*     */       } 
/* 166 */     } catch (SerializationException e) {
/* 167 */       throw e;
/* 168 */     } catch (JAXRPCExceptionBase e) {
/* 169 */       throw new SerializationException(e);
/* 170 */     } catch (Exception e) {
/* 171 */       throw new SerializationException(new LocalizableExceptionAdapter(e));
/*     */     } finally {
/*     */       
/* 174 */       if (pushedEncodingStyle) {
/* 175 */         context.popEncodingStyle();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void serializeArrayInstance(Object obj, int[] dims, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/* 187 */     serializeArrayElements((Object[])obj, 0, dims, writer, context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void serializeArrayElements(Object[] arr, int level, int[] dims, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/* 198 */     if (arr == null || arr.length != dims[level]) {
/* 199 */       throw new SerializationException("soap.irregularMultiDimensionalArray");
/*     */     }
/*     */     
/* 202 */     boolean serializeLeaves = false;
/* 203 */     JAXRPCSerializer elemSer = null;
/*     */     
/* 205 */     if (level == dims.length - 1) {
/* 206 */       serializeLeaves = true;
/* 207 */       elemSer = (JAXRPCSerializer)this.registry.getSerializer(this.encodingStyle, arr.getClass().getComponentType());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     for (int i = 0; i < dims[level]; i++) {
/* 214 */       Object elem = arr[i];
/* 215 */       if (serializeLeaves) {
/* 216 */         elemSer.serialize(elem, this.elemName, null, writer, context);
/*     */       } else {
/* 218 */         serializeArrayElements((Object[])elem, level + 1, dims, writer, context);
/*     */       } 
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
/*     */   protected void serializeNull(QName name, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/* 235 */     boolean pushedEncodingStyle = false;
/* 236 */     writer.startElement((name != null) ? name : this.type);
/*     */     
/* 238 */     pushedEncodingStyle = context.pushEncodingStyle(this.encodingStyle, writer);
/*     */     
/* 240 */     if (this.encodeType) {
/* 241 */       String attrVal = XMLWriterUtil.encodeQName(writer, this.type);
/* 242 */       writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_TYPE, attrVal);
/*     */     } 
/*     */     
/* 245 */     writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_NIL, "1");
/*     */     
/* 247 */     writer.endElement();
/* 248 */     if (pushedEncodingStyle) {
/* 249 */       context.popEncodingStyle();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserialize(QName name, XMLReader reader, SOAPDeserializationContext context) {
/* 258 */     boolean pushedEncodingStyle = false;
/*     */     try {
/* 260 */       pushedEncodingStyle = context.processEncodingStyle(reader);
/* 261 */       context.verifyEncodingStyle(this.encodingStyle);
/*     */       
/* 263 */       if (name != null) {
/* 264 */         verifyName(reader, name);
/*     */       }
/*     */       
/* 267 */       boolean isNull = getNullStatus(reader);
/* 268 */       if (!isNull) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 275 */         QName actualType = getType(reader);
/* 276 */         if (actualType != null && 
/* 277 */           !actualType.equals(this.type) && !actualType.equals(this.soapEncodingConstants.getQNameEncodingArray()))
/*     */         {
/*     */           
/* 280 */           throw new DeserializationException("soap.unexpectedElementType", new Object[] { this.type.toString(), actualType.toString() });
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 288 */         String arrayType = null;
/* 289 */         Attributes attrs = reader.getAttributes();
/* 290 */         arrayType = this.helper.getArrayType(attrs);
/* 291 */         if (arrayType == null) {
/* 292 */           throw new DeserializationException("soap.malformedArrayType", "<arrayType attribute missing>");
/*     */         }
/*     */ 
/*     */         
/* 296 */         int[] dims = ArraySerializerBase.getArrayDimensions(arrayType, reader);
/*     */         
/* 298 */         QName elemXmlType = ArraySerializerBase.getArrayElementType(arrayType, reader);
/*     */         
/* 300 */         Class elemJavaType = this.registry.getJavaType(this.encodingStyle, elemXmlType);
/*     */         
/* 302 */         if (elemJavaType == null) {
/* 303 */           throw new SerializationException("typemapping.deserializerNotRegistered", new Object[] { elemXmlType });
/*     */         }
/*     */ 
/*     */         
/* 307 */         JAXRPCDeserializer elemDeser = (JAXRPCDeserializer)this.registry.getDeserializer(this.encodingStyle, elemXmlType);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 312 */         Object rslt = deserializeArrayInstance(reader, context, dims, elemJavaType, elemDeser);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 319 */         XMLReaderUtil.verifyReaderState(reader, 2);
/* 320 */         return rslt;
/*     */       } 
/* 322 */       if (!this.isNullable) {
/* 323 */         throw new DeserializationException("soap.unexpectedNull");
/*     */       }
/*     */       
/* 326 */       skipEmptyContent(reader);
/*     */       
/* 328 */       return null;
/*     */     }
/* 330 */     catch (DeserializationException e) {
/* 331 */       throw e;
/* 332 */     } catch (JAXRPCExceptionBase e) {
/* 333 */       throw new DeserializationException(e);
/* 334 */     } catch (Exception e) {
/* 335 */       throw new DeserializationException(new LocalizableExceptionAdapter(e));
/*     */     } finally {
/*     */       
/* 338 */       if (pushedEncodingStyle) {
/* 339 */         context.popEncodingStyle();
/*     */       }
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
/*     */   protected Object deserializeArrayInstance(XMLReader reader, SOAPDeserializationContext context, int[] dims, Class<?> elemClass, JAXRPCDeserializer elemDeser) throws Exception {
/* 352 */     String id = getID(reader);
/* 353 */     SOAPDeserializationState state = (id != null) ? context.getStateFor(id) : null;
/*     */     
/* 355 */     boolean isComplete = true;
/* 356 */     boolean emptyDims = ArraySerializerBase.isEmptyDimensions(dims);
/* 357 */     int[] dimOffsets = ArraySerializerBase.getDimensionOffsets(dims);
/*     */     
/* 359 */     int[] offset = ArraySerializerBase.getArrayOffset(reader, dims);
/* 360 */     if (offset == null) {
/* 361 */       offset = new int[emptyDims ? 1 : dims.length];
/*     */     }
/*     */     
/* 364 */     Object[] value = null;
/* 365 */     int maxPosition = 0;
/* 366 */     int length = 0;
/*     */     
/* 368 */     if (reader.nextElementContent() != 2) {
/* 369 */       int[] position = ArraySerializerBase.getArrayElementPosition(reader, dims);
/*     */       
/* 371 */       boolean isSparseArray = (position != null);
/*     */       
/* 373 */       if (!isSparseArray) {
/* 374 */         position = offset;
/*     */       }
/*     */       
/* 377 */       if (emptyDims) {
/* 378 */         maxPosition = position[0];
/* 379 */         length = Math.max(maxPosition * 2, 1024);
/* 380 */         value = (Object[])Array.newInstance(elemClass, length);
/*     */       } else {
/* 382 */         value = (Object[])Array.newInstance(elemClass, dims);
/*     */       } 
/*     */       
/*     */       while (true) {
/* 386 */         if (!emptyDims && !ArraySerializerBase.isPositionWithinBounds(position, dims)) {
/*     */ 
/*     */ 
/*     */           
/* 390 */           if (isSparseArray) {
/* 391 */             throw new DeserializationException("soap.outOfBoundsArrayElementPosition", ArraySerializerBase.encodeArrayDimensions(position));
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 396 */           throw new DeserializationException("soap.tooManyArrayElements");
/*     */         } 
/*     */ 
/*     */         
/* 400 */         if (emptyDims && 
/* 401 */           position[0] >= length) {
/* 402 */           int newLength = length * 2;
/* 403 */           while (position[0] >= newLength) {
/* 404 */             newLength *= 2;
/*     */           }
/* 406 */           Object[] newValue = (Object[])Array.newInstance(elemClass, newLength);
/*     */           
/* 408 */           System.arraycopy(value, 0, newValue, 0, length);
/* 409 */           value = newValue;
/* 410 */           length = newLength;
/*     */         } 
/*     */ 
/*     */         
/* 414 */         Object elem = null;
/* 415 */         elem = elemDeser.deserialize(this.elemName, reader, context);
/*     */         
/* 417 */         if (elem instanceof SOAPDeserializationState) {
/* 418 */           SOAPDeserializationState elemState = (SOAPDeserializationState)elem;
/*     */           
/* 420 */           isComplete = false;
/*     */           
/* 422 */           if (state == null)
/*     */           {
/* 424 */             state = new SOAPDeserializationState();
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 430 */           state.setInstance(value);
/*     */           
/* 432 */           if (state.getBuilder() == null) {
/* 433 */             state.setBuilder(new ObjectArrayInstanceBuilder(dimOffsets));
/*     */           }
/*     */ 
/*     */           
/* 437 */           elemState.registerListener(state, ArraySerializerBase.indexFromPosition(position, dimOffsets));
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 443 */           ObjectArraySerializer.setElement(value, position, elem);
/*     */         } 
/*     */         
/* 446 */         if (reader.nextElementContent() == 2) {
/*     */           break;
/*     */         }
/*     */         
/* 450 */         if (isSparseArray) {
/* 451 */           position = ArraySerializerBase.getArrayElementPosition(reader, dims);
/*     */ 
/*     */ 
/*     */           
/* 455 */           if (position == null)
/*     */           {
/* 457 */             throw new DeserializationException("soap.missingArrayElementPosition");
/*     */           }
/*     */         }
/* 460 */         else if (emptyDims) {
/* 461 */           position[0] = position[0] + 1;
/*     */         } else {
/* 463 */           ArraySerializerBase.incrementPosition(position, dims);
/*     */         } 
/*     */ 
/*     */         
/* 467 */         if (emptyDims) {
/* 468 */           maxPosition = Math.max(position[0], maxPosition);
/*     */         }
/*     */       } 
/*     */       
/* 472 */       if (emptyDims && 
/* 473 */         length != maxPosition + 1) {
/* 474 */         int newLength = maxPosition + 1;
/* 475 */         Object[] newValue = (Object[])Array.newInstance(elemClass, newLength);
/*     */         
/* 477 */         System.arraycopy(value, 0, newValue, 0, newLength);
/* 478 */         value = newValue;
/* 479 */         length = newLength;
/*     */       }
/*     */     
/*     */     }
/* 483 */     else if (emptyDims) {
/* 484 */       value = (Object[])Array.newInstance(elemClass, 0);
/*     */     } else {
/* 486 */       value = (Object[])Array.newInstance(elemClass, dims);
/*     */     } 
/*     */ 
/*     */     
/* 490 */     if (state != null) {
/* 491 */       state.setDeserializer(this);
/* 492 */       state.setInstance(value);
/* 493 */       state.doneReading();
/*     */     } 
/*     */     
/* 496 */     if (isComplete) {
/* 497 */       return value;
/*     */     }
/* 499 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getArrayRank(Object obj) {
/* 505 */     int rank = 0;
/* 506 */     Class<?> type = obj.getClass();
/* 507 */     while (type.isArray()) {
/* 508 */       rank++;
/* 509 */       type = type.getComponentType();
/*     */     } 
/* 511 */     return rank;
/*     */   }
/*     */   
/*     */   private class ObjectArrayInstanceBuilder
/*     */     implements SOAPInstanceBuilder {
/* 516 */     Object[] instance = null;
/* 517 */     int[] dimOffsets = null;
/*     */     
/*     */     ObjectArrayInstanceBuilder(int[] dimOffsets) {
/* 520 */       this.dimOffsets = dimOffsets;
/*     */     }
/*     */     
/*     */     public int memberGateType(int memberIndex) {
/* 524 */       return 6;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void construct() {
/* 530 */       throw new IllegalStateException();
/*     */     }
/*     */     
/*     */     public void setMember(int index, Object memberValue) {
/* 534 */       int[] position = ArraySerializerBase.positionFromIndex(index, this.dimOffsets);
/*     */       
/* 536 */       ObjectArraySerializer.setElement(this.instance, position, memberValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public void initialize() {}
/*     */ 
/*     */     
/*     */     public void setInstance(Object instance) {
/* 544 */       this.instance = (Object[])instance;
/*     */     }
/*     */     
/*     */     public Object getInstance() {
/* 548 */       return this.instance;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\PolymorphicArraySerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */