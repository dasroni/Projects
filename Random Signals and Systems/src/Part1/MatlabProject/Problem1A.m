clear all
clc
close all
range = 0.0:0.001:5;
B = 3;
f1 = 1;
f2 = 0.5;
phi1 = 0;
phi2 = 0;

for m = 1:100
    A = rand*3 + 3;
    y = my_periodic_signal(range,A,B,f1,f2,phi1,phi2);
    hold on
    plot(range,y);
    xlabel('t');
    ylabel('Y(t)');
end