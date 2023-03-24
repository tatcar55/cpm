/*     */ package com.sun.xml.ws.transport.tcp.encoding.configurator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum WSTCPCodecConfigurator
/*     */ {
/*     */   private int characterContentChunkMapMemoryLimit;
/*     */   private int attributeValueMapMemoryLimit;
/*     */   private int maxCharacterContentChunkSize;
/*  49 */   INSTANCE; private int minCharacterContentChunkSize;
/*     */   private int maxAttributeValueSize;
/*     */   private int minAttributeValueSize;
/*     */   private SerializerVocabularyFactory serializerVocabularyFactory;
/*     */   private ParserVocabularyFactory parserVocabularyFactory;
/*     */   
/*     */   WSTCPCodecConfigurator() {
/*  56 */     this.documentParserFactory = new DefaultDocumentParserFactory();
/*  57 */     this.documentSerializerFactory = new DefaultDocumentSerializerFactory();
/*     */     
/*  59 */     this.parserVocabularyFactory = new DefaultParserVocabularyFactory();
/*  60 */     this.serializerVocabularyFactory = new DefaultSerializerVocabularyFactory();
/*     */     
/*  62 */     this.minAttributeValueSize = 0;
/*  63 */     this.maxAttributeValueSize = 32;
/*  64 */     this.minCharacterContentChunkSize = 0;
/*  65 */     this.maxCharacterContentChunkSize = 32;
/*  66 */     this.attributeValueMapMemoryLimit = 4194304;
/*  67 */     this.characterContentChunkMapMemoryLimit = 4194304;
/*     */   }
/*     */ 
/*     */   
/*     */   private DocumentSerializerFactory documentSerializerFactory;
/*     */   
/*     */   public DocumentParserFactory getDocumentParserFactory() {
/*  74 */     return this.documentParserFactory;
/*     */   }
/*     */   private DocumentParserFactory documentParserFactory;
/*     */   private static final int INDEXED_STRING_MEMORY_LIMIT = 4194304;
/*     */   private static final int MAX_INDEXED_STRING_SIZE_LIMIT = 32;
/*     */   private static final int MIN_INDEXED_STRING_SIZE_LIMIT = 0;
/*     */   
/*     */   public void setDocumentParserFactory(DocumentParserFactory documentParserFactory) {
/*  82 */     this.documentParserFactory = documentParserFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DocumentSerializerFactory getDocumentSerializerFactory() {
/*  90 */     return this.documentSerializerFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDocumentSerializerFactory(DocumentSerializerFactory documentSerializerFactory) {
/*  98 */     this.documentSerializerFactory = documentSerializerFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParserVocabularyFactory getParserVocabularyFactory() {
/* 106 */     return this.parserVocabularyFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParserVocabularyFactory(ParserVocabularyFactory parserVocabularyFactory) {
/* 114 */     this.parserVocabularyFactory = parserVocabularyFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SerializerVocabularyFactory getSerializerVocabularyFactory() {
/* 122 */     return this.serializerVocabularyFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSerializerVocabularyFactory(SerializerVocabularyFactory serializerVocabularyFactory) {
/* 130 */     this.serializerVocabularyFactory = serializerVocabularyFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinAttributeValueSize() {
/* 141 */     return this.minAttributeValueSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMinAttributeValueSize(int minAttributeValueSize) {
/* 151 */     this.minAttributeValueSize = minAttributeValueSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxAttributeValueSize() {
/* 161 */     return this.maxAttributeValueSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxAttributeValueSize(int maxAttributeValueSize) {
/* 171 */     this.maxAttributeValueSize = maxAttributeValueSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAttributeValueMapMemoryLimit() {
/* 181 */     return this.attributeValueMapMemoryLimit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttributeValueMapMemoryLimit(int attributeValueMapMemoryLimit) {
/* 192 */     this.attributeValueMapMemoryLimit = attributeValueMapMemoryLimit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinCharacterContentChunkSize() {
/* 202 */     return this.minCharacterContentChunkSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMinCharacterContentChunkSize(int minCharacterContentChunkSize) {
/* 212 */     this.minCharacterContentChunkSize = minCharacterContentChunkSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxCharacterContentChunkSize() {
/* 222 */     return this.maxCharacterContentChunkSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxCharacterContentChunkSize(int maxCharacterContentChunkSize) {
/* 232 */     this.maxCharacterContentChunkSize = maxCharacterContentChunkSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCharacterContentChunkMapMemoryLimit() {
/* 242 */     return this.characterContentChunkMapMemoryLimit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCharacterContentChunkMapMemoryLimit(int characterContentChunkMapMemoryLimit) {
/* 253 */     this.characterContentChunkMapMemoryLimit = characterContentChunkMapMemoryLimit;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\encoding\configurator\WSTCPCodecConfigurator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */