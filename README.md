# CodeDoctorAPI

![Jenkins](https://img.shields.io/jenkins/build?jobUrl=https%3A%2F%2Fci.codemc.io%2Fjob%2FCodeDoctorDE%2Fjob%2FCodeDoctorAPI&style=for-the-badge)
![GitHub repo size](https://img.shields.io/github/repo-size/CodeDoctorDE/CodeDoctorAPI?style=for-the-badge)
![GitHub commit activity](https://img.shields.io/github/commit-activity/m/CodeDoctorDE/CodeDoctorAPI?style=for-the-badge)
![GitHub](https://img.shields.io/github/license/CodeDoctorDE/CodeDoctorAPI?style=for-the-badge)

My own api for my plugins.

JavaDocs: <https://codedoctor.tk/CodeDoctorAPI>

## Structure

```mermaid
graph TD
  utils
  animation
  command
  config
  game
  item
  region
  request
  scoreboard
  serializer
  server
  translation
  gui
  gui-template
  util
  request
  item --> translation
  config --> translation
  item --> ui
  request --> gui-template
  gui --> gui-template
  item --> gui-template
  util --> gui-template
  server --> gui-template
  translation --> gui-template
```