

resource "google_compute_network" "default" {
  name                    = "test"
  auto_create_subnetworks = "true"
}

resource "google_compute_subnetwork" "dmz" {
  name          = "internal-dmz"
  ip_cidr_range = "${var.dmz_cidr}"
  network       = "${google_compute_network.default.self_link}"
  region        = "${var.region}"
}

