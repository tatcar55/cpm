package com.sun.xml.ws.rx.rm.runtime.sequence.persistent;

import javax.sql.DataSource;

public interface DataSourceProvider {
  DataSource getDataSource() throws PersistenceException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\sequence\persistent\DataSourceProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */