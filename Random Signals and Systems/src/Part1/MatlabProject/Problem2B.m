clear all
clc 
close all

range = 0.0:0.001:5; %time
A = 4.5;
B = 1;
f1 = 2;
f2 = 1;
phi1 = 0;
phi2 = 0;
x = normrnd(0,sqrt(.1),1,length(range));
y = my_periodic_signal(range,A,B,f1,f2,phi1,phi2);
y1 = y + x;

plot(range, y1,'Color','k','LineWidth',2);
hold on
plot(range, y,'Color','r','LineWidth',1);
grid on
xlabel('t');
ylabel('y(t)');


