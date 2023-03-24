/*    */ package com.sun.xml.bind.v2.runtime.reflect.opt;
/*    */ 
/*    */ import com.sun.xml.bind.v2.runtime.reflect.Accessor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FieldAccessor_Byte
/*    */   extends Accessor
/*    */ {
/*    */   public FieldAccessor_Byte() {
/* 55 */     super(Byte.class);
/*    */   }
/*    */   
/*    */   public Object get(Object bean) {
/* 59 */     return Byte.valueOf(((Bean)bean).f_byte);
/*    */   }
/*    */   
/*    */   public void set(Object bean, Object value) {
/* 63 */     ((Bean)bean).f_byte = (value == null) ? Const.default_value_byte : ((Byte)value).byteValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\reflect\opt\FieldAccessor_Byte.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */