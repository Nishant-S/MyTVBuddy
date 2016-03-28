Application will download Episode List from server for popular series "Game of Thrones". Once user select any Episode, he 
can view details on next screen.
Application uses Volley for networking operation; it is efficient for processing requests in separate thread and publish 
result on main thread as well as maintain memory cache.
For parsing Json, it uses Gson library; it efficiently parses Json string and populated referenced model classes.
Application uses Activity just to hold the Fragments and communicate between them.All remaining work like get Json data, parse Json data are initiated by Fragment; hence it can handle different configuration change like device rotation.
