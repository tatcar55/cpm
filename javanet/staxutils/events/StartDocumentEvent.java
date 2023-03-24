/*     */ package javanet.staxutils.events;
/*     */ 
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.events.StartDocument;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StartDocumentEvent
/*     */   extends AbstractXMLEvent
/*     */   implements StartDocument
/*     */ {
/*     */   public static final String DEFAULT_VERSION = "1.0";
/*     */   public static final String DEFAULT_SYSTEM_ID = "";
/*     */   public static final String DEFAULT_ENCODING = "UTF-8";
/*     */   protected String encoding;
/*     */   protected Boolean standalone;
/*     */   protected String version;
/*     */   
/*     */   public StartDocumentEvent() {}
/*     */   
/*     */   public StartDocumentEvent(Location location) {
/*  74 */     super(location);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public StartDocumentEvent(String encoding, Location location) {
/*  80 */     super(location);
/*  81 */     this.encoding = encoding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StartDocumentEvent(String encoding, Boolean standalone, String version, Location location) {
/*  88 */     super(location);
/*  89 */     this.encoding = encoding;
/*  90 */     this.standalone = standalone;
/*  91 */     this.version = version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StartDocumentEvent(String encoding, Boolean standalone, String version, Location location, QName schemaType) {
/*  98 */     super(location, schemaType);
/*  99 */     this.encoding = encoding;
/* 100 */     this.standalone = standalone;
/* 101 */     this.version = version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StartDocumentEvent(StartDocument that) {
/* 112 */     super(that);
/*     */ 
/*     */     
/* 115 */     if (that.encodingSet())
/*     */     {
/* 117 */       this.encoding = that.getCharacterEncodingScheme();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 122 */     if (standaloneSet())
/*     */     {
/* 124 */       this.standalone = that.isStandalone() ? Boolean.TRUE : Boolean.FALSE;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     this.version = "1.0".equals(that.getVersion()) ? null : that.getVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEventType() {
/* 139 */     return 7;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean encodingSet() {
/* 145 */     return (this.encoding != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCharacterEncodingScheme() {
/* 151 */     return (this.encoding == null) ? "UTF-8" : this.encoding;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSystemId() {
/* 157 */     Location location = getLocation();
/* 158 */     if (location != null) {
/*     */       
/* 160 */       String systemId = location.getSystemId();
/* 161 */       if (systemId != null)
/*     */       {
/* 163 */         return systemId;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 169 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 175 */     return (this.version == null) ? "1.0" : this.version;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStandalone() {
/* 181 */     return (this.standalone == null) ? false : this.standalone.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean standaloneSet() {
/* 187 */     return (this.standalone != null);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\events\StartDocumentEvent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */