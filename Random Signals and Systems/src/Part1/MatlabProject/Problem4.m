clear all
clc
close all

k = 1:20;
lambda = 5;
y = poisspdf(lambda,k);
subplot(3,2,1)
stem (k,y)

M = 10000;
x= poissrnd(lambda , M, 1);

time=1:100;

for t=1:length(time)
    
    x(t) = poissrnd(5);
end


for t=1:length(time)
    n(t) = poissrnd(5);
end
subplot(3,2,2)
stem(time,n)

N = 30;
subplot(3,2,3)
h=histogram(x,N,'Normalization','pdf');
title('Histogram Samples of k');
xlabel('x'); ylabel('Probability');

subplot(3,2,4)
for t=1:length(time)
    e = rand*0.005;
    b = unidrnd(5);
    r = e*t  + b;
    z(t) = poissrnd(r);
end

plot(time,z) 


subplot(3,2,6)
for t = 1: length(time)
    l(t) = n(t) + z(t);
   
end
plot(time,l)



subplot(3,2,5)

  for m = 1:10 
    for t = 1: length(time)
    e = rand*0.005;
    b = unidrnd(5);
    r = e*t  + b;
    z(t) = poissrnd(r);   
    n(t) = poissrnd(5);
        
        
    l(t) = n(t) + z(t);
    
    end
       plot(time,l)
   hold on

  end    

  
