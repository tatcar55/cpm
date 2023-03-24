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
/*    */ public class FieldAccessor_Ref
/*    */   extends Accessor
/*    */ {
/*    */   public FieldAccessor_Ref() {
/* 52 */     super(Ref.class);
/*    */   }
/*    */   
/*    */   public Object get(Object bean) {
/* 56 */     return ((Bean)bean).f_ref;
/*    */   }
/*    */   
/*    */   public void set(Object bean, Object value) {
/* 60 */     ((Bean)bean).f_ref = (Ref)value;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\reflect\opt\FieldAccessor_Ref.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */