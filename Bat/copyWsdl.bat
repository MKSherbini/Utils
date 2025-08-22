set prefix=%1

curl -o wsdl-%prefix%.xml http://localhost:9090/SOAP-D4/ws/EmployeeService?wsdl
curl -o wsdl-%prefix%.xsd http://localhost:9090/SOAP-D4/ws/EmployeeService?xsd=1
curl -o wsdl-%prefix%1.xml http://localhost:9090/SOAP-D4/ws/EmployeeService?wsdl=1