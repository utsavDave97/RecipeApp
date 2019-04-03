# RecipeApp
An app for food lovers and also for those who want to try some hand in creating their own recipe.

## What this app is about
* This app displays Recipes from **Food2Fork API** along with their images. You could also share the particular recipe with friends.
* Further, the displayed Recipes could also be liked which would than store it locally, so you could access it anytime.
* Another great feature of this app is that you could **Create Your Own** Recipe, you could insert image, cook time, ingredients, directions for that recipe.
* The Recipe made by you would be stored locally and access it as per your need.
* Also, this app offers you **Calorie Calculator** through which you can calculate calories needed per day based on whether you want to gain weight or lose weight. 

## App logo
![Logo](https://github.com/utsavDave97/RecipeApp/blob/master/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png)

## Libraries & API Used
* [Food2Fork API](https://www.food2fork.com/)- API through which Recipes are displayed.
* [Volley](https://developer.android.com/training/volley) - Library used to make API calls
* [Picasso](https://square.github.io/picasso/) - Library used to display high resolution images
* [Image Picker Library](https://github.com/IhorKlimov/SmartImagePicker) - Library used to pick images from Camera/Gallery
* [Skeleton View](https://github.com/ethanhua/Skeleton) - Library used to display Skeleton view of how content are going to loaded
* [Step By Step](https://github.com/stepstone-tech/android-material-stepper) - Library used to show step by step form in Create Your Own Screen
* [Material Stepper](https://github.com/fython/MaterialStepperView) - Library used to show steps in Recipes' directions

## Author
* **Utsav Dave**

## Technologies Used
* [Android Studio](https://developer.android.com/studio)
* [Php Storm](https://www.jetbrains.com/phpstorm/)
* [Sketch](https://www.sketch.com/)
* [MySQL](https://www.mysql.com/)

## Screenshots
* As soon as the app launches HomeScreen loads up. This screen pulls images, title, url from **Food2Fork API**. Here, the user can share the Recipe on any platform. Also, user can search Recipe and it would load results from API.

![HomeScreen](https://github.com/utsavDave97/RecipeApp/blob/master/ScreenShots/HomeScreenGIF.gif)

* The below screen is Calorie Calculator - a unique feature of the app to calculate calories needed based on whether to gain or lose weight. User needs to input age, gender, weight, height, & exercise level.

![CalorieCalculator](https://github.com/utsavDave97/RecipeApp/blob/master/ScreenShots/CalorieCalculator.gif)

* The below screen is Create Your Own screen. In here, there are 3 steps in order to add your own recipe, which would be stored in local database. 
   * Here, 1st Step is to add image from Camera/Gallery, Name and Cook time of Recipe. 
   * 2nd Step is to add ingredients. Ingredients are pulled from API created by me using PHP and MySQL database.
      * Also, user can remove ingredients by clicking red minus button.
   * 3rd Step is to add step by step directions on how to make the Recipe. After, clicking Finish button all the data gets inserted inside local database and Your Recipe Fragment opens up.
   * Inside Your Recipe Fragment, clicking on each Recipe pulls data from local database and shows it to user.
      * Also, user can delete Recipe by clicking three elipses and the data gets removed from local database too.

![CreateYourOwn](https://github.com/utsavDave97/RecipeApp/blob/master/ScreenShots/CreateYourOwnGIF.gif)

* The below screen is Favorites Screen - this allows the user to like any Recipe on Home Screen and it would store it locally and show it inside Favorites Fragment.

![Favorites](https://github.com/utsavDave97/RecipeApp/blob/master/ScreenShots/Favorites.gif)

* The below screen is Settings Screen. 
   * Here user can change layout of Home Screen by switching on/off the **Grid View** setting which is only applicable in Tablets.
   * Also, user can sort the list displayed on Home Screen based on trendiness or ratings.
   * Further, user can contact, mail or visit the developer.
   * Lastly, there are Credits and License also shown inside Settings Screen.

![Favorites](https://github.com/utsavDave97/RecipeApp/blob/master/ScreenShots/SettingsGIF.gif)

## License
```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
