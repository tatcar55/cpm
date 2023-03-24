package com.sun.xml.ws.rx.rm.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.xml.ws.spi.WebServiceFeatureAnnotation;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@WebServiceFeatureAnnotation(id = "http://docs.oasis-open.org/ws-rx/wsrm/", bean = ReliableMessagingFeature.class)
public @interface ReliableMessaging {
  boolean enabled() default true;
  
  RmProtocolVersion version() default RmProtocolVersion.WSRM200702;
  
  long sequenceInactivityTimeout() default 600000L;
  
  long destinationBufferQuota() default 32L;
  
  boolean orderedDeliveryEnabled() default false;
  
  ReliableMessagingFeature.DeliveryAssurance deliveryAssurance() default ReliableMessagingFeature.DeliveryAssurance.EXACTLY_ONCE;
  
  ReliableMessagingFeature.SecurityBinding securityBinding() default ReliableMessagingFeature.SecurityBinding.NONE;
  
  boolean persistenceEnabled() default false;
  
  long sequenceManagerMaintenancePeriod() default 60000L;
  
  long maxConcurrentSessions() default -1L;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\api\ReliableMessaging.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */