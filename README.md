# FranquiciasAPI
APIs en Java para Franquicias Demo
Java 17
Springboot

# Compilar
./gradle clean build
./gradle bootRun

# Docker
Archivo para levantar docker en Dockerfile en la raiz del proyecto

# Terraform
Avance de script Terraform para desplegar en AWS ECS en archivo terraform/main.tf

- Inicializar terraform : 
terraform init (si da error, comentar antes el backend dentro de main.tf y luego descomentarlo)

- Crear los resources para terraform backend
terraform apply -target=aws_s3_bucket.demo -target=aws_dynamodb_table.terraform_locks

- Ejecutar creación de terra scripts (de ser necesario ejecutar un apply antes)
terraform apply

