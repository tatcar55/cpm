/*     */ package com.ctc.wstx.api;
/*     */ 
/*     */ import com.ctc.wstx.util.DataUtil;
/*     */ import java.util.HashMap;
/*     */ import org.codehaus.stax2.XMLStreamProperties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class CommonConfig
/*     */   implements XMLStreamProperties
/*     */ {
/*     */   protected static final String IMPL_NAME = "woodstox";
/*     */   protected static final String IMPL_VERSION = "4.1";
/*     */   static final int PROP_IMPL_NAME = 1;
/*     */   static final int PROP_IMPL_VERSION = 2;
/*     */   static final int PROP_SUPPORTS_XML11 = 3;
/*     */   static final int PROP_SUPPORT_XMLID = 4;
/*     */   static final int PROP_RETURN_NULL_FOR_DEFAULT_NAMESPACE = 5;
/*  59 */   static final HashMap sStdProperties = new HashMap(16);
/*     */   
/*     */   static {
/*  62 */     sStdProperties.put("org.codehaus.stax2.implName", DataUtil.Integer(1));
/*     */     
/*  64 */     sStdProperties.put("org.codehaus.stax2.implVersion", DataUtil.Integer(2));
/*     */ 
/*     */ 
/*     */     
/*  68 */     sStdProperties.put("org.codehaus.stax2.supportsXml11", DataUtil.Integer(3));
/*     */ 
/*     */ 
/*     */     
/*  72 */     sStdProperties.put("org.codehaus.stax2.supportXmlId", DataUtil.Integer(4));
/*     */ 
/*     */     
/*  75 */     sStdProperties.put("com.ctc.wstx.returnNullForDefaultNamespace", DataUtil.Integer(5));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  82 */     sStdProperties.put("http://java.sun.com/xml/stream/properties/implementation-name", DataUtil.Integer(1));
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
/*     */   public Object getProperty(String propName) {
/* 101 */     int id = findPropertyId(propName);
/* 102 */     if (id >= 0) {
/* 103 */       return getProperty(id);
/*     */     }
/* 105 */     id = findStdPropertyId(propName);
/* 106 */     if (id < 0) {
/* 107 */       reportUnknownProperty(propName);
/* 108 */       return null;
/*     */     } 
/* 110 */     return getStdProperty(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPropertySupported(String propName) {
/* 115 */     return (findPropertyId(propName) >= 0 || findStdPropertyId(propName) >= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setProperty(String propName, Object value) {
/* 125 */     int id = findPropertyId(propName);
/* 126 */     if (id >= 0) {
/* 127 */       return setProperty(propName, id, value);
/*     */     }
/* 129 */     id = findStdPropertyId(propName);
/* 130 */     if (id < 0) {
/* 131 */       reportUnknownProperty(propName);
/* 132 */       return false;
/*     */     } 
/* 134 */     return setStdProperty(propName, id, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void reportUnknownProperty(String propName) {
/* 140 */     throw new IllegalArgumentException("Unrecognized property '" + propName + "'");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Object safeGetProperty(String propName) {
/* 151 */     int id = findPropertyId(propName);
/* 152 */     if (id >= 0) {
/* 153 */       return getProperty(id);
/*     */     }
/* 155 */     id = findStdPropertyId(propName);
/* 156 */     if (id < 0) {
/* 157 */       return null;
/*     */     }
/* 159 */     return getStdProperty(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getImplName() {
/* 166 */     return "woodstox";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getImplVersion() {
/* 172 */     return "4.1";
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
/*     */   protected boolean doesSupportXml11() {
/* 192 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean doesSupportXmlId() {
/* 199 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean returnNullForDefaultNamespace() {
/* 203 */     return Boolean.getBoolean("com.ctc.wstx.returnNullForDefaultNamespace");
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
/*     */   protected int findStdPropertyId(String propName) {
/* 218 */     Integer I = (Integer)sStdProperties.get(propName);
/* 219 */     return (I == null) ? -1 : I.intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean setStdProperty(String propName, int id, Object value) {
/* 225 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object getStdProperty(int id) {
/* 230 */     switch (id) {
/*     */       case 1:
/* 232 */         return "woodstox";
/*     */       case 2:
/* 234 */         return "4.1";
/*     */       case 3:
/* 236 */         return doesSupportXml11() ? Boolean.TRUE : Boolean.FALSE;
/*     */       case 4:
/* 238 */         return doesSupportXmlId() ? Boolean.TRUE : Boolean.FALSE;
/*     */       case 5:
/* 240 */         return returnNullForDefaultNamespace() ? Boolean.TRUE : Boolean.FALSE;
/*     */     } 
/* 242 */     throw new IllegalStateException("Internal error: no handler for property with internal id " + id + ".");
/*     */   }
/*     */   
/*     */   protected abstract int findPropertyId(String paramString);
/*     */   
/*     */   protected abstract Object getProperty(int paramInt);
/*     */   
/*     */   protected abstract boolean setProperty(String paramString, int paramInt, Object paramObject);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\ctc\wstx\api\CommonConfig.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */