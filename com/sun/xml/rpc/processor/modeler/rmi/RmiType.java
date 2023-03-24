/*     */ package com.sun.xml.rpc.processor.modeler.rmi;
/*     */ 
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RmiType
/*     */   implements RmiConstants
/*     */ {
/*  36 */   private static final Hashtable typeHash = new Hashtable<Object, Object>(231);
/*     */   
/*     */   private String typeSig;
/*  39 */   private int typeCode = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   public static final RmiType tVoid = new RmiType(11, "V");
/*  53 */   public static final RmiType tBoolean = new RmiType(0, "Z");
/*  54 */   public static final RmiType tByte = new RmiType(1, "B");
/*  55 */   public static final RmiType tChar = new RmiType(2, "C");
/*  56 */   public static final RmiType tShort = new RmiType(3, "S");
/*  57 */   public static final RmiType tInt = new RmiType(4, "I");
/*  58 */   public static final RmiType tFloat = new RmiType(6, "F");
/*  59 */   public static final RmiType tLong = new RmiType(5, "J");
/*  60 */   public static final RmiType tDouble = new RmiType(7, "D");
/*  61 */   public static final RmiType tObject = classType("java.lang.Object");
/*  62 */   public static final RmiType tClassDesc = classType("java.lang.Class");
/*  63 */   public static final RmiType tString = classType("java.lang.String");
/*     */ 
/*     */   
/*     */   protected RmiType() {}
/*     */   
/*     */   protected RmiType(int typeCode, String typeSig) {
/*  69 */     this.typeCode = typeCode;
/*  70 */     this.typeSig = typeSig;
/*  71 */     typeHash.put(typeSig, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getTypeSignature() {
/*  78 */     return this.typeSig;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTypeCode() {
/*  83 */     return this.typeCode;
/*     */   }
/*     */   
/*     */   public RmiType getElementType() {
/*  87 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getClassName() {
/*  91 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public int getArrayDimension() {
/*  95 */     return 0;
/*     */   }
/*     */   
/*     */   public Class getTypeClass(ClassLoader loader) throws ClassNotFoundException {
/*  99 */     if (this.typeSig.length() == 1) {
/* 100 */       return RmiUtils.getClassForName(typeString(false), loader);
/*     */     }
/* 102 */     String sig = getTypeSigClassName(this.typeSig);
/* 103 */     return Class.forName(sig, true, loader);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getTypeSigClassName(String typeSig) {
/* 108 */     String sig = typeSig;
/* 109 */     if (sig.charAt(0) == 'L') {
/* 110 */       sig = sig.substring(1, sig.length() - 1).replace('/', '.');
/*     */     }
/* 112 */     return sig;
/*     */   }
/*     */   
/*     */   public static RmiType classType(String className) {
/* 116 */     String sig = new String("L" + className + ";");
/*     */     
/* 118 */     RmiType t = (RmiType)typeHash.get(sig);
/* 119 */     if (t == null) {
/* 120 */       t = new ClassType(sig, className);
/*     */     }
/*     */     
/* 123 */     return t;
/*     */   }
/*     */   
/*     */   public static RmiType arrayType(RmiType elem) {
/* 127 */     String sig = new String("[" + elem.getTypeSignature());
/* 128 */     RmiType t = (RmiType)typeHash.get(sig);
/* 129 */     if (t == null) {
/* 130 */       t = new ArrayType(sig, elem);
/*     */     }
/* 132 */     return t;
/*     */   }
/*     */ 
/*     */   
/*     */   public static RmiType getRmiType(Class classObj) {
/*     */     String sig;
/* 138 */     if (classObj.isArray()) {
/* 139 */       sig = classObj.getName();
/*     */     } else {
/* 141 */       sig = RmiUtils.getTypeSig(classObj.getName());
/*     */     } 
/* 143 */     return getRmiType(sig);
/*     */   }
/*     */   
/*     */   public static RmiType getRmiType(String sig) {
/* 147 */     RmiType type = (RmiType)typeHash.get(sig);
/* 148 */     if (type != null) {
/* 149 */       return type;
/*     */     }
/* 151 */     switch (sig.charAt(0)) {
/*     */       case '[':
/* 153 */         return arrayType(getRmiType(sig.substring(1)));
/*     */       
/*     */       case 'L':
/* 156 */         return classType(sig.substring(1, sig.length() - 1).replace('/', '.'));
/*     */     } 
/* 158 */     return type;
/*     */   }
/*     */   
/*     */   public String typeString(boolean abbrev) {
/* 162 */     switch (this.typeCode) { case 11:
/* 163 */         return "void";
/* 164 */       case 0: return "boolean";
/* 165 */       case 1: return "byte";
/* 166 */       case 2: return "char";
/* 167 */       case 3: return "short";
/* 168 */       case 4: return "int";
/* 169 */       case 5: return "long";
/* 170 */       case 6: return "float";
/* 171 */       case 7: return "double"; }
/* 172 */      return "unknown";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNillable() {
/* 177 */     return false;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 181 */     return typeString(false);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\rmi\RmiType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */