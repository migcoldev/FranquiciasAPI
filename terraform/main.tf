provider "aws" {
  region  = "us-east-1"
  profile = "default"
}

module "networking" {
  source = "./modules/networking"
}

module "database" {
  source = "./modules/database"
}

module "logs" {
  source = "./modules/logs"
}

module "ecs" {
  source         = "./modules/ecs"
  vpc_id         = module.networking.vpc_id
  subnets        = module.networking.public_subnets
  log_group_name = module.logs.log_group_name
}

terraform {
  backend "s3" {
    bucket         = "bucket-s3-terra-demo"
    key            = "ruta/al/estado/terraform.tfstate"
    region         = "us-east-1"
    dynamodb_table = "terraform-locks"
    encrypt        = true
  }
}