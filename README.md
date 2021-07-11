# HeightTerrainProject
A small procedurally generated first person game level using JMonkey Libraries among others.
To build and run with Intellij:
change the line(29) in build.sbt to:
Compile / unmanagedResourceDirectories := List(file("{URI of assets folder in your HelloCollision-SDK folder}"))
this line makes the assets folder visible to the runtime jar.
the assets folder is where game assets are placed and follows the Jmonkey-SDK's assets folder structure (any .j3o,textures,etc made with the SDK or anything compatible with Jmonkey can be placed here) 
unpack the textures archive before build
