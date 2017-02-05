package org.lendi.umtapo.solr.service.implementation;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.lendi.umtapo.entity.Borrower;
import org.lendi.umtapo.mapper.BorrowerMapper;
import org.lendi.umtapo.solr.document.BorrowerDocument;
import org.lendi.umtapo.solr.repository.specific.SolrBorrowerRepository;
import org.lendi.umtapo.solr.service.SolrBorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;

/**
 * The type Borrower index service.
 */
@Service
public class SolrBorrowerServiceImpl implements SolrBorrowerService {

    private static final Logger LOGGER = Logger.getLogger(SolrBorrowerServiceImpl.class);

    private SolrBorrowerRepository documentRepository;
    private final BorrowerMapper borrowerMapper;

    /**
     * Instantiates a new Borrower index service.
     *
     * @param documentRepository the document repository
     */
    @Autowired
    public SolrBorrowerServiceImpl(SolrBorrowerRepository documentRepository, BorrowerMapper borrowerMapper) {
        Assert.notNull(documentRepository);
        Assert.notNull(borrowerMapper);

        this.documentRepository = documentRepository;
        this.borrowerMapper = borrowerMapper;
    }

    @Override
    public void addToIndex(Borrower borrower) {
        BorrowerDocument document = this.mapBorrowerToBorrowerDocument(borrower);

        try {
            documentRepository.save(document);
        } catch (final IOException | SolrServerException e) {
            LOGGER.error(e.getStackTrace());
        }
    }

    @Override
    public List<BorrowerDocument> searchByNameOrEmail(String term) {
        List<BorrowerDocument> result = null;
        try {
            result = this.documentRepository.searchByNameOrEmail(term);
        } catch (final IOException | SolrServerException e) {
            LOGGER.error(e.getStackTrace());
        }

        return result;
    }

    @Override
    public void deleteFromIndex(Integer id) {
        try {
            documentRepository.delete(id.toString());
        } catch (final IOException | SolrServerException e) {
            LOGGER.error(e.getStackTrace());
        }
    }

    private BorrowerDocument mapBorrowerToBorrowerDocument(Borrower borrower) {
        return this.borrowerMapper.mapBorrowerToBorrowerDocument(borrower);
    }
}
