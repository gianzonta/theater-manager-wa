RUZZANTE THEATER MANAGEMENT SYSTEM

Database informations:

The website is based on a server built with the provided SQL files in "src/main/database"

For ease of development we used a remote server @dei, using for connection the scripts provided in "dei_database_conn" folder

The context.xml should be modified accordingly if another data source is used.
Two versions are available in the same folder: "src/main/webapp/META-INF/context_backup.txt"


AUTHORIZED USERS:

Role: Company Manager (Full Access) - usr: cm , psw: cm

Role: Director (Can't update members) - usr:  lucas , psw: thelucaspsw

Role: Crew (Director + can't access transactions) - usr: linda , psw: thelindapsw

Role: Actor (Crew + can't access activities and items) - usr: liam , psw: theliampsw