resource "aws_cloudwatch_log_group" "franquicias_log_group" {
  name              = "/ecs/franquicias"
  retention_in_days = 7
}