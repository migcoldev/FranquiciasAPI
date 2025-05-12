output "vpc_id" {
  value = aws_vpc.franquicias_vpc.id
}

output "public_subnets" {
  value = aws_subnet.public_subnets[*].id
}