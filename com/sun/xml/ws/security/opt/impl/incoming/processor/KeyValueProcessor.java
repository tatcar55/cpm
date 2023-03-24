/*     */ package com.sun.xml.ws.security.opt.impl.incoming.processor;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.util.StreamUtil;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import com.sun.xml.wss.logging.impl.opt.LogStringsMessages;
/*     */ import java.math.BigInteger;
/*     */ import java.security.Key;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PublicKey;
/*     */ import java.security.spec.InvalidKeySpecException;
/*     */ import java.security.spec.RSAPublicKeySpec;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.jvnet.staxex.Base64Data;
/*     */ import org.jvnet.staxex.XMLStreamReaderEx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KeyValueProcessor
/*     */ {
/*  70 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt", "com.sun.xml.wss.logging.impl.opt.LogStrings");
/*     */   
/*     */   private static final int SECURITY_TOKEN_REFERENCE_ELEMENT = 3;
/*     */   
/*     */   private static final int ENCRYPTED_KEY_ELEMENT = 4;
/*     */   
/*     */   private static final int KEY_VALUE_ELEMENT = 5;
/*     */   
/*     */   private static final int RSA_KEY_VALUE_ELEMENT = 6;
/*     */   
/*     */   private static final int DSA_KEY_VALUE_ELEMENT = 7;
/*     */   private static final int MODULUS_ELEMENT = 8;
/*     */   private static final int EXPONENT_ELEMENT = 9;
/*     */   private static final String RSA_KEY_VALUE = "RSAKeyValue";
/*     */   private static final String DSA_KEY_VALUE = "DSAKeyValue";
/*     */   private static final String ENCRYPTED_KEY = "EncryptedKey";
/*     */   private static final String KEY_VALUE = "KeyValue";
/*     */   private static final String EXPONENT = "Exponent";
/*     */   private static final String MODULUS = "Modulus";
/*  89 */   private XMLStreamWriter canonWriter = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyValueProcessor(JAXBFilterProcessingContext pc, XMLStreamWriter writer) {
/*  94 */     this.canonWriter = writer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Key processKeyValue(XMLStreamReader reader) throws XMLStreamException, XWSSecurityException {
/* 104 */     boolean done = false;
/* 105 */     Key retKey = null;
/*     */     
/* 107 */     while (!done && reader.hasNext() && !_breaKeyValue(reader)) {
/* 108 */       reader.next();
/* 109 */       int event = getKeyValueEventType(reader);
/* 110 */       switch (event) {
/*     */         case 6:
/* 112 */           if (this.canonWriter != null) {
/* 113 */             StreamUtil.writeCurrentEvent(reader, this.canonWriter);
/*     */           }
/* 115 */           retKey = processRSAKeyValue(reader);
/*     */           continue;
/*     */         
/*     */         case 7:
/* 119 */           if (this.canonWriter != null) {
/* 120 */             StreamUtil.writeCurrentEvent(reader, this.canonWriter);
/*     */           }
/*     */           continue;
/*     */       } 
/* 124 */       if (_breaKeyValue(reader)) {
/* 125 */         done = true;
/*     */         continue;
/*     */       } 
/* 128 */       if (this.canonWriter != null) {
/* 129 */         StreamUtil.writeCurrentEvent(reader, this.canonWriter);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 134 */     return retKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Key processRSAKeyValue(XMLStreamReader reader) throws XMLStreamException, XWSSecurityException {
/* 145 */     BigInteger modulus = null;
/* 146 */     BigInteger exponent = null;
/* 147 */     boolean done = false;
/* 148 */     while (!done && reader.hasNext() && !_breakRSAKeyValue(reader)) {
/* 149 */       StringBuffer sb; byte[] value; CharSequence charSeq; String dv; reader.next();
/* 150 */       int event = getRSAKVEventType(reader);
/* 151 */       switch (event) {
/*     */         case 8:
/* 153 */           if (this.canonWriter != null) {
/* 154 */             StreamUtil.writeCurrentEvent(reader, this.canonWriter);
/*     */           }
/* 156 */           reader.next();
/* 157 */           sb = null;
/* 158 */           value = null;
/* 159 */           charSeq = ((XMLStreamReaderEx)reader).getPCDATA();
/* 160 */           if (charSeq instanceof Base64Data) {
/* 161 */             Base64Data bd = (Base64Data)((XMLStreamReaderEx)reader).getPCDATA();
/* 162 */             value = bd.getExact();
/* 163 */             modulus = new BigInteger(1, value);
/* 164 */             if (this.canonWriter != null) {
/* 165 */               String ev = Base64.encode(value);
/* 166 */               this.canonWriter.writeCharacters(ev);
/*     */             }  continue;
/*     */           } 
/* 169 */           sb = new StringBuffer();
/*     */ 
/*     */           
/* 172 */           while (reader.getEventType() == 4 && reader.getEventType() != 2) {
/* 173 */             charSeq = ((XMLStreamReaderEx)reader).getPCDATA();
/* 174 */             for (int i = 0; i < charSeq.length(); i++) {
/* 175 */               if (charSeq.charAt(i) != '\n')
/*     */               {
/*     */                 
/* 178 */                 sb.append(charSeq.charAt(i)); } 
/*     */             } 
/* 180 */             reader.next();
/*     */           } 
/* 182 */           dv = sb.toString();
/* 183 */           if (this.canonWriter != null) {
/* 184 */             this.canonWriter.writeCharacters(dv);
/*     */           }
/*     */           try {
/* 187 */             value = Base64.decode(dv);
/* 188 */           } catch (Base64DecodingException dec) {
/* 189 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1606_ERROR_RSAKEYINFO_BASE_64_DECODING("EXPONENT"), dec);
/* 190 */             throw new XWSSecurityException(LogStringsMessages.WSS_1606_ERROR_RSAKEYINFO_BASE_64_DECODING("EXPONENT"));
/*     */           } 
/* 192 */           modulus = new BigInteger(1, value);
/*     */           continue;
/*     */ 
/*     */ 
/*     */         
/*     */         case 9:
/* 198 */           if (this.canonWriter != null) {
/* 199 */             StreamUtil.writeCurrentEvent(reader, this.canonWriter);
/*     */           }
/* 201 */           reader.next();
/* 202 */           sb = null;
/* 203 */           value = null;
/* 204 */           charSeq = ((XMLStreamReaderEx)reader).getPCDATA();
/* 205 */           if (charSeq instanceof Base64Data) {
/* 206 */             Base64Data bd = (Base64Data)((XMLStreamReaderEx)reader).getPCDATA();
/* 207 */             value = bd.getExact();
/* 208 */             exponent = new BigInteger(1, value);
/* 209 */             if (this.canonWriter != null) {
/* 210 */               String ev = Base64.encode(value);
/* 211 */               this.canonWriter.writeCharacters(ev);
/*     */             }  continue;
/*     */           } 
/* 214 */           sb = new StringBuffer();
/*     */           
/* 216 */           while (reader.getEventType() == 4 && reader.getEventType() != 2) {
/* 217 */             charSeq = ((XMLStreamReaderEx)reader).getPCDATA();
/* 218 */             for (int i = 0; i < charSeq.length(); i++) {
/* 219 */               sb.append(charSeq.charAt(i));
/*     */             }
/* 221 */             reader.next();
/*     */           } 
/* 223 */           dv = sb.toString();
/* 224 */           if (this.canonWriter != null) {
/* 225 */             this.canonWriter.writeCharacters(dv);
/*     */           }
/*     */           try {
/* 228 */             value = Base64.decode(dv);
/* 229 */           } catch (Base64DecodingException dec) {
/* 230 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1606_ERROR_RSAKEYINFO_BASE_64_DECODING("EXPONENT"), dec);
/* 231 */             throw new XWSSecurityException(LogStringsMessages.WSS_1606_ERROR_RSAKEYINFO_BASE_64_DECODING("EXPONENT"));
/*     */           } 
/* 233 */           exponent = new BigInteger(1, value);
/*     */           continue;
/*     */       } 
/*     */ 
/*     */       
/* 238 */       if (_breakRSAKeyValue(reader)) {
/* 239 */         done = true;
/*     */         continue;
/*     */       } 
/* 242 */       if (this.canonWriter != null) {
/* 243 */         StreamUtil.writeCurrentEvent(reader, this.canonWriter);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 249 */       KeyFactory rsaFactory = KeyFactory.getInstance("RSA");
/* 250 */       RSAPublicKeySpec rsaKeyspec = new RSAPublicKeySpec(modulus, exponent);
/* 251 */       PublicKey pk = rsaFactory.generatePublic(rsaKeyspec);
/* 252 */       return pk;
/* 253 */     } catch (NoSuchAlgorithmException ex) {
/* 254 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1607_ERROR_RSAPUBLIC_KEY(), ex);
/* 255 */       throw new XWSSecurityException(LogStringsMessages.WSS_1607_ERROR_RSAPUBLIC_KEY(), ex);
/* 256 */     } catch (InvalidKeySpecException ex) {
/* 257 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1607_ERROR_RSAPUBLIC_KEY(), ex);
/* 258 */       throw new XWSSecurityException(LogStringsMessages.WSS_1607_ERROR_RSAPUBLIC_KEY(), ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int getKeyValueEventType(XMLStreamReader reader) throws XMLStreamException {
/* 264 */     if (reader.getEventType() == 1) {
/* 265 */       if (reader.getLocalName() == "RSAKeyValue") {
/* 266 */         return 6;
/*     */       }
/*     */       
/* 269 */       if (reader.getLocalName() == "DSAKeyValue") {
/* 270 */         return 7;
/*     */       }
/*     */     } 
/* 273 */     return -1;
/*     */   }
/*     */   
/*     */   private int getRSAKVEventType(XMLStreamReader reader) throws XMLStreamException {
/* 277 */     if (reader.getEventType() == 1) {
/* 278 */       if (reader.getLocalName() == "Modulus") {
/* 279 */         return 8;
/*     */       }
/*     */       
/* 282 */       if (reader.getLocalName() == "Exponent") {
/* 283 */         return 9;
/*     */       }
/*     */     } 
/* 286 */     return -1;
/*     */   }
/*     */   
/*     */   private boolean isRSAKeyValue(XMLStreamReader reader) throws XMLStreamException {
/* 290 */     if (reader.getLocalName() == "RSAKeyValue") {
/* 291 */       return true;
/*     */     }
/* 293 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isKeyValue(XMLStreamReader reader) throws XMLStreamException {
/* 297 */     if (reader.getLocalName() == "KeyValue") {
/* 298 */       return true;
/*     */     }
/* 300 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _breaKeyValue(XMLStreamReader reader) throws XMLStreamException {
/* 304 */     if (reader.getEventType() == 2 && isKeyValue(reader)) {
/* 305 */       if (this.canonWriter != null) {
/* 306 */         StreamUtil.writeCurrentEvent(reader, this.canonWriter);
/*     */       }
/* 308 */       return true;
/*     */     } 
/* 310 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _breakRSAKeyValue(XMLStreamReader reader) throws XMLStreamException {
/* 314 */     if (reader.getEventType() == 2 && isRSAKeyValue(reader)) {
/* 315 */       if (this.canonWriter != null) {
/* 316 */         StreamUtil.writeCurrentEvent(reader, this.canonWriter);
/*     */       }
/* 318 */       return true;
/*     */     } 
/* 320 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\processor\KeyValueProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */