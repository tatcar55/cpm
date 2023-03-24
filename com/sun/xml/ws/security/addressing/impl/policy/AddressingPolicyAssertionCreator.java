/*     */ package com.sun.xml.ws.security.addressing.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.policy.spi.AssertionCreationException;
/*     */ import com.sun.xml.ws.policy.spi.PolicyAssertionCreator;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.logging.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AddressingPolicyAssertionCreator
/*     */   implements PolicyAssertionCreator
/*     */ {
/*  63 */   private static HashSet<String> implementedAssertions = new HashSet<String>();
/*  64 */   private static final String[] NS_SUPPORTED_LIST = new String[] { AddressingVersion.MEMBER.nsUri, AddressingVersion.W3C.nsUri };
/*     */ 
/*     */   
/*  67 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(AddressingPolicyAssertionCreator.class);
/*     */   
/*     */   static {
/*  70 */     implementedAssertions.add("Address");
/*  71 */     implementedAssertions.add("EndpointReference");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getSupportedDomainNamespaceURIs() {
/*  80 */     return NS_SUPPORTED_LIST;
/*     */   }
/*     */   
/*     */   protected Class<?> getClass(AssertionData assertionData) throws AssertionCreationException {
/*  84 */     LOGGER.entering(new Object[] { assertionData });
/*     */     try {
/*  86 */       String className = assertionData.getName().getLocalPart();
/*  87 */       Class<?> result = Class.forName("com.sun.xml.ws.security.addressing.impl.policy." + className);
/*  88 */       LOGGER.exiting();
/*  89 */       return result;
/*  90 */     } catch (ClassNotFoundException ex) {
/*  91 */       LOGGER.warning(LocalizationMessages.WSA_0001_UNKNOWN_ASSERTION(assertionData.toString()), ex);
/*  92 */       throw new AssertionCreationException(assertionData, ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public PolicyAssertion createAssertion(AssertionData assertionData, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative, PolicyAssertionCreator policyAssertionCreator) throws AssertionCreationException {
/*  97 */     String localName = assertionData.getName().getLocalPart();
/*  98 */     if (implementedAssertions.contains(localName)) {
/*  99 */       Class<?> cl = getClass(assertionData);
/* 100 */       Constructor<?> cons = null;
/*     */       
/*     */       try {
/* 103 */         cons = getConstructor(cl);
/*     */       
/*     */       }
/* 106 */       catch (NoSuchMethodException ex) {
/* 107 */         if (LOGGER.isLoggable(Level.SEVERE)) {
/* 108 */           LOGGER.log(Level.SEVERE, LocalizationMessages.WSA_0002_ERROR_OBTAINING_CONSTRUCTOR(assertionData.getName()), ex);
/*     */         }
/* 110 */         throw new AssertionCreationException(assertionData, ex);
/* 111 */       } catch (SecurityException ex) {
/* 112 */         if (LOGGER.isLoggable(Level.SEVERE)) {
/* 113 */           LOGGER.log(Level.SEVERE, LocalizationMessages.WSA_0002_ERROR_OBTAINING_CONSTRUCTOR(assertionData.getName()), ex);
/*     */         }
/*     */ 
/*     */         
/* 117 */         throw new AssertionCreationException(assertionData, ex);
/*     */       } 
/* 119 */       if (cons != null) {
/*     */         try {
/* 121 */           return PolicyAssertion.class.cast(cons.newInstance(new Object[] { assertionData, nestedAssertions, nestedAlternative }));
/* 122 */         } catch (IllegalArgumentException ex) {
/* 123 */           if (LOGGER.isLoggable(Level.SEVERE)) {
/* 124 */             LOGGER.log(Level.SEVERE, LocalizationMessages.WSA_0003_ERROR_INSTANTIATING(assertionData.getName()));
/*     */           }
/*     */           
/* 127 */           throw new AssertionCreationException(assertionData, ex);
/* 128 */         } catch (InvocationTargetException ex) {
/* 129 */           if (LOGGER.isLoggable(Level.SEVERE)) {
/* 130 */             LOGGER.log(Level.SEVERE, LocalizationMessages.WSA_0003_ERROR_INSTANTIATING(assertionData.getName()));
/*     */           }
/* 132 */           throw new AssertionCreationException(assertionData, ex);
/* 133 */         } catch (InstantiationException ex) {
/* 134 */           if (LOGGER.isLoggable(Level.SEVERE)) {
/* 135 */             LOGGER.log(Level.SEVERE, LocalizationMessages.WSA_0003_ERROR_INSTANTIATING(assertionData.getName()));
/*     */           }
/* 137 */           throw new AssertionCreationException(assertionData, ex);
/* 138 */         } catch (IllegalAccessException ex) {
/* 139 */           if (LOGGER.isLoggable(Level.SEVERE)) {
/* 140 */             LOGGER.log(Level.SEVERE, LocalizationMessages.WSA_0003_ERROR_INSTANTIATING(assertionData.getName()));
/*     */           }
/* 142 */           throw new AssertionCreationException(assertionData, ex);
/*     */         } 
/*     */       }
/*     */       try {
/* 146 */         return (PolicyAssertion)cl.newInstance();
/* 147 */       } catch (InstantiationException ex) {
/* 148 */         if (LOGGER.isLoggable(Level.SEVERE)) {
/* 149 */           LOGGER.log(Level.SEVERE, LocalizationMessages.WSA_0003_ERROR_INSTANTIATING(assertionData.getName()));
/*     */         }
/* 151 */         throw new AssertionCreationException(assertionData, ex);
/* 152 */       } catch (IllegalAccessException ex) {
/* 153 */         if (LOGGER.isLoggable(Level.SEVERE)) {
/* 154 */           LOGGER.log(Level.SEVERE, LocalizationMessages.WSA_0003_ERROR_INSTANTIATING(assertionData.getName()));
/*     */         }
/* 156 */         throw new AssertionCreationException(assertionData, ex);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 162 */     return policyAssertionCreator.createAssertion(assertionData, nestedAssertions, nestedAlternative, policyAssertionCreator);
/*     */   }
/*     */ 
/*     */   
/*     */   private <T> Constructor<T> getConstructor(Class<T> cl) throws NoSuchMethodException {
/* 167 */     return cl.getConstructor(new Class[] { AssertionData.class, Collection.class, AssertionSet.class });
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\addressing\impl\policy\AddressingPolicyAssertionCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */