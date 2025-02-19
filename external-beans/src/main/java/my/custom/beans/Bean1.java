package my.custom.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bean1 {

    private static final Logger LOG = LoggerFactory.getLogger(Bean1.class);

    public Bean1() {
        LOG.info("Bean 1 was created");
    }

}
