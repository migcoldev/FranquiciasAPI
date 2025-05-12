variable "vpc_id" {
  description = "ID de la VPC"
  type        = string
}

variable "subnets" {
  description = "Lista de subnets públicas"
  type        = list(string)
}

variable "log_group_name" {
  description = "Nombre del grupo de logs"
  type        = string
}