# Table of Contents

**[1. Main Pages](#1-main-pages)**\
[1.1 Home Page](#11-home-page)\
[1.2 Pricing Plans](#12-pricing-plans)

**[2. User Authentication](#2-user-authentication)**\
[2.1 Login Page](#21-login-page)\
[2.2 Register Page](#22-register-page)\
[2.3 Recover Password Page](#23-recover-password-page)\
[2.3.1 Enter Email Page](#231-enter-email-page)\
[2.3.2 Email Verify Page](#232-email-verify-page)\
[2.3.3 New Password Page](#233-new-password-page)\
[2.4 Main Page](#24-main-page)

**[3. Project Prototypes](#3-project-prototypes)**\
[3.1 Backlog Page](#31-backlog-page)\
[3.2 Active Sprint Page](#32-active-sprint-page)\
[3.3 Epics Page](#33-epics-page)\
[3.4 Members Page](#34-members-page)

## 1. Main Pages

The following prototypes are describing some main pages of Dira.\
These pages are accessible from anyone , no account is needed.

### 1.1 Home Page

The following prototype is describing the Main Page of Dira.\
This page is reached by the user which is not logged-in / not registered and it is the first page the user will see on our website.

![Initial Page](prototypes/Main.jpg?raw=true "Initial Page")

Here they can see some of the features of the software, get informed about its pricing plans,
and join at any moment using the *Sign up* button.

Users that are already registered can use the *Sign in* button on the header to redirect them 
at a login page.

### 1.2 Pricing Plans

![Pricing Plans Page](prototypes/pricing.jpg?raw=true "Pricing Plans")

This prototype provides users two options they can take in order to use the software.
More information about the plans can be found in the [srs file](srs.md).


## 2. User Authentication

The following prototypes are describing the pages which are connecting with the user authentication.
In User Authentication we assume the Login process, the Register process etc.

### 2.1 Login Page

![Login](prototypes/login.png?raw=true "Login")

The above prototype is describing the "Login Page" .
The user should enter their username and password and jump into their main page by pressing the *Submit* button.

Also, we provide a *Keep me logged in* option which can keep the user logged in for this specific device, until the user logs out.

The *Create an account* anchor is redirecting the user to *Register Page*.\
The *Forgot Password* anchor is redirecting the user to *Recover Password Page*.

### 2.2 Register Page

![Register](prototypes/register.png?raw=true "Register")

The above prototype is describing the *Register Page*.\
The user should fill the four input fields and then press the *Create Account* button to create their account in Dira.

The *Login* anchor is redirecting the user to *Login Page*.

### 2.3 Recover Password Page

The Recover Password page is a process with three steps. \
First the system needs to verify the user by sending an 6-digit code to their email and after that the user can create a new password for their account.

#### 2.3.1 Enter Email Page

![Password_Recover](prototypes/recovery_email.png?raw=true "Password Recover")

The above prototype is describing the first step of  *Recover Password Page*.

The user should fill the input field with their email (the email that he is declared on this registration) and press the *Continue* button.

#### 2.3.2 Email Verify Page

![Password Recover Verify](prototypes/recovery_verification_code.png?raw=true "Password Recover Verify")

The above prototype is describing the second step of *Recover Password Page*.\
The user should received an 6-digit code on their email, when they have completed the previous step.

The user should fill the input field with the code and press the *Continue* button.

#### 2.3.3 New Password Page

![New Password](prototypes/recovery_new_password.png?raw=true "Enter new Password")

The above prototype is describing the third step of *Recover Password Page*.

The user should type their new password in the two input fields and press the *Create new Password* button.\
After that, the user will redirect automatically to  *Login Page*.

### 2.4 Main Page

![Main Page](prototypes/product-main.png?raw=true "Main page")

This prototype provides the user a handful of options regarding the user's projects.
They can navigate between the projects they are member to, and the recent issues from those projects.


## 3. Project Prototypes

### 3.1 Backlog Page

![Backlog Page](prototypes/backlog.png?raw=true "Backlog Page")

The above prototype is describing the *Backlog Page*.\
The "Backlog Page" is the main page of a logged-in user.

The main box with title *Backlog* has a list of all the open issues. \
The boxes on the right are the active sprints and the issues that are included in them.

The *Logout* anchor logout and redirecting the user to *Home Page*.\
The *Profile* anchor redirecting the user to *Profile Page*.

To create a new issue, the user has to press the *Create Issue* button in *Backlog* Box and the following pop-up will appear.

![Backlog Page New Issue](prototypes/backlog-new_issue.png?raw=true "Backlog Page New Issue")

The user has to fill the details of the issue.


To add some issues to a sprint the user has to press the *Add Issue* button in the Sprint box that he wants and the following pop-up will appear.

![Backlog Page Add Issues to Sprint](prototypes/backlog-issues_to_sprint.png?raw=true "Backlog Add Issues to Sprint")

The User has to select the issue(s) and then has to press the *Add* button to add them to the Sprint.

### 3.2 Active Sprint Page

![Active Sprint](prototypes/active_sprint.png?raw=True "Active Sprint Page")

This prototype show the user a board of issues for the currently running Sprint.
The first column show the issues that are inactive and are to be done, the second shows issues that are being worked on and the third column shows finished issues. When a user presses an issue, a panel appears on the left with more info about that issue.
Such info includes:
- the issue key as well as its title
- a brief description
- its status, priority and resolution
- a bunch of useful labels
- the asignee and reported users
- the date it was created and how much time is remaining
- 0 or more issue links
- 0 or 1 epic link
- a comment panel

### 3.3 Epics Page

![Epics Page](prototypes/epics.png?raw=True "Epics Page")

This page is where users can view the project's epics and create a new one.

### 3.4 Members Page

![Members Page](prototypes/epics.png?raw=True "Members Page")

The Members Page shows the user all the members under a current project and provides an option to add new members.
