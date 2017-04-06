provider "google" {
  credentials = "${file("~/.gcloud/account.json")}"
  project     = "turnkey-wording-163610"
  region      = "europe-west1"
}
