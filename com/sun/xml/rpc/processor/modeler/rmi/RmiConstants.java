/*     */ package com.sun.xml.rpc.processor.modeler.rmi;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.modeler.ModelerConstants;
/*     */ import java.io.Serializable;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import javax.xml.rpc.holders.Holder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface RmiConstants
/*     */   extends ModelerConstants
/*     */ {
/*     */   public static final int TC_BOOLEAN = 0;
/*     */   public static final int TC_BYTE = 1;
/*     */   public static final int TC_CHAR = 2;
/*     */   public static final int TC_SHORT = 3;
/*     */   public static final int TC_INT = 4;
/*     */   public static final int TC_LONG = 5;
/*     */   public static final int TC_FLOAT = 6;
/*     */   public static final int TC_DOUBLE = 7;
/*     */   public static final int TC_NULL = 8;
/*     */   public static final int TC_ARRAY = 9;
/*     */   public static final int TC_CLASS = 10;
/*     */   public static final int TC_VOID = 11;
/*     */   public static final int TC_METHOD = 12;
/*     */   public static final int TC_ERROR = 13;
/*     */   public static final char SIGC_VOID = 'V';
/*     */   public static final String SIG_VOID = "V";
/*     */   public static final char SIGC_BOOLEAN = 'Z';
/*     */   public static final String SIG_BOOLEAN = "Z";
/*     */   public static final char SIGC_BYTE = 'B';
/*     */   public static final String SIG_BYTE = "B";
/*     */   public static final char SIGC_CHAR = 'C';
/*     */   public static final String SIG_CHAR = "C";
/*     */   public static final char SIGC_SHORT = 'S';
/*     */   public static final String SIG_SHORT = "S";
/*     */   public static final char SIGC_INT = 'I';
/*     */   public static final String SIG_INT = "I";
/*     */   public static final char SIGC_LONG = 'J';
/*     */   public static final String SIG_LONG = "J";
/*     */   public static final char SIGC_FLOAT = 'F';
/*     */   public static final String SIG_FLOAT = "F";
/*     */   public static final char SIGC_DOUBLE = 'D';
/*     */   public static final String SIG_DOUBLE = "D";
/*     */   public static final char SIGC_ARRAY = '[';
/*     */   public static final String SIG_ARRAY = "[";
/*     */   public static final char SIGC_CLASS = 'L';
/*     */   public static final String SIG_CLASS = "L";
/*     */   public static final char SIGC_METHOD = '(';
/*     */   public static final String SIG_METHOD = "(";
/*     */   public static final char SIGC_ENDCLASS = ';';
/*     */   public static final String SIG_ENDCLASS = ";";
/*     */   public static final char SIGC_ENDMETHOD = ')';
/*     */   public static final String SIG_ENDMETHOD = ")";
/*     */   public static final char SIGC_PACKAGE = '/';
/*     */   public static final String SIG_PACKAGE = "/";
/*     */   public static final char SIGC_INNERCLASS = '$';
/*     */   public static final String SIG_INNERCLASS = "$";
/*     */   public static final char SIGC_UNDERSCORE = '_';
/*     */   public static final String RMI_MODELER_NESTED_RMI_MODELER_ERROR = "rmimodeler.nestedRmiModelerError";
/*     */   public static final String RMI_MODELER_CLASS_NOT_FOUND = "rmimodeler.class.not.found";
/*     */   public static final String RMI_MODELER_NESTED_INNER_CLASSES_NOT_SUPPORTED = "rmimodeler.nested.inner.classes.not.supported";
/*     */   public static final String RMI_MODELER_INVALID_REMOTE_INTERFACE = "rmimodeler.invalid.remote.interface";
/*     */   public static final String EMPTY_STRING = "";
/*     */   public static final String DOT = ".";
/*     */   public static final char DOTC = '.';
/*     */   public static final String UNDERSCORE = "_";
/*     */   public static final String IMPL = "Impl";
/*     */   public static final String RESPONSE_STRUCT = "_ResponseStruct";
/*     */   public static final String REQUEST_STRUCT = "_RequestStruct";
/*     */   public static final String RESULT = "result";
/*     */   public static final String ARRAY_OF = "arrayOf";
/*     */   public static final String PORT = "Port";
/*     */   public static final String BINDING = "Binding";
/*     */   public static final String RESPONSE = "Response";
/*     */   public static final String ELEMENT = "Element";
/* 115 */   public static final String EXCEPTION_CLASSNAME = Exception.class.getName();
/*     */   
/* 117 */   public static final String REMOTE_CLASSNAME = Remote.class.getName();
/*     */   
/* 119 */   public static final String REMOTE_EXCEPTION_CLASSNAME = RemoteException.class.getName();
/*     */   
/* 121 */   public static final String RUNTIME_EXCEPTION_CLASSNAME = RuntimeException.class.getName();
/*     */   
/* 123 */   public static final String SERIALIZABLE_CLASSNAME = Serializable.class.getName();
/*     */   
/* 125 */   public static final String HOLDER_CLASSNAME = Holder.class.getName();
/*     */   public static final String GET_MESSAGE = "getMessage";
/*     */   public static final String GET_LOCALIZED_MESSAGE = "getLocalizedMessage";
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\rmi\RmiConstants.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */