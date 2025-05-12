resource "aws_s3_bucket" "demo" {
  bucket = "bucket-s3-terra-demo"

  tags = {
    Environment = "Dev"
    Project     = "Demo para Franquicias"
  }
}

resource "aws_dynamodb_table" "terraform_locks" {
  name         = "terraform-locks"
  billing_mode = "PAY_PER_REQUEST"
  hash_key     = "LockID"

  attribute {
    name = "LockID"
    type = "S"
  }
}