package org.glassfish.external.amx;

import javax.management.ObjectName;
import javax.management.remote.JMXServiceURL;
import org.glassfish.external.arc.Stability;
import org.glassfish.external.arc.Taxonomy;

@Taxonomy(stability = Stability.UNCOMMITTED)
public interface BootAMXMBean {
  public static final String BOOT_AMX_OPERATION_NAME = "bootAMX";
  
  ObjectName bootAMX();
  
  JMXServiceURL[] getJMXServiceURLs();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\external\amx\BootAMXMBean.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */