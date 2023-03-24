/*     */ package com.sun.xml.ws.security.opt.impl.incoming.processor;
/*     */ 
/*     */ import javax.xml.stream.StreamFilter;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UsernameTokenProcessor
/*     */   implements StreamFilter
/*     */ {
/*  54 */   String username = null;
/*  55 */   String password = null;
/*  56 */   String passwordDigest = null;
/*  57 */   String passwordType = null;
/*  58 */   String nonce = null;
/*  59 */   String created = null;
/*     */   String Iteration;
/*     */   String Iterations;
/*     */   String Salt;
/*  63 */   String currentElement = "";
/*     */   
/*  65 */   private static String USERNAME = "Username".intern();
/*  66 */   private static String PASSWORD = "Password".intern();
/*  67 */   private static String NONCE = "Nonce".intern();
/*  68 */   private static String CREATED = "Created".intern();
/*  69 */   private static String SALT = "Salt".intern();
/*  70 */   private static String ITERATION = "Iteration".intern();
/*  71 */   private static String ITERATIONS = "Iterations".intern();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean accept(XMLStreamReader reader) {
/*  83 */     if (reader.getEventType() == 1)
/*     */     {
/*  85 */       if ("Username".equals(reader.getLocalName())) {
/*  86 */         this.currentElement = USERNAME;
/*  87 */       } else if ("Password".equals(reader.getLocalName())) {
/*  88 */         this.currentElement = PASSWORD;
/*  89 */         this.passwordType = reader.getAttributeValue(null, "Type");
/*  90 */       } else if ("Nonce".equals(reader.getLocalName())) {
/*  91 */         this.currentElement = NONCE;
/*  92 */       } else if ("Created".equals(reader.getLocalName())) {
/*  93 */         this.currentElement = CREATED;
/*  94 */       } else if ("Salt".equals(reader.getLocalName())) {
/*  95 */         this.currentElement = SALT;
/*  96 */       } else if ("Iteration".equals(reader.getLocalName())) {
/*  97 */         this.currentElement = ITERATION;
/*  98 */       } else if ("Iterations".equals(reader.getLocalName())) {
/*  99 */         this.currentElement = ITERATIONS;
/*     */       } 
/*     */     }
/*     */     
/* 103 */     if (reader.getEventType() == 4) {
/* 104 */       if (this.currentElement == USERNAME) {
/* 105 */         this.username = reader.getText();
/* 106 */         this.currentElement = "";
/* 107 */       } else if (this.currentElement == PASSWORD) {
/* 108 */         if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest".equals(this.passwordType)) {
/* 109 */           this.passwordDigest = reader.getText();
/*     */         } else {
/* 111 */           this.password = reader.getText();
/*     */         } 
/* 113 */         this.currentElement = "";
/* 114 */       } else if (this.currentElement == NONCE) {
/* 115 */         this.nonce = reader.getText();
/* 116 */         this.currentElement = "";
/* 117 */       } else if (this.currentElement == CREATED) {
/* 118 */         this.created = reader.getText();
/* 119 */         this.currentElement = "";
/* 120 */       } else if (this.currentElement == SALT) {
/* 121 */         this.Salt = reader.getText();
/* 122 */         this.currentElement = "";
/* 123 */       } else if (this.currentElement == ITERATION) {
/* 124 */         this.Iteration = reader.getText();
/* 125 */         this.currentElement = "";
/* 126 */       } else if (this.currentElement == ITERATIONS) {
/* 127 */         this.Iterations = reader.getText();
/* 128 */         this.currentElement = "";
/*     */       } 
/*     */     }
/*     */     
/* 132 */     return true;
/*     */   }
/*     */   
/*     */   public String getUsername() {
/* 136 */     return this.username;
/*     */   }
/*     */   
/*     */   public String getPassword() {
/* 140 */     return this.password;
/*     */   }
/*     */   
/*     */   public String getPasswordDigest() {
/* 144 */     return this.passwordDigest;
/*     */   }
/*     */   
/*     */   public String getPasswordType() {
/* 148 */     return this.passwordType;
/*     */   }
/*     */   
/*     */   public String getNonce() {
/* 152 */     return this.nonce;
/*     */   }
/*     */   
/*     */   public String getCreated() {
/* 156 */     return this.created;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSalt() {
/* 163 */     return this.Salt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIterations() {
/* 170 */     if (this.Iteration != null) {
/* 171 */       return this.Iteration;
/*     */     }
/*     */     
/* 174 */     return this.Iterations;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\processor\UsernameTokenProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */