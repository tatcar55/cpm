/*     */ package com.sun.xml.rpc.encoding.literal;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.DeserializationException;
/*     */ import com.sun.xml.rpc.encoding.SOAPDeserializationContext;
/*     */ import com.sun.xml.rpc.encoding.SOAPSerializationContext;
/*     */ import com.sun.xml.rpc.encoding.SerializationException;
/*     */ import com.sun.xml.rpc.encoding.SerializerBase;
/*     */ import com.sun.xml.rpc.encoding.SerializerCallback;
/*     */ import com.sun.xml.rpc.encoding.simpletype.SimpleTypeConstants;
/*     */ import com.sun.xml.rpc.encoding.simpletype.SimpleTypeEncoder;
/*     */ import com.sun.xml.rpc.streaming.Attributes;
/*     */ import com.sun.xml.rpc.streaming.FastInfosetReader;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLReaderUtil;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
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
/*     */ public class LiteralSimpleTypeSerializer
/*     */   extends SerializerBase
/*     */   implements SimpleTypeConstants
/*     */ {
/*     */   protected SimpleTypeEncoder encoder;
/*     */   
/*     */   public LiteralSimpleTypeSerializer(QName type, String encodingStyle, SimpleTypeEncoder encoder) {
/*  64 */     super(type, false, true, encodingStyle);
/*  65 */     this.encoder = encoder;
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
/*  76 */     boolean pushedEncodingStyle = false;
/*     */     try {
/*  78 */       writer.startElement((name != null) ? name : this.type);
/*  79 */       if (callback != null) {
/*  80 */         callback.onStartTag(obj, name, writer, context);
/*     */       }
/*     */       
/*  83 */       pushedEncodingStyle = context.pushEncodingStyle(this.encodingStyle, writer);
/*     */ 
/*     */       
/*  86 */       if (obj == null) {
/*  87 */         if (!this.isNullable) {
/*  88 */           throw new SerializationException("xsd.unexpectedNull");
/*     */         }
/*     */         
/*  91 */         writer.writeAttributeUnquoted(QNAME_XSI_NIL, "1");
/*     */       } else {
/*  93 */         this.encoder.writeAdditionalNamespaceDeclarations(obj, writer);
/*  94 */         this.encoder.writeValue(obj, writer);
/*     */       } 
/*     */       
/*  97 */       writer.endElement();
/*  98 */     } catch (SerializationException e) {
/*  99 */       throw e;
/* 100 */     } catch (JAXRPCExceptionBase e) {
/* 101 */       throw new SerializationException(e);
/* 102 */     } catch (Exception e) {
/* 103 */       throw new SerializationException(new LocalizableExceptionAdapter(e));
/*     */     } finally {
/*     */       
/* 106 */       if (pushedEncodingStyle) {
/* 107 */         context.popEncodingStyle();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserialize(QName name, XMLReader reader, SOAPDeserializationContext context) throws DeserializationException {
/* 118 */     boolean pushedEncodingStyle = false;
/*     */     try {
/* 120 */       pushedEncodingStyle = context.processEncodingStyle(reader);
/* 121 */       context.verifyEncodingStyle(this.encodingStyle);
/*     */       
/* 123 */       if (name != null) {
/* 124 */         QName actualName = reader.getName();
/* 125 */         if (!actualName.equals(name)) {
/* 126 */           throw new DeserializationException("xsd.unexpectedElementName", new Object[] { name.toString(), actualName.toString() });
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 132 */       Attributes attrs = reader.getAttributes();
/*     */       
/* 134 */       String nullVal = attrs.getValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
/* 135 */       boolean isNull = (nullVal != null && (nullVal.equals("true") || nullVal.equals("1")));
/*     */ 
/*     */ 
/*     */       
/* 139 */       reader.next();
/* 140 */       Object obj = null;
/*     */       
/* 142 */       if (isNull) {
/* 143 */         if (!this.isNullable) {
/* 144 */           throw new DeserializationException("xsd.unexpectedNull");
/*     */         }
/*     */       } else {
/* 147 */         String val = null;
/*     */         
/* 149 */         switch (reader.getState()) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case 3:
/* 155 */             if (reader instanceof FastInfosetReader) {
/* 156 */               FastInfosetReader fiReader = (FastInfosetReader)reader;
/*     */               
/* 158 */               obj = fiReader.getTextAlgorithmBytes();
/* 159 */               if (obj != null && fiReader.getTextAlgorithmIndex() == 1) {
/*     */ 
/*     */                 
/* 162 */                 obj = fiReader.getTextAlgorithmBytesClone();
/* 163 */                 reader.next();
/*     */                 break;
/*     */               } 
/*     */             } 
/* 167 */             val = reader.getValue();
/* 168 */             obj = this.encoder.stringToObject(val, reader);
/* 169 */             reader.next();
/*     */             break;
/*     */           case 2:
/* 172 */             val = "";
/* 173 */             obj = this.encoder.stringToObject(val, reader);
/*     */             break;
/*     */         } 
/*     */ 
/*     */       
/*     */       } 
/* 179 */       XMLReaderUtil.verifyReaderState(reader, 2);
/*     */       
/* 181 */       return obj;
/* 182 */     } catch (DeserializationException e) {
/* 183 */       throw e;
/* 184 */     } catch (JAXRPCExceptionBase e) {
/* 185 */       throw new DeserializationException(e);
/* 186 */     } catch (Exception e) {
/* 187 */       throw new DeserializationException(new LocalizableExceptionAdapter(e));
/*     */     } finally {
/*     */       
/* 190 */       if (pushedEncodingStyle) {
/* 191 */         context.popEncodingStyle();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public SimpleTypeEncoder getEncoder() {
/* 197 */     return this.encoder;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\literal\LiteralSimpleTypeSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */