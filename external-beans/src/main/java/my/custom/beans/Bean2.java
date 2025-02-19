package my.custom.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bean2 {

    private static final Logger LOG = LoggerFactory.getLogger(Bean1.class);

    public Bean2() {
        LOG.info("Bean 2 was created");
    }

}
