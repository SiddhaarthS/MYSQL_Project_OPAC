MYSQL_Project_OPAC
==================

Developed a simple OPAC system for library using event­driven and concurrent pro­ gramming paradigms of Java. Use JDBC to connect to a back­end database.

---------
1.Create a Online Public Access Catalogue System for Library Management with the  following operations.            a) Tables – Book Details ( Title, Access no, Author, Category, Status (Available  / Issued / Transfered) etc.,)       ­ Members Details( Member name, Member id, Category (Student /  Staff) , Department, Year of Study, Issued count etc.,)        ­ Issue Details ( Book Access no, Member id, Issue Date, Return  Date, Fine if any etc.,)       b) Operations : (Can Use Menu) i) Entry – for Book & for Member ii) Search – by Title & by Author  iii) Issue  iv) Return v)  List issued books based on Member id      c) Conditions:
– For Entry (i) the access should be given for only Librarian.
– For Search (ii) – Exception should be thrown for Book not found  and if  found then the details of the book and its current status should be shown.  If issued then the member id should also be shown.
– For Issue(iii) A Staff can take 5 books. Return date is 6 months from  issue date. (Issue has to validate this maximum limit). A Student can  take 3 books. Return date is 3 month from issue date. (Issue (iii) has to  validate this maximum limit). Whenever a book is issued then the Issued  count field in Member details table should be updated.
