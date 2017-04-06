variable "project_id" {
  description = "project id"
  default = "turnkey-wording-163610"
}

variable "region" {
  description = "Region to launch configuration in"
  default = "europe-west1"
}

variable "jenkins_count" {
  description = "Count for Jenkins (0=do not create, 1=create Jenkins"
  default = 1
}

variable "git_private_key" {
  description = "The key to use for ssh access to the git repository"
  default ="/project/id_rsa"
}

variable "dmz_cidr" {
  description = "Cidr for dmz"
  default = "10.200.0.0/23"
}

variable "jenkins_machine_type" {
  description = "Machine Type for Jenkins"
  default = "n1-standard-4"
}

variable "proxy_machine_type" {
  description = "Machine Type for Proxy"
  default = "n1-standard-1"
}

variable "disk_image" {
  description = "Default disk image"
  default = "ubuntu-os-cloud/ubuntu-1604-lts"
}

