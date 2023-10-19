# Java-Amusement-Park-JDBC-SQL

Database Management for Amusement Park Rides

The "BDDGestion" class in this Java code facilitates database management for amusement park rides. It handles database connections and offers methods for tasks such as retrieving ride data, adding new rides, and calculating ride frequencies within different parks. This class plays a pivotal role in managing and interacting with the database, ensuring efficient data handling for amusement park ride information.


Central Application Manager for Theme Park Database

The "MainClass" in this Java code serves as the central component of the program, housing the "main" method. It creates the main application window, manages the display of ride and park information, and handles interactions with the database. The "MainClass" plays a key role in orchestrating the user interface and database interactions within this application.


Theme Park Ride Class

The "Manege" class defines the attributes of a theme park ride, such as its name, height, and speed. This class implements the Comparable interface, enabling ride comparisons based on their names. It provides methods for hash code calculation, equality checks, and a human-readable string representation of the ride. The class allows easy access to ride information, making it a fundamental building block for managing and displaying theme park data.


Secondary Ride Details Window in Database Application

The "ManegeInfo" class serves as a secondary window designed to display in-depth information about theme park rides retrieved from the database. When a user selects a ride from the main application, this class creates a separate window to showcase details such as the ride's name, height, and speed. The chosen ride's name becomes the window title, and information is neatly presented for user reference. Importantly, when the secondary ride details window is closed, it triggers the re-display of the main application window. This class ensures a streamlined user experience, facilitating the exploration of theme park ride specifics.


Secondary Park Details Window in Database Application

This Java code defines a secondary window for displaying details about amusement parks in a database application. It allows users to view information about the parks, such as the names of rides they contain and their respective speeds and heights. The window is designed to be displayed when the main park window is closed, offering a more detailed perspective on park contents. Users can navigate between different rides within a park and view their attributes, enhancing the overall user experience of the application.


UserData Class for Database Connectivity

The "UserData" class serves as a simple container for database connection information in a Java program. It includes the URL of the database, the login username, and the associated password required to establish a connection to the specified database. This information is essential for connecting to the database seamlessly.
