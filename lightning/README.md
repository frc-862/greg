# lightning

![gradle build](https://github.com/frc-862/lightning/workflows/gradle%20build/badge.svg)

utility package for frc robots.

## Getting Started

in root directory of WPILib robot project (must be on git), run the following

```bash
git subtree add --prefix=lightning https://github.com/frc-862/lightning master --squash
```

you can later replace repository url with a remote, as configured below

```bash
git remote add lightning https://github.com/frc-862/lightning.git
```

you must also add the line below to the project's `build.gradle` file in the `dependencies` section

```groovy
dependencies {
  compile(project("lightning"))
}
```

also, you will need to add the following line to the end of the project's `settings.gradle` file

```groovy
include 'lightning'
```
