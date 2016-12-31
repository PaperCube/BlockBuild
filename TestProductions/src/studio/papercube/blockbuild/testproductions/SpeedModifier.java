package studio.papercube.blockbuild.testproductions;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by imzhy on 2016/12/18.
 */
public class SpeedModifier {
    public static void main(String[] args) throws Exception {
        Class testClass = Class.forName("studio.papercube.blockbuild.edgesupport.binlevel.Mods$SpeedModifier");

        Method mainMethod = testClass.getMethod("main", String[].class);
        mainMethod.invoke(null, (Object)args);
    }
}
