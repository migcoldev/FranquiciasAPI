provider "aws" {
  region  = "us-east-1"
  profile = "default"
}

### TABLAS DYNAMODB ###
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
}
### TABLAS DYNAMODB ###

### ECR ###
resource "aws_ecr_repository" "franquicias_repo" {
  name = "franquicias-api"
}

### ECR Cluster ###
resource "aws_ecs_cluster" "franquicias_cluster" {
  name = "franquicias-cluster"
}

### ROLES IAM ###
resource "aws_iam_role" "ecs_task_execution_role" {
  name = "ecsTaskExecutionRole"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = "sts:AssumeRole"
        Effect = "Allow"
        Principal = {
          Service = "ecs-tasks.amazonaws.com"
        }
      }
    ]
  })
}

resource "aws_iam_role_policy_attachment" "ecs_task_execution_policy" {
  role       = aws_iam_role.ecs_task_execution_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

### ECS TASK ###
resource "aws_ecs_task_definition" "franquicias_task" {
  family                   = "franquicias-task"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu                      = "256"
  memory                   = "512"

  execution_role_arn = aws_iam_role.ecs_task_execution_role.arn

  container_definitions = jsonencode([
    {
      name      = "franquicias-api"
      image     = "${aws_ecr_repository.franquicias_repo.repository_url}:latest"
      cpu       = 256
      memory    = 512
      essential = true
      portMappings = [
        {
          containerPort = 8080
          hostPort      = 8080
          protocol      = "tcp"
        }
      ]
    }
  ])
}

### ECS SERVICE ###
resource "aws_ecs_service" "franquicias_service" {
  name            = "franquicias-service"
  cluster         = aws_ecs_cluster.franquicias_cluster.id
  task_definition = aws_ecs_task_definition.franquicias_task.arn
  launch_type     = "FARGATE"

  network_configuration {
    subnets         = aws_subnet.public_subnets[*].id
    security_groups = [aws_security_group.ecs_security_group.id]
    assign_public_ip = true
  }

  desired_count = 1
}

### VPC ###
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