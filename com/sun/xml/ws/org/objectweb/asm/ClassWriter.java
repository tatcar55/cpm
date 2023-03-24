/*      */ package com.sun.xml.ws.org.objectweb.asm;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ClassWriter
/*      */   implements ClassVisitor
/*      */ {
/*      */   public static final int COMPUTE_MAXS = 1;
/*      */   public static final int COMPUTE_FRAMES = 2;
/*      */   static final int NOARG_INSN = 0;
/*      */   static final int SBYTE_INSN = 1;
/*      */   static final int SHORT_INSN = 2;
/*      */   static final int VAR_INSN = 3;
/*      */   static final int IMPLVAR_INSN = 4;
/*      */   static final int TYPE_INSN = 5;
/*      */   static final int FIELDORMETH_INSN = 6;
/*      */   static final int ITFMETH_INSN = 7;
/*      */   static final int LABEL_INSN = 8;
/*      */   static final int LABELW_INSN = 9;
/*      */   static final int LDC_INSN = 10;
/*      */   static final int LDCW_INSN = 11;
/*      */   static final int IINC_INSN = 12;
/*      */   static final int TABL_INSN = 13;
/*      */   static final int LOOK_INSN = 14;
/*      */   static final int MANA_INSN = 15;
/*      */   static final int WIDE_INSN = 16;
/*      */   static final byte[] TYPE;
/*      */   static final int CLASS = 7;
/*      */   static final int FIELD = 9;
/*      */   static final int METH = 10;
/*      */   static final int IMETH = 11;
/*      */   static final int STR = 8;
/*      */   static final int INT = 3;
/*      */   static final int FLOAT = 4;
/*      */   static final int LONG = 5;
/*      */   static final int DOUBLE = 6;
/*      */   static final int NAME_TYPE = 12;
/*      */   static final int UTF8 = 1;
/*      */   static final int TYPE_NORMAL = 13;
/*      */   static final int TYPE_UNINIT = 14;
/*      */   static final int TYPE_MERGED = 15;
/*      */   ClassReader cr;
/*      */   int version;
/*      */   int index;
/*      */   final ByteVector pool;
/*      */   Item[] items;
/*      */   int threshold;
/*      */   final Item key;
/*      */   final Item key2;
/*      */   final Item key3;
/*      */   Item[] typeTable;
/*      */   private short typeCount;
/*      */   private int access;
/*      */   private int name;
/*      */   String thisName;
/*      */   private int signature;
/*      */   private int superName;
/*      */   private int interfaceCount;
/*      */   private int[] interfaces;
/*      */   private int sourceFile;
/*      */   private ByteVector sourceDebug;
/*      */   private int enclosingMethodOwner;
/*      */   private int enclosingMethod;
/*      */   private AnnotationWriter anns;
/*      */   private AnnotationWriter ianns;
/*      */   private Attribute attrs;
/*      */   private int innerClassesCount;
/*      */   private ByteVector innerClasses;
/*      */   FieldWriter firstField;
/*      */   FieldWriter lastField;
/*      */   MethodWriter firstMethod;
/*      */   MethodWriter lastMethod;
/*      */   private final boolean computeMaxs;
/*      */   private final boolean computeFrames;
/*      */   boolean invalidFrames;
/*      */   
/*      */   static {
/*  446 */     byte[] b = new byte[220];
/*  447 */     String s = "AAAAAAAAAAAAAAAABCKLLDDDDDEEEEEEEEEEEEEEEEEEEEAAAAAAAADDDDDEEEEEEEEEEEEEEEEEEEEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAMAAAAAAAAAAAAAAAAAAAAIIIIIIIIIIIIIIIIDNOAAAAAAGGGGGGGHAFBFAAFFAAQPIIJJIIIIIIIIIIIIIIIIII";
/*      */ 
/*      */ 
/*      */     
/*  451 */     for (int i = 0; i < b.length; i++) {
/*  452 */       b[i] = (byte)(s.charAt(i) - 65);
/*      */     }
/*  454 */     TYPE = b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassWriter(int flags) {
/*  535 */     this.index = 1;
/*  536 */     this.pool = new ByteVector();
/*  537 */     this.items = new Item[256];
/*  538 */     this.threshold = (int)(0.75D * this.items.length);
/*  539 */     this.key = new Item();
/*  540 */     this.key2 = new Item();
/*  541 */     this.key3 = new Item();
/*  542 */     this.computeMaxs = ((flags & 0x1) != 0);
/*  543 */     this.computeFrames = ((flags & 0x2) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassWriter(ClassReader classReader, int flags) {
/*  571 */     this(flags);
/*  572 */     classReader.copyPool(this);
/*  573 */     this.cr = classReader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
/*  588 */     this.version = version;
/*  589 */     this.access = access;
/*  590 */     this.name = newClass(name);
/*  591 */     this.thisName = name;
/*  592 */     if (signature != null) {
/*  593 */       this.signature = newUTF8(signature);
/*      */     }
/*  595 */     this.superName = (superName == null) ? 0 : newClass(superName);
/*  596 */     if (interfaces != null && interfaces.length > 0) {
/*  597 */       this.interfaceCount = interfaces.length;
/*  598 */       this.interfaces = new int[this.interfaceCount];
/*  599 */       for (int i = 0; i < this.interfaceCount; i++) {
/*  600 */         this.interfaces[i] = newClass(interfaces[i]);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void visitSource(String file, String debug) {
/*  606 */     if (file != null) {
/*  607 */       this.sourceFile = newUTF8(file);
/*      */     }
/*  609 */     if (debug != null) {
/*  610 */       this.sourceDebug = (new ByteVector()).putUTF8(debug);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitOuterClass(String owner, String name, String desc) {
/*  619 */     this.enclosingMethodOwner = newClass(owner);
/*  620 */     if (name != null && desc != null) {
/*  621 */       this.enclosingMethod = newNameType(name, desc);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/*  632 */     ByteVector bv = new ByteVector();
/*      */     
/*  634 */     bv.putShort(newUTF8(desc)).putShort(0);
/*  635 */     AnnotationWriter aw = new AnnotationWriter(this, true, bv, bv, 2);
/*  636 */     if (visible) {
/*  637 */       aw.next = this.anns;
/*  638 */       this.anns = aw;
/*      */     } else {
/*  640 */       aw.next = this.ianns;
/*  641 */       this.ianns = aw;
/*      */     } 
/*  643 */     return aw;
/*      */   }
/*      */   
/*      */   public void visitAttribute(Attribute attr) {
/*  647 */     attr.next = this.attrs;
/*  648 */     this.attrs = attr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitInnerClass(String name, String outerName, String innerName, int access) {
/*  657 */     if (this.innerClasses == null) {
/*  658 */       this.innerClasses = new ByteVector();
/*      */     }
/*  660 */     this.innerClassesCount++;
/*  661 */     this.innerClasses.putShort((name == null) ? 0 : newClass(name));
/*  662 */     this.innerClasses.putShort((outerName == null) ? 0 : newClass(outerName));
/*  663 */     this.innerClasses.putShort((innerName == null) ? 0 : newUTF8(innerName));
/*  664 */     this.innerClasses.putShort(access);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
/*  674 */     return new FieldWriter(this, access, name, desc, signature, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
/*  684 */     return new MethodWriter(this, access, name, desc, signature, exceptions, this.computeMaxs, this.computeFrames);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitEnd() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] toByteArray() {
/*  708 */     int size = 24 + 2 * this.interfaceCount;
/*  709 */     int nbFields = 0;
/*  710 */     FieldWriter fb = this.firstField;
/*  711 */     while (fb != null) {
/*  712 */       nbFields++;
/*  713 */       size += fb.getSize();
/*  714 */       fb = fb.next;
/*      */     } 
/*  716 */     int nbMethods = 0;
/*  717 */     MethodWriter mb = this.firstMethod;
/*  718 */     while (mb != null) {
/*  719 */       nbMethods++;
/*  720 */       size += mb.getSize();
/*  721 */       mb = mb.next;
/*      */     } 
/*  723 */     int attributeCount = 0;
/*  724 */     if (this.signature != 0) {
/*  725 */       attributeCount++;
/*  726 */       size += 8;
/*  727 */       newUTF8("Signature");
/*      */     } 
/*  729 */     if (this.sourceFile != 0) {
/*  730 */       attributeCount++;
/*  731 */       size += 8;
/*  732 */       newUTF8("SourceFile");
/*      */     } 
/*  734 */     if (this.sourceDebug != null) {
/*  735 */       attributeCount++;
/*  736 */       size += this.sourceDebug.length + 4;
/*  737 */       newUTF8("SourceDebugExtension");
/*      */     } 
/*  739 */     if (this.enclosingMethodOwner != 0) {
/*  740 */       attributeCount++;
/*  741 */       size += 10;
/*  742 */       newUTF8("EnclosingMethod");
/*      */     } 
/*  744 */     if ((this.access & 0x20000) != 0) {
/*  745 */       attributeCount++;
/*  746 */       size += 6;
/*  747 */       newUTF8("Deprecated");
/*      */     } 
/*  749 */     if ((this.access & 0x1000) != 0 && (this.version & 0xFFFF) < 49) {
/*      */ 
/*      */       
/*  752 */       attributeCount++;
/*  753 */       size += 6;
/*  754 */       newUTF8("Synthetic");
/*      */     } 
/*  756 */     if (this.innerClasses != null) {
/*  757 */       attributeCount++;
/*  758 */       size += 8 + this.innerClasses.length;
/*  759 */       newUTF8("InnerClasses");
/*      */     } 
/*  761 */     if (this.anns != null) {
/*  762 */       attributeCount++;
/*  763 */       size += 8 + this.anns.getSize();
/*  764 */       newUTF8("RuntimeVisibleAnnotations");
/*      */     } 
/*  766 */     if (this.ianns != null) {
/*  767 */       attributeCount++;
/*  768 */       size += 8 + this.ianns.getSize();
/*  769 */       newUTF8("RuntimeInvisibleAnnotations");
/*      */     } 
/*  771 */     if (this.attrs != null) {
/*  772 */       attributeCount += this.attrs.getCount();
/*  773 */       size += this.attrs.getSize(this, null, 0, -1, -1);
/*      */     } 
/*  775 */     size += this.pool.length;
/*      */ 
/*      */     
/*  778 */     ByteVector out = new ByteVector(size);
/*  779 */     out.putInt(-889275714).putInt(this.version);
/*  780 */     out.putShort(this.index).putByteArray(this.pool.data, 0, this.pool.length);
/*  781 */     out.putShort(this.access).putShort(this.name).putShort(this.superName);
/*  782 */     out.putShort(this.interfaceCount);
/*  783 */     for (int i = 0; i < this.interfaceCount; i++) {
/*  784 */       out.putShort(this.interfaces[i]);
/*      */     }
/*  786 */     out.putShort(nbFields);
/*  787 */     fb = this.firstField;
/*  788 */     while (fb != null) {
/*  789 */       fb.put(out);
/*  790 */       fb = fb.next;
/*      */     } 
/*  792 */     out.putShort(nbMethods);
/*  793 */     mb = this.firstMethod;
/*  794 */     while (mb != null) {
/*  795 */       mb.put(out);
/*  796 */       mb = mb.next;
/*      */     } 
/*  798 */     out.putShort(attributeCount);
/*  799 */     if (this.signature != 0) {
/*  800 */       out.putShort(newUTF8("Signature")).putInt(2).putShort(this.signature);
/*      */     }
/*  802 */     if (this.sourceFile != 0) {
/*  803 */       out.putShort(newUTF8("SourceFile")).putInt(2).putShort(this.sourceFile);
/*      */     }
/*  805 */     if (this.sourceDebug != null) {
/*  806 */       int len = this.sourceDebug.length - 2;
/*  807 */       out.putShort(newUTF8("SourceDebugExtension")).putInt(len);
/*  808 */       out.putByteArray(this.sourceDebug.data, 2, len);
/*      */     } 
/*  810 */     if (this.enclosingMethodOwner != 0) {
/*  811 */       out.putShort(newUTF8("EnclosingMethod")).putInt(4);
/*  812 */       out.putShort(this.enclosingMethodOwner).putShort(this.enclosingMethod);
/*      */     } 
/*  814 */     if ((this.access & 0x20000) != 0) {
/*  815 */       out.putShort(newUTF8("Deprecated")).putInt(0);
/*      */     }
/*  817 */     if ((this.access & 0x1000) != 0 && (this.version & 0xFFFF) < 49)
/*      */     {
/*      */       
/*  820 */       out.putShort(newUTF8("Synthetic")).putInt(0);
/*      */     }
/*  822 */     if (this.innerClasses != null) {
/*  823 */       out.putShort(newUTF8("InnerClasses"));
/*  824 */       out.putInt(this.innerClasses.length + 2).putShort(this.innerClassesCount);
/*  825 */       out.putByteArray(this.innerClasses.data, 0, this.innerClasses.length);
/*      */     } 
/*  827 */     if (this.anns != null) {
/*  828 */       out.putShort(newUTF8("RuntimeVisibleAnnotations"));
/*  829 */       this.anns.put(out);
/*      */     } 
/*  831 */     if (this.ianns != null) {
/*  832 */       out.putShort(newUTF8("RuntimeInvisibleAnnotations"));
/*  833 */       this.ianns.put(out);
/*      */     } 
/*  835 */     if (this.attrs != null) {
/*  836 */       this.attrs.put(this, null, 0, -1, -1, out);
/*      */     }
/*  838 */     if (this.invalidFrames) {
/*  839 */       ClassWriter cw = new ClassWriter(2);
/*  840 */       (new ClassReader(out.data)).accept(cw, 4);
/*  841 */       return cw.toByteArray();
/*      */     } 
/*  843 */     return out.data;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Item newConstItem(Object cst) {
/*  861 */     if (cst instanceof Integer) {
/*  862 */       int val = ((Integer)cst).intValue();
/*  863 */       return newInteger(val);
/*  864 */     }  if (cst instanceof Byte) {
/*  865 */       int val = ((Byte)cst).intValue();
/*  866 */       return newInteger(val);
/*  867 */     }  if (cst instanceof Character) {
/*  868 */       int val = ((Character)cst).charValue();
/*  869 */       return newInteger(val);
/*  870 */     }  if (cst instanceof Short) {
/*  871 */       int val = ((Short)cst).intValue();
/*  872 */       return newInteger(val);
/*  873 */     }  if (cst instanceof Boolean) {
/*  874 */       int val = ((Boolean)cst).booleanValue() ? 1 : 0;
/*  875 */       return newInteger(val);
/*  876 */     }  if (cst instanceof Float) {
/*  877 */       float val = ((Float)cst).floatValue();
/*  878 */       return newFloat(val);
/*  879 */     }  if (cst instanceof Long) {
/*  880 */       long val = ((Long)cst).longValue();
/*  881 */       return newLong(val);
/*  882 */     }  if (cst instanceof Double) {
/*  883 */       double val = ((Double)cst).doubleValue();
/*  884 */       return newDouble(val);
/*  885 */     }  if (cst instanceof String)
/*  886 */       return newString((String)cst); 
/*  887 */     if (cst instanceof Type) {
/*  888 */       Type t = (Type)cst;
/*  889 */       return newClassItem((t.getSort() == 10) ? t.getInternalName() : t.getDescriptor());
/*      */     } 
/*      */ 
/*      */     
/*  893 */     throw new IllegalArgumentException("value " + cst);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int newConst(Object cst) {
/*  910 */     return (newConstItem(cst)).index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int newUTF8(String value) {
/*  923 */     this.key.set(1, value, null, null);
/*  924 */     Item result = get(this.key);
/*  925 */     if (result == null) {
/*  926 */       this.pool.putByte(1).putUTF8(value);
/*  927 */       result = new Item(this.index++, this.key);
/*  928 */       put(result);
/*      */     } 
/*  930 */     return result.index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Item newClassItem(String value) {
/*  943 */     this.key2.set(7, value, null, null);
/*  944 */     Item result = get(this.key2);
/*  945 */     if (result == null) {
/*  946 */       this.pool.put12(7, newUTF8(value));
/*  947 */       result = new Item(this.index++, this.key2);
/*  948 */       put(result);
/*      */     } 
/*  950 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int newClass(String value) {
/*  963 */     return (newClassItem(value)).index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Item newFieldItem(String owner, String name, String desc) {
/*  977 */     this.key3.set(9, owner, name, desc);
/*  978 */     Item result = get(this.key3);
/*  979 */     if (result == null) {
/*  980 */       put122(9, newClass(owner), newNameType(name, desc));
/*  981 */       result = new Item(this.index++, this.key3);
/*  982 */       put(result);
/*      */     } 
/*  984 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int newField(String owner, String name, String desc) {
/* 1000 */     return (newFieldItem(owner, name, desc)).index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Item newMethodItem(String owner, String name, String desc, boolean itf) {
/* 1019 */     int type = itf ? 11 : 10;
/* 1020 */     this.key3.set(type, owner, name, desc);
/* 1021 */     Item result = get(this.key3);
/* 1022 */     if (result == null) {
/* 1023 */       put122(type, newClass(owner), newNameType(name, desc));
/* 1024 */       result = new Item(this.index++, this.key3);
/* 1025 */       put(result);
/*      */     } 
/* 1027 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int newMethod(String owner, String name, String desc, boolean itf) {
/* 1048 */     return (newMethodItem(owner, name, desc, itf)).index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Item newInteger(int value) {
/* 1059 */     this.key.set(value);
/* 1060 */     Item result = get(this.key);
/* 1061 */     if (result == null) {
/* 1062 */       this.pool.putByte(3).putInt(value);
/* 1063 */       result = new Item(this.index++, this.key);
/* 1064 */       put(result);
/*      */     } 
/* 1066 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Item newFloat(float value) {
/* 1077 */     this.key.set(value);
/* 1078 */     Item result = get(this.key);
/* 1079 */     if (result == null) {
/* 1080 */       this.pool.putByte(4).putInt(this.key.intVal);
/* 1081 */       result = new Item(this.index++, this.key);
/* 1082 */       put(result);
/*      */     } 
/* 1084 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Item newLong(long value) {
/* 1095 */     this.key.set(value);
/* 1096 */     Item result = get(this.key);
/* 1097 */     if (result == null) {
/* 1098 */       this.pool.putByte(5).putLong(value);
/* 1099 */       result = new Item(this.index, this.key);
/* 1100 */       put(result);
/* 1101 */       this.index += 2;
/*      */     } 
/* 1103 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Item newDouble(double value) {
/* 1114 */     this.key.set(value);
/* 1115 */     Item result = get(this.key);
/* 1116 */     if (result == null) {
/* 1117 */       this.pool.putByte(6).putLong(this.key.longVal);
/* 1118 */       result = new Item(this.index, this.key);
/* 1119 */       put(result);
/* 1120 */       this.index += 2;
/*      */     } 
/* 1122 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Item newString(String value) {
/* 1133 */     this.key2.set(8, value, null, null);
/* 1134 */     Item result = get(this.key2);
/* 1135 */     if (result == null) {
/* 1136 */       this.pool.put12(8, newUTF8(value));
/* 1137 */       result = new Item(this.index++, this.key2);
/* 1138 */       put(result);
/*      */     } 
/* 1140 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int newNameType(String name, String desc) {
/* 1154 */     this.key2.set(12, name, desc, null);
/* 1155 */     Item result = get(this.key2);
/* 1156 */     if (result == null) {
/* 1157 */       put122(12, newUTF8(name), newUTF8(desc));
/* 1158 */       result = new Item(this.index++, this.key2);
/* 1159 */       put(result);
/*      */     } 
/* 1161 */     return result.index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int addType(String type) {
/* 1172 */     this.key.set(13, type, null, null);
/* 1173 */     Item result = get(this.key);
/* 1174 */     if (result == null) {
/* 1175 */       result = addType(this.key);
/*      */     }
/* 1177 */     return result.index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int addUninitializedType(String type, int offset) {
/* 1191 */     this.key.type = 14;
/* 1192 */     this.key.intVal = offset;
/* 1193 */     this.key.strVal1 = type;
/* 1194 */     this.key.hashCode = Integer.MAX_VALUE & 14 + type.hashCode() + offset;
/* 1195 */     Item result = get(this.key);
/* 1196 */     if (result == null) {
/* 1197 */       result = addType(this.key);
/*      */     }
/* 1199 */     return result.index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Item addType(Item item) {
/* 1210 */     this.typeCount = (short)(this.typeCount + 1);
/* 1211 */     Item result = new Item(this.typeCount, this.key);
/* 1212 */     put(result);
/* 1213 */     if (this.typeTable == null) {
/* 1214 */       this.typeTable = new Item[16];
/*      */     }
/* 1216 */     if (this.typeCount == this.typeTable.length) {
/* 1217 */       Item[] newTable = new Item[2 * this.typeTable.length];
/* 1218 */       System.arraycopy(this.typeTable, 0, newTable, 0, this.typeTable.length);
/* 1219 */       this.typeTable = newTable;
/*      */     } 
/* 1221 */     this.typeTable[this.typeCount] = result;
/* 1222 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getMergedType(int type1, int type2) {
/* 1236 */     this.key2.type = 15;
/* 1237 */     this.key2.longVal = type1 | type2 << 32L;
/* 1238 */     this.key2.hashCode = Integer.MAX_VALUE & 15 + type1 + type2;
/* 1239 */     Item result = get(this.key2);
/* 1240 */     if (result == null) {
/* 1241 */       String t = (this.typeTable[type1]).strVal1;
/* 1242 */       String u = (this.typeTable[type2]).strVal1;
/* 1243 */       this.key2.intVal = addType(getCommonSuperClass(t, u));
/* 1244 */       result = new Item(0, this.key2);
/* 1245 */       put(result);
/*      */     } 
/* 1247 */     return result.intVal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getCommonSuperClass(String type1, String type2) {
/*      */     Class<?> c;
/*      */     Class<?> d;
/*      */     try {
/* 1268 */       c = Class.forName(type1.replace('/', '.'));
/* 1269 */       d = Class.forName(type2.replace('/', '.'));
/* 1270 */     } catch (Exception e) {
/* 1271 */       throw new RuntimeException(e.toString());
/*      */     } 
/* 1273 */     if (c.isAssignableFrom(d)) {
/* 1274 */       return type1;
/*      */     }
/* 1276 */     if (d.isAssignableFrom(c)) {
/* 1277 */       return type2;
/*      */     }
/* 1279 */     if (c.isInterface() || d.isInterface()) {
/* 1280 */       return "java/lang/Object";
/*      */     }
/*      */     while (true) {
/* 1283 */       c = c.getSuperclass();
/* 1284 */       if (c.isAssignableFrom(d)) {
/* 1285 */         return c.getName().replace('.', '/');
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Item get(Item key) {
/* 1298 */     Item i = this.items[key.hashCode % this.items.length];
/* 1299 */     while (i != null && !key.isEqualTo(i)) {
/* 1300 */       i = i.next;
/*      */     }
/* 1302 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void put(Item i) {
/* 1312 */     if (this.index > this.threshold) {
/* 1313 */       int ll = this.items.length;
/* 1314 */       int nl = ll * 2 + 1;
/* 1315 */       Item[] newItems = new Item[nl];
/* 1316 */       for (int l = ll - 1; l >= 0; l--) {
/* 1317 */         Item j = this.items[l];
/* 1318 */         while (j != null) {
/* 1319 */           int m = j.hashCode % newItems.length;
/* 1320 */           Item k = j.next;
/* 1321 */           j.next = newItems[m];
/* 1322 */           newItems[m] = j;
/* 1323 */           j = k;
/*      */         } 
/*      */       } 
/* 1326 */       this.items = newItems;
/* 1327 */       this.threshold = (int)(nl * 0.75D);
/*      */     } 
/* 1329 */     int index = i.hashCode % this.items.length;
/* 1330 */     i.next = this.items[index];
/* 1331 */     this.items[index] = i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void put122(int b, int s1, int s2) {
/* 1342 */     this.pool.put12(b, s1).putShort(s2);
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\org\objectweb\asm\ClassWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */