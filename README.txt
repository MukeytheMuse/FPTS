# Financial Portfolio Tracking System
# SWEN-262 Team C
Eric Epstein
Kaitlin Brockway
Ian London
Kimberly Sookoo
Luke Veilleux


In Release 1 of our Financial Portfolio Tracking System, most of the base functionality is implemented in the system. In this zip file you will find
many different files including:
 - The Design document that shows our designs for this application as well as our thought process for those designs. This documents everything
 this application was meant to be, regardless if some of that functionality didn't make it into the project for Release 1.
 - The Powerpoint presentation that goes along with this release of the FPTS project.
 - The source code directory aptly named src/ where all of the source code for this release of the project can be found.
 - Listing.pdf A singular file with all of the source code in this application auto-generated for this projects purpose.
 - vclog.txt A copy of the git log that shows the log of the GitHub repository used in this project.
 - buildlog.txt The output messages that appear after building the project from scratch.
 - An executable .jar file, which can be used to run the application without building the project through an IDE.
 - start.bat a file that will automatically run the .jar file for the user.
 - This README.txt file
 
 In this release, the team would like our users to be aware that there is some limiting functionality in the implementation of the application.
 Namely this shows up in a few different places listed below.
  - Getting into the application, the pages for the app lose the clean overlay provided by .fxml files.
  - Persisting User data across logging in of the same user and different runs of the application is not working completely.
    - Transactional data is not persisted across logout/login and restarting of the application.
    - A bug exists where logging in a new user will leave the old user's portfolio loaded into the system, potentially across invocations of the 
        project.
  - 
  
  
  
  
  