
provider "google" {
  credentials = "${file("~/.gcloud/account.json")}"
  project     = "turnkey-wording-163610"
  region      = "europe-west1-b"
}

// Create a new instance
resource "google_compute_instance" "default" {

    name         = "test"
    machine_type = "n1-standard-1"
    zone         = "europe-west1-b"

    tags = ["foo", "bar"]

    disk {
      image = "debian-cloud/debian-8"
    }

    // Local SSD disk
    disk {
      type    = "local-ssd"
      scratch = true
    }

    network_interface {
      network = "default"

      access_config {
        // Ephemeral IP
      }
    }

}