/**
 * 
 */
package com.impetus.client.rdbms;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;

import com.impetus.client.rdbms.query.RDBMSEntityReader;
import com.impetus.kundera.client.Client;
import com.impetus.kundera.index.IndexManager;
import com.impetus.kundera.index.LuceneIndexer;
import com.impetus.kundera.loader.GenericClientFactory;
import com.impetus.kundera.persistence.EntityReader;

/**
 * @author impadmin
 * 
 */
public class RDBMSClientFactory extends GenericClientFactory
{

    private static Logger logger = Logger.getLogger(RDBMSClientFactory.class);

    IndexManager indexManager;

    EntityReader reader;
    /*
     * (non-Javadoc)
     * 
     * @see com.impetus.kundera.loader.Loader#unload(java.lang.String[])
     */
    @Override
    public void unload(String... paramArrayOfString)
    {
        indexManager.close();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.impetus.kundera.loader.GenericClientFactory#initializeClient()
     */
    @Override
    protected void initializeClient()
    {
        indexManager = new IndexManager(LuceneIndexer.getInstance(new StandardAnalyzer(Version.LUCENE_34)));
        reader = new RDBMSEntityReader();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.impetus.kundera.loader.GenericClientFactory#createPoolOrConnection()
     */
    @Override
    protected Object createPoolOrConnection()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.impetus.kundera.loader.GenericClientFactory#instantiateClient()
     */
    @Override
    protected Client instantiateClient()
    {
        // TODO Auto-generated method stub
        return new HibernateClient(getPersistenceUnit(), indexManager, reader);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.impetus.kundera.loader.GenericClientFactory#isClientThreadSafe()
     */
    @Override
    protected boolean isClientThreadSafe()
    {
        return true;
    }

}