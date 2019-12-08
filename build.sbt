name := "numbers"

version := "0.1"

scalaVersion := "2.13.1"

val sharedSettings = Seq(
  scalaVersion := "2.13.1",
  libraryDependencies += "org.scalatest" %%% "scalatest" % "3.1.0" % "test",
)

lazy val gen =
// select supported platforms
  crossProject(JSPlatform, JVMPlatform/*, NativePlatform*/)
    .crossType(CrossType.Full) // [Pure, Full, Dummy], default: CrossType.Full
    .withoutSuffixFor(JVMPlatform)
    .settings(sharedSettings)
    .jsSettings(/* ... */) // defined in sbt-scalajs-crossproject
    .jvmSettings(/* ... */)
//    .in(file("gen"))
    // configure Scala-Native settings
//    .nativeSettings(/* ... */) // defined in sbt-scala-native

lazy val playground = project.settings(sharedSettings).dependsOn(gen.jvm)

//lazy val spa = (project in file("spa"))
//  .settings(
////    libraryDependencies += "com.lihaoyi" %%% "scalatags" % "0.7.0",
//  )
//  .enablePlugins(ScalaJSPlugin)
//  .dependsOn(gen.js)

lazy val root = (project in file("."))
  .aggregate(gen.jvm, gen.js/*, spa*/)
  .settings(update / aggregate := false)