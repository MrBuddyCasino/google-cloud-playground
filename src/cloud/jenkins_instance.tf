resource "google_compute_instance" "jenkins" {
  name         = "jenkins"
  machine_type = "${var.jenkins_machine_type}"
  zone         = "${var.region}-b"
  count        = "${var.jenkins_count}"

  disk {
    image = "${var.disk_image}"
  }

  disk {
    disk = "${google_compute_disk.jenkins_workspace.name}"
  }

  disk {
    disk = "${google_compute_disk.jenkins_docker.name}"
  }

  network_interface {
    subnetwork = "${google_compute_subnetwork.europe-west1-dmz.name}"
    access_config {
      // Ephemeral IP
    }
  }

  metadata_startup_script = "${data.template_file.jenkins_metadata_startup_script.rendered}"

}

data "template_file" "jenkins_metadata_startup_script" {
  template = "${file("${path.module}/metadata_startup_script.sh")}"

  vars {
    git_private_key             = "${file("~/.ssh/github_rsa")}"
    vault_pass                  = "12345678"
    playbook                    = "buildserver.yml"
    additional_parameters       = "--extra-vars \"@/var/conrad/src/provision/extra_vars/jenkins.yml\""
  }
}

resource "google_compute_disk" "jenkins_workspace" {
  name  = "jenkins-workspace-disk"
  type  = "pd-ssd"
  zone  = "${var.region}-b"
  size  = 100
  count = "${var.jenkins_count}"
}

resource "google_compute_disk" "jenkins_docker" {
  name  = "jenkins-docker-disk"
  type  = "pd-ssd"
  zone  = "${var.region}-b"
  size  = 100
  count = "${var.jenkins_count}"
}
