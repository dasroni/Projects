clear all
clc
close all

x_init=0; 
sig=1;
T=200; 
SIMS=1;

for s=1:SIMS

    x=[];

    x(1)=normrnd(x_init,sqrt(sig));
    tt(1) = target_1(x(1));
    acc(1) = tt(1)/x(1);
    for t=2:T
        
        x(t)=normrnd(x(t-1),sig);
        tt(t) = target_1(x(t));
        acc(t) = tt(t)/x(t);
        u = unidrnd(0,1);
        
        if (u < acc)
            x(t+1) = tt(t);
       
        else
            x(t+1) = x(t);
        end

        
    end
    %subplot(1,2,1)
    %plot(x,tt);
    %subplot(1,2,2)
    %stem(x,tt)
       subplot(2,2,1)
    plot(1:T,x(t));
    hold on;
    
    subplot(2,2,2)
    plot(1:T,acc);
    hold on
   
    subplot(2,2,3)
    plot(1:T,tt);
    hold on
    
    
end




