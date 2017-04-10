#!/bin/bash
export LANGUAGE=en_US.UTF-8
export LANG=en_US.UTF-8
export LC_ALL=en_US.UTF-8
locale-gen en_US en_US.UTF-8
echo "${file("~/.ssh/github_rsa")}" &> /root/git_id_rsa
#echo "${vault_pass}" &> /root/vault_pass.b64
#openssl base64 -d -in /root/vault_pass.b64 -out /root/vault_pass
echo "${vault_pass}" &> /root/vault_pass

chmod 400 /root/git_id_rsa
apt-get -y update
apt-get -y upgrade
apt-get install -y git software-properties-common language-pack-en
apt-add-repository -y ppa:ansible/ansible
apt-get -y update
apt-get -y install python2.7 python-pip libssl-dev libffi-dev
pip install ansible==2.2.0
mkdir -p /var/conrad/


eval $(ssh-agent -s)
ssh-add /root/git_id_rsa
echo -e "Host github.com\n\tStrictHostKeyChecking no\n" &> ~/.ssh/config
# public key that is known to github
#git clone git@github.com:codecentric/conrad-infrastructure /var/conrad/
git clone git@github.com:MrBuddyCasino/google-cloud-playground /var/conrad/
cd /var/conrad/src/provision/
/usr/bin/flock -w 0 /var/conrad/provisioning.lock ansible-playbook -i "localhost" --user=root -c local "${playbook}" --vault-password-file /root/vault_pass ${additional_parameters}
apt-get -y upgrade

