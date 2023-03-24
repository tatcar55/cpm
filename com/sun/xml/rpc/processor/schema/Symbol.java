/*     */ package com.sun.xml.rpc.processor.schema;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Symbol
/*     */ {
/*     */   public static final Symbol DEFAULT;
/*     */   public static final Symbol FIXED;
/*     */   public static final Symbol EXTENSION;
/*     */   public static final Symbol RESTRICTION;
/*     */   public static final Symbol SUBSTITUTION;
/*     */   public static final Symbol SKIP;
/*     */   public static final Symbol LAX;
/*     */   public static final Symbol STRICT;
/*     */   public static final Symbol KEY;
/*     */   public static final Symbol KEYREF;
/*     */   public static final Symbol UNIQUE;
/*     */   public static final Symbol ALL;
/*     */   public static final Symbol CHOICE;
/*     */   public static final Symbol SEQUENCE;
/*     */   public static final Symbol ATOMIC;
/*     */   public static final Symbol LIST;
/*     */   public static final Symbol UNION;
/*     */   
/*     */   public static Symbol named(String s) {
/*  62 */     return (Symbol)_symbolMap.get(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private static Map _symbolMap = new HashMap<Object, Object>();
/*     */   static {
/*  70 */     DEFAULT = new Symbol("default");
/*  71 */     FIXED = new Symbol("fixed");
/*  72 */     EXTENSION = new Symbol("extension");
/*  73 */     RESTRICTION = new Symbol("restriction");
/*  74 */     SUBSTITUTION = new Symbol("substitution");
/*     */     
/*  76 */     SKIP = new Symbol("skip");
/*  77 */     LAX = new Symbol("lax");
/*  78 */     STRICT = new Symbol("strict");
/*     */     
/*  80 */     KEY = new Symbol("key");
/*  81 */     KEYREF = new Symbol("keyref");
/*  82 */     UNIQUE = new Symbol("unique");
/*     */     
/*  84 */     ALL = new Symbol("all");
/*  85 */     CHOICE = new Symbol("choice");
/*  86 */     SEQUENCE = new Symbol("sequence");
/*     */     
/*  88 */     ATOMIC = new Symbol("atomic");
/*  89 */     LIST = new Symbol("list");
/*  90 */     UNION = new Symbol("union");
/*     */   }
/*     */   private String _name;
/*     */   
/*     */   private Symbol(String s) {
/*  95 */     this._name = s;
/*  96 */     _symbolMap.put(s, this);
/*     */   }
/*     */   
/*     */   public String getName() {
/* 100 */     return this._name;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\schema\Symbol.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */