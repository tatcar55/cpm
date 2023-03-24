/*     */ package com.sun.xml.ws.security.opt.impl.enc;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.algorithms.JCEMapper;
/*     */ import com.sun.xml.ws.security.opt.crypto.JAXBData;
/*     */ import com.sun.xml.ws.security.opt.crypto.StreamWriterData;
/*     */ import com.sun.xml.ws.security.opt.impl.util.OutputStreamWrapper;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*     */ import com.sun.xml.wss.impl.c14n.StAXEXC14nCanonicalizerImpl;
/*     */ import com.sun.xml.wss.logging.impl.opt.crypto.LogStringsMessages;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Key;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.crypto.BadPaddingException;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.CipherInputStream;
/*     */ import javax.crypto.CipherOutputStream;
/*     */ import javax.crypto.IllegalBlockSizeException;
/*     */ import javax.crypto.NoSuchPaddingException;
/*     */ import javax.crypto.spec.IvParameterSpec;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.jvnet.staxex.NamespaceContextEx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CryptoProcessor
/*     */ {
/*  83 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.crypto", "com.sun.xml.wss.logging.impl.opt.crypto.LogStrings");
/*     */ 
/*     */   
/*  86 */   protected Cipher cipher = null;
/*  87 */   protected Key key = null;
/*  88 */   protected Data data = null;
/*  89 */   private int mode = 1;
/*  90 */   private String algorithm = "";
/*  91 */   private Key dk = null;
/*  92 */   private byte[] ed = null;
/*  93 */   private IvParameterSpec ivSpec = null;
/*  94 */   private byte[] encryptedDataCV = null;
/*     */   
/*     */   public CryptoProcessor() {}
/*     */   
/*     */   public CryptoProcessor(int mode, String algo, Data ed, Key key) throws XWSSecurityException {
/*  99 */     this.mode = mode;
/* 100 */     this.algorithm = algo;
/* 101 */     this.data = ed;
/* 102 */     this.key = key;
/*     */   }
/*     */   
/*     */   public CryptoProcessor(int mode, String algo, Key dk, Key key) throws XWSSecurityException {
/* 106 */     this.mode = mode;
/* 107 */     this.algorithm = algo;
/* 108 */     this.key = key;
/* 109 */     this.dk = dk;
/*     */   }
/*     */   
/*     */   public CryptoProcessor(int mode, String algo, Key key) throws XWSSecurityException {
/* 113 */     this.mode = mode;
/* 114 */     this.algorithm = algo;
/* 115 */     this.key = key;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initCipher() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
/* 125 */     if (this.cipher == null) {
/* 126 */       String transformation = convertAlgURIToTransformation(getAlgorithm());
/* 127 */       this.cipher = Cipher.getInstance(transformation);
/* 128 */       this.cipher.init(this.mode, getKey());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String getAlgorithm() {
/* 133 */     return this.algorithm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String convertAlgURIToTransformation(String algorithmURI) {
/* 142 */     return JCEMapper.translateURItoJCEID(algorithmURI);
/*     */   }
/*     */   
/*     */   protected Key getKey() {
/* 146 */     return this.key;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encrypt(OutputStream outputStream) throws IOException {
/* 155 */     if (this.mode == 1) {
/* 156 */       encryptData(outputStream);
/* 157 */     } else if (this.mode == 3) {
/* 158 */       encryptKey(outputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getCipherValueOfEK() {
/*     */     try {
/* 167 */       if (this.ed == null) {
/* 168 */         if (this.cipher == null) {
/* 169 */           initCipher();
/*     */         }
/* 171 */         this.ed = this.cipher.wrap(this.dk);
/*     */       } 
/* 173 */     } catch (NoSuchAlgorithmException ex) {
/* 174 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1904_UNSUPPORTED_KEYENCRYPTION_ALGORITHM(getAlgorithm()), ex);
/* 175 */       throw new XWSSecurityRuntimeException(ex);
/* 176 */     } catch (NoSuchPaddingException ex) {
/* 177 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1905_ERROR_INITIALIZING_CIPHER(), ex);
/* 178 */       throw new XWSSecurityRuntimeException(ex);
/* 179 */     } catch (InvalidKeyException ex) {
/* 180 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1906_INVALID_KEY_ERROR(), ex);
/* 181 */       throw new XWSSecurityRuntimeException(ex);
/* 182 */     } catch (IllegalBlockSizeException ibe) {
/* 183 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1907_INCORRECT_BLOCK_SIZE(), ibe);
/* 184 */       throw new XWSSecurityRuntimeException(ibe);
/*     */     } 
/* 186 */     return this.ed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encryptKey(OutputStream outputStream) throws IOException {
/*     */     try {
/* 195 */       if (this.ed == null) {
/* 196 */         if (this.cipher == null) {
/* 197 */           initCipher();
/*     */         }
/* 199 */         this.ed = this.cipher.wrap(this.dk);
/*     */       } 
/*     */       
/* 202 */       outputStream.write(this.ed);
/* 203 */       outputStream.flush();
/*     */     }
/* 205 */     catch (NoSuchAlgorithmException ex) {
/* 206 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1904_UNSUPPORTED_KEYENCRYPTION_ALGORITHM(getAlgorithm()), ex);
/* 207 */       throw new XWSSecurityRuntimeException("Unable to compute CipherValue as " + getAlgorithm() + " is not supported", ex);
/* 208 */     } catch (NoSuchPaddingException ex) {
/* 209 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1905_ERROR_INITIALIZING_CIPHER(), ex);
/* 210 */       throw new XWSSecurityRuntimeException("Error occurred while initializing the Cipher", ex);
/* 211 */     } catch (InvalidKeyException ex) {
/* 212 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1906_INVALID_KEY_ERROR(), ex);
/* 213 */       throw new XWSSecurityRuntimeException("Unable to calculate cipher value as invalid key was provided", ex);
/* 214 */     } catch (IllegalBlockSizeException ibe) {
/* 215 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1907_INCORRECT_BLOCK_SIZE(), ibe);
/* 216 */       throw new XWSSecurityRuntimeException(ibe);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setEncryptedDataCV(byte[] cv) {
/* 221 */     this.encryptedDataCV = cv;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encryptData(byte[] cipherInput) {
/*     */     try {
/* 231 */       if (this.cipher == null) {
/* 232 */         initCipher();
/*     */       }
/*     */       
/* 235 */       byte[] cipherOutput = this.cipher.doFinal(cipherInput);
/* 236 */       byte[] iv = this.cipher.getIV();
/* 237 */       byte[] encryptedBytes = new byte[iv.length + cipherOutput.length];
/* 238 */       System.arraycopy(iv, 0, encryptedBytes, 0, iv.length);
/* 239 */       System.arraycopy(cipherOutput, 0, encryptedBytes, iv.length, cipherOutput.length);
/* 240 */       return encryptedBytes;
/* 241 */     } catch (NoSuchAlgorithmException ex) {
/* 242 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1909_UNSUPPORTED_DATAENCRYPTION_ALGORITHM(getAlgorithm()), ex);
/* 243 */       throw new XWSSecurityRuntimeException("Unable to compute CipherValue as " + getAlgorithm() + " is not supported", ex);
/* 244 */     } catch (NoSuchPaddingException ex) {
/* 245 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1905_ERROR_INITIALIZING_CIPHER(), ex);
/* 246 */       throw new XWSSecurityRuntimeException("Error occurred while initializing the Cipher", ex);
/* 247 */     } catch (InvalidKeyException ex) {
/* 248 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1906_INVALID_KEY_ERROR(), ex);
/* 249 */       throw new XWSSecurityRuntimeException("Unable to calculate cipher value as invalid key was provided", ex);
/* 250 */     } catch (IllegalBlockSizeException ibse) {
/* 251 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1915_INVALID_ALGORITHM_PARAMETERS(getAlgorithm()), ibse);
/* 252 */       throw new XWSSecurityRuntimeException(ibse);
/* 253 */     } catch (BadPaddingException bpe) {
/* 254 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1915_INVALID_ALGORITHM_PARAMETERS(getAlgorithm()), bpe);
/* 255 */       throw new XWSSecurityRuntimeException(bpe);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encryptData(OutputStream eos) throws IOException {
/*     */     try {
/* 265 */       OutputStreamWrapper outputStream = new OutputStreamWrapper(eos);
/* 266 */       if (this.encryptedDataCV != null) {
/* 267 */         outputStream.write(this.encryptedDataCV);
/*     */         
/*     */         return;
/*     */       } 
/* 271 */       if (this.cipher == null) {
/* 272 */         initCipher();
/*     */       }
/*     */ 
/*     */       
/* 276 */       CipherOutputStream cos = new CipherOutputStream((OutputStream)outputStream, this.cipher);
/*     */       
/* 278 */       byte[] iv = this.cipher.getIV();
/* 279 */       outputStream.write(iv);
/* 280 */       outputStream.flush();
/* 281 */       if (this.data instanceof JAXBData) {
/* 282 */         ((JAXBData)this.data).writeTo(cos);
/* 283 */       } else if (this.data instanceof StreamWriterData) {
/* 284 */         StAXEXC14nCanonicalizerImpl stAXEXC14nCanonicalizerImpl = new StAXEXC14nCanonicalizerImpl();
/*     */         
/* 286 */         NamespaceContextEx nsEx = ((StreamWriterData)this.data).getNamespaceContext();
/* 287 */         Iterator<NamespaceContextEx.Binding> iter = nsEx.iterator();
/* 288 */         while (iter.hasNext()) {
/* 289 */           NamespaceContextEx.Binding binding = iter.next();
/* 290 */           stAXEXC14nCanonicalizerImpl.writeNamespace(binding.getPrefix(), binding.getNamespaceURI());
/*     */         } 
/* 292 */         if (logger.isLoggable(Level.FINEST)) {
/* 293 */           stAXEXC14nCanonicalizerImpl.setStream(new ByteArrayOutputStream());
/*     */         } else {
/* 295 */           stAXEXC14nCanonicalizerImpl.setStream(cos);
/*     */         } 
/*     */         try {
/* 298 */           ((StreamWriterData)this.data).write((XMLStreamWriter)stAXEXC14nCanonicalizerImpl);
/* 299 */           if (logger.isLoggable(Level.FINEST)) {
/* 300 */             byte[] cd = ((ByteArrayOutputStream)stAXEXC14nCanonicalizerImpl.getOutputStream()).toByteArray();
/* 301 */             logger.log(Level.FINEST, LogStringsMessages.WSS_1951_ENCRYPTED_DATA_VALUE(new String(cd)));
/* 302 */             cos.write(cd);
/*     */           } 
/* 304 */         } catch (XMLStreamException ex) {
/* 305 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1908_ERROR_WRITING_ENCRYPTEDDATA(), ex);
/*     */         } 
/*     */       } 
/*     */       
/* 309 */       cos.flush();
/* 310 */       cos.close();
/* 311 */     } catch (NoSuchAlgorithmException ex) {
/* 312 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1909_UNSUPPORTED_DATAENCRYPTION_ALGORITHM(getAlgorithm()), ex);
/* 313 */       throw new XWSSecurityRuntimeException("Unable to compute CipherValue as " + getAlgorithm() + " is not supported", ex);
/* 314 */     } catch (NoSuchPaddingException ex) {
/* 315 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1905_ERROR_INITIALIZING_CIPHER(), ex);
/* 316 */       throw new XWSSecurityRuntimeException("Error occurred while initializing the Cipher", ex);
/* 317 */     } catch (InvalidKeyException ex) {
/* 318 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1906_INVALID_KEY_ERROR(), ex);
/* 319 */       throw new XWSSecurityRuntimeException("Unable to calculate cipher value as invalid key was provided", ex);
/* 320 */     } catch (XMLStreamException xse) {
/* 321 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1910_ERROR_WRITING_NAMESPACES_CANONICALIZER(xse.getMessage()), xse);
/* 322 */       throw new XWSSecurityRuntimeException("Unable to write namespaces to exclusive canonicalizer", xse);
/* 323 */     } catch (XWSSecurityException ex) {
/* 324 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1911_ERROR_WRITING_CIPHERVALUE(ex.getMessage()), (Throwable)ex);
/* 325 */       throw new XWSSecurityRuntimeException("Unable to calculate cipher value ", ex);
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
/*     */   public Key decryptKey(byte[] encryptedKey, String encAlgo) throws IOException {
/*     */     try {
/* 338 */       if (this.mode == 4) {
/* 339 */         if (this.algorithm == null || this.algorithm.length() == 0) {
/* 340 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1912_DECRYPTION_ALGORITHM_NULL());
/* 341 */           throw new IOException("Cannot decrypt a key without knowing the algorithm");
/*     */         } 
/*     */         
/* 344 */         if (this.key == null) {
/* 345 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1913_DECRYPTION_KEY_NULL());
/* 346 */           throw new IOException("Key used to decrypt EncryptedKey cannot be null");
/*     */         } 
/* 348 */         if (this.cipher == null) {
/* 349 */           initCipher();
/*     */         }
/* 351 */         return this.cipher.unwrap(encryptedKey, JCEMapper.getJCEKeyAlgorithmFromURI(encAlgo), 3);
/*     */       }
/*     */     
/* 354 */     } catch (InvalidKeyException ex) {
/* 355 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1906_INVALID_KEY_ERROR(), ex);
/* 356 */       throw new XWSSecurityRuntimeException(ex);
/* 357 */     } catch (NoSuchAlgorithmException ex) {
/* 358 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1904_UNSUPPORTED_KEYENCRYPTION_ALGORITHM(this.algorithm), ex);
/* 359 */       throw new XWSSecurityRuntimeException(ex);
/* 360 */     } catch (NoSuchPaddingException ex) {
/* 361 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1905_ERROR_INITIALIZING_CIPHER(), ex);
/* 362 */       throw new XWSSecurityRuntimeException(ex);
/*     */     } 
/* 364 */     logger.log(Level.SEVERE, LogStringsMessages.WSS_1914_INVALID_CIPHER_MODE(Integer.valueOf(this.mode)));
/* 365 */     throw new IOException("Invalid Cipher mode:" + this.mode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream decryptData(InputStream is) throws IOException {
/*     */     try {
/* 375 */       if (this.mode == 2) {
/* 376 */         if (this.cipher == null) {
/* 377 */           String transformation = convertAlgURIToTransformation(getAlgorithm());
/* 378 */           this.cipher = Cipher.getInstance(transformation);
/* 379 */           int len = this.cipher.getBlockSize();
/* 380 */           byte[] iv = new byte[len];
/* 381 */           is.read(iv, 0, len);
/* 382 */           this.ivSpec = new IvParameterSpec(iv);
/* 383 */           this.cipher.init(this.mode, this.key, this.ivSpec);
/*     */         } 
/* 385 */         return new CipherInputStream(is, this.cipher);
/*     */       } 
/* 387 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1914_INVALID_CIPHER_MODE(Integer.valueOf(this.mode)), "Invalid Cipher mode:" + this.mode);
/* 388 */       throw new IOException("Invalid Cipher mode:" + this.mode);
/*     */     
/*     */     }
/* 391 */     catch (InvalidKeyException ex) {
/* 392 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1906_INVALID_KEY_ERROR(), ex);
/* 393 */       throw new XWSSecurityRuntimeException(ex);
/* 394 */     } catch (NoSuchAlgorithmException ex) {
/* 395 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1909_UNSUPPORTED_DATAENCRYPTION_ALGORITHM(getAlgorithm()), ex);
/* 396 */       throw new XWSSecurityRuntimeException(ex);
/* 397 */     } catch (NoSuchPaddingException ex) {
/* 398 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1905_ERROR_INITIALIZING_CIPHER(), ex);
/* 399 */       throw new XWSSecurityRuntimeException(ex);
/* 400 */     } catch (InvalidAlgorithmParameterException invalidAPE) {
/* 401 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1915_INVALID_ALGORITHM_PARAMETERS(getAlgorithm()), invalidAPE);
/* 402 */       throw new XWSSecurityRuntimeException(invalidAPE);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decryptData(byte[] encryptedContent) throws IOException {
/*     */     try {
/* 414 */       if (this.mode == 2) {
/*     */         
/* 416 */         String transformation = convertAlgURIToTransformation(getAlgorithm());
/* 417 */         this.cipher = Cipher.getInstance(transformation);
/* 418 */         int len = this.cipher.getBlockSize();
/* 419 */         byte[] iv = new byte[len];
/* 420 */         System.arraycopy(encryptedContent, 0, iv, 0, len);
/* 421 */         this.ivSpec = new IvParameterSpec(iv);
/* 422 */         this.cipher.init(this.mode, this.key, this.ivSpec);
/* 423 */         return this.cipher.doFinal(encryptedContent, len, encryptedContent.length - len);
/*     */       } 
/* 425 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1914_INVALID_CIPHER_MODE(Integer.valueOf(this.mode)));
/* 426 */       throw new IOException("Invalid Cipher mode:" + this.mode);
/*     */     }
/* 428 */     catch (InvalidKeyException ex) {
/* 429 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1906_INVALID_KEY_ERROR(), ex);
/* 430 */       throw new XWSSecurityRuntimeException(ex);
/* 431 */     } catch (NoSuchAlgorithmException ex) {
/* 432 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1909_UNSUPPORTED_DATAENCRYPTION_ALGORITHM(getAlgorithm()), ex);
/* 433 */       throw new XWSSecurityRuntimeException(ex);
/* 434 */     } catch (NoSuchPaddingException ex) {
/* 435 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1905_ERROR_INITIALIZING_CIPHER(), ex);
/* 436 */       throw new XWSSecurityRuntimeException(ex);
/* 437 */     } catch (InvalidAlgorithmParameterException invalidAPE) {
/* 438 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1915_INVALID_ALGORITHM_PARAMETERS(getAlgorithm()), invalidAPE);
/* 439 */       throw new XWSSecurityRuntimeException(invalidAPE);
/* 440 */     } catch (IllegalBlockSizeException ibse) {
/* 441 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1915_INVALID_ALGORITHM_PARAMETERS(getAlgorithm()), ibse);
/* 442 */       throw new XWSSecurityRuntimeException(ibse);
/* 443 */     } catch (BadPaddingException bpe) {
/* 444 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1915_INVALID_ALGORITHM_PARAMETERS(getAlgorithm()), bpe);
/* 445 */       throw new XWSSecurityRuntimeException(bpe);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\enc\CryptoProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */