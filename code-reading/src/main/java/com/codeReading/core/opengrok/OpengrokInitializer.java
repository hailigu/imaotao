/*
 * CDDL HEADER START
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License (the "License").
 * You may not use this file except in compliance with the License.
 *
 * See LICENSE.txt included in this distribution for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL HEADER in each
 * file and include the License file at LICENSE.txt.
 * If applicable, add the following below this CDDL HEADER, with the
 * fields enclosed by brackets "[]" replaced with your own identifying
 * information: Portions Copyright [yyyy] [name of copyright owner]
 *
 * CDDL HEADER END
 */

/*
 * Copyright (c) 2007, 2010, Oracle and/or its affiliates. All rights reserved.
 */
package com.codeReading.core.opengrok;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.opensolaris.opengrok.OpenGrokLogger;
import org.opensolaris.opengrok.configuration.RuntimeEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Populate the Mercurial Repositories
 *
 * @author Trond Norbye
 */
public final class OpengrokInitializer implements InitializingBean {
	private Logger log = LoggerFactory.getLogger(OpengrokInitializer.class);
	
	@Autowired private OpengrokBean opengrokBean;
	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("########### RuntimeEnvironment initial!!!");
        RuntimeEnvironment env = RuntimeEnvironment.getInstance();

        String config = opengrokBean.getConfigurationpath();
        if (config == null) {
            OpenGrokLogger.getLogger().severe("CONFIGURATION section missing in web.xml");
        } else {
            try {
                env.readConfiguration(new File(config));
            } catch (IOException ex) {
                OpenGrokLogger.getLogger().log(Level.WARNING, "OpenGrok Configuration error. Failed to read config file: ", ex);
            }
        }        
    }
}
