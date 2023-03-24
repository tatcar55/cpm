/*     */ package com.sun.xml.ws.api;
/*     */ 
/*     */ import javax.xml.ws.WebServiceFeature;
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
/*     */ public class ComponentFeature
/*     */   extends WebServiceFeature
/*     */   implements ServiceSharedFeatureMarker
/*     */ {
/*     */   private final Component component;
/*     */   private final Target target;
/*     */   
/*     */   public enum Target
/*     */   {
/*  68 */     CONTAINER, ENDPOINT, SERVICE, STUB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComponentFeature(Component component) {
/*  79 */     this(component, Target.CONTAINER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComponentFeature(Component component, Target target) {
/*  88 */     this.enabled = true;
/*  89 */     this.component = component;
/*  90 */     this.target = target;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getID() {
/*  95 */     return ComponentFeature.class.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Component getComponent() {
/* 103 */     return this.component;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Target getTarget() {
/* 111 */     return this.target;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\ComponentFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */