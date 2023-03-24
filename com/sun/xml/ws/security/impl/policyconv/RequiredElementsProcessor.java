/*    */ package com.sun.xml.ws.security.impl.policyconv;
/*    */ 
/*    */ import com.sun.xml.ws.security.policy.RequiredElements;
/*    */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*    */ import com.sun.xml.wss.impl.policy.PolicyGenerationException;
/*    */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*    */ import com.sun.xml.wss.impl.policy.mls.MandatoryTargetPolicy;
/*    */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*    */ import com.sun.xml.wss.impl.policy.mls.Target;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Vector;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RequiredElementsProcessor
/*    */ {
/*    */   private List<RequiredElements> assertionList;
/*    */   private MessagePolicy mp;
/*    */   
/*    */   public RequiredElementsProcessor(List<RequiredElements> al, MessagePolicy mp) {
/* 62 */     this.assertionList = al;
/* 63 */     this.mp = mp;
/*    */   }
/*    */   
/*    */   public void process() throws PolicyGenerationException {
/* 67 */     Vector<String> targetValues = new Vector<String>();
/* 68 */     MandatoryTargetPolicy mt = new MandatoryTargetPolicy();
/* 69 */     MandatoryTargetPolicy.FeatureBinding mfb = new MandatoryTargetPolicy.FeatureBinding();
/* 70 */     mt.setFeatureBinding((MLSPolicy)mfb);
/* 71 */     List<Target> targets = mfb.getTargetBindings();
/* 72 */     for (RequiredElements re : this.assertionList) {
/* 73 */       Iterator<String> itr = re.getTargets();
/* 74 */       while (itr.hasNext()) {
/* 75 */         String xpathExpr = itr.next();
/* 76 */         if (!targetValues.contains(xpathExpr)) {
/* 77 */           targetValues.add(xpathExpr);
/* 78 */           Target tr = new Target();
/* 79 */           tr.setType("xpath");
/* 80 */           tr.setValue(xpathExpr);
/* 81 */           tr.setContentOnly(false);
/* 82 */           tr.setEnforce(true);
/* 83 */           targets.add(tr);
/*    */         } 
/*    */       } 
/*    */     } 
/* 87 */     this.mp.append((SecurityPolicy)mt);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policyconv\RequiredElementsProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */