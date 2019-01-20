# Active Diagrams

Live execution of connected modules inside popular free and open-source office suite LibreOffice. Modules are represented interms of 
shapes and other components available in LibreOffice. Execution will be supported with the presence of Active Diagrams extension moudule.
These components will act as pure office files if the extension is not present. Helping users to demonstrate what they try to explain 
from the architecture diagrams. Input values can be provided to the flow diagrams and after execution results will be presented in 
instructed format.

## Development

### Environment Setup

Install the below softwares in computer where the software going to develop.
* LibreOffice: 6.1
* LibreOffice SDK: 6.1
* Java: 1.7 or above
* Eclipse: Latest version for Java EE

### Setup Eclipse for development

Install the below plug-in(s) in Eclipse.
* LOEclipse: For LibreOffice extension project development
* e(fx)clipse: For JavaFX project development

Import projct Active Diagrams into Eclipse (File->Import->General->Existing projects into workspace)
  - Go to Window->Preferences in Eclipse and configure Java->Build path
  - Right click on project and select Properties
    - Select LibreOffice Properties
      - Configure LibreOffice path
      - Configure LibreOffice SDK path

No compilation errors shown in Probelms Tab

### Execute project

- Right click project
- Select Run As->LibreOffice Extension

Check whether 'Modeler' menu appeared in Main Menu Bar.

