# ICS414 Summer 2017 Ambient Device
Created by:  
Aaron Jhumar Villanueva  
Ryan Theriot

##### An Ambient device that will track different data sources and show visual representations of the data's current state.

## Current Iteration
The current iteration tracks the US Army corps of Engineers King River Basin Data.  

The Ambient device changes color to indicate the Pine Flat outflow and inflow status.  

When the lamp is BLUE the outflow is greater than the inflow and all is well.  
Weh the lamp is RED the inflow is greater than the outflow and all is not well.  
Green indicates the flows are exactly the same.  
Yellow indicates the data source is not available.  

![alt test](https://raw.githubusercontent.com/aaronvil/ICS414_ambient/ryan-assignment2/doc/images/outflow-greater.png)  
![alt test](https://raw.githubusercontent.com/aaronvil/ICS414_ambient/ryan-assignment2/doc/images/inflow-greater.png)
![alt test](https://raw.githubusercontent.com/aaronvil/ICS414_ambient/ryan-assignment2/doc/images/flows-same.png)
![alt test](https://raw.githubusercontent.com/aaronvil/ICS414_ambient/ryan-assignment2/doc/images/no-date.png)

## Interface Controls
The Min and Max Sliders allow you to change the min and max flow values.  

The Reset Slider button sets the sliders to values that are ideal for the current data.  

It is best to click this button and then adjust the sliders accordingly.  

## EZGraphics
We use [EZGraphics](http://www2.hawaii.edu/~dylank/ics111/) for the UI and Graphics which is developed by Dylan Kobayashi.



![alt test](https://raw.githubusercontent.com/aaronvil/ICS414_ambient/comments-tests/doc/images/assignment1.png)