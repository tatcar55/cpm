/*     */ package com.oracle.webservices.api.databinding;
/*     */ 
/*     */ import com.sun.xml.ws.api.databinding.MetadataReader;
/*     */ import com.sun.xml.ws.model.ExternalMetadataReader;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExternalMetadataFeature
/*     */   extends WebServiceFeature
/*     */ {
/*     */   private static final String ID = "com.oracle.webservices.api.databinding.ExternalMetadataFeature";
/*     */   private boolean enabled = true;
/*     */   private List<String> resourceNames;
/*     */   private List<File> files;
/*     */   
/*     */   public void addResources(String... resourceNames) {
/*  75 */     if (this.resourceNames == null) {
/*  76 */       this.resourceNames = new ArrayList<String>();
/*     */     }
/*  78 */     Collections.addAll(this.resourceNames, resourceNames);
/*     */   }
/*     */   public List<String> getResourceNames() {
/*  81 */     return this.resourceNames;
/*     */   }
/*     */   public void addFiles(File... files) {
/*  84 */     if (this.files == null) {
/*  85 */       this.files = new ArrayList<File>();
/*     */     }
/*  87 */     Collections.addAll(this.files, files);
/*     */   }
/*     */   public List<File> getFiles() {
/*  90 */     return this.files;
/*     */   }
/*     */   public boolean isEnabled() {
/*  93 */     return this.enabled;
/*     */   }
/*     */   
/*     */   private void setEnabled(boolean x) {
/*  97 */     this.enabled = x;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getID() {
/* 102 */     return "com.oracle.webservices.api.databinding.ExternalMetadataFeature";
/*     */   }
/*     */   
/*     */   public MetadataReader getMetadataReader(ClassLoader classLoader) {
/* 106 */     return this.enabled ? (MetadataReader)new ExternalMetadataReader(this.files, this.resourceNames, classLoader, true) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 111 */     if (this == o) return true; 
/* 112 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 114 */     ExternalMetadataFeature that = (ExternalMetadataFeature)o;
/*     */     
/* 116 */     if (this.enabled != that.enabled) return false; 
/* 117 */     if ((this.files != null) ? !this.files.equals(that.files) : (that.files != null)) return false; 
/* 118 */     if ((this.resourceNames != null) ? !this.resourceNames.equals(that.resourceNames) : (that.resourceNames != null)) {
/* 119 */       return false;
/*     */     }
/* 121 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 126 */     int result = this.enabled ? 1 : 0;
/* 127 */     result = 31 * result + ((this.resourceNames != null) ? this.resourceNames.hashCode() : 0);
/* 128 */     result = 31 * result + ((this.files != null) ? this.files.hashCode() : 0);
/* 129 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 134 */     return "[" + getID() + ", enabled=" + this.enabled + ", resourceNames=" + this.resourceNames + ", files=" + this.files + ']';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Builder builder() {
/* 142 */     return new Builder(new ExternalMetadataFeature());
/*     */   }
/*     */   
/*     */   public static final class Builder {
/*     */     private final ExternalMetadataFeature o;
/*     */     
/*     */     Builder(ExternalMetadataFeature x) {
/* 149 */       this.o = x;
/*     */     }
/*     */     
/*     */     public ExternalMetadataFeature build() {
/* 153 */       return this.o;
/*     */     }
/*     */     
/*     */     public Builder addResources(String... res) {
/* 157 */       this.o.addResources(res);
/* 158 */       return this;
/*     */     }
/*     */     
/*     */     public Builder addFiles(File... files) {
/* 162 */       this.o.addFiles(files);
/* 163 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setEnabled(boolean enabled) {
/* 167 */       this.o.setEnabled(enabled);
/* 168 */       return this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\oracle\webservices\api\databinding\ExternalMetadataFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */