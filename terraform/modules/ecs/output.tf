output "ecs_cluster_id" {
  description = "ID del cluster ECS"
  value       = aws_ecs_cluster.franquicias_cluster.id
}

output "ecs_service_name" {
  description = "Nombre del servicio ECS"
  value       = aws_ecs_service.franquicias_service.name
}