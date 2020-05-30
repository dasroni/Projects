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
probability = [1/2 , 1/2];
data1 = datasample(phi1,1,'Weights',probability);
data2 = datasample(phi2,1,'weights',probability);


for m = 1: M
    a(m) = rand*.25 + 2;
    b(m) = rand*.25 + 2;
    c(m) = gamrnd(5,1/4);
    d(m) = gamrnd(5,1/4);
    e(m) = unifrnd(-pi/25,pi/25);
    f(m) = unifrnd(-pi/25,pi/25);
end
    
N=30;
subplot(3,2,1);
histogram(a,N,'Normalization','pdf');
title('Histogram Samples of A');
xlabel('Range of the Uniform Distribution'); 
ylabel('# times it was chosen');
    
N=30;
subplot(3,2,2);
histogram(b,N,'Normalization','pdf');
title('Histogram Samples of B');
xlabel('Range of Uniform Distribution'); 
ylabel('# times it was chosen');

N=30;
subplot(3,2,3);
histogram(c,N,'Normalization','pdf');
title('Histogram Samples of f1');
xlabel('Range of Gamma Distribution');
ylabel('Probability');

N=30;
subplot(3,2,4);
histogram(d,N,'Normalization','pdf');
title('Histogram Samples of f2');
xlabel('Range of Gamma Distribution'); 
ylabel('Probability');

N=30;
subplot(3,2,5);
histogram(e,N,'Normalization','pdf');
title('Histogram Samples of phi1');
xlabel('Range of phi1'); 
ylabel('Frequency of phi1');

N=30;
subplot(3,2,6);
histogram(f,N,'Normalization','pdf');
title('Histogram Samples of phi2');
xlabel('Range of phi'); 
ylabel('Frequency of phi2');




