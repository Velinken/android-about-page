# Android About Page
Create an awesome About Page for your Android App in 2 minutes

![Android About Page](/resources/android-about-page-1.png) ![Android About Page 2](/resources/android-about-page-1.png)

This library allows to generate beautiful About Pages with less effort, it's fully customizable and supports opening specific intent
              
~~~
View aboutPage = new AboutPage(this)
  .isRTL(false)
  .setImage(R.drawable.dummy_image)
  .addItem(versionElement)
  .addItem(adsElement)
  .addGroup("Connect with us")
  .addEmail("elmehdi.sakout@gmail.com")
  .addFacebook("the.medy")
  .addTwitter("medyo80")
  .addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")
  .addPlayStore("com.ideashower.readitlater.pro")
  .create();
~~~

## Setup
Available on Jcenter, Maven and JitPack

~~~
compile 'com.github.medyo:android-about-page:1.0'
~~~


## Usage
### 1. Add Description

~~~
setDescription(String)
~~~

### 2. Add Image
~~~
setImage(Int)
~~~

### 3. Add predefined Social network
The library has already some predefined social networks like :  

* Facebook 
* Twitter
* Instagram
* Youtube
* PlayStore

~~~
addFacebook(String PageID)
addTwitter(String AccountID)
addYoutube(String AccountID)
addPlayStore(String PackageName)
addInstagram(String AccountID)
~~~

### 4. Add Custom Element
For example `app version` :

~~~
Element versionElement = new Element();
versionElement.setTitle("Version 6.2");
addItem(versionElement)
~~~

### 5. Available attributes for Element Class

| Function        | Description  |
| ------------- |:-------------:| -----:|
| setTitle(String) | Set title of the element|
| setColor(Int) | Set color of the element|
| setIcon(Int) | Set icon of the element|
| setValue(String) | Set Element value like Facebook ID|
| setTag(String) | Set a unique tag value to the element|
| setIntent(Intent) | Set an intent to be called on `onClickListener` |


## Sample Project
[medyo/android-about-page/app/](https://github.com/medyo/android-about-page/app/)


## ProGuard
Nothing to include

## License

~~~
The MIT License (MIT)
Copyright (c) 2016 Mehdi Sakout

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
~~~