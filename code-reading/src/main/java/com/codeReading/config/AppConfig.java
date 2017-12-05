package com.codeReading.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@ComponentScan({"com.codeReading.busi.dpn", "com.codeReading.busi.service" })
@Import({OfflineMessageConfig.class, UpYunConfig.class, OpengrokConfig.class})
public class AppConfig {

}
