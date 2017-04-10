
resource "google_compute_firewall" "dmz" {
  name    = "dmz"
  network = "${google_compute_network.europe-west1.name}"

  allow {
    protocol = "icmp"
  }

  allow {
    protocol = "tcp"
    ports    = ["22", "80", "8080", "443", "8443"]
  }

  source_ranges = ["0.0.0.0/0"]
}