# TodoSampeApp
Todo Sample app for technical assessment

The project contains following files - 

MainActivity - Provides options to add, edit ,delete and view tasks.

TaskDataBaseHandler - this db handler class creates a SQLite database "TodoSampleDB" and a table in it "tasks", also provides functionality to create new tasks using addTask() method, delete a task using deleteTask() method, edit a task using editTask() method and get the complete list of all tasks using getAllTasks() method. it also takes care of db version.

ShowTaskRecyclerAdapter - this class populates the list which shows all the tasks saved in the database.

Task class - this class contains all the attributes related to a task like name, description,date created, date modified. Also contains getter and setter methods for all the attributes.

Editing and Creation of new tasks is done by showing a popup with edittexts and the new values are written in into the db in db handler class.
