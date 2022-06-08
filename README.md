# VerticalStepperProgress
Vertical step by step progress widget

> Step 1. Add the JitPack repository to your build file

```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```  
> Step 2. Add the dependency

```gradle
dependencies {
	        implementation 'com.github.ScreamShiv:VerticalStepperProgress:Tag'
	}
```	
  
> Replace Tag with latest version. currently 1.0.0  

> Step 3. Add the widget to your xml and set the data in code

```
   VerticalStepperProgress stepperProgress = findViewById(R.id.my_progress);
   stepperProgress.setProgressWidth(4);
   stepperProgress.setStepSize(30);
   stepperProgress.setTickImageSize(24);
   stepperProgress.createStepper(steps,list,sublist,completed);
   
   // steps --> total steps required
   // list --> arraylist of string containing the steps title
   // sublist --> arraylist of string containing the steps subtitle
   // completed --> total steps completed
```

> Customize the UI as per your requirements in the xml file.

> Get a well made widget for you

![Error](https://user-images.githubusercontent.com/37095473/172666004-522523a4-71b4-4f12-b1c0-b0e2606306d0.png)
  
  
