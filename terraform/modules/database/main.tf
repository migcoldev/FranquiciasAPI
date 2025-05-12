resource "aws_dynamodb_table" "franquicias" {
  name         = "franquicias"
  billing_mode = "PAY_PER_REQUEST"
  hash_key     = "id"

  attribute {
    name = "id"
    type = "S"
  }
}

resource "aws_dynamodb_table" "productos" {
  name         = "productos"
  billing_mode = "PAY_PER_REQUEST"
  hash_key     = "id"

  attribute {
    name = "id"
    type = "S"
  }

  attribute {
    name = "sucursalId"
    type = "S"
  }

  global_secondary_index {
    name            = "sucursalId-index"
    hash_key        = "sucursalId"
    projection_type = "ALL"
  }
}

resource "aws_dynamodb_table" "sucursales" {
  name         = "sucursales"
  billing_mode = "PAY_PER_REQUEST"
  hash_key     = "id"

  attribute {
    name = "id"
    type = "S"
  }

  attribute {
    name = "franquiciaId"
    type = "S"
  }

  global_secondary_index {
    name            = "franquiciaId-index"
    hash_key        = "franquiciaId"
    projection_type = "ALL"
  }

  attribute {
    name = "franquiciaId"
    type = "S"
  }
}