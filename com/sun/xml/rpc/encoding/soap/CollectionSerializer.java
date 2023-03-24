/*     */ package com.sun.xml.rpc.encoding.soap;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.DeserializationException;
/*     */ import com.sun.xml.rpc.encoding.Initializable;
/*     */ import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;
/*     */ import com.sun.xml.rpc.encoding.JAXRPCDeserializer;
/*     */ import com.sun.xml.rpc.encoding.JAXRPCSerializer;
/*     */ import com.sun.xml.rpc.encoding.SOAPDeserializationContext;
/*     */ import com.sun.xml.rpc.encoding.SOAPDeserializationState;
/*     */ import com.sun.xml.rpc.encoding.SOAPInstanceBuilder;
/*     */ import com.sun.xml.rpc.encoding.SOAPSerializationContext;
/*     */ import com.sun.xml.rpc.encoding.SerializationException;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Collection;
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
/*     */ public class CollectionSerializer
/*     */   extends CollectionSerializerBase
/*     */   implements Initializable
/*     */ {
/*  56 */   protected Class collClass = null;
/*     */ 
/*     */ 
/*     */   
/*     */   protected JAXRPCSerializer elemSer;
/*     */ 
/*     */ 
/*     */   
/*     */   protected JAXRPCDeserializer elemDeser;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CollectionSerializer(QName type, Class collClass, boolean encodeType, boolean isNullable, String encodingStyle, QName elemName, QName elemType, Class elemClass) {
/*  70 */     this(type, collClass, encodeType, isNullable, encodingStyle, elemName, elemType, elemClass, SOAPVersion.SOAP_11);
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
/*     */   public CollectionSerializer(QName type, Class collClass, boolean encodeType, boolean isNullable, String encodingStyle, QName elemName, QName elemType, Class elemClass, SOAPVersion version) {
/*  93 */     super(type, encodeType, isNullable, encodingStyle, elemName, elemType, elemClass, 1, (int[])null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     this.collClass = collClass;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(InternalTypeMappingRegistry registry) throws Exception {
/* 109 */     this.elemSer = (JAXRPCSerializer)registry.getSerializer(this.encodingStyle, this.elemClass, this.elemType);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     this.elemDeser = (JAXRPCDeserializer)registry.getDeserializer(this.encodingStyle, this.elemClass, this.elemType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object[] convertToArray(Object obj) throws Exception {
/* 122 */     return ((Collection)obj).toArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void serializeArrayInstance(Object obj, int[] dims, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/* 132 */     serializeArrayElements((Object[])obj, 0, dims, writer, context);
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
/* 143 */     if (arr == null || arr.length != dims[level]) {
/* 144 */       throw new SerializationException("soap.irregularMultiDimensionalArray");
/*     */     }
/*     */     
/* 147 */     boolean serializeLeaves = (level == dims.length - 1);
/*     */     
/* 149 */     for (int i = 0; i < dims[level]; i++) {
/* 150 */       Object elem = arr[i];
/* 151 */       if (serializeLeaves) {
/* 152 */         this.elemSer.serialize(elem, this.elemName, null, writer, context);
/*     */       } else {
/* 154 */         serializeArrayElements((Object[])elem, level + 1, dims, writer, context);
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
/* 170 */     Collection instance = this.collClass.newInstance();
/* 171 */     String id = getID(reader);
/* 172 */     SOAPDeserializationState state = (id != null) ? context.getStateFor(id) : null;
/*     */     
/* 174 */     boolean isComplete = true;
/* 175 */     boolean emptyDims = isEmptyDimensions(dims);
/* 176 */     int[] dimOffsets = getDimensionOffsets(dims);
/*     */     
/* 178 */     int[] offset = getArrayOffset(reader, dims);
/* 179 */     if (offset == null) {
/* 180 */       offset = new int[emptyDims ? 1 : dims.length];
/*     */     }
/*     */     
/* 183 */     Object[] value = null;
/* 184 */     int maxPosition = 0;
/* 185 */     int length = 0;
/*     */     
/* 187 */     if (reader.nextElementContent() != 2) {
/* 188 */       int[] position = getArrayElementPosition(reader, dims);
/* 189 */       boolean isSparseArray = (position != null);
/*     */       
/* 191 */       if (!isSparseArray) {
/* 192 */         position = offset;
/*     */       }
/*     */       
/* 195 */       if (emptyDims) {
/* 196 */         maxPosition = position[0];
/* 197 */         length = Math.max(maxPosition * 2, 1024);
/* 198 */         value = (Object[])Array.newInstance(this.elemClass, length);
/*     */       } else {
/* 200 */         value = (Object[])Array.newInstance(this.elemClass, dims);
/*     */       } 
/*     */       
/*     */       while (true) {
/* 204 */         if (!emptyDims && !isPositionWithinBounds(position, dims)) {
/* 205 */           if (isSparseArray) {
/* 206 */             throw new DeserializationException("soap.outOfBoundsArrayElementPosition", encodeArrayDimensions(position));
/*     */           }
/*     */ 
/*     */           
/* 210 */           throw new DeserializationException("soap.tooManyArrayElements");
/*     */         } 
/*     */ 
/*     */         
/* 214 */         if (emptyDims && 
/* 215 */           position[0] >= length) {
/* 216 */           int newLength = length * 2;
/* 217 */           while (position[0] >= newLength) {
/* 218 */             newLength *= 2;
/*     */           }
/* 220 */           Object[] newValue = (Object[])Array.newInstance(this.elemClass, newLength);
/*     */           
/* 222 */           System.arraycopy(value, 0, newValue, 0, length);
/* 223 */           value = newValue;
/* 224 */           length = newLength;
/*     */         } 
/*     */ 
/*     */         
/* 228 */         Object elem = null;
/* 229 */         elem = this.elemDeser.deserialize(this.elemName, reader, context);
/*     */         
/* 231 */         if (elem instanceof SOAPDeserializationState) {
/* 232 */           SOAPDeserializationState elemState = (SOAPDeserializationState)elem;
/*     */           
/* 234 */           isComplete = false;
/*     */           
/* 236 */           if (state == null)
/*     */           {
/* 238 */             state = new SOAPDeserializationState();
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 244 */           state.setInstance(instance);
/*     */           
/* 246 */           if (state.getBuilder() == null) {
/* 247 */             state.setBuilder(new CollectionInstanceBuilder(value, dimOffsets));
/*     */           }
/*     */ 
/*     */           
/* 251 */           elemState.registerListener(state, indexFromPosition(position, dimOffsets));
/*     */         }
/*     */         else {
/*     */           
/* 255 */           setElement(value, position, elem);
/*     */         } 
/*     */         
/* 258 */         if (reader.nextElementContent() == 2) {
/*     */           break;
/*     */         }
/*     */         
/* 262 */         if (isSparseArray) {
/* 263 */           position = getArrayElementPosition(reader, dims);
/* 264 */           if (position == null)
/*     */           {
/* 266 */             throw new DeserializationException("soap.missingArrayElementPosition");
/*     */           }
/*     */         }
/* 269 */         else if (emptyDims) {
/* 270 */           position[0] = position[0] + 1;
/*     */         } else {
/* 272 */           incrementPosition(position, dims);
/*     */         } 
/*     */ 
/*     */         
/* 276 */         if (emptyDims) {
/* 277 */           maxPosition = Math.max(position[0], maxPosition);
/*     */         }
/*     */       } 
/*     */       
/* 281 */       if (emptyDims && 
/* 282 */         length != maxPosition + 1) {
/* 283 */         int newLength = maxPosition + 1;
/* 284 */         Object[] newValue = (Object[])Array.newInstance(this.elemClass, newLength);
/*     */         
/* 286 */         System.arraycopy(value, 0, newValue, 0, newLength);
/* 287 */         value = newValue;
/* 288 */         length = newLength;
/*     */       }
/*     */     
/*     */     }
/* 292 */     else if (emptyDims) {
/* 293 */       value = (Object[])Array.newInstance(this.elemClass, 0);
/*     */     } else {
/* 295 */       value = (Object[])Array.newInstance(this.elemClass, dims);
/*     */     } 
/*     */ 
/*     */     
/* 299 */     if (state != null) {
/* 300 */       state.setDeserializer((JAXRPCDeserializer)this);
/* 301 */       state.setInstance(instance);
/* 302 */       state.doneReading();
/*     */     } 
/*     */     
/* 305 */     if (isComplete) {
/* 306 */       return arrayToCollection(instance, value);
/*     */     }
/* 308 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setElement(Object[] value, int[] position, Object elem) {
/* 317 */     Object[] arr = value;
/* 318 */     for (int i = 0; i < position.length - 1; i++) {
/* 319 */       arr = (Object[])arr[position[i]];
/*     */     }
/*     */     
/* 322 */     arr[position[position.length - 1]] = elem;
/*     */   }
/*     */   
/*     */   private class CollectionInstanceBuilder
/*     */     implements SOAPInstanceBuilder
/*     */   {
/* 328 */     int[] dimOffsets = null;
/* 329 */     Object[] arrInstance = null;
/* 330 */     Collection instance = null;
/*     */     
/*     */     CollectionInstanceBuilder(Object[] arrInstance, int[] dimOffsets) {
/* 333 */       this.arrInstance = arrInstance;
/* 334 */       this.dimOffsets = dimOffsets;
/*     */     }
/*     */     
/*     */     public int memberGateType(int memberIndex) {
/* 338 */       return 10;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void construct() {
/* 344 */       throw new IllegalStateException();
/*     */     }
/*     */     
/*     */     public void setMember(int index, Object memberValue) {
/* 348 */       int[] position = CollectionSerializerBase.positionFromIndex(index, this.dimOffsets);
/* 349 */       CollectionSerializer.setElement(this.arrInstance, position, memberValue);
/*     */     }
/*     */     
/*     */     public void initialize() {
/* 353 */       this.instance = CollectionSerializer.arrayToCollection(this.instance, this.arrInstance);
/*     */     }
/*     */     
/*     */     public void setInstance(Object instance) {
/* 357 */       this.instance = (Collection)instance;
/*     */     }
/*     */     
/*     */     public Object getInstance() {
/* 361 */       return this.instance;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Collection arrayToCollection(Collection<Object> collInstance, Object[] arrInstance) {
/* 369 */     if (arrInstance != null)
/*     */     {
/* 371 */       for (int i = 0; i < arrInstance.length; i++) {
/* 372 */         Object collItem = arrInstance[i];
/* 373 */         collInstance.add(collItem);
/*     */       } 
/*     */     }
/* 376 */     return collInstance;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\soap\CollectionSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */