/*     */ package com.sun.xml.ws.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.privateutil.LocalizationMessages;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyUtils;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
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
/*     */ public final class AssertionSet
/*     */   implements Iterable<PolicyAssertion>, Comparable<AssertionSet>
/*     */ {
/*  63 */   private static final AssertionSet EMPTY_ASSERTION_SET = new AssertionSet(Collections.unmodifiableList(new LinkedList<PolicyAssertion>()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   private static final Comparator<PolicyAssertion> ASSERTION_COMPARATOR = new Comparator<PolicyAssertion>() {
/*     */       public int compare(PolicyAssertion pa1, PolicyAssertion pa2) {
/*  77 */         if (pa1.equals(pa2)) {
/*  78 */           return 0;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*  83 */         int result = PolicyUtils.Comparison.QNAME_COMPARATOR.compare(pa1.getName(), pa2.getName());
/*  84 */         if (result != 0) {
/*  85 */           return result;
/*     */         }
/*     */         
/*  88 */         result = PolicyUtils.Comparison.compareNullableStrings(pa1.getValue(), pa2.getValue());
/*  89 */         if (result != 0) {
/*  90 */           return result;
/*     */         }
/*     */         
/*  93 */         result = PolicyUtils.Comparison.compareBoolean(pa1.hasNestedAssertions(), pa2.hasNestedAssertions());
/*  94 */         if (result != 0) {
/*  95 */           return result;
/*     */         }
/*     */         
/*  98 */         result = PolicyUtils.Comparison.compareBoolean(pa1.hasNestedPolicy(), pa2.hasNestedPolicy());
/*  99 */         if (result != 0) {
/* 100 */           return result;
/*     */         }
/*     */         
/* 103 */         return Math.round(Math.signum((pa1.hashCode() - pa2.hashCode())));
/*     */       }
/*     */     };
/*     */   
/*     */   private final List<PolicyAssertion> assertions;
/* 108 */   private final Set<QName> vocabulary = new TreeSet<QName>(PolicyUtils.Comparison.QNAME_COMPARATOR);
/* 109 */   private final Collection<QName> immutableVocabulary = Collections.unmodifiableCollection(this.vocabulary);
/*     */   
/*     */   private AssertionSet(List<PolicyAssertion> list) {
/* 112 */     assert list != null : LocalizationMessages.WSP_0037_PRIVATE_CONSTRUCTOR_DOES_NOT_TAKE_NULL();
/* 113 */     this.assertions = list;
/*     */   }
/*     */   
/*     */   private AssertionSet(Collection<AssertionSet> alternatives) {
/* 117 */     this.assertions = new LinkedList<PolicyAssertion>();
/* 118 */     for (AssertionSet alternative : alternatives) {
/* 119 */       addAll(alternative.assertions);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean add(PolicyAssertion assertion) {
/* 124 */     if (assertion == null) {
/* 125 */       return false;
/*     */     }
/*     */     
/* 128 */     if (this.assertions.contains(assertion)) {
/* 129 */       return false;
/*     */     }
/* 131 */     this.assertions.add(assertion);
/* 132 */     this.vocabulary.add(assertion.getName());
/* 133 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean addAll(Collection<? extends PolicyAssertion> assertions) {
/* 138 */     boolean result = true;
/*     */     
/* 140 */     if (assertions != null) {
/* 141 */       for (PolicyAssertion assertion : assertions) {
/* 142 */         result &= add(assertion);
/*     */       }
/*     */     }
/*     */     
/* 146 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Collection<PolicyAssertion> getAssertions() {
/* 155 */     return this.assertions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Collection<QName> getVocabulary() {
/* 165 */     return this.immutableVocabulary;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isCompatibleWith(AssertionSet alternative, PolicyIntersector.CompatibilityMode mode) {
/* 176 */     boolean result = (mode == PolicyIntersector.CompatibilityMode.LAX || this.vocabulary.equals(alternative.vocabulary));
/*     */     
/* 178 */     result = (result && areAssertionsCompatible(alternative, mode));
/* 179 */     result = (result && alternative.areAssertionsCompatible(this, mode));
/*     */     
/* 181 */     return result;
/*     */   }
/*     */   
/*     */   private boolean areAssertionsCompatible(AssertionSet alternative, PolicyIntersector.CompatibilityMode mode) {
/* 185 */     label17: for (PolicyAssertion thisAssertion : this.assertions) {
/* 186 */       if (mode == PolicyIntersector.CompatibilityMode.STRICT || !thisAssertion.isIgnorable()) {
/* 187 */         for (PolicyAssertion thatAssertion : alternative.assertions) {
/* 188 */           if (thisAssertion.isCompatibleWith(thatAssertion, mode)) {
/*     */             continue label17;
/*     */           }
/*     */         } 
/* 192 */         return false;
/*     */       } 
/*     */     } 
/* 195 */     return true;
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
/*     */   public static AssertionSet createMergedAssertionSet(Collection<AssertionSet> alternatives) {
/* 210 */     if (alternatives == null || alternatives.isEmpty()) {
/* 211 */       return EMPTY_ASSERTION_SET;
/*     */     }
/*     */     
/* 214 */     AssertionSet result = new AssertionSet(alternatives);
/* 215 */     Collections.sort(result.assertions, ASSERTION_COMPARATOR);
/*     */     
/* 217 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AssertionSet createAssertionSet(Collection<? extends PolicyAssertion> assertions) {
/* 227 */     if (assertions == null || assertions.isEmpty()) {
/* 228 */       return EMPTY_ASSERTION_SET;
/*     */     }
/*     */     
/* 231 */     AssertionSet result = new AssertionSet(new LinkedList<PolicyAssertion>());
/* 232 */     result.addAll(assertions);
/* 233 */     Collections.sort(result.assertions, ASSERTION_COMPARATOR);
/*     */     
/* 235 */     return result;
/*     */   }
/*     */   
/*     */   public static AssertionSet emptyAssertionSet() {
/* 239 */     return EMPTY_ASSERTION_SET;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<PolicyAssertion> iterator() {
/* 247 */     return this.assertions.iterator();
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
/*     */   public Collection<PolicyAssertion> get(QName name) {
/* 259 */     List<PolicyAssertion> matched = new LinkedList<PolicyAssertion>();
/*     */     
/* 261 */     if (this.vocabulary.contains(name))
/*     */     {
/* 263 */       for (PolicyAssertion assertion : this.assertions) {
/* 264 */         if (assertion.getName().equals(name)) {
/* 265 */           matched.add(assertion);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 270 */     return matched;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 279 */     return this.assertions.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(QName assertionName) {
/* 289 */     return this.vocabulary.contains(assertionName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(AssertionSet that) {
/* 297 */     if (equals(that)) {
/* 298 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 302 */     Iterator<QName> vIterator1 = getVocabulary().iterator();
/* 303 */     Iterator<QName> vIterator2 = that.getVocabulary().iterator();
/* 304 */     while (vIterator1.hasNext()) {
/* 305 */       QName entry1 = vIterator1.next();
/* 306 */       if (vIterator2.hasNext()) {
/* 307 */         QName entry2 = vIterator2.next();
/* 308 */         int result = PolicyUtils.Comparison.QNAME_COMPARATOR.compare(entry1, entry2);
/* 309 */         if (result != 0)
/* 310 */           return result; 
/*     */         continue;
/*     */       } 
/* 313 */       return 1;
/*     */     } 
/*     */ 
/*     */     
/* 317 */     if (vIterator2.hasNext()) {
/* 318 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 322 */     Iterator<PolicyAssertion> pIterator1 = getAssertions().iterator();
/* 323 */     Iterator<PolicyAssertion> pIterator2 = that.getAssertions().iterator();
/* 324 */     while (pIterator1.hasNext()) {
/* 325 */       PolicyAssertion pa1 = pIterator1.next();
/* 326 */       if (pIterator2.hasNext()) {
/* 327 */         PolicyAssertion pa2 = pIterator2.next();
/* 328 */         int result = ASSERTION_COMPARATOR.compare(pa1, pa2);
/* 329 */         if (result != 0)
/* 330 */           return result; 
/*     */         continue;
/*     */       } 
/* 333 */       return 1;
/*     */     } 
/*     */ 
/*     */     
/* 337 */     if (pIterator2.hasNext()) {
/* 338 */       return -1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 344 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 351 */     if (this == obj) {
/* 352 */       return true;
/*     */     }
/*     */     
/* 355 */     if (!(obj instanceof AssertionSet)) {
/* 356 */       return false;
/*     */     }
/*     */     
/* 359 */     AssertionSet that = (AssertionSet)obj;
/* 360 */     boolean result = true;
/*     */     
/* 362 */     result = (result && this.vocabulary.equals(that.vocabulary));
/* 363 */     result = (result && this.assertions.size() == that.assertions.size() && this.assertions.containsAll(that.assertions));
/*     */     
/* 365 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 372 */     int result = 17;
/*     */     
/* 374 */     result = 37 * result + this.vocabulary.hashCode();
/* 375 */     result = 37 * result + this.assertions.hashCode();
/*     */     
/* 377 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 384 */     return toString(0, new StringBuffer()).toString();
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
/* 395 */     String indent = PolicyUtils.Text.createIndent(indentLevel);
/* 396 */     String innerIndent = PolicyUtils.Text.createIndent(indentLevel + 1);
/*     */     
/* 398 */     buffer.append(indent).append("assertion set {").append(PolicyUtils.Text.NEW_LINE);
/*     */     
/* 400 */     if (this.assertions.isEmpty()) {
/* 401 */       buffer.append(innerIndent).append("no assertions").append(PolicyUtils.Text.NEW_LINE);
/*     */     } else {
/* 403 */       for (PolicyAssertion assertion : this.assertions) {
/* 404 */         assertion.toString(indentLevel + 1, buffer).append(PolicyUtils.Text.NEW_LINE);
/*     */       }
/*     */     } 
/*     */     
/* 408 */     buffer.append(indent).append('}');
/*     */     
/* 410 */     return buffer;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\AssertionSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */