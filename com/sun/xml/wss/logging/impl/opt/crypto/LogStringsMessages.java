/*     */ package com.sun.xml.wss.logging.impl.opt.crypto;
/*     */ 
/*     */ import com.sun.istack.localization.Localizable;
/*     */ import com.sun.istack.localization.LocalizableMessageFactory;
/*     */ import com.sun.istack.localization.Localizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class LogStringsMessages
/*     */ {
/*  15 */   private static final LocalizableMessageFactory messageFactory = new LocalizableMessageFactory("com.sun.xml.wss.logging.impl.opt.crypto.LogStrings");
/*  16 */   private static final Localizer localizer = new Localizer();
/*     */   
/*     */   public static Localizable localizableWSS_1950_DATAENCRYPTION_ALGORITHM_NOTSET() {
/*  19 */     return messageFactory.getMessage("WSS1950.dataencryption.algorithm.notset", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1950_DATAENCRYPTION_ALGORITHM_NOTSET() {
/*  27 */     return localizer.localize(localizableWSS_1950_DATAENCRYPTION_ALGORITHM_NOTSET());
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSS_1919_ERROR_WRITING_ENCRYPTEDDATA(Object arg0) {
/*  31 */     return messageFactory.getMessage("WSS1919.error.writing.encrypteddata", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1919_ERROR_WRITING_ENCRYPTEDDATA(Object arg0) {
/*  39 */     return localizer.localize(localizableWSS_1919_ERROR_WRITING_ENCRYPTEDDATA(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSS_1916_ERROR_WRITING_ECRYPTEDHEADER(Object arg0) {
/*  43 */     return messageFactory.getMessage("WSS1916.error.writing.ecryptedheader", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1916_ERROR_WRITING_ECRYPTEDHEADER(Object arg0) {
/*  51 */     return localizer.localize(localizableWSS_1916_ERROR_WRITING_ECRYPTEDHEADER(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSS_1915_INVALID_ALGORITHM_PARAMETERS(Object arg0) {
/*  55 */     return messageFactory.getMessage("WSS1915.invalid.algorithm.parameters", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1915_INVALID_ALGORITHM_PARAMETERS(Object arg0) {
/*  63 */     return localizer.localize(localizableWSS_1915_INVALID_ALGORITHM_PARAMETERS(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSS_1928_UNRECOGNIZED_CIPHERTEXT_TRANSFORM(Object arg0) {
/*  67 */     return messageFactory.getMessage("WSS1928.unrecognized.ciphertext.transform", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1928_UNRECOGNIZED_CIPHERTEXT_TRANSFORM(Object arg0) {
/*  75 */     return localizer.localize(localizableWSS_1928_UNRECOGNIZED_CIPHERTEXT_TRANSFORM(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSS_1903_UNSUPPORTED_KEYBINDING_ENCRYPTIONPOLICY(Object arg0) {
/*  79 */     return messageFactory.getMessage("WSS1903.unsupported.keybinding.encryptionpolicy", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1903_UNSUPPORTED_KEYBINDING_ENCRYPTIONPOLICY(Object arg0) {
/*  87 */     return localizer.localize(localizableWSS_1903_UNSUPPORTED_KEYBINDING_ENCRYPTIONPOLICY(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSS_1924_CIPHERVAL_MISSINGIN_CIPHERDATA() {
/*  91 */     return messageFactory.getMessage("WSS1924.cipherval.missingin.cipherdata", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1924_CIPHERVAL_MISSINGIN_CIPHERDATA() {
/*  99 */     return localizer.localize(localizableWSS_1924_CIPHERVAL_MISSINGIN_CIPHERDATA());
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSS_1907_INCORRECT_BLOCK_SIZE() {
/* 103 */     return messageFactory.getMessage("WSS1907.incorrect.block.size", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1907_INCORRECT_BLOCK_SIZE() {
/* 111 */     return localizer.localize(localizableWSS_1907_INCORRECT_BLOCK_SIZE());
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSS_1908_ERROR_WRITING_ENCRYPTEDDATA() {
/* 115 */     return messageFactory.getMessage("WSS1908.error.writing.encrypteddata", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1908_ERROR_WRITING_ENCRYPTEDDATA() {
/* 123 */     return localizer.localize(localizableWSS_1908_ERROR_WRITING_ENCRYPTEDDATA());
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSS_1910_ERROR_WRITING_NAMESPACES_CANONICALIZER(Object arg0) {
/* 127 */     return messageFactory.getMessage("WSS1910.error.writing.namespaces.canonicalizer", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1910_ERROR_WRITING_NAMESPACES_CANONICALIZER(Object arg0) {
/* 135 */     return localizer.localize(localizableWSS_1910_ERROR_WRITING_NAMESPACES_CANONICALIZER(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSS_1914_INVALID_CIPHER_MODE(Object arg0) {
/* 139 */     return messageFactory.getMessage("WSS1914.invalid.cipher.mode", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1914_INVALID_CIPHER_MODE(Object arg0) {
/* 147 */     return localizer.localize(localizableWSS_1914_INVALID_CIPHER_MODE(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSS_1918_ILLEGAL_ENCRYPTION_TARGET(Object arg0, Object arg1) {
/* 151 */     return messageFactory.getMessage("WSS1918.illegal.encryption.target", new Object[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1918_ILLEGAL_ENCRYPTION_TARGET(Object arg0, Object arg1) {
/* 159 */     return localizer.localize(localizableWSS_1918_ILLEGAL_ENCRYPTION_TARGET(arg0, arg1));
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSS_1912_DECRYPTION_ALGORITHM_NULL() {
/* 163 */     return messageFactory.getMessage("WSS1912.decryption.algorithm.null", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1912_DECRYPTION_ALGORITHM_NULL() {
/* 171 */     return localizer.localize(localizableWSS_1912_DECRYPTION_ALGORITHM_NULL());
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSS_1925_EMPTY_ENCMETHOD_ED() {
/* 175 */     return messageFactory.getMessage("WSS1925.empty.encmethod.ed", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1925_EMPTY_ENCMETHOD_ED() {
/* 183 */     return localizer.localize(localizableWSS_1925_EMPTY_ENCMETHOD_ED());
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSS_1917_CRLF_INIT_FAILED() {
/* 187 */     return messageFactory.getMessage("WSS1917.crlf.init.failed", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1917_CRLF_INIT_FAILED() {
/* 195 */     return localizer.localize(localizableWSS_1917_CRLF_INIT_FAILED());
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSS_1927_ERROR_DECRYPT_ED(Object arg0) {
/* 199 */     return messageFactory.getMessage("WSS1927.error.decrypt.ed", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1927_ERROR_DECRYPT_ED(Object arg0) {
/* 207 */     return localizer.localize(localizableWSS_1927_ERROR_DECRYPT_ED(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSS_1921_ERROR_WRITING_ENCRYPTEDKEY(Object arg0) {
/* 211 */     return messageFactory.getMessage("WSS1921.error.writing.encryptedkey", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1921_ERROR_WRITING_ENCRYPTEDKEY(Object arg0) {
/* 219 */     return localizer.localize(localizableWSS_1921_ERROR_WRITING_ENCRYPTEDKEY(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSS_1951_ENCRYPTED_DATA_VALUE(Object arg0) {
/* 223 */     return messageFactory.getMessage("WSS1951.encrypted.data.value", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1951_ENCRYPTED_DATA_VALUE(Object arg0) {
/* 231 */     return localizer.localize(localizableWSS_1951_ENCRYPTED_DATA_VALUE(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSS_1902_UNSUPPORTED_USERNAMETOKEN_KEYBINDING() {
/* 235 */     return messageFactory.getMessage("WSS1902.unsupported.usernametoken.keybinding", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1902_UNSUPPORTED_USERNAMETOKEN_KEYBINDING() {
/* 243 */     return localizer.localize(localizableWSS_1902_UNSUPPORTED_USERNAMETOKEN_KEYBINDING());
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSS_1920_ERROR_CALCULATING_CIPHERVALUE() {
/* 247 */     return messageFactory.getMessage("WSS1920.error.calculating.ciphervalue", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1920_ERROR_CALCULATING_CIPHERVALUE() {
/* 255 */     return localizer.localize(localizableWSS_1920_ERROR_CALCULATING_CIPHERVALUE());
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSS_1906_INVALID_KEY_ERROR() {
/* 259 */     return messageFactory.getMessage("WSS1906.invalid.key.error", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1906_INVALID_KEY_ERROR() {
/* 267 */     return localizer.localize(localizableWSS_1906_INVALID_KEY_ERROR());
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSS_1905_ERROR_INITIALIZING_CIPHER() {
/* 271 */     return messageFactory.getMessage("WSS1905.error.initializing.cipher", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1905_ERROR_INITIALIZING_CIPHER() {
/* 279 */     return localizer.localize(localizableWSS_1905_ERROR_INITIALIZING_CIPHER());
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSS_1913_DECRYPTION_KEY_NULL() {
/* 283 */     return messageFactory.getMessage("WSS1913.decryption.key.null", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1913_DECRYPTION_KEY_NULL() {
/* 291 */     return localizer.localize(localizableWSS_1913_DECRYPTION_KEY_NULL());
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSS_1911_ERROR_WRITING_CIPHERVALUE(Object arg0) {
/* 295 */     return messageFactory.getMessage("WSS1911.error.writing.ciphervalue", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1911_ERROR_WRITING_CIPHERVALUE(Object arg0) {
/* 303 */     return localizer.localize(localizableWSS_1911_ERROR_WRITING_CIPHERVALUE(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSS_1923_ERROR_PROCESSING_CIPHERVAL(Object arg0) {
/* 307 */     return messageFactory.getMessage("WSS1923.error.processing.cipherval", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1923_ERROR_PROCESSING_CIPHERVAL(Object arg0) {
/* 315 */     return localizer.localize(localizableWSS_1923_ERROR_PROCESSING_CIPHERVAL(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSS_1909_UNSUPPORTED_DATAENCRYPTION_ALGORITHM(Object arg0) {
/* 319 */     return messageFactory.getMessage("WSS1909.unsupported.dataencryption.algorithm", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1909_UNSUPPORTED_DATAENCRYPTION_ALGORITHM(Object arg0) {
/* 327 */     return localizer.localize(localizableWSS_1909_UNSUPPORTED_DATAENCRYPTION_ALGORITHM(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSS_1922_ERROR_DECODING_CIPHERVAL(Object arg0) {
/* 331 */     return messageFactory.getMessage("WSS1922.error.decoding.cipherval", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1922_ERROR_DECODING_CIPHERVAL(Object arg0) {
/* 339 */     return localizer.localize(localizableWSS_1922_ERROR_DECODING_CIPHERVAL(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSS_1952_ENCRYPTION_KEYBINDING_VALUE(Object arg0) {
/* 343 */     return messageFactory.getMessage("WSS1952.encryption.keybinding.value", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1952_ENCRYPTION_KEYBINDING_VALUE(Object arg0) {
/* 351 */     return localizer.localize(localizableWSS_1952_ENCRYPTION_KEYBINDING_VALUE(arg0));
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSS_1926_ED_KEY_NOTSET() {
/* 355 */     return messageFactory.getMessage("WSS1926.ed.key.notset", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1926_ED_KEY_NOTSET() {
/* 363 */     return localizer.localize(localizableWSS_1926_ED_KEY_NOTSET());
/*     */   }
/*     */   
/*     */   public static Localizable localizableWSS_1904_UNSUPPORTED_KEYENCRYPTION_ALGORITHM(Object arg0) {
/* 367 */     return messageFactory.getMessage("WSS1904.unsupported.keyencryption.algorithm", new Object[] { arg0 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String WSS_1904_UNSUPPORTED_KEYENCRYPTION_ALGORITHM(Object arg0) {
/* 375 */     return localizer.localize(localizableWSS_1904_UNSUPPORTED_KEYENCRYPTION_ALGORITHM(arg0));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\logging\impl\opt\crypto\LogStringsMessages.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */