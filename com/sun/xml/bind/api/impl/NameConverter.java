/*     */ package com.sun.xml.bind.api.impl;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.lang.model.SourceVersion;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface NameConverter
/*     */ {
/* 109 */   public static final NameConverter standard = new Standard();
/*     */   
/*     */   public static class Standard extends NameUtil implements NameConverter {
/*     */     public String toClassName(String s) {
/* 113 */       return toMixedCaseName(toWordList(s), true);
/*     */     }
/*     */     public String toVariableName(String s) {
/* 116 */       return toMixedCaseName(toWordList(s), false);
/*     */     }
/*     */     public String toInterfaceName(String token) {
/* 119 */       return toClassName(token);
/*     */     }
/*     */     public String toPropertyName(String s) {
/* 122 */       String prop = toClassName(s);
/*     */ 
/*     */       
/* 125 */       if (prop.equals("Class"))
/* 126 */         prop = "Clazz"; 
/* 127 */       return prop;
/*     */     }
/*     */     public String toConstantName(String token) {
/* 130 */       return super.toConstantName(token);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toPackageName(String nsUri) {
/* 142 */       int idx = nsUri.indexOf(':');
/* 143 */       String scheme = "";
/* 144 */       if (idx >= 0) {
/* 145 */         scheme = nsUri.substring(0, idx);
/* 146 */         if (scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("urn")) {
/* 147 */           nsUri = nsUri.substring(idx + 1);
/*     */         }
/*     */       } 
/*     */       
/* 151 */       ArrayList<String> tokens = tokenize(nsUri, "/: ");
/* 152 */       if (tokens.size() == 0) {
/* 153 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 157 */       if (tokens.size() > 1) {
/*     */ 
/*     */ 
/*     */         
/* 161 */         String lastToken = tokens.get(tokens.size() - 1);
/* 162 */         idx = lastToken.lastIndexOf('.');
/* 163 */         if (idx > 0) {
/* 164 */           lastToken = lastToken.substring(0, idx);
/* 165 */           tokens.set(tokens.size() - 1, lastToken);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 170 */       String domain = tokens.get(0);
/* 171 */       idx = domain.indexOf(':');
/* 172 */       if (idx >= 0) domain = domain.substring(0, idx); 
/* 173 */       ArrayList<String> r = reverse(tokenize(domain, scheme.equals("urn") ? ".-" : "."));
/* 174 */       if (((String)r.get(r.size() - 1)).equalsIgnoreCase("www"))
/*     */       {
/* 176 */         r.remove(r.size() - 1);
/*     */       }
/*     */ 
/*     */       
/* 180 */       tokens.addAll(1, r);
/* 181 */       tokens.remove(0);
/*     */ 
/*     */       
/* 184 */       for (int i = 0; i < tokens.size(); i++) {
/*     */ 
/*     */         
/* 187 */         String token = tokens.get(i);
/* 188 */         token = removeIllegalIdentifierChars(token);
/*     */ 
/*     */         
/* 191 */         if (SourceVersion.isKeyword(token.toLowerCase())) {
/* 192 */           token = '_' + token;
/*     */         }
/*     */         
/* 195 */         tokens.set(i, token.toLowerCase());
/*     */       } 
/*     */ 
/*     */       
/* 199 */       return combine(tokens, '.');
/*     */     }
/*     */ 
/*     */     
/*     */     private static String removeIllegalIdentifierChars(String token) {
/* 204 */       StringBuilder newToken = new StringBuilder(token.length() + 1);
/* 205 */       for (int i = 0; i < token.length(); i++) {
/* 206 */         char c = token.charAt(i);
/* 207 */         if (i == 0 && !Character.isJavaIdentifierStart(c)) {
/* 208 */           newToken.append('_');
/*     */         }
/* 210 */         if (!Character.isJavaIdentifierPart(c)) {
/* 211 */           newToken.append('_');
/*     */         } else {
/* 213 */           newToken.append(c);
/*     */         } 
/*     */       } 
/* 216 */       return newToken.toString();
/*     */     }
/*     */ 
/*     */     
/*     */     private static ArrayList<String> tokenize(String str, String sep) {
/* 221 */       StringTokenizer tokens = new StringTokenizer(str, sep);
/* 222 */       ArrayList<String> r = new ArrayList<String>();
/*     */       
/* 224 */       while (tokens.hasMoreTokens()) {
/* 225 */         r.add(tokens.nextToken());
/*     */       }
/* 227 */       return r;
/*     */     }
/*     */     
/*     */     private static <T> ArrayList<T> reverse(List<T> a) {
/* 231 */       ArrayList<T> r = new ArrayList<T>();
/*     */       
/* 233 */       for (int i = a.size() - 1; i >= 0; i--) {
/* 234 */         r.add(a.get(i));
/*     */       }
/* 236 */       return r;
/*     */     }
/*     */     
/*     */     private static String combine(List<E> r, char sep) {
/* 240 */       StringBuilder buf = new StringBuilder(r.get(0).toString());
/*     */       
/* 242 */       for (int i = 1; i < r.size(); i++) {
/* 243 */         buf.append(sep);
/* 244 */         buf.append(r.get(i));
/*     */       } 
/*     */       
/* 247 */       return buf.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 257 */   public static final NameConverter jaxrpcCompatible = new Standard() {
/*     */       protected boolean isPunct(char c) {
/* 259 */         return (c == '.' || c == '-' || c == ';' || c == '·' || c == '·' || c == '۝' || c == '۞');
/*     */       }
/*     */       
/*     */       protected boolean isLetter(char c) {
/* 263 */         return (super.isLetter(c) || c == '_');
/*     */       }
/*     */       
/*     */       protected int classify(char c0) {
/* 267 */         if (c0 == '_') return 2; 
/* 268 */         return super.classify(c0);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 275 */   public static final NameConverter smart = new Standard() {
/*     */       public String toConstantName(String token) {
/* 277 */         String name = super.toConstantName(token);
/* 278 */         if (!SourceVersion.isKeyword(name)) {
/* 279 */           return name;
/*     */         }
/* 281 */         return '_' + name;
/*     */       }
/*     */     };
/*     */   
/*     */   String toClassName(String paramString);
/*     */   
/*     */   String toInterfaceName(String paramString);
/*     */   
/*     */   String toPropertyName(String paramString);
/*     */   
/*     */   String toConstantName(String paramString);
/*     */   
/*     */   String toVariableName(String paramString);
/*     */   
/*     */   String toPackageName(String paramString);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\api\impl\NameConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */