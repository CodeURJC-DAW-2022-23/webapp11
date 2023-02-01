# **3TechMarket**

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
---
</details>

<details><summary>Users and their permissions</summary>

| Entity | Description |
| ----------- | ----------- |
| **Admin** - **Site owner**|  This user will be able to add new products, manage stock and purchases and remove products, as well as moderating reviews. This entity owns the whole website and can make changes to any other entities excluding support messages, which are only owned by support agents and users.  |
| **Support** | These users will see and answers all complaints from the registered customers. These users won't be able to make purchases, use the cart or the wishing list, they will be able to browse the web as usual but their actions will be limited to replying to customer queries. They own the support messages entity, and they can manage their own profile | 
| **Registered customer** | These users will be able to make purchases, add products to a wishlist, use the shopping cart, add reviews (they will also be able to delete their own reviews), view their purchase history, generate invoices and contact support. They own their own entity and can modify their user as much as they want, they can also modify the support message entity, marking a query as solved, or creating one, adding a new table to the database.  | 
| **Guest** | These users will be able to browse the whole website, but their reach will be limited and they won't be able to make purchases, contact support, or have any wishlist. They will be able to have a shopping cart which they will preserve if they register later. They won't own any entity and won't be able to modify any entity. They are only allowed to create a new user entity, registering on our website. | 
---

</details>

<details><summary>Images</summary>

| Entity | Description |
| ----------- | ----------- |
| Users | This entity includes the user profile picture, this image can be uploaded by the user |
| Products | The products will contain one or more pictures of the product we are selling, these pictures can only be uploaded by the administrator.| 
| Purchase history | The purchase history will contain a thumbnail of the product the user bought, this will be the same image as the product's main image| 
| Reviews | Users adding reviews will be able to upload up to 3 photos of the product, no one will be able to change these photos once the review is submitted.| 
---

</details>



<details><summary>Charts</summary>

Our web app will include 2 types of charts, the first one will be available for anyone, it will be a price history chart and it will be shown on each product, this is a type of line chart.
The other chart will be a bar chart showing the quantities sold each day for the last week for the administrator.

</details>

<details><summary>Complementary technologies</summary>
- Invoice generator: Each user will be able to download a PDF invoice of their purchases.
- Automatic emails: Users will receive notification emails when they register and when they make purchases on our website.
- Stock notifications: Users will be able to receive a notification on the website when they toogle the let me know when there's stock button on our out of stock products.
- Support notifications: If a support agent replies to a customer, this customer will have a notification on his dashboard.
- Use of Google Maps for the user to be able pin point his location, avoiding the trouble of inputting all the address details.

</details>


<details><summary>Algorithm</summary>

The algorithm used on our web app will be a simple recommendation algorithm, which will recommend products to our users while they are browsing our web page, according to different factors like their history, their wishlist, their recent purchases or their cart.

</details>