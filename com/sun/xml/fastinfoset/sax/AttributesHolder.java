/*     */ package com.sun.xml.fastinfoset.sax;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.CommonResourceBundle;
/*     */ import com.sun.xml.fastinfoset.QualifiedName;
/*     */ import com.sun.xml.fastinfoset.algorithm.BuiltInEncodingAlgorithm;
/*     */ import com.sun.xml.fastinfoset.algorithm.BuiltInEncodingAlgorithmFactory;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import org.jvnet.fastinfoset.EncodingAlgorithm;
/*     */ import org.jvnet.fastinfoset.EncodingAlgorithmException;
/*     */ import org.jvnet.fastinfoset.FastInfosetException;
/*     */ import org.jvnet.fastinfoset.sax.EncodingAlgorithmAttributes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AttributesHolder
/*     */   implements EncodingAlgorithmAttributes
/*     */ {
/*     */   private static final int DEFAULT_CAPACITY = 8;
/*     */   private Map _registeredEncodingAlgorithms;
/*     */   private int _attributeCount;
/*  48 */   private QualifiedName[] _names = new QualifiedName[8];
/*  49 */   private String[] _values = new String[8];
/*     */   
/*  51 */   private String[] _algorithmURIs = new String[8];
/*  52 */   private int[] _algorithmIds = new int[8];
/*  53 */   private Object[] _algorithmData = new Object[8];
/*     */ 
/*     */   
/*     */   public AttributesHolder(Map registeredEncodingAlgorithms) {
/*  57 */     this();
/*  58 */     this._registeredEncodingAlgorithms = registeredEncodingAlgorithms;
/*     */   }
/*     */   
/*     */   public AttributesHolder() {}
/*     */   
/*     */   public final int getLength() {
/*  64 */     return this._attributeCount;
/*     */   }
/*     */   
/*     */   public final String getLocalName(int index) {
/*  68 */     return (this._names[index]).localName;
/*     */   }
/*     */   
/*     */   public final String getQName(int index) {
/*  72 */     return this._names[index].getQNameString();
/*     */   }
/*     */   
/*     */   public final String getType(int index) {
/*  76 */     return "CDATA";
/*     */   }
/*     */   
/*     */   public final String getURI(int index) {
/*  80 */     return (this._names[index]).namespaceName;
/*     */   }
/*     */   
/*     */   public final String getValue(int index) {
/*  84 */     String value = this._values[index];
/*  85 */     if (value != null) {
/*  86 */       return value;
/*     */     }
/*     */     
/*  89 */     if (this._algorithmData[index] == null || (this._algorithmIds[index] >= 32 && this._registeredEncodingAlgorithms == null))
/*     */     {
/*     */       
/*  92 */       return null;
/*     */     }
/*     */     
/*     */     try {
/*  96 */       this._values[index] = convertEncodingAlgorithmDataToString(this._algorithmIds[index], this._algorithmURIs[index], this._algorithmData[index]).toString(); return convertEncodingAlgorithmDataToString(this._algorithmIds[index], this._algorithmURIs[index], this._algorithmData[index]).toString();
/*     */ 
/*     */     
/*     */     }
/* 100 */     catch (IOException e) {
/* 101 */       return null;
/* 102 */     } catch (FastInfosetException e) {
/* 103 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final int getIndex(String qName) {
/* 108 */     int i = qName.indexOf(':');
/* 109 */     String prefix = "";
/* 110 */     String localName = qName;
/* 111 */     if (i >= 0) {
/* 112 */       prefix = qName.substring(0, i);
/* 113 */       localName = qName.substring(i + 1);
/*     */     } 
/*     */     
/* 116 */     for (i = 0; i < this._attributeCount; i++) {
/* 117 */       QualifiedName name = this._names[i];
/* 118 */       if (localName.equals(name.localName) && prefix.equals(name.prefix))
/*     */       {
/* 120 */         return i;
/*     */       }
/*     */     } 
/* 123 */     return -1;
/*     */   }
/*     */   
/*     */   public final String getType(String qName) {
/* 127 */     int index = getIndex(qName);
/* 128 */     if (index >= 0) {
/* 129 */       return "CDATA";
/*     */     }
/* 131 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getValue(String qName) {
/* 136 */     int index = getIndex(qName);
/* 137 */     if (index >= 0) {
/* 138 */       return this._values[index];
/*     */     }
/* 140 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getIndex(String uri, String localName) {
/* 145 */     for (int i = 0; i < this._attributeCount; i++) {
/* 146 */       QualifiedName name = this._names[i];
/* 147 */       if (localName.equals(name.localName) && uri.equals(name.namespaceName))
/*     */       {
/* 149 */         return i;
/*     */       }
/*     */     } 
/* 152 */     return -1;
/*     */   }
/*     */   
/*     */   public final String getType(String uri, String localName) {
/* 156 */     int index = getIndex(uri, localName);
/* 157 */     if (index >= 0) {
/* 158 */       return "CDATA";
/*     */     }
/* 160 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getValue(String uri, String localName) {
/* 165 */     int index = getIndex(uri, localName);
/* 166 */     if (index >= 0) {
/* 167 */       return this._values[index];
/*     */     }
/* 169 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void clear() {
/* 174 */     for (int i = 0; i < this._attributeCount; i++) {
/* 175 */       this._values[i] = null;
/* 176 */       this._algorithmData[i] = null;
/*     */     } 
/* 178 */     this._attributeCount = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getAlgorithmURI(int index) {
/* 184 */     return this._algorithmURIs[index];
/*     */   }
/*     */   
/*     */   public final int getAlgorithmIndex(int index) {
/* 188 */     return this._algorithmIds[index];
/*     */   }
/*     */   
/*     */   public final Object getAlgorithmData(int index) {
/* 192 */     return this._algorithmData[index];
/*     */   }
/*     */   
/*     */   public String getAlpababet(int index) {
/* 196 */     return null;
/*     */   }
/*     */   
/*     */   public boolean getToIndex(int index) {
/* 200 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void addAttribute(QualifiedName name, String value) {
/* 206 */     if (this._attributeCount == this._names.length) {
/* 207 */       resize();
/*     */     }
/* 209 */     this._names[this._attributeCount] = name;
/* 210 */     this._values[this._attributeCount++] = value;
/*     */   }
/*     */   
/*     */   public final void addAttributeWithAlgorithmData(QualifiedName name, String URI, int id, Object data) {
/* 214 */     if (this._attributeCount == this._names.length) {
/* 215 */       resize();
/*     */     }
/* 217 */     this._names[this._attributeCount] = name;
/* 218 */     this._values[this._attributeCount] = null;
/*     */     
/* 220 */     this._algorithmURIs[this._attributeCount] = URI;
/* 221 */     this._algorithmIds[this._attributeCount] = id;
/* 222 */     this._algorithmData[this._attributeCount++] = data;
/*     */   }
/*     */   
/*     */   public final QualifiedName getQualifiedName(int index) {
/* 226 */     return this._names[index];
/*     */   }
/*     */   
/*     */   public final String getPrefix(int index) {
/* 230 */     return (this._names[index]).prefix;
/*     */   }
/*     */   
/*     */   private final void resize() {
/* 234 */     int newLength = this._attributeCount * 3 / 2 + 1;
/*     */     
/* 236 */     QualifiedName[] names = new QualifiedName[newLength];
/* 237 */     String[] values = new String[newLength];
/*     */     
/* 239 */     String[] algorithmURIs = new String[newLength];
/* 240 */     int[] algorithmIds = new int[newLength];
/* 241 */     Object[] algorithmData = new Object[newLength];
/*     */     
/* 243 */     System.arraycopy(this._names, 0, names, 0, this._attributeCount);
/* 244 */     System.arraycopy(this._values, 0, values, 0, this._attributeCount);
/*     */     
/* 246 */     System.arraycopy(this._algorithmURIs, 0, algorithmURIs, 0, this._attributeCount);
/* 247 */     System.arraycopy(this._algorithmIds, 0, algorithmIds, 0, this._attributeCount);
/* 248 */     System.arraycopy(this._algorithmData, 0, algorithmData, 0, this._attributeCount);
/*     */     
/* 250 */     this._names = names;
/* 251 */     this._values = values;
/*     */     
/* 253 */     this._algorithmURIs = algorithmURIs;
/* 254 */     this._algorithmIds = algorithmIds;
/* 255 */     this._algorithmData = algorithmData;
/*     */   }
/*     */   
/*     */   private final StringBuffer convertEncodingAlgorithmDataToString(int identifier, String URI, Object data) throws FastInfosetException, IOException {
/* 259 */     EncodingAlgorithm ea = null;
/* 260 */     if (identifier < 9)
/* 261 */     { BuiltInEncodingAlgorithm builtInEncodingAlgorithm = BuiltInEncodingAlgorithmFactory.getAlgorithm(identifier); }
/* 262 */     else { if (identifier == 9)
/* 263 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.CDATAAlgorithmNotSupported")); 
/* 264 */       if (identifier >= 32) {
/* 265 */         if (URI == null) {
/* 266 */           throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.URINotPresent") + identifier);
/*     */         }
/*     */         
/* 269 */         ea = (EncodingAlgorithm)this._registeredEncodingAlgorithms.get(URI);
/* 270 */         if (ea == null) {
/* 271 */           throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.algorithmNotRegistered") + URI);
/*     */         
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 277 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.identifiers10to31Reserved"));
/*     */       }  }
/*     */     
/* 280 */     StringBuffer sb = new StringBuffer();
/* 281 */     ea.convertToCharacters(data, sb);
/* 282 */     return sb;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\sax\AttributesHolder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */