package edu.unc.mapseq.executor.ncnexus38.alignment;

import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NCNEXUS38AlignmentWorkflowExecutorService {

    private static final Logger logger = LoggerFactory.getLogger(NCNEXUS38AlignmentWorkflowExecutorService.class);

    private final Timer mainTimer = new Timer();

    private NCNEXUS38AlignmentWorkflowExecutorTask task;

    private Long period = 5L;

    public NCNEXUS38AlignmentWorkflowExecutorService() {
        super();
    }

    public void start() throws Exception {
        logger.info("ENTERING start()");
        long delay = 1 * 60 * 1000;
        mainTimer.scheduleAtFixedRate(task, delay, period * 60 * 1000);
    }

    public void stop() throws Exception {
        logger.info("ENTERING stop()");
        mainTimer.purge();
        mainTimer.cancel();
    }

    public NCNEXUS38AlignmentWorkflowExecutorTask getTask() {
        return task;
    }

    public void setTask(NCNEXUS38AlignmentWorkflowExecutorTask task) {
        this.task = task;
    }

    public Long getPeriod() {
        return period;
    }

    public void setPeriod(Long period) {
        this.period = period;
    }

}
