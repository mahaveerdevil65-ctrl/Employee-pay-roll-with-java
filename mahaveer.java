import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
//Employee Class Requirement: Default, Parameterized, Copy Constructors
class Employee 
{
private String id;
private String name;
private double basicSalary;
private String department;
// Default Constructor
public Employee()
 {
this.id = "";
this.name = "";
this.basicSalary = 0.0;
this.department = "";
}
// Parameterized Constructor
public Employee(
String id, 
String name, 
double basicSalary, 
String department
) 
{
this.id = id;
this.name = name;
this.basicSalary = basicSalary;
this.department = department;
}
 // Copy Constructor
public Employee(Employee other)
 {
this.id = other.id;
this.name = other.name;
this.basicSalary = other.basicSalary;
this.department = other.department;
}
// Getters and Setters
public String getId()
 {
return id; 
 }
public String getName()
 { 
return name; 
 }
 public double getBasicSalary()
 {
 return basicSalary;
 }
public String getDepartment()
 {
return department; 
}
// Net Salary Calculation Method: basic salary + allowances (fixed 2000) - deductions (fixed 500)
public double calculateNetSalary()
 {
double allowances = 2000.0; 
// Allowance
double deductions = 500.0;
// Deduction
return this.basicSalary + allowances - deductions;
}
 // Format data to save in text file
public String toFileString()
 {
 return id + "," + name + "," + basicSalary + "," + department;
}
}
// Main Swing Application Class 
public class mahaveer extends JFrame
 {
 // Components
 private JTextField txtId, txtName, txtSalary;
private JComboBox<String> cmbDepartment;
 private JTextArea txtAreaDisplay;
// Requirement: Store employee objects in an ArrayList Collection
 private ArrayList<Employee> employeeList;
private static final String FILE_NAME = "employees.txt";
public mahaveer() 
{
employeeList = new ArrayList<>();
// Frame Setup
setTitle("Employee Payroll System");
setSize(650, 550);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setLocationRelativeTo(null);
setLayout(new BorderLayout());
// Top Form Panel 
JPanel panelInput = new JPanel(new GridLayout(4, 2, 10, 10));
panelInput.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
panelInput.add(new JLabel("Employee ID:"));
txtId = new JTextField();
panelInput.add(txtId);
panelInput.add(new JLabel("Employee Name:"));
txtName = new JTextField();
panelInput.add(txtName);
panelInput.add(new JLabel("Basic Salary:"));
txtSalary = new JTextField();
panelInput.add(txtSalary);
panelInput.add(new JLabel("Department:"));
String[] departments = {"HR", "IT", "Finance", "Sales", "Marketing"};
cmbDepartment = new JComboBox<>(departments);
panelInput.add(cmbDepartment);
add(panelInput, BorderLayout.NORTH);
// Display Area use JTextArea
txtAreaDisplay = new JTextArea();
txtAreaDisplay.setEditable(false);
JScrollPane scrollPane = new JScrollPane(txtAreaDisplay);
scrollPane.setBorder(BorderFactory.createTitledBorder("Employee Records & Payroll Summary"));
add(scrollPane, BorderLayout.CENTER);
 // Bottom Panel Buttons: Add, Save, Load, View All
JPanel panelButtons = new JPanel();
JButton btnAdd = new JButton("Add Employee");
JButton btnSave = new JButton("Save to File");
JButton btnLoad = new JButton("Load from File");
JButton btnViewAll = new JButton("View All");
panelButtons.add(btnAdd);
panelButtons.add(btnSave);
panelButtons.add(btnLoad);
panelButtons.add(btnViewAll);
add(panelButtons, BorderLayout.SOUTH);
// BUTTON  
// Add Employee Button 
btnAdd.addActionListener(new ActionListener()
 {
@Override
public void actionPerformed(ActionEvent e)
 {
try 
{
String id = txtId.getText().trim();
String name = txtName.getText().trim();
if (id.isEmpty() || name.isEmpty() || txtSalary.getText().trim().isEmpty())
{
JOptionPane.showMessageDialog(mahaveer.this,
 "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
System.out.println("[CONSOLE LOG] Error: Attempted to add employee with empty fields.");
return;
}
double salary = Double.parseDouble(txtSalary.getText().trim());
String dept = (String) cmbDepartment.getSelectedItem();
// Using Parameterized Constructor to create Employee object
Employee emp = new Employee(id, name, salary, dept);
// Adding object to ArrayList Collection
employeeList.add(emp);
// System.out.println statements for console tracking
System.out.println("========================================");
System.out.println("[CONSOLE LOG] Employee Added Successfully!");
System.out.println("ID         : " + id);
System.out.println("Name       : " + name);
System.out.println("Basic Salary: " + salary);
System.out.println("Department : " + dept);
System.out.println("Net Salary : " + emp.calculateNetSalary());
System.out.println("========================================");
JOptionPane.showMessageDialog(mahaveer.this,
 "Employee Added to List Successfully!");
clearFields();
}
 catch (NumberFormatException ex)
 {
JOptionPane.showMessageDialog(mahaveer.this, "Invalid Salary format! Enter numbers only.", "Error", JOptionPane.ERROR_MESSAGE);
System.out.println("[CONSOLE LOG] Error: Invalid salary format entered.");
}
}
});
//Save Button Logic File I/O using FileWriter & BufferedWriter
btnSave.addActionListener(new ActionListener() {
@Override
public void actionPerformed(ActionEvent e) 
{
if (employeeList.isEmpty()) 
{
JOptionPane.showMessageDialog(mahaveer.this,
 "No records in memory to save!", "Warning", JOptionPane.WARNING_MESSAGE);
System.out.println("[CONSOLE LOG] Warning: Save attempted, but memory list is empty.");
return;
}
try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) 
{
for (Employee emp : employeeList)
{
bw.write(emp.toFileString());
 bw.newLine();
}
System.out.println("========================================");
System.out.println("[CONSOLE LOG] Success: All records saved to " + FILE_NAME);
System.out.println("Total Records Saved: " + employeeList.size());
System.out.println("========================================");
JOptionPane.showMessageDialog(mahaveer.this, "Records Saved to " + FILE_NAME + " successfully!");
 }
catch (IOException ex)
 {
JOptionPane.showMessageDialog(mahaveer.this,
 "Error saving file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
System.out.println("[CONSOLE LOG] Error saving file: " + ex.getMessage());
}
}
});
//  Load Button Logic File I/O using BufferedReader
btnLoad.addActionListener(new ActionListener() 
{
@Override
public void actionPerformed(ActionEvent e)
 {
File file = new File(FILE_NAME);
if (!file.exists()) 
{
JOptionPane.showMessageDialog(mahaveer.this, "No saved file found (" + FILE_NAME + ")", "Error", JOptionPane.ERROR_MESSAGE);
System.out.println("[CONSOLE LOG] Error: File " + FILE_NAME + " not found for loading.");
return;
 }
employeeList.clear();
 // Clear current list before loading fresh data
try (BufferedReader br = new BufferedReader(new FileReader(file))) 
{
String line;
int count = 0;
System.out.println("========================================");
System.out.println("[CONSOLE LOG] Loading records from " + FILE_NAME + "...");
while ((line = br.readLine()) != null)
{
String[] parts = line.split(",");
if (parts.length == 4) 
{
String id = parts[0];
String name = parts[1];
double salary = Double.parseDouble(parts[2]);
String dept = parts[3];
// Reconstruct employee object and add to list
Employee emp = new Employee(id, name, salary, dept);
employeeList.add(emp);
count++;
System.out.println("Loaded -> ID: " + id + ", Name: "
 + name + ", Salary: " + salary + ", Dept: " + dept);
 }
}
System.out.println("Total Records Loaded: " + count);
System.out.println("========================================");
JOptionPane.showMessageDialog(mahaveer.this, "Records Loaded from file successfully!");
viewAllEmployees();
 // Automatically display after loading
}
catch (IOException | NumberFormatException ex)
 {
JOptionPane.showMessageDialog(mahaveer.this, "Error loading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
System.out.println("[CONSOLE LOG] Error loading file: " + ex.getMessage());
}
 }
 });
//  View All Button Displays formatted records & Net Salary
btnViewAll.addActionListener(new ActionListener()
 {
@Override
public void actionPerformed(ActionEvent e) 
{
 viewAllEmployees();
 }
  });
}
 // Helper method to display all employees in JTextArea & Console
private void viewAllEmployees() 
{
txtAreaDisplay.setText("");
if (employeeList.isEmpty())
{
txtAreaDisplay.setText("No employee records found.");
System.out.println("[CONSOLE LOG] View All: No records found in memory.");
return;
}
 txtAreaDisplay.append(String.format("%-10s | %-15s | %-12s | %-10s | %-12s\n", "ID", "Name", "Basic Salary", "Dept", "Net Salary"));
 txtAreaDisplay.append("------------------------------------------------------------------------------------\n");
System.out.println("========================================");
System.out.println("[CONSOLE LOG] Displaying All Employee Records:");
for (Employee emp : employeeList)
	{
double netSalary = emp.calculateNetSalary(); // Formula evaluation
 txtAreaDisplay.append(String.format("%-10s | %-15s | %-12.2f | %-10s | %-12.2f\n",
emp.getId(), emp.getName(), emp.getBasicSalary(), emp.getDepartment(), netSalary));
System.out.println("ID: " + emp.getId() + " | Name: " + emp.getName() + " | Basic: " + emp.getBasicSalary() + " | Dept: " + emp.getDepartment() + " | Net: " + netSalary);
 }
System.out.println("========================================");
 }
// Clear input fields
private void clearFields() 
{
 txtId.setText("");
  txtName.setText("");
 txtSalary.setText("");
  cmbDepartment.setSelectedIndex(0);
  }
  public static void main(String[] args) 
  {
 System.out.println("[CONSOLE LOG] Starting Employee Payroll System Application...");
// Run Swing GUI safely on Event Dispatch Thread
SwingUtilities.invokeLater(new Runnable()
 {
 @Override
public void run()
 {
 new mahaveer().setVisible(true);
}
});
 }
}