clear all
clc
close all

range = 0.0:0.001:5;

f1 = 1;
f2 = 0.5;
phi1 = [-pi,0,pi];
phi2 = 0;
probability = [1/3,1/3,1/3];

for m = 1:100
    A = rand*3 + 3;
    B = -1*(rand*3 + 3);
    data = datasample(phi1,1,'Weights',probability);
    y = my_periodic_signal(range,A,B,f1,f2,data,phi2);
    hold on
    plot(range,y);
    xlabel('t');
    ylabel('y(t)');
end