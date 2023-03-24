/*     */ package com.sun.xml.fastinfoset.tools;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.CommonResourceBundle;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TransformInputOutput
/*     */ {
/*     */   public void parse(String[] args) throws Exception {
/*  42 */     InputStream in = null;
/*  43 */     OutputStream out = null;
/*  44 */     if (args.length == 0) {
/*  45 */       in = new BufferedInputStream(System.in);
/*  46 */       out = new BufferedOutputStream(System.out);
/*  47 */     } else if (args.length == 1) {
/*  48 */       in = new BufferedInputStream(new FileInputStream(args[0]));
/*  49 */       out = new BufferedOutputStream(System.out);
/*  50 */     } else if (args.length == 2) {
/*  51 */       in = new BufferedInputStream(new FileInputStream(args[0]));
/*  52 */       out = new BufferedOutputStream(new FileOutputStream(args[1]));
/*     */     } else {
/*  54 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.optinalFileNotSpecified"));
/*     */     } 
/*     */     
/*  57 */     parse(in, out);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parse(InputStream in, OutputStream out, String workingDirectory) throws Exception {
/*  65 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  70 */   private static URI currentJavaWorkingDirectory = (new File(System.getProperty("user.dir"))).toURI();
/*     */ 
/*     */   
/*     */   protected static EntityResolver createRelativePathResolver(final String workingDirectory) {
/*  74 */     return new EntityResolver() {
/*     */         public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
/*  76 */           if (systemId != null && systemId.startsWith("file:/")) {
/*  77 */             URI workingDirectoryURI = (new File(workingDirectory)).toURI();
/*     */ 
/*     */             
/*     */             try {
/*  81 */               URI workingFile = TransformInputOutput.convertToNewWorkingDirectory(TransformInputOutput.currentJavaWorkingDirectory, workingDirectoryURI, (new File(new URI(systemId))).toURI());
/*  82 */               return new InputSource(workingFile.toString());
/*  83 */             } catch (URISyntaxException ex) {}
/*     */           } 
/*     */ 
/*     */           
/*  87 */           return null;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private static URI convertToNewWorkingDirectory(URI oldwd, URI newwd, URI file) throws IOException, URISyntaxException {
/*  93 */     String oldwdStr = oldwd.toString();
/*  94 */     String newwdStr = newwd.toString();
/*  95 */     String fileStr = file.toString();
/*     */     
/*  97 */     String cmpStr = null;
/*     */     
/*  99 */     if (fileStr.startsWith(oldwdStr) && (cmpStr = fileStr.substring(oldwdStr.length())).indexOf('/') == -1) {
/* 100 */       return new URI(newwdStr + '/' + cmpStr);
/*     */     }
/*     */     
/* 103 */     String[] oldwdSplit = oldwdStr.split("/");
/* 104 */     String[] newwdSplit = newwdStr.split("/");
/* 105 */     String[] fileSplit = fileStr.split("/");
/*     */ 
/*     */     
/* 108 */     int diff = 0; while (true) { if ((((diff < oldwdSplit.length) ? 1 : 0) & ((diff < fileSplit.length) ? 1 : 0)) == 0 || 
/* 109 */         !oldwdSplit[diff].equals(fileSplit[diff])) {
/*     */         break;
/*     */       }
/*     */       diff++; }
/*     */     
/*     */     int diffNew;
/* 115 */     for (diffNew = 0; diffNew < newwdSplit.length && diffNew < fileSplit.length && 
/* 116 */       newwdSplit[diffNew].equals(fileSplit[diffNew]); diffNew++);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 123 */     if (diffNew > diff) {
/* 124 */       return file;
/*     */     }
/*     */     
/* 127 */     int elemsToSub = oldwdSplit.length - diff;
/* 128 */     StringBuffer resultStr = new StringBuffer(100); int i;
/* 129 */     for (i = 0; i < newwdSplit.length - elemsToSub; i++) {
/* 130 */       resultStr.append(newwdSplit[i]);
/* 131 */       resultStr.append('/');
/*     */     } 
/*     */     
/* 134 */     for (i = diff; i < fileSplit.length; i++) {
/* 135 */       resultStr.append(fileSplit[i]);
/* 136 */       if (i < fileSplit.length - 1) resultStr.append('/');
/*     */     
/*     */     } 
/* 139 */     return new URI(resultStr.toString());
/*     */   }
/*     */   
/*     */   public abstract void parse(InputStream paramInputStream, OutputStream paramOutputStream) throws Exception;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\tools\TransformInputOutput.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */