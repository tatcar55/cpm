/*     */ package com.sun.xml.rpc.encoding;
/*     */ 
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjectArraySerializer
/*     */   extends ArraySerializerBase
/*     */   implements Initializable
/*     */ {
/*     */   protected JAXRPCSerializer elemSer;
/*     */   protected JAXRPCDeserializer elemDeser;
/*     */   
/*     */   public ObjectArraySerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, QName elemName, QName elemType, Class elemClass, int rank, int[] dims) {
/*  59 */     this(type, encodeType, isNullable, encodingStyle, elemName, elemType, elemClass, rank, dims, SOAPVersion.SOAP_11);
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
/*     */   public ObjectArraySerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, QName elemName, QName elemType, Class elemClass, int rank, int[] dims, SOAPVersion version) {
/*  84 */     super(type, encodeType, isNullable, encodingStyle, elemName, elemType, elemClass, rank, dims, version);
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
/*     */   public void initialize(InternalTypeMappingRegistry registry) throws Exception {
/* 100 */     this.elemSer = (JAXRPCSerializer)registry.getSerializer(this.encodingStyle, this.elemClass, this.elemType);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 105 */     this.elemDeser = (JAXRPCDeserializer)registry.getDeserializer(this.encodingStyle, this.elemClass, this.elemType);
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
/*     */   protected void serializeArrayInstance(Object obj, int[] dims, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/* 119 */     serializeArrayElements((Object[])obj, 0, dims, writer, context);
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
/* 130 */     if (arr == null || arr.length != dims[level]) {
/* 131 */       throw new SerializationException("soap.irregularMultiDimensionalArray");
/*     */     }
/*     */     
/* 134 */     boolean serializeLeaves = (level == dims.length - 1);
/*     */     
/* 136 */     for (int i = 0; i < dims[level]; i++) {
/* 137 */       Object elem = arr[i];
/* 138 */       if (serializeLeaves) {
/* 139 */         this.elemSer.serialize(elem, this.elemName, null, writer, context);
/*     */       } else {
/* 141 */         serializeArrayElements((Object[])elem, level + 1, dims, writer, context);
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
/*     */   protected Object deserializeArrayInstance(XMLReader reader, SOAPDeserializationContext context, int[] dims) throws Exception {
/* 157 */     String id = getID(reader);
/* 158 */     SOAPDeserializationState state = (id != null) ? context.getStateFor(id) : null;
/*     */     
/* 160 */     boolean isComplete = true;
/* 161 */     int arbitraryArrayLength = 1024;
/* 162 */     int maxIncomingArrayLength = 4096;
/* 163 */     boolean emptyDims = isEmptyDimensions(dims);
/*     */ 
/*     */     
/* 166 */     if (!emptyDims && dimLargerThan(dims, maxIncomingArrayLength)) {
/* 167 */       emptyDims = true;
/* 168 */       arbitraryArrayLength = maxIncomingArrayLength;
/*     */     } 
/*     */     
/* 171 */     int[] dimOffsets = getDimensionOffsets(dims);
/*     */     
/* 173 */     int[] offset = getArrayOffset(reader, dims);
/* 174 */     if (offset == null) {
/* 175 */       offset = new int[emptyDims ? 1 : dims.length];
/*     */     }
/*     */     
/* 178 */     Object[] value = null;
/* 179 */     int maxPosition = 0;
/* 180 */     int length = 0;
/*     */     
/* 182 */     if (reader.nextElementContent() != 2) {
/* 183 */       int[] position = getArrayElementPosition(reader, dims);
/* 184 */       boolean isSparseArray = (position != null);
/*     */       
/* 186 */       if (!isSparseArray) {
/* 187 */         position = offset;
/*     */       }
/*     */       
/* 190 */       if (emptyDims) {
/* 191 */         maxPosition = position[0];
/* 192 */         length = Math.max(maxPosition * 2, arbitraryArrayLength);
/* 193 */         value = (Object[])Array.newInstance(this.elemClass, length);
/*     */       } else {
/* 195 */         value = (Object[])Array.newInstance(this.elemClass, dims);
/*     */       } 
/*     */       
/*     */       while (true) {
/* 199 */         if (!emptyDims && !isPositionWithinBounds(position, dims)) {
/* 200 */           if (isSparseArray) {
/* 201 */             throw new DeserializationException("soap.outOfBoundsArrayElementPosition", encodeArrayDimensions(position));
/*     */           }
/*     */ 
/*     */           
/* 205 */           throw new DeserializationException("soap.tooManyArrayElements");
/*     */         } 
/*     */ 
/*     */         
/* 209 */         if (emptyDims && 
/* 210 */           position[0] >= length) {
/* 211 */           int newLength = length * 2;
/* 212 */           while (position[0] >= newLength) {
/* 213 */             newLength *= 2;
/*     */           }
/* 215 */           Object[] newValue = (Object[])Array.newInstance(this.elemClass, newLength);
/*     */           
/* 217 */           System.arraycopy(value, 0, newValue, 0, length);
/* 218 */           value = newValue;
/* 219 */           length = newLength;
/*     */         } 
/*     */ 
/*     */         
/* 223 */         Object elem = null;
/* 224 */         elem = this.elemDeser.deserialize(null, reader, context);
/*     */         
/* 226 */         if (elem instanceof SOAPDeserializationState) {
/* 227 */           SOAPDeserializationState elemState = (SOAPDeserializationState)elem;
/*     */           
/* 229 */           isComplete = false;
/*     */           
/* 231 */           if (state == null)
/*     */           {
/* 233 */             state = new SOAPDeserializationState();
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 239 */           state.setInstance(value);
/*     */           
/* 241 */           if (state.getBuilder() == null) {
/* 242 */             state.setBuilder(new ObjectArrayInstanceBuilder(dimOffsets));
/*     */           }
/*     */ 
/*     */           
/* 246 */           elemState.registerListener(state, indexFromPosition(position, dimOffsets));
/*     */         }
/*     */         else {
/*     */           
/* 250 */           setElement(value, position, elem);
/*     */         } 
/*     */         
/* 253 */         if (reader.nextElementContent() == 2) {
/*     */           break;
/*     */         }
/*     */         
/* 257 */         if (isSparseArray) {
/* 258 */           position = getArrayElementPosition(reader, dims);
/* 259 */           if (position == null)
/*     */           {
/* 261 */             throw new DeserializationException("soap.missingArrayElementPosition");
/*     */           }
/*     */         }
/* 264 */         else if (emptyDims) {
/* 265 */           position[0] = position[0] + 1;
/*     */         } else {
/* 267 */           incrementPosition(position, dims);
/*     */         } 
/*     */ 
/*     */         
/* 271 */         if (emptyDims) {
/* 272 */           maxPosition = Math.max(position[0], maxPosition);
/*     */         }
/*     */       } 
/*     */       
/* 276 */       if (emptyDims)
/*     */       {
/*     */         
/* 279 */         if (length != maxPosition + 1) {
/* 280 */           int newLength = maxPosition + 1;
/* 281 */           Object[] newValue = (Object[])Array.newInstance(this.elemClass, newLength);
/*     */           
/* 283 */           System.arraycopy(value, 0, newValue, 0, newLength);
/* 284 */           value = newValue;
/* 285 */           length = newLength;
/*     */         }
/*     */       
/*     */       }
/* 289 */     } else if (emptyDims) {
/* 290 */       value = (Object[])Array.newInstance(this.elemClass, 0);
/*     */     } else {
/* 292 */       value = (Object[])Array.newInstance(this.elemClass, dims);
/*     */     } 
/*     */ 
/*     */     
/* 296 */     if (state != null) {
/* 297 */       state.setDeserializer(this);
/* 298 */       state.setInstance(value);
/* 299 */       state.doneReading();
/*     */     } 
/*     */     
/* 302 */     if (isComplete) {
/* 303 */       return value;
/*     */     }
/* 305 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setElement(Object[] value, int[] position, Object elem) {
/* 314 */     Object[] arr = value;
/* 315 */     for (int i = 0; i < position.length - 1; i++) {
/* 316 */       arr = (Object[])arr[position[i]];
/*     */     }
/*     */     
/* 319 */     arr[position[position.length - 1]] = elem;
/*     */   }
/*     */   
/*     */   private boolean dimLargerThan(int[] dims, int maxLength) {
/* 323 */     for (int i = 0; i < dims.length; i++) {
/* 324 */       if (dims[i] > maxLength) {
/* 325 */         return true;
/*     */       }
/*     */     } 
/* 328 */     return false;
/*     */   }
/*     */   
/*     */   private class ObjectArrayInstanceBuilder
/*     */     implements SOAPInstanceBuilder {
/* 333 */     Object[] instance = null;
/* 334 */     int[] dimOffsets = null;
/*     */     
/*     */     ObjectArrayInstanceBuilder(int[] dimOffsets) {
/* 337 */       this.dimOffsets = dimOffsets;
/*     */     }
/*     */     
/*     */     public int memberGateType(int memberIndex) {
/* 341 */       return 6;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void construct() {
/* 347 */       throw new IllegalStateException();
/*     */     }
/*     */     
/*     */     public void setMember(int index, Object memberValue) {
/* 351 */       int[] position = ArraySerializerBase.positionFromIndex(index, this.dimOffsets);
/* 352 */       ObjectArraySerializer.setElement(this.instance, position, memberValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public void initialize() {}
/*     */ 
/*     */     
/*     */     public void setInstance(Object instance) {
/* 360 */       this.instance = (Object[])instance;
/*     */     }
/*     */     
/*     */     public Object getInstance() {
/* 364 */       return this.instance;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\ObjectArraySerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */