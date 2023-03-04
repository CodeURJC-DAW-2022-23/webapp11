<img src="backend/app/src/main/resources/static/logo2.png" alt="3TechMarket Logo" width="200"/>

Our web app provides a online tech shop for users around the world to be able to buy the latest gear with the best reviews at the best prices. We aim to provide the best buying experience, with nice to have features such as support messages, PDF invoices, email confirmations, stock alerts and much more.

## **Team**

- Inés Alonso Izquierdo - i.alonso.2020@alumnos.urjc.es - Github: tsukii14
- Santiago Arias Paniagua - s.arias.2020@alumnos.urjc.es - Github: 4rius
- Ignacio Canículo Domínguez - i.caniculo.2020@alumnos.urjc.es - Github: nachocaniculo
- Ángel Covarrubias Roldán - a.covarrubias.2020@alumnos.urjc.es - Github: Angelcova
- Andreas Wolf Wolf - a.wolf.2020@alumnos.urjc.es - Github: Andreas4122002

## **Additional tools**

- [Miro](https://miro.com/welcomeonboard/bFFKSVBqOXhMM2NWRnNqT3NhcFNpUEV2TVd3dmdOUlRmWGs4anlVRVFpdmdzMG1mSU9QVmRZNURnemREMmpZcnwzNDU4NzY0NTM0NTA5OTk5MjM4fDI=?share_link_id=393370734333)
- [Trello](https://trello.com/invite/daw296/ATTI2056649f95538554568fdd81cacddf0800E7D919)

## **Main web app features**


<details><summary>Entities</summary>

| Entity | Description |
| ----------- | ----------- |
| Users | People that will be using our web app. There will be four types of users which will be explained below. All users can edit their profile, and details, including an image. |
| Products | Our users will be able to add these products to their shopping carts and wishlist. They will all have a description, an image, and different reviews. The administrator will also be able to check the stock.| 
| Purchase history | Users will be able to check their purchase history, with the timestamp of the purchase date, hour and payment method, along with the shipping address and price details. Users will be able to generate PDFs from each purchase so they can have access to their invoice. The administrator will be able to access the whole purchase history of the webpage, including details like who made the purchase.| 
| Reviews | Products will have reviews, and these reviews will be added by the users that previously bought the product. Each review will contain a five star rating, along with a comment, and the possibility of adding images. Only users who have bought the product will be able to add a review. The review will be published inmeadiately after sending it, but the administrator will be able to take it down if it contains harmful or inappropiate content.| 
| Support messages | Each user will be able to send support messages, these messages will be stored on our database, and any support personnel will be able to answer the query, this messages will be kept for as long as the query is active and will be removed after a qeury is marked as resolved by either a user or a support agent. | 

</details>

<details><summary>Users and their permissions</summary>

| Entity | Description |
| ----------- | ----------- |
| **Admin** - **Site owner**|  This user will be able to add new products, manage stock and purchases and remove products, as well as moderating reviews. This entity owns the whole website and can make changes to any other entities excluding support messages, which are only owned by support agents and users.  |
| **Support** | These users will see and answers all complaints from the registered customers. These users won't be able to make purchases, use the cart or the wishing list, they will be able to browse the web as usual but their actions will be limited to replying to customer queries. They own the support messages entity, and they can manage their own profile | 
| **Registered customer** | These users will be able to make purchases, add products to a wishlist, use the shopping cart, add reviews (they will also be able to delete their own reviews), view their purchase history, generate invoices and contact support. They own their own entity and can modify their user as much as they want, they can also modify the support message entity, marking a query as solved, or creating one, adding a new table to the database.  | 
| **Guest** | These users will be able to browse the whole website, but their reach will be limited and they won't be able to make purchases, contact support, or have any wishlist. They will be able to have a shopping cart which they will preserve if they register later. They won't own any entity and won't be able to modify any entity. They are only allowed to create a new user entity, registering on our website. | 

</details>

<details><summary>Images</summary>

| Entity | Description |
| ----------- | ----------- |
| Users | This entity includes the user profile picture, this image can be uploaded by the user |
| Products | The products will contain one or more pictures of the product we are selling, these pictures can only be uploaded by the administrator.| 
| Purchase history | The purchase history will contain a thumbnail of the product the user bought, this will be the same image as the product's main image| 
| Reviews | Users adding reviews will be able to upload up to 3 photos of the product, no one will be able to change these photos once the review is submitted.| 

</details>



<details><summary>Charts</summary>

Our web app will include 2 types of charts, the first one will be available for anyone, it will be a price history chart and it will be shown on each product, this is a type of line chart.
The other chart will be a bar chart showing the quantities sold each day for the last week for the administrator.

</details>

<details><summary>Complementary technologies</summary>
<p>
- Invoice generator: Each user will be able to download a PDF invoice of their purchases.
</p>
<p>
- Automatic emails: Users will receive notification emails when they register and when they make purchases on our website.
</p>
<p>
- Stock notifications: Users will be able to receive a notification on the website when they toogle the let me know when there's stock button on our out of stock products.
</p>
<p>
- Support notifications: If a support agent replies to a customer, this customer will have a notification on his dashboard.
</p>
<p>
- Use of Google Maps for the user to be able pin point his location, avoiding the trouble of inputting all the address details.
</p>

</details>


<details><summary>Algorithm</summary>

The algorithm used on our web app will be a simple recommendation algorithm, which will recommend products to our users while they are browsing our web page, according to different factors like their history, their wishlist, their recent purchases or their cart.

</details>

<br>

## **Navigation and models**

<details><summary><b>Screenshots</b></summary>

*We have developed all our pages from scratch using Bootstrap, if everything works out as planned, our model should be completely responsive, as we have used Bootstrap's grid system. But we will have to start developing the web app itself to know if the model is really responsive.*

#### **Home page**

![Home page](Model%20screenshots/home.png)
*We aim to provide a simple, yet enjoyable, shopping experience, with a few featured items running on a carousel in the middle of the website, as well as some recommendations beneath it, we aim to have at least 2 rows of products, but this is just the model.*

<details><summary> <b> Login </b> </summary>

![Login](Model%20screenshots/login.png)
*A simple login page for the user to be able to authenticate, this allows the administrator and the support agents to login as well*
</details>
<details><summary> <b> Sign up </b> </summary>

![Sign up](Model%20screenshots/signup.png)
*Another simple sign up page, where new users can sign up. Administrators and support agents have to be manually added into the system, so they won't be able to sign up using this form*
</details>

<details><summary> <b> Purchase history </b> </summary>

![Purchase history](Model%20screenshots/purchasehistory.png)
*Our website will let the user know all the purchases he/she has done, as well as getting further assistance, leaving a review, returning the product, or cancelling an order that has recently been made. This history is similar to the one that will be accesible for the administrator. We will also provide a unique ID for each order.*
</details>
<details><summary> <b> Add review </b> </summary>

![Add review](Model%20screenshots/addreview.png)
*Our users will be able to leave reviews that include a title, a comment, and some photos*
</details>
<details><summary> <b> Product view </b> </summary>

![Product view](Model%20screenshots/product.png)
*A view where clients can check out photos, specifications and reviews on a product. They will feature a review, and the rest of them will be available before. Note that this is just a model, and our products will include all their information in English.*
</details>

<details><summary> <b> Confirmation </b> </summary>

![Confirmation](Model%20screenshots/confirmation.png)
*A simple message confirming a purchase providing a unique identifier*

</details>
<details><summary> <b> Error </b> </summary>

![Error](Model%20screenshots/error.png)
*A simple error page*

</details>
<details><summary> <b> Checkout </b> </summary>

![Checkout](Model%20screenshots/checkout.png)
*An easy checkout for users to complete their purchases*

</details>
<details><summary> <b> Shopping cart </b> </summary>

![Shopping cart](Model%20screenshots/shoppingcart.png)
*Clients will be able to add quantities and get errors if there is no stock left, as well as continuing to checkout*

</details>
<details><summary> <b> Search results </b> </summary>

![Search results](Model%20screenshots/searchresults.png)
*How we plan our products to be displayed when a user performs a search*

</details>
<details><summary> <b> Review history </b> </summary>

![Review history](Model%20screenshots/reviewhistory.png)
*How the administrator will be able to moderate reviews*

</details>
<details><summary> <b> Edit profile </b> </summary>

![Edit profile](Model%20screenshots/editprofile.png)
*The user will be able to change anything on its profile*

</details>
<details><summary> <b> Add produtcs </b> </summary>

![Add products](Model%20screenshots/addproduct.png)
*The administrator will be able to upload new products, that will become available for everyone browsing the website the moment the form is submitted. We will add the option to add "tags" so the recommendation algorithm can start rolling out the product to select clients.*

</details>
<details><summary> <b> Edit product </b> </summary>

![Edit product](Model%20screenshots/editproduct.png)
*The administrator will be able to access this tool to update the product's price, as well as name, description, photos...*

</details>
<details><summary> <b> Support messages </b> </summary>

![Support messages](Model%20screenshots/messages.png)
*Users will be able to chat live or leave a conversation opened with a support agent on their messages tab*

</details>
<details><summary> <b> Admin dashboard </b> </summary>

![Admin dashboard](Model%20screenshots/dashboard.png)
*A set of tools for the admin to be able to track the progress of the website, as well as editing and adding products, and modding reviews. The administrator will also be able to access a graph that will show the number of orders that were created the last week.*

</details>

</details>

---

<details><summary><b>Diagram</b></summary>

![Navigation diagram](Model%20screenshots/navigationdiagram.jpg)
<br>
*The diagram shows how the user will be able to move throughout our website, all the screenshots can be found on the tab before, as the diagram was not comprehensible using thumbnails, as our website includes several administrator tools and other features that made it impossible to read.*

</details>

<br>

# **Our Web app - Phase 2**
## New navigation diagram
The navigation is as we established on the previous phase, with minor tweaks, for example, we figured agents should be able to use their
account to buy products, and the admin shouldn't have access to the shopping cart, as it would be nonsense for him to buy products.
We believe the roles are clear enough, we allow the users and agents to buy products, and the admin to manage the website. All the features in
between are available for all of them, as long as they are not related to the purchase of products.
The anonymous user will be able to access the home page, the login page, the sign-up page, the search results page, the product page, and he will
need to log in to access the rest of the features.

Our main pages have been minimally updated, and here are the results:

## Building and running our web app

We provide instructions to build and run a complete .jar package including all the necessary dependencies, as well as running the web app from an IDE or from the command line.
Note: We don't provide instructions to build a .war file, as we believe that the .jar file is more convenient for this project as we don't need to deploy it on a server, and we will use Docker when we deploy it on a server anyway.
<br>

**For this guide to work, you need to have the following installed:**
- [Java 17](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html) *(We use Corretto 17, but any other OpenJDK 17 distribution should work, we use Corretto because Amazon provides it, and it's a trusted source)*
- [Maven](https://maven.apache.org/download.cgi) *(We recommend installing it from your package manager (brew, pacman, apt), or let your IDE do it for you)*
- [Git](https://git-scm.com/downloads) *(You can also use the GitHub Desktop app)*
- Access to our database (We will provide the credentials, URL, ports and allow the IP addresses of the machines that will be accessing the database)
- Access to a terminal
- Access to a web browser (We have tested [Firefox](https://www.mozilla.org/en-US/firefox/new/) and some [Chromium](https://www.chromium.org/getting-involved/download-chromium) based browsers, it should work on anything)
- Access to our [GitHub repository](https://github.com/CodeURJC-DAW-2022-23/webapp11)

Optional:
- [IntelliJ IDEA Ultimate](https://www.jetbrains.com/idea/download/)
- [VS Code](https://code.visualstudio.com/download)
- [MySQL Server](https://dev.mysql.com/downloads/mysql/) *(We use an Azure MySQL database as it was more convenient for us, but it works on any MySQL server)*

*We use Azure's MySQL database as it was more convenient for us than having to deploy lots of objects
when testing the ajax buttons, or other features, plus it has allowed us to save time redeploying it
each time we had to test, or adding lots of products, allowing us to store them indefinitely. This was free
as part of our Microsoft Azure Subscription.*

*Either way, we provide a SampleDataService.java file that creates 3 products, 3 users and a review,
for basic testing it might come in handy, however, to test our web app and get a glimpse of what it looks like
with more products, we highly recommend running it with our database. The credentials can be found
under application.properties, and we can provide access to the IPs that will be running the app*

<details><summary> <b> Setting up a database <i>(Not recommended)</i> </b> </summary>

1. Install MySQL Server
2. Create a database with a schema called `3techmarket`
3. Change the commented lines in the `application.properties` file to match your database's credentials and comment the lines that connect the actual Azure database.

    ![img_6.png](img_6.png)
4. Uncomment the @PostConstruct method in the `SampleDataService.java` file

    ![img_7.png](img_7.png)
5. Run the web app, and it should create the tables and populate them with the sample data
</details>

<details><summary> <b> Building a .jar using mvn and running it from the command line <i>(recommended)</i> </b> </summary>

1. Clone the repository: `git clone https://github.com/CodeURJC-DAW-2022-23/webapp11.git`
2. Navigate to the project's root directory: `cd webapp11/backend/app`, this is where the `pom.xml` file is located
3. Build the .jar file: `mvn clean package`
   - If everything goes well, you should see something like this:
      ![img_4.png](img_4.png)
4. Run the .jar file: `java -jar target/app-0.0.1-SNAPSHOT.jar`
5. Go to `https://localhost:8443/` to see the web app running
6. To stop the web app, press `Ctrl+C` on the terminal
- To run the web app in the background, run `java -jar target/app-0.0.1-SNAPSHOT.jar &` instead of step 4
- To stop the web app, run `kill $(lsof -t -i:8443)` on the terminal
  - To run the web app on a different port, run `java -jar target/app-0.0.1-SNAPSHOT.jar --server.port=XXXX` instead of step 4, where `XXXX` is the port you want to use, we use 8443 because it's the default port for HTTPS when using Spring Boot in development mode.
  - To stop the web app, run `kill $(lsof -t -i:XXXX)` on the terminal, where `XXXX` is the port you used to run the web app

</details>
<details><summary> <b> Running the app from an IDE <i>(easier)</i> </b> </summary>

We recommend using IntelliJ Idea Ultimate, as it's the IDE we use to develop the web app, and it's the one we're most familiar with, but you can use any IDE you want, as long as it supports Maven projects.

<details><summary> <b> Using IntelliJ Idea Ultimate </b> </summary>

1. Open the project in IntelliJ Idea Ultimate, clone the repository if you haven't already: `git clone https://github.com/CodeURJC-DAW-2022-23/webapp11.git`
2. IntelliJ will automatically detect the project as a Maven project, and will ask you if you want to import it, click on `Import Maven Projects`
3. Once the project is imported, navigate to the `AppApplication.java` file, located at `backend/app/src/main/java/com/techmarket/app/AppApplication.java`
4. Right click on the file and click on `Run 'AppApplication'`

    ![img_1.png](img_1.png)
5. Go to `https://localhost:8443/` to see the web app running
6. To stop the web app, press the stop button in the top right corner of the IDE or on the bottom left side of the console
- If your IDE is properly configured, you can also run the web app from the top right corner of the IDE, by clicking on the green play button next to the `AppApplication` class

  ![img_2.png](img_2.png)

</details>

<details><summary> <b> Visual Studio Code </b> </summary>

This steps assume you have the Java Extension Pack and the Spring Boot Extension Pack installed on Visual Studio Code.
1. Open the project in Visual Studio Code, clone the repository if you haven't already: `git clone https://github.com/CodeURJC-DAW-2022-23/webapp11.git`
2. Navigate to the `AppApplication.java` file, located at `backend/app/src/main/java/com/techmarket/app/AppApplication.java`
3. The editor should show a `Run` button above the `main` method, click on it (you can also press the Play button in the top right corner of the editor assuming you have the Spring Boot Extension Pack installed)
   
    ![img.png](img.png)
4. Go to `https://localhost:8443/` to see the web app running
5. To stop the web app, press the stop button in the floating menu bar that will appear at the top of the screen, or press `Ctrl+C` on the terminal

</details>
</details>

<details><summary> <b> Running the app from the command line <i>(faster)</i> </b> </summary>

1. Clone the repository: `git clone https://github.com/CodeURJC-DAW-2022-23/webapp11.git`
2. Navigate to the project's root directory: `cd webapp11/backend/app`, this is where the `pom.xml` file is located
3. Run the app: `mvn spring-boot:run`
   - If everything goes well, you should see something like this:
      
       ![img_3.png](img_3.png)
4. Navigate to `https://localhost:8443/` to see the web app running
5. To stop the web app, press `Ctrl+C` on the terminal
6. To run the web app in the background, run `mvn spring-boot:run &` instead of step 3
7. To stop the web app, run `kill $(lsof -t -i:8443)` on the terminal
  - To run the web app on a different port, run `mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=XXXX` instead of step 3, where `XXXX` is the port you want to use, we use 8443 because it's the default port for HTTPS when using Spring Boot in development mode.
  - To stop the web app, run `kill $(lsof -t -i:XXXX)` on the terminal, where `XXXX` is the port you used to run the web app

</details>

## Diagrams
<details><summary> <b> Entity Relationship Diagram (ERD) </b> </summary>
Please bear in mind that many of the relationships represent lists, and that sequences, which may
appear like we have lots of entities, are actually just one entity with a list of objects. Some of the
relationships overlap on the diagram, to read the diagram, please follow the arrows and read
how the entities are related, comparing the diagram with the code will help you understand it.

![ER Diagram](img_5.png)

</details>

<details><summary> <b> Class and templates diagram </b> </summary>

![Class Diagram](img_6.jpg)

</details>

## Contributions

All members have contributed to the project in a similar way, and all members have worked on all parts of the project, this table is just a rough estimate of the amount of work each member has done.

| Member                                               | Contributions                                                                                                                                                         |
|------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [Santiago Arias](https://github.com/4rius)           | Spring security - All AJAX request buttons - Cart - Purchase history - Image handling - Product search - Automatic emails - Support messages - Entities - Add reviews |
| [Andreas Wolf](https://github.com/Andreas4122002)    | Checkout - Wishlist - Edit profile - Edit product - Admin dashboard - Entities                                                                                        |
| [Ignacio Canículo](https://github.com/nachocaniculo) | Add product - Price history chart - Email password recovery - Login - Signup                                                                                          |
| [Inés Alonso](https://github.com/tsukii14)           | Product details - Review handling - Image handling - Thorough testing                                                                                                                 |
| [Ángel Covarrubias](https://github.com/angelcova)    | Recommendation algorithm - Home page - Thorough testing                                                                                                               |

### 5 most important commits

<details><summary> <b> Santiago Arias </b> </summary>

- [Finished support chat](https://github.com/CodeURJC-DAW-2022-23/webapp11/commit/adbb2af3a1d2687c3d75ad596838626ce0b51691)
- [Ajax button and cart controller](https://github.com/CodeURJC-DAW-2022-23/webapp11/commit/ddbf93d0da4b8249b3f8604d182d21388da7a4bf)
- [Headers,pageable and AJAX with JSON](https://github.com/CodeURJC-DAW-2022-23/webapp11/commit/0f55370475d94e8f47ed2c345ea3ee146af542dc#diff-1cc9442bfbec310ff7a3a5005337b137a0e1c1dde0a3ffe08258b49b34aad16d)
- [Database optimisation (reduced queries for the vast majority of tasks)](https://github.com/CodeURJC-DAW-2022-23/webapp11/commit/fd8278e475a8d64609f27dd5edb72e5dadb21f65)
- [Email confirmation when signing up](https://github.com/CodeURJC-DAW-2022-23/webapp11/commit/5788d318549cb70423c3e8dd691a622316ae3238)

</details>

<details><summary> <b> Andreas Wolf </b> </summary>

</details>

<details><summary> <b> Ignacio Canículo </b> </summary>

</details>

<details><summary> <b> Inés Alonso </b> </summary>

</details>

<details><summary> <b> Ángel Covarrubias </b> </summary>
    
- [Recommendations refactor](https://github.com/CodeURJC-DAW-2022-23/webapp11/commit/3ff044766d26450e162bbd2dd2493b13dc54fb1a)
- [Recommended products](https://github.com/CodeURJC-DAW-2022-23/webapp11/commit/074fce6d60867cae65c9cf5276f001c665dae9ff#diff-82607adf4067143c3ecd7fa4eb43cff33b549f9293579f0a40ba5c9714b05e4d)
- [Some algorithm errors fixed](https://github.com/CodeURJC-DAW-2022-23/webapp11/commit/2a2ec4012f0acfccc2147d117b9db2b794556c2b#diff-62d8fd2e53751f3d3cae52f82e0cd8f3ed87ab4ef25ce26f2c8d3be019c0a3e3)
- [Recommendation algorithm fix](https://github.com/CodeURJC-DAW-2022-23/webapp11/commit/dbe953d2d766617f9f4b1e4730c428a548f4ea21#diff-8ee70ef5ad0b0383cd723ccf16bc5c27c4da071c7b63de1ff2458b06d62c1def)
- [Update recommendation algorithm](https://github.com/CodeURJC-DAW-2022-23/webapp11/commit/fca38df21321509047d4fee5d88d5c1f5a6b539e)
    
</details>

### 5 most edited files

<details><summary> <b> Santiago Arias </b> </summary>

- [ProductController.java](https://github.com/CodeURJC-DAW-2022-23/webapp11/blob/main/backend/app/src/main/java/com/techmarket/app/controller/ProductController.java)
- [CartController.java](https://github.com/CodeURJC-DAW-2022-23/webapp11/blob/development/backend/app/src/main/java/com/techmarket/app/controller/CartController.java)
- [ajaxsearch.js](https://github.com/CodeURJC-DAW-2022-23/webapp11/blob/main/backend/app/src/main/resources/static/ajaxsearch.js)
- [ChatController.java](https://github.com/CodeURJC-DAW-2022-23/webapp11/blob/main/backend/app/src/main/java/com/techmarket/app/controller/ChatController.java)
- [SearchController.java](https://github.com/CodeURJC-DAW-2022-23/webapp11/blob/main/backend/app/src/main/java/com/techmarket/app/controller/SearchController.java)

</details>

<details><summary> <b> Andreas Wolf </b> </summary>

</details>

<details><summary> <b> Ignacio Canículo </b> </summary>

</details>

<details><summary> <b> Inés Alonso </b> </summary>

</details>

<details><summary> <b> Ángel Covarrubias </b> </summary>

- [HomeController.java](https://github.com/CodeURJC-DAW-2022-23/webapp11/blob/main/backend/app/src/main/java/com/techmarket/app/controller/HomeController.java)
- [RecommendationService.java](https://github.com/CodeURJC-DAW-2022-23/webapp11/blob/main/backend/app/src/main/java/com/techmarket/app/service/RecommendationService.java)
- [ProductRepository.java](https://github.com/CodeURJC-DAW-2022-23/webapp11/blob/main/backend/app/src/main/java/com/techmarket/app/Repositories/ProductRepository.java)
- [PurchaseRepository.java](https://github.com/CodeURJC-DAW-2022-23/webapp11/blob/main/backend/app/src/main/java/com/techmarket/app/Repositories/PurchaseRepository.java)
- [index.html](https://github.com/CodeURJC-DAW-2022-23/webapp11/blob/main/backend/app/src/main/resources/templates/index.html)    
    
</details>
