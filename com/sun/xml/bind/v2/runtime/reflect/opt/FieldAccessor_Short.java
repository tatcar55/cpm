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
/*    */ public class FieldAccessor_Short
/*    */   extends Accessor
/*    */ {
/*    */   public FieldAccessor_Short() {
/* 55 */     super(Short.class);
/*    */   }
/*    */   
/*    */   public Object get(Object bean) {
/* 59 */     return Short.valueOf(((Bean)bean).f_short);
/*    */   }
/*    */   
/*    */   public void set(Object bean, Object value) {
/* 63 */     ((Bean)bean).f_short = (value == null) ? Const.default_value_short : ((Short)value).shortValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\reflect\opt\FieldAccessor_Short.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */