/*     */ package com.sun.xml.rpc.wsdl.parser;
/*     */ 
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaKinds;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPConstants;
/*     */ import com.sun.xml.rpc.wsdl.framework.EntityReferenceValidator;
/*     */ import com.sun.xml.rpc.wsdl.framework.Kind;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SOAPEntityReferenceValidator
/*     */   implements EntityReferenceValidator
/*     */ {
/*     */   public boolean isValid(Kind kind, QName name) {
/*  53 */     if (name.getNamespaceURI().equals("http://www.w3.org/XML/1998/namespace")) {
/*  54 */       return true;
/*     */     }
/*  56 */     if (kind == SchemaKinds.XSD_TYPE)
/*  57 */       return _validTypes.contains(name); 
/*  58 */     if (kind == SchemaKinds.XSD_ELEMENT)
/*  59 */       return _validElements.contains(name); 
/*  60 */     if (kind == SchemaKinds.XSD_ATTRIBUTE) {
/*  61 */       return _validAttributes.contains(name);
/*     */     }
/*     */     
/*  64 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   private static final Set _validTypes = new HashSet(); static {
/*  75 */     _validTypes.add(SOAPConstants.QNAME_TYPE_ARRAY);
/*  76 */     _validTypes.add(SchemaConstants.QNAME_TYPE_STRING);
/*  77 */     _validTypes.add(SchemaConstants.QNAME_TYPE_NORMALIZED_STRING);
/*  78 */     _validTypes.add(SchemaConstants.QNAME_TYPE_TOKEN);
/*  79 */     _validTypes.add(SchemaConstants.QNAME_TYPE_BYTE);
/*  80 */     _validTypes.add(SchemaConstants.QNAME_TYPE_UNSIGNED_BYTE);
/*  81 */     _validTypes.add(SchemaConstants.QNAME_TYPE_BASE64_BINARY);
/*  82 */     _validTypes.add(SchemaConstants.QNAME_TYPE_HEX_BINARY);
/*  83 */     _validTypes.add(SchemaConstants.QNAME_TYPE_INTEGER);
/*  84 */     _validTypes.add(SchemaConstants.QNAME_TYPE_POSITIVE_INTEGER);
/*  85 */     _validTypes.add(SchemaConstants.QNAME_TYPE_NEGATIVE_INTEGER);
/*  86 */     _validTypes.add(SchemaConstants.QNAME_TYPE_NON_NEGATIVE_INTEGER);
/*  87 */     _validTypes.add(SchemaConstants.QNAME_TYPE_NON_POSITIVE_INTEGER);
/*  88 */     _validTypes.add(SchemaConstants.QNAME_TYPE_INT);
/*  89 */     _validTypes.add(SchemaConstants.QNAME_TYPE_UNSIGNED_INT);
/*  90 */     _validTypes.add(SchemaConstants.QNAME_TYPE_LONG);
/*  91 */     _validTypes.add(SchemaConstants.QNAME_TYPE_UNSIGNED_LONG);
/*  92 */     _validTypes.add(SchemaConstants.QNAME_TYPE_SHORT);
/*  93 */     _validTypes.add(SchemaConstants.QNAME_TYPE_UNSIGNED_SHORT);
/*  94 */     _validTypes.add(SchemaConstants.QNAME_TYPE_DECIMAL);
/*  95 */     _validTypes.add(SchemaConstants.QNAME_TYPE_FLOAT);
/*  96 */     _validTypes.add(SchemaConstants.QNAME_TYPE_DOUBLE);
/*  97 */     _validTypes.add(SchemaConstants.QNAME_TYPE_BOOLEAN);
/*  98 */     _validTypes.add(SchemaConstants.QNAME_TYPE_TIME);
/*  99 */     _validTypes.add(SchemaConstants.QNAME_TYPE_DATE_TIME);
/* 100 */     _validTypes.add(SchemaConstants.QNAME_TYPE_DURATION);
/* 101 */     _validTypes.add(SchemaConstants.QNAME_TYPE_DATE);
/* 102 */     _validTypes.add(SchemaConstants.QNAME_TYPE_G_MONTH);
/* 103 */     _validTypes.add(SchemaConstants.QNAME_TYPE_G_YEAR);
/* 104 */     _validTypes.add(SchemaConstants.QNAME_TYPE_G_YEAR_MONTH);
/* 105 */     _validTypes.add(SchemaConstants.QNAME_TYPE_G_DAY);
/* 106 */     _validTypes.add(SchemaConstants.QNAME_TYPE_G_MONTH_DAY);
/* 107 */     _validTypes.add(SchemaConstants.QNAME_TYPE_NAME);
/* 108 */     _validTypes.add(SchemaConstants.QNAME_TYPE_QNAME);
/* 109 */     _validTypes.add(SchemaConstants.QNAME_TYPE_NCNAME);
/* 110 */     _validTypes.add(SchemaConstants.QNAME_TYPE_ANY_URI);
/* 111 */     _validTypes.add(SchemaConstants.QNAME_TYPE_ID);
/* 112 */     _validTypes.add(SchemaConstants.QNAME_TYPE_IDREF);
/* 113 */     _validTypes.add(SchemaConstants.QNAME_TYPE_IDREFS);
/* 114 */     _validTypes.add(SchemaConstants.QNAME_TYPE_ENTITY);
/* 115 */     _validTypes.add(SchemaConstants.QNAME_TYPE_ENTITIES);
/* 116 */     _validTypes.add(SchemaConstants.QNAME_TYPE_NOTATION);
/* 117 */     _validTypes.add(SchemaConstants.QNAME_TYPE_NMTOKEN);
/* 118 */     _validTypes.add(SchemaConstants.QNAME_TYPE_NMTOKENS);
/* 119 */     _validTypes.add(SchemaConstants.QNAME_TYPE_URTYPE);
/* 120 */     _validTypes.add(SchemaConstants.QNAME_TYPE_SIMPLE_URTYPE);
/* 121 */     _validTypes.add(SOAPConstants.QNAME_TYPE_STRING);
/* 122 */     _validTypes.add(SOAPConstants.QNAME_TYPE_NORMALIZED_STRING);
/* 123 */     _validTypes.add(SOAPConstants.QNAME_TYPE_TOKEN);
/* 124 */     _validTypes.add(SOAPConstants.QNAME_TYPE_BYTE);
/* 125 */     _validTypes.add(SOAPConstants.QNAME_TYPE_UNSIGNED_BYTE);
/* 126 */     _validTypes.add(SOAPConstants.QNAME_TYPE_BASE64_BINARY);
/* 127 */     _validTypes.add(SOAPConstants.QNAME_TYPE_HEX_BINARY);
/* 128 */     _validTypes.add(SOAPConstants.QNAME_TYPE_INTEGER);
/* 129 */     _validTypes.add(SOAPConstants.QNAME_TYPE_POSITIVE_INTEGER);
/* 130 */     _validTypes.add(SOAPConstants.QNAME_TYPE_NEGATIVE_INTEGER);
/* 131 */     _validTypes.add(SOAPConstants.QNAME_TYPE_NON_NEGATIVE_INTEGER);
/* 132 */     _validTypes.add(SOAPConstants.QNAME_TYPE_NON_POSITIVE_INTEGER);
/* 133 */     _validTypes.add(SOAPConstants.QNAME_TYPE_INT);
/* 134 */     _validTypes.add(SOAPConstants.QNAME_TYPE_UNSIGNED_INT);
/* 135 */     _validTypes.add(SOAPConstants.QNAME_TYPE_LONG);
/* 136 */     _validTypes.add(SOAPConstants.QNAME_TYPE_UNSIGNED_LONG);
/* 137 */     _validTypes.add(SOAPConstants.QNAME_TYPE_SHORT);
/* 138 */     _validTypes.add(SOAPConstants.QNAME_TYPE_UNSIGNED_SHORT);
/* 139 */     _validTypes.add(SOAPConstants.QNAME_TYPE_DECIMAL);
/* 140 */     _validTypes.add(SOAPConstants.QNAME_TYPE_FLOAT);
/* 141 */     _validTypes.add(SOAPConstants.QNAME_TYPE_DOUBLE);
/* 142 */     _validTypes.add(SOAPConstants.QNAME_TYPE_BOOLEAN);
/* 143 */     _validTypes.add(SOAPConstants.QNAME_TYPE_TIME);
/* 144 */     _validTypes.add(SOAPConstants.QNAME_TYPE_DATE_TIME);
/* 145 */     _validTypes.add(SOAPConstants.QNAME_TYPE_DURATION);
/* 146 */     _validTypes.add(SOAPConstants.QNAME_TYPE_DATE);
/* 147 */     _validTypes.add(SOAPConstants.QNAME_TYPE_G_MONTH);
/* 148 */     _validTypes.add(SOAPConstants.QNAME_TYPE_G_YEAR);
/* 149 */     _validTypes.add(SOAPConstants.QNAME_TYPE_G_YEAR_MONTH);
/* 150 */     _validTypes.add(SOAPConstants.QNAME_TYPE_G_DAY);
/* 151 */     _validTypes.add(SOAPConstants.QNAME_TYPE_G_MONTH_DAY);
/* 152 */     _validTypes.add(SOAPConstants.QNAME_TYPE_NAME);
/* 153 */     _validTypes.add(SOAPConstants.QNAME_TYPE_QNAME);
/* 154 */     _validTypes.add(SOAPConstants.QNAME_TYPE_NCNAME);
/* 155 */     _validTypes.add(SOAPConstants.QNAME_TYPE_ANY_URI);
/* 156 */     _validTypes.add(SOAPConstants.QNAME_TYPE_ID);
/* 157 */     _validTypes.add(SOAPConstants.QNAME_TYPE_IDREF);
/* 158 */     _validTypes.add(SOAPConstants.QNAME_TYPE_IDREFS);
/* 159 */     _validTypes.add(SOAPConstants.QNAME_TYPE_ENTITY);
/* 160 */     _validTypes.add(SOAPConstants.QNAME_TYPE_ENTITIES);
/* 161 */     _validTypes.add(SOAPConstants.QNAME_TYPE_NOTATION);
/* 162 */     _validTypes.add(SOAPConstants.QNAME_TYPE_NMTOKEN);
/* 163 */     _validTypes.add(SOAPConstants.QNAME_TYPE_NMTOKENS);
/* 164 */     _validTypes.add(SOAPConstants.QNAME_TYPE_BASE64);
/*     */     
/* 166 */     _validTypes.add(SchemaConstants.QNAME_TYPE_LANGUAGE);
/*     */   }
/*     */   
/* 169 */   private static final Set _validElements = new HashSet(); static {
/* 170 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_STRING);
/* 171 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_NORMALIZED_STRING);
/* 172 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_TOKEN);
/* 173 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_BYTE);
/* 174 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_UNSIGNED_BYTE);
/* 175 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_BASE64_BINARY);
/* 176 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_HEX_BINARY);
/* 177 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_INTEGER);
/* 178 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_POSITIVE_INTEGER);
/* 179 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_NEGATIVE_INTEGER);
/* 180 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_NON_NEGATIVE_INTEGER);
/* 181 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_NON_POSITIVE_INTEGER);
/* 182 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_INT);
/* 183 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_UNSIGNED_INT);
/* 184 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_LONG);
/* 185 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_UNSIGNED_LONG);
/* 186 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_SHORT);
/* 187 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_UNSIGNED_SHORT);
/* 188 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_DECIMAL);
/* 189 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_FLOAT);
/* 190 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_DOUBLE);
/* 191 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_BOOLEAN);
/* 192 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_TIME);
/* 193 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_DATE_TIME);
/* 194 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_DURATION);
/* 195 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_DATE);
/* 196 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_G_MONTH);
/* 197 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_G_YEAR);
/* 198 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_G_YEAR_MONTH);
/* 199 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_G_DAY);
/* 200 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_G_MONTH_DAY);
/* 201 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_NAME);
/* 202 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_QNAME);
/* 203 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_NCNAME);
/* 204 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_ANY_URI);
/* 205 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_ID);
/* 206 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_IDREF);
/* 207 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_IDREFS);
/* 208 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_ENTITY);
/* 209 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_ENTITIES);
/* 210 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_NOTATION);
/* 211 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_NMTOKEN);
/* 212 */     _validElements.add(SOAPConstants.QNAME_ELEMENT_NMTOKENS);
/*     */   }
/* 214 */   private static final Set _validAttributes = new HashSet(); static {
/* 215 */     _validAttributes.add(SOAPConstants.QNAME_ATTR_ARRAY_TYPE);
/* 216 */     _validAttributes.add(SOAPConstants.QNAME_ATTR_OFFSET);
/* 217 */     _validAttributes.add(SOAPConstants.QNAME_ATTR_POSITION);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\parser\SOAPEntityReferenceValidator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */