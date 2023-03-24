/*     */ package com.sun.xml.rpc.encoding.soap;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.DeserializationException;
/*     */ import com.sun.xml.rpc.encoding.InternalEncodingConstants;
/*     */ import com.sun.xml.rpc.encoding.SOAPDeserializationContext;
/*     */ import com.sun.xml.rpc.encoding.SOAPSerializationContext;
/*     */ import com.sun.xml.rpc.encoding.SerializationException;
/*     */ import com.sun.xml.rpc.encoding.SerializerBase;
/*     */ import com.sun.xml.rpc.encoding.SerializerCallback;
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
/*     */ import java.util.Arrays;
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
/*     */ public abstract class CollectionSerializerBase
/*     */   extends SerializerBase
/*     */   implements InternalEncodingConstants
/*     */ {
/*     */   protected QName elemName;
/*     */   protected QName elemType;
/*     */   protected Class elemClass;
/*  63 */   protected int rank = -1;
/*     */   
/*     */   protected int[] dims;
/*     */   protected int[] null_dims;
/*  67 */   private SOAPEncodingConstants soapEncodingConstants = null;
/*     */ 
/*     */   
/*     */   private void init(SOAPVersion ver) {
/*  71 */     this.soapEncodingConstants = SOAPConstantsFactory.getSOAPEncodingConstants(ver);
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
/*     */   protected CollectionSerializerBase(QName type, boolean encodeType, boolean isNullable, String encodingStyle, QName elemName, QName elemType, Class elemClass, int rank, int[] dims) {
/*  86 */     this(type, encodeType, isNullable, encodingStyle, elemName, elemType, elemClass, rank, dims, SOAPVersion.SOAP_11);
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
/*     */   protected CollectionSerializerBase(QName type, boolean encodeType, boolean isNullable, String encodingStyle, QName elemName, QName elemType, Class elemClass, int rank, int[] dims, SOAPVersion ver) {
/* 111 */     super(type, encodeType, isNullable, encodingStyle);
/* 112 */     init(ver);
/* 113 */     if (elemType == null) {
/* 114 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 117 */     this.elemName = elemName;
/* 118 */     this.elemType = elemType;
/* 119 */     this.elemClass = elemClass;
/* 120 */     this.rank = rank;
/* 121 */     this.dims = dims;
/*     */     
/* 123 */     if (dims != null) {
/* 124 */       this.null_dims = dims;
/* 125 */     } else if (rank >= 0) {
/* 126 */       this.null_dims = new int[rank];
/*     */     } else {
/* 128 */       this.null_dims = new int[0];
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(Object obj, QName name, SerializerCallback callback, XMLWriter writer, SOAPSerializationContext context) {
/* 139 */     boolean pushedEncodingStyle = false;
/*     */     try {
/* 141 */       if (obj == null) {
/* 142 */         if (!this.isNullable) {
/* 143 */           throw new SerializationException("soap.unexpectedNull");
/*     */         }
/*     */         
/* 146 */         serializeNull(name, writer, context);
/*     */       }
/*     */       else {
/*     */         
/* 150 */         writer.startElement((name != null) ? name : this.type);
/* 151 */         if (callback != null) {
/* 152 */           callback.onStartTag(obj, name, writer, context);
/*     */         }
/*     */         
/* 155 */         pushedEncodingStyle = context.pushEncodingStyle(this.encodingStyle, writer);
/*     */ 
/*     */         
/* 158 */         if (this.encodeType) {
/* 159 */           String str = XMLWriterUtil.encodeQName(writer, this.type);
/* 160 */           writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_TYPE, str);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 170 */         Object[] objArray = convertToArray(obj);
/* 171 */         int[] dims = (this.dims != null) ? this.dims : getArrayDimensions(objArray);
/*     */ 
/*     */ 
/*     */         
/* 175 */         String encodedDims = encodeArrayDimensions(dims);
/* 176 */         String attrVal = XMLWriterUtil.encodeQName(writer, this.elemType) + encodedDims;
/*     */         
/* 178 */         writer.writeAttributeUnquoted(this.soapEncodingConstants.getQNameEncodingArraytype(), attrVal);
/*     */ 
/*     */ 
/*     */         
/* 182 */         serializeArrayInstance(objArray, dims, writer, context);
/*     */         
/* 184 */         writer.endElement();
/*     */       } 
/* 186 */     } catch (SerializationException e) {
/* 187 */       throw e;
/* 188 */     } catch (JAXRPCExceptionBase e) {
/* 189 */       throw new SerializationException(e);
/* 190 */     } catch (Exception e) {
/* 191 */       throw new SerializationException(new LocalizableExceptionAdapter(e));
/*     */     } finally {
/*     */       
/* 194 */       if (pushedEncodingStyle) {
/* 195 */         context.popEncodingStyle();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Object[] convertToArray(Object paramObject) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void serializeNull(QName name, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/* 209 */     boolean pushedEncodingStyle = false;
/* 210 */     writer.startElement((name != null) ? name : this.type);
/*     */     
/* 212 */     pushedEncodingStyle = context.pushEncodingStyle(this.encodingStyle, writer);
/*     */     
/* 214 */     if (this.encodeType) {
/* 215 */       String str = XMLWriterUtil.encodeQName(writer, this.type);
/* 216 */       writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_TYPE, str);
/*     */     } 
/*     */     
/* 219 */     String encodedDims = encodeArrayDimensions(this.null_dims);
/* 220 */     String attrVal = XMLWriterUtil.encodeQName(writer, this.elemType) + encodedDims;
/* 221 */     writer.writeAttributeUnquoted(this.soapEncodingConstants.getQNameEncodingArraytype(), attrVal);
/*     */ 
/*     */ 
/*     */     
/* 225 */     writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_NIL, "1");
/*     */     
/* 227 */     writer.endElement();
/* 228 */     if (pushedEncodingStyle) {
/* 229 */       context.popEncodingStyle();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void serializeArrayInstance(Object paramObject, int[] paramArrayOfint, XMLWriter paramXMLWriter, SOAPSerializationContext paramSOAPSerializationContext) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserialize(QName name, XMLReader reader, SOAPDeserializationContext context) {
/* 245 */     boolean pushedEncodingStyle = false;
/*     */     try {
/* 247 */       pushedEncodingStyle = context.processEncodingStyle(reader);
/* 248 */       context.verifyEncodingStyle(this.encodingStyle);
/*     */       
/* 250 */       if (name != null) {
/* 251 */         verifyName(reader, name);
/*     */       }
/*     */       
/* 254 */       boolean isNull = getNullStatus(reader);
/* 255 */       if (!isNull) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 262 */         QName actualType = getType(reader);
/* 263 */         if (actualType != null && 
/* 264 */           !actualType.equals(this.type) && !actualType.equals(this.soapEncodingConstants.getQNameEncodingArray()))
/*     */         {
/*     */           
/* 267 */           throw new DeserializationException("soap.unexpectedElementType", new Object[] { this.type.toString(), actualType.toString() });
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 275 */         int[] dims = verifyArrayType(reader);
/*     */         
/* 277 */         Object rslt = deserializeArrayInstance(reader, context, dims);
/* 278 */         XMLReaderUtil.verifyReaderState(reader, 2);
/* 279 */         return rslt;
/*     */       } 
/* 281 */       if (!this.isNullable) {
/* 282 */         throw new DeserializationException("soap.unexpectedNull");
/*     */       }
/*     */       
/* 285 */       skipEmptyContent(reader);
/*     */       
/* 287 */       return null;
/*     */     }
/* 289 */     catch (DeserializationException e) {
/* 290 */       throw e;
/* 291 */     } catch (JAXRPCExceptionBase e) {
/* 292 */       throw new DeserializationException(e);
/* 293 */     } catch (Exception e) {
/* 294 */       throw new DeserializationException(new LocalizableExceptionAdapter(e));
/*     */     } finally {
/*     */       
/* 297 */       if (pushedEncodingStyle) {
/* 298 */         context.popEncodingStyle();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Object deserializeArrayInstance(XMLReader paramXMLReader, SOAPDeserializationContext paramSOAPDeserializationContext, int[] paramArrayOfint) throws Exception;
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isEmptyDimensions(int[] dims) {
/* 310 */     return (dims.length == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getArrayElementPosition(XMLReader reader, int[] dims) throws Exception {
/* 316 */     return getArrayElementPosition(reader, dims, SOAPVersion.SOAP_11);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getArrayElementPosition(XMLReader reader, int[] dims, SOAPVersion ver) throws Exception {
/* 325 */     int[] elemPos = null;
/* 326 */     String attrVal = null;
/*     */     
/* 328 */     Attributes attrs = reader.getAttributes();
/*     */     
/* 330 */     if (ver == SOAPVersion.SOAP_11) {
/* 331 */       attrVal = attrs.getValue("http://schemas.xmlsoap.org/soap/encoding/", "position");
/*     */ 
/*     */     
/*     */     }
/* 335 */     else if (ver == SOAPVersion.SOAP_12) {
/* 336 */       attrVal = attrs.getValue("http://www.w3.org/2002/06/soap-encoding", "position");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 341 */     if (attrVal != null) {
/* 342 */       elemPos = decodeArrayDimensions(attrVal);
/* 343 */       if ((isEmptyDimensions(dims) && elemPos.length != 1) || (!isEmptyDimensions(dims) && elemPos.length != dims.length))
/*     */       {
/* 345 */         throw new DeserializationException("soap.illegalArrayElementPosition", attrVal);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 351 */     return elemPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getArrayOffset(XMLReader reader, int[] dims) throws Exception {
/* 357 */     return getArrayOffset(reader, dims, SOAPVersion.SOAP_11);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getArrayOffset(XMLReader reader, int[] dims, SOAPVersion ver) throws Exception {
/* 366 */     int[] offset = null;
/* 367 */     String attrVal = null;
/*     */     
/* 369 */     Attributes attrs = reader.getAttributes();
/* 370 */     if (ver == SOAPVersion.SOAP_11) {
/* 371 */       attrVal = attrs.getValue("http://schemas.xmlsoap.org/soap/encoding/", "offset");
/*     */ 
/*     */     
/*     */     }
/* 375 */     else if (ver == SOAPVersion.SOAP_12) {
/* 376 */       attrVal = attrs.getValue("http://www.w3.org/2002/06/soap-encoding", "offset");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 381 */     if (attrVal != null) {
/* 382 */       offset = decodeArrayDimensions(attrVal);
/* 383 */       if ((isEmptyDimensions(dims) && offset.length != 1) || (!isEmptyDimensions(dims) && offset.length != dims.length))
/*     */       {
/* 385 */         throw new DeserializationException("soap.illegalArrayOffset", attrVal);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 391 */     return offset;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int[] verifyArrayType(XMLReader reader) throws Exception {
/* 396 */     String arrayType = null;
/* 397 */     Attributes attrs = reader.getAttributes();
/* 398 */     arrayType = attrs.getValue(this.soapEncodingConstants.getURIEncoding(), "arrayType");
/*     */     
/* 400 */     if (arrayType == null) {
/* 401 */       throw new DeserializationException("soap.malformedArrayType", "<arrayType attribute missing>");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 406 */     verifyArrayElementType(arrayType, reader);
/* 407 */     return verifyArrayDimensions(arrayType, reader);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void verifyArrayElementType(String arrayType, XMLReader reader) throws Exception {
/* 413 */     QName actualElemType = getArrayElementType(arrayType, reader);
/* 414 */     if (!actualElemType.equals(this.elemType)) {
/* 415 */       throw new DeserializationException("soap.unexpectedArrayElementType", new Object[] { this.elemType.toString(), actualElemType.toString() });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static QName getArrayElementType(String arrayType, XMLReader reader) throws Exception {
/* 424 */     QName elemType = null;
/* 425 */     boolean malformed = true;
/*     */     
/* 427 */     int idx = arrayType.indexOf('[');
/* 428 */     if (idx >= 0) {
/* 429 */       String elemTypeStr = arrayType.substring(0, idx).trim();
/* 430 */       if (elemTypeStr.length() > 0) {
/* 431 */         elemType = XMLReaderUtil.decodeQName(reader, elemTypeStr);
/* 432 */         malformed = false;
/*     */       } 
/*     */     } 
/*     */     
/* 436 */     if (malformed) {
/* 437 */       throw new DeserializationException("soap.malformedArrayType", arrayType);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 442 */     return elemType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int[] verifyArrayDimensions(String arrayType, XMLReader reader) throws Exception {
/* 448 */     int[] actualDims = getArrayDimensions(arrayType, reader);
/*     */     
/* 450 */     if (this.rank >= 0 && ((
/* 451 */       isEmptyDimensions(actualDims) && this.rank != 1) || (!isEmptyDimensions(actualDims) && actualDims.length != this.rank)))
/*     */     {
/*     */       
/* 454 */       throw new DeserializationException("soap.unexpectedArrayRank", new Object[] { Integer.toString(this.rank), Integer.toString(actualDims.length), arrayType });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 463 */     if (this.dims != null && 
/* 464 */       actualDims.length > 0 && !Arrays.equals(this.dims, actualDims)) {
/* 465 */       throw new DeserializationException("soap.unexpectedArrayDimensions", new Object[] { encodeArrayDimensions(this.dims), encodeArrayDimensions(actualDims), arrayType });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 474 */     return actualDims;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getArrayDimensions(String arrayType, XMLReader reader) throws Exception {
/* 480 */     int startIdx = arrayType.lastIndexOf('[');
/* 481 */     int endIdx = arrayType.lastIndexOf(']');
/* 482 */     if (startIdx < 0 || endIdx < 0 || startIdx > endIdx) {
/* 483 */       throw new DeserializationException("soap.malformedArrayType", arrayType);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 488 */     String dimStr = arrayType.substring(startIdx, endIdx + 1);
/*     */     
/* 490 */     return decodeArrayDimensions(dimStr);
/*     */   }
/*     */   
/*     */   protected int getArrayRank(Object obj) {
/* 494 */     int rank = 0;
/* 495 */     Class<?> type = obj.getClass();
/* 496 */     while (type != this.elemClass) {
/* 497 */       rank++;
/* 498 */       type = type.getComponentType();
/*     */     } 
/* 500 */     return rank;
/*     */   }
/*     */   
/*     */   protected int[] getArrayDimensions(Object obj) {
/* 504 */     int rank = (this.rank >= 0) ? this.rank : getArrayRank(obj);
/* 505 */     return getArrayDimensions(obj, rank);
/*     */   }
/*     */   
/*     */   public static int[] getArrayDimensions(Object obj, int rank) {
/* 509 */     int[] dims = new int[rank];
/*     */     
/* 511 */     Object arr = obj;
/* 512 */     for (int i = 0; i < rank; i++) {
/* 513 */       dims[i] = Array.getLength(arr);
/* 514 */       if (dims[i] == 0) {
/*     */         break;
/*     */       }
/*     */       
/* 518 */       arr = Array.get(arr, 0);
/*     */     } 
/* 520 */     return dims;
/*     */   }
/*     */   
/*     */   public static int[] decodeArrayDimensions(String dimStr) throws Exception {
/* 524 */     String str = dimStr.trim();
/* 525 */     if (str.charAt(0) != '[' || str.charAt(str.length() - 1) != ']') {
/* 526 */       throw new DeserializationException("soap.malformedArrayDimensions", dimStr);
/*     */     }
/*     */ 
/*     */     
/* 530 */     str = str.substring(1, str.length() - 1).trim();
/* 531 */     int strLen = str.length();
/*     */     
/* 533 */     int dimCount = 0;
/* 534 */     if (strLen > 0) {
/* 535 */       dimCount++;
/* 536 */       int commaIdx = -1;
/* 537 */       while ((commaIdx = str.indexOf(',', commaIdx + 1)) >= 0) {
/* 538 */         dimCount++;
/*     */       }
/*     */     } 
/*     */     
/* 542 */     int[] dims = new int[dimCount];
/*     */     
/* 544 */     int idx = 0;
/*     */     
/* 546 */     for (int i = 0; i < dimCount; i++) {
/* 547 */       while (idx < strLen && Character.isWhitespace(str.charAt(idx))) {
/* 548 */         idx++;
/*     */       }
/*     */       
/* 551 */       int startIdx = idx;
/* 552 */       int dim = 0; char c;
/* 553 */       while (idx < strLen && Character.isDigit(c = str.charAt(idx))) {
/* 554 */         dim = dim * 10 + c - 48;
/* 555 */         idx++;
/*     */       } 
/*     */       
/* 558 */       if (idx > startIdx) {
/* 559 */         dims[i] = dim;
/*     */       } else {
/* 561 */         throw new DeserializationException("soap.malformedArrayDimensions", dimStr);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 566 */       while (idx < strLen && Character.isWhitespace(str.charAt(idx))) {
/* 567 */         idx++;
/*     */       }
/*     */       
/* 570 */       if (i < dimCount - 1) {
/* 571 */         if (idx >= strLen || str.charAt(idx) != ',') {
/* 572 */           throw new DeserializationException("soap.malformedArrayDimensions", dimStr);
/*     */         }
/*     */ 
/*     */         
/* 576 */         idx++;
/*     */       }
/* 578 */       else if (idx != strLen) {
/* 579 */         throw new DeserializationException("soap.malformedArrayDimensions", dimStr);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 586 */     return dims;
/*     */   }
/*     */   
/*     */   public static String encodeArrayDimensions(int[] dims) throws Exception {
/* 590 */     StringBuffer buf = new StringBuffer("[");
/* 591 */     for (int i = 0; i < dims.length; i++) {
/* 592 */       if (i > 0) {
/* 593 */         buf.append(',');
/*     */       }
/* 595 */       buf.append(dims[i]);
/*     */     } 
/* 597 */     buf.append(']');
/* 598 */     return buf.toString();
/*     */   }
/*     */   
/*     */   public static boolean isPositionWithinBounds(int[] position, int[] dims) {
/* 602 */     for (int i = 0; i < position.length; i++) {
/* 603 */       if (position[i] >= dims[i]) {
/* 604 */         return false;
/*     */       }
/*     */     } 
/* 607 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void incrementPosition(int[] position, int[] dims) throws Exception {
/* 613 */     int i = position.length - 1;
/* 614 */     position[i] = position[i] + 1; for (; i >= 0 && position[i] + 1 >= dims[i]; i--) {
/*     */ 
/*     */       
/* 617 */       if (i == 0) {
/* 618 */         throw new DeserializationException("soap.outOfBoundsArrayElementPosition", encodeArrayDimensions(position));
/*     */       }
/*     */ 
/*     */       
/* 622 */       position[i] = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getDimensionOffsets(int[] dims) {
/* 629 */     int[] dimOffsets = null;
/*     */     
/* 631 */     if (isEmptyDimensions(dims)) {
/* 632 */       dimOffsets = new int[] { 1 };
/*     */     } else {
/* 634 */       dimOffsets = new int[dims.length];
/* 635 */       dimOffsets[dimOffsets.length - 1] = 1;
/* 636 */       for (int i = dimOffsets.length - 2; i >= 0; i--) {
/* 637 */         dimOffsets[i] = dims[i + 1] * dimOffsets[i + 1];
/*     */       }
/*     */     } 
/*     */     
/* 641 */     return dimOffsets;
/*     */   }
/*     */   
/*     */   public static int indexFromPosition(int[] position, int[] dimOffsets) {
/* 645 */     int index = 0;
/* 646 */     for (int i = 0; i < position.length; i++) {
/* 647 */       index += position[i] * dimOffsets[i];
/*     */     }
/* 649 */     return index;
/*     */   }
/*     */   
/*     */   public static int[] positionFromIndex(int index, int[] dimOffsets) {
/* 653 */     int[] position = new int[dimOffsets.length];
/* 654 */     int tmp = index;
/* 655 */     for (int i = 0; i < position.length; i++) {
/* 656 */       position[i] = tmp / dimOffsets[i];
/* 657 */       tmp %= dimOffsets[i];
/*     */     } 
/* 659 */     return position;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\soap\CollectionSerializerBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */