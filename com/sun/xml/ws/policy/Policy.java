/*     */ package com.sun.xml.ws.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.privateutil.LocalizationMessages;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyUtils;
/*     */ import com.sun.xml.ws.policy.sourcemodel.wspolicy.NamespaceVersion;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Policy
/*     */   implements Iterable<AssertionSet>
/*     */ {
/*     */   private static final String POLICY_TOSTRING_NAME = "policy";
/*  72 */   private static final List<AssertionSet> NULL_POLICY_ASSERTION_SETS = Collections.unmodifiableList(new LinkedList<AssertionSet>());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   private static final List<AssertionSet> EMPTY_POLICY_ASSERTION_SETS = Collections.unmodifiableList(new LinkedList<AssertionSet>(Arrays.asList(new AssertionSet[] { AssertionSet.emptyAssertionSet() })));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   private static final Set<QName> EMPTY_VOCABULARY = Collections.unmodifiableSet(new TreeSet<QName>(PolicyUtils.Comparison.QNAME_COMPARATOR));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   private static final Policy ANONYMOUS_NULL_POLICY = new Policy(null, null, NULL_POLICY_ASSERTION_SETS, EMPTY_VOCABULARY);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   private static final Policy ANONYMOUS_EMPTY_POLICY = new Policy(null, null, EMPTY_POLICY_ASSERTION_SETS, EMPTY_VOCABULARY);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String policyId;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private NamespaceVersion nsVersion;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final List<AssertionSet> assertionSets;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Set<QName> vocabulary;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Collection<QName> immutableVocabulary;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String toStringName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Policy createNullPolicy() {
/* 142 */     return ANONYMOUS_NULL_POLICY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Policy createEmptyPolicy() {
/* 153 */     return ANONYMOUS_EMPTY_POLICY;
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
/*     */   public static Policy createNullPolicy(String name, String policyId) {
/* 165 */     if (name == null && policyId == null) {
/* 166 */       return ANONYMOUS_NULL_POLICY;
/*     */     }
/* 168 */     return new Policy(name, policyId, NULL_POLICY_ASSERTION_SETS, EMPTY_VOCABULARY);
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
/*     */   public static Policy createNullPolicy(NamespaceVersion nsVersion, String name, String policyId) {
/* 182 */     if ((nsVersion == null || nsVersion == NamespaceVersion.getLatestVersion()) && name == null && policyId == null) {
/* 183 */       return ANONYMOUS_NULL_POLICY;
/*     */     }
/* 185 */     return new Policy(nsVersion, name, policyId, NULL_POLICY_ASSERTION_SETS, EMPTY_VOCABULARY);
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
/*     */   public static Policy createEmptyPolicy(String name, String policyId) {
/* 200 */     if (name == null && policyId == null) {
/* 201 */       return ANONYMOUS_EMPTY_POLICY;
/*     */     }
/* 203 */     return new Policy(name, policyId, EMPTY_POLICY_ASSERTION_SETS, EMPTY_VOCABULARY);
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
/*     */   public static Policy createEmptyPolicy(NamespaceVersion nsVersion, String name, String policyId) {
/* 219 */     if ((nsVersion == null || nsVersion == NamespaceVersion.getLatestVersion()) && name == null && policyId == null) {
/* 220 */       return ANONYMOUS_EMPTY_POLICY;
/*     */     }
/* 222 */     return new Policy(nsVersion, name, policyId, EMPTY_POLICY_ASSERTION_SETS, EMPTY_VOCABULARY);
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
/*     */   public static Policy createPolicy(Collection<AssertionSet> sets) {
/* 239 */     if (sets == null || sets.isEmpty()) {
/* 240 */       return createNullPolicy();
/*     */     }
/* 242 */     return new Policy("policy", sets);
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
/*     */   public static Policy createPolicy(String name, String policyId, Collection<AssertionSet> sets) {
/* 261 */     if (sets == null || sets.isEmpty()) {
/* 262 */       return createNullPolicy(name, policyId);
/*     */     }
/* 264 */     return new Policy("policy", name, policyId, sets);
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
/*     */   public static Policy createPolicy(NamespaceVersion nsVersion, String name, String policyId, Collection<AssertionSet> sets) {
/* 284 */     if (sets == null || sets.isEmpty()) {
/* 285 */       return createNullPolicy(nsVersion, name, policyId);
/*     */     }
/* 287 */     return new Policy(nsVersion, "policy", name, policyId, sets);
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
/*     */   private Policy(String name, String policyId, List<AssertionSet> assertionSets, Set<QName> vocabulary) {
/* 304 */     this.nsVersion = NamespaceVersion.getLatestVersion();
/* 305 */     this.toStringName = "policy";
/* 306 */     this.name = name;
/* 307 */     this.policyId = policyId;
/* 308 */     this.assertionSets = assertionSets;
/* 309 */     this.vocabulary = vocabulary;
/* 310 */     this.immutableVocabulary = Collections.unmodifiableCollection(this.vocabulary);
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
/*     */   Policy(String toStringName, Collection<AssertionSet> sets) {
/* 325 */     this.nsVersion = NamespaceVersion.getLatestVersion();
/* 326 */     this.toStringName = toStringName;
/*     */     
/* 328 */     if (sets == null || sets.isEmpty()) {
/* 329 */       this.assertionSets = NULL_POLICY_ASSERTION_SETS;
/* 330 */       this.vocabulary = EMPTY_VOCABULARY;
/* 331 */       this.immutableVocabulary = EMPTY_VOCABULARY;
/*     */     } else {
/* 333 */       this.assertionSets = new LinkedList<AssertionSet>();
/* 334 */       this.vocabulary = new TreeSet<QName>(PolicyUtils.Comparison.QNAME_COMPARATOR);
/* 335 */       this.immutableVocabulary = Collections.unmodifiableCollection(this.vocabulary);
/*     */       
/* 337 */       addAll(sets);
/*     */     } 
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
/*     */   Policy(String toStringName, String name, String policyId, Collection<AssertionSet> sets) {
/* 355 */     this(toStringName, sets);
/* 356 */     this.name = name;
/* 357 */     this.policyId = policyId;
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
/*     */   private Policy(NamespaceVersion nsVersion, String name, String policyId, List<AssertionSet> assertionSets, Set<QName> vocabulary) {
/* 374 */     this.nsVersion = nsVersion;
/* 375 */     this.toStringName = "policy";
/* 376 */     this.name = name;
/* 377 */     this.policyId = policyId;
/* 378 */     this.assertionSets = assertionSets;
/* 379 */     this.vocabulary = vocabulary;
/* 380 */     this.immutableVocabulary = Collections.unmodifiableCollection(this.vocabulary);
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
/*     */   Policy(NamespaceVersion nsVersion, String toStringName, Collection<AssertionSet> sets) {
/* 396 */     this.nsVersion = nsVersion;
/* 397 */     this.toStringName = toStringName;
/*     */     
/* 399 */     if (sets == null || sets.isEmpty()) {
/* 400 */       this.assertionSets = NULL_POLICY_ASSERTION_SETS;
/* 401 */       this.vocabulary = EMPTY_VOCABULARY;
/* 402 */       this.immutableVocabulary = EMPTY_VOCABULARY;
/*     */     } else {
/* 404 */       this.assertionSets = new LinkedList<AssertionSet>();
/* 405 */       this.vocabulary = new TreeSet<QName>(PolicyUtils.Comparison.QNAME_COMPARATOR);
/* 406 */       this.immutableVocabulary = Collections.unmodifiableCollection(this.vocabulary);
/*     */       
/* 408 */       addAll(sets);
/*     */     } 
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
/*     */   Policy(NamespaceVersion nsVersion, String toStringName, String name, String policyId, Collection<AssertionSet> sets) {
/* 427 */     this(nsVersion, toStringName, sets);
/* 428 */     this.name = name;
/* 429 */     this.policyId = policyId;
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
/*     */   private boolean add(AssertionSet set) {
/* 441 */     if (set == null) {
/* 442 */       return false;
/*     */     }
/*     */     
/* 445 */     if (this.assertionSets.contains(set)) {
/* 446 */       return false;
/*     */     }
/* 448 */     this.assertionSets.add(set);
/* 449 */     this.vocabulary.addAll(set.getVocabulary());
/* 450 */     return true;
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
/*     */   private boolean addAll(Collection<AssertionSet> sets) {
/* 465 */     assert sets != null && !sets.isEmpty() : LocalizationMessages.WSP_0036_PRIVATE_METHOD_DOES_NOT_ACCEPT_NULL_OR_EMPTY_COLLECTION();
/*     */     
/* 467 */     boolean result = true;
/* 468 */     for (AssertionSet set : sets) {
/* 469 */       result &= add(set);
/*     */     }
/* 471 */     Collections.sort(this.assertionSets);
/*     */     
/* 473 */     return result;
/*     */   }
/*     */   
/*     */   Collection<AssertionSet> getContent() {
/* 477 */     return this.assertionSets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 486 */     return this.policyId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 495 */     return this.name;
/*     */   }
/*     */   
/*     */   public NamespaceVersion getNamespaceVersion() {
/* 499 */     return this.nsVersion;
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
/*     */   public String getIdOrName() {
/* 511 */     if (this.policyId != null) {
/* 512 */       return this.policyId;
/*     */     }
/* 514 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumberOfAssertionSets() {
/* 523 */     return this.assertionSets.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<AssertionSet> iterator() {
/* 533 */     return this.assertionSets.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNull() {
/* 542 */     return (this.assertionSets.size() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 551 */     return (this.assertionSets.size() == 1 && ((AssertionSet)this.assertionSets.get(0)).isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(String namespaceUri) {
/* 561 */     for (QName entry : this.vocabulary) {
/* 562 */       if (entry.getNamespaceURI().equals(namespaceUri)) {
/* 563 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 567 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<QName> getVocabulary() {
/* 577 */     return this.immutableVocabulary;
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
/*     */   public boolean contains(QName assertionName) {
/* 589 */     return this.vocabulary.contains(assertionName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 597 */     if (this == obj) {
/* 598 */       return true;
/*     */     }
/*     */     
/* 601 */     if (!(obj instanceof Policy)) {
/* 602 */       return false;
/*     */     }
/*     */     
/* 605 */     Policy that = (Policy)obj;
/*     */     
/* 607 */     boolean result = true;
/*     */     
/* 609 */     result = (result && this.vocabulary.equals(that.vocabulary));
/* 610 */     result = (result && this.assertionSets.size() == that.assertionSets.size() && this.assertionSets.containsAll(that.assertionSets));
/*     */     
/* 612 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 620 */     int result = 17;
/*     */     
/* 622 */     result = 37 * result + this.vocabulary.hashCode();
/* 623 */     result = 37 * result + this.assertionSets.hashCode();
/*     */     
/* 625 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 633 */     return toString(0, new StringBuffer()).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   StringBuffer toString(int indentLevel, StringBuffer buffer) {
/* 644 */     String indent = PolicyUtils.Text.createIndent(indentLevel);
/* 645 */     String innerIndent = PolicyUtils.Text.createIndent(indentLevel + 1);
/* 646 */     String innerDoubleIndent = PolicyUtils.Text.createIndent(indentLevel + 2);
/*     */     
/* 648 */     buffer.append(indent).append(this.toStringName).append(" {").append(PolicyUtils.Text.NEW_LINE);
/* 649 */     buffer.append(innerIndent).append("namespace version = '").append(this.nsVersion.name()).append('\'').append(PolicyUtils.Text.NEW_LINE);
/* 650 */     buffer.append(innerIndent).append("id = '").append(this.policyId).append('\'').append(PolicyUtils.Text.NEW_LINE);
/* 651 */     buffer.append(innerIndent).append("name = '").append(this.name).append('\'').append(PolicyUtils.Text.NEW_LINE);
/*     */     
/* 653 */     buffer.append(innerIndent).append("vocabulary {").append(PolicyUtils.Text.NEW_LINE);
/* 654 */     if (this.vocabulary.isEmpty()) {
/* 655 */       buffer.append(innerDoubleIndent).append("no entries").append(PolicyUtils.Text.NEW_LINE);
/*     */     } else {
/* 657 */       int index = 1;
/* 658 */       for (QName entry : this.vocabulary) {
/* 659 */         buffer.append(innerDoubleIndent).append(index++).append(". entry = '").append(entry.getNamespaceURI()).append(':').append(entry.getLocalPart()).append('\'').append(PolicyUtils.Text.NEW_LINE);
/*     */       }
/*     */     } 
/* 662 */     buffer.append(innerIndent).append('}').append(PolicyUtils.Text.NEW_LINE);
/*     */     
/* 664 */     if (this.assertionSets.isEmpty()) {
/* 665 */       buffer.append(innerIndent).append("no assertion sets").append(PolicyUtils.Text.NEW_LINE);
/*     */     } else {
/* 667 */       for (AssertionSet set : this.assertionSets) {
/* 668 */         set.toString(indentLevel + 1, buffer).append(PolicyUtils.Text.NEW_LINE);
/*     */       }
/*     */     } 
/*     */     
/* 672 */     buffer.append(indent).append('}');
/*     */     
/* 674 */     return buffer;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\Policy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */