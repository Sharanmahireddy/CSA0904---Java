# GitHub Upload Guide

The ZIP is organized as a GitHub-ready Java project. The assignment instructions require the complete assignment work and source code to be uploaded to GitHub and the repository link to be submitted through Google Classroom.

## 1. Create a repository
Create a new GitHub repository, for example:

`smart-library-management-java`

## 2. Open the project folder
Open a terminal in the extracted project folder.

## 3. Run and verify the project
```text
javac -d out src/*.java
java -cp out Main
```

## 4. Initialize Git
```text
git init
git add .
git commit -m "Initial Smart Library Management assignment"
```

## 5. Connect the GitHub repository
Replace the placeholder repository address with the actual repository address created by the team.

```text
git branch -M main
git remote add origin <YOUR-GITHUB-REPOSITORY-URL>
git push -u origin main
```

## 6. Submit
Submit the GitHub repository link in Google Classroom together with the final assignment report as required by the supplied assignment instructions.

## Before submission
- Replace all blank team/faculty details in the report.
- Enter the actual contribution percentages.
- Create one individual reflection for every team member.
- Review the source code and execution outputs.
- Ensure the repository link works.
- Check the institution's plagiarism/originality requirements and acknowledge external/AI-assisted resources as required.
