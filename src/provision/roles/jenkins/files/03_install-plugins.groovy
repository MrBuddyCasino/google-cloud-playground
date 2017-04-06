import jenkins.model.*

def installed = false
def initialized = false
def plugins = [
    "structs",
    "vsphere-cloud",
    "config-file-provider",
    "managed-scripts",
    "job-dsl",
    "workflow-step-api",
    "ansicolor",
    "matrix-project",
    "dashboard-view",
    "token-macro",
    "analysis-core",
    "maven-plugin",
    "ssh-credentials",
    "credentials",
    "config-file-provider",
    "ssh-slaves",
    "durable-task",
    "docker-plugin",
    "ivy",
    "envinject",
    "cloudbees-folder",
    "git",
    "jquery",
    "rebuild",
    "git-parameter",
    "parameterized-trigger",
    "workflow-scm-step",
    "scm-api",
    "git-parameter",
    "promoted-builds",
    "git-parameter",
    "mailer",
    "git-client",
    "junit",
    "display-url-api",
    "mask-passwords",
    "blueocean",
    "copyartifact",
    "credentials-binding",
    "disk-usage",
    "emotional-jenkins-plugin",
    "multiple-scms",
    "ws-cleanup",
    "build-user-vars-plugin",
    "build-pipeline-plugin",
    "sbt",
    "clone-workspace-scm"
]

def instance = Jenkins.getInstance()
def pm = instance.getPluginManager()
def uc = instance.getUpdateCenter()
uc.updateAllSites()

plugins.each {
    if (!pm.getPlugin(it)) {
        if (!initialized) {
            uc.updateAllSites()
            initialized = true
        }
        def plugin = uc.getPlugin(it)
        if (plugin) {
            plugin.deploy()
            installed = true
        }
    }
}

if (installed) {
    instance.save()
    instance.doSafeRestart()
}