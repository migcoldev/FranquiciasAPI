resource "aws_lb" "franquicias_alb" {
  name               = "franquicias-alb"
  internal           = false
  load_balancer_type = "application"
  security_groups    = [var.vpc_id]
  subnets            = var.subnets
}

resource "aws_lb_target_group" "franquicias_tg" {
  name     = "franquicias-tg"
  port     = 8080
  protocol = "HTTP"
  vpc_id   = var.vpc_id

  health_check {
    path                = "/"
    interval            = 30
    timeout             = 5
    healthy_threshold   = 2
    unhealthy_threshold = 2
    matcher             = "200"
  }
}

resource "aws_ecr_repository" "franquicias_repo" {
  name = "franquicias-api"
}

resource "aws_ecs_cluster" "franquicias_cluster" {
  name = "franquicias-cluster"
}

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
      logConfiguration = {
        logDriver = "awslogs"
        options = {
          awslogs-group         = var.log_group_name
          awslogs-region        = "us-east-1"
          awslogs-stream-prefix = "franquicias"
        }
      }
    }
  ])
}

resource "aws_ecs_service" "franquicias_service" {
  name            = "franquicias-service"
  cluster         = aws_ecs_cluster.franquicias_cluster.id
  task_definition = aws_ecs_task_definition.franquicias_task.arn
  launch_type     = "FARGATE"

  network_configuration {
    subnets         = var.subnets
    security_groups = [var.vpc_id]
    assign_public_ip = true
  }

  desired_count = 1

  load_balancer {
    target_group_arn = aws_lb_target_group.franquicias_tg.arn
    container_name   = "franquicias-api"
    container_port   = 8080
  }
}