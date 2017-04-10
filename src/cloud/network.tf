

resource "google_compute_network" "europe-west1" {
  name                    = "europe-west1"
  auto_create_subnetworks = "false"
}


resource "google_compute_subnetwork" "europe-west1-dmz" {
  name          = "europe-west1-dmz"
  ip_cidr_range = "${var.dmz_cidr}"
  network       = "${google_compute_network.europe-west1.self_link}"
  region        = "${var.region}"
}

