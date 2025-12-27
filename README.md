# 📇 Contact Desktop Application

A Java desktop application built using **JavaFX** for the graphical user interface and **Apache Derby** as the embedded database.  
The application uses **JDBC** to connect to the database and perform full **CRUD operations** on contact data.

The system allows users to manage contacts efficiently through a clean and user-friendly interface, ensuring reliable data storage and retrieval.

---

## 🔧 Technologies Used
- Java  
- JavaFX  
- Apache Derby  
- JDBC  
- NetBeans IDE  

---

## ✨ Features
- Add new contacts  
- View stored contacts  
- Update contact information  
- Delete contacts  
- Database connectivity using JDBC  

---

## ▶️ How to Download & Run the Project (NetBeans)

### 1️⃣ Prerequisites
Make sure you have the following installed:
- **JDK 8 or higher**
- **NetBeans IDE**
- **Apache Derby** (bundled with NetBeans)
- **Scene Builder** (optional – for UI editing)

---

### 2️⃣ Clone the Repository
```bash
git clone https://github.com/your-username/contact-desktop-app.git

3️⃣ Open the Project in NetBeans

Open NetBeans

Go to File → Open Project

Select the cloned project folder

Wait for NetBeans to load the project

4️⃣ Configure JavaFX in NetBeans

If using JDK 8:
JavaFX is included by default ✅

If using JDK 11 or higher:

Download JavaFX SDK

Right-click the project → Properties

Go to Run

Add the following to VM Options:

--module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml

5️⃣ Apache Derby Configuration

NetBeans includes Apache Derby by default

The application uses Derby in embedded mode

Database and tables are created automatically on first run

6️⃣ Run the Application

Right-click the project

Select Run

The Contact Desktop Application window should appear 🎉

📌 Notes

This project is optimized for NetBeans IDE

Uses Apache Derby Embedded Database

All database operations are handled using JDBC

No external database setup is required
