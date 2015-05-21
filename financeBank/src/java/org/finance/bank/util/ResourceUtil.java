package org.finance.bank.util;

/**
 *
 * @author oscar
 */
import java.net.URL;
import javax.swing.ImageIcon;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ResourceUtil {

    private static Log log = LogFactory.getLog(ResourceUtil.class);

    /** XXX Abstraction around how we find resources, do something smarter
    in the future. */
    public static URL findResource(String name) {
        return ClassLoader.getSystemResource(name);
    }

    public static ImageIcon getImageIcon(String imageResource) {
        URL res = findResource(imageResource);
        if (res != null) {
            return new ImageIcon(res);
        } else {
            log.warn("Didn't find resource " + imageResource + ", returning null");
            return null;
        }
    }
}
