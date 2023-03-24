package org.glassfish.external.statistics;

public interface TimeStatistic extends Statistic {
  long getCount();
  
  long getMaxTime();
  
  long getMinTime();
  
  long getTotalTime();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\external\statistics\TimeStatistic.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */