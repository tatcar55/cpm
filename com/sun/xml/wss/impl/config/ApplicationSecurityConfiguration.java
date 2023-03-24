/*     */ package com.sun.xml.wss.impl.config;
/*     */ 
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.configuration.StaticApplicationContext;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicyContainer;
/*     */ import com.sun.xml.wss.impl.policy.StaticPolicyContext;
/*     */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Logger;
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
/*     */ public class ApplicationSecurityConfiguration
/*     */   extends SecurityPolicyContainer
/*     */ {
/*  73 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */   
/*     */   boolean bsp = false;
/*     */ 
/*     */   
/*     */   boolean useCacheFlag = false;
/*     */   
/*  81 */   String envHandlerClassName = null;
/*     */   
/*     */   boolean optimize = false;
/*     */   
/*     */   private boolean retainSecHeader = false;
/*     */   
/*     */   private boolean resetMU = false;
/*     */   
/*     */   private Hashtable augmentedCtx2PolicyMap;
/*     */   
/*     */   private SecurityPolicy configForSingleServiceNoPorts;
/*     */   
/*     */   private boolean sSNP;
/*     */   
/*     */   private boolean hasOps;
/*     */   
/*     */   private Collection allReceiverPolicies;
/*     */   
/*     */   private ArrayList servicesList;
/*     */ 
/*     */   
/*     */   public void setSecurityEnvironmentHandler(String handlerClassName) {
/* 103 */     this.envHandlerClassName = handlerClassName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSecurityEnvironmentHandler() {
/* 111 */     return this.envHandlerClassName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection getAllTopLevelApplicationSecurityConfigurations() {
/* 120 */     return this.servicesList;
/*     */   }
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
/*     */   public Collection getAllPolicies() {
/* 150 */     Collection c = this._ctx2PolicyMap.values();
/* 151 */     Collection<SecurityPolicy> d = new ArrayList();
/* 152 */     Iterator<ArrayList> itr = c.iterator();
/* 153 */     while (itr.hasNext()) {
/* 154 */       ArrayList<SecurityPolicy> list = itr.next();
/* 155 */       for (int i = 0; i < list.size(); i++) {
/* 156 */         SecurityPolicy policy = list.get(i);
/* 157 */         if (PolicyTypeUtil.applicationSecurityConfiguration(policy)) {
/* 158 */           d.addAll(((ApplicationSecurityConfiguration)policy).getAllPolicies());
/*     */         } else {
/* 160 */           d.add(policy);
/*     */         } 
/*     */       } 
/*     */     } 
/* 164 */     return d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection getAllSenderPolicies() {
/* 172 */     Collection c = this._ctx2PolicyMap.values();
/* 173 */     Collection<MessagePolicy> d = new ArrayList();
/* 174 */     Iterator<ArrayList> itr = c.iterator();
/* 175 */     while (itr.hasNext()) {
/* 176 */       ArrayList<SecurityPolicy> list = itr.next();
/* 177 */       for (int i = 0; i < list.size(); i++) {
/* 178 */         SecurityPolicy policy = list.get(i);
/* 179 */         if (PolicyTypeUtil.applicationSecurityConfiguration(policy)) {
/* 180 */           d.addAll(((ApplicationSecurityConfiguration)policy).getAllSenderPolicies());
/*     */         } else {
/* 182 */           DeclarativeSecurityConfiguration dsc = (DeclarativeSecurityConfiguration)policy;
/* 183 */           MessagePolicy mp = dsc.senderSettings();
/* 184 */           d.add(mp);
/*     */         } 
/*     */       } 
/*     */     } 
/* 188 */     return d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection getAllReceiverPolicies() {
/* 197 */     if (this.allReceiverPolicies != null) {
/* 198 */       return this.allReceiverPolicies;
/*     */     }
/* 200 */     Collection c = this._ctx2PolicyMap.values();
/*     */     
/* 202 */     Collection<MessagePolicy> d = new ArrayList();
/* 203 */     Iterator<ArrayList> itr = c.iterator();
/* 204 */     while (itr.hasNext()) {
/* 205 */       ArrayList<SecurityPolicy> list = itr.next();
/* 206 */       for (int i = 0; i < list.size(); i++) {
/* 207 */         SecurityPolicy policy = list.get(i);
/* 208 */         if (PolicyTypeUtil.applicationSecurityConfiguration(policy)) {
/* 209 */           d.addAll(((ApplicationSecurityConfiguration)policy).getAllReceiverPolicies());
/*     */         }
/* 211 */         else if (PolicyTypeUtil.declarativeSecurityConfiguration(policy)) {
/* 212 */           DeclarativeSecurityConfiguration dsc = (DeclarativeSecurityConfiguration)policy;
/* 213 */           MessagePolicy mp = dsc.receiverSettings();
/* 214 */           if ((mp.getPrimaryPolicies().size() == 0 && mp.getSecondaryPolicies().size() == 0) || (mp.getPrimaryPolicies().size() != 0 && mp.getSecondaryPolicies().size() == 0) || (mp.getPrimaryPolicies().size() != 0 && mp.getSecondaryPolicies().size() != 0))
/*     */           {
/*     */             
/* 217 */             d.add(mp);
/*     */           }
/*     */         } else {
/*     */           
/* 221 */           d.add(policy);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 226 */     return d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 233 */     return this._ctx2PolicyMap.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBSP() {
/* 240 */     return this.bsp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void isBSP(boolean flag) {
/* 247 */     this.bsp = flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean useCache() {
/* 254 */     return this.useCacheFlag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void useCache(boolean flag) {
/* 261 */     this.useCacheFlag = flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean retainSecurityHeader() {
/* 268 */     return this.retainSecHeader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void retainSecurityHeader(boolean arg) {
/* 275 */     this.retainSecHeader = arg;
/*     */   }
/*     */   
/*     */   public ApplicationSecurityConfiguration() {
/* 279 */     this.augmentedCtx2PolicyMap = new Hashtable<Object, Object>();
/*     */ 
/*     */     
/* 282 */     this.configForSingleServiceNoPorts = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 367 */     this.sSNP = false;
/* 368 */     this.hasOps = true;
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
/* 448 */     this.allReceiverPolicies = null;
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
/* 461 */     this.servicesList = new ArrayList(); } public SecurityPolicy getSecurityConfiguration(StaticApplicationContext context) { if (this.configForSingleServiceNoPorts != null) return this.configForSingleServiceNoPorts;  SecurityPolicy sp = (SecurityPolicy)this.augmentedCtx2PolicyMap.get(context); if (sp != null) return sp;  sp = getDSCORDSP((ArrayList)this._ctx2PolicyMap.get(context)); if (sp != null) { this.augmentedCtx2PolicyMap.put(context, sp); return sp; }  StaticApplicationContext ctx = new StaticApplicationContext(context); if (ctx.isOperation()) { ctx.setOperationIdentifier(""); sp = getDSCORDSP((ArrayList)this._ctx2PolicyMap.get(ctx)); if (sp == null) { ctx.setPortIdentifier(""); sp = getDSCORDSP((ArrayList)this._ctx2PolicyMap.get(ctx)); }  } else if (ctx.isPort()) { ctx.setPortIdentifier(""); sp = getDSCORDSP((ArrayList)this._ctx2PolicyMap.get(ctx)); }  if (context != null && sp != null) this.augmentedCtx2PolicyMap.put(context, sp);  return sp; } public boolean hasOperationPolicies() { return this.hasOps; } public void hasOperationPolicies(boolean flag) { this.hasOps = flag; } public ApplicationSecurityConfiguration(String handlerClassName) { this.augmentedCtx2PolicyMap = new Hashtable<Object, Object>(); this.configForSingleServiceNoPorts = null; this.sSNP = false; this.hasOps = true; this.allReceiverPolicies = null; this.servicesList = new ArrayList();
/*     */     this.envHandlerClassName = handlerClassName; }
/*     */   
/*     */   public void singleServiceNoPorts(boolean flag) {
/*     */     this.sSNP = flag;
/*     */   }
/*     */   public void resetMustUnderstand(boolean value) {
/*     */     this.resetMU = value;
/*     */   }
/*     */   
/*     */   public void setSecurityPolicy(StaticPolicyContext ctx, SecurityPolicy policy) {
/* 472 */     if (ctx instanceof StaticApplicationContext && (
/* 473 */       (StaticApplicationContext)ctx).isService() && PolicyTypeUtil.applicationSecurityConfiguration(policy))
/*     */     {
/* 475 */       this.servicesList.add(policy);
/*     */     }
/*     */ 
/*     */     
/* 479 */     super.setSecurityPolicy(ctx, policy); } public boolean resetMustUnderstand() { return this.resetMU; }
/*     */   private boolean singleServiceNoPorts() { return this.sSNP; }
/*     */   private SecurityPolicy getDSCORDSP(ArrayList list) { if (list == null)
/*     */       return null;  Iterator<SecurityPolicy> i = list.iterator(); while (i.hasNext()) { SecurityPolicy policy = i.next(); if (PolicyTypeUtil.applicationSecurityConfiguration(policy))
/*     */         return ((ApplicationSecurityConfiguration)policy).getDSCORDSP();  }  return null; }
/*     */   private SecurityPolicy getDSCORDSP() { Collection c = this._ctx2PolicyMap.values(); Iterator<ArrayList> i = c.iterator(); while (i.hasNext()) { ArrayList<SecurityPolicy> al = i.next(); SecurityPolicy policy = al.iterator().next(); if (PolicyTypeUtil.declarativeSecurityConfiguration(policy) || PolicyTypeUtil.dynamicSecurityPolicy(policy))
/*     */         return policy;  }  return null; }
/*     */   public String getType() { return "ApplicationSecurityConfiguration"; }
/*     */   public void init() { setConfigForSingleServiceNoPorts(); this.allReceiverPolicies = getAllReceiverPoliciesFromConfig(); }
/* 488 */   private Collection getAllReceiverPoliciesFromConfig() { Collection d = new ArrayList();
/* 489 */     for (int i = 0; i < this.servicesList.size(); i++) {
/* 490 */       ApplicationSecurityConfiguration policy = this.servicesList.get(i);
/* 491 */       d.addAll(policy.getAllReceiverPolicies());
/*     */     } 
/* 493 */     return d; }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setConfigForSingleServiceNoPorts() {
/* 498 */     if (singleServiceNoPorts()) {
/* 499 */       Collection<ArrayList> c = this._ctx2PolicyMap.values();
/* 500 */       ArrayList<ApplicationSecurityConfiguration> al = c.iterator().next();
/* 501 */       ApplicationSecurityConfiguration serviceConfig = al.iterator().next();
/*     */       
/* 503 */       this.configForSingleServiceNoPorts = serviceConfig.getDSCORDSP();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void isOptimized(boolean optimize) {
/* 508 */     this.optimize = optimize;
/*     */   }
/*     */   
/*     */   public boolean isOptimized() {
/* 512 */     return this.optimize;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\config\ApplicationSecurityConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */