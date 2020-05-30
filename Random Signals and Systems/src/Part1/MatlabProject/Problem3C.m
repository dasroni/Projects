clear all
clc
close all
range = 0.0 : 0.001: 5.0;

A = rand*.25 + 2;
B = rand*.25 + 2;
f1 = gamrnd(5,1/4);
f2 = gamrnd(5,1/4);
phi1 = [-pi/25 , pi/25];
phi2 = [-pi/25 , pi/25];

a = (2+2.25)/2;
b = (2+2.25)/2;
c = gamstat(5,1/4); % returns the mean , 5*(1/4) = 1.25
d = gamstat(5,1/4);
e = 0;
f = 0;
    
x = normrnd(0,sqrt(0.1),1,length(range));
y = my_periodic_signal(range,a,b,c,d,e,f);
yfinal = y + x;
hold on
subplot(1,2,1)
plot(range,y)
title('True signal')
xlabel('t')
ylabel('y(t)')
subplot(1,2,2)
plot(range,yfinal)
title('True signal with added noise')
xlabel('t')
ylabel('y(t)')

