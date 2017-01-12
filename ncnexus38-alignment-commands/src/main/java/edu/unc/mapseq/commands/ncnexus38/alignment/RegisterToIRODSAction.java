package edu.unc.mapseq.commands.ncnexus38.alignment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.Option;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.unc.mapseq.commons.ncnexus38.alignment.RegisterToIRODSRunnable;
import edu.unc.mapseq.dao.MaPSeqDAOBeanService;
import edu.unc.mapseq.dao.MaPSeqDAOException;
import edu.unc.mapseq.dao.model.WorkflowRunAttempt;

@Command(scope = "ncnexus-baseline", name = "register-to-irods", description = "Register a NCNEXUSBaseline sample output to iRODS")
@Service
public class RegisterToIRODSAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(RegisterToIRODSAction.class);

    @Reference
    private MaPSeqDAOBeanService maPSeqDAOBeanService;

    @Option(name = "--sampleId", description = "Sample Identifier", required = false, multiValued = false)
    private Long sampleId;

    @Option(name = "--flowcellId", description = "Flowcell Identifier", required = false, multiValued = false)
    private Long flowcellId;

    @Option(name = "--workflowRunAttemptId", description = "WorkflowRunAttempt Identifier", required = true, multiValued = false)
    private Long workflowRunAttemptId;

    @Override
    public Object execute() throws Exception {
        logger.debug("ENTERING execute()");
        try {
            ExecutorService es = Executors.newSingleThreadExecutor();
            WorkflowRunAttempt workflowRunAttempt = maPSeqDAOBeanService.getWorkflowRunAttemptDAO().findById(workflowRunAttemptId);
            RegisterToIRODSRunnable runnable = new RegisterToIRODSRunnable(maPSeqDAOBeanService, workflowRunAttempt);
            if (sampleId != null) {
                runnable.setSampleId(sampleId);
            }
            if (flowcellId != null) {
                runnable.setFlowcellId(flowcellId);
            }
            es.submit(runnable);
            es.shutdown();
        } catch (MaPSeqDAOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public Long getFlowcellId() {
        return flowcellId;
    }

    public void setFlowcellId(Long flowcellId) {
        this.flowcellId = flowcellId;
    }

    public Long getSampleId() {
        return sampleId;
    }

    public void setSampleId(Long sampleId) {
        this.sampleId = sampleId;
    }

    public Long getWorkflowRunAttemptId() {
        return workflowRunAttemptId;
    }

    public void setWorkflowRunAttemptId(Long workflowRunAttemptId) {
        this.workflowRunAttemptId = workflowRunAttemptId;
    }

}
