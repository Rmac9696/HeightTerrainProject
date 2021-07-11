name := "HeightTerrain"

version := "0.1"

scalaVersion := "3.0.0"

resolvers+= "Jcenter" at "https://jcenter.bintray.com/"

resolvers+= "center" at "https://repo1.maven.org/maven2/"

libraryDependencies+= "org.jmonkeyengine" % "jme3-core" % "3.4.0-stable"

libraryDependencies+= "org.jmonkeyengine" % "jme3-desktop" % "3.4.0-stable"

libraryDependencies+= "org.jmonkeyengine" % "jme3-lwjgl" % "3.4.0-stable"

libraryDependencies+= "org.jmonkeyengine" % "jme3-jbullet" % "3.4.0-stable"

libraryDependencies+= "org.jmonkeyengine" % "jme3-plugins" % "3.4.0-stable"

libraryDependencies += "com.google.guava" % "guava" % "19.0"

libraryDependencies += "org.codehaus.groovy" % "groovy-jsr223" % "3.0.0"

libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.30"

libraryDependencies += "com.simsilica" % "lemur" % "1.14.0"

Compile / unmanagedResourceDirectories := List(file("J:\\Jmonkey tutorials\\HeightTerrianProject\\HelloCollision-SDK\\HelloCollision\\assets"))

run / fork := true
