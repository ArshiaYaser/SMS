# README.txt

## Student Management System (SMS)

### Introduction

The Student Management System (SMS) is a Java-based application designed to manage various aspects of an educational institution, including student and teacher records, attendance, feedback, and communication. The system provides a user-friendly graphical user interface (GUI) and uses text files for data storage and retrieval.

### Features

- **Admin Login and Account Management**
  - Create and manage administrator accounts.
  - Login functionality for administrators.
  
- **View Records**
  - View and manage student records.
  - View and manage teacher records.
  
- **Attendance Management**
  - Mark and view attendance records for students.

- **Feedback System**
  - View feedback provided by students.

- **Communication System**
  - Admins can send and receive messages.

- **Profile Management**
  - View administrator profile.

### Technologies Used

- **Programming Language:** Java
- **GUI Framework:** Swing
- **Data Storage:** Text files (.txt)
- **Development Environment:** Eclipse IDE
- **Version Control:** Git

### Installation

1. **Download and Install Java**
   - Ensure you have Java Development Kit (JDK) installed. You can download it from [Oracle's official site](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).

2. **Clone the Repository**
   - Clone the project repository from GitHub to your local machine using the following command:
     ```
     git clone <repository-url>
     ```

3. **Open the Project in Eclipse IDE**
   - Open Eclipse IDE and import the project by selecting `File > Import > Existing Projects into Workspace`.

4. **Compile and Run the Application**
   - Compile and run the application by selecting the main class `CreateAdminAccountGUI` and clicking on the `Run` button.

### Usage

1. **Creating an Admin Account**
   - Launch the application and use the `Create Admin Account` feature to create an admin account.
   - Enter the required details (Admin ID, Username, Name, and Password) and click `Create Account`.

2. **Admin Login**
   - Use the `Login` feature to log in with your admin credentials.

3. **Viewing and Managing Records**
   - Use the `View Teacher Records` and `View Student Records` buttons to view and manage teacher and student information.

4. **Attendance Management**
   - Mark and view attendance records through the attendance management interface.

5. **Feedback System**
   - View student feedback by clicking the `View Feedback` button.

6. **Communication System**
   - Use the `Messages` button to access the communication system, where admins can send and receive messages.

7. **Profile Management**
   - View the administrator profile by clicking the `View Profile` button.

### File Structure

- **src/sms/**
  - Contains the Java source files for the project.

- **data/**
  - Directory where text files for storing data are located (e.g., `admins.txt`, `teachers.txt`, `students.txt`, `attendance.txt`, `feedback.txt`).

### Data Files

- **admins.txt**
  - Stores admin account information in the format: `ID,Username,Name,Password`.

- **teachers.txt**
  - Stores teacher information in the format: `ID,Username,Name,Password,Subject`.

- **students.txt**
  - Stores student information in the format: `ID,Username,Name,Password,Class,Semester,DOB`.

- **attendance.txt**
  - Stores attendance records.

- **feedback.txt**
  - Stores feedback from students in the format: `StudentID:Feedback`.

### Contributions

- **Development Team:**
  - Developer 1
  - Developer 2
  - Developer 3

### License

This project is licensed under the MIT License. See the `LICENSE` file for details.

### Support

For any issues or support, please contact [support@example.com].

---

Thank you for using the Student Management System (SMS). We hope this system helps you manage your educational institution effectively.# SMS
