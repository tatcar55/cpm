package org.glassfish.external.statistics;

public interface RangeStatistic extends Statistic {
  long getHighWaterMark();
  
  long getLowWaterMark();
  
  long getCurrent();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\external\statistics\RangeStatistic.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */