# Sample Data for MarkIt App 



In order to build the Mark It application and apply its full functionality I will need some sample data. That data will need to be described somewhere so I can be sure that it is all working correctly. This is that somewhere.

#### Tables in the database

These are the classes that exist currently, and the fields that they contain:

- **Student**
  - mID
  - Student ID
  - StudentFirstName
  - StudentLastName
- **LabGroup**
  - mID
  - LabGroupStudentID
  - LabGroupAssignmentID
  - LabGroupNumber
  - LabGroupLocation
  - LabGroupUnit
- **MarkScheme**
  - mID
  - MarkSchemeAssignmentID
  - MarkSchemeAssignmentName
  - MarkSchemeAssignmentAvailableMarks
  - MarkSchemeAuthor
- **MarkSchemeSection**
  - mID
  - SectionAssignmentID
  - SectionID
  - SectionName
  - SectionAvailableMarks
  - SectionAuthor
- **MarkSchemePart**
  - mID
  - PartAssignmentID
  - PartSectionID
  - PartName
  - PartAvailableMarks
  - PartAuthor
- **StudentSectionMarks**
  - mID
  - SectionStudentID
  - SectionAssignmentID
  - SectionPartID
  - SectionID
  - SectionStudentMarks
  - SectionAuthor
- **StudentTotalMarks**
  - mID
  - TotalStudentID
  - TotalAssignmentID
  - TotalMarksAchieved



Each of these classes has a table in the database. "mID" is always the unique key for the row and it is a string value provided by the Azure backend, which adds further columns for creation and update times, deleted and update flags and a version number.



#### Sample data: the imaginary students

A sample of 10 "imaginary" students will be used for testing. This is them:

- **Enrique Tasa**
  - ID: 9673164

- **Zain Shahid**
  - ID: 9999999
- **David English**
  - ID: 8888888
- **Chloe Alexander**
  - ID: 7777777
- **Amy Hamilton**
  - ID: 6666666
- **Javaad Mahzar**
  - ID: 5555555
- **Michael Goldsmith**
  - ID: 4444444
- **George Wellard**
  - ID: 3333333
- **Jake Kealey**
  - ID: 2222222
- **Elias Wee**
  - ID: 1111111



#### Sample data: the imaginary labs

Once the students are defined, we can define the labs that will "happen" and enrol said students in them:

- **Assignment ID 1**

  - Microcontroller Engineering II

  - "Getting to grips with the PIC18"

  - Barnes Wallis PC Cluster

  - Group 1

    - Enrique Tasa
    - Zain Shahid
    - Elias Wee
    - David English

  - Group 2

    - Amy Hamilton

    - Chloe Alexander

    - Michael Goldsmith

      

- **Assignment ID 2**
  - Microcontroller Engineering II

  - "Using the ADC on the PIC18"

  - Joule Library PC Cluster

  - Group 1

    - Enrique Tasa
    - Elias Wee
    - Amy Hamilton
    - Michael Goldsmith

  - Group 2

    - Zain Shahid

    - Chloe Alexander

    - David English

      

- **Assignment ID 3**

  - Concurrent Systems
  - "Basics of Semaphores"
  - Sackville Street Building A16
  - Group 1 
    - Enrique Tasa
    - Javaad Mahzar
    - Jake Kealey
  - Group 2
    - George Wellard
    - Amy Hamilton



- **Assignment ID 4**
  - Concurrent Systems
  - "Memory Management in OSs"
  - Sackville Street Building D16
  - Group 1
    - Amy Hamilton 
    - Enrique Tasa
    - George Wellard
  - Group 2
    - Jake Kealey
    - Javaad Mahzar





#### Sample data: the imaginary mark schemes

Once we have defined the students and the labs they will take part in, we only need to define the mark scheme structure for said labs. This will consist of an assignment which is split into sections which in turn are split into parts. Each of these will have a certain amount of marks assigned to them.



- **Assignment ID 1** - Getting to grips with the PIC18 (9 marks)
  - Section 1 - Attendance (1 mark)
    - Part 1 - Student Present (1 mark)
  - Section 2 - Basic use of the PIC18 (3 marks)
    - Part 1 - Student can build a program on the IDE (1 mark)
    - Part 2 - Student can run a program on the PIC18 (2 marks)
  - Section 3 - Programming the PIC18 (5 marks)
    - Part 1 - Student can display a binary value on the LEDs (2 marks)
    - Part 2 - Student can display a numerical value on the 7SEG display (3 marks)



- **Assignment ID 2** - Using the ADC on the PIC18 (14 marks)
  - Section 1 - Attendance (1 mark)
    - Part 1 - Student Present (1 mark)
  - Section 2 - Manipulation of the ADC (5 marks)
    - Part 1 - Student can activate the ADC via code (2 mark)
    - Part 2 - Student can display ADC value on 7SEG (3 marks)
  - Section 3 - The ADC in use (8 marks)
    - Part 1 - Student can change ADC value with potentiometer (3 marks)
    - Part 2 - Student can display temperature reading on 7SEG display (5 marks)



- **Assignment ID 3** - Basics of Semaphores (14 marks)
  - Section 1 - Attendance (1 mark)
    - Part 1 - Student Present (1 mark)
  - Section 2 - Theory (5 marks)
    - Part 1 - Student can explain basic synchronisation (1 mark)
    - Part 2 - Student can explain where synchronisation issues might arise (1 mark)
    - Part 3 - Student can explain function and use of semaphores (3 marks)
  - Section 3 - Practice (8 marks)
    - Part 1 - Student can program a simple semaphore class in C++ (3 marks)
    - Part 2 - Student can make use of semaphore class and change amount of available resources (5 marks)



- **Assignment ID 4** - Memory Management in OSs (14 marks)
  - Section 1 - Attendance (1 mark)
    - Part 1 - Student Present (1 mark)
  - Section 2 - Theory (6 marks)
    - Part 1 - Student can explain memory paging (3 mark)
    - Part 2 - Student understands page misses (1 mark)
    - Part 3 - Student can explain DMA (3 marks)
  - Section 3 - Practice (7 marks)
    - Part 1 - Student can access sample data in trace files (2 marks)
    - Part 2 - Student can simulate cache in a terminal window (5 marks)





