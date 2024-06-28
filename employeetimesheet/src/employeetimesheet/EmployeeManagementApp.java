package employeetimesheet;

import java.sql.*;
import java.lang.*;
import java.util.*;

public class EmployeeManagementApp{
    // Replace with your actual database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/timesheet";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "harishm@052003";

    public static void main(String[] args)  {
    	try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Connected to the database!");
            
            Scanner scanner = new Scanner(System.in);

            // Get user input for employee data
            System.out.print("Enter employee ID: ");
            int emp_ID = scanner.nextInt();

            System.out.print("Enter first name: ");
            String emp_firstname = scanner.next();

            System.out.print("Enter last name: ");
            String emp_lastname = scanner.next();
            
            System.out.print("Enter In-time: ");
            String emp_intime = scanner.next();
            
            System.out.print("Enter Out-time: ");
            String emp_outtime = scanner.next();
            
            System.out.print("Enter project-name: ");
            String project_name = scanner.next();
            

            // Call methods for performing operations
            createEmployee(conn, emp_ID, emp_firstname, emp_lastname,  emp_intime, emp_outtime, project_name);
            Employee employee = getEmployee(conn, 1);
            if (employee != null) {
                System.out.println("Employee: " + employee.getEmp_firstname() + " " + employee.getEmp_lastname());
            }


            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Employee class to represent employee data
    static class Employee {
        private int emp_ID;
        private String emp_firstname;
        private String emp_lastname;
        
        public String getEmp_firstname() {
            return emp_firstname;
        }

        public void setEmp_firstname(String emp_firstname) {
            this.emp_firstname = emp_firstname;
        }

        public String getEmp_lastname() {
            return emp_lastname;
        }

        public void setEmp_lastname(String emp_lastname) {
            this.emp_lastname = emp_lastname;
        }
        public void setEmp_ID(int emp_ID) {
        	this.emp_ID = emp_ID;
        }
    }

    private static void createEmployee(Connection conn, int emp_ID, String emp_firstname, String emp_lastname,
                                       String emp_intime,String emp_outtime, String project_name) throws SQLException {
        String sql = "INSERT INTO employee (emp_ID, emp_firstname, emp_lastname, emp_intime, emp_outtime, project_name) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, emp_ID);
        statement.setString(2, emp_firstname);
        statement.setString(3, emp_lastname);
        statement.setString(4, emp_intime);
        statement.setString(5, emp_outtime);
        statement.setString(6, project_name);

        statement.executeUpdate();
        System.out.println("Employee created successfully!");
    }

    private static Employee getEmployee(Connection conn, int emp_ID) throws SQLException {
        String sql = "SELECT * FROM employee WHERE emp_ID = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, emp_ID);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Employee employee = new Employee();
            employee.setEmp_ID(resultSet.getInt("emp_ID"));
            employee.setEmp_firstname(resultSet.getString("emp_firstname"));
            employee.setEmp_lastname(resultSet.getString("emp_lastname"));
            // Set other properties if needed

            return employee;
        }
        return null;
    }


}