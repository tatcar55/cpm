/*     */ package com.sun.xml.rpc.encoding;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.simpletype.SimpleTypeConstants;
/*     */ import com.sun.xml.rpc.encoding.simpletype.SimpleTypeEncoder;
/*     */ import com.sun.xml.rpc.streaming.Attributes;
/*     */ import com.sun.xml.rpc.streaming.FastInfosetReader;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLReaderUtil;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
/*     */ import com.sun.xml.rpc.streaming.XMLWriterUtil;
/*     */ import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
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
/*     */ public class SimpleTypeSerializer
/*     */   extends SerializerBase
/*     */   implements SimpleTypeConstants
/*     */ {
/*     */   protected SimpleTypeEncoder encoder;
/*     */   
/*     */   public SimpleTypeSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, SimpleTypeEncoder encoder) {
/*  62 */     super(type, encodeType, isNullable, encodingStyle);
/*  63 */     this.encoder = encoder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(Object obj, QName name, SerializerCallback callback, XMLWriter writer, SOAPSerializationContext context) {
/*  73 */     boolean pushedEncodingStyle = false;
/*     */     try {
/*  75 */       writer.startElement((name != null) ? name : this.type);
/*  76 */       if (callback != null) {
/*  77 */         callback.onStartTag(obj, name, writer, context);
/*     */       }
/*     */       
/*  80 */       if (this.encodingStyle != null) {
/*  81 */         pushedEncodingStyle = context.pushEncodingStyle(this.encodingStyle, writer);
/*     */       }
/*     */       
/*  84 */       if (this.encodeType) {
/*  85 */         String attrVal = XMLWriterUtil.encodeQName(writer, this.type);
/*  86 */         writer.writeAttributeUnquoted(QNAME_XSI_TYPE, attrVal);
/*     */       } 
/*     */       
/*  89 */       if (obj == null) {
/*  90 */         if (!this.isNullable) {
/*  91 */           throw new SerializationException("xsd.unexpectedNull");
/*     */         }
/*     */         
/*  94 */         writer.writeAttributeUnquoted(QNAME_XSI_NIL, "1");
/*     */       } else {
/*  96 */         this.encoder.writeAdditionalNamespaceDeclarations(obj, writer);
/*  97 */         this.encoder.writeValue(obj, writer);
/*     */       } 
/*     */       
/* 100 */       writer.endElement();
/* 101 */     } catch (SerializationException e) {
/* 102 */       throw e;
/* 103 */     } catch (JAXRPCExceptionBase e) {
/* 104 */       throw new SerializationException(e);
/* 105 */     } catch (Exception e) {
/* 106 */       throw new SerializationException(new LocalizableExceptionAdapter(e));
/*     */     } finally {
/*     */       
/* 109 */       if (pushedEncodingStyle) {
/* 110 */         context.popEncodingStyle();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserialize(QName name, XMLReader reader, SOAPDeserializationContext context) {
/* 120 */     boolean pushedEncodingStyle = false;
/*     */     try {
/* 122 */       pushedEncodingStyle = context.processEncodingStyle(reader);
/* 123 */       if (this.encodingStyle != null) {
/* 124 */         context.verifyEncodingStyle(this.encodingStyle);
/*     */       }
/* 126 */       if (name != null) {
/* 127 */         QName actualName = reader.getName();
/* 128 */         if (!actualName.equals(name)) {
/* 129 */           throw new DeserializationException("xsd.unexpectedElementName", new Object[] { name.toString(), actualName.toString() });
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 135 */       verifyType(reader);
/*     */       
/* 137 */       Attributes attrs = reader.getAttributes();
/*     */       
/* 139 */       String nullVal = attrs.getValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
/* 140 */       boolean isNull = (nullVal != null && (nullVal.equals("true") || nullVal.equals("1")));
/*     */ 
/*     */ 
/*     */       
/* 144 */       reader.next();
/* 145 */       Object obj = null;
/*     */       
/* 147 */       if (isNull) {
/* 148 */         if (!this.isNullable) {
/* 149 */           throw new DeserializationException("xsd.unexpectedNull");
/*     */         }
/*     */       } else {
/* 152 */         String val = null;
/*     */         
/* 154 */         switch (reader.getState()) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case 3:
/* 160 */             if (reader instanceof FastInfosetReader && this.encoder instanceof com.sun.xml.rpc.encoding.simpletype.XSDBase64BinaryEncoder) {
/*     */ 
/*     */               
/* 163 */               FastInfosetReader fiReader = (FastInfosetReader)reader;
/* 164 */               obj = fiReader.getTextAlgorithmBytes();
/* 165 */               if (obj != null && fiReader.getTextAlgorithmIndex() == 1) {
/*     */ 
/*     */                 
/* 168 */                 obj = fiReader.getTextAlgorithmBytesClone();
/* 169 */                 reader.next();
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/* 174 */             val = reader.getValue();
/* 175 */             obj = this.encoder.stringToObject(val, reader);
/* 176 */             reader.next();
/*     */             break;
/*     */           case 2:
/* 179 */             val = "";
/* 180 */             obj = this.encoder.stringToObject(val, reader);
/*     */             break;
/*     */         } 
/*     */       
/*     */       } 
/* 185 */       XMLReaderUtil.verifyReaderState(reader, 2);
/*     */       
/* 187 */       return obj;
/* 188 */     } catch (DeserializationException e) {
/* 189 */       throw e;
/* 190 */     } catch (JAXRPCExceptionBase e) {
/* 191 */       throw new DeserializationException(e);
/* 192 */     } catch (Exception e) {
/* 193 */       throw new DeserializationException(new LocalizableExceptionAdapter(e));
/*     */     } finally {
/*     */       
/* 196 */       if (pushedEncodingStyle)
/* 197 */         context.popEncodingStyle(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\SimpleTypeSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */