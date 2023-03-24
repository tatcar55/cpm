/*     */ package com.sun.xml.ws.org.objectweb.asm;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Type
/*     */ {
/*     */   public static final int VOID = 0;
/*     */   public static final int BOOLEAN = 1;
/*     */   public static final int CHAR = 2;
/*     */   public static final int BYTE = 3;
/*     */   public static final int SHORT = 4;
/*     */   public static final int INT = 5;
/*     */   public static final int FLOAT = 6;
/*     */   public static final int LONG = 7;
/*     */   public static final int DOUBLE = 8;
/*     */   public static final int ARRAY = 9;
/*     */   public static final int OBJECT = 10;
/* 102 */   public static final Type VOID_TYPE = new Type(0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   public static final Type BOOLEAN_TYPE = new Type(1);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 112 */   public static final Type CHAR_TYPE = new Type(2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   public static final Type BYTE_TYPE = new Type(3);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   public static final Type SHORT_TYPE = new Type(4);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   public static final Type INT_TYPE = new Type(5);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   public static final Type FLOAT_TYPE = new Type(6);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   public static final Type LONG_TYPE = new Type(7);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 142 */   public static final Type DOUBLE_TYPE = new Type(8);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int sort;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final char[] buf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int off;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int len;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Type(int sort) {
/* 181 */     this(sort, null, 0, 1);
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
/*     */   private Type(int sort, char[] buf, int off, int len) {
/* 194 */     this.sort = sort;
/* 195 */     this.buf = buf;
/* 196 */     this.off = off;
/* 197 */     this.len = len;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Type getType(String typeDescriptor) {
/* 207 */     return getType(typeDescriptor.toCharArray(), 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Type getObjectType(String internalName) {
/* 217 */     char[] buf = internalName.toCharArray();
/* 218 */     return new Type((buf[0] == '[') ? 9 : 10, buf, 0, buf.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Type getType(Class<int> c) {
/* 228 */     if (c.isPrimitive()) {
/* 229 */       if (c == int.class)
/* 230 */         return INT_TYPE; 
/* 231 */       if (c == void.class)
/* 232 */         return VOID_TYPE; 
/* 233 */       if (c == boolean.class)
/* 234 */         return BOOLEAN_TYPE; 
/* 235 */       if (c == byte.class)
/* 236 */         return BYTE_TYPE; 
/* 237 */       if (c == char.class)
/* 238 */         return CHAR_TYPE; 
/* 239 */       if (c == short.class)
/* 240 */         return SHORT_TYPE; 
/* 241 */       if (c == double.class)
/* 242 */         return DOUBLE_TYPE; 
/* 243 */       if (c == float.class) {
/* 244 */         return FLOAT_TYPE;
/*     */       }
/* 246 */       return LONG_TYPE;
/*     */     } 
/*     */     
/* 249 */     return getType(getDescriptor(c));
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
/*     */   public static Type[] getArgumentTypes(String methodDescriptor) {
/* 262 */     char[] buf = methodDescriptor.toCharArray();
/* 263 */     int off = 1;
/* 264 */     int size = 0;
/*     */     while (true) {
/* 266 */       char car = buf[off++];
/* 267 */       if (car == ')')
/*     */         break; 
/* 269 */       if (car == 'L') {
/* 270 */         while (buf[off++] != ';');
/*     */         
/* 272 */         size++; continue;
/* 273 */       }  if (car != '[') {
/* 274 */         size++;
/*     */       }
/*     */     } 
/* 277 */     Type[] args = new Type[size];
/* 278 */     off = 1;
/* 279 */     size = 0;
/* 280 */     while (buf[off] != ')') {
/* 281 */       args[size] = getType(buf, off);
/* 282 */       off += (args[size]).len + (((args[size]).sort == 10) ? 2 : 0);
/* 283 */       size++;
/*     */     } 
/* 285 */     return args;
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
/*     */   public static Type[] getArgumentTypes(Method method) {
/* 297 */     Class[] classes = method.getParameterTypes();
/* 298 */     Type[] types = new Type[classes.length];
/* 299 */     for (int i = classes.length - 1; i >= 0; i--) {
/* 300 */       types[i] = getType(classes[i]);
/*     */     }
/* 302 */     return types;
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
/*     */   public static Type getReturnType(String methodDescriptor) {
/* 314 */     char[] buf = methodDescriptor.toCharArray();
/* 315 */     return getType(buf, methodDescriptor.indexOf(')') + 1);
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
/*     */   public static Type getReturnType(Method method) {
/* 327 */     return getType(method.getReturnType());
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
/*     */   private static Type getType(char[] buf, int off) {
/* 339 */     switch (buf[off]) {
/*     */       case 'V':
/* 341 */         return VOID_TYPE;
/*     */       case 'Z':
/* 343 */         return BOOLEAN_TYPE;
/*     */       case 'C':
/* 345 */         return CHAR_TYPE;
/*     */       case 'B':
/* 347 */         return BYTE_TYPE;
/*     */       case 'S':
/* 349 */         return SHORT_TYPE;
/*     */       case 'I':
/* 351 */         return INT_TYPE;
/*     */       case 'F':
/* 353 */         return FLOAT_TYPE;
/*     */       case 'J':
/* 355 */         return LONG_TYPE;
/*     */       case 'D':
/* 357 */         return DOUBLE_TYPE;
/*     */       case '[':
/* 359 */         len = 1;
/* 360 */         while (buf[off + len] == '[') {
/* 361 */           len++;
/*     */         }
/* 363 */         if (buf[off + len] == 'L') {
/* 364 */           len++;
/* 365 */           while (buf[off + len] != ';') {
/* 366 */             len++;
/*     */           }
/*     */         } 
/* 369 */         return new Type(9, buf, off, len + 1);
/*     */     } 
/*     */     
/* 372 */     int len = 1;
/* 373 */     while (buf[off + len] != ';') {
/* 374 */       len++;
/*     */     }
/* 376 */     return new Type(10, buf, off + 1, len - 1);
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
/*     */   public int getSort() {
/* 394 */     return this.sort;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDimensions() {
/* 404 */     int i = 1;
/* 405 */     while (this.buf[this.off + i] == '[') {
/* 406 */       i++;
/*     */     }
/* 408 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getElementType() {
/* 418 */     return getType(this.buf, this.off + getDimensions());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getClassName() {
/*     */     StringBuffer b;
/*     */     int i;
/* 427 */     switch (this.sort) {
/*     */       case 0:
/* 429 */         return "void";
/*     */       case 1:
/* 431 */         return "boolean";
/*     */       case 2:
/* 433 */         return "char";
/*     */       case 3:
/* 435 */         return "byte";
/*     */       case 4:
/* 437 */         return "short";
/*     */       case 5:
/* 439 */         return "int";
/*     */       case 6:
/* 441 */         return "float";
/*     */       case 7:
/* 443 */         return "long";
/*     */       case 8:
/* 445 */         return "double";
/*     */       case 9:
/* 447 */         b = new StringBuffer(getElementType().getClassName());
/* 448 */         for (i = getDimensions(); i > 0; i--) {
/* 449 */           b.append("[]");
/*     */         }
/* 451 */         return b.toString();
/*     */     } 
/*     */     
/* 454 */     return (new String(this.buf, this.off, this.len)).replace('/', '.');
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
/*     */   public String getInternalName() {
/* 467 */     return new String(this.buf, this.off, this.len);
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
/*     */   public String getDescriptor() {
/* 480 */     StringBuffer buf = new StringBuffer();
/* 481 */     getDescriptor(buf);
/* 482 */     return buf.toString();
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
/*     */   public static String getMethodDescriptor(Type returnType, Type[] argumentTypes) {
/* 498 */     StringBuffer buf = new StringBuffer();
/* 499 */     buf.append('(');
/* 500 */     for (int i = 0; i < argumentTypes.length; i++) {
/* 501 */       argumentTypes[i].getDescriptor(buf);
/*     */     }
/* 503 */     buf.append(')');
/* 504 */     returnType.getDescriptor(buf);
/* 505 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void getDescriptor(StringBuffer buf) {
/* 515 */     switch (this.sort) {
/*     */       case 0:
/* 517 */         buf.append('V');
/*     */         return;
/*     */       case 1:
/* 520 */         buf.append('Z');
/*     */         return;
/*     */       case 2:
/* 523 */         buf.append('C');
/*     */         return;
/*     */       case 3:
/* 526 */         buf.append('B');
/*     */         return;
/*     */       case 4:
/* 529 */         buf.append('S');
/*     */         return;
/*     */       case 5:
/* 532 */         buf.append('I');
/*     */         return;
/*     */       case 6:
/* 535 */         buf.append('F');
/*     */         return;
/*     */       case 7:
/* 538 */         buf.append('J');
/*     */         return;
/*     */       case 8:
/* 541 */         buf.append('D');
/*     */         return;
/*     */       case 9:
/* 544 */         buf.append(this.buf, this.off, this.len);
/*     */         return;
/*     */     } 
/*     */     
/* 548 */     buf.append('L');
/* 549 */     buf.append(this.buf, this.off, this.len);
/* 550 */     buf.append(';');
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
/*     */   public static String getInternalName(Class c) {
/* 568 */     return c.getName().replace('.', '/');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getDescriptor(Class c) {
/* 578 */     StringBuffer buf = new StringBuffer();
/* 579 */     getDescriptor(buf, c);
/* 580 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getConstructorDescriptor(Constructor c) {
/* 590 */     Class[] parameters = c.getParameterTypes();
/* 591 */     StringBuffer buf = new StringBuffer();
/* 592 */     buf.append('(');
/* 593 */     for (int i = 0; i < parameters.length; i++) {
/* 594 */       getDescriptor(buf, parameters[i]);
/*     */     }
/* 596 */     return buf.append(")V").toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getMethodDescriptor(Method m) {
/* 606 */     Class[] parameters = m.getParameterTypes();
/* 607 */     StringBuffer buf = new StringBuffer();
/* 608 */     buf.append('(');
/* 609 */     for (int i = 0; i < parameters.length; i++) {
/* 610 */       getDescriptor(buf, parameters[i]);
/*     */     }
/* 612 */     buf.append(')');
/* 613 */     getDescriptor(buf, m.getReturnType());
/* 614 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void getDescriptor(StringBuffer buf, Class<int> c) {
/* 624 */     Class<int> d = c;
/*     */     while (true) {
/* 626 */       if (d.isPrimitive()) {
/*     */         char car;
/* 628 */         if (d == int.class) {
/* 629 */           car = 'I';
/* 630 */         } else if (d == void.class) {
/* 631 */           car = 'V';
/* 632 */         } else if (d == boolean.class) {
/* 633 */           car = 'Z';
/* 634 */         } else if (d == byte.class) {
/* 635 */           car = 'B';
/* 636 */         } else if (d == char.class) {
/* 637 */           car = 'C';
/* 638 */         } else if (d == short.class) {
/* 639 */           car = 'S';
/* 640 */         } else if (d == double.class) {
/* 641 */           car = 'D';
/* 642 */         } else if (d == float.class) {
/* 643 */           car = 'F';
/*     */         } else {
/* 645 */           car = 'J';
/*     */         } 
/* 647 */         buf.append(car); return;
/*     */       } 
/* 649 */       if (d.isArray()) {
/* 650 */         buf.append('[');
/* 651 */         d = (Class)d.getComponentType(); continue;
/*     */       }  break;
/* 653 */     }  buf.append('L');
/* 654 */     String name = d.getName();
/* 655 */     int len = name.length();
/* 656 */     for (int i = 0; i < len; i++) {
/* 657 */       char car = name.charAt(i);
/* 658 */       buf.append((car == '.') ? 47 : car);
/*     */     } 
/* 660 */     buf.append(';');
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
/*     */   public int getSize() {
/* 677 */     return (this.sort == 7 || this.sort == 8) ? 2 : 1;
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
/*     */   public int getOpcode(int opcode) {
/* 691 */     if (opcode == 46 || opcode == 79) {
/* 692 */       switch (this.sort) {
/*     */         case 1:
/*     */         case 3:
/* 695 */           return opcode + 5;
/*     */         case 2:
/* 697 */           return opcode + 6;
/*     */         case 4:
/* 699 */           return opcode + 7;
/*     */         case 5:
/* 701 */           return opcode;
/*     */         case 6:
/* 703 */           return opcode + 2;
/*     */         case 7:
/* 705 */           return opcode + 1;
/*     */         case 8:
/* 707 */           return opcode + 3;
/*     */       } 
/*     */ 
/*     */       
/* 711 */       return opcode + 4;
/*     */     } 
/*     */     
/* 714 */     switch (this.sort) {
/*     */       case 0:
/* 716 */         return opcode + 5;
/*     */       case 1:
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/* 722 */         return opcode;
/*     */       case 6:
/* 724 */         return opcode + 2;
/*     */       case 7:
/* 726 */         return opcode + 1;
/*     */       case 8:
/* 728 */         return opcode + 3;
/*     */     } 
/*     */ 
/*     */     
/* 732 */     return opcode + 4;
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
/*     */   public boolean equals(Object o) {
/* 748 */     if (this == o) {
/* 749 */       return true;
/*     */     }
/* 751 */     if (!(o instanceof Type)) {
/* 752 */       return false;
/*     */     }
/* 754 */     Type t = (Type)o;
/* 755 */     if (this.sort != t.sort) {
/* 756 */       return false;
/*     */     }
/* 758 */     if (this.sort == 10 || this.sort == 9) {
/* 759 */       if (this.len != t.len) {
/* 760 */         return false;
/*     */       }
/* 762 */       for (int i = this.off, j = t.off, end = i + this.len; i < end; i++, j++) {
/* 763 */         if (this.buf[i] != t.buf[j]) {
/* 764 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 768 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 777 */     int hc = 13 * this.sort;
/* 778 */     if (this.sort == 10 || this.sort == 9) {
/* 779 */       for (int i = this.off, end = i + this.len; i < end; i++) {
/* 780 */         hc = 17 * (hc + this.buf[i]);
/*     */       }
/*     */     }
/* 783 */     return hc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 792 */     return getDescriptor();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\org\objectweb\asm\Type.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */