package edu.unc.mapseq.ws.ncnexus38.alignment.impl;

import java.io.File;
import java.util.Set;

import org.renci.vcf.VCFParser;
import org.renci.vcf.VCFResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.unc.mapseq.dao.MaPSeqDAOException;
import edu.unc.mapseq.dao.SampleDAO;
import edu.unc.mapseq.dao.model.FileData;
import edu.unc.mapseq.dao.model.MimeType;
import edu.unc.mapseq.dao.model.Sample;
import edu.unc.mapseq.ws.ncnexus38.alignment.NCNEXUS38AlignmentService;

public class NCNEXUS38AlignmentServiceImpl implements NCNEXUS38AlignmentService {

    private static final Logger logger = LoggerFactory.getLogger(NCNEXUS38AlignmentServiceImpl.class);

    private SampleDAO sampleDAO;

    public NCNEXUS38AlignmentServiceImpl() {
        super();
    }

    @Override
    public VCFResult identityCheck(Long sampleId) {
        logger.debug("ENTERING identityCheck(Long)");

        VCFParser parser = VCFParser.getInstance();

        VCFResult ret = null;
        if (sampleId == null) {
            logger.warn("sampleId is empty");
            return null;
        }

        Sample sample = null;
        try {
            sample = sampleDAO.findById(sampleId);
            logger.info(sample.toString());
        } catch (MaPSeqDAOException e) {
            logger.error("Failed to find Sample", e);
        }

        if (sample == null) {
            return null;
        }

        Set<FileData> sampleFileDataSet = sample.getFileDatas();

        if (sampleFileDataSet != null) {
            for (FileData fileData : sampleFileDataSet) {
                if (MimeType.TEXT_VCF.equals(fileData.getMimeType()) && fileData.getName().endsWith(".ic.vcf")) {
                    File icSNPVCFFile = new File(fileData.getPath(), fileData.getName());
                    logger.info("identity check file is: {}", icSNPVCFFile.getAbsolutePath());
                    if (icSNPVCFFile.exists()) {
                        ret = parser.parse(icSNPVCFFile);
                    }
                }
            }
        }
        return ret;
    }

    public SampleDAO getSampleDAO() {
        return sampleDAO;
    }

    public void setSampleDAO(SampleDAO sampleDAO) {
        this.sampleDAO = sampleDAO;
    }

}
