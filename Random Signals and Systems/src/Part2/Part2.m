clear all
clc
close all

x_init = [0 ,0];
C0 = [1 0; 0 1];
C1 = [1 -0.9; -0.9 1];
C2 = [1 0.9; 0.9 1];
mu = [0, 0]'; 
T = 100;
sim = 1;

for s=1: sim
    
    x = [];
    x(1,:) = mvnrnd(mu,C0);
    
    for t=2: T
        x(t, :) = mvnrnd(x(t-1,:),C0);
    end
    
    subplot(2,2,1)
   
    plot(1:T, x(:, 1),'red');
    hold on
    plot(1:T, x(:, 2),'blue'); 
    title("Plot with C0");
end

for s=1: sim
    
    x = [];
    x(1,:) = mvnrnd(mu,C1);
    
    for t=2: T
        x(t, :) = mvnrnd(x(t-1,:),C1);
    end
    
    subplot(2,2,2)
    plot(1:T, x(:, 1),'red');
    hold on
    plot(1:T, x(:, 2),'blue');
    title("Plot with C1");
end

for s=1: sim
    
    x = [];
    x(1,:) = mvnrnd(mu,C2);
    
    for t=2: T
        x(t, :) = mvnrnd(x(t-1,:),C2);
    end
    
    subplot(2,2,3)
    plot(1:T, x(:, 1),'red');
    hold on
    plot(1:T, x(:, 2),'blue'); 
    title("Plot with C2");
end

