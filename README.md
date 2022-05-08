# TheVirtualCloud | Cloud Network Environment

---

![TheVirtualCloud](./docs/img/header.png)

## Installation

---

If you want to download virtualcloud manually, follow this link [here](https://download1587.mediafire.com/rbnqmmvceysg/38eqqnrgd9gznhl/cloud-master.zip) and create a start.sh.
If you are using a linux operating system you need to create a start.sh and add the following start parameter.

```
screen -S VirtualCloud java -XX:+UseG1GC -XX:MaxGCPauseMillis=50 -XX:CompileThreshold=100 -XX:+UnlockExperimentalVMOptions -XX:+UseCompressedOops -Xmx512m -Xms256m -jar theVirtualCloud.jar
```

If you are using a Windows operating system you need to create a start.bat file and add the following start parameters.

```
java -XX:+UseG1GC -XX:MaxGCPauseMillis=50 -XX:CompileThreshold=100 -XX:+UnlockExperimentalVMOptions -XX:+UseCompressedOops -Xmx512m -Xms256m -jar launcher.jar
```

## Introduction

---

## Features

---

- **module system** 
- **default modules**
- **group system** 
- **template system** 

## Modules

---





