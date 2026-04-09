 ---
  layout: default.md
  title: "User Guide"
  pageNav: 3
 ---

## **Hall Ledger User Guide**

**Hall Ledger (HL)** is a desktop application that helps **Resident Assistants (RAs) efficiently manage residents in NUS halls**. It is optimised for users who prefer typing commands, while still offering an intuitive visual interface for viewing resident data at a glance.
<!-- * Table of Contents -->

---
<div class="section table-of-contents">

### **Table of Contents**

1. [Quick Start](#1-quick-start)  
   1.1. [Installation Guide](#1-1-installation-guide)  
   1.2. [Understanding the Interface](#1-2-understanding-the-interface)  
   1.3. [Brief Walkthrough](#1-3-brief-walkthrough)  
2. [General Command Format](#2-general-command-format)  
3. [Adding a Resident](#3-adding-a-resident)  
4. [Editing a Resident](#4-editing-a-resident)
5. [Tagging a Resident](#5-tagging-a-resident)  
5.1. [Adding or Editing Tags](#51-adding-or-editing-tags)  
5.2 [Clearing Tags](#52-clearing-tags)
6. [Viewing Residents](#6-viewing-residents)
7. [Finding Residents](#7-finding-residents)  
7.1. [Using the Command Line](#71-using-typed-commands)  
7.2 [Using the User Interface](#72-using-the-filter-panel)
8. [Managing Resident Remarks](#8-managing-resident-remarks-)  
   8.1. [Adding or Editing a Remark](#81-adding-or-editing-a-remark)  
   8.2. [Clearing a Remark](#82-clearing-a-remark)  
9. [Adding a Demerit Record to a Resident](#9-adding-a-demerit-record-to-a-resident)  
   9.1. [Listing Demerit Rules](#91-listing-demerit-rules)  
   9.2. [Adding a Demerit Record](#92-adding-a-demerit-record)  
10. [Deleting a Resident](#10-deleting-a-resident)  
11. [Clearing All Residents](#11-clearing-all-residents)  
12. [Viewing Help](#12-viewing-help)  
13. [Exiting the Program](#13-exiting-the-program)  
14. [Saving the Data](#14-saving-the-data)  
15. [Editing the Data File](#15-editing-the-data-file)   
16. [FAQ](#16-faq)  
17. [Known Issues](#17-known-issues)  
18. [Command Summary](#18-command-summary)  

---

<div class="section">

### 1. Quick start

##### 1.1 Installation Guide

1. Open the Terminal/Command Prompt
   * On MacOS, refer to this [guide](https://support.apple.com/en-sg/guide/terminal/apd5265185d-f365-44cb-8b09-71a064a42125/mac)
   * On Windows, refer to this [guide](https://www.lifewire.com/how-to-open-command-prompt-2618089)
2. Ensure you have ``Java 17`` installed on your computer. To check, type and enter `java –version` in the Terminal.
3. If the previous step gives an error, you do not have Java 17. 
   * To install on Windows follow this [guide](https://se-education.org/guides/tutorials/javaInstallationWindows.html)
   * To install on MacOS, follow this [guide](https://se-education.org/guides/tutorials/javaInstallationMac.html)
   * To install on Linux, follow this [guide](https://se-education.org/guides/tutorials/javaInstallationLinux.html)
4. Download the Hall Ledger application by clicking on
   this [link](https://github.com/AY2526S2-CS2103T-T15-1/tp/releases/download/v1.5.1/hall-ledger.jar). The download
   should start immedietely.
5. On your computer, move the ``hall-ledger.jar`` file into an empty folder.
6. Using the Terminal (refer to step 1), navigate to the folder where you placed the ``Hall Ledger.jar`` file. Use the [
   `cd`](https://www.geeksforgeeks.org/linux-unix/cd-command-in-linux-with-examples/) command for this step.
7. Type `java -jar hall-ledger.jar` in the Terminal and enter to start the application. 
8. A Hall Ledger window similar to the below should appear in a few seconds. Note that the app will contain some sample
   data.


##### 1.2 Understanding the Interface

When Hall Ledger is opened, you will see an interface like the image below. The interface consists of the following
components:

<img src="images/interface.png" alt="Hall Ledger Integer" width="80%" align="center"/>

<br>

<br>

* **Options Box**: Lets you access basic functions such as viewing the help window and exiting the program.
* **Command Input Box**: Lets you enter commands to execute actions. This is where you will type the commands described in this user guide.
* **Output Box**: Shows the results of your commands, such as success messages or error messages.
* **Resident List**: Shows a list of residents in the ledger. You can click on a resident to view their profile.
* **Profile Tab**: Shows the details of the resident you have selected from the resident list.
* **Filter Panel**: Lets you filter the resident list by various criteria.
* **Other Tabs**: The other tabs (Dashboard, Demerit List and Demerit Records) show additional information about the residents and the ledger.

##### 1.3 Brief Walkthrough

This section gives a brief walkthrough of how to get started with using Hall Ledger. You'll learn how to add a resident,
edit their details, tag their major and finally delete them from the Ledger.

To start, type the following instructions into the command box (in the given order)and press Enter after each:
1. `add n=Vera Tan i=A1234567X e=vera.tan@gmail.com p=+6598765432 r=1A ec=+6512345678` to add a resident named `Vera Tan` to the ledger.
2. `edit i=A1234567X p=+6512345678` to edit Vera's phone number to `+6512345678`.
3. `tag i=A1234567X m=Computer Science` to tag Vera's major as `Computer Science`
4. Finally, `delete i=A1234567X` to delete Vera from the ledger. After entering this command, a confirmation dialog will appear. Click **Confirm** to proceed with the deletion, or click **Cancel** to stop the deletion.

<box type="tip">
<b>Tip:  </b>  Type and enter `help` in the command box to see a list of available commands and their usage formats.
</box>

</div>


--------------------------------------------------------------------------------------------------------------------

### 2. General Command Format

The commands used in Hall Ledger generally follow the format: `COMMAND i=STUDENT_ID [PREFIX=...]` where:
* `COMMAND` is the action you want to perform (e.g. `add`, `edit`, `delete`, etc.)
* `i=STUDENT_ID` is used to specify the resident you want to perform the action on.
  * The student ID must start with an uppercase 'A', followed by 6-8 digits, and end with an uppercase letter (e.g. `A1234567X`).
* `PREFIX=...` are used to specify the details of the action you want to perform. The specific prefixes used depend on the command.
* ``[ ]`` indicates that the field is optional, and can be omitted if not needed. This is followed throughout this guide.

**Example:**  
`remark i=A1234567X rm="Allergic to peanuts"` adds the remark "Allergic to peanuts" to the resident with student ID A1234567X.

<img src="images/command-format.png" alt="Command Format" width="80%" align="center"/>
<br>
<br>
<box type="info">
<b>Note:</b> This is a general command format, not all commands follow this format. For example, the `list` command does not require a student ID or any prefixes. Refer to the specific command's usage format for details.
</box>
</div>


***

<div class="section">

### 3. Adding a Resident

Adds a new person to the hall ledger.

**Command:** `add`

**Usage**: `add n=NAME p=PHONE_NUMBER e=EMAIL i=STUDENT_ID r=ROOM_NUMBER ec=EMERGENCY_CONTACT`
* All fields are required.

<box type="wrong">
<b>Duplicate student IDs and room numbers are not allowed.</b> If you try to add a resident with a student ID/room number that already exists in the ledger, Hall Ledger will show an error message and the command will fail.
</box>

Examples:
* `add n=John Doe p=+6598765432 e=johnd@example.com i=A101010X r=10A ec=+9123459876`


</div>

<div class="section">

***
### 4. Editing a Resident

Edits an existing resident in the _Hall Ledger_.

**Command:** `edit`

**Usage**: `edit i=STUDENT_ID [n=NAME] [p=PHONE] [e=EMAIL] [r=ROOM_NUMBER] [ec=EMERGENCY_CONTACT]`

* Edits the resident with the specified `STUDENT_ID`.
* At least one of the optional fields must be provided.

Examples:
* `edit i=A1234567X p=+6591234567 e=johndoe@example.com` edits the phone number and email address of the resident with student ID A1234567X to be +6591234567 and johndoe@example.com respectively.
* `edit i=A8765432Y n=Betsy Crower ec=+6598765432` edits the name and emergency contact of the resident with student ID A8765432Y to be Betsy Crower and +6598765432 respectively.
 
</div>
 
***

<div class="section">

### 5. Tagging a Resident

Tags are **optional labels** that can be added to a resident’s profile.  

**Command:** `tag`

There are three types of tags in Hall Ledger:
| Tag Type            | Constraints                                                                 | Examples                          | Prefix |
|---------------------|------------------------------------------------------------------------------|-----------------------------------|-------|
| **Major**           | Must be a valid academic major. Letters, spaces, and `&` are allowed.       | Computer Science, Economics & Business | `m=`   |
| **Year**            | Must be a number from 1 to 6 (inclusive).                                   | 1, 4                              | `y=`   |
| **Gender Pronouns** | Accepts `he/him`, `she/her`, or `they/them`. Input is flexible (e.g. `he`, `her`), and will be standardised automatically. | she/her, they/them | `g=`   |

<box type="info">
For residents with <b>double majors</b>, you can separate the two majors with an <code>&</code> symbol (e.g. <code>Computer Science & Mathematics</code>). However, Hall Ledger will treat that as a single Major tag, and not as two separate Major tags.
</box>

##### 5.1 Adding or Editing Tags

**Usage:** `tag i=STUDENT_ID [m=MAJOR] [y=YEAR] [g=GENDER]`

* Adds or edits tags for the resident uniquely identified by `STUDENT_ID`
* At least one of the optional tag fields (`m=`, `y=`, `g=`) must be provided
* Only tag types specified in the command will be added or edited. All other tags will remain unchanged
* Re-tagging a resident will **overwrite** previously assigned tags of that type

Usage Examples:
* `tag i=A0123456N y=3 m=Information Systems` 
* `tag i=A0101010X g=she/her`

##### 5.2 Clearing Tags

**Usage:** `tag i=STUDENT_ID [m=] [y=] [g=]`

* Providing an empty value for a tag field (e.g. `m=`, `y=`, `g=`) will clear the existing tag of that type for the specified resident.

Example usage:
* `tag i=A0123456N y=` clears the Year tag but leaves Major and Gender tag unchanged.
* `tag i=A0101010X g= y= m=` clears all tags for the resident

</div>

***

<div class="section">

### 6. Viewing Residents

Displays all residents the resident list panel on the right 


**Command:** `list`

</div>

***

<div class="section">

### 7. Finding Residents

Hall Ledger allows you to search for residents by:
* **Name**
* **Phone Number**
* **Email**
* **Room Number**
* **Student ID**
* **Emergency Contact**
* **Year**
* **Major**
* **Gender**   

You can perform searches either through the **typed commands** or through the **filter panel**.

<box type="tip">
<b>Tip</b>: You may use the <code>list</code> command to reset the resident list after performing a search with the <code>find</code> command or
after using the Filter panel. This will allow you to see all residents again.
</box>


##### 7.1 Using Typed Commands

**Command:** `find`

**Usage:** `find [n=NAME] [p=PHONE] [e=EMAIL] [r=ROOM_NUMBER] [i=STUDENT_ID] [ec=EMERGENCY_CONTACT] [y=YEAR] [m=MAJOR] [g=GENDER]`

**Example**
Suppose you want to find all residents named "Alex":

* Type in the command box: `find n=Alex`
* The resident list updates to show all residents whose names match "Alex"

**Example: Finding residents with different prefixes**
Suppose you want to find residents named "Alex" who are in Year 2. You can search for both criteria at once:

* Type in the command box: `find n=Alex y=Y2`
* The resident list updates to show only residents who match **both** the name "Alex" **and** Year 2

**Example: Finding residents using multiple keywords within the same criterion**
Suppose you want to find residents named "Alex" or "Bernice". You can search for multiple values within the same
criterion by repeating that field:

* Type in the command box: `find n=Alex n=Bernice`
* The resident list updates to show only residents whose name match either "Alex" **or** Bernice

##### 7.2 Using the Filter Panel

The Filter Panel supports the same sea behaviour as the typed `find` command.

**Steps:**

1. Open the Filter panel (if it is collapsed).

   ![Default filter panel](images/empty-filter-panel.png)

2. Click on a filter field (e.g., "Search by Name"), then type a keyword.

3. Press Enter to add the keyword. The resident list updates to show residents that match the keyword in that field.

   ![Filter panel with one keyword](images/filter-panel-with-name-alex-and-bernice.png)

4. Add more keywords if needed:
    * Add more keywords in the same field to include residents that match any of the keywords in that field.
    * Add keywords in other fields to limit results to residents that match the keywords in every field you used.

5. To remove a keyword, click the `x` next to it.

6. To clear the filter completely, remove all keywords from all fields or type 'list' in the command box.

<box type="warning">

Entering a command in the command box will reset the Filter panel.

</box>

<box type="tip">

**Tips:**

* Matching ignores letter case, and keyword order does not matter.
* Using more than one filter field makes the results more specific.
* Using more keywords in one field helps you find residents matching any of those keywords.
* Hall Ledger supports fuzzy matching, so you can still find results even when you type a partial keyword or make a
  small typo. For more details, see [Fuzzy Matching Details](FuzzyMatching.md).

</box>

</div>

***

<div class="section">

### 8. Managing Resident Remarks: 

Remarks are **optional short notes** that can be added to a resident’s profile.
They can be used to store important information about the resident that does not fit into the other fields, such as allergies, medical conditions, or other special notes. 
You can view remarks in the resident's profile tab.    

**Command:** `remark`

##### 8.1 Adding or Editing a Remark
 
**Usage:** `remark i=STUDENT_ID rm=REMARK`

- Adds or edits a remark for the resident uniquely identified by `STUDENT_ID`.
- If a remark **already exists** for the resident, it will be **overwritten** by the new remark.
- There is no character limit for remarks, but keeping them concise is recommended for readability.
<box type="warning">
Remarks can contain any content. However, <b>avoid using special characters</b> that may interfere with the command format (e.g., <code>=</code> or <code>i=</code>), as they may cause issues when editing or clearing remarks.
</box>

Example usages:
- `remark i=A1234567X rm=Allergic to peanuts`
- `remark i=A1121212X rm=Has asthma, needs inhaler nearby`      



##### 8.2 Clearing a Remark
 
**Usage:** `remark i=STUDENT_ID rm=`

- Providing an empty `rm=` field clears the existing remark for the specified resident.

Example usage:
- `remark i=A1121212X rm=`

</div>

***

<div class="section">

### 9. Adding a Demerit Record to a Resident

##### 9.1 Listing Demerit Rules

Shows the indexed demerit rules available in Hall Ledger.

**Command:** `demeritlist`

* Displays the demerit rule catalogue together with the rule index and point tiers.
* You can use the displayed rule index together with the `demerit` command when recording a resident’s demerit incident.
* This list can also be viewed in the Demerit List tab

##### 9.2 Adding a Demerit Record

Adds a demerit record to an existing resident.

**Command:** `demerit`

**Usage:** `demerit i=STUDENT_ID di=RULE_INDEX [rm=REMARK]`

* Applies the demerit rule identified by `RULE_INDEX` to the resident identified by `STUDENT_ID`.
* `STUDENT_ID` must refer to an existing resident in Hall Ledger.
* `RULE_INDEX` must match one of the indexed rules shown by `demeritlist`.
* If the same resident receives the same rule again, Hall Ledger automatically applies the next offence tier for that
  rule.
* `rm=` is optional and can be used to store a short context note for that incident.
* The resident’s displayed total demerit points will update after the command succeeds.

Examples:
* `demerit i=A1234567X di=18`
* `demerit i=A1234567X di=18 rm=Visitor during quiet hours`
* `demerit i=A0312075X di=28 rm=Common pantry left dirty`

</div>

***

<div class="section">

### 10. Deleting a Resident

Deletes the resident identified by student ID from Hall Ledger.

Format: `delete i=STUDENT_ID`

Example:
* `delete i=A0312075X`

After a valid delete command is entered, Hall Ledger shows a confirmation dialog before the resident is actually
removed.

* Click **Confirm** to proceed with the deletion.
* Click **Cancel** to stop the deletion. Hall Ledger will display the message `Deletion cancelled.` and no resident will
  be removed.

If the command format is invalid, Hall Ledger will show an error message instead of opening the confirmation dialog.

![Delete confirmation dialog](images/deleteConfirmation.png)

</div>

***

<div class="section">

### 11. Clearing all Residents

Clears all residents from Hall Ledger all at once.

Command: `clear`

<box type="wrong">

**Caution:**
This action **permanently deletes all resident data**. We recommend creating a backup of your data file before running this command. Once cleared, the **deletion cannot be undone**.

</box>

</div>

***

<div class="section">

### 12. Viewing Help

Opens the Hall Ledger Help window, which displays the available commands and their usage formats.

**Command:** `help`
<div align="center">
<img src="images/help-window.png" alt="Help Window 2" width="500" align="center"/>      
</div>
<br>
<br>

</div>

***

<div class="section">

### 13. Exiting the program

Exits the program.

**Command:** `exit`

</div>

***

<div class="section">

### 14. Saving the Data

Hall Ledger **automatically saves** your data on your device whenever you make changes. There is no need to manually save
your work.

When you exit the program and open it again later, all your data will still be available.

</div>

***

<div class="section">

### 15. Editing the Data File
Hall Ledger data are saved automatically as a JSON file `[JAR file location]/data/hall-ledger.json`. Advanced users are
welcome to update data directly by editing that data file.

<box type="warning">
<b>Warning:</b> If your changes to the data file make its format invalid, Hall Ledger will discard all data and start with an empty data
file at the next run. Hence, it is recommended to take a backup of the file before editing it.
</box>
Furthermore, certain edits can cause Hall Ledger to behave in unexpected ways (e.g., if a value entered is outside the
acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.


For more details on editing the JSON file, please refer to our [Developer Guide](DeveloperGuide.md)

</div>

--------------------------------------------------------------------------------------------------------------------

<div class="section">

### 16. FAQ

**Q**: How do I transfer my data to another Computer?  
**A**: Install the app on the other computer and overwrite the empty data file it creates with the file that contains
the data of your previous Hall Ledger home folder.

**Q**: Can I edit the data file manually?  
**A**: Yes. Hall Ledger stores data locally in a human-editable text file. However, manual edits should be done
carefully, because invalid edits may prevent Hall Ledger from loading the data correctly.

**Q**: How do I go back to seeing the list of all residents after running `find`?  
**A**: Run the `list` command to see the full list of residents again.

</div>


--------------------------------------------------------------------------------------------------------------------

<div class="section">

### 17. Known issues
1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

</div>

--------------------------------------------------------------------------------------------------------------------

<div class="section">

### 18. Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**[Add](#3-adding-a-resident)** | `add n=NAME p=PHONE_NUMBER e=EMAIL i=STUDENT_ID r=ROOM_NUMBER ec=EMERGENCY_CONTACT` <br> e.g., `add n=James Lee p=+65 98765432 e=james@example.com i=A1234567X r=15R ec=+65 98765432`
**[Clear](#52-clearing-tags)** | `clear`
**[Delete](#10-deleting-a-resident)** | `delete i=STUDENT_ID`<br> e.g., `delete i=A1234567X`
**[Edit](#4-editing-a-resident)** | `edit i=STUDENT_ID [n=NAME] [p=PHONE_NUMBER] [e=EMAIL] [r=ROOM_NUMBER] [ec=EMERGENCY_CONTACT]`<br> e.g., `edit i=A1234567X n=James Lee e=jameslee@example.com`
**[Tag](#51-adding-or-editing-tags)** | `tag i=STUDENT_ID [m=MAJOR] [y=YEAR] [g=GENDER]`<br> e.g., `tag i=A1234567X m=Computer Science y=3`
**[Find](#7-finding-residents)** | `find [n=NAME] [p=PHONE] [e=EMAIL] [r=ROOM_NUMBER] [i=STUDENT_ID] [ec=EMERGENCY_CONTACT] [y=YEAR] [m=MAJOR] [g=GENDER]`<br> e.g., `find n=James y=Y1`
**[Remark](#81-adding-or-editing-a-remark)** | `remark i=STUDENT_ID rm=REMARK`<br> e.g., `remark i=A1234567X rm=Allergic to peanuts`
**[Demerit List](#91-listing-demerit-rules)** | `demeritlist`
**[Add Demerit](#92-adding-a-demerit-record)** | `demerit i=STUDENT_ID di=RULE_INDEX [rm=REMARK]`<br> e.g., `demerit i=A1234567X di=18 rm=Visitor during quiet hours`
**[List](#6-viewing-residents)** | `list`
**[Help](#12-viewing-help)** | `help`
  
</div>
