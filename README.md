<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@latest/tabler-icons.min.css">

# <i class="ti ti-route"></i> Prison Solver Service
Esta aplicación encuentra la ruta de salida dada una configuración de un laberinto (`Prisión`), posición inicial del sujeto (`Prisionero`) y una ubicación final de destino (`Salida`)

### <i class="ti ti-abc"></i> Terminologías
El servicio expone el endpoint `/prisoner` que su función consiste en un determinar una ruta de escape dada la configuración de una Prisión (laberinto). La configuración de la prisión se especifica en el Body del Request mediante un listado arreglo de `String`. Para dicha configuración solamente se permiten como válidos los caracteres que se listan a continuación junto a su significado:

| **Caracter**            | **Representa**                            |
|-------------------------|-------------------------------------------|
| `P`                     | Prisionero                                |
| `S`                     | Salida                                    |
| `&#124;`                | Muro                                       |
| Espacio en Blanco (`' '`) | Camino                                    |
| `>`                     | Guardia que está viendo hacia la derecha  |
| `<`                     | Guardia que está viendo hacia la izquierda |
| `v`                     | Guardia que está viendo hacia abajo       |
| `^`                     | Guardia que está viendo hacia Arriba      |


### <i class="bi bi-signpost-2"></i> Reglas Implementadas
En la solución se implementó una estrategia de `Invocación de Reglas en Cadena`. A continuación se enumeran las reglas o restricciones que implementa el servicio:

* Solamente puede existir un **Salida** (`S`)
* La **Salida** solo puede estar ubicada en el contorno de la Prisión
* El contorno de la Prisión solamente puede estar formada por **Muros**, a excepción de un espacio que corresponde a la ubicación de la **Salida**
* Solamente puede existir un **Prisionero** (`P`)
* El **Prisionero** sólo puede desplazarse en las siguientes direcciones: arriba, abajo, derecha e izquierda
* El **Prisionero** sólo puede desplazarse por espacios en blanco, **Camino** (`' '`)
* Existen **Guardias**, por lo cual el **Prisionero** no podrá pasar por un camino si el guardia tiene visibilidad. No obstante, la visibilidad puede ser bloqueada por una pared
* La **Prisión** solamente admite caracteres válidos que se detallan en la tabla anterior, es decir: `|, ^, <, >, v, P, S y espacios en blanco`
* Se valida que el parámetro que recibe la configuración de la **Prisión** no sea `NULL`
* Se valida que el arreglo de `String` no contenga ninguna fila en `NULL` o vacía
* La **Prisión** debe tener al menos 6 filas y un máximo de 30 filas
* Cada fila de la configuración de la **Prisión** debe tener al menos 6 columnas y un máximo de 30. Todas las filas deben tener el mismo tamaño

# <i class="ti ti-unlink"></i> Operaciones Disponibles
### <i class="ti ti-wall"></i> Endpoint: Existe una Ruta de Escape
En este endpoint determina si existe una `Ruta de Escape` conforme a la configuración dada de una **Prisión** (y su **Salida**), posición del **Prisionero** y **Guardias**.

A continuación se puestra un ejemplo para consumir el endpoint por medio de la utilidad de líneas de comando `curl`:

```bash
  curl -v --location 'https://prison-solver-service-rrodriguez.azurewebsites.net/api/v1/prisoner' --header 'Content-Type: application/json' --data '{"prison":["|||||||||","| ||   P|","||    | |","|v| | < |","|   <   |","| ^     |","||||S||||"]}'
```

El endpoint una vez que sirve la respuesta, retorna en la información de los Headers (Header Location) la URL para obtener la información que procesó (Prisión, Solución y el flag que indica si tiene o no posilidad de escapar). Del ejemplo anterior, esta sería la respuesta completa:

```bash
  curl -v --location 'https://prison-solver-service-rrodriguez.azurewebsites.net/api/v1/prisoner' --header 'Content-Type: application/json' --data '{"prison":["|||||||||","| ||   P|","||    | |","|v| | < |","|   <   |","| ^     |","||||S||||"]}'
*   Trying 20.119.0.42:443...
* Connected to prison-solver-service-rrodriguez.azurewebsites.net (20.119.0.42) port 443 (#0)
* ALPN: offers h2,http/1.1
* (304) (OUT), TLS handshake, Client hello (1):
*  CAfile: /etc/ssl/cert.pem
*  CApath: none
* (304) (IN), TLS handshake, Server hello (2):
* TLSv1.2 (IN), TLS handshake, Certificate (11):
* TLSv1.2 (IN), TLS handshake, Server key exchange (12):
* TLSv1.2 (IN), TLS handshake, Server finished (14):
* TLSv1.2 (OUT), TLS handshake, Client key exchange (16):
* TLSv1.2 (OUT), TLS change cipher, Change cipher spec (1):
* TLSv1.2 (OUT), TLS handshake, Finished (20):
* TLSv1.2 (IN), TLS change cipher, Change cipher spec (1):
* TLSv1.2 (IN), TLS handshake, Finished (20):
* SSL connection using TLSv1.2 / ECDHE-RSA-AES256-GCM-SHA384
* ALPN: server accepted http/1.1
* Server certificate:
*  subject: C=US; ST=WA; L=Redmond; O=Microsoft Corporation; CN=*.azurewebsites.net
*  start date: Mar 10 03:05:55 2023 GMT
*  expire date: Mar  4 03:05:55 2024 GMT
*  subjectAltName: host "prison-solver-service-rrodriguez.azurewebsites.net" matched cert's "*.azurewebsites.net"
*  issuer: C=US; O=Microsoft Corporation; CN=Microsoft Azure TLS Issuing CA 02
*  SSL certificate verify ok.
* using HTTP/1.1
> POST /api/v1/prisoner HTTP/1.1
> Host: prison-solver-service-rrodriguez.azurewebsites.net
> User-Agent: curl/7.88.1
> Accept: */*
> Content-Type: application/json
> Content-Length: 96
>
< HTTP/1.1 200 OK
< Content-Length: 0
< Date: Wed, 19 Jul 2023 00:53:31 GMT
< **Location:** https://prison-solver-service-rrodriguez.azurewebsites.net/api/v1/prisoner/6c1ebe77-7fe8-4b5d-bf55-b7ee35aee22c
<
* Connection #0 to host prison-solver-service-rrodriguez.azurewebsites.net left intact
```
> <i class="ti ti-external-link"></i> **Location:** [https://prison-solver-service-rrodriguez.azurewebsites.net/api/v1/prisoner/6c1ebe77-7fe8-4b5d-bf55-b7ee35aee22c](https://prison-solver-service-rrodriguez.azurewebsites.net/api/v1/prisoner/6c1ebe77-7fe8-4b5d-bf55-b7ee35aee22c)

### <i class="ti ti-list-search"></i> Endpoint: Búsqueda de solicitud por su Identificador
Este endpoint tiene la finaldad de recuperar los datos de solicitud que se envían con el endpoint anterior.

A continuación se puestra un ejemplo para consumir el endpoint por medio de la utilidad de líneas de comando `curl`:

```bash
  curl -v --location https://prison-solver-service-rrodriguez.azurewebsites.net/api/v1/prisoner/6c1ebe77-7fe8-4b5d-bf55-b7ee35aee22c --header 'Content-Type: application/json'
```

Este sería resultado (Body del Response) de la ejecución anterior:
```json
{
    "canEscape": true,
    "challenge": [
        "|||||||||",
        "| ||   P|",
        "||    | |",
        "|v| | < |",
        "|   <   |",
        "| ^     |",
        "||||S||||"
    ],
    "solution": [
        "|||||||||",
        "| ||   P|",
        "||    |*|",
        "|v| |<<*|",
        "|v<<<***|",
        "|v^ ****|",
        "||||S||||"
    ]
}
```

### <i class="ti ti-chart-histogram"></i> Endpoint: Estadísticas
La finalidad de este endpoint es retorna un detalle de los conteos de la cantidad de peticiones en donde la configuración de la prisión  diera como resultado la posibilidad de un escape exitoso, así como las que no fueron exitosas y un cálculo de cuánto representan los escapes exitosos con respecto a los que no fueron exitosos.

A continuación se puestra un ejemplo para consumir el endpoint por medio de la utilidad de líneas de comando `curl`:

```bash
  curl -v --location 'https://prison-solver-service-rrodriguez.azurewebsites.net/api/v1/prisoner/stats' --header 'Content-Type: application/json'
```

Este sería resultado (Body del Response) de la ejecución anterior:
```json
{
    "count_successful_scape": 9,
    "count_unsuccessful_scape": 1,
    "ratio": 9.00
}
```


# <i class="bi bi-gear"></i> Tecnologías y Frameworks

- [JDK 17 - Eclipse Temurin](https://adoptium.net/temurin/releases/)
- [Spring Boot 3](https://spring.io/projects/spring-boot)
- [Jakarta EE 10](https://jakarta.ee/)
- [H2 Database Engine](https://www.h2database.com/html/main.html)
- [Hibernate 6](https://hibernate.org/)
- [Hibernate Validator - Bean Validation](https://hibernate.org/validator/)
- [Hypersistence](https://hypersistence.io/)
- [JUnit 5](https://junit.org/junit5/)
- [Mockito](https://site.mockito.org/)
- [Jacoco](https://www.eclemma.org/jacoco/)
- [Maven 3.9.1](https://maven.apache.org/)
- [Azure App Service](https://azure.microsoft.com/es-es/products/app-service)


# <i class="ti ti-brand-tabler"></i> Ejecución Local del Proyecto
### <i class="bi bi-bookmark-check"></i> Requerimientos
- [Git](https://git-scm.com/)
- [JDK 17 - Eclipse Temuring](https://adoptium.net/temurin/releases/) (Se puede utilizar cualquier otra distribución del JDK 17)
- [Maven](https://maven.apache.org/)

### <i class="ti ti-brand-github"></i> Habilitar localmente el Repositorio

Descargar el repositorio del proyecto con la siguiente instrucción:

```bash
  git clone https://github.com/rcrodriguez89/prison-solver-service.git
```

Ingresar al directorio del proyecto:

```bash
  cd prison-solver-service
```

### <i class="bi bi-play"></i> Iniciar la Aplicación

Ejecutar la aplicación con la configuración predeterminada:

```bash
  ./mvnw spring-boot:run
```

Con el comando anterior se establecen las siguientes configuraciones:

| Variable de Entorno    | Descripción                              | Valor por Defecto |
|------------------------|------------------------------------------|-------------------|
| API_VERSION            | Versión de la API                        | `v1`                |
| SUPER_USER_DB          | Username de la Base de Datos             | `sa`                |
| SUPER_USER_PASSWORD_DB | Password del usuario de la Base de Datos | `sa`                |

La siguiente instrucción de línea de comandos es un ejemplo para iniciar la aplicación modificando los valores predeterminados:

```bash
  ./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-DAPI_VERSION=v2 -DSUPER_USER_DB=admin -DSUPER_USER_PASSWORD_DB=secretpassword"
```
Nótese que todas las variables de entornos se pasan por medio de `-Dspring-boot.run.jvmArguments`. El formato de acuerdo a la [Documentación Oficial](https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/htmlsingle/#run.examples.system-properties) de Spring Boot debe ser `-Dspring-boot.run.jvmArguments=-Dvariable1=valor1 -Dvariable2=valor2` (las variables deben estar separadas por un espacio en blanco).

### <i class="ti ti-database-share"></i> Base de Datos en Memoria: H2
Para ingresar localmente a las tablas creadas por la aplicación en H2 se debe ingresar a la siguiente URL (asumiendo que se ejecutó la aplicación con los parámetros por defecto):

> <i class="ti ti-external-link"></i> [http://localhost:8080/api/v1/h2-console/login.jsp](http://localhost:8080/api/v1/h2-console/login.jsp)
> ![H2 Console](https://github.com/rcrodriguez89/prison-solver-service/assets/7888870/d7b63769-9c86-4c1e-aaf2-4c63e0ad46ac)

# <i class="ti ti-settings-check"></i> Pruebas Unitarias, Pruebas de Integración y Cobertura

Con la siguiente instrucción se compila el código (si es necesario) y se ejecutan las Pruebas Unitarias y de Integración:

```bash
  ./mvnw test
```

### <i class="bi bi-shield-check"></i> Cobertura
El goal `test` de Maven también genera el `Reporte de Cobertura` de Jacoco
```bash
  cd target/site/jacoco/index.html
```

> ![Coverage](https://user-images.githubusercontent.com/7888870/254346715-11dbd822-ae40-4fbd-bc5a-9be7295ae79a.png)
> <i class="ti ti-info-circle-filled"></i> La Cobertura actual del proyecto es del `100%`

# <i class="ti ti-brand-azure"></i> Proveedor Cloud: Azure
Se seleccionó Azure como proveedor por su facilidad de uso, amplia documentación y finalmente por la integración que posee con GitHub.
Para el despliegue de la aplicación se utiliza un servicio llamado [Azure App Service](https://azure.microsoft.com/es-es/products/app-service) que tiene un enfoque de compatabilidad de aplicaciones escritas en Java, especialmente desarrolladas con Spring Boot.

El servicio se encuentra desplegado de manera pública bajo el siguiente dominio:

> <i class="ti ti-external-link"></i> [https://prison-solver-service-rrodriguez.azurewebsites.net](https://prison-solver-service-rrodriguez.azurewebsites.net)


## <i class="ti ti-infinity"></i> Integración Continua / Entrega Continua (CI/CD)
Se utilizó [GitHub Actions](https://github.com/features/actions) como servicio SaaS de CI/CD. La configuración actual consiste en un pipeline que inicia su ejecución automáticamente cuando ocurre un evento de `push` hacia el branch `main` del repositorio. En cuanto los stages que contiene el pipeline, básicamente son dos:

- **Build:** Compila y empaqueta el código fuente del repositorio conforme al último commit en el branch `main`
- **Deploy:** Realiza el despliegue en el recurso creado ([App Service](https://azure.microsoft.com/es-es/products/app-service)) en Azure para esta aplicación

> ![CI/CD](https://github-production-user-asset-6210df.s3.amazonaws.com/7888870/254416986-6ec30ad8-06b2-41fc-8dd1-6bed48f4418c.png)

# <i class="ti ti-tool"></i> Recursos Disponibles
### <i class="ti ti-brand-pushover"></i> Colección Postman
La colección de Postman para consumir el entorno de Producción (aplicación desplegada en Azure) se encuentra publicada en el siguiente enlace:

> <i class="ti ti-external-link"></i> [https://documenter.getpostman.com/view/1483361/2s946iaqgA](https://documenter.getpostman.com/view/1483361/2s946iaqgA)

### <i class="bi bi-film"></i> Demo Postman
A continuación se comparte una demostración del consumo los endpoints del servicio mediante la colección de Postman:

![Demo Postman](https://user-images.githubusercontent.com/7888870/254441386-e910a5b0-9f67-4c2a-8d4d-c33e095784ab.gif)