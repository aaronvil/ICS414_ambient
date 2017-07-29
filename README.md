# ICS414 Summer 2017 Ambient Device
Created by:  
Aaron Jhumar Villanueva  
Ryan Theriot

##### An Ambient device that will track different data sources and show visual representations of the data's current state.

## Current Iteration
The current iteration tracks the flowing data sources:  
  - King River Basin Pine Flat Water Flow.  
  - King River Basin Pine Flat Water Temperature.  
  - Steam friends data.  
  - Traffic data from home to school utilizing Bing.  

The Ambient device changes color to indicate the selected data's status.
You can select the different data sources with the top row of buttons.
The user can then set the min and max values for each data source with the sliders. (Steam does not use sliders)

![alt test](https://raw.githubusercontent.com/aaronvil/ICS414_ambient/master/doc/images/Application.PNG)

Below are the current data sources color charts.

![alt test](https://raw.githubusercontent.com/aaronvil/ICS414_ambient/master/doc/images/PineFlatFlow.png)  

---  
 
![alt test](https://raw.githubusercontent.com/aaronvil/ICS414_ambient/master/doc/images/PineFlatTemp.png)  

---  

![alt test](https://raw.githubusercontent.com/aaronvil/ICS414_ambient/master/doc/images/Steam.png)  

---  

![alt test](https://raw.githubusercontent.com/aaronvil/ICS414_ambient/master/doc/images/BingTraffic.png)  

---  

## Interface Controls
The Min and Max Sliders allow the user to change the min and max values.  

The Reset Slider button sets the sliders to values that are ideal for the current data.  

It is best to click this button and then adjust the sliders accordingly.  

## EZGraphics
We use [EZGraphics](http://www2.hawaii.edu/~dylank/ics111/) for the UI and Graphics which is developed by Dylan Kobayashi.
