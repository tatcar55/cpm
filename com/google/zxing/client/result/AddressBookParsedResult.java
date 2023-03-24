/*     */ package com.google.zxing.client.result;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AddressBookParsedResult
/*     */   extends ParsedResult
/*     */ {
/*     */   private final String[] names;
/*     */   private final String[] nicknames;
/*     */   private final String pronunciation;
/*     */   private final String[] phoneNumbers;
/*     */   private final String[] phoneTypes;
/*     */   private final String[] emails;
/*     */   private final String[] emailTypes;
/*     */   private final String instantMessenger;
/*     */   private final String note;
/*     */   private final String[] addresses;
/*     */   private final String[] addressTypes;
/*     */   private final String org;
/*     */   private final String birthday;
/*     */   private final String title;
/*     */   private final String[] urls;
/*     */   private final String[] geo;
/*     */   
/*     */   public AddressBookParsedResult(String[] names, String[] phoneNumbers, String[] phoneTypes, String[] emails, String[] emailTypes, String[] addresses, String[] addressTypes) {
/*  48 */     this(names, (String[])null, (String)null, phoneNumbers, phoneTypes, emails, emailTypes, (String)null, (String)null, addresses, addressTypes, (String)null, (String)null, (String)null, (String[])null, (String[])null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AddressBookParsedResult(String[] names, String[] nicknames, String pronunciation, String[] phoneNumbers, String[] phoneTypes, String[] emails, String[] emailTypes, String instantMessenger, String note, String[] addresses, String[] addressTypes, String org, String birthday, String title, String[] urls, String[] geo) {
/*  82 */     super(ParsedResultType.ADDRESSBOOK);
/*  83 */     this.names = names;
/*  84 */     this.nicknames = nicknames;
/*  85 */     this.pronunciation = pronunciation;
/*  86 */     this.phoneNumbers = phoneNumbers;
/*  87 */     this.phoneTypes = phoneTypes;
/*  88 */     this.emails = emails;
/*  89 */     this.emailTypes = emailTypes;
/*  90 */     this.instantMessenger = instantMessenger;
/*  91 */     this.note = note;
/*  92 */     this.addresses = addresses;
/*  93 */     this.addressTypes = addressTypes;
/*  94 */     this.org = org;
/*  95 */     this.birthday = birthday;
/*  96 */     this.title = title;
/*  97 */     this.urls = urls;
/*  98 */     this.geo = geo;
/*     */   }
/*     */   
/*     */   public String[] getNames() {
/* 102 */     return this.names;
/*     */   }
/*     */   
/*     */   public String[] getNicknames() {
/* 106 */     return this.nicknames;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPronunciation() {
/* 116 */     return this.pronunciation;
/*     */   }
/*     */   
/*     */   public String[] getPhoneNumbers() {
/* 120 */     return this.phoneNumbers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getPhoneTypes() {
/* 128 */     return this.phoneTypes;
/*     */   }
/*     */   
/*     */   public String[] getEmails() {
/* 132 */     return this.emails;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getEmailTypes() {
/* 140 */     return this.emailTypes;
/*     */   }
/*     */   
/*     */   public String getInstantMessenger() {
/* 144 */     return this.instantMessenger;
/*     */   }
/*     */   
/*     */   public String getNote() {
/* 148 */     return this.note;
/*     */   }
/*     */   
/*     */   public String[] getAddresses() {
/* 152 */     return this.addresses;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getAddressTypes() {
/* 160 */     return this.addressTypes;
/*     */   }
/*     */   
/*     */   public String getTitle() {
/* 164 */     return this.title;
/*     */   }
/*     */   
/*     */   public String getOrg() {
/* 168 */     return this.org;
/*     */   }
/*     */   
/*     */   public String[] getURLs() {
/* 172 */     return this.urls;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBirthday() {
/* 179 */     return this.birthday;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getGeo() {
/* 186 */     return this.geo;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDisplayResult() {
/* 191 */     StringBuilder result = new StringBuilder(100);
/* 192 */     maybeAppend(this.names, result);
/* 193 */     maybeAppend(this.nicknames, result);
/* 194 */     maybeAppend(this.pronunciation, result);
/* 195 */     maybeAppend(this.title, result);
/* 196 */     maybeAppend(this.org, result);
/* 197 */     maybeAppend(this.addresses, result);
/* 198 */     maybeAppend(this.phoneNumbers, result);
/* 199 */     maybeAppend(this.emails, result);
/* 200 */     maybeAppend(this.instantMessenger, result);
/* 201 */     maybeAppend(this.urls, result);
/* 202 */     maybeAppend(this.birthday, result);
/* 203 */     maybeAppend(this.geo, result);
/* 204 */     maybeAppend(this.note, result);
/* 205 */     return result.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\result\AddressBookParsedResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */