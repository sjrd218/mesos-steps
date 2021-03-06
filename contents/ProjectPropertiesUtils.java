import com.dtolabs.rundeck.core.common.Framework;
import com.dtolabs.rundeck.core.utils.PropertyLookupException;
import com.dtolabs.rundeck.plugins.step.PluginStepContext;

import static java.lang.String.format;

public class ProjectPropertiesUtils {
    public static final String PROJECT_PROPERTY_API_TOKEN = "project.mesos.apitoken";
    public static final String PROJECT_PROPERTY_HOST = "project.mesos.host";
    public static final String PROJECT_PROPERTY_API_CONTEXT = "project.mesos.marathon.context";
    public static final String PROJECT_PROPERTY_PORT = "project.mesos.port";

    static String getMesosApiTokenConfig(PluginStepContext context){
        return getValueOf(PROJECT_PROPERTY_API_TOKEN, context);
    }

    static String getMesosHostPortConfig(PluginStepContext context){
        String marathonContext = getValueOf(PROJECT_PROPERTY_API_CONTEXT, context);
        return format("%s:%s%s",
                getValueOf(PROJECT_PROPERTY_HOST, context),
                getValueOf(PROJECT_PROPERTY_PORT, context),
                marathonContext != null && !marathonContext.isEmpty() ? "/" + marathonContext : "");
    }

    static String getMesosApiTokenConfig(Framework framework, String projectName){
        return getValueOf(PROJECT_PROPERTY_API_TOKEN, framework, projectName);
    }

    static String getMesosHostConfig(Framework framework, String projectName){
        return getValueOf(PROJECT_PROPERTY_HOST, framework, projectName);
    }

    static String getMesosApiContextConfig(Framework framework, String projectName){
        return getValueOf(PROJECT_PROPERTY_API_CONTEXT, framework, projectName);
    }

    static String getMesosPortConfig(Framework framework, String projectName){
        return getValueOf(PROJECT_PROPERTY_PORT, framework, projectName);
    }

    static String getValueOf(String name, PluginStepContext context){
        try{
            return context.getFramework()
                    .getFrameworkProjectMgr().getFrameworkProject(context.getFrameworkProject()).getProperty(name);
        } catch (PropertyLookupException e){
            return "";
        }
    }

    static String getValueOf(String name, Framework framework, String projectName){
        try{
            return framework.getFrameworkProjectMgr().getFrameworkProject(projectName).getProperty(name);
        } catch (PropertyLookupException e){
            return "";
        }
    }
}
