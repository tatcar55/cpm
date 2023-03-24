/*     */ package com.sun.xml.rpc.sp;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Resolver
/*     */   implements EntityResolver
/*     */ {
/*     */   private boolean ignoringMIME;
/*     */   private Map id2uri;
/*     */   private Map id2resource;
/*     */   private Map id2loader;
/* 115 */   private static final String[] types = new String[] { "application/xml", "text/xml", "text/plain", "text/html", "application/x-netcdf", "content/unknown" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InputSource createInputSource(String contentType, InputStream stream, boolean checkType, String scheme) throws IOException {
/* 167 */     String charset = null;
/*     */     
/* 169 */     if (contentType != null) {
/*     */ 
/*     */       
/* 172 */       contentType = contentType.toLowerCase();
/* 173 */       int index = contentType.indexOf(';');
/* 174 */       if (index != -1) {
/*     */ 
/*     */         
/* 177 */         String attributes = contentType.substring(index + 1);
/* 178 */         contentType = contentType.substring(0, index);
/*     */ 
/*     */         
/* 181 */         index = attributes.indexOf("charset");
/* 182 */         if (index != -1) {
/* 183 */           attributes = attributes.substring(index + 7);
/*     */           
/* 185 */           if ((index = attributes.indexOf(';')) != -1) {
/* 186 */             attributes = attributes.substring(0, index);
/*     */           }
/* 188 */           if ((index = attributes.indexOf('=')) != -1) {
/* 189 */             attributes = attributes.substring(index + 1);
/*     */             
/* 191 */             if ((index = attributes.indexOf('(')) != -1) {
/* 192 */               attributes = attributes.substring(0, index);
/*     */             }
/* 194 */             if ((index = attributes.indexOf('"')) != -1) {
/* 195 */               attributes = attributes.substring(index + 1);
/* 196 */               attributes = attributes.substring(0, attributes.indexOf('"'));
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 201 */             charset = attributes.trim();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 210 */       if (checkType) {
/* 211 */         boolean isOK = false;
/* 212 */         for (int i = 0; i < types.length; i++) {
/* 213 */           if (types[i].equals(contentType)) {
/* 214 */             isOK = true; break;
/*     */           } 
/*     */         } 
/* 217 */         if (!isOK) {
/* 218 */           throw new IOException("Not XML: " + contentType);
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 227 */       if (charset == null) {
/* 228 */         contentType = contentType.trim();
/* 229 */         if (contentType.startsWith("text/") && 
/* 230 */           !"file".equalsIgnoreCase(scheme)) {
/* 231 */           charset = "US-ASCII";
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 237 */     InputSource retval = new InputSource(XmlReader.createReader(stream, charset));
/* 238 */     retval.setByteStream(stream);
/* 239 */     retval.setEncoding(charset);
/* 240 */     return retval;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InputSource createInputSource(URL uri, boolean checkType) throws IOException {
/*     */     InputSource retval;
/* 252 */     URLConnection conn = uri.openConnection();
/*     */ 
/*     */     
/* 255 */     if (checkType) {
/* 256 */       String contentType = conn.getContentType();
/* 257 */       retval = createInputSource(contentType, conn.getInputStream(), false, uri.getProtocol());
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 264 */       retval = new InputSource(XmlReader.createReader(conn.getInputStream()));
/*     */     } 
/*     */     
/* 267 */     retval.setSystemId(conn.getURL().toString());
/* 268 */     return retval;
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
/*     */   public static InputSource createInputSource(File file) throws IOException {
/* 281 */     InputSource retval = new InputSource(XmlReader.createReader(new FileInputStream(file)));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 286 */     String path = file.getAbsolutePath();
/* 287 */     if (File.separatorChar != '/')
/* 288 */       path = path.replace(File.separatorChar, '/'); 
/* 289 */     if (!path.startsWith("/"))
/* 290 */       path = "/" + path; 
/* 291 */     if (!path.endsWith("/") && file.isDirectory()) {
/* 292 */       path = path + "/";
/*     */     }
/* 294 */     retval.setSystemId("file:" + path);
/* 295 */     return retval;
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
/*     */   public InputSource resolveEntity(String name, String uri) throws IOException, SAXException {
/*     */     InputSource retval;
/* 319 */     String mappedURI = name2uri(name);
/*     */     
/*     */     InputStream stream;
/*     */     
/* 323 */     if (mappedURI == null && (stream = mapResource(name)) != null) {
/* 324 */       uri = "java:resource:" + (String)this.id2resource.get(name);
/* 325 */       retval = new InputSource(XmlReader.createReader(stream));
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 332 */       if (mappedURI != null) {
/* 333 */         uri = mappedURI;
/* 334 */       } else if (uri == null) {
/* 335 */         return null;
/*     */       } 
/* 337 */       URL url = new URL(uri);
/* 338 */       URLConnection conn = url.openConnection();
/* 339 */       uri = conn.getURL().toString();
/*     */       
/* 341 */       if (this.ignoringMIME) {
/* 342 */         retval = new InputSource(XmlReader.createReader(conn.getInputStream()));
/*     */       }
/*     */       else {
/*     */         
/* 346 */         String contentType = conn.getContentType();
/* 347 */         retval = createInputSource(contentType, conn.getInputStream(), false, url.getProtocol());
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 355 */     retval.setSystemId(uri);
/* 356 */     retval.setPublicId(name);
/* 357 */     return retval;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIgnoringMIME() {
/* 366 */     return this.ignoringMIME;
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
/*     */   public void setIgnoringMIME(boolean value) {
/* 380 */     this.ignoringMIME = value;
/*     */   }
/*     */ 
/*     */   
/*     */   private String name2uri(String publicId) {
/* 385 */     if (publicId == null || this.id2uri == null)
/* 386 */       return null; 
/* 387 */     return (String)this.id2uri.get(publicId);
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
/*     */   public void registerCatalogEntry(String publicId, String uri) {
/* 401 */     if (this.id2uri == null)
/* 402 */       this.id2uri = new HashMap<Object, Object>(17); 
/* 403 */     this.id2uri.put(publicId, uri);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private InputStream mapResource(String publicId) {
/* 409 */     if (publicId == null || this.id2resource == null) {
/* 410 */       return null;
/*     */     }
/* 412 */     String resourceName = (String)this.id2resource.get(publicId);
/* 413 */     ClassLoader loader = null;
/*     */     
/* 415 */     if (resourceName == null) {
/* 416 */       return null;
/*     */     }
/*     */     
/* 419 */     if (this.id2loader != null) {
/* 420 */       loader = (ClassLoader)this.id2loader.get(publicId);
/*     */     }
/* 422 */     if (loader == null)
/* 423 */       return ClassLoader.getSystemResourceAsStream(resourceName); 
/* 424 */     return loader.getResourceAsStream(resourceName);
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
/*     */   public void registerCatalogEntry(String publicId, String resourceName, ClassLoader loader) {
/* 447 */     if (this.id2resource == null)
/* 448 */       this.id2resource = new HashMap<Object, Object>(17); 
/* 449 */     this.id2resource.put(publicId, resourceName);
/*     */     
/* 451 */     if (loader != null) {
/* 452 */       if (this.id2loader == null)
/* 453 */         this.id2loader = new HashMap<Object, Object>(17); 
/* 454 */       this.id2loader.put(publicId, loader);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\sp\Resolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */