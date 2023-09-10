<p>To start the app, use java 17 and use the the task <b>./gradlew clean build run</b></p>
<p>This app will start a single producer and consumer thread which will listen till the app is not aborted.
The database schema gets created on initialization.
The class SynchronizedBuffer utilizes the Producer-Consumer design pattern to
apply the necessary commands. The assignment is as follows:</p>

<p>
Create program in Java language that will process commands from FIFO queue using Producer –
Consumer pattern.
Supported commands are the following:
Add  - adds a user into a database
PrintAll – prints all users into standard output
DeleteAll – deletes all users from database
User is defined as database table SUSERS with columns (USER_ID, USER_GUID, USER_NAME)
Demonstrate program on the following sequence (using main method or test):
Add (1, &quot;a1&quot;, &quot;Robert&quot;)
Add (2, &quot;a2&quot;, &quot;Martin&quot;)
PrintAll
DeleteAll
PrintAll
Show your ability to unit test code on at least one class.
Goal of this exercise is to show Java language and JDK know-how, OOP principles, clean code
understanding, concurrent programming knowledge, unit testing experience.
Please do not use Spring framework in this exercise. Embedded database is sufficient.
</p>