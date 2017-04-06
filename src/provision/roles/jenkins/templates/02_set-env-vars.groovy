import jenkins.model.*

def instance = Jenkins.getInstance()
def globalNodeProperties = instance.getGlobalNodeProperties()
def envVarsNodePropertyList = globalNodeProperties.getAll(hudson.slaves.EnvironmentVariablesNodeProperty.class)
def newEnvVarsNodeProperty = null
def envVars = null

if (envVarsNodePropertyList == null || envVarsNodePropertyList.size() == 0) {
    newEnvVarsNodeProperty = new hudson.slaves.EnvironmentVariablesNodeProperty();
    globalNodeProperties.add(newEnvVarsNodeProperty)
    envVars = newEnvVarsNodeProperty.getEnvVars()
} else {
    envVars = envVarsNodePropertyList.get(0).getEnvVars()
}

envVars.put("GCP_DEFAULT_REGION", "europe-west1")
envVars.put("GCP_K8_USERNAME", "k8admin")

envVars.put("GCP_DEVELOPMENT_K8_PASSWORD", "{{  gcp_development_k8_password }}")
envVars.put("GCP_CONSOLIDATION_K8_PASSWORD", "{{  gcp_consolidation_k8_password }}")
envVars.put("GCP_PRODUCTION_K8_PASSWORD", "{{  gcp_production_k8_password }}")

envVars.put("VPN_SHARED_SECRET", "{{ vpn_shared_secret }}")

instance.save()
