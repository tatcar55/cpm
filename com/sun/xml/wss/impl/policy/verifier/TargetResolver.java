package com.sun.xml.wss.impl.policy.verifier;

import com.sun.xml.wss.XWSSecurityException;
import com.sun.xml.wss.impl.policy.mls.Target;
import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
import java.util.List;

public interface TargetResolver {
  void resolveAndVerifyTargets(List<Target> paramList1, List<Target> paramList2, WSSPolicy paramWSSPolicy) throws XWSSecurityException;
  
  boolean isTargetPresent(List<Target> paramList) throws XWSSecurityException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\verifier\TargetResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */