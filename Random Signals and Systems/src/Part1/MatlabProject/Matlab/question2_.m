clear all
clc 
close all

x_init=[0 0]; sig=[1 0; 0 1]; T=100; SIMS=1;mu = [0 0]';
sig1 = [1 -0.9; -0.9 1];
sig2 = [1 0.9; 0.9 1];

for s=1:SIMS
    x=[];
    x(1,:)=mvnrnd(mu,sig);
    for t=2:T
        x(t,:)=mvnrnd(x(t-1,:),sig);
    end
    plot(1:T,x(:,1),'red');
    hold on
    plot(1:T,x(:,2),'blue');
end
grid on

for s=1:SIMS
    x=[];
    x(1,:)=mvnrnd(mu,sig1);
    for t=2:T
        x(t,:)=mvnrnd(x(t-1,:),sig1);
    end
    figure()
    plot(1:T,x(:,1),'red');
    hold on
    plot(1:T,x(:,2),'blue');
end
grid on

for s=1:SIMS
    x=[];
    x(1,:)=mvnrnd(mu,sig2);
    for t=2:T
        x(t,:)=mvnrnd(x(t-1,:),sig2);
    end
    figure()
    plot(1:T,x(:,1),'red');
    hold on
    plot(1:T,x(:,2),'blue');
end
grid on
%when there is a minus on the diagonal on the covariance matrix they are
%negativelly correlated so one one goes up the other one goes down. 

%when there is a plius on the covariance matrix they are positvely
%correlated so they go up together.

%If it is zero it does not matter what happens they go in random order. 