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
/*    */ public class FieldAccessor_Double
/*    */   extends Accessor
/*    */ {
/*    */   public FieldAccessor_Double() {
/* 55 */     super(Double.class);
/*    */   }
/*    */   
/*    */   public Object get(Object bean) {
/* 59 */     return Double.valueOf(((Bean)bean).f_double);
/*    */   }
/*    */   
/*    */   public void set(Object bean, Object value) {
/* 63 */     ((Bean)bean).f_double = (value == null) ? Const.default_value_double : ((Double)value).doubleValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\reflect\opt\FieldAccessor_Double.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */