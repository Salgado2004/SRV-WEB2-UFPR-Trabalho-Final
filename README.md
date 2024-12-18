<h1 align="center" style="display: flex;">
  Project ManuTADS
  <img src="src/main/resources/static/images/logo.svg" width="40">
</h1>

<div align="center"">
  <img src="https://img.shields.io/badge/Angular-e43139?style=for-the-badge&logo=angular&logoColor=ffffff">
  <img src="https://img.shields.io/badge/Typescript-007bcc?style=for-the-badge&logo=typescript&logoColor=ffffff">
  <img src="https://img.shields.io/badge/Tailwind-39bcf9?style=for-the-badge&logo=tailwindcss&logoColor=ffffff">
  <img src="https://img.shields.io/badge/Spring-60b831?style=for-the-badge&logo=spring&logoColor=ffffff">
  <img src="https://img.shields.io/badge/Java-e76f00?style=for-the-badge&logo=coffeescript&logoColor=ffffff">
  <!--<img src="https://img.shields.io/badge/Postman-ffffff?style=for-the-badge&logo=postman&logoColor=ffffff">-->
  <img src="https://img.shields.io/badge/Postgre_sql-346892?style=for-the-badge&logo=postgresql&logoColor=ffffff">
</div></br>

<p align="center">
<b>ManuTADS</b> is a web system developed as the final project for the <b>Web Development II</b> course in the undergraduate program in Systems Analysis and Development at UFPR. It is a website for a fictitious equipment maintenance company, <b>ManuTADS</b>. Through this site, customers can request their mechanical repairs, while professionals can view the demands and follow the complete workflow provided by the system.
</p>

<p align="center">
     <a href="https://github.com/Salgado2004/FED-WEB2-UFPR-Trabalho-Final" target="_blank">Visit the frontend repository</a>
</p>

<h2>💻 Technologies and Tools</h2>

- Java
- Spring Boot
- JWT (JSON Web Tokens)
- JDBC (Java Database Connectivity)
- Design Patterns (Singleton, Factory, DAO & Strategy)
- Postman
- Postgre SQL

## 🚀 API Endpoints

Base URL: `/service/v1`

| **Route**                                | **Description**                                               |
|------------------------------------------|---------------------------------------------------------------|
| **<img height=20 src="https://img.shields.io/badge//auth/register-151b23?style=flat&label=GET&labelColor=60b831">**        | Registers a new user in the system.                           |
| **<img height=20 src="https://img.shields.io/badge//auth/login-151b23?style=flat&label=POST&labelColor=007bcc">**        | Logs in an existing user by validating credentials.              |
| **<img height=20 src="https://img.shields.io/badge//employee-151b23?style=flat&label=GET&labelColor=60b831">**        | Retrieves a list of all employees.                           |
| **<img height=20 src="https://img.shields.io/badge//employee-151b23?style=flat&label=POST&labelColor=007bcc">**        | Creates a new employee record.                           
| **<img height=20 src="https://img.shields.io/badge//employee/{userId}-151b23?style=flat&label=PUT&labelColor=e76f00">**        | Updates the employee details for the specified `userId`.   |
| **<img height=20 src="https://img.shields.io/badge//employee/{userId}-151b23?style=flat&label=DELETE&labelColor=e43139">**        | Deactivates the employee record with the given `userId`.|
| **<img height=20 src="https://img.shields.io/badge//equipment--category-151b23?style=flat&label=GET&labelColor=60b831">**        | Retrieves a list of all equipment categories.           |
| **<img height=20 src="https://img.shields.io/badge//equipment--category-151b23?style=flat&label=POST&labelColor=007bcc">**        | Creates a new equipment category.                       |
| **<img height=20 src="https://img.shields.io/badge//equipment--category-151b23?style=flat&label=PUT&labelColor=e76f00">**        | Updates the details of a specific equipment category.   |
| **<img height=20 src="https://img.shields.io/badge//equipment--category/{id}-151b23?style=flat&label=DELETE&labelColor=e43139">**       | Deactivates a specific equipment category by `id`.|
| **<img height=20 src="https://img.shields.io/badge//receipt-151b23?style=flat&label=GET&labelColor=60b831">**        | Retrieves a list of receipts.|
| **<img height=20 src="https://img.shields.io/badge//requests-151b23?style=flat&label=GET&labelColor=60b831">**        | Retrieves a list of all requests.                          |
| **<img height=20 src="https://img.shields.io/badge//requests/{id}-151b23?style=flat&label=GET&labelColor=60b831">**        | Retrieves details of a specific request by `id`.              |
| **<img height=20 src="https://img.shields.io/badge//requests/report-151b23?style=flat&label=GET&labelColor=60b831">**        | Generates a report of all requests.                    |
| **<img height=20 src="https://img.shields.io/badge//requests/report/category-151b23?style=flat&label=GET&labelColor=60b831">**        | Generates a report of requests, grouped by category.|
| **<img height=20 src="https://img.shields.io/badge//requests-151b23?style=flat&label=POST&labelColor=007bcc">**        | Creates a new request (e.g., service request, support request).  |
| **<img height=20 src="https://img.shields.io/badge//requests/{id}-151b23?style=flat&label=PUT&labelColor=e76f00">**        | Updates the specified request by `id`.   |

<h2>🤝 Collaborators</h2>
<!-- contributors -->
<table>
  <tr>
    <td align="center">
      <a href="https://github.com/AlissonGSantos" target="_blank">
        <img src="https://avatars.githubusercontent.com/u/180354000?v=4" width="100px;" alt="Alisson Gabriel Santos"/><br>
          Alisson Gabriel
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Gabriel-Troni" target="_blank">
        <img src="https://avatars.githubusercontent.com/u/104802740?s=96&v=4" width="100px;" alt="Gabriel Troni"/><br>
          Gabriel Troni
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Salgado2004" target="_blank">
        <img src="https://avatars.githubusercontent.com/u/53799801?v=4" width="100px;" alt="Leonardo Felipe Salgado"/><br>
          Leo Salgado
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/matbaaz" target="_blank">
        <img src="https://avatars.githubusercontent.com/u/28157917?v=4" width="100px;" alt="Mateus Bazan Bespalhok"/><br>
          Mateus Bazan
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Pedro-H108" target="_blank">
        <img src="https://avatars.githubusercontent.com/u/124636773?v=4" width="100px;" alt="Pedro Henrique de Souza"/><br>
        Pedro Henrique
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/raulbana" target="_blank">
        <img src="https://avatars.githubusercontent.com/u/99099003?v=4" width="100px;" alt="Raul Ferreira Costa Bana"/><br>
         Raul Bana 
      </a>
   </td>
  </tr>
</table>
<!-- /contributors -->
Special thanks to Professor Dr. Razer A. N. R. Montaño