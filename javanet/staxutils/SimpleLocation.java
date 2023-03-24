/*     */ package javanet.staxutils;
/*     */ 
/*     */ import javax.xml.stream.Location;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleLocation
/*     */   implements ExtendedLocation, StaticLocation
/*     */ {
/*     */   private Location nestedLocation;
/*  50 */   private int lineNumber = -1;
/*     */ 
/*     */   
/*  53 */   private int characterOffset = -1;
/*     */ 
/*     */   
/*  56 */   private int columnNumber = -1;
/*     */ 
/*     */   
/*     */   private String publicId;
/*     */ 
/*     */   
/*     */   private String systemId;
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleLocation(String publicId, String systemId, int lineNumber, Location nestedLocation) {
/*  67 */     this.publicId = publicId;
/*  68 */     this.systemId = systemId;
/*  69 */     this.lineNumber = lineNumber;
/*  70 */     this.nestedLocation = nestedLocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleLocation(String publicId, String systemId, int lineNumber, int columnNumber, Location nestedLocation) {
/*  77 */     this.publicId = publicId;
/*  78 */     this.systemId = systemId;
/*  79 */     this.lineNumber = lineNumber;
/*  80 */     this.columnNumber = columnNumber;
/*  81 */     this.nestedLocation = nestedLocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleLocation(String publicId, String systemId, int lineNumber, int columnNumber, int characterOffset, Location nestedLocation) {
/*  88 */     this.publicId = publicId;
/*  89 */     this.systemId = systemId;
/*  90 */     this.lineNumber = lineNumber;
/*  91 */     this.columnNumber = columnNumber;
/*  92 */     this.characterOffset = characterOffset;
/*  93 */     this.nestedLocation = nestedLocation;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleLocation(Location loc) {
/*  99 */     this.publicId = loc.getPublicId();
/* 100 */     this.systemId = loc.getSystemId();
/* 101 */     this.lineNumber = loc.getLineNumber();
/* 102 */     this.columnNumber = loc.getColumnNumber();
/* 103 */     this.characterOffset = loc.getCharacterOffset();
/* 104 */     if (loc instanceof ExtendedLocation)
/*     */     {
/* 106 */       this.nestedLocation = ((ExtendedLocation)loc).getNestedLocation();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCharacterOffset() {
/* 114 */     return this.characterOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnNumber() {
/* 120 */     return this.columnNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLineNumber() {
/* 126 */     return this.lineNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPublicId() {
/* 132 */     return this.publicId;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSystemId() {
/* 138 */     return this.systemId;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Location getNestedLocation() {
/* 144 */     return this.nestedLocation;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 150 */     StringBuffer buffer = new StringBuffer();
/*     */     
/* 152 */     String publicId = getPublicId();
/* 153 */     String systemId = getSystemId();
/* 154 */     if (publicId != null) {
/*     */       
/* 156 */       buffer.append(publicId);
/* 157 */       if (systemId != null)
/*     */       {
/* 159 */         buffer.append("#").append(systemId);
/*     */       
/*     */       }
/*     */     }
/* 163 */     else if (systemId != null) {
/*     */       
/* 165 */       buffer.append(publicId);
/*     */     } 
/*     */ 
/*     */     
/* 169 */     buffer.append('[');
/* 170 */     buffer.append("line=").append(getLineNumber());
/* 171 */     buffer.append("column=").append(getColumnNumber());
/* 172 */     buffer.append(']');
/*     */     
/* 174 */     Location nested = getNestedLocation();
/* 175 */     if (nested != null) {
/*     */       
/* 177 */       buffer.append("->");
/* 178 */       buffer.append(nested);
/*     */     } 
/*     */ 
/*     */     
/* 182 */     return buffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\SimpleLocation.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */