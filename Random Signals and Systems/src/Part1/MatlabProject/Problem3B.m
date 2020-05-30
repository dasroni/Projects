clear all
clc
close all
range = 0.0 : 0.001: 5.0;
M=10000;
A = rand*.25 + 2;
B = rand*.25 + 2;
f1 = gamrnd(5,1/4);
f2 = gamrnd(5,1/4);
phi1 = [-pi/25 , pi/25];
phi2 = [-pi/25 , pi/25];

for m = 1: M
    a(m) = rand*.25 + 2;
    b(m) = rand*.25 + 2;
    c(m) = gamrnd(5,1/4);
    d(m) = gamrnd(5,1/4);
    e(m) = unifrnd(-pi/25 , pi/25);
    f(m) = unifrnd(-pi/25 , pi/25);
end
    
for m = 1:10
    x = normrnd(0,sqrt(0.1),1,length(range));
    y = my_periodic_signal(range,a(m),b(m),c(m),d(m),e(m),f(m));
    yfinal = y + x;
    hold on
    plot(range,yfinal);
    xlabel('t')
    ylabel('y(t)')
end
