/*     */ package com.sun.xml.ws.security.kerb;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.security.auth.DestroyFailedException;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.security.auth.kerberos.KerberosKey;
/*     */ import javax.security.auth.kerberos.KerberosTicket;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SubjectComber
/*     */ {
/*  30 */   private static final boolean DEBUG = Krb5Util.DEBUG;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Object find(Subject subject, String serverPrincipal, String clientPrincipal, Class credClass) {
/*  41 */     return findAux(subject, serverPrincipal, clientPrincipal, credClass, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Object findMany(Subject subject, String serverPrincipal, String clientPrincipal, Class credClass) {
/*  48 */     return findAux(subject, serverPrincipal, clientPrincipal, credClass, false);
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
/*     */   private static Object findAux(Subject subject, String serverPrincipal, String clientPrincipal, Class<KerberosKey> credClass, boolean oneOnly) {
/*  61 */     if (subject == null) {
/*  62 */       return null;
/*     */     }
/*  64 */     List<KerberosKey> answer = oneOnly ? null : new ArrayList();
/*     */     
/*  66 */     if (credClass == KerberosKey.class) {
/*     */ 
/*     */       
/*  69 */       Iterator<KerberosKey> iterator = subject.<KerberosKey>getPrivateCredentials(KerberosKey.class).iterator();
/*     */       
/*  71 */       while (iterator.hasNext()) {
/*  72 */         KerberosKey key = iterator.next();
/*  73 */         if (serverPrincipal == null || serverPrincipal.equals(key.getPrincipal().getName()))
/*     */         {
/*  75 */           if (DEBUG) {
/*  76 */             System.out.println("Found key for " + key.getPrincipal() + "(" + key.getKeyType() + ")");
/*     */           }
/*     */ 
/*     */           
/*  80 */           if (oneOnly) {
/*  81 */             return key;
/*     */           }
/*  83 */           if (serverPrincipal == null)
/*     */           {
/*     */             
/*  86 */             serverPrincipal = key.getPrincipal().getName();
/*     */           }
/*     */           
/*  89 */           answer.add(key);
/*     */         }
/*     */       
/*     */       } 
/*  93 */     } else if (credClass == KerberosTicket.class) {
/*     */ 
/*     */       
/*  96 */       Set<Object> pcs = subject.getPrivateCredentials();
/*  97 */       synchronized (pcs) {
/*  98 */         Iterator iterator = pcs.iterator();
/*  99 */         while (iterator.hasNext()) {
/* 100 */           Object obj = iterator.next();
/* 101 */           if (obj instanceof KerberosTicket) {
/* 102 */             KerberosTicket ticket = (KerberosTicket)obj;
/* 103 */             if (DEBUG) {
/* 104 */               System.out.println("Found ticket for " + ticket.getClient() + " to go to " + ticket.getServer() + " expiring on " + ticket.getEndTime());
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 111 */             if (!ticket.isCurrent()) {
/*     */ 
/*     */ 
/*     */               
/* 115 */               if (!subject.isReadOnly()) {
/* 116 */                 iterator.remove();
/*     */                 try {
/* 118 */                   ticket.destroy();
/* 119 */                   if (DEBUG) {
/* 120 */                     System.out.println("Removed and destroyed the expired Ticket \n" + ticket);
/*     */                   
/*     */                   }
/*     */                 
/*     */                 }
/* 125 */                 catch (DestroyFailedException dfe) {
/* 126 */                   if (DEBUG) {
/* 127 */                     System.out.println("Expired ticket not detroyed successfully. " + dfe);
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */               
/*     */               continue;
/*     */             } 
/* 134 */             if (ticket.getServer().getName().equals(serverPrincipal))
/*     */             {
/*     */               
/* 137 */               if (clientPrincipal == null || clientPrincipal.equals(ticket.getClient().getName())) {
/*     */ 
/*     */                 
/* 140 */                 if (oneOnly) {
/* 141 */                   return ticket;
/*     */                 }
/*     */ 
/*     */                 
/* 145 */                 if (clientPrincipal == null) {
/* 146 */                   clientPrincipal = ticket.getClient().getName();
/*     */                 }
/*     */                 
/* 149 */                 if (serverPrincipal == null) {
/* 150 */                   serverPrincipal = ticket.getServer().getName();
/*     */                 }
/*     */                 
/* 153 */                 answer.add(ticket);
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 162 */     return answer;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\kerb\SubjectComber.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */