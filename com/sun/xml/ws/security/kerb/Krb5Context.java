/*      */ package com.sun.xml.ws.security.kerb;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.security.Provider;
/*      */ import javax.crypto.Cipher;
/*      */ import javax.security.auth.Subject;
/*      */ import javax.security.auth.kerberos.KerberosTicket;
/*      */ import javax.security.auth.kerberos.ServicePermission;
/*      */ import org.ietf.jgss.ChannelBinding;
/*      */ import org.ietf.jgss.GSSException;
/*      */ import org.ietf.jgss.MessageProp;
/*      */ import org.ietf.jgss.Oid;
/*      */ import sun.misc.HexDumpEncoder;
/*      */ import sun.security.jgss.GSSUtil;
/*      */ import sun.security.jgss.TokenTracker;
/*      */ import sun.security.jgss.spi.GSSContextSpi;
/*      */ import sun.security.jgss.spi.GSSCredentialSpi;
/*      */ import sun.security.jgss.spi.GSSNameSpi;
/*      */ import sun.security.krb5.Credentials;
/*      */ import sun.security.krb5.EncryptionKey;
/*      */ import sun.security.krb5.KrbApReq;
/*      */ import sun.security.krb5.KrbException;
/*      */ import sun.security.krb5.PrincipalName;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class Krb5Context
/*      */   implements GSSContextSpi
/*      */ {
/*      */   private static final int STATE_NEW = 1;
/*      */   private static final int STATE_IN_PROCESS = 2;
/*      */   private static final int STATE_DONE = 3;
/*      */   private static final int STATE_DELETED = 4;
/*   50 */   private int state = 1;
/*      */   
/*      */   private boolean credDelegState = false;
/*      */   
/*      */   private boolean mutualAuthState = true;
/*      */   
/*      */   private boolean replayDetState = true;
/*      */   
/*      */   private boolean sequenceDetState = true;
/*      */   
/*      */   private boolean confState = true;
/*      */   
/*      */   private boolean integState = true;
/*      */   
/*      */   private int mySeqNumber;
/*      */   
/*      */   private int peerSeqNumber;
/*      */   private TokenTracker peerTokenTracker;
/*   68 */   private CipherHelper cipherHelper = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   79 */   private Object mySeqNumberLock = new Object();
/*   80 */   private Object peerSeqNumberLock = new Object();
/*      */   
/*      */   private EncryptionKey key;
/*      */   
/*      */   private Krb5NameElement myName;
/*      */   
/*      */   private Krb5NameElement peerName;
/*      */   
/*      */   private int lifetime;
/*      */   private boolean initiator;
/*      */   private ChannelBinding channelBinding;
/*      */   private Krb5CredElement myCred;
/*      */   private Krb5CredElement delegatedCred;
/*   93 */   private Cipher desCipher = null;
/*      */   
/*      */   private Credentials serviceCreds;
/*      */   
/*      */   private KrbApReq apReq;
/*      */   
/*      */   private final int caller;
/*  100 */   private static final boolean DEBUG = Krb5Util.DEBUG;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Krb5Context(int caller, Krb5NameElement peerName, Krb5CredElement myCred, int lifetime) throws GSSException {
/*  110 */     if (peerName == null) {
/*  111 */       throw new IllegalArgumentException("Cannot have null peer name");
/*      */     }
/*  113 */     this.caller = caller;
/*  114 */     this.peerName = peerName;
/*  115 */     this.myCred = myCred;
/*  116 */     this.lifetime = lifetime;
/*  117 */     this.initiator = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Krb5Context(int caller, Krb5CredElement myCred) throws GSSException {
/*  126 */     this.caller = caller;
/*  127 */     this.myCred = myCred;
/*  128 */     this.initiator = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Krb5Context(int caller, byte[] interProcessToken) throws GSSException {
/*  136 */     throw new GSSException(16, -1, "GSS Import Context not available");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isTransferable() throws GSSException {
/*  145 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getLifetime() {
/*  153 */     return Integer.MAX_VALUE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void requestLifetime(int lifetime) throws GSSException {
/*  180 */     if (this.state == 1 && isInitiator()) {
/*  181 */       this.lifetime = lifetime;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void requestConf(boolean value) throws GSSException {
/*  188 */     if (this.state == 1 && isInitiator()) {
/*  189 */       this.confState = value;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean getConfState() {
/*  196 */     return this.confState;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void requestInteg(boolean value) throws GSSException {
/*  203 */     if (this.state == 1 && isInitiator()) {
/*  204 */       this.integState = value;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean getIntegState() {
/*  211 */     return this.integState;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void requestCredDeleg(boolean value) throws GSSException {
/*  219 */     if (this.state == 1 && isInitiator()) {
/*  220 */       this.credDelegState = value;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean getCredDelegState() {
/*  227 */     return this.credDelegState;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void requestMutualAuth(boolean value) throws GSSException {
/*  236 */     if (this.state == 1 && isInitiator()) {
/*  237 */       this.mutualAuthState = value;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean getMutualAuthState() {
/*  247 */     return this.mutualAuthState;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void requestReplayDet(boolean value) throws GSSException {
/*  255 */     if (this.state == 1 && isInitiator()) {
/*  256 */       this.replayDetState = value;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean getReplayDetState() {
/*  264 */     return (this.replayDetState || this.sequenceDetState);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void requestSequenceDet(boolean value) throws GSSException {
/*  272 */     if (this.state == 1 && isInitiator()) {
/*  273 */       this.sequenceDetState = value;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean getSequenceDetState() {
/*  281 */     return (this.sequenceDetState || this.replayDetState);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void requestAnonymity(boolean value) throws GSSException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean getAnonymityState() {
/*  303 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final CipherHelper getCipherHelper(EncryptionKey ckey) throws GSSException {
/*  315 */     EncryptionKey cipherKey = null;
/*  316 */     if (this.cipherHelper == null) {
/*  317 */       cipherKey = (getKey() == null) ? ckey : getKey();
/*  318 */       this.cipherHelper = new CipherHelper(cipherKey);
/*      */     } 
/*  320 */     return this.cipherHelper;
/*      */   }
/*      */   
/*      */   final int incrementMySequenceNumber() {
/*      */     int retVal;
/*  325 */     synchronized (this.mySeqNumberLock) {
/*  326 */       retVal = this.mySeqNumber;
/*  327 */       this.mySeqNumber = retVal + 1;
/*      */     } 
/*  329 */     return retVal;
/*      */   }
/*      */   
/*      */   final void resetMySequenceNumber(int seqNumber) {
/*  333 */     if (DEBUG) {
/*  334 */       System.out.println("Krb5Context setting mySeqNumber to: " + seqNumber);
/*      */     }
/*      */     
/*  337 */     synchronized (this.mySeqNumberLock) {
/*  338 */       this.mySeqNumber = seqNumber;
/*      */     } 
/*      */   }
/*      */   
/*      */   final void resetPeerSequenceNumber(int seqNumber) {
/*  343 */     if (DEBUG) {
/*  344 */       System.out.println("Krb5Context setting peerSeqNumber to: " + seqNumber);
/*      */     }
/*      */     
/*  347 */     synchronized (this.peerSeqNumberLock) {
/*  348 */       this.peerSeqNumber = seqNumber;
/*  349 */       this.peerTokenTracker = new TokenTracker(this.peerSeqNumber);
/*      */     } 
/*      */   }
/*      */   
/*      */   final void setKey(final EncryptionKey key) throws GSSException {
/*  354 */     this.key = key;
/*      */     
/*  356 */     final AccessControlContext acc = AccessController.getContext();
/*      */     
/*  358 */     final Subject subject = AccessController.<Subject>doPrivileged(new PrivilegedAction<Subject>()
/*      */         {
/*      */           public Object run()
/*      */           {
/*  362 */             return Subject.getSubject(acc);
/*      */           }
/*      */         });
/*  365 */     if (subject != null && !subject.isReadOnly()) {
/*  366 */       AccessController.doPrivileged(new PrivilegedAction() {
/*      */             public Object run() {
/*  368 */               subject.getPrivateCredentials().add(key);
/*  369 */               return null;
/*      */             }
/*      */           });
/*      */     }
/*      */     
/*  374 */     this.cipherHelper = new CipherHelper(key);
/*      */   }
/*      */   
/*      */   private final EncryptionKey getKey() {
/*  378 */     return this.key;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void setDelegCred(Krb5CredElement delegatedCred) {
/*  386 */     this.delegatedCred = delegatedCred;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void setCredDelegState(boolean state) {
/*  402 */     this.credDelegState = state;
/*      */   }
/*      */   
/*      */   final void setMutualAuthState(boolean state) {
/*  406 */     this.mutualAuthState = state;
/*      */   }
/*      */   
/*      */   final void setReplayDetState(boolean state) {
/*  410 */     this.replayDetState = state;
/*      */   }
/*      */   
/*      */   final void setSequenceDetState(boolean state) {
/*  414 */     this.sequenceDetState = state;
/*      */   }
/*      */   
/*      */   final void setConfState(boolean state) {
/*  418 */     this.confState = state;
/*      */   }
/*      */   
/*      */   final void setIntegState(boolean state) {
/*  422 */     this.integState = state;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setChannelBinding(ChannelBinding channelBinding) throws GSSException {
/*  431 */     this.channelBinding = channelBinding;
/*      */   }
/*      */   
/*      */   final ChannelBinding getChannelBinding() {
/*  435 */     return this.channelBinding;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Oid getMech() {
/*  444 */     return Krb5MechFactory.GSS_KRB5_MECH_OID;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final GSSNameSpi getSrcName() throws GSSException {
/*  454 */     return isInitiator() ? this.myName : this.peerName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final GSSNameSpi getTargName() throws GSSException {
/*  464 */     return !isInitiator() ? this.myName : this.peerName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final GSSCredentialSpi getDelegCred() throws GSSException {
/*  478 */     if (this.state != 2 && this.state != 3)
/*  479 */       throw new GSSException(12); 
/*  480 */     if (this.delegatedCred == null)
/*  481 */       throw new GSSException(13); 
/*  482 */     return this.delegatedCred;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isInitiator() {
/*  492 */     return this.initiator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isProtReady() {
/*  504 */     return (this.state == 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final byte[] initSecContext(InputStream is, int mechTokenSize) throws GSSException {
/*  523 */     byte[] retVal = null;
/*  524 */     InitialToken token = null;
/*  525 */     int errorCode = 11;
/*  526 */     if (DEBUG) {
/*  527 */       System.out.println("Entered Krb5Context.initSecContext with state=" + printState(this.state));
/*      */     }
/*      */     
/*  530 */     if (!isInitiator()) {
/*  531 */       throw new GSSException(11, -1, "initSecContext on an acceptor GSSContext");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  537 */       if (this.state == 1) {
/*  538 */         this.state = 2;
/*      */         
/*  540 */         errorCode = 13;
/*      */         
/*  542 */         if (this.myCred == null) {
/*  543 */           this.myCred = Krb5InitCredential.getInstance(this.caller, this.myName, 0);
/*      */         }
/*  545 */         else if (!this.myCred.isInitiatorCredential()) {
/*  546 */           throw new GSSException(errorCode, -1, "No TGT available");
/*      */         } 
/*      */         
/*  549 */         this.myName = (Krb5NameElement)this.myCred.getName();
/*  550 */         Credentials tgt = ((Krb5InitCredential)this.myCred).getKrb5Credentials();
/*      */ 
/*      */         
/*  553 */         checkPermission(this.peerName.getKrb5PrincipalName().getName(), "initiate");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  561 */         final AccessControlContext acc = AccessController.getContext();
/*      */ 
/*      */         
/*  564 */         if (GSSUtil.useSubjectCredsOnly()) {
/*  565 */           KerberosTicket kerbTicket = null;
/*      */           
/*      */           try {
/*  568 */             kerbTicket = AccessController.<KerberosTicket>doPrivileged(new PrivilegedExceptionAction<KerberosTicket>()
/*      */                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*      */                   public Object run() throws Exception
/*      */                   {
/*  577 */                     return Krb5Util.getTicket(-1, Krb5Context.this.myName.getKrb5PrincipalName().getName(), Krb5Context.this.peerName.getKrb5PrincipalName().getName(), acc);
/*      */                   }
/*      */                 });
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           }
/*  585 */           catch (PrivilegedActionException e) {
/*  586 */             if (DEBUG) {
/*  587 */               System.out.println("Attempt to obtain service ticket from the subject failed!");
/*      */             }
/*      */           } 
/*      */           
/*  591 */           if (kerbTicket != null) {
/*  592 */             if (DEBUG) {
/*  593 */               System.out.println("Found service ticket in the subject" + kerbTicket);
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  601 */             this.serviceCreds = Krb5Util.ticketToCreds(kerbTicket);
/*      */           } 
/*      */         } 
/*  604 */         if (this.serviceCreds == null) {
/*      */ 
/*      */           
/*  607 */           if (DEBUG) {
/*  608 */             System.out.println("Service ticket not found in the subject");
/*      */           }
/*      */ 
/*      */           
/*  612 */           this.serviceCreds = Credentials.acquireServiceCreds(this.peerName.getKrb5PrincipalName().getName(), tgt);
/*      */ 
/*      */           
/*  615 */           if (GSSUtil.useSubjectCredsOnly()) {
/*  616 */             final Subject subject = AccessController.<Subject>doPrivileged(new PrivilegedAction<Subject>()
/*      */                 {
/*      */                   public Object run()
/*      */                   {
/*  620 */                     return Subject.getSubject(acc);
/*      */                   }
/*      */                 });
/*  623 */             if (subject != null && !subject.isReadOnly()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  632 */               final KerberosTicket kt = Krb5Util.credsToTicket(this.serviceCreds);
/*      */               
/*  634 */               AccessController.doPrivileged(new PrivilegedAction()
/*      */                   {
/*      */                     public Object run() {
/*  637 */                       subject.getPrivateCredentials().add(kt);
/*  638 */                       return null;
/*      */                     }
/*      */                   });
/*      */             
/*      */             }
/*  643 */             else if (DEBUG) {
/*  644 */               System.out.println("Subject is readOnly;Kerberos Service ticket not stored");
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  652 */         errorCode = 11;
/*  653 */         token = new InitSecContextToken(this, tgt, this.serviceCreds);
/*  654 */         this.apReq = ((InitSecContextToken)token).getKrbApReq();
/*  655 */         retVal = token.encode();
/*  656 */         this.myCred = null;
/*  657 */         if (!getMutualAuthState()) {
/*  658 */           this.state = 3;
/*      */         }
/*  660 */         if (DEBUG) {
/*  661 */           System.out.println("Created InitSecContextToken:\n" + (new HexDumpEncoder()).encodeBuffer(retVal));
/*      */         }
/*      */       }
/*  664 */       else if (this.state == 2) {
/*      */ 
/*      */         
/*  667 */         new AcceptSecContextToken(this, this.serviceCreds, this.apReq, is);
/*  668 */         this.serviceCreds = null;
/*  669 */         this.apReq = null;
/*  670 */         this.state = 3;
/*      */       
/*      */       }
/*  673 */       else if (DEBUG) {
/*  674 */         System.out.println(this.state);
/*      */       }
/*      */     
/*  677 */     } catch (KrbException e) {
/*  678 */       if (DEBUG) {
/*  679 */         e.printStackTrace();
/*      */       }
/*  681 */       GSSException gssException = new GSSException(errorCode, -1, e.getMessage());
/*      */       
/*  683 */       gssException.initCause(e);
/*  684 */       throw gssException;
/*  685 */     } catch (IOException e) {
/*  686 */       GSSException gssException = new GSSException(errorCode, -1, e.getMessage());
/*      */       
/*  688 */       gssException.initCause(e);
/*  689 */       throw gssException;
/*      */     } 
/*  691 */     return retVal;
/*      */   }
/*      */   
/*      */   public final boolean isEstablished() {
/*  695 */     return (this.state == 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final byte[] acceptSecContext(InputStream is, int mechTokenSize) throws GSSException {
/*  713 */     byte[] retVal = null;
/*      */     
/*  715 */     if (DEBUG) {
/*  716 */       System.out.println("Entered Krb5Context.acceptSecContext with state=" + printState(this.state));
/*      */     }
/*      */ 
/*      */     
/*  720 */     if (isInitiator()) {
/*  721 */       throw new GSSException(11, -1, "acceptSecContext on an initiator GSSContext");
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  726 */       if (this.state == 1) {
/*  727 */         this.state = 2;
/*  728 */         if (this.myCred == null) {
/*  729 */           this.myCred = Krb5AcceptCredential.getInstance(this.caller, this.myName);
/*  730 */         } else if (!this.myCred.isAcceptorCredential()) {
/*  731 */           throw new GSSException(13, -1, "No Secret Key available");
/*      */         } 
/*      */         
/*  734 */         this.myName = (Krb5NameElement)this.myCred.getName();
/*      */         
/*  736 */         checkPermission(this.myName.getKrb5PrincipalName().getName(), "accept");
/*      */ 
/*      */         
/*  739 */         EncryptionKey[] secretKeys = ((Krb5AcceptCredential)this.myCred).getKrb5EncryptionKeys();
/*      */ 
/*      */         
/*  742 */         InitSecContextToken token = new InitSecContextToken(this, secretKeys, is);
/*      */         
/*  744 */         PrincipalName clientName = token.getKrbApReq().getClient();
/*  745 */         this.peerName = Krb5NameElement.getInstance(clientName);
/*  746 */         if (getMutualAuthState()) {
/*  747 */           retVal = (new AcceptSecContextToken(this, token.getKrbApReq())).encode();
/*      */         }
/*      */         
/*  750 */         this.myCred = null;
/*  751 */         this.state = 3;
/*      */       
/*      */       }
/*  754 */       else if (DEBUG) {
/*  755 */         System.out.println(this.state);
/*      */       }
/*      */     
/*  758 */     } catch (KrbException e) {
/*  759 */       GSSException gssException = new GSSException(11, -1, e.getMessage());
/*      */       
/*  761 */       gssException.initCause(e);
/*  762 */       throw gssException;
/*  763 */     } catch (IOException e) {
/*  764 */       if (DEBUG) {
/*  765 */         e.printStackTrace();
/*      */       }
/*  767 */       GSSException gssException = new GSSException(11, -1, e.getMessage());
/*      */       
/*  769 */       gssException.initCause(e);
/*  770 */       throw gssException;
/*      */     } 
/*      */     
/*  773 */     return retVal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getWrapSizeLimit(int qop, boolean confReq, int maxTokSize) throws GSSException {
/*  795 */     int retVal = 0;
/*  796 */     if (this.cipherHelper.getProto() == 0) {
/*  797 */       retVal = WrapToken.getSizeLimit(qop, confReq, maxTokSize, getCipherHelper(null));
/*      */     }
/*  799 */     else if (this.cipherHelper.getProto() == 1) {
/*  800 */       retVal = WrapToken_v2.getSizeLimit(qop, confReq, maxTokSize, getCipherHelper(null));
/*      */     } 
/*      */     
/*  803 */     return retVal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final byte[] wrap(byte[] inBuf, int offset, int len, MessageProp msgProp) throws GSSException {
/*  815 */     if (DEBUG) {
/*  816 */       System.out.println("Krb5Context.wrap: data=[" + getHexBytes(inBuf, offset, len) + "]");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  821 */     if (this.state != 3) {
/*  822 */       throw new GSSException(12, -1, "Wrap called in invalid state!");
/*      */     }
/*      */     
/*  825 */     byte[] encToken = null;
/*      */     try {
/*  827 */       if (this.cipherHelper.getProto() == 0) {
/*  828 */         WrapToken token = new WrapToken(this, msgProp, inBuf, offset, len);
/*      */         
/*  830 */         encToken = token.encode();
/*  831 */       } else if (this.cipherHelper.getProto() == 1) {
/*  832 */         WrapToken_v2 token = new WrapToken_v2(this, msgProp, inBuf, offset, len);
/*      */         
/*  834 */         encToken = token.encode();
/*      */       } 
/*  836 */       if (DEBUG) {
/*  837 */         System.out.println("Krb5Context.wrap: token=[" + getHexBytes(encToken, 0, encToken.length) + "]");
/*      */       }
/*      */ 
/*      */       
/*  841 */       return encToken;
/*  842 */     } catch (IOException e) {
/*  843 */       encToken = null;
/*  844 */       GSSException gssException = new GSSException(11, -1, e.getMessage());
/*      */       
/*  846 */       gssException.initCause(e);
/*  847 */       throw gssException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int wrap(byte[] inBuf, int inOffset, int len, byte[] outBuf, int outOffset, MessageProp msgProp) throws GSSException {
/*  855 */     if (this.state != 3) {
/*  856 */       throw new GSSException(12, -1, "Wrap called in invalid state!");
/*      */     }
/*      */     
/*  859 */     int retVal = 0;
/*      */     try {
/*  861 */       if (this.cipherHelper.getProto() == 0) {
/*  862 */         WrapToken token = new WrapToken(this, msgProp, inBuf, inOffset, len);
/*      */         
/*  864 */         retVal = token.encode(outBuf, outOffset);
/*  865 */       } else if (this.cipherHelper.getProto() == 1) {
/*  866 */         WrapToken_v2 token = new WrapToken_v2(this, msgProp, inBuf, inOffset, len);
/*      */         
/*  868 */         retVal = token.encode(outBuf, outOffset);
/*      */       } 
/*  870 */       if (DEBUG) {
/*  871 */         System.out.println("Krb5Context.wrap: token=[" + getHexBytes(outBuf, outOffset, retVal) + "]");
/*      */       }
/*      */ 
/*      */       
/*  875 */       return retVal;
/*  876 */     } catch (IOException e) {
/*  877 */       retVal = 0;
/*  878 */       GSSException gssException = new GSSException(11, -1, e.getMessage());
/*      */       
/*  880 */       gssException.initCause(e);
/*  881 */       throw gssException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void wrap(byte[] inBuf, int offset, int len, OutputStream os, MessageProp msgProp) throws GSSException {
/*  889 */     if (this.state != 3) {
/*  890 */       throw new GSSException(12, -1, "Wrap called in invalid state!");
/*      */     }
/*      */     
/*  893 */     byte[] encToken = null;
/*      */     try {
/*  895 */       if (this.cipherHelper.getProto() == 0) {
/*  896 */         WrapToken token = new WrapToken(this, msgProp, inBuf, offset, len);
/*      */         
/*  898 */         token.encode(os);
/*  899 */         if (DEBUG) {
/*  900 */           encToken = token.encode();
/*      */         }
/*  902 */       } else if (this.cipherHelper.getProto() == 1) {
/*  903 */         WrapToken_v2 token = new WrapToken_v2(this, msgProp, inBuf, offset, len);
/*      */         
/*  905 */         token.encode(os);
/*  906 */         if (DEBUG) {
/*  907 */           encToken = token.encode();
/*      */         }
/*      */       } 
/*  910 */     } catch (IOException e) {
/*  911 */       GSSException gssException = new GSSException(11, -1, e.getMessage());
/*      */       
/*  913 */       gssException.initCause(e);
/*  914 */       throw gssException;
/*      */     } 
/*      */     
/*  917 */     if (DEBUG) {
/*  918 */       System.out.println("Krb5Context.wrap: token=[" + getHexBytes(encToken, 0, encToken.length) + "]");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void wrap(InputStream is, OutputStream os, MessageProp msgProp) throws GSSException {
/*      */     byte[] data;
/*      */     try {
/*  929 */       data = new byte[is.available()];
/*  930 */       is.read(data);
/*  931 */     } catch (IOException e) {
/*  932 */       GSSException gssException = new GSSException(11, -1, e.getMessage());
/*      */       
/*  934 */       gssException.initCause(e);
/*  935 */       throw gssException;
/*      */     } 
/*  937 */     wrap(data, 0, data.length, os, msgProp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final byte[] unwrap(byte[] inBuf, int offset, int len, MessageProp msgProp) throws GSSException {
/*  944 */     if (DEBUG) {
/*  945 */       System.out.println("Krb5Context.unwrap: token=[" + getHexBytes(inBuf, offset, len) + "]");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  950 */     if (this.state != 3) {
/*  951 */       throw new GSSException(12, -1, " Unwrap called in invalid state!");
/*      */     }
/*      */ 
/*      */     
/*  955 */     byte[] data = null;
/*  956 */     if (this.cipherHelper.getProto() == 0) {
/*  957 */       WrapToken token = new WrapToken(this, inBuf, offset, len, msgProp);
/*      */       
/*  959 */       data = token.getData();
/*  960 */       setSequencingAndReplayProps(token, msgProp);
/*  961 */     } else if (this.cipherHelper.getProto() == 1) {
/*  962 */       WrapToken_v2 token = new WrapToken_v2(this, inBuf, offset, len, msgProp);
/*      */       
/*  964 */       data = token.getData();
/*  965 */       setSequencingAndReplayProps(token, msgProp);
/*      */     } 
/*      */     
/*  968 */     if (DEBUG) {
/*  969 */       System.out.println("Krb5Context.unwrap: data=[" + getHexBytes(data, 0, data.length) + "]");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  974 */     return data;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int unwrap(byte[] inBuf, int inOffset, int len, byte[] outBuf, int outOffset, MessageProp msgProp) throws GSSException {
/*  981 */     if (this.state != 3) {
/*  982 */       throw new GSSException(12, -1, "Unwrap called in invalid state!");
/*      */     }
/*      */     
/*  985 */     if (this.cipherHelper.getProto() == 0) {
/*  986 */       WrapToken token = new WrapToken(this, inBuf, inOffset, len, msgProp);
/*      */       
/*  988 */       len = token.getData(outBuf, outOffset);
/*  989 */       setSequencingAndReplayProps(token, msgProp);
/*  990 */     } else if (this.cipherHelper.getProto() == 1) {
/*  991 */       WrapToken_v2 token = new WrapToken_v2(this, inBuf, inOffset, len, msgProp);
/*      */       
/*  993 */       len = token.getData(outBuf, outOffset);
/*  994 */       setSequencingAndReplayProps(token, msgProp);
/*      */     } 
/*  996 */     return len;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int unwrap(InputStream is, byte[] outBuf, int outOffset, MessageProp msgProp) throws GSSException {
/* 1003 */     if (this.state != 3) {
/* 1004 */       throw new GSSException(12, -1, "Unwrap called in invalid state!");
/*      */     }
/*      */     
/* 1007 */     int len = 0;
/* 1008 */     if (this.cipherHelper.getProto() == 0) {
/* 1009 */       WrapToken token = new WrapToken(this, is, msgProp);
/* 1010 */       len = token.getData(outBuf, outOffset);
/* 1011 */       setSequencingAndReplayProps(token, msgProp);
/* 1012 */     } else if (this.cipherHelper.getProto() == 1) {
/* 1013 */       WrapToken_v2 token = new WrapToken_v2(this, is, msgProp);
/* 1014 */       len = token.getData(outBuf, outOffset);
/* 1015 */       setSequencingAndReplayProps(token, msgProp);
/*      */     } 
/* 1017 */     return len;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void unwrap(InputStream is, OutputStream os, MessageProp msgProp) throws GSSException {
/* 1024 */     if (this.state != 3) {
/* 1025 */       throw new GSSException(12, -1, "Unwrap called in invalid state!");
/*      */     }
/*      */     
/* 1028 */     byte[] data = null;
/* 1029 */     if (this.cipherHelper.getProto() == 0) {
/* 1030 */       WrapToken token = new WrapToken(this, is, msgProp);
/* 1031 */       data = token.getData();
/* 1032 */       setSequencingAndReplayProps(token, msgProp);
/* 1033 */     } else if (this.cipherHelper.getProto() == 1) {
/* 1034 */       WrapToken_v2 token = new WrapToken_v2(this, is, msgProp);
/* 1035 */       data = token.getData();
/* 1036 */       setSequencingAndReplayProps(token, msgProp);
/*      */     } 
/*      */     
/*      */     try {
/* 1040 */       os.write(data);
/* 1041 */     } catch (IOException e) {
/* 1042 */       GSSException gssException = new GSSException(11, -1, e.getMessage());
/*      */       
/* 1044 */       gssException.initCause(e);
/* 1045 */       throw gssException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final byte[] getMIC(byte[] inMsg, int offset, int len, MessageProp msgProp) throws GSSException {
/* 1053 */     byte[] micToken = null;
/*      */     try {
/* 1055 */       if (this.cipherHelper.getProto() == 0) {
/* 1056 */         MicToken token = new MicToken(this, msgProp, inMsg, offset, len);
/*      */         
/* 1058 */         micToken = token.encode();
/* 1059 */       } else if (this.cipherHelper.getProto() == 1) {
/* 1060 */         MicToken_v2 token = new MicToken_v2(this, msgProp, inMsg, offset, len);
/*      */         
/* 1062 */         micToken = token.encode();
/*      */       } 
/* 1064 */       return micToken;
/* 1065 */     } catch (IOException e) {
/* 1066 */       micToken = null;
/* 1067 */       GSSException gssException = new GSSException(11, -1, e.getMessage());
/*      */       
/* 1069 */       gssException.initCause(e);
/* 1070 */       throw gssException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getMIC(byte[] inMsg, int offset, int len, byte[] outBuf, int outOffset, MessageProp msgProp) throws GSSException {
/* 1079 */     int retVal = 0;
/*      */     try {
/* 1081 */       if (this.cipherHelper.getProto() == 0) {
/* 1082 */         MicToken token = new MicToken(this, msgProp, inMsg, offset, len);
/*      */         
/* 1084 */         retVal = token.encode(outBuf, outOffset);
/* 1085 */       } else if (this.cipherHelper.getProto() == 1) {
/* 1086 */         MicToken_v2 token = new MicToken_v2(this, msgProp, inMsg, offset, len);
/*      */         
/* 1088 */         retVal = token.encode(outBuf, outOffset);
/*      */       } 
/* 1090 */       return retVal;
/* 1091 */     } catch (IOException e) {
/* 1092 */       retVal = 0;
/* 1093 */       GSSException gssException = new GSSException(11, -1, e.getMessage());
/*      */       
/* 1095 */       gssException.initCause(e);
/* 1096 */       throw gssException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getMIC(byte[] inMsg, int offset, int len, OutputStream os, MessageProp msgProp) throws GSSException {
/*      */     try {
/* 1112 */       if (this.cipherHelper.getProto() == 0) {
/* 1113 */         MicToken token = new MicToken(this, msgProp, inMsg, offset, len);
/*      */         
/* 1115 */         token.encode(os);
/* 1116 */       } else if (this.cipherHelper.getProto() == 1) {
/* 1117 */         MicToken_v2 token = new MicToken_v2(this, msgProp, inMsg, offset, len);
/*      */         
/* 1119 */         token.encode(os);
/*      */       } 
/* 1121 */     } catch (IOException e) {
/* 1122 */       GSSException gssException = new GSSException(11, -1, e.getMessage());
/*      */       
/* 1124 */       gssException.initCause(e);
/* 1125 */       throw gssException;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void getMIC(InputStream is, OutputStream os, MessageProp msgProp) throws GSSException {
/*      */     byte[] data;
/*      */     try {
/* 1133 */       data = new byte[is.available()];
/* 1134 */       is.read(data);
/* 1135 */     } catch (IOException e) {
/* 1136 */       GSSException gssException = new GSSException(11, -1, e.getMessage());
/*      */       
/* 1138 */       gssException.initCause(e);
/* 1139 */       throw gssException;
/*      */     } 
/* 1141 */     getMIC(data, 0, data.length, os, msgProp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void verifyMIC(byte[] inTok, int tokOffset, int tokLen, byte[] inMsg, int msgOffset, int msgLen, MessageProp msgProp) throws GSSException {
/* 1149 */     if (this.cipherHelper.getProto() == 0) {
/* 1150 */       MicToken token = new MicToken(this, inTok, tokOffset, tokLen, msgProp);
/*      */       
/* 1152 */       token.verify(inMsg, msgOffset, msgLen);
/* 1153 */       setSequencingAndReplayProps(token, msgProp);
/* 1154 */     } else if (this.cipherHelper.getProto() == 1) {
/* 1155 */       MicToken_v2 token = new MicToken_v2(this, inTok, tokOffset, tokLen, msgProp);
/*      */       
/* 1157 */       token.verify(inMsg, msgOffset, msgLen);
/* 1158 */       setSequencingAndReplayProps(token, msgProp);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void verifyMIC(InputStream is, byte[] inMsg, int msgOffset, int msgLen, MessageProp msgProp) throws GSSException {
/* 1167 */     if (this.cipherHelper.getProto() == 0) {
/* 1168 */       MicToken token = new MicToken(this, is, msgProp);
/* 1169 */       token.verify(inMsg, msgOffset, msgLen);
/* 1170 */       setSequencingAndReplayProps(token, msgProp);
/* 1171 */     } else if (this.cipherHelper.getProto() == 1) {
/* 1172 */       MicToken_v2 token = new MicToken_v2(this, is, msgProp);
/* 1173 */       token.verify(inMsg, msgOffset, msgLen);
/* 1174 */       setSequencingAndReplayProps(token, msgProp);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void verifyMIC(InputStream is, InputStream msgStr, MessageProp mProp) throws GSSException {
/*      */     byte[] msg;
/*      */     try {
/* 1182 */       msg = new byte[msgStr.available()];
/* 1183 */       msgStr.read(msg);
/* 1184 */     } catch (IOException e) {
/* 1185 */       GSSException gssException = new GSSException(11, -1, e.getMessage());
/*      */       
/* 1187 */       gssException.initCause(e);
/* 1188 */       throw gssException;
/*      */     } 
/* 1190 */     verifyMIC(is, msg, 0, msg.length, mProp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final byte[] export() throws GSSException {
/* 1202 */     throw new GSSException(16, -1, "GSS Export Context not available");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void dispose() throws GSSException {
/* 1214 */     this.state = 4;
/* 1215 */     this.delegatedCred = null;
/*      */   }
/*      */   
/*      */   public final Provider getProvider() {
/* 1219 */     return Krb5MechFactory.PROVIDER;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setSequencingAndReplayProps(MessageToken token, MessageProp prop) {
/* 1228 */     if (this.replayDetState || this.sequenceDetState) {
/* 1229 */       int seqNum = token.getSequenceNumber();
/* 1230 */       this.peerTokenTracker.getProps(seqNum, prop);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setSequencingAndReplayProps(MessageToken_v2 token, MessageProp prop) {
/* 1240 */     if (this.replayDetState || this.sequenceDetState) {
/* 1241 */       int seqNum = token.getSequenceNumber();
/* 1242 */       this.peerTokenTracker.getProps(seqNum, prop);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkPermission(String principal, String action) {
/* 1247 */     SecurityManager sm = System.getSecurityManager();
/* 1248 */     if (sm != null) {
/* 1249 */       ServicePermission perm = new ServicePermission(principal, action);
/*      */       
/* 1251 */       sm.checkPermission(perm);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static String getHexBytes(byte[] bytes, int pos, int len) {
/* 1257 */     StringBuffer sb = new StringBuffer();
/* 1258 */     for (int i = 0; i < len; i++) {
/*      */       
/* 1260 */       int b1 = bytes[i] >> 4 & 0xF;
/* 1261 */       int b2 = bytes[i] & 0xF;
/*      */       
/* 1263 */       sb.append(Integer.toHexString(b1));
/* 1264 */       sb.append(Integer.toHexString(b2));
/* 1265 */       sb.append(' ');
/*      */     } 
/* 1267 */     return sb.toString();
/*      */   }
/*      */   
/*      */   private static String printState(int state) {
/* 1271 */     switch (state) {
/*      */       case 1:
/* 1273 */         return "STATE_NEW";
/*      */       case 2:
/* 1275 */         return "STATE_IN_PROCESS";
/*      */       case 3:
/* 1277 */         return "STATE_DONE";
/*      */       case 4:
/* 1279 */         return "STATE_DELETED";
/*      */     } 
/* 1281 */     return "Unknown state " + state;
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\kerb\Krb5Context.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */