# Group2

This project is for the Term Project for Group 2.

**Contributors:**

Jordan Bronstetteter, Joyshree Chowdhury, Zachary Nicolai, and Jacob Price

# **Purpose:**

We are developing a software application for the WSU Inc. Wholesale Food Distribution company to help them meet their business needs to become more efficient and profitable. In this sprint, we focused on developing log in/log out functionalities, accepting user inputs from a variety of users, and storing those user's information. To complete this, we implemented the following four different features:

# **Functionalities:**

**Feature #1:**

Functionality:

- Allow registered Users to login to the system using their user id and password.
- Allow Owner or Administrator users to create user profiles with the following details: User last name (max 15 characters), User First Name (max 15 characters), User ID (max 6 alpha numeric characters), Password(combination of alpha numeric and special characters minimum 8 and maximum 16 characters), and User role.
- Allow Owner and Administrator users to delete user profiles.
- Allow Owner and Administrator users to update user profiles.
- Allow Owner and Administrator users to search for users with user id.
- The search result will show the details of the user profile. 
- The system displays 
- Allow Owner and Administrator users to see a lists of registered users.
- Users are allowed to logout of the system at any time.
- Non-Owner and Non-Administrator users are allowed to change their password on first login.

Restrictions:
- Administrator users are not allowed to create, edit or update Administrator or Owner users.
- The system restricts duplicate User ID's when creating or updating user profiles.


**Feature #2:**

Functionalities: 
- Allow Owner User to create a customer profile with the following details : Customer ID( auto generated ) , Full Name, Street Address, City, State, Phone, Balance, Last Paid Amount, Last order Date.
- Allow Owner users to update any customer profiles.
- Allow Owner users to delete any customer profiles.
- Allow Owner users to search for a customer profile by customer ID or name.
- The search result shall show the customer profile details of the searched customer.
- Allow Owner users to see a list of customers in the system. 
- The list should show : full name, phone number , balance and last paid amount.
	     
Restrictions:
-  The last order date shall have no values set during profile creation.
-  The balance and last paid amount fields shall have a default value of
   0 during profile creation.
-  The system shall restrict adding duplicate customer names.
- The system shall restrict invalid inputs from the users for all required                                    
  fields.  
- The system shall restrict empty inputs from the users for all required                                    
                   fields. 
- The system shall restrict adding past date values.
- The system shall allow delete any customer profile only when balance is 0
- The system should display appropriate message when a customer profile is not found by the search. 
 - When deleting a customer profile the system shall warn the users that all information will be deleted.
-  Complete the deletion only if the users accept the deletion.
- The system shall automatically delete all invoices associated with the deleted customer. 


**Feature #3:**

Functionality:
- Allow purchaser or owner users to create vendor profiles with the following information:
   - automatically generated Vendor ID, Full Name, Street Address, City, State, Phone Number, Balance, Last Paid Amount, Last order Date, and Seasonal Discounts Start Date.
- Allow purchaser or owner users to update vendor profiles
- Allow purchaser or owner users to delete vendor profiles
   - only if the selected vendor profile has a balance of 0.
- Allow purchaser or owner users to search for a vendor profile
   - by either Vendor ID or Full Name.
   - The search result shall display either
      - the vendor profile details of the searched vendor if found
      - an appropriate error message if the searched vendor is not found
- Allow owner users to see a list of vendors in the system.
   - the list shall show the vendor profile details of all the vendors

Restrictions:
- The system shall restrict adding duplicate Vendor names.
- The system requires user input for all fields, besides Balance and Last Paid Amount, for profile creation.
   - the Balance and Last Paid Amount fields shall have a default value of zero
- The system shall restrict invalid inputs from the users.
- The system shall warn users that all associated purchase orders will be deleted when deleting a vendor profile
    - If user accepts, the system shall automatically delete all purchase orders associated with the deleted vendor


**Feature #4:**

*Functionality*
- Allow purchaser, owner, or inventory manager to create Item Profiles.
- Automatically generate item ID upon an items creation.
- Purchase, owner, or inventory managers can delete item profiles if if their are no associated purchase 
  orders or invoices.
- Owner or purchase users may search for an item profile by either the item id, item name, or item 
 expiration date.
- A message will be displayed if the search is not found.
- The item profile details will be displayed if the search result is found.
- The system will allow owner users to see a list of items in the system.
- The system shall allow purchase users, or purchase owners to update item profiles.

*Restrictions*
- The system shall restrict users from adding duplicate item names for a given vendor id.
- The system shall restrict invalid input from all users.
- The system shall restrict users from entering a past expiring date.
- The system shall restrict users from entering negative quantities on hand values.


