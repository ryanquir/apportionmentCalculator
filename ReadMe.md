# HW1
Final Submission: (02/07/2023 11:05pm ET)
## Authors

* Pranav Ramkumar - vej8jz
* Christopher Joseph - tbh7cm
* Ryan Quiram - zhe5qq

## Description

This repository contains a program that executes the Hamilton's Apportionment algorithm.

In order to run this program on a local environment, the following steps must be taken:
 * Copy the https link of the github repo (https://github.com/uva-cs3140-sp23/hw1-vej8jz-tbh7cm-zhe5qq.git)
 * Use the link to clone the repo into a desired local folder
   * Via CLI: Use "git init" followed by the "git clone 'link'" command on the desired directory
   * Via IntelliJ: Go to "File>New>Project From Version Control" and enter the link into the URL field
 * In any terminal, enter the directory, and enter "./gradlew build" to enable automated building in this local directory
 * To run the program, run Apportionment.jar, which is found in "./build/libs/", with a csv file and optional representative number parameter using the following command:

 "java -jar build/libs/Apportionment.jar Data/2020census.csv 1000"
