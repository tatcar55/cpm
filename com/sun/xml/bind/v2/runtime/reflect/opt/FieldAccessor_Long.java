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
/*    */ public class FieldAccessor_Long
/*    */   extends Accessor
/*    */ {
/*    */   public FieldAccessor_Long() {
/* 55 */     super(Long.class);
/*    */   }
/*    */   
/*    */   public Object get(Object bean) {
/* 59 */     return Long.valueOf(((Bean)bean).f_long);
/*    */   }
/*    */   
/*    */   public void set(Object bean, Object value) {
/* 63 */     ((Bean)bean).f_long = (value == null) ? Const.default_value_long : ((Long)value).longValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\reflect\opt\FieldAccessor_Long.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */