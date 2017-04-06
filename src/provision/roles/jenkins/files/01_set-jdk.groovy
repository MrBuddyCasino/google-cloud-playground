import jenkins.model.*
import hudson.model.*

// Jenkins global JDK configuration
def jdkConfig = Jenkins.getInstance().getExtensionList(hudson.model.JDK.DescriptorImpl.class)[0]
jdkConfig.setInstallations(new hudson.model.JDK("JDK_8_112", "/usr/local/jdk1.8.0_112/"))
jdkConfig.save()