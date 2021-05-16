# Team-Project
This is the coursework for the Team Project Module. The task was to create an Air Ticket Sales System that utilised a database.

This was my first ever attempt at a large scale project in Java and this was also my first try at making a standalone application that communicated with a Database.

Unfortunately, due to the pandemic I was the only coder left on the project as my other coder was affected due to the unforseen circumstances so if you see any coding sins, please forgive me. But altogether, I think the project turned out pretty well!

On reflection, if I were to do this project again:
I would try to make my code more compact: this would involve generalizing methods in different classes in order to make them reusable and call them in different classes. There are a few points where I found myself copy pasting the same methods and modifying them slightly, which is quite inefficient.

Instead of using JAVAFX, I would use HTML, CSS, JavaScript and MongoDB.

Finally, I would code some of the extra features that I intended to implement such as automatic backup. Unfortunately, the time constraints and unforseen circumstances led to me leaving those extra features out and just going for the main system.

## Running the Program (in IntelliJ IDEA):
* In order to run the program, you will need a MYSQL server running in the background. Install MYSQL to your machine and setup a Database and make a note of the Database name, your username and password.
* Manually insert the BackupOfDatabase.txt file into your database SQL client and push the data into the database. Without doing this you will be stuck on the login page!
* Download the correct JavaFX SDK for your platform from the following link and unzip it: https://gluonhq.com/products/javafx/
* Download the Platform Independent J Connector for MySQL from the following link and unzip it: https://dev.mysql.com/downloads/file/?id=504660
* Git clone the program and open it in IntelliJ IDEA (IntelliJ should automatically recognise the Java SDK and make an out folder, if not please make an out folder at the project root and link Java 13.0.2 to the project).
* Go to File -> Project Structure -> Libraries and add all the JavaFX .jar dependencies that you will find in the "lib" folder of the unzipped JavaFX SDK.
* Do the same for the J connector .jar file.
* Edit the dbName, username and password variables in the DatabaseConnection.java file so the program will be linked to your local (**running**) database.
* Go to the Main.java file and press the "Add Configuration" button. Press the "+" button and select "Application". Identify "Main" as the main method and press the "Apply" button.
* Rebuild the Project.
* Run

A list of logins for the system in Username | Password form are as follows...

* Root | Root
* Dennis | Gnasher
* Minnie | NotiGirl
* Penelope | PinkMobile
* Arthur | LiesaLot
* You can make your own logins as well as long as you login as Root (or any other account that has System Administrator privileges).
