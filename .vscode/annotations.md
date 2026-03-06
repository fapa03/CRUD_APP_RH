# DATABASE PROPERTIES>
database_name: empleados_db
username: postgres
password: Patopato1!

# DIRECT QUERY TO CHECK DATABASE>

$env:PATH += ";C:\Program Files\PostgreSQL\18\bin"
psql -U postgres -d empleados_db
[x] SELECT * FROM empleados;
SELECT id, nombre, apellido, email, activo FROM empleados;


