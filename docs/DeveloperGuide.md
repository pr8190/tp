---
layout: default.md
title: "Developer Guide"
pageNav: 3
---

# Hall Ledger Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* Hall Ledger’s demerit rule catalogue is adapted from the NUS Office of Student Affairs **Demerit Point Structure (DPS)
  for Breach of Housing Agreement**, dated 9 January 2026.
* This project is based on the **AddressBook-Level3 (AB3)** codebase from [se-education/addressbook-level3](https://github.com/se-edu/addressbook-level3).
* dinhcodes has used co-pilot complete to help write some of the code in this project, as well as to answer 
  questions on architectural, class designs, and menial tasks such as creating css styles, but dinhcodes has made 
  sure to understand and review all code written by co-pilot.
--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="300" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues a delete command.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="600" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="350" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)
The following is a (partial) class diagram of the `UI` component:

<puml src="diagrams/UiClassDiagram.puml" width="900" alt="Structure of the UI Component" />

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `TabSection`,
`ListSection` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the
commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` objects residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550" />

The simplified sequence diagram below illustrates the interactions within the `Logic` component, taking a delete
command as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" width="800" alt="Interactions Inside the Logic Component for a delete command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.

</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:

* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g., during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="700" />

The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` object.
* stores a `FilterDetails` object that represents the details of the most recent filter applied to the hall ledger data.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="800" />

</box>

### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### How UI triggers command execution

Hall Ledger uses callback-style dependency injection to keep low-level UI components decoupled from command execution
details.

`MainWindow` holds the reference to `Logic` and implements small executor interfaces that match what child UI
components need.

The diagram below shows this flow: `MainWindow` implements `CommandExecutor` and `FilterExecutor`, then injects
itself into child components through their constructors. As a result, child components can request execution through
interfaces without knowing about `Logic` internals. For example, `CommandBox` only knows it has a `CommandExecutor` to
call when the user submits a command, and 'FilterPanel' only knows it has a `FilterExecutor` to call when the user
updates filter criteria.

<puml src="diagrams/find/UiExecutor.puml" width="700" />

`MainWindow` wires dependencies and defines the execution logic, then passes them into child UI constructors as
callbacks:

```java
public class MainWindow extends UiPart<Stage>
        implements CommandExecutor, FilterExecutor {

    public MainWindow(Logic logic) {
        commandBox = new CommandBox(this); // 'this' implements CommandExecutor
        filterPanel = new FilterPanel(logic.getFilterDetails(), this); // 'this' implements FilterExecutor
    }
}
```

Each child component triggers behavior only through these interfaces, without depending on parser or
`LogicManager` implementation details:

```java
// In CommandBox
CommandResult result = commandExecutor.execute(commandText);

// In FilterPanel
CommandResult result = filterExecutor.executeFilter(updatedFilterDetails);
```

This separation keeps responsibilities clear: UI components handle user input, while command and filter execution
remain centralized behind injected callbacks.

### Demerit point tracking

Hall Ledger stores demerit incidents as resident-level records instead of storing only a mutable running total.

Each demerit incident records:
* the DPS rule index,
* the rule title,
* the offence number for that resident and rule,
* the points applied for that occurrence,
* and an optional remark.

The resident’s total demerit points are **derived** by summing the points applied across all stored demerit incidents. This avoids duplicated derived state and keeps the resident’s demerit history auditable.

The demerit feature is split into two user-facing commands:

* `demeritlist` — displays the indexed demerit rule catalogue and point tiers.
* `demerit` — applies an indexed demerit rule to a resident identified by student ID.

#### Rationale for the current design

A resident may commit the same rule multiple times, and the DPS applies different point tiers based on repeated occurrences. Hence, storing only a running total would lose important context such as:
* which rule was applied,
* how many times it has already been committed by that resident,
* and what exact points were awarded for that occurrence.

By storing incidents individually, Hall Ledger can:
* reconstruct total demerit points at any time,
* preserve a readable incident history,
* and support future enhancements such as warnings, alerts, or administrative review.

#### Current scope note

Hall Ledger currently records resident demerit incidents and computes accumulated totals. It does **not** yet
automatically enforce semester-based or lifetime housing sanctions tied to DPS thresholds.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes a delete command to delete a resident in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes an add command to add a new resident. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the resident was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the earlier add command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command is correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

Hall Ledger is a desktop application for Resident Assistants (RAs) and other hall administrators who need to manage
resident contact records quickly and accurately. It is optimized for hall-level resident administration, where users
frequently need to search, update, and maintain resident details such as student ID, room assignment, contact
information, and emergency contact details.

Beyond basic resident record management, Hall Ledger is intended to support common hall-administration workflows such as
tagging residents by attributes (e.g. year of study, major, gender), monitoring occupancy at the room level, tracking
demerit incidents, and serving as a foundation for future hall-management features such as retention-related review and
other resident administration tasks.

Hall Ledger is not intended to replace university-wide housing allocation systems, payment systems, or institutional
access-control systems. Its scope is limited to block-level or hall-level resident management and operational tracking.

**Target user profile:**
* has a need to manage a significant number of resident records within a hall or block
* is a Resident Assistant (RA), hall administrator, or student leader handling resident administration
* frequently needs to search for residents by name, student ID, room number, or tagged attributes
* needs quick access to resident contact details and emergency contacts
* prefers a desktop app over manual spreadsheets or scattered notes
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI-style commands

**Value proposition:** Hall Ledger helps hall administrators manage resident records faster and with fewer errors than
spreadsheets or manual lists, while providing a centralized and command-driven workflow tailored to hall operations.

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a(n) …​ | I want to …​ | So that I can…​ |
|----------|------------|--------------|-----------------|
| `* * *`  | new user   | see usage instructions | refer to instructions when I forget how to use the App |
| `* * *`  | RA         | add a new student contact | keep up-to-date records of students under my care |
| `* * *`  | RA         | list all student contacts | get an overview of students assigned to me |
| `* * *`  | RA         | search for existing student contacts | quickly find a specific resident's information |
| `* * *`  | RA         | delete records of students | remove entries of students no longer in hall |
| `* * *`  | RA         | clear all current student records | quickly reset the system for a new semester |
| `* * *`  | RA         | edit existing contacts | maintain accurate and up-to-date student resident records |
| `* * *`  | RA         | add demerit records to a student profile | track resident behaviour incidents accurately |
| `* * `   | RA         | view the data file in JSON | enjoy data portability without opening the app |
| `* * *`  | RA         | filter existing contacts based on attributes (e.g., block, year) | easily view and manage specific groups of resident students |
| `* * *`  | RA         | add custom tags to students | allow for efficient categorisation of students |
| `* * `   | RA         | add and administer CCA point records to a student's profile | track their CCA contributions accurately |
| `* * `   | RA         | view student CCA records | minimize chance of someone else seeing them by accident |
| `* `     | RA         | rank students by their total accumulated points | prioritize residents based on points-related review |
| `* *`    | RA         | view student demerit records | assess a student's overall behaviour |
| `* `     | RA         | generate occupancy reports by block and room | plan effectively for next semester's housing allocation |
| `* `     | RA         | export all data to a CSV file | share or analyse data externally for admin use |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is Hall Ledger and the **Actor** is the Residential Assistant (RA), unless
specified otherwise)

**Use case: UC01 - Add a new student**

**MSS**

1. RA requests to add a new student, providing the student's details (e.g., name, phone, email, room number, tags).
2. Hall Ledger adds the new student.
3. Hall Ledger displays a success message with the added student's details.

Use case ends.

**Extensions**

* 1a. RA provides an invalid format for the details (e.g., incorrect phone number format).
    * 1a1. Hall Ledger shows an error message indicating the correct format.
    Use case resumes from step 1.

* 1b. A student with a provided unique identifier (Student ID, room) already exists in the system.
    * 1b1. Hall Ledger detects the duplicate entry and displays an errors message. Use case ends.

* 1c. RA fails to provide compulsory details (name, phone, email, room number).
    * 1c1. Hall Ledger shows an error message indicating the compulsory details.
    Use case resumes from step 1.

**Use case: UC02 - View a student's details (basic info, demerit records)**

**MSS**

1. RA requests to list all students.
2. Hall Ledger shows a list of students.
3. RA identifies a specific student from the list.
4. Hall Ledger displays the student's basic information and current demerit total in the UI.

Use case ends.

**Extensions**

* 2a. The student list is empty.
    * 2a1. Hall Ledger indicates that the student list is empty.
    Use case ends.

**Use case: UC03 - Edit a student's info**

**MSS**

1. RA requests to edit specific details (e.g., phone, email, room number, tags) of a student using their student ID.
2. Hall Ledger updates the student's details.
3. Hall Ledger displays a success message with the updated student's details.

Use case ends.

**Extensions**

* 1a. The given student ID does not exist.
    * 1a1. Hall Ledger shows an error message indicating that the student was not found.
    Use case ends.

* 1b. RA provides an invalid format for the details to be updated.
    * 1b1. Hall Ledger shows an error message indicating the correct format.
    Use case resumes from step 1.

* 1c. RA provides details that are exactly the same as the existing ones, resulting in no changes.
  Use case resumes from step 1.

**Use case: UC04 - Delete or Clear student records**

**MSS**

1. RA requests to delete a specific student or clear all current student records.
2. Hall Ledger deletes the specified student or clears all data.
3. Hall Ledger displays a success message reflecting the changes.

Use case ends.

**Extensions**

* 1a. The student list is already empty.
  Use case ends.

* 1b. If deleting, the given student ID is invalid.
    * 1b1. Hall Ledger shows an error message.
    Use case resumes from step 1.

**Use case: UC05 - Search and filter students**

**MSS**

1. RA requests to search by name or filter by specific attributes (e.g., room, year, tags).
2. Hall Ledger processes the query.
3. Hall Ledger shows a list of matching students.

Use case ends.

**Extensions**

* 1a. RA provides empty keywords or invalid command format.
    * 1a1. Hall Ledger shows an error message indicating how to use the specific command correctly.
    Use case ends.

* 1b. RA provides invalid keywords for an attribute that only accept a fixed set of values (year, gender).
    * 1b1. Hall Ledger displays a warning that invalid keywords will be ignored in search.
      Use case ends.

* 2a. No students match the given criteria.
    * 2a1. Hall Ledger shows an empty list and indicates that 0 students were found.
    Use case ends.

**Use case: UC06 - Add a demerit record**

**MSS**

1. RA requests to list the available demerit rules.
2. Hall Ledger displays the indexed demerit rule catalogue.
3. RA requests to add a demerit record to a specific student using the student's ID and the rule index.
4. Hall Ledger records the demerit incident for that student.
5. Hall Ledger updates the resident's total demerit points.
6. Hall Ledger displays a success message showing the applied rule and updated total.

Use case ends.

**Extensions**

* 1a. No demerit rules are available.
    * 1a1. Hall Ledger displays an empty result.
    Use case ends.

* 3a. The given student ID is invalid.
    * 3a1. Hall Ledger shows an error message indicating that the student was not found.
    Use case resumes at step 2.

* 3b. The given rule index does not exist.
    * 3b1. Hall Ledger shows an error message indicating that the rule index is invalid.
    Use case resumes at step 2.

**Use case: UC07 - Export data**

**MSS**

1. RA requests to export all student details data to a CSV file.
2. Hall Ledger gathers the relevant data.
3. Hall Ledger exports the file to the user's system.

Use case ends.

**Extensions**

* 1a. The student list is empty.
    * 1a1. Hall Ledger indicates that there is no data to generate a report or export.
    Use case ends.

### Non-Functional Requirements

1. Should work on any mainstream OS as long as it has Java 17 or above installed.

2. Should be able to store up to 250 students without noticeable sluggishness in performance for typical usage.

3. Should have a response time of < 3 seconds for all instructions.

4. A user with above-average typing speed for regular English text (i.e., not code, not system admin commands) should be able to accomplish most tasks faster using commands than using the mouse.

5. Interaction with the product should be intuitive even for non-technical users, e.g., simple error messages should be displayed and help should be easily available when needed.

6. The product is not required to handle more than one user at a time.

7. The product should be free to use and open source.

8. The product should not need an internet connection to use.

### Glossary

* **Hall** : A residential building on campus that houses students. Each hall is made up of multiple blocks, and each block is made up of multiple rooms.
* **RA** : Resident Assistant, a student leader who is responsible for managing a block of rooms in a hall and the students living in those rooms.
* **CCA** : Co-Curricular Activities, which are activities that students participate in outside of their academic curriculum.
* **DPS** : Demerit Point Structure used as the source reference for Hall Ledger’s demerit rule catalogue.
* **Mainstream OS** : Windows, Linux, Unix, macOS.
* **Non-technical users** : Users who are not familiar with technical jargon, command-line interfaces, or programming concepts.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy it into an empty folder.

   1. Double-click the jar file.<br>
      Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
      Expected: The most recent window size and location is retained.

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete i=A1234567X`<br>
      Expected: The matching resident is deleted from the list. Details of the deleted resident are shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete i=A0000000Z`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `delete i=notAStudentId`<br>
      Expected: Similar to previous.

### Demerit commands

1. Listing demerit rules

   1. Test case: `demeritlist`<br>
      Expected: The result box shows the indexed demerit rule catalogue.

2. Adding a demerit record

   1. Prerequisites: At least one resident exists in the list.

   1. Test case: `demerit i=A1234567X di=18`<br>
      Expected: A success message is shown. The resident's displayed demerit total increases according to the first-offence tier of rule 18.

   1. Test case: Repeat `demerit i=A1234567X di=18`<br>
      Expected: A success message is shown. The resident's displayed demerit total increases again according to the next offence tier.

   1. Test case: `demerit i=A9999999Z di=18`<br>
      Expected: No demerit is added. An error message is shown indicating that the resident was not found.

   1. Test case: `demerit i=A1234567X di=999`<br>
      Expected: No demerit is added. An error message is shown indicating that the rule index does not exist.

### Saving data

1. Dealing with missing/corrupted data files

   1. Delete or rename the `data/addressbook.json` file, then relaunch the app.<br>
      Expected: Hall Ledger starts with sample data or an empty initialized data file instead of crashing.

   1. Edit `data/addressbook.json` into an invalid JSON format, then relaunch the app.<br>
      Expected: Hall Ledger detects that the data file cannot be loaded and starts safely without using the corrupted
      data.
