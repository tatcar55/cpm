/*     */ package com.sun.xml.wss.provider.wsit;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.security.auth.message.config.AuthConfigFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RegStoreFileParser
/*     */ {
/*  69 */   private static Logger logger = Logger.getLogger("com.sun.xml.wss.provider.wsit", "com.sun.xml.wss.provider.wsit.logging.LogStrings");
/*     */   
/*     */   private static final String SEP = ":";
/*     */   
/*     */   private static final String CON_ENTRY = "con-entry";
/*     */   
/*     */   private static final String REG_ENTRY = "reg-entry";
/*     */   private static final String REG_CTX = "reg-ctx";
/*     */   private static final String LAYER = "layer";
/*     */   private static final String APP_CTX = "app-ctx";
/*     */   private static final String DESCRIPTION = "description";
/*  80 */   private static final String[] INDENT = new String[] { "", "  ", "    " };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private File confFile;
/*     */ 
/*     */ 
/*     */   
/*     */   private List<EntryInfo> entries;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   RegStoreFileParser(String pathParent, String pathChild, boolean create) {
/*     */     try {
/*  96 */       this.confFile = new File(pathParent, pathChild);
/*  97 */       if (this.confFile.exists()) {
/*  98 */         loadEntries();
/*     */       }
/* 100 */       else if (create) {
/* 101 */         synchronized (this.confFile) {
/* 102 */           this.entries = JMACAuthConfigFactory.getDefaultProviders();
/* 103 */           writeEntries();
/*     */         }
/*     */       
/* 106 */       } else if (logger.isLoggable(Level.FINER)) {
/* 107 */         logger.log(Level.FINER, "jmac.factory_file_not_found", pathParent + File.pathSeparator + pathChild);
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 112 */     catch (IOException ioe) {
/* 113 */       logWarningDefault(ioe);
/* 114 */     } catch (IllegalArgumentException iae) {
/* 115 */       logWarningDefault(iae);
/*     */     } 
/*     */ 
/*     */     
/* 119 */     if (this.entries == null) {
/* 120 */       this.entries = JMACAuthConfigFactory.getDefaultProviders();
/*     */     }
/*     */   }
/*     */   
/*     */   private void logWarningUpdated(Exception exception) {
/* 125 */     if (logger.isLoggable(Level.WARNING)) {
/* 126 */       logger.log(Level.WARNING, "jmac.factory_could_not_persist", exception.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void logWarningDefault(Exception exception) {
/* 132 */     if (logger.isLoggable(Level.WARNING)) {
/* 133 */       logger.log(Level.WARNING, "jmac.factory_could_not_read", exception.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   List<EntryInfo> getPersistedEntries() {
/* 142 */     return this.entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void store(String className, AuthConfigFactory.RegistrationContext ctx, Map<String, String> properties) {
/* 151 */     synchronized (this) {
/* 152 */       if (checkAndAddToList(className, ctx, properties)) {
/*     */         try {
/* 154 */           writeEntries();
/* 155 */         } catch (IOException ioe) {
/* 156 */           logWarningUpdated(ioe);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void delete(AuthConfigFactory.RegistrationContext ctx) {
/* 168 */     synchronized (this) {
/* 169 */       if (checkAndRemoveFromList(ctx)) {
/*     */         try {
/* 171 */           writeEntries();
/* 172 */         } catch (IOException ioe) {
/* 173 */           logWarningUpdated(ioe);
/*     */         } 
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
/*     */ 
/*     */   
/*     */   private boolean checkAndAddToList(String className, AuthConfigFactory.RegistrationContext ctx, Map<String, String> props) {
/* 188 */     if (props != null && props.isEmpty()) {
/* 189 */       props = null;
/*     */     }
/* 191 */     EntryInfo newEntry = new EntryInfo(className, props, ctx);
/* 192 */     EntryInfo entry = getMatchingRegEntry(newEntry);
/*     */ 
/*     */     
/* 195 */     if (entry == null) {
/* 196 */       this.entries.add(newEntry);
/* 197 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 201 */     if (entry.isConstructorEntry()) {
/* 202 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 206 */     if (entry.getRegContexts().contains(ctx)) {
/* 207 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 211 */     entry.getRegContexts().add(new RegistrationContextImpl(ctx));
/* 212 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkAndRemoveFromList(AuthConfigFactory.RegistrationContext target) {
/* 223 */     boolean retValue = false;
/* 224 */     for (EntryInfo info : this.entries) {
/* 225 */       if (info.isConstructorEntry()) {
/*     */         continue;
/*     */       }
/*     */       
/* 229 */       Iterator<AuthConfigFactory.RegistrationContext> iter = info.getRegContexts().iterator();
/*     */       
/* 231 */       while (iter.hasNext()) {
/* 232 */         AuthConfigFactory.RegistrationContext ctx = iter.next();
/* 233 */         if (ctx.equals(target)) {
/* 234 */           iter.remove();
/* 235 */           retValue = true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 239 */     return retValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EntryInfo getMatchingRegEntry(EntryInfo target) {
/* 248 */     for (EntryInfo info : this.entries) {
/* 249 */       if (info.equals(target)) {
/* 250 */         return info;
/*     */       }
/*     */     } 
/* 253 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeEntries() throws IOException {
/* 261 */     if (!this.confFile.canWrite() && logger.isLoggable(Level.WARNING)) {
/* 262 */       logger.log(Level.WARNING, "jmac.factory_cannot_write_file", this.confFile.getPath());
/*     */     }
/*     */     
/* 265 */     clearExistingFile();
/* 266 */     PrintWriter out = new PrintWriter(this.confFile);
/* 267 */     int indent = 0;
/* 268 */     for (EntryInfo info : this.entries) {
/* 269 */       if (info.isConstructorEntry()) {
/* 270 */         writeConEntry(info, out, indent); continue;
/*     */       } 
/* 272 */       writeRegEntry(info, out, indent);
/*     */     } 
/*     */     
/* 275 */     out.close();
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
/*     */   private void writeConEntry(EntryInfo info, PrintWriter out, int i) {
/* 293 */     out.println(INDENT[i++] + "con-entry" + " {");
/* 294 */     out.println(INDENT[i] + info.getClassName());
/* 295 */     Map<String, String> props = info.getProperties();
/* 296 */     if (props != null) {
/* 297 */       for (String key : props.keySet()) {
/* 298 */         out.println(INDENT[i] + key + ":" + (String)props.get(key));
/*     */       }
/*     */     }
/* 301 */     out.println(INDENT[--i] + "}");
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
/*     */   private void writeRegEntry(EntryInfo info, PrintWriter out, int i) {
/* 318 */     out.println(INDENT[i++] + "reg-entry" + " {");
/* 319 */     if (info.getClassName() != null) {
/* 320 */       writeConEntry(info, out, i);
/*     */     }
/* 322 */     for (AuthConfigFactory.RegistrationContext ctx : info.getRegContexts()) {
/* 323 */       out.println(INDENT[i++] + "reg-ctx" + " {");
/* 324 */       if (ctx.getMessageLayer() != null) {
/* 325 */         out.println(INDENT[i] + "layer" + ":" + ctx.getMessageLayer());
/*     */       }
/* 327 */       if (ctx.getAppContext() != null) {
/* 328 */         out.println(INDENT[i] + "app-ctx" + ":" + ctx.getAppContext());
/*     */       }
/* 330 */       if (ctx.getDescription() != null) {
/* 331 */         out.println(INDENT[i] + "description" + ":" + ctx.getDescription());
/*     */       }
/*     */       
/* 334 */       out.println(INDENT[--i] + "}");
/*     */     } 
/* 336 */     out.println(INDENT[--i] + "}");
/*     */   }
/*     */   
/*     */   private void clearExistingFile() throws IOException {
/* 340 */     if (this.confFile.exists()) {
/* 341 */       this.confFile.delete();
/*     */     }
/* 343 */     this.confFile.createNewFile();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadEntries() throws IOException {
/* 352 */     this.entries = new ArrayList<EntryInfo>();
/* 353 */     BufferedReader reader = new BufferedReader(new FileReader(this.confFile));
/* 354 */     String line = reader.readLine();
/* 355 */     while (line != null) {
/* 356 */       String trimLine = line.trim();
/* 357 */       if (trimLine.startsWith("con-entry")) {
/* 358 */         this.entries.add(readConEntry(reader));
/* 359 */       } else if (trimLine.startsWith("reg-entry")) {
/* 360 */         this.entries.add(readRegEntry(reader));
/*     */       } 
/* 362 */       line = reader.readLine();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private EntryInfo readConEntry(BufferedReader reader) throws IOException {
/* 368 */     String line = reader.readLine();
/* 369 */     String className = (line != null) ? line.trim() : null;
/* 370 */     Map<String, String> properties = readProperties(reader);
/* 371 */     return new EntryInfo(className, properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<String, String> readProperties(BufferedReader reader) throws IOException {
/* 382 */     String nextLine = reader.readLine();
/* 383 */     String line = (nextLine != null) ? nextLine.trim() : null;
/* 384 */     if (line != null && line.equals("}")) {
/* 385 */       return null;
/*     */     }
/* 387 */     Map<String, String> properties = new HashMap<String, String>();
/* 388 */     while (line != null && !line.equals("}")) {
/* 389 */       properties.put(line.substring(0, line.indexOf(":")), line.substring(line.indexOf(":") + 1, line.length()));
/*     */       
/* 391 */       line = reader.readLine().trim();
/*     */     } 
/* 393 */     return properties;
/*     */   }
/*     */   
/*     */   private EntryInfo readRegEntry(BufferedReader reader) throws IOException {
/* 397 */     String className = null;
/* 398 */     Map<String, String> properties = null;
/* 399 */     List<AuthConfigFactory.RegistrationContext> ctxs = new ArrayList<AuthConfigFactory.RegistrationContext>();
/*     */     
/* 401 */     String nextLine = reader.readLine();
/* 402 */     String line = (nextLine != null) ? nextLine.trim() : null;
/* 403 */     while (line != null && !line.equals("}")) {
/* 404 */       if (line.startsWith("con-entry")) {
/* 405 */         EntryInfo conEntry = readConEntry(reader);
/* 406 */         className = conEntry.getClassName();
/* 407 */         properties = conEntry.getProperties();
/* 408 */       } else if (line.startsWith("reg-ctx")) {
/* 409 */         ctxs.add(readRegContext(reader));
/*     */       } 
/* 411 */       line = reader.readLine().trim();
/*     */     } 
/* 413 */     return new EntryInfo(className, properties, ctxs);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private AuthConfigFactory.RegistrationContext readRegContext(BufferedReader reader) throws IOException {
/* 419 */     String layer = null;
/* 420 */     String appCtx = null;
/* 421 */     String description = null;
/* 422 */     String nextLine = reader.readLine();
/* 423 */     String line = (nextLine != null) ? nextLine.trim() : null;
/* 424 */     while (line != null && !line.equals("}")) {
/* 425 */       String value = line.substring(line.indexOf(":") + 1, line.length());
/*     */       
/* 427 */       if (line.startsWith("layer")) {
/* 428 */         layer = value;
/* 429 */       } else if (line.startsWith("app-ctx")) {
/* 430 */         appCtx = value;
/* 431 */       } else if (line.startsWith("description")) {
/* 432 */         description = value;
/*     */       } 
/* 434 */       line = reader.readLine().trim();
/*     */     } 
/* 436 */     return new RegistrationContextImpl(layer, appCtx, description, true);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\wsit\RegStoreFileParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */