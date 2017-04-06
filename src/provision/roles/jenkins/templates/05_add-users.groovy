import static java.util.UUID.randomUUID
import jenkins.model.*
import hudson.security.*

def jenkins = Jenkins.getInstance()
def hudsonRealm = new HudsonPrivateSecurityRealm(false)
def initialAdminPasswordFile = null

def strategy = new GlobalMatrixAuthorizationStrategy()
//{% for user in users %}


initialAdminPasswordFile = new File("/var/lib/jenkins/secrets/initialAdminPassword-{{ user.name }}")
if (!initialAdminPasswordFile.exists()){
    def password = randomUUID() as String
    password = password.replaceAll("-", "")
    def user = hudsonRealm.createAccount("{{ user.name }}", password)
    try {
        user.save()

        initialAdminPasswordFile.createNewFile()
        initialAdminPasswordFile << password

        println("User {{ user.name }} was created")
    } catch(Exception e) { println("Error: $e") }
}

strategy.add(Jenkins.ADMINISTER, "{{ user.name }}")
//{% endfor %}

jenkins.setAuthorizationStrategy(strategy)
jenkins.setSecurityRealm(hudsonRealm)
jenkins.save()
