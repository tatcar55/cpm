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
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class MapSerializer
/*     */   extends CollectionSerializerBase
/*     */   implements Initializable
/*     */ {
/*  57 */   protected Class mapClass = null;
/*     */ 
/*     */   
/*     */   protected JAXRPCSerializer elemSer;
/*     */ 
/*     */   
/*     */   protected JAXRPCDeserializer elemDeser;
/*     */ 
/*     */ 
/*     */   
/*     */   public MapSerializer(QName type, Class mapClass, boolean encodeType, boolean isNullable, String encodingStyle) {
/*  68 */     this(type, mapClass, encodeType, isNullable, encodingStyle, SOAPVersion.SOAP_11);
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
/*     */   public MapSerializer(QName type, Class mapClass, boolean encodeType, boolean isNullable, String encodingStyle, SOAPVersion version) {
/*  85 */     super(type, encodeType, isNullable, encodingStyle, COLLECTION_ELEMENT_NAME, QNAME_TYPE_JAX_RPC_MAP_ENTRY, JAXRpcMapEntry.class, 1, (int[])null, version);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     this.mapClass = mapClass;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(InternalTypeMappingRegistry registry) throws Exception {
/* 102 */     this.elemSer = (JAXRPCSerializer)registry.getSerializer(this.encodingStyle, this.elemClass, this.elemType);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     this.elemDeser = (JAXRPCDeserializer)registry.getDeserializer(this.encodingStyle, this.elemClass, this.elemType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object[] convertToArray(Object obj) throws Exception {
/* 115 */     Map mapObj = (Map)obj;
/*     */ 
/*     */     
/* 118 */     Set mapEnt_set = mapObj.entrySet();
/* 119 */     Object[] mapEnt_array = mapEnt_set.toArray();
/*     */ 
/*     */     
/* 122 */     JAXRpcMapEntry[] JAXRPCMapEntries = new JAXRpcMapEntry[mapEnt_array.length];
/*     */     
/* 124 */     for (int i = 0; i < mapEnt_array.length; i++) {
/* 125 */       Map.Entry o = (Map.Entry)mapEnt_array[i];
/* 126 */       JAXRpcMapEntry map_entry = new JAXRpcMapEntry(o.getKey(), o.getValue());
/*     */       
/* 128 */       JAXRPCMapEntries[i] = map_entry;
/*     */     } 
/*     */     
/* 131 */     return (Object[])JAXRPCMapEntries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void serializeArrayInstance(Object obj, int[] dims, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/* 141 */     serializeArrayElements((Object[])obj, 0, dims, writer, context);
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
/* 152 */     if (arr == null || arr.length != dims[level]) {
/* 153 */       throw new SerializationException("soap.irregularMultiDimensionalArray");
/*     */     }
/*     */     
/* 156 */     boolean serializeLeaves = (level == dims.length - 1);
/*     */     
/* 158 */     for (int i = 0; i < dims[level]; i++) {
/* 159 */       Object elem = arr[i];
/* 160 */       if (serializeLeaves) {
/* 161 */         this.elemSer.serialize(elem, this.elemName, null, writer, context);
/*     */       } else {
/* 163 */         serializeArrayElements((Object[])elem, level + 1, dims, writer, context);
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
/* 179 */     Map instance = this.mapClass.newInstance();
/* 180 */     String id = getID(reader);
/* 181 */     SOAPDeserializationState state = (id != null) ? context.getStateFor(id) : null;
/*     */     
/* 183 */     boolean isComplete = true;
/* 184 */     boolean emptyDims = isEmptyDimensions(dims);
/* 185 */     int[] dimOffsets = getDimensionOffsets(dims);
/*     */     
/* 187 */     int[] offset = getArrayOffset(reader, dims);
/* 188 */     if (offset == null) {
/* 189 */       offset = new int[emptyDims ? 1 : dims.length];
/*     */     }
/*     */     
/* 192 */     Object[] value = null;
/* 193 */     int maxPosition = 0;
/* 194 */     int length = 0;
/*     */     
/* 196 */     if (reader.nextElementContent() != 2) {
/* 197 */       int[] position = getArrayElementPosition(reader, dims);
/* 198 */       boolean isSparseArray = (position != null);
/*     */       
/* 200 */       if (!isSparseArray) {
/* 201 */         position = offset;
/*     */       }
/*     */       
/* 204 */       if (emptyDims) {
/* 205 */         maxPosition = position[0];
/* 206 */         length = Math.max(maxPosition * 2, 1024);
/* 207 */         value = (Object[])Array.newInstance(this.elemClass, length);
/*     */       } else {
/* 209 */         value = (Object[])Array.newInstance(this.elemClass, dims);
/*     */       } 
/*     */       
/*     */       while (true) {
/* 213 */         if (!emptyDims && !isPositionWithinBounds(position, dims)) {
/* 214 */           if (isSparseArray) {
/* 215 */             throw new DeserializationException("soap.outOfBoundsArrayElementPosition", encodeArrayDimensions(position));
/*     */           }
/*     */ 
/*     */           
/* 219 */           throw new DeserializationException("soap.tooManyArrayElements");
/*     */         } 
/*     */ 
/*     */         
/* 223 */         if (emptyDims && 
/* 224 */           position[0] >= length) {
/* 225 */           int newLength = length * 2;
/* 226 */           while (position[0] >= newLength) {
/* 227 */             newLength *= 2;
/*     */           }
/* 229 */           Object[] newValue = (Object[])Array.newInstance(this.elemClass, newLength);
/*     */           
/* 231 */           System.arraycopy(value, 0, newValue, 0, length);
/* 232 */           value = newValue;
/* 233 */           length = newLength;
/*     */         } 
/*     */ 
/*     */         
/* 237 */         Object elem = null;
/* 238 */         elem = this.elemDeser.deserialize(this.elemName, reader, context);
/*     */         
/* 240 */         if (elem instanceof SOAPDeserializationState) {
/* 241 */           SOAPDeserializationState elemState = (SOAPDeserializationState)elem;
/*     */           
/* 243 */           isComplete = false;
/*     */           
/* 245 */           if (state == null)
/*     */           {
/* 247 */             state = new SOAPDeserializationState();
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 253 */           state.setInstance(instance);
/*     */           
/* 255 */           if (state.getBuilder() == null) {
/* 256 */             state.setBuilder(new MapInstanceBuilder(value, dimOffsets));
/*     */           }
/*     */ 
/*     */           
/* 260 */           elemState.registerListener(state, indexFromPosition(position, dimOffsets));
/*     */         }
/*     */         else {
/*     */           
/* 264 */           setElement(value, position, elem);
/*     */         } 
/*     */         
/* 267 */         if (reader.nextElementContent() == 2) {
/*     */           break;
/*     */         }
/*     */         
/* 271 */         if (isSparseArray) {
/* 272 */           position = getArrayElementPosition(reader, dims);
/* 273 */           if (position == null)
/*     */           {
/* 275 */             throw new DeserializationException("soap.missingArrayElementPosition");
/*     */           }
/*     */         }
/* 278 */         else if (emptyDims) {
/* 279 */           position[0] = position[0] + 1;
/*     */         } else {
/* 281 */           incrementPosition(position, dims);
/*     */         } 
/*     */ 
/*     */         
/* 285 */         if (emptyDims) {
/* 286 */           maxPosition = Math.max(position[0], maxPosition);
/*     */         }
/*     */       } 
/*     */       
/* 290 */       if (emptyDims && 
/* 291 */         length != maxPosition + 1) {
/* 292 */         int newLength = maxPosition + 1;
/* 293 */         Object[] newValue = (Object[])Array.newInstance(this.elemClass, newLength);
/*     */         
/* 295 */         System.arraycopy(value, 0, newValue, 0, newLength);
/* 296 */         value = newValue;
/* 297 */         length = newLength;
/*     */       }
/*     */     
/*     */     }
/* 301 */     else if (emptyDims) {
/* 302 */       value = (Object[])Array.newInstance(this.elemClass, 0);
/*     */     } else {
/* 304 */       value = (Object[])Array.newInstance(this.elemClass, dims);
/*     */     } 
/*     */ 
/*     */     
/* 308 */     if (state != null) {
/* 309 */       state.setDeserializer((JAXRPCDeserializer)this);
/* 310 */       state.setInstance(instance);
/* 311 */       state.doneReading();
/*     */     } 
/*     */     
/* 314 */     if (isComplete) {
/* 315 */       return arrayToMap(instance, value);
/*     */     }
/* 317 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setElement(Object[] value, int[] position, Object elem) {
/* 326 */     Object[] arr = value;
/* 327 */     for (int i = 0; i < position.length - 1; i++) {
/* 328 */       arr = (Object[])arr[position[i]];
/*     */     }
/*     */     
/* 331 */     arr[position[position.length - 1]] = elem;
/*     */   }
/*     */   
/*     */   private class MapInstanceBuilder
/*     */     implements SOAPInstanceBuilder
/*     */   {
/* 337 */     int[] dimOffsets = null;
/* 338 */     Object[] arrInstance = null;
/* 339 */     Map instance = null;
/*     */     
/*     */     MapInstanceBuilder(Object[] arrInstance, int[] dimOffsets) {
/* 342 */       this.arrInstance = arrInstance;
/* 343 */       this.dimOffsets = dimOffsets;
/*     */     }
/*     */     
/*     */     public int memberGateType(int memberIndex) {
/* 347 */       return 10;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void construct() {
/* 353 */       throw new IllegalStateException();
/*     */     }
/*     */     
/*     */     public void setMember(int index, Object memberValue) {
/* 357 */       int[] position = CollectionSerializerBase.positionFromIndex(index, this.dimOffsets);
/* 358 */       MapSerializer.setElement(this.arrInstance, position, memberValue);
/*     */     }
/*     */     
/*     */     public void initialize() {
/* 362 */       this.instance = MapSerializer.arrayToMap(this.instance, this.arrInstance);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setInstance(Object instance) {
/* 367 */       this.instance = (Map)instance;
/*     */     }
/*     */     
/*     */     public Object getInstance() {
/* 371 */       return this.instance;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static Map arrayToMap(Map<Object, Object> mapInstance, Object[] arrInstance) {
/* 377 */     if (arrInstance != null)
/*     */     {
/* 379 */       for (int i = 0; i < arrInstance.length; i++) {
/* 380 */         JAXRpcMapEntry mapItem = (JAXRpcMapEntry)arrInstance[i];
/* 381 */         Object key = mapItem.getKey();
/* 382 */         Object value = mapItem.getValue();
/* 383 */         mapInstance.put(key, value);
/*     */       } 
/*     */     }
/* 386 */     return mapInstance;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\soap\MapSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */