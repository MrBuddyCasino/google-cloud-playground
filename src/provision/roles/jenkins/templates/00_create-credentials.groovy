import jenkins.*
import hudson.*
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.common.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.jenkins.plugins.sshcredentials.impl.*
import org.jenkinsci.plugins.plaincredentials.impl.FileCredentialsImpl
import hudson.plugins.sshslaves.*;
import hudson.model.*
import jenkins.model.*
import hudson.security.*

def global_domain = Domain.global()
def credentials_store =
        Jenkins.instance.getExtensionList(
                'com.cloudbees.plugins.credentials.SystemCredentialsProvider'
        )[0].getStore()

def basicSSHCredentials = new BasicSSHUserPrivateKey(
        CredentialsScope.GLOBAL,
        "jenkins_privatekey_authentication",
        "jenkins",
        new BasicSSHUserPrivateKey.UsersPrivateKeySource(),
        "",
        "")

//if use base64 decoding by ansible groovy file will not work proper
def googleCredentialsFileContentDevelopment = "{{ google_credentials_development }}".decodeBase64()
def googleAccountJsonCredentialsDevelopment = new FileCredentialsImpl(
        CredentialsScope.GLOBAL,
        "google_cloud_credentials_file_development",
        "Google Account JSON File For Development",
        "account_development.json",
        SecretBytes.fromBytes(googleCredentialsFileContentDevelopment))

//if use base64 decoding by ansible groovy file will not work proper
def googleCredentialsFileContentConsolidation = "{{ google_credentials_consolidation }}".decodeBase64()
def googleAccountJsonCredentialsConsolidation = new FileCredentialsImpl(
        CredentialsScope.GLOBAL,
        "google_cloud_credentials_file_consolidation",
        "Google Account JSON File For Consolidation",
        "account_consolidation.json",
        SecretBytes.fromBytes(googleCredentialsFileContentConsolidation))

def googleCredentialsFileContentProduction = "{{ google_credentials_production }}".decodeBase64()
def googleAccountJsonCredentialsProduction = new FileCredentialsImpl(
        CredentialsScope.GLOBAL,
        "google_cloud_credentials_file_production",
        "Google Account JSON File For Production",
        "account_production.json",
        SecretBytes.fromBytes(googleCredentialsFileContentProduction))

def githubReadKeyContent= "{{ github_read_key }}".decodeBase64()
def gitRepositoryReadCredentials = new FileCredentialsImpl(
        CredentialsScope.GLOBAL,
        "git_infrastructure_project_access_key",
        "Access the Infrastructure Project (Read Only)",
        "id_rsa",
        SecretBytes.fromBytes(githubReadKeyContent))

def vaultMasterPasswordFileContent= "{{ vault_master_password }}".decodeBase64()
def vaultMasterPasswordCredentials = new FileCredentialsImpl(
        CredentialsScope.GLOBAL,
        "vault_master_password_file",
        "Vault master password file",
        "vault_pass",
        SecretBytes.fromBytes(vaultMasterPasswordFileContent))

// vault password has to be stored as base64 encoded string, because of terraform shell scripts which can not handle dollar signs
def vaultMasterPasswordFileContentBase64= "{{ vault_master_password }}".bytes
def vaultMasterPasswordCredentialsBase64 = new FileCredentialsImpl(
        CredentialsScope.GLOBAL,
        "vault_master_password_file_base64",
        "Vault master password file base64",
        "vault_pass_base64",
        SecretBytes.fromBytes(vaultMasterPasswordFileContentBase64))

credentials_store.addCredentials(global_domain, basicSSHCredentials)
credentials_store.addCredentials(global_domain, googleAccountJsonCredentialsDevelopment)
credentials_store.addCredentials(global_domain, googleAccountJsonCredentialsConsolidation)
credentials_store.addCredentials(global_domain, googleAccountJsonCredentialsProduction)
credentials_store.addCredentials(global_domain, gitRepositoryReadCredentials)
credentials_store.addCredentials(global_domain, vaultMasterPasswordCredentials)
credentials_store.addCredentials(global_domain, vaultMasterPasswordCredentialsBase64)
