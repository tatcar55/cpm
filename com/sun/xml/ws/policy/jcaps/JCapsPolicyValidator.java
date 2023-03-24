/*    */ package com.sun.xml.ws.policy.jcaps;
/*    */ 
/*    */ import com.sun.xml.ws.policy.spi.AbstractQNameValidator;
/*    */ import java.util.ArrayList;
/*    */ import javax.xml.namespace.QName;
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
/*    */ public class JCapsPolicyValidator
/*    */   extends AbstractQNameValidator
/*    */ {
/*    */   public static final String NS_URI_BASIC_AUTHENTICATION_SECURITY_POLICY = "http://sun.com/ws/httpbc/security/BasicauthSecurityPolicy";
/* 52 */   private static final ArrayList<QName> supportedAssertions = new ArrayList<QName>(2);
/*    */   
/*    */   static {
/* 55 */     supportedAssertions.add(new QName("http://sun.com/ws/httpbc/security/BasicauthSecurityPolicy", "MustSupportBasicAuthentication"));
/* 56 */     supportedAssertions.add(new QName("http://sun.com/ws/httpbc/security/BasicauthSecurityPolicy", "UsernameToken"));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public JCapsPolicyValidator() {
/* 63 */     super(supportedAssertions, supportedAssertions);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\jcaps\JCapsPolicyValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */