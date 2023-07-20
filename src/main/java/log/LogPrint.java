package log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogPrint {
//    方便其他类调用，直接在这获取logger对象，然后其他类就直接调用对应的等级就可以噢
    public static final Logger logger = LoggerFactory.getLogger(LogPrint.class);
        }
