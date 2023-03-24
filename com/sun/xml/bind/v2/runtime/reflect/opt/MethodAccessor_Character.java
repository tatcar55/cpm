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
/*    */ public class MethodAccessor_Character
/*    */   extends Accessor
/*    */ {
/*    */   public MethodAccessor_Character() {
/* 55 */     super(Character.class);
/*    */   }
/*    */   
/*    */   public Object get(Object bean) {
/* 59 */     return Character.valueOf(((Bean)bean).get_char());
/*    */   }
/*    */   
/*    */   public void set(Object bean, Object value) {
/* 63 */     ((Bean)bean).set_char((value == null) ? Const.default_value_char : ((Character)value).charValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\reflect\opt\MethodAccessor_Character.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */