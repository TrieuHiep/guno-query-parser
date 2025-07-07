package vn.guno.global;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.guno.utils.CommonUtils;

import java.io.File;


public class ConfigInfo {

    protected static final Logger cLogger = LogManager.getLogger("CacheLog");

//    private static Config config = ConfigFactory.parseFile(new File("conf.properties"));

    private static final Config config;

    static {
        String profileName = CommonUtils.getActiveProfile();
        String confProfile = "conf-" + profileName + ".properties";
        cLogger.info("got profile name {} ---> loading config file {}", profileName, confProfile);
        config = ConfigFactory.parseFile(new File(confProfile));
    }

    public static final int SERVICE_PORT = config.getInt("service.port");

}
