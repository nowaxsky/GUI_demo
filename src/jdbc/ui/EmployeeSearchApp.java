package jdbc.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jdbc.core.Employee;
import jdbc.dao.EmployeeDAO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class EmployeeSearchApp extends JFrame {

	private JPanel contentPane;
	private JTextField lastNameTextField;
	private JTable table;
	
	private EmployeeDAO employeeDAO;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeSearchApp frame = new EmployeeSearchApp();
					frame.setVisible(true);
				} catch (Exception exc) {
					exc.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EmployeeSearchApp() {
		
		// Create the DAO
		try {
			employeeDAO = new EmployeeDAO();
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		
		setTitle("Employee Demo App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Enter last name");
		panel.add(lblNewLabel);
		
		lastNameTextField = new JTextField();
		panel.add(lastNameTextField);
		lastNameTextField.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Get last name from the text field
				
				// Call DAO and get employees for the last name
				
				// If last name is empty, then get all employees
				
				// Print out employees
				
				try {
					String lastName = lastNameTextField.getText();
					
					List<Employee> employees = null;
					
					if (lastName != null && lastName.trim().length() > 0) {
						employees = employeeDAO.searchEmployees(lastName);
					} else {
						employees = employeeDAO.getAllEmployees();
					}
					
					// Create a model and update the "table"
					EmployeeTableModel model = new EmployeeTableModel(employees);
					
					table.setModel(model);
					
					/*
					for (Employee temp : employees) {
						System.out.println(temp);
					}
					*/
					
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(EmployeeSearchApp.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
					
				}
				
							
			}
		});
		panel.add(btnSearch);
		
		JLabel lbljustClickTo = new JLabel("(Just click to show the database)");
		panel.add(lbljustClickTo);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		JButton btnAddEmployee = new JButton("Add Employee");
		btnAddEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// create dialog
				EmployeeDialog dialog = new EmployeeDialog(EmployeeSearchApp.this, employeeDAO);
				
				// show dialog
				dialog.setVisible(true);
			
			}
		});
		panel_1.add(btnAddEmployee);
		
		JButton btnUpdateEmployee = new JButton("Update Employee");
		btnUpdateEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// get the selected item
				int row = table.getSelectedRow();
				
				// make sure a row is selected
				if (row < 0) {
					JOptionPane.showMessageDialog(EmployeeSearchApp.this, "You must select an employee", "Error",
							JOptionPane.ERROR_MESSAGE);				
					return;
				}
				
				// get the current employee
				Employee tempEmployee = (Employee) table.getValueAt(row, EmployeeTableModel.OBJECT_COL);
				
				// create dialog
				EmployeeDialog dialog = new EmployeeDialog(EmployeeSearchApp.this, employeeDAO, 
															tempEmployee, true);

				// show dialog
				dialog.setVisible(true);			
			
			}
		});
		panel_1.add(btnUpdateEmployee);
		
		JButton btnDeleteEmployee = new JButton("Delete Employee");
		btnDeleteEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					// get the selected row
					int row = table.getSelectedRow();

					// make sure a row is selected
					if (row < 0) {
						JOptionPane.showMessageDialog(EmployeeSearchApp.this, 
								"You must select an employee", "Error", JOptionPane.ERROR_MESSAGE);				
						return;
					}

					// prompt the user
					int response = JOptionPane.showConfirmDialog(
							EmployeeSearchApp.this, "Delete this employee?", "Confirm", 
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (response != JOptionPane.YES_OPTION) {
						return;
					}

					// get the current employee
					Employee tempEmployee = (Employee) table.getValueAt(row, EmployeeTableModel.OBJECT_COL);

					// delete the employee
					employeeDAO.deleteEmployee(tempEmployee.getId());

					// refresh GUI
					refreshEmployeesView();

					// show success message
					JOptionPane.showMessageDialog(EmployeeSearchApp.this,
							"Employee deleted succesfully.", "Employee Deleted",
							JOptionPane.INFORMATION_MESSAGE);

				} catch (Exception exc) {
					JOptionPane.showMessageDialog(EmployeeSearchApp.this,
							"Error deleting employee: " + exc.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			
			}
		});
		panel_1.add(btnDeleteEmployee);
	}

	public void refreshEmployeesView() {

		try {
			List<Employee> employees = employeeDAO.getAllEmployees();

			// create the model and update the "table"
			EmployeeTableModel model = new EmployeeTableModel(employees);

			table.setModel(model);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
}
