package com.sun.xml.wss.saml.internal.saml11.jaxb10;

import java.util.Calendar;
import java.util.List;

public interface ConditionsType {
  Calendar getNotOnOrAfter();
  
  void setNotOnOrAfter(Calendar paramCalendar);
  
  Calendar getNotBefore();
  
  void setNotBefore(Calendar paramCalendar);
  
  List getAudienceRestrictionConditionOrDoNotCacheConditionOrCondition();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\ConditionsType.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */