resource "aws_vpc" "franquicias_vpc" {
  cidr_block = "10.0.0.0/16"
}

resource "aws_subnet" "public_subnets" {
  count                   = 2
  vpc_id                  = aws_vpc.franquicias_vpc.id
  cidr_block              = cidrsubnet(aws_vpc.franquicias_vpc.cidr_block, 8, count.index)
  map_public_ip_on_launch = true
}

resource "aws_security_group" "ecs_security_group" {
  vpc_id = aws_vpc.franquicias_vpc.id

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}