/*    */ package com.sun.xml.ws.api.policy;
/*    */ 
/*    */ import com.sun.xml.ws.policy.EffectiveAlternativeSelector;
/*    */ import com.sun.xml.ws.policy.EffectivePolicyModifier;
/*    */ import com.sun.xml.ws.policy.PolicyException;
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
/*    */ public class AlternativeSelector
/*    */   extends EffectiveAlternativeSelector
/*    */ {
/*    */   public static void doSelection(EffectivePolicyModifier modifier) throws PolicyException {
/* 54 */     ValidationProcessor validationProcessor = ValidationProcessor.getInstance();
/* 55 */     selectAlternatives(modifier, validationProcessor);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\policy\AlternativeSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */