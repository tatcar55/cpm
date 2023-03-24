/*     */ package org.glassfish.ha.store.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import org.glassfish.ha.store.annotations.Attribute;
/*     */ import org.glassfish.ha.store.api.Storeable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleMetadata
/*     */   implements Storeable
/*     */ {
/*  51 */   private long version = -1L;
/*     */   
/*     */   private long lastAccessTime;
/*     */   
/*     */   private long maxInactiveInterval;
/*     */   
/*     */   private byte[] state;
/*     */   
/*  59 */   private static final String[] attributeNames = new String[] { "state" };
/*     */   
/*  61 */   private static final boolean[] dirtyStatus = new boolean[] { true };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleMetadata() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleMetadata(long version, long lastAccesstime, long maxInactiveInterval, byte[] state) {
/*  83 */     this.version = version;
/*  84 */     this.lastAccessTime = lastAccesstime;
/*  85 */     this.maxInactiveInterval = maxInactiveInterval;
/*  86 */     this.state = state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getVersion() {
/*  95 */     return this.version;
/*     */   }
/*     */   
/*     */   public void setVersion(long version) {
/*  99 */     this.version = version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLastAccessTime() {
/* 110 */     return this.lastAccessTime;
/*     */   }
/*     */   
/*     */   public void setLastAccessTime(long lastAccessTime) {
/* 114 */     this.lastAccessTime = lastAccessTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMaxInactiveInterval() {
/* 125 */     return this.maxInactiveInterval;
/*     */   }
/*     */   
/*     */   public void setMaxInactiveInterval(long maxInactiveInterval) {
/* 129 */     this.maxInactiveInterval = maxInactiveInterval;
/*     */   }
/*     */   
/*     */   public byte[] getState() {
/* 133 */     return this.state;
/*     */   }
/*     */   
/*     */   @Attribute("state")
/*     */   public void setState(byte[] state) {
/* 138 */     this.state = state;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 143 */     StringBuilder sb = new StringBuilder("SimpleMetadata->state");
/* 144 */     if (this.state != null) {
/* 145 */       for (byte b : this.state) {
/* 146 */         sb.append(b + "_");
/*     */       }
/*     */     } else {
/* 149 */       sb.append("null");
/*     */     } 
/* 151 */     return "SimpleMetadata{version=" + this.version + ", lastAccessTime=" + this.lastAccessTime + ", maxInactiveInterval=" + this.maxInactiveInterval + ", state.length=" + ((this.state == null) ? 0 : this.state.length) + ", state=" + sb.toString() + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long _storeable_getVersion() {
/* 161 */     return this.version;
/*     */   }
/*     */   
/*     */   public void _storeable_setVersion(long val) {
/* 165 */     this.version = val;
/*     */   }
/*     */   
/*     */   public long _storeable_getLastAccessTime() {
/* 169 */     return this.lastAccessTime;
/*     */   }
/*     */   
/*     */   public void _storeable_setLastAccessTime(long val) {
/* 173 */     this.lastAccessTime = val;
/*     */   }
/*     */   
/*     */   public long _storeable_getMaxIdleTime() {
/* 177 */     return this.maxInactiveInterval;
/*     */   }
/*     */   
/*     */   public void _storeable_setMaxIdleTime(long val) {
/* 181 */     this.maxInactiveInterval = val;
/*     */   }
/*     */   
/*     */   public String[] _storeable_getAttributeNames() {
/* 185 */     return attributeNames;
/*     */   }
/*     */   
/*     */   public boolean[] _storeable_getDirtyStatus() {
/* 189 */     return dirtyStatus;
/*     */   }
/*     */   
/*     */   public void _storeable_writeState(OutputStream os) throws IOException {
/* 193 */     ObjectOutputStream oos = new ObjectOutputStream(os);
/*     */     try {
/* 195 */       oos.writeInt(this.state.length);
/* 196 */       oos.write(this.state);
/*     */     } finally {
/*     */       try {
/* 199 */         oos.close();
/* 200 */       } catch (Exception ex) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void _storeable_readState(InputStream is) throws IOException {
/* 206 */     ObjectInputStream ois = new ObjectInputStream(is);
/*     */     try {
/* 208 */       int len = ois.readInt();
/* 209 */       this.state = new byte[len];
/* 210 */       ois.readFully(this.state);
/*     */     } finally {
/*     */       try {
/* 213 */         ois.close();
/* 214 */       } catch (Exception ex) {}
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\ha\stor\\util\SimpleMetadata.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */