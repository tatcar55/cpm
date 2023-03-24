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
/*     */ public class SimpleTypeArraySerializer
/*     */   extends ArraySerializerBase
/*     */ {
/*     */   protected SimpleTypeSerializer elemSer;
/*     */   protected Class encoderElemClass;
/*     */   
/*     */   public SimpleTypeArraySerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, QName elemName, QName elemType, Class elemClass, int rank, int[] dims, SimpleTypeSerializer elemSer) {
/*  58 */     this(type, encodeType, isNullable, encodingStyle, elemName, elemType, elemClass, rank, dims, elemSer, SOAPVersion.SOAP_11);
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
/*     */   public SimpleTypeArraySerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, QName elemName, QName elemType, Class elemClass, int rank, int[] dims, SimpleTypeSerializer elemSer, SOAPVersion version) {
/*  85 */     super(type, encodeType, isNullable, encodingStyle, elemName, elemType, elemClass, rank, dims, version);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     this.elemSer = elemSer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void serializeArrayInstance(Object obj, int[] dims, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/* 106 */     serializeArrayElements(obj, 0, dims, writer, context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void serializeArrayElements(Object obj, int level, int[] dims, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/* 117 */     if (obj == null || Array.getLength(obj) != dims[level]) {
/* 118 */       throw new SerializationException("soap.irregularMultiDimensionalArray");
/*     */     }
/*     */     
/* 121 */     boolean serializeLeaves = (level == dims.length - 1);
/*     */     
/* 123 */     for (int i = 0; i < dims[level]; i++) {
/* 124 */       if (serializeLeaves) {
/* 125 */         serializeElement(obj, i, writer, context);
/*     */       } else {
/* 127 */         serializeArrayElements(Array.get(obj, i), level + 1, dims, writer, context);
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
/*     */   protected void serializeElement(Object obj, int index, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/* 144 */     Object elem = Array.get(obj, index);
/* 145 */     this.elemSer.serialize(elem, this.elemName, null, writer, context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object deserializeArrayInstance(XMLReader reader, SOAPDeserializationContext context, int[] dims) throws Exception {
/* 154 */     boolean emptyDims = isEmptyDimensions(dims);
/*     */     
/* 156 */     int[] offset = getArrayOffset(reader, dims);
/* 157 */     if (offset == null) {
/* 158 */       offset = new int[emptyDims ? 1 : dims.length];
/*     */     }
/*     */     
/* 161 */     Object value = null;
/* 162 */     int maxPosition = 0;
/* 163 */     int length = 0;
/*     */     
/* 165 */     if (reader.nextElementContent() != 2) {
/* 166 */       int[] position = getArrayElementPosition(reader, dims);
/* 167 */       boolean isSparseArray = (position != null);
/*     */       
/* 169 */       if (!isSparseArray) {
/* 170 */         position = offset;
/*     */       }
/*     */       
/* 173 */       if (emptyDims) {
/* 174 */         maxPosition = position[0];
/* 175 */         length = Math.max(maxPosition * 2, 1024);
/* 176 */         value = Array.newInstance(this.elemClass, length);
/*     */       } else {
/* 178 */         value = Array.newInstance(this.elemClass, dims);
/*     */       } 
/*     */       
/*     */       while (true) {
/* 182 */         if (!emptyDims && !isPositionWithinBounds(position, dims)) {
/* 183 */           if (isSparseArray) {
/* 184 */             throw new DeserializationException("soap.outOfBoundsArrayElementPosition", encodeArrayDimensions(position));
/*     */           }
/*     */ 
/*     */           
/* 188 */           throw new DeserializationException("soap.tooManyArrayElements");
/*     */         } 
/*     */ 
/*     */         
/* 192 */         if (emptyDims && 
/* 193 */           position[0] >= length) {
/* 194 */           int newLength = length * 2;
/* 195 */           while (position[0] >= newLength) {
/* 196 */             newLength *= 2;
/*     */           }
/* 198 */           Object newValue = Array.newInstance(this.elemClass, newLength);
/*     */           
/* 200 */           System.arraycopy(value, 0, newValue, 0, length);
/* 201 */           value = newValue;
/* 202 */           length = newLength;
/*     */         } 
/*     */ 
/*     */         
/* 206 */         deserializeElement(value, position, reader, context);
/*     */         
/* 208 */         if (reader.nextElementContent() == 2) {
/*     */           break;
/*     */         }
/*     */         
/* 212 */         if (isSparseArray) {
/* 213 */           position = getArrayElementPosition(reader, dims);
/* 214 */           if (position == null)
/*     */           {
/* 216 */             throw new DeserializationException("soap.missingArrayElementPosition");
/*     */           }
/*     */         }
/* 219 */         else if (emptyDims) {
/* 220 */           position[0] = position[0] + 1;
/*     */         } else {
/* 222 */           incrementPosition(position, dims);
/*     */         } 
/*     */ 
/*     */         
/* 226 */         if (emptyDims) {
/* 227 */           maxPosition = Math.max(position[0], maxPosition);
/*     */         }
/*     */       } 
/*     */       
/* 231 */       if (emptyDims && 
/* 232 */         length != maxPosition + 1) {
/* 233 */         int newLength = maxPosition + 1;
/* 234 */         Object newValue = Array.newInstance(this.elemClass, newLength);
/* 235 */         System.arraycopy(value, 0, newValue, 0, newLength);
/* 236 */         value = newValue;
/* 237 */         length = newLength;
/*     */       }
/*     */     
/*     */     }
/* 241 */     else if (emptyDims) {
/* 242 */       value = Array.newInstance(this.elemClass, 0);
/*     */     } else {
/* 244 */       value = Array.newInstance(this.elemClass, dims);
/*     */     } 
/*     */ 
/*     */     
/* 248 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void deserializeElement(Object value, int[] position, XMLReader reader, SOAPDeserializationContext context) throws Exception {
/* 258 */     Object arr = value;
/* 259 */     for (int i = 0; i < position.length - 1; i++) {
/* 260 */       arr = Array.get(arr, position[i]);
/*     */     }
/*     */     
/* 263 */     deserializeElement(arr, position[position.length - 1], reader, context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void deserializeElement(Object value, int position, XMLReader reader, SOAPDeserializationContext context) throws Exception {
/* 272 */     Array.set(value, position, this.elemSer.deserialize(null, reader, context));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\SimpleTypeArraySerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */