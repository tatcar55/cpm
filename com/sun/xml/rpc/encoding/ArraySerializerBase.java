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
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
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
/*     */ 
/*     */ 
/*     */ public abstract class ArraySerializerBase
/*     */   extends SerializerBase
/*     */ {
/*     */   protected QName elemName;
/*     */   protected QName elemType;
/*     */   protected Class elemClass;
/*  58 */   protected int rank = -1;
/*     */   
/*     */   protected int[] dims;
/*     */   protected int[] null_dims;
/*     */   protected ArraySerializerHelper helper;
/*  63 */   private SOAPEncodingConstants soapEncodingConstants = null;
/*     */ 
/*     */   
/*     */   private void init(SOAPVersion ver) {
/*  67 */     if (ver.toString().equals(SOAPVersion.SOAP_12.toString())) {
/*  68 */       this.helper = new SOAP12ArraySerializerHelper();
/*     */     } else {
/*  70 */       this.helper = new SOAP11ArraySerializerHelper();
/*     */     } 
/*  72 */     this.soapEncodingConstants = SOAPConstantsFactory.getSOAPEncodingConstants(ver);
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
/*     */   protected ArraySerializerBase(QName type, boolean encodeType, boolean isNullable, String encodingStyle, QName elemName, QName elemType, Class elemClass, int rank, int[] dims) {
/*  87 */     this(type, encodeType, isNullable, encodingStyle, elemName, elemType, elemClass, rank, dims, SOAPVersion.SOAP_11);
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
/*     */   protected ArraySerializerBase(QName type, boolean encodeType, boolean isNullable, String encodingStyle, QName elemName, QName elemType, Class elemClass, int rank, int[] dims, SOAPVersion ver) {
/* 113 */     super(type, encodeType, isNullable, encodingStyle);
/*     */ 
/*     */     
/* 116 */     init(ver);
/*     */     
/* 118 */     if (elemType == null) {
/* 119 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 122 */     this.elemName = elemName;
/* 123 */     this.elemType = elemType;
/* 124 */     this.elemClass = elemClass;
/* 125 */     this.rank = rank;
/* 126 */     this.dims = dims;
/*     */     
/* 128 */     if (dims != null) {
/* 129 */       this.null_dims = dims;
/* 130 */     } else if (rank >= 0) {
/* 131 */       this.null_dims = new int[rank];
/*     */     } else {
/* 133 */       this.null_dims = new int[0];
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
/* 144 */     boolean pushedEncodingStyle = false;
/*     */     try {
/* 146 */       if (obj == null) {
/* 147 */         if (!this.isNullable) {
/* 148 */           throw new SerializationException("soap.unexpectedNull");
/*     */         }
/*     */         
/* 151 */         serializeNull(name, writer, context);
/*     */       }
/*     */       else {
/*     */         
/* 155 */         writer.startElement((name != null) ? name : this.type);
/* 156 */         if (callback != null) {
/* 157 */           callback.onStartTag(obj, name, writer, context);
/*     */         }
/*     */         
/* 160 */         pushedEncodingStyle = context.pushEncodingStyle(this.encodingStyle, writer);
/*     */ 
/*     */         
/* 163 */         if (this.encodeType) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 172 */           String attrVal = XMLWriterUtil.encodeQName(writer, this.soapEncodingConstants.getQNameEncodingArray());
/*     */ 
/*     */ 
/*     */           
/* 176 */           writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_TYPE, attrVal);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 181 */         int[] dims = (this.dims != null) ? this.dims : getArrayDimensions(obj);
/*     */         
/* 183 */         String encodedDims = this.helper.encodeArrayDimensions(dims);
/*     */         
/* 185 */         this.helper.serializeArray(this.elemType, encodedDims, writer);
/*     */         
/* 187 */         serializeArrayInstance(obj, dims, writer, context);
/*     */         
/* 189 */         writer.endElement();
/*     */       } 
/* 191 */     } catch (SerializationException e) {
/* 192 */       throw e;
/* 193 */     } catch (JAXRPCExceptionBase e) {
/* 194 */       throw new SerializationException(e);
/* 195 */     } catch (Exception e) {
/* 196 */       throw new SerializationException(new LocalizableExceptionAdapter(e));
/*     */     } finally {
/*     */       
/* 199 */       if (pushedEncodingStyle) {
/* 200 */         context.popEncodingStyle();
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
/*     */   protected void serializeNull(QName name, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/* 212 */     boolean pushedEncodingStyle = false;
/* 213 */     writer.startElement((name != null) ? name : this.type);
/*     */     
/* 215 */     pushedEncodingStyle = context.pushEncodingStyle(this.encodingStyle, writer);
/*     */     
/* 217 */     if (this.encodeType) {
/* 218 */       String attrVal = XMLWriterUtil.encodeQName(writer, this.type);
/* 219 */       writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_TYPE, attrVal);
/*     */     } 
/*     */     
/* 222 */     String encodedDims = this.helper.encodeArrayDimensions(this.null_dims);
/* 223 */     this.helper.serializeArray(this.elemType, encodedDims, writer);
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
/*     */     
/* 371 */     if (ver == SOAPVersion.SOAP_11) {
/* 372 */       attrVal = attrs.getValue("http://schemas.xmlsoap.org/soap/encoding/", "offset");
/*     */ 
/*     */     
/*     */     }
/* 376 */     else if (ver == SOAPVersion.SOAP_12) {
/* 377 */       attrVal = attrs.getValue("http://www.w3.org/2002/06/soap-encoding", "offset");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 382 */     if (attrVal != null) {
/* 383 */       offset = decodeArrayDimensions(attrVal);
/* 384 */       if ((isEmptyDimensions(dims) && offset.length != 1) || (!isEmptyDimensions(dims) && offset.length != dims.length))
/*     */       {
/* 386 */         throw new DeserializationException("soap.illegalArrayOffset", attrVal);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 392 */     return offset;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int[] verifyArrayType(XMLReader reader) throws Exception {
/* 397 */     String arrayType = null;
/* 398 */     Attributes attrs = reader.getAttributes();
/* 399 */     arrayType = this.helper.getArrayType(attrs);
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
/* 414 */     if (!actualElemType.equals(this.elemType) && !actualElemType.equals(SchemaConstants.QNAME_TYPE_URTYPE))
/*     */     {
/* 416 */       throw new DeserializationException("soap.unexpectedArrayElementType", new Object[] { this.elemType.toString(), actualElemType.toString() });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static QName getArrayElementType(String arrayType, XMLReader reader) throws Exception {
/* 425 */     QName elemType = null;
/* 426 */     boolean malformed = true;
/*     */     
/* 428 */     String elemTypeStr = arrayType;
/*     */     
/* 430 */     int idx = arrayType.indexOf('[');
/* 431 */     if (idx >= 0) {
/* 432 */       elemTypeStr = arrayType.substring(0, idx).trim();
/*     */     }
/*     */     
/* 435 */     if (elemTypeStr.length() > 0) {
/* 436 */       elemType = XMLReaderUtil.decodeQName(reader, elemTypeStr);
/* 437 */       malformed = false;
/*     */     } 
/*     */     
/* 440 */     if (malformed) {
/* 441 */       throw new DeserializationException("soap.malformedArrayType", arrayType);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 446 */     return elemType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int[] verifyArrayDimensions(String arrayType, XMLReader reader) throws Exception {
/* 452 */     int[] actualDims = getArrayDimensions(arrayType, reader);
/*     */     
/* 454 */     if (this.rank >= 0 && ((
/* 455 */       isEmptyDimensions(actualDims) && this.rank != 1) || (!isEmptyDimensions(actualDims) && actualDims.length != this.rank)))
/*     */     {
/*     */       
/* 458 */       throw new DeserializationException("soap.unexpectedArrayRank", new Object[] { Integer.toString(this.rank), Integer.toString(actualDims.length), arrayType });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 467 */     if (this.dims != null && 
/* 468 */       actualDims.length > 0 && !Arrays.equals(this.dims, actualDims)) {
/* 469 */       throw new DeserializationException("soap.unexpectedArrayDimensions", new Object[] { this.helper.encodeArrayDimensions(this.dims), this.helper.encodeArrayDimensions(actualDims), arrayType });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 478 */     return actualDims;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getArrayDimensions(String arrayType, XMLReader reader) throws Exception {
/* 484 */     boolean isSOAP12 = false;
/* 485 */     String dimStr = null;
/*     */     
/* 487 */     int startIdx = arrayType.lastIndexOf('[');
/* 488 */     int endIdx = arrayType.lastIndexOf(']');
/* 489 */     if (startIdx < 0 || endIdx < 0)
/* 490 */     { isSOAP12 = true; }
/* 491 */     else { if (startIdx > endIdx) {
/* 492 */         throw new DeserializationException("soap.malformedArrayType", arrayType);
/*     */       }
/*     */ 
/*     */       
/* 496 */       dimStr = arrayType.substring(startIdx, endIdx + 1); }
/*     */     
/* 498 */     if (isSOAP12) {
/* 499 */       Attributes attrs = reader.getAttributes();
/* 500 */       dimStr = attrs.getValue("http://www.w3.org/2002/06/soap-encoding", "arraySize");
/* 501 */       if (dimStr == null) {
/* 502 */         throw new DeserializationException("soap.malformedArraySize", "<arraySize attribute mssing>");
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 508 */     return decodeArrayDimensions(dimStr);
/*     */   }
/*     */   
/*     */   protected int getArrayRank(Object obj) {
/* 512 */     int rank = 0;
/* 513 */     Class<?> type = obj.getClass();
/* 514 */     while (type != this.elemClass) {
/* 515 */       rank++;
/* 516 */       type = type.getComponentType();
/*     */     } 
/* 518 */     return rank;
/*     */   }
/*     */   
/*     */   protected int[] getArrayDimensions(Object obj) {
/* 522 */     int rank = (this.rank >= 0) ? this.rank : getArrayRank(obj);
/* 523 */     return getArrayDimensions(obj, rank);
/*     */   }
/*     */   
/*     */   public static int[] getArrayDimensions(Object obj, int rank) {
/* 527 */     int[] dims = new int[rank];
/*     */     
/* 529 */     Object arr = obj;
/* 530 */     for (int i = 0; i < rank; i++) {
/* 531 */       dims[i] = Array.getLength(arr);
/* 532 */       if (dims[i] == 0) {
/*     */         break;
/*     */       }
/*     */       
/* 536 */       arr = Array.get(arr, 0);
/*     */     } 
/* 538 */     return dims;
/*     */   }
/*     */   
/*     */   public static int[] decodeArrayDimensions(String dimStr) throws Exception {
/* 542 */     String str = dimStr.trim();
/*     */ 
/*     */     
/* 545 */     if (str.charAt(0) == '[' || str.charAt(str.length() - 1) == ']') {
/* 546 */       str = str.substring(1, str.length() - 1).trim();
/*     */     }
/*     */ 
/*     */     
/* 550 */     int strLen = str.length();
/*     */     
/* 552 */     int dimCount = 0;
/* 553 */     if (strLen > 0) {
/* 554 */       dimCount++;
/* 555 */       int commaIdx = -1;
/* 556 */       while ((commaIdx = str.indexOf(',', commaIdx + 1)) >= 0) {
/* 557 */         dimCount++;
/*     */       }
/*     */     } 
/*     */     
/* 561 */     int[] dims = new int[dimCount];
/*     */     
/* 563 */     int idx = 0;
/*     */     
/* 565 */     for (int i = 0; i < dimCount; i++) {
/* 566 */       while (idx < strLen && Character.isWhitespace(str.charAt(idx))) {
/* 567 */         idx++;
/*     */       }
/*     */       
/* 570 */       int startIdx = idx;
/* 571 */       int dim = 0; char c;
/* 572 */       while (idx < strLen && Character.isDigit(c = str.charAt(idx))) {
/* 573 */         dim = dim * 10 + c - 48;
/* 574 */         idx++;
/*     */       } 
/*     */       
/* 577 */       if (idx > startIdx) {
/* 578 */         dims[i] = dim;
/*     */       } else {
/* 580 */         throw new DeserializationException("soap.malformedArrayDimensions", dimStr);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 585 */       while (idx < strLen && Character.isWhitespace(str.charAt(idx))) {
/* 586 */         idx++;
/*     */       }
/*     */       
/* 589 */       if (i < dimCount - 1) {
/* 590 */         if (idx >= strLen || str.charAt(idx) != ',') {
/* 591 */           throw new DeserializationException("soap.malformedArrayDimensions", dimStr);
/*     */         }
/*     */ 
/*     */         
/* 595 */         idx++;
/*     */       }
/* 597 */       else if (idx != strLen) {
/* 598 */         throw new DeserializationException("soap.malformedArrayDimensions", dimStr);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 605 */     return dims;
/*     */   }
/*     */   
/*     */   public static String encodeArrayDimensions(int[] dims) throws Exception {
/* 609 */     StringBuffer buf = new StringBuffer("[");
/* 610 */     for (int i = 0; i < dims.length; i++) {
/* 611 */       if (i > 0) {
/* 612 */         buf.append(',');
/*     */       }
/* 614 */       buf.append(dims[i]);
/*     */     } 
/* 616 */     buf.append(']');
/* 617 */     return buf.toString();
/*     */   }
/*     */   
/*     */   public static boolean isPositionWithinBounds(int[] position, int[] dims) {
/* 621 */     for (int i = 0; i < position.length; i++) {
/* 622 */       if (position[i] >= dims[i]) {
/* 623 */         return false;
/*     */       }
/*     */     } 
/* 626 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void incrementPosition(int[] position, int[] dims) throws Exception {
/* 632 */     int i = position.length - 1;
/* 633 */     position[i] = position[i] + 1; for (; i >= 0 && position[i] + 1 >= dims[i]; i--) {
/*     */ 
/*     */       
/* 636 */       if (i == 0) {
/* 637 */         throw new DeserializationException("soap.outOfBoundsArrayElementPosition", encodeArrayDimensions(position));
/*     */       }
/*     */ 
/*     */       
/* 641 */       position[i] = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getDimensionOffsets(int[] dims) {
/* 648 */     int[] dimOffsets = null;
/*     */     
/* 650 */     if (isEmptyDimensions(dims)) {
/* 651 */       dimOffsets = new int[] { 1 };
/*     */     } else {
/* 653 */       dimOffsets = new int[dims.length];
/* 654 */       dimOffsets[dimOffsets.length - 1] = 1;
/* 655 */       for (int i = dimOffsets.length - 2; i >= 0; i--) {
/* 656 */         dimOffsets[i] = dims[i + 1] * dimOffsets[i + 1];
/*     */       }
/*     */     } 
/*     */     
/* 660 */     return dimOffsets;
/*     */   }
/*     */   
/*     */   public static int indexFromPosition(int[] position, int[] dimOffsets) {
/* 664 */     int index = 0;
/* 665 */     for (int i = 0; i < position.length; i++) {
/* 666 */       index += position[i] * dimOffsets[i];
/*     */     }
/* 668 */     return index;
/*     */   }
/*     */   
/*     */   public static int[] positionFromIndex(int index, int[] dimOffsets) {
/* 672 */     int[] position = new int[dimOffsets.length];
/* 673 */     int tmp = index;
/* 674 */     for (int i = 0; i < position.length; i++) {
/* 675 */       position[i] = tmp / dimOffsets[i];
/* 676 */       tmp %= dimOffsets[i];
/*     */     } 
/* 678 */     return position;
/*     */   }
/*     */   
/*     */   public void whatAmI() {
/* 682 */     this.helper.whatAmI();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\ArraySerializerBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */