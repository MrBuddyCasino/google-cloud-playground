import jenkins.model.*

def jobName = "seed"

def configXml = """\
<?xml version='1.0' encoding='UTF-8'?>
<project>
    <actions/>
    <description>seed job</description>
    <keepDependencies>false</keepDependencies>
    <properties>
        <jenkins.model.BuildDiscarderProperty>
            <strategy class="hudson.tasks.LogRotator">
                <daysToKeep>30</daysToKeep>
                <numToKeep>10</numToKeep>
                <artifactDaysToKeep>-1</artifactDaysToKeep>
                <artifactNumToKeep>-1</artifactNumToKeep>
            </strategy>
        </jenkins.model.BuildDiscarderProperty>
        <hudson.plugins.disk__usage.DiskUsageProperty plugin="disk-usage@0.28"/>
        <com.sonyericsson.rebuild.RebuildSettings plugin="rebuild@1.25">
            <autoRebuild>false</autoRebuild>
            <rebuildDisabled>false</rebuildDisabled>
        </com.sonyericsson.rebuild.RebuildSettings>
    </properties>
    <scm class="hudson.plugins.git.GitSCM" plugin="git@3.0.1">
        <configVersion>2</configVersion>
        <userRemoteConfigs>
            <hudson.plugins.git.UserRemoteConfig>
                <url>git@github.com:codecentric/conrad-jobseed.git</url>
                <credentialsId>jenkins_privatekey_authentication</credentialsId>
            </hudson.plugins.git.UserRemoteConfig>
        </userRemoteConfigs>
        <branches>
            <hudson.plugins.git.BranchSpec>
                <name>*/master</name>
            </hudson.plugins.git.BranchSpec>
        </branches>
        <doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations>
        <submoduleCfg class="list"/>
        <extensions/>
    </scm>
    <canRoam>true</canRoam>
    <disabled>false</disabled>
    <blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>
    <blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>
    <triggers/>
    <concurrentBuild>false</concurrentBuild>
    <builders>
        <javaposse.jobdsl.plugin.ExecuteDslScripts plugin="job-dsl@1.53">
            <targets>*.groovy</targets>
            <usingScriptText>false</usingScriptText>
            <ignoreExisting>false</ignoreExisting>
            <ignoreMissingFiles>false</ignoreMissingFiles>
            <failOnMissingPlugin>true</failOnMissingPlugin>
            <unstableOnDeprecation>true</unstableOnDeprecation>
            <removedJobAction>DELETE</removedJobAction>
            <removedViewAction>DELETE</removedViewAction>
            <lookupStrategy>JENKINS_ROOT</lookupStrategy>
        </javaposse.jobdsl.plugin.ExecuteDslScripts>
    </builders>
    <publishers/>
    <buildWrappers/>
</project>
""".stripIndent()


if (!Jenkins.instance.getItem(jobName)) {
    def xmlStream = new ByteArrayInputStream( configXml.getBytes() )
    try {
        def seedJob = Jenkins.instance.createProjectFromXML(jobName, xmlStream)
        seedJob.scheduleBuild(0, null)
    } catch (ex) {
        println "ERROR: ${ex}"
        println configXml.stripIndent()
    }
}