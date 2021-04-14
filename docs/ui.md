# Table of Contents

**[1. Main Pages](#1-main-pages)**\
[1.1 Home Page](#11-home-page)\
[1.2 Contact Page](#12-contact-page)

**[2. User Authentication](#2-user-authentication)**\
[2.1 Login Page](#21-login-page)\
[2.2 Register Page](#22-register-page)\
[2.3 Recover Password Page](#23-recover-password-page)\
[2.3.1 Enter Email Page](#231-enter-email-page)\
[2.3.2 Email Verify Page](#232-email-verify-page)\
[2.3.3 New Password Page](#233-new-password-page)

**[3. Project Prototypes](#3-project-prototypes)**\
[3.1 Backlog Page](#31-backlog-page)

## 1. Main Pages

The following prototypes are describing some main pages of Dira.\
These pages are accessible from anyone , no account is needed.

### 1.1 Home Page

The following prototype is describing the Main Page of Dira.\
This page is reached by the user which is not logged-in / not registered and it is the first page the user will see on our website.

![Initial Page](prototypes/initial_page.png?raw=true "Initial Page")

The "Login" anchor on the header is redirecting the user to "Login Page".\
The "Contact Us" anchor on the footer is redirecting the user to "Contact Page".

### 1.2 Contact Page

![Contact Page](prototypes/contact.png?raw=true "Contact Page")

The above prototype is describing the "Contact Page".

The User could fill theses input fields and contact with the team of Dira by pressing the "Submit" Button.

The "Sing Up" anchor is redirecting the user to "Register Page".\
The "Sign In" anchor is redirecting the user to "Login Page".

## 2. User Authentication

The following prototypes are describing the pages which are connecting with the user authentication.
In User Authentication we assume the Login process, the Register process etc.

### 2.1 Login Page

![Login](prototypes/login.png?raw=true "Login")

The above prototype is describing the "Login Page" .
The user should fill the two input fields and then press the "Sign in" button to login to his account.

The "Keep me logged in" checkbox is an option which can keep login the user for this specific user's device, until the user logout.

The "Create an account" anchor is redirecting the user to "Register Page".\
The "Forgot Password" anchor is redirecting the user to "Recover Password Page".

### 2.2 Register Page

![Register](prototypes/register.png?raw=true "Register")

The above prototype is describing the "Register Page".\
The user should fill the four input fields and then press the "Create Account" button to create his account in Dira.

The "Login" anchor is redirecting the user to "Login Page".\
The "Terms and Conditions" anchor is redirecting the user to "Terms and Coniditions Page".

### 2.3 Recover Password Page

The Recover Password page is a process with three steps. \
First the system needs to verify the user by sending an 6-digit code to his email and after that the user can create a new password for his account.

#### 2.3.1 Enter Email Page

![Password_Recover](prototypes/password_revocer.png?raw=true "Password Recover")

The above prototype is describing the first step of  "Recover Password Page".

The user should fill the input field with his email ( the email that he is declared on this registration ) and press the "Continue" button.

#### 2.3.2 Email Verify Page

![Password Recover Verify](prototypes/password_revocer_verify.png?raw=true "Password Recover Verify")

The above prototype is describing the second step of "Recover Password Page".\
The user should received an 6-digit code on his email, when he completed the previous step.

The user should fill the input field with the code and press the "Continue" button.

#### 2.3.3 New Password Page

![New Password](prototypes/new_password.png?raw=true "New Password")

The above prototype is describing the third step of "Recover Password Page".

The user should type his new password in the two input fields and press the "Create new Password" button.\
After thatthe user will redirect automatically to  "Login Page".

## 3. Project Prototypes

### 3.1 Backlog Page

![Backlog Page](prototypes/backlog.png?raw=true "Backlog Page")

The above prototype is describing the "Backlog Page".\
The "Backlog Page" is the main page of a logged-in user.

The main box with title "Backlog" has a list of all the open issues. \
The boxes on the right are the active sprints and the issues that are included in them.

The "Logout" anchor logout and redirecting the user to "Home Page".\
The "Profile" anchor redirecting the user to "Profile Page".

To create a new issue the user has to press the "Create Issue" button in "Backlog" Box and the following pop-up wll appear.

![Backlog Page New Issue](prototypes/backlog-new_issue.png?raw=true "Backlog Page New Issue")

The user has to fill the details of the issue.


To add some issues to a sprint the user has to press the "Add Issue" button in the Sprint box that he wants and the following pop-up will appear.

![Backlog Page Add Issues to Sprint](prototypes/backlog-issues_to_sprint.png?raw=true "Backlog Add Issues to Sprint")

The User has to select the issue(s) and then has to press the "Add" button to add them to the Sprint.
